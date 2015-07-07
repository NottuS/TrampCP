package gov.pr.celepar.abi.dao.implementation;

import gov.pr.celepar.abi.dao.CessaoDeUsoDAO;
import gov.pr.celepar.abi.pojo.CessaoDeUso;
import gov.pr.celepar.abi.pojo.Instituicao;
import gov.pr.celepar.abi.pojo.ParametroAgenda;
import gov.pr.celepar.abi.pojo.StatusTermo;
import gov.pr.celepar.abi.util.Log;
import gov.pr.celepar.framework.database.GenericHibernateDAO;
import gov.pr.celepar.framework.database.HibernateUtil;
import gov.pr.celepar.framework.exception.ApplicationException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

/**
 * Classe de manipulação de objetos da classe CessaoDeUso.
 * 
 * @author CodeGenerator - Esta classe foi gerada automaticamente
 * @since 1.0
 * @version 1.0, 24/12/2009
 *
 */
public class CessaoDeUsoHibernateDAO extends GenericHibernateDAO<CessaoDeUso, Integer> implements CessaoDeUsoDAO {

	private static Logger log4j = Logger.getLogger(CessaoDeUsoHibernateDAO.class);
	
	
	/**
	 * Metodo para obter CessaoDeUso com objetos internos carregados.<br>
	 * @author ginaalmeida
	 * @since 01/08/2011
	 * @param  codCessaoDeUso : Integer
	 * @return CessaoDeUso
	 * @throws ApplicationException
	 */
	public CessaoDeUso obterCessaoDeUsoCompleto(Integer codCessaoDeUso) throws ApplicationException {
		try {
			log4j.debug(Log.INICIO);
			Session session = HibernateUtil.currentSession();

			log4j.debug(Log.CONSULTA);
			StringBuffer sbQuery = new StringBuffer();

			sbQuery.append("FROM CessaoDeUso cessaoDeUso ");
			sbQuery.append("LEFT JOIN FETCH cessaoDeUso.listaAssinaturaCessaoDeUso listaAssinaturaCessaoDeUso ");
			sbQuery.append("LEFT JOIN FETCH listaAssinaturaCessaoDeUso.assinatura assinatura ");
			sbQuery.append("LEFT JOIN FETCH assinatura.orgao orgao ");
			sbQuery.append("LEFT JOIN FETCH assinatura.cargoAssinatura cargoAssinatura ");
			sbQuery.append("LEFT JOIN FETCH cessaoDeUso.listaItemCessaoDeUso listaItemCessaoDeUso ");
			sbQuery.append("LEFT JOIN FETCH listaItemCessaoDeUso.edificacao edificacao ");
			sbQuery.append("LEFT JOIN FETCH cessaoDeUso.leiBemImovel leiBemImovel ");
			sbQuery.append("LEFT JOIN FETCH cessaoDeUso.bemImovel bemImovel ");
			sbQuery.append("LEFT JOIN FETCH cessaoDeUso.instituicao instituicao ");
			sbQuery.append("LEFT JOIN FETCH cessaoDeUso.orgaoCessionario orgaoCessionario ");
			sbQuery.append("LEFT JOIN FETCH cessaoDeUso.orgaoCedente orgaoCedente ");
			sbQuery.append("LEFT JOIN FETCH cessaoDeUso.cessaoDeUsoOriginal cessaoDeUsoOriginal ");
			sbQuery.append("LEFT JOIN FETCH cessaoDeUso.statusTermo statusTermo ");
			sbQuery.append("WHERE cessaoDeUso.codCessaoDeUso = :codCessaoDeUso ");

			Query query = session.createQuery(sbQuery.toString());	

			if(codCessaoDeUso != null){
				query.setInteger("codCessaoDeUso", codCessaoDeUso);
			}
			
			return (CessaoDeUso)query.uniqueResult();

		}catch (ApplicationException ae) {
			log4j.debug(ae.getMessage(),ae);
			throw ae;
		}catch (Exception e) {
			log4j.error(e.getMessage(),e);
			throw new ApplicationException("ERRO.1", new String[]{CessaoDeUsoHibernateDAO.class.getSimpleName()+".obterCessaoDeUsoCompleto()"}, e, ApplicationException.ICON_ERRO);
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
	 * Metodo para listar CessaoDeUso vencidos/a vencer.<br>
	 * @author Oksana
	 * @since 02/08/2011
	 * @param  parametroAgenda : ParametroAgenda
	 * @return Collection<CessaoDeUso>
	 * @throws ApplicationException
	 */
	public Collection<CessaoDeUso> listarVencidaAVencer(Instituicao instituicao, ParametroAgenda parametroAgenda) throws ApplicationException {
		
		Collection<CessaoDeUso> coll = new ArrayList<CessaoDeUso>();
		
		try {
			Session session = HibernateUtil.currentSession();
			StringBuffer hql = new StringBuffer();
			hql.append(" FROM CessaoDeUso n ");
			hql.append(" left join fetch n.orgaoCessionario orgaoCessionario ");
			hql.append(" left join fetch n.bemImovel b ");
			hql.append(" left join fetch n.instituicao instituicao ");
			hql.append(	" WHERE n.statusTermo.codStatusTermo <> 2 ");
			hql.append(" AND b.instituicao.codInstituicao = :codInstituicao ");
			if (parametroAgenda != null) {
				hql.append("AND (n.dataFinalVigenciaPrevisao - :dias) <= :dataAtual AND  n.dataFinalVigenciaPrevisao >= :dataAtual");
			}else{
				hql.append("AND n.dataFinalVigenciaPrevisao < :dataAtual ");
			}
			Query q = session.createQuery(hql.toString());
			if (parametroAgenda != null) {
				q.setInteger("dias", parametroAgenda.getNumeroDiasVencimentoCessaoDeUso());				
			}
			q.setDate("dataAtual", new Date());
			q.setInteger("codInstituicao", instituicao.getCodInstituicao());
	
			coll= q.list();
			return coll;			

		} catch (HibernateException he) {
			throw new ApplicationException("ERRO.201",  new String[]{"ao listar Cessão de Uso Vencida e a Vencer"});
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201",  new String[]{"ao listar Cessão de Uso Vencida e a Vencer"}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				HibernateUtil.closeSession();
			}catch (Exception e) {
				log4j.error("Problema ao tentar fechar conexao com o banco de dados: listarVencidaAVencer", e);
			}		
		}
	}

	/**
	 * Metodo de listagem de CessaoDeUso por status.<br>
	 * @author oksana
	 * @since 02/08/2001
	 * @param  StatusTermo: statusTermo
	 * @return List<CessaoDeUso> 
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public List<CessaoDeUso> listarCessaoDeUsoPorStatus(Instituicao instituicao, StatusTermo statusTermo) throws ApplicationException {
		try {
			log4j.debug(Log.INICIO);
			Session session = HibernateUtil.currentSession();

			log4j.debug(Log.CONSULTA);
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append("FROM CessaoDeUso cessaoDeUso ");
			sbQuery.append("LEFT JOIN FETCH cessaoDeUso.bemImovel bemImovel ");
			sbQuery.append("LEFT JOIN FETCH cessaoDeUso.instituicao instituicao ");
			sbQuery.append("LEFT JOIN FETCH cessaoDeUso.statusTermo statusTermo ");
			sbQuery.append("LEFT JOIN FETCH cessaoDeUso.orgaoCessionario orgaoCessionario ");
			sbQuery.append("WHERE statusTermo.codStatusTermo = :codStatusTermo ");
			sbQuery.append("AND bemImovel.instituicao.codInstituicao = :codInstituicao ");
			sbQuery.append("ORDER BY bemImovel.codBemImovel ASC");

			Query query = session.createQuery(sbQuery.toString());	

			query.setInteger("codStatusTermo", statusTermo.getCodStatusTermo());
			query.setInteger("codInstituicao", instituicao.getCodInstituicao());
			
			return (List<CessaoDeUso>)query.list();

		}catch (ApplicationException ae) {
			log4j.debug(ae.getMessage(),ae);
			throw ae;
		}catch (Exception e) {
			log4j.error(e.getMessage(),e);
			throw new ApplicationException("ERRO.1", new String[]{CessaoDeUsoHibernateDAO.class.getSimpleName()+".listarCessaoDeUsoPorStatus()"}, 
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


	@Override
	public Collection<CessaoDeUso> listar(Integer qtdPagina, Integer numPagina, CessaoDeUso cessaoDeUso) throws ApplicationException {
		try {
			Session session = HibernateUtil.currentSession();
			StringBuffer hql = new StringBuffer();
			hql.append(" FROM CessaoDeUso a ")
			.append(" LEFT JOIN FETCH a.bemImovel bemImovel ")	
			.append(" LEFT JOIN FETCH a.instituicao instituicao ")	
			.append(" LEFT JOIN FETCH a.orgaoCedente orgaoCedente ")	
			.append(" LEFT JOIN FETCH a.orgaoCessionario orgaoCessionario ")	
			.append(" LEFT JOIN FETCH a.statusTermo statusTermo ");
			hql.append("WHERE 1=1 ");
			
			if (cessaoDeUso.getCodCessaoDeUso() != null && cessaoDeUso.getCodCessaoDeUso() > 0) {
				hql.append("AND a.codCessaoDeUso = :codCessaoDeUso "); //Nr do Termo
			}
			if (cessaoDeUso.getInstituicao() != null && cessaoDeUso.getInstituicao().getCodInstituicao() != null && cessaoDeUso.getInstituicao().getCodInstituicao() > 0) {
				hql.append("AND a.instituicao.codInstituicao = :codInstituicao ");
			}
			if (cessaoDeUso.getBemImovel() != null) {
				if (cessaoDeUso.getBemImovel().getCodBemImovel() != null && cessaoDeUso.getBemImovel().getCodBemImovel() > 0) {
					hql.append("AND a.bemImovel.codBemImovel = :codBemImovel ");
				}
				if (cessaoDeUso.getBemImovel().getUf() != null && cessaoDeUso.getBemImovel().getUf().length() > 0) {
					hql.append("AND a.bemImovel.uf = :uf ");
				}
				if (cessaoDeUso.getBemImovel().getCodMunicipio() != null && cessaoDeUso.getBemImovel().getCodMunicipio() > 0) {
					hql.append("AND a.bemImovel.codMunicipio = :codMunicipio ");
				}
			}
			if (cessaoDeUso.getProtocolo() != null && cessaoDeUso.getProtocolo().length() > 0) {
				hql.append("AND a.protocolo = :protocolo ");
			}
			if (cessaoDeUso.getStatusTermo() != null && cessaoDeUso.getStatusTermo().getCodStatusTermo() != null && 
					cessaoDeUso.getStatusTermo().getCodStatusTermo() >= 0) {
				hql.append("AND a.statusTermo.codStatusTermo = :codStatusTermo ");
			}
			if (cessaoDeUso.getOrgaoCessionario() != null && cessaoDeUso.getOrgaoCessionario().getCodOrgao() != null &&
					cessaoDeUso.getOrgaoCessionario().getCodOrgao() > 0) {
				hql.append("AND a.orgaoCessionario.codOrgao = :codOrgao ");
			}
			if (cessaoDeUso.getDataInicioVigencia() != null && cessaoDeUso.getDataFinalVigencia() != null) {
				hql.append("AND ((a.dataFinalVigenciaPrevisao >= :dataInic AND  a.dataFinalVigenciaPrevisao <= :dataFim) OR (a.dataFinalVigencia >= :dataInic AND  a.dataFinalVigencia <= :dataFim) )");
			}

			hql.append(" ORDER BY a.codCessaoDeUso ");
			
			Query q = session.createQuery(hql.toString());

			if (cessaoDeUso.getCodCessaoDeUso() != null && cessaoDeUso.getCodCessaoDeUso() > 0) {
				q.setInteger("codCessaoDeUso", cessaoDeUso.getCodCessaoDeUso()); //Nr do Termo
			}
			if (cessaoDeUso.getInstituicao() != null && cessaoDeUso.getInstituicao().getCodInstituicao() != null && cessaoDeUso.getInstituicao().getCodInstituicao() > 0) {
				q.setInteger("codInstituicao", cessaoDeUso.getInstituicao().getCodInstituicao()); 
			}
			if (cessaoDeUso.getBemImovel() != null) {
				if (cessaoDeUso.getBemImovel().getCodBemImovel() != null && cessaoDeUso.getBemImovel().getCodBemImovel() > 0) {
					q.setInteger("codBemImovel", cessaoDeUso.getBemImovel().getCodBemImovel()); 
				}
				if (cessaoDeUso.getBemImovel().getUf() != null && cessaoDeUso.getBemImovel().getUf().length() > 0) {
					q.setString("uf", cessaoDeUso.getBemImovel().getUf()); 
				}
				if (cessaoDeUso.getBemImovel().getCodMunicipio() != null && cessaoDeUso.getBemImovel().getCodMunicipio() > 0) {
					q.setInteger("codMunicipio", cessaoDeUso.getBemImovel().getCodMunicipio()); 
				}
			}
			if (cessaoDeUso.getProtocolo() != null && cessaoDeUso.getProtocolo().length() > 0) {
				q.setString("protocolo", cessaoDeUso.getProtocolo()); 
			}
			if (cessaoDeUso.getStatusTermo() != null && cessaoDeUso.getStatusTermo().getCodStatusTermo() != null && 
					cessaoDeUso.getStatusTermo().getCodStatusTermo() >= 0) {
				q.setInteger("codStatusTermo", cessaoDeUso.getStatusTermo().getCodStatusTermo()); 
			}
			if (cessaoDeUso.getOrgaoCessionario() != null && cessaoDeUso.getOrgaoCessionario().getCodOrgao() != null &&
					cessaoDeUso.getOrgaoCessionario().getCodOrgao() > 0) {
				q.setInteger("codOrgao", cessaoDeUso.getOrgaoCessionario().getCodOrgao()); 
			}
			if (cessaoDeUso.getDataInicioVigencia() != null && cessaoDeUso.getDataFinalVigencia() != null) {
				q.setDate("dataInic", cessaoDeUso.getDataInicioVigencia());
				q.setDate("dataFim", cessaoDeUso.getDataFinalVigencia());
			}

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
	public List<CessaoDeUso> listarCessaoDeUso(CessaoDeUso cessaoDeUso) throws ApplicationException {
		try {
			log4j.debug(Log.INICIO);
			Session session = HibernateUtil.currentSession();

			log4j.debug(Log.CONSULTA);
			StringBuffer sbQuery = new StringBuffer();

			sbQuery.append("FROM CessaoDeUso cessaoDeUso ");
			sbQuery.append("LEFT JOIN FETCH cessaoDeUso.bemImovel bemImovel ");
			sbQuery.append("LEFT JOIN FETCH cessaoDeUso.instituicao instituicao ");
			sbQuery.append("LEFT JOIN FETCH bemImovel.denominacaoImovel denominacaoImovel ");
			sbQuery.append("LEFT JOIN FETCH cessaoDeUso.statusTermo statusTermo ");
			sbQuery.append("LEFT JOIN FETCH cessaoDeUso.orgaoCessionario orgaoCessionario ");
			sbQuery.append("WHERE 1=1");

			if (cessaoDeUso.getCodCessaoDeUso() != null && cessaoDeUso.getCodCessaoDeUso() > 0) {
				sbQuery.append(" AND cessaoDeUso.codCessaoDeUso <> :codCessaoDeUso ");
			}
			if (cessaoDeUso.getStatusTermo() != null && cessaoDeUso.getStatusTermo().getCodStatusTermo() >= 0) {
				sbQuery.append(" AND statusTermo.codStatusTermo = :codStatusTermo ");
			}
			if (cessaoDeUso.getInstituicao() != null && cessaoDeUso.getInstituicao().getCodInstituicao() != null && cessaoDeUso.getInstituicao().getCodInstituicao() > 0) {
				sbQuery.append("AND a.instituicao.codInstituicao = :codInstituicao ");
			}
			if (cessaoDeUso.getBemImovel() != null && cessaoDeUso.getBemImovel().getCodBemImovel() > 0) {
				sbQuery.append(" AND bemImovel.codBemImovel = :codBemImovel ");
			}

			sbQuery.append("ORDER BY bemImovel.codBemImovel ASC");

			Query query = session.createQuery(sbQuery.toString());	

			if (cessaoDeUso.getCodCessaoDeUso() != null && cessaoDeUso.getCodCessaoDeUso() > 0) {
				query.setInteger("codCessaoDeUso", cessaoDeUso.getCodCessaoDeUso());
			}
			if (cessaoDeUso.getStatusTermo() != null && cessaoDeUso.getStatusTermo().getCodStatusTermo() >= 0) {
				query.setInteger("codStatusTermo", cessaoDeUso.getStatusTermo().getCodStatusTermo());
			}
			if (cessaoDeUso.getBemImovel() != null && cessaoDeUso.getBemImovel().getCodBemImovel() > 0) {
				query.setInteger("codBemImovel", cessaoDeUso.getBemImovel().getCodBemImovel());
			}
			if (cessaoDeUso.getInstituicao() != null && cessaoDeUso.getInstituicao().getCodInstituicao() != null && cessaoDeUso.getInstituicao().getCodInstituicao() > 0) {
				query.setInteger("codInstituicao", cessaoDeUso.getInstituicao().getCodInstituicao()); 
			}

			return (List<CessaoDeUso>)query.list();

		}catch (ApplicationException ae) {
			log4j.debug(ae.getMessage(),ae);
			throw ae;
		}catch (Exception e) {
			log4j.error(e.getMessage(),e);
			throw new ApplicationException("ERRO.1", new String[]{CessaoDeUsoHibernateDAO.class.getSimpleName()+".listarCessaoDeUso()"}, 
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

	@Override
	public CessaoDeUso salvarCessaoDeUso(CessaoDeUso cessaoDeUso) throws ApplicationException {
		CessaoDeUso retorno = new CessaoDeUso();
		// Validando parâmetro
		if (cessaoDeUso == null){
			throw new ApplicationException("mensagem.erro.9002", new String[]{"CessaoDeUsoHibernateDAO.salvarCessaoDeUso()"}, ApplicationException.ICON_ERRO);
		}
		
		try {
			// Salvando objeto
			log4j.debug("Obtendo sessao corrente...");
			Session session = HibernateUtil.currentSession();
			session.clear();
			Serializable chave = session.save(cessaoDeUso);
			session.flush();
			log4j.info("SALVAR:" + cessaoDeUso.getClass().getName() + " salvo.");
			retorno = (CessaoDeUso) session.get(CessaoDeUso.class, chave);
			log4j.info("GET:" + cessaoDeUso.getClass().getName() + " q acabou de ser salvo.");
						
		} catch (ConstraintViolationException cve) {
			log4j.error("CessaoDeUsoHibernateDAO.salvarCessaoDeUso()" + cve);
			throw new ApplicationException("mensagem.erro.9003", new String[]{cessaoDeUso.getClass().getSimpleName()}, ApplicationException.ICON_AVISO);
		} catch (HibernateException he) {
			log4j.error("CessaoDeUsoHibernateDAO.salvarCessaoDeUso()" + he);
			throw new ApplicationException("mensagem.erro.9001", new String[]{"CessaoDeUsoHibernateDAO.salvarCessaoDeUso()"}, he, ApplicationException.ICON_ERRO);
		} catch (Exception e) {
			log4j.error("CessaoDeUsoHibernateDAO.salvarCessaoDeUso()" + e);
			throw new ApplicationException("mensagem.erro.9001",new String[]{"CessaoDeUsoHibernateDAO.salvarCessaoDeUso()"}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				log4j.debug("Fechando sessao corrente.");
				HibernateUtil.closeSession();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.9003", new String[]{cessaoDeUso.getClass().getSimpleName()}, ApplicationException.ICON_AVISO);
			}
		}
		
		return retorno;
	}

	@Override
	public List<CessaoDeUso> listarCessaoDeUsoVinculada(CessaoDeUso cessaoDeUso) throws ApplicationException {
		try {
			log4j.debug(Log.INICIO);
			Session session = HibernateUtil.currentSession();

			log4j.debug(Log.CONSULTA);
			StringBuffer sbQuery = new StringBuffer();

			sbQuery.append("FROM CessaoDeUso cessaoDeUso ");
			sbQuery.append("LEFT JOIN FETCH cessaoDeUso.bemImovel bemImovel ");
			sbQuery.append("LEFT JOIN FETCH cessaoDeUso.instituicao instituicao ");
			sbQuery.append("LEFT JOIN FETCH bemImovel.denominacaoImovel denominacaoImovel ");
			sbQuery.append("LEFT JOIN FETCH cessaoDeUso.statusTermo statusTermo ");
			sbQuery.append("LEFT JOIN FETCH cessaoDeUso.orgaoCessionario orgaoCessionario ");
			sbQuery.append("WHERE 1=1");

			if (cessaoDeUso.getCessaoDeUsoOriginal() != null && cessaoDeUso.getCessaoDeUsoOriginal().getCodCessaoDeUso() != null && 
				cessaoDeUso.getCessaoDeUsoOriginal().getCodCessaoDeUso() > 0) {
				sbQuery.append(" AND cessaoDeUso.cessaoDeUsoOriginal.codCessaoDeUso = :codCessaoDeUso ");
			}
			if (cessaoDeUso.getInstituicao() != null && cessaoDeUso.getInstituicao().getCodInstituicao() != null && cessaoDeUso.getInstituicao().getCodInstituicao() > 0) {
				sbQuery.append("AND a.instituicao.codInstituicao = :codInstituicao ");
			}
			if (cessaoDeUso.getStatusTermo() != null && cessaoDeUso.getStatusTermo().getCodStatusTermo() >= 0) {
				sbQuery.append(" AND statusTermo.codStatusTermo = :codStatusTermo ");
			}

			sbQuery.append("ORDER BY bemImovel.codBemImovel ASC");

			Query query = session.createQuery(sbQuery.toString());	

			if (cessaoDeUso.getCessaoDeUsoOriginal() != null && cessaoDeUso.getCessaoDeUsoOriginal().getCodCessaoDeUso() != null && 
				cessaoDeUso.getCessaoDeUsoOriginal().getCodCessaoDeUso() > 0) {
				query.setInteger("codCessaoDeUso", cessaoDeUso.getCessaoDeUsoOriginal().getCodCessaoDeUso());
			}
			if (cessaoDeUso.getInstituicao() != null && cessaoDeUso.getInstituicao().getCodInstituicao() != null && cessaoDeUso.getInstituicao().getCodInstituicao() > 0) {
				query.setInteger("codInstituicao", cessaoDeUso.getInstituicao().getCodInstituicao()); 
			}
			if (cessaoDeUso.getStatusTermo() != null && cessaoDeUso.getStatusTermo().getCodStatusTermo() >= 0) {
				query.setInteger("codStatusTermo", cessaoDeUso.getStatusTermo().getCodStatusTermo());
			}

			return (List<CessaoDeUso>)query.list();

		}catch (ApplicationException ae) {
			log4j.debug(ae.getMessage(),ae);
			throw ae;
		}catch (Exception e) {
			log4j.error(e.getMessage(),e);
			throw new ApplicationException("ERRO.1", new String[]{CessaoDeUsoHibernateDAO.class.getSimpleName()+".listarCessaoDeUso()"}, 
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