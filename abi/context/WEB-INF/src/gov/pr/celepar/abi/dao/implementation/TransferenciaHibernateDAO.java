package gov.pr.celepar.abi.dao.implementation;

import gov.pr.celepar.abi.dao.TransferenciaDAO;
import gov.pr.celepar.abi.pojo.Transferencia;
import gov.pr.celepar.abi.util.Dominios.statusTermo;
import gov.pr.celepar.abi.util.Log;
import gov.pr.celepar.framework.database.GenericHibernateDAO;
import gov.pr.celepar.framework.database.HibernateUtil;
import gov.pr.celepar.framework.exception.ApplicationException;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

/**
 * Classe de manipulação de objetos da classe Transferencia.<BR>
 * @author ginaalmeida
 * @since 02/08/2011
 * @version 1.0
 */
public class TransferenciaHibernateDAO extends GenericHibernateDAO<Transferencia, Integer> implements TransferenciaDAO {

	private static Logger log4j = Logger.getLogger(TransferenciaHibernateDAO.class);
	
	
	/**
	 * Metodo para obter Transferencia com objetos internos carregados.<br>
	 * @author ginaalmeida
	 * @since 02/08/2011
	 * @param  codTransferencia : Integer
	 * @return Transferencia
	 * @throws ApplicationException
	 */
	public Transferencia obterTransferenciaCompleto(Integer codTransferencia) throws ApplicationException {
		try {
			log4j.debug(Log.INICIO);
			Session session = HibernateUtil.currentSession();

			log4j.debug(Log.CONSULTA);
			StringBuffer hql = new StringBuffer();

			hql.append(" FROM Transferencia a ");
			hql.append("LEFT JOIN FETCH a.listaAssinaturaTransferencia listaAssinaturaTransferencia ");
			hql.append("LEFT JOIN FETCH a.listaAssinaturaDocTransferencia listaAssinaturaDocTransferencia ");
			hql.append("LEFT JOIN FETCH listaAssinaturaTransferencia.assinatura assinatura ");
			hql.append("LEFT JOIN FETCH a.listaItemTransferencia listaItemTransferencia ");
			hql.append("LEFT JOIN FETCH listaItemTransferencia.edificacao edificacao ");
			hql.append("LEFT JOIN FETCH a.bemImovel bemImovel ");
			hql.append("LEFT JOIN FETCH bemImovel.classificacaoBemImovel classificacaoBemImovel ");
			hql.append("LEFT JOIN FETCH bemImovel.situacaoLegalCartorial situacaoLegalCartorial ");
			hql.append("LEFT JOIN FETCH bemImovel.situacaoImovel situacaoImovel ");
			hql.append("LEFT JOIN FETCH a.orgaoCessionario orgaoCessionario ");
			hql.append("LEFT JOIN FETCH a.orgaoCedente orgaoCedente ");
			hql.append("LEFT JOIN FETCH assinatura.orgao orgao ");
			hql.append("LEFT JOIN FETCH a.instituicao instituicao ");
			hql.append("LEFT JOIN FETCH assinatura.cargoAssinatura cargoAssinatura ");
			hql.append("LEFT JOIN FETCH a.statusTermo statusTermo ");
			hql.append("WHERE a.codTransferencia = :codTransferencia ");

			Query query = session.createQuery(hql.toString());	

			if(codTransferencia != null){
				query.setInteger("codTransferencia", codTransferencia);
			}
			
			return (Transferencia)query.uniqueResult();

		}catch (ApplicationException ae) {
			log4j.debug(ae.getMessage(),ae);
			throw ae;
		}catch (Exception e) {
			log4j.error(e.getMessage(),e);
			throw new ApplicationException("ERRO.1", new String[]{TransferenciaHibernateDAO.class.getSimpleName()+".obterTransferenciaCompleto()"}, e, ApplicationException.ICON_ERRO);
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
	 * Persiste no banco de dados o objeto Transferencia.<BR>
	 * @author ginaalmeida
	 * @param transferencia : Transferencia
	 * @return Transferencia
	 * @throws ApplicationException
	 */
	public Transferencia salvarTransferencia(Transferencia transferencia) throws ApplicationException {
		Transferencia retorno = new Transferencia();
		
		// Validando parâmetro
		if (transferencia == null){
			throw new ApplicationException("mensagem.erro.9002", new String[]{"TransferenciaHibernateDAO.salvarTransferencia()"}, ApplicationException.ICON_ERRO);
		}
		
		try {
			log4j.debug("Obtendo sessao corrente...");
			Session session = HibernateUtil.currentSession();
			session.clear();
			Serializable chave = session.save(transferencia);
			session.flush();
			log4j.info("SALVAR:" + transferencia.getClass().getName() + " salvo.");
			retorno = (Transferencia) session.get(Transferencia.class, chave);
			log4j.info("GET:" + transferencia.getClass().getName() + " q acabou de ser salvo.");
						
		} catch (ConstraintViolationException cve) {
			log4j.error("TransferenciaHibernateDAO.salvarTransferencia()" + cve);
			throw new ApplicationException("mensagem.erro.9003", new String[]{transferencia.getClass().getSimpleName()}, ApplicationException.ICON_AVISO);
		} catch (HibernateException he) {
			log4j.error("TransferenciaHibernateDAO.salvarTransferencia()" + he);
			throw new ApplicationException("mensagem.erro.9001", new String[]{"TransferenciaHibernateDAO.salvarTransferencia()"}, he, ApplicationException.ICON_ERRO);
		} catch (Exception e) {
			log4j.error("TransferenciaHibernateDAO.salvarTransferencia()" + e);
			throw new ApplicationException("mensagem.erro.9001",new String[]{"TransferenciaHibernateDAO.salvarTransferencia()"}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				log4j.debug("Fechando sessao corrente.");
				HibernateUtil.closeSession();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.9003", new String[]{transferencia.getClass().getSimpleName()}, ApplicationException.ICON_AVISO);
			}
		}
		
		return retorno;
	}

	@Override
	public Collection<Transferencia> listar(Integer qtdPagina, Integer numPagina, Transferencia transferencia, List<Integer> listaCodOrgao) throws ApplicationException {
		try {
			Session session = HibernateUtil.currentSession();
			StringBuffer hql = new StringBuffer();
			hql.append(" FROM Transferencia a ")
			.append(" LEFT JOIN FETCH a.bemImovel bemImovel ")	
			.append(" LEFT JOIN FETCH a.instituicao instituicao ")	
			.append(" LEFT JOIN FETCH a.orgaoCessionario orgaoCessionario ")	
			.append(" LEFT JOIN FETCH a.orgaoCedente orgaoCedente ")	
			.append(" LEFT JOIN FETCH a.statusTermo statusTermo ");
			//Incluido para adequar a disponibilizacao para orgaos
			if (listaCodOrgao != null){
				hql.append(	" left join fetch bemImovel.ocupacaosTerreno ot") ;
				hql.append(	" left join fetch ot.orgao orgTer") ;
				hql.append(	" left join fetch bemImovel.edificacaos edi");
				hql.append(" LEFT JOIN FETCH edi.ocupacaos ocu ");
				hql.append(" LEFT JOIN FETCH ocu.orgao orgOcu ");
				hql.append(	" left join fetch bemImovel.listaTransferencia transferencia") ;
				hql.append(	" left join fetch transferencia.orgaoCessionario orgaoTrans ") ;	
			}
			//
			hql.append(" WHERE 1=1 ");
			
			if (transferencia.getCodTransferencia() != null && transferencia.getCodTransferencia() > 0) {
				hql.append("AND a.codTransferencia = :codTransferencia "); //Nr do Termo
			}
			if (transferencia.getInstituicao() != null && transferencia.getInstituicao().getCodInstituicao() != null && transferencia.getInstituicao().getCodInstituicao() > 0) {
				hql.append("AND a.instituicao.codInstituicao = :codInstituicao ");
			}
			if (transferencia.getBemImovel() != null) {
				if (transferencia.getBemImovel().getCodBemImovel() != null && transferencia.getBemImovel().getCodBemImovel() > 0) {
					hql.append("AND a.bemImovel.codBemImovel = :codBemImovel ");
				}
				if (transferencia.getBemImovel().getUf() != null && transferencia.getBemImovel().getUf().length() > 0) {
					hql.append("AND a.bemImovel.uf = :uf ");
				}
				if (transferencia.getBemImovel().getCodMunicipio() != null && transferencia.getBemImovel().getCodMunicipio() > 0) {
					hql.append("AND a.bemImovel.codMunicipio = :codMunicipio ");
				}
			}
			if (transferencia.getProtocolo() != null && transferencia.getProtocolo().length() > 0) {
				hql.append("AND a.protocolo = :protocolo ");
			}
			if (transferencia.getStatusTermo() != null && transferencia.getStatusTermo().getCodStatusTermo() != null && 
					transferencia.getStatusTermo().getCodStatusTermo() >= 0) {
				hql.append("AND a.statusTermo.codStatusTermo = :codStatusTermo ");
			}
			if (transferencia.getOrgaoCessionario() != null && transferencia.getOrgaoCessionario().getCodOrgao() != null &&
					transferencia.getOrgaoCessionario().getCodOrgao() > 0) {
				hql.append("AND a.orgaoCessionario.codOrgao = :codOrgao ");
			}
			//Incluido para adequar a disponibilizacao para orgaos
			if (listaCodOrgao != null){
				hql.append(" AND (");
				hql.append(" (orgTer.codOrgao IN (:listaCodOrgao)) OR");
				hql.append(" (orgOcu.codOrgao IN (:listaCodOrgao)) OR");
				hql.append(" (transferencia.statusTermo.codStatusTermo = :codStatusTermo AND transferencia.orgaoCessionario.codOrgao IN (:listaCodOrgao))");
				hql.append(") ");
			}
			//

			hql.append(" ORDER BY a.codTransferencia ");
			
			Query q = session.createQuery(hql.toString());

			if (transferencia.getCodTransferencia() != null && transferencia.getCodTransferencia() > 0) {
				q.setInteger("codTransferencia", transferencia.getCodTransferencia()); //Nr do Termo
			}
			if (transferencia.getInstituicao() != null && transferencia.getInstituicao().getCodInstituicao() != null && transferencia.getInstituicao().getCodInstituicao() > 0) {
				q.setInteger("codInstituicao", transferencia.getInstituicao().getCodInstituicao()); 
			}
			if (transferencia.getBemImovel() != null) {
				if (transferencia.getBemImovel().getCodBemImovel() != null && transferencia.getBemImovel().getCodBemImovel() > 0) {
					q.setInteger("codBemImovel", transferencia.getBemImovel().getCodBemImovel()); 
				}
				if (transferencia.getBemImovel().getUf() != null && transferencia.getBemImovel().getUf().length() > 0) {
					q.setString("uf", transferencia.getBemImovel().getUf()); 
				}
				if (transferencia.getBemImovel().getCodMunicipio() != null && transferencia.getBemImovel().getCodMunicipio() > 0) {
					q.setInteger("codMunicipio", transferencia.getBemImovel().getCodMunicipio()); 
				}
			}
			if (transferencia.getProtocolo() != null && transferencia.getProtocolo().length() > 0) {
				q.setString("protocolo", transferencia.getProtocolo()); 
			}
			if (transferencia.getStatusTermo() != null && transferencia.getStatusTermo().getCodStatusTermo() != null && 
					transferencia.getStatusTermo().getCodStatusTermo() >= 0) {
				q.setInteger("codStatusTermo", transferencia.getStatusTermo().getCodStatusTermo()); 
			}
			if (transferencia.getOrgaoCessionario() != null && transferencia.getOrgaoCessionario().getCodOrgao() != null &&
					transferencia.getOrgaoCessionario().getCodOrgao() > 0) {
				q.setInteger("codOrgao", transferencia.getOrgaoCessionario().getCodOrgao()); 
			}
			
			//Incluido para adequar a disponibilizacao para orgaos
			if (listaCodOrgao != null){
				q.setInteger("codStatusTermo", statusTermo.VIGENTE.getIndex());
				q.setParameterList("listaCodOrgao", listaCodOrgao);
			}
			//
			
			if (qtdPagina != null && numPagina != null) {
				q.setMaxResults(qtdPagina.intValue());
				q.setFirstResult( (numPagina.intValue()-1) * qtdPagina.intValue() );
			}

			return q.list();

		} catch (HibernateException he) {
			throw new ApplicationException("mensagem.erro.banco.listar", he);
		} catch (Exception e) {
			throw new ApplicationException("mensagem.erro.banco.listar", e, ApplicationException.ICON_ERRO);
		}finally {
			try {
				HibernateUtil.closeSession();
			} catch (Exception e) {
				log4j.error("Erro ao Fechar Conexao com o Hibernate: ", e);				
			}
		}
	}

	@Override
	public List<Transferencia> listarTransferencia(Transferencia transferencia) throws ApplicationException {
		try {
			log4j.debug(Log.INICIO);
			Session session = HibernateUtil.currentSession();

			StringBuffer sbQuery = new StringBuffer();

			sbQuery.append("FROM Transferencia transferencia ");
			sbQuery.append("LEFT JOIN FETCH transferencia.bemImovel bemImovel ");
			sbQuery.append("LEFT JOIN FETCH transferencia.instituicao instituicao ");
			sbQuery.append("LEFT JOIN FETCH bemImovel.denominacaoImovel denominacaoImovel ");
			sbQuery.append("LEFT JOIN FETCH transferencia.statusTermo statusTermo ");
			sbQuery.append("LEFT JOIN FETCH transferencia.orgaoCessionario orgaoCessionario ");
			sbQuery.append("WHERE 1=1 ");

			if (transferencia.getCodTransferencia() != null && transferencia.getCodTransferencia() > 0) {
				sbQuery.append(" AND transferencia.codTransferencia <> :codTransferencia ");
			}
			if (transferencia.getStatusTermo() != null && transferencia.getStatusTermo().getCodStatusTermo() >= 0) {
				sbQuery.append("AND statusTermo.codStatusTermo = :codStatusTermo ");
			}
			if (transferencia.getInstituicao() != null && transferencia.getInstituicao().getCodInstituicao() != null && transferencia.getInstituicao().getCodInstituicao() > 0) {
				sbQuery.append("AND a.instituicao.codInstituicao = :codInstituicao ");
			}
			if (transferencia.getBemImovel() != null && transferencia.getBemImovel().getCodBemImovel() > 0) {
				sbQuery.append("AND bemImovel.codBemImovel = :codBemImovel ");
			}

			sbQuery.append("ORDER BY bemImovel.codBemImovel ASC");

			Query query = session.createQuery(sbQuery.toString());	

			if (transferencia.getCodTransferencia() != null && transferencia.getCodTransferencia() > 0) {
				query.setInteger("codTransferencia", transferencia.getCodTransferencia());
			}
			if (transferencia.getStatusTermo() != null && transferencia.getStatusTermo().getCodStatusTermo() >= 0) {
				query.setInteger("codStatusTermo", transferencia.getStatusTermo().getCodStatusTermo());
			}
			if (transferencia.getInstituicao() != null && transferencia.getInstituicao().getCodInstituicao() != null && transferencia.getInstituicao().getCodInstituicao() > 0) {
				query.setInteger("codInstituicao", transferencia.getInstituicao().getCodInstituicao()); 
			}
			if (transferencia.getBemImovel() != null && transferencia.getBemImovel().getCodBemImovel() > 0) {
				query.setInteger("codBemImovel", transferencia.getBemImovel().getCodBemImovel());
			}

			return (List<Transferencia>)query.list();

		}catch (ApplicationException ae) {
			log4j.debug(ae.getMessage(),ae);
			throw ae;
		}catch (Exception e) {
			log4j.error(e.getMessage(),e);
			throw new ApplicationException("ERRO.1", new String[]{TransferenciaHibernateDAO.class.getSimpleName()+".listarTransferencia()"}, 
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
	
}