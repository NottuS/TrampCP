package gov.pr.celepar.abi.dao.implementation;

import gov.pr.celepar.abi.dao.VistoriaDAO;
import gov.pr.celepar.abi.pojo.ParametroAgenda;
import gov.pr.celepar.abi.pojo.StatusVistoria;
import gov.pr.celepar.abi.pojo.Vistoria;
import gov.pr.celepar.abi.util.Dominios.statusTermo;
import gov.pr.celepar.abi.util.Log;
import gov.pr.celepar.framework.database.GenericHibernateDAO;
import gov.pr.celepar.framework.database.HibernateUtil;
import gov.pr.celepar.framework.exception.ApplicationException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class VistoriaHibernateDAO extends GenericHibernateDAO<Vistoria, Integer> implements VistoriaDAO {
	
	private static final Logger log4j = Logger.getLogger(VistoriaDAO.class);
	protected static final String LOGINICIO = "Abrindo Sessao Hibernate";
	protected static final String LOGFIM = "Fechando Sessao Hibernate";
	protected static final String LOGERROFECHARSESSAO = "Problema ao tentar fechar sessao do Hibernate";
	
	/**
	 * Metodo de listagem de Vistoria paginada.<br>
	 * @author tatianapires
	 * @since 28/06/2011
	 * @param  codBemImovel : Integer
	 * @param  dataInicial : Date
	 * @param  dataFinal : Date
	 * @param  codSituacaoImovel : Integer
	 * @param  qtdePagina : Integer
	 * @param  numPagina : Integer
	 * @return List<Vistoria> 
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public List<Vistoria> listarVistoriaBemImovelPaginado(Integer codBemImovel, Date dataInicial, Date dataFinal, Integer codSituacaoImovel, Integer qtdePagina, Integer numPagina, List<Integer> listaCodOrgao, Integer codInstituicao) throws ApplicationException {
		try {
			log4j.debug(Log.INICIO);
			Session session = HibernateUtil.currentSession();

			log4j.debug(Log.CONSULTA);
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append("FROM Vistoria vistoria ");
			sbQuery.append("LEFT JOIN FETCH vistoria.bemImovel bemImovel ");
			sbQuery.append("LEFT JOIN FETCH vistoria.statusVistoria statusVistoria ");
			sbQuery.append("LEFT JOIN FETCH vistoria.vistoriador vistoriador ");
			//Incluido para adequar a disponibilizacao para orgaos
			if (listaCodOrgao != null){
				sbQuery.append(	" left join fetch bemImovel.ocupacaosTerreno ot") ;
				sbQuery.append(	" left join fetch ot.orgao orgTer") ;
				sbQuery.append(	" left join fetch bemImovel.edificacaos edi");
				sbQuery.append(" LEFT JOIN FETCH edi.ocupacaos ocu ");
				sbQuery.append(" LEFT JOIN FETCH ocu.orgao orgOcu ");
				sbQuery.append(	" left join fetch bemImovel.listaTransferencia transferencia") ;
				sbQuery.append(	" left join fetch transferencia.orgaoCessionario orgaoTrans") ;	
			}
			//
			
			sbQuery.append(" WHERE 1=1 ");

			sbQuery.append("AND bemImovel.instituicao.codInstituicao = :codInstituicao ");
			
			
			if(codBemImovel != null){
				sbQuery.append("AND bemImovel.nrBemImovel = :codBemImovel ");
			}
			
			if(dataInicial != null){
				sbQuery.append("AND vistoria.dataVistoria >= :dataInicial ");
			}
			
			if(dataFinal != null){
				sbQuery.append("AND vistoria.dataVistoria <= :dataFinal ");
			}
			
			if(codSituacaoImovel != null){
				sbQuery.append("AND statusVistoria.codStatusVistoria = :codSituacaoImovel ");
			}

			//Incluido para adequar a disponibilizacao para orgaos
			if (listaCodOrgao != null){
				sbQuery.append(" AND (");
				sbQuery.append(" (orgTer.codOrgao IN (:listaCodOrgao)) OR");
				sbQuery.append(" (orgOcu.codOrgao IN (:listaCodOrgao)) OR");
				sbQuery.append(" (transferencia.statusTermo.codStatusTermo = :codStatusTermo AND transferencia.orgaoCessionario.codOrgao IN (:listaCodOrgao))");
				sbQuery.append(") ");
			}
			//
			sbQuery.append("ORDER BY bemImovel.codBemImovel ASC, vistoria.dataVistoria DESC ");

			Query query = session.createQuery(sbQuery.toString());	

			if(codBemImovel != null){
				query.setInteger("codBemImovel", codBemImovel);
			}
			
			if(dataInicial != null){
				query.setDate("dataInicial", dataInicial);
			}
			
			if(dataFinal != null){
				query.setDate("dataFinal", dataFinal);
			}
			
			if(codSituacaoImovel != null){
				query.setInteger("codSituacaoImovel", codSituacaoImovel);
			}

			//Incluido para adequar a disponibilizacao para orgaos
			if (listaCodOrgao != null){
				query.setInteger("codStatusTermo", statusTermo.VIGENTE.getIndex());
				query.setParameterList("listaCodOrgao", listaCodOrgao);
			}
			//
			query.setInteger("codInstituicao", codInstituicao);
			
			if (qtdePagina != null && numPagina != null) {
				query.setMaxResults(qtdePagina.intValue());
				query.setFirstResult((numPagina.intValue() -1) * qtdePagina.intValue());
			}

			return (List<Vistoria>)query.list();

		}catch (ApplicationException ae) {
			log4j.debug(ae.getMessage(),ae);
			throw ae;
		}catch (Exception e) {
			log4j.error(e.getMessage(),e);
			throw new ApplicationException("ERRO.1", new String[]{VistoriaHibernateDAO.class.getSimpleName()+".listarVistoriaBemImovelPaginado()"}, 
					e, ApplicationException.ICON_ERRO);
		}finally {
			try {
				log4j.debug(Log.FIM);
				HibernateUtil.closeSession();
			}catch (Exception e) {	
				log4j.error(Log.ERRO_ENCERRAMENTO_SESSAO);
			}
		}
	}

	/**
	 * Metodo de listagem de Vistoria paginada.<br>
	 * @author tatianapires
	 * @since 28/06/2011
	 * @param  codVistoria : Integer
	 * @return Vistoria
	 * @throws ApplicationException
	 */
	public Vistoria obterVistoriaCompleta(Integer codVistoria) throws ApplicationException {
		try {
			log4j.debug(Log.INICIO);
			Session session = HibernateUtil.currentSession();

			log4j.debug(Log.CONSULTA);
			StringBuffer sbQuery = new StringBuffer();

			sbQuery.append("FROM Vistoria vistoria ");
			sbQuery.append("LEFT JOIN FETCH vistoria.bemImovel bemImovel ");
			sbQuery.append("LEFT JOIN FETCH vistoria.statusVistoria statusVistoria ");
			sbQuery.append("LEFT JOIN FETCH vistoria.vistoriador vistoriador ");
			sbQuery.append("LEFT JOIN FETCH vistoria.edificacao edificacao ");
			sbQuery.append("LEFT JOIN FETCH vistoria.listaItemVistoria listaItemVistoria ");
			sbQuery.append("LEFT JOIN FETCH listaItemVistoria.listaItemVistoriaDominio listaItemVistoriaDominio ");
			sbQuery.append("LEFT JOIN FETCH listaItemVistoriaDominio.itemVistoria itemVistoria ");
			sbQuery.append("WHERE vistoria.codVistoria = :codVistoria ");

			Query query = session.createQuery(sbQuery.toString());	

			if(codVistoria != null){
				query.setInteger("codVistoria", codVistoria);
			}
			
			return (Vistoria)query.uniqueResult();

		}catch (ApplicationException ae) {
			log4j.debug(ae.getMessage(),ae);
			throw ae;
		}catch (Exception e) {
			log4j.error(e.getMessage(),e);
			throw new ApplicationException("ERRO.1", new String[]{VistoriaHibernateDAO.class.getSimpleName()+".obterVistoriaCompleta()"}, 
					e, ApplicationException.ICON_ERRO);
		}finally {
			try {
				log4j.debug(Log.FIM);
				HibernateUtil.closeSession();
			}catch (Exception e) {	
				log4j.error(Log.ERRO_ENCERRAMENTO_SESSAO);
			}
		}
	}

	
	/**
	 * Metodo de listagem de Vistoria por status.<br>
	 * @author oksana
	 * @since 07/07/2011
	 * @param  StatusVistoria: statusVistoria
	 * @return List<Vistoria> 
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public List<Vistoria> listarVistoriaPorStatus(StatusVistoria statusVistoria, ParametroAgenda parametroAgenda) throws ApplicationException {
		try {
			log4j.debug(Log.INICIO);
			Session session = HibernateUtil.currentSession();

			log4j.debug(Log.CONSULTA);
			StringBuffer sbQuery = new StringBuffer();

			sbQuery.append("FROM Vistoria vistoria ");
			sbQuery.append("LEFT JOIN FETCH vistoria.bemImovel bemImovel ");
			sbQuery.append("LEFT JOIN FETCH bemImovel.denominacaoImovel denominacaoImovel ");
			sbQuery.append("LEFT JOIN FETCH vistoria.statusVistoria statusVistoria ");
			sbQuery.append("LEFT JOIN FETCH vistoria.vistoriador vistoriador ");
			sbQuery.append("WHERE 1=1 ");
			sbQuery.append("AND bemImovel.instituicao.codInstituicao = :codInstituicao ");
			sbQuery.append("AND statusVistoria.codStatusVistoria = :codStatusVistoria ");

			sbQuery.append("ORDER BY bemImovel.codBemImovel ASC, vistoria.dataVistoria DESC ");

			Query query = session.createQuery(sbQuery.toString());	

			query.setInteger("codStatusVistoria", statusVistoria.getCodStatusVistoria());
			query.setInteger("codInstituicao", parametroAgenda.getInstituicao().getCodInstituicao());
			
			return (List<Vistoria>)query.list();

		}catch (ApplicationException ae) {
			log4j.debug(ae.getMessage(),ae);
			throw ae;
		}catch (Exception e) {
			log4j.error(e.getMessage(),e);
			throw new ApplicationException("ERRO.1", new String[]{VistoriaHibernateDAO.class.getSimpleName()+".listarVistoriaPorStatus()"}, 
					e, ApplicationException.ICON_ERRO);
		}finally {
			try {
				log4j.debug(Log.FIM);
				HibernateUtil.closeSession();
			}catch (Exception e) {	
				log4j.error(Log.ERRO_ENCERRAMENTO_SESSAO);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public Collection<Integer> listarBemImovelPorVistoriador(int codVistoriador) throws ApplicationException {
			
			try {
				Session session = HibernateUtil.currentSession();
				StringBuffer hql = new StringBuffer();
				hql.append("SELECT v.bemImovel.codBemImovel ");
				hql.append("FROM Vistoria v ");
				hql.append("WHERE v.vistoriador.codVistoriador = :codVistoriador ");
				hql.append("GROUP BY v.bemImovel.codBemImovel ");
				hql.append("ORDER BY v.bemImovel");
				
				Query q = session.createQuery(hql.toString()).setInteger("codVistoriador", codVistoriador);

				return (Collection<Integer>)q.list();			

			} catch (HibernateException he) {
				throw new ApplicationException("ERRO.201",  new String[]{"ao listar Bem Imóvel por Vistoriador"});
			} catch (Exception e) {
				throw new ApplicationException("ERRO.201",  new String[]{"ao listar Bem Imóvel por Vistoriador"}, e, ApplicationException.ICON_ERRO);
			} finally {
				try {
					HibernateUtil.closeSession();
				}catch (Exception e) {
					log4j.error("Problema ao tentar fechar conexao com o banco de dados: BemImovel", e);
				}		
			}
		}
	
	@SuppressWarnings("unchecked")
	public StringBuffer listarVistoriadlPorBemImovel(int codVistoriador,int codBemImovel) throws ApplicationException {
			
			try {
				StringBuffer str = new StringBuffer();
				Session session = HibernateUtil.currentSession();
				StringBuffer hql = new StringBuffer();
				hql.append(" SELECT codVistoria ");
				hql.append("FROM Vistoria v ");
				hql.append("WHERE v.vistoriador.codVistoriador = :codVistoriador ");
				hql.append("AND v.bemImovel.codBemImovel = :codBemImovel");
				
				Query q = session.createQuery(hql.toString()).setInteger("codVistoriador", codVistoriador).setInteger("codBemImovel", codBemImovel);
				ArrayList<Integer> listaVistoria = (ArrayList<Integer>)q.list();
				int i = 1;
				
				for (Integer codVistorias: listaVistoria){
					if (i == listaVistoria.size()){
						str.append( String.valueOf(codVistorias).concat("),")); 
						break;
					}
					str.append(String.valueOf(codVistorias).concat(","));
					i++;
				}
				
				return str;		

			} catch (HibernateException he) {
				throw new ApplicationException("ERRO.201",  new String[]{"ao listar Vistoria por Bem Imóvel"});
			} catch (Exception e) {
				throw new ApplicationException("ERRO.201",  new String[]{"ao listar Vistoria por Bem Imóvel"}, e, ApplicationException.ICON_ERRO);
			} finally {
				try {
					HibernateUtil.closeSession();
				}catch (Exception e) {
					log4j.error("Problema ao tentar fechar conexao com o banco de dados: BemImovel", e);
				}		
			}
		}

	@Override
	public void alterarVistoria(Vistoria vistoria) throws ApplicationException {
		try{
			log4j.debug(LOGINICIO);
			Session session = HibernateUtil.currentSession();
			vistoria = (Vistoria) session.merge(vistoria);
			HibernateUtil.currentSession().flush();
			log4j.debug(LOGFIM);
		}catch (ApplicationException ae) {
			log4j.debug(ae.getMessage(),ae);
			throw ae;
		}catch (Exception e) {
			log4j.error(e.getMessage());
			throw new ApplicationException("mensagem.erro.9001", new String[]{VistoriaHibernateDAO.class.getSimpleName().concat(".merge()")}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				HibernateUtil.closeSession();	
			} catch (Exception e) {
				log4j.error(LOGERROFECHARSESSAO, e);
			}
		}
	}

}
