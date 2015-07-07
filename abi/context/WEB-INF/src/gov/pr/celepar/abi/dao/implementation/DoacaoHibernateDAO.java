package gov.pr.celepar.abi.dao.implementation;

import gov.pr.celepar.abi.dao.DoacaoDAO;
import gov.pr.celepar.abi.pojo.Doacao;
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
 * Classe de manipulação de objetos da classe Doacao.
 * 
 * @author CodeGenerator - Esta classe foi gerada automaticamente
 * @since 1.0
 * @version 1.0, 24/12/2009
 *
 */
public class DoacaoHibernateDAO extends GenericHibernateDAO<Doacao, Integer> implements DoacaoDAO {

	private static Logger log4j = Logger.getLogger(DoacaoHibernateDAO.class);
	
	@SuppressWarnings("unchecked")
	public Collection<Doacao> listarVencidaAVencer(Instituicao instituicao, ParametroAgenda parametroAgenda) throws ApplicationException {
		
		Collection<Doacao> coll = new ArrayList<Doacao>();
		
		try {
			Session session = HibernateUtil.currentSession();
			StringBuffer hql = new StringBuffer();
			hql.append(" FROM Doacao n ");
			hql.append(" left join fetch n.orgaoResponsavel orgaoResponsavel ");
			hql.append(" left join fetch n.bemImovel b ");
			hql.append(" WHERE n.statusTermo.codStatusTermo <> 2 ");
			hql.append(" AND b.instituicao.codInstituicao = :codInstituicao ");
			if (parametroAgenda != null) {
				hql.append(" AND (n.dtFimVigencia - :dias) <= :dataAtual AND  n.dtFimVigencia >= :dataAtual");
			}else{
				hql.append(" AND n.dtFimVigencia < :dataAtual ");
			}
			Query q = session.createQuery(hql.toString());
			if (parametroAgenda != null) {
				q.setInteger("dias", parametroAgenda.getNumeroDiasVencimentoDoacao());				
			}
			q.setDate("dataAtual", new Date());
			q.setInteger("codInstituicao", instituicao.getCodInstituicao());
			coll= q.list();
			return coll;			

		} catch (HibernateException he) {
			throw new ApplicationException("ERRO.201",  new String[]{"ao listar Doação Vencida e a Vencer"});
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201",  new String[]{"ao listar Doação Vencida e a Vencer"}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				HibernateUtil.closeSession();
			}catch (Exception e) {
				log4j.error("Problema ao tentar fechar conexao com o banco de dados: listarVencidaAVencer", e);
			}		
		}
	}

	/**
	 * Metodo de listagem de Doacao por status.<br>
	 * @author oksana
	 * @since 08/07/2011
	 * @param  StatusTermo: statusTermo
	 * @return List<Vistoria> 
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public List<Doacao> listarDoacaoPorStatus(StatusTermo statusTermo, Instituicao instituicao) throws ApplicationException {
		try {
			log4j.debug(Log.INICIO);
			Session session = HibernateUtil.currentSession();

			log4j.debug(Log.CONSULTA);
			StringBuffer sbQuery = new StringBuffer();

			sbQuery.append("FROM Doacao doacao ");
			sbQuery.append("LEFT JOIN FETCH doacao.bemImovel bemImovel ");
			sbQuery.append("LEFT JOIN FETCH bemImovel.denominacaoImovel denominacaoImovel ");
			sbQuery.append("LEFT JOIN FETCH doacao.statusTermo statusTermo ");
			sbQuery.append("LEFT JOIN FETCH doacao.orgaoResponsavel orgaoResponsavel ");
			sbQuery.append("WHERE statusTermo.codStatusTermo = :codStatusTermo ");
			sbQuery.append("AND bemImovel.instituicao.codInstituicao = :codInstituicao ");
			sbQuery.append("ORDER BY bemImovel.codBemImovel ASC ");

			Query query = session.createQuery(sbQuery.toString());	

			query.setInteger("codStatusTermo", statusTermo.getCodStatusTermo());
			query.setInteger("codInstituicao", instituicao.getCodInstituicao());
			return (List<Doacao>)query.list();

		}catch (ApplicationException ae) {
			log4j.debug(ae.getMessage(),ae);
			throw ae;
		}catch (Exception e) {
			log4j.error(e.getMessage(),e);
			throw new ApplicationException("ERRO.1", new String[]{DoacaoHibernateDAO.class.getSimpleName()+".listarDoacaoPorStatus()"}, 
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
	public Collection<Doacao> listar(Integer qtdPagina, Integer numPagina, Doacao doacao) throws ApplicationException {
		try {
			Session session = HibernateUtil.currentSession();
			StringBuffer hql = new StringBuffer();
			hql.append(" FROM Doacao a ")
			.append(" LEFT JOIN FETCH a.instituicao instituicao ")	
			.append(" LEFT JOIN FETCH a.bemImovel bemImovel ")	
			.append(" LEFT JOIN FETCH a.orgaoProprietario orgaoProprietario ")	
			.append(" LEFT JOIN FETCH a.orgaoResponsavel orgaoResponsavel ")	
			.append(" LEFT JOIN FETCH a.statusTermo statusTermo ");
			hql.append("WHERE 1=1 ");
			
			if (doacao.getCodDoacao() != null && doacao.getCodDoacao() > 0) {
				hql.append("AND a.codDoacao = :codDoacao "); //Nr do Termo
			}
			if (doacao.getInstituicao() != null && doacao.getInstituicao().getCodInstituicao() != null && doacao.getInstituicao().getCodInstituicao() > 0) {
				hql.append("AND a.instituicao.codInstituicao = :codInstituicao ");
			}
			if (doacao.getBemImovel() != null) {
				if (doacao.getBemImovel().getCodBemImovel() != null && doacao.getBemImovel().getCodBemImovel() > 0) {
					hql.append("AND a.bemImovel.codBemImovel = :codBemImovel ");
				}
				if (doacao.getBemImovel().getUf() != null && doacao.getBemImovel().getUf().length() > 0) {
					hql.append("AND a.bemImovel.uf = :uf ");
				}
				if (doacao.getBemImovel().getCodMunicipio() != null && doacao.getBemImovel().getCodMunicipio() > 0) {
					hql.append("AND a.bemImovel.codMunicipio = :codMunicipio ");
				}
			}
			if (doacao.getProtocolo() != null && doacao.getProtocolo().length() > 0) {
				hql.append("AND a.protocolo = :protocolo ");
			}
			if (doacao.getStatusTermo() != null && doacao.getStatusTermo().getCodStatusTermo() != null && 
					doacao.getStatusTermo().getCodStatusTermo() >= 0) {
				hql.append("AND a.statusTermo.codStatusTermo = :codStatusTermo ");
			}
			if (doacao.getAdministracao() != null && doacao.getAdministracao() > 0) {
				hql.append("AND a.administracao = :administracao ");
			}
			if (doacao.getOrgaoResponsavel() != null && doacao.getOrgaoResponsavel().getCodOrgao() != null &&
					doacao.getOrgaoResponsavel().getCodOrgao() > 0) {
				hql.append("AND a.orgaoResponsavel.codOrgao = :codOrgao ");
			}

			hql.append(" ORDER BY a.codDoacao ");
			
			Query q = session.createQuery(hql.toString());

			if (doacao.getCodDoacao() != null && doacao.getCodDoacao() > 0) {
				q.setInteger("codDoacao", doacao.getCodDoacao()); //Nr do Termo
			}
			if (doacao.getInstituicao() != null && doacao.getInstituicao().getCodInstituicao() != null && doacao.getInstituicao().getCodInstituicao() > 0) {
				q.setInteger("codInstituicao", doacao.getInstituicao().getCodInstituicao()); 
			}
			if (doacao.getBemImovel() != null) {
				if (doacao.getBemImovel().getCodBemImovel() != null && doacao.getBemImovel().getCodBemImovel() > 0) {
					q.setInteger("codBemImovel", doacao.getBemImovel().getCodBemImovel()); 
				}
				if (doacao.getBemImovel().getUf() != null && doacao.getBemImovel().getUf().length() > 0) {
					q.setString("uf", doacao.getBemImovel().getUf()); 
				}
				if (doacao.getBemImovel().getCodMunicipio() != null && doacao.getBemImovel().getCodMunicipio() > 0) {
					q.setInteger("codMunicipio", doacao.getBemImovel().getCodMunicipio()); 
				}
			}
			if (doacao.getProtocolo() != null && doacao.getProtocolo().length() > 0) {
				q.setString("protocolo", doacao.getProtocolo()); 
			}
			if (doacao.getStatusTermo() != null && doacao.getStatusTermo().getCodStatusTermo() != null && 
					doacao.getStatusTermo().getCodStatusTermo() >= 0) {
				q.setInteger("codStatusTermo", doacao.getStatusTermo().getCodStatusTermo()); 
			}
			if (doacao.getAdministracao() != null && doacao.getAdministracao() > 0) {
				q.setInteger("administracao", doacao.getAdministracao()); 
			}
			if (doacao.getOrgaoResponsavel() != null && doacao.getOrgaoResponsavel().getCodOrgao() != null &&
					doacao.getOrgaoResponsavel().getCodOrgao() > 0) {
				q.setInteger("codOrgao", doacao.getOrgaoResponsavel().getCodOrgao()); 
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
	public List<Doacao> listarDoacao(Doacao doacao) throws ApplicationException {
		try {
			log4j.debug(Log.INICIO);
			Session session = HibernateUtil.currentSession();

			log4j.debug(Log.CONSULTA);
			StringBuffer sbQuery = new StringBuffer();

			sbQuery.append("FROM Doacao doacao ");
			sbQuery.append("LEFT JOIN FETCH doacao.bemImovel bemImovel ");
			sbQuery.append("LEFT JOIN FETCH bemImovel.denominacaoImovel denominacaoImovel ");
			sbQuery.append("LEFT JOIN FETCH doacao.statusTermo statusTermo ");
			sbQuery.append("LEFT JOIN FETCH doacao.orgaoResponsavel orgaoResponsavel ");
			sbQuery.append("WHERE 1=1");

			if (doacao.getCodDoacao() != null && doacao.getCodDoacao() > 0) {
				sbQuery.append(" AND doacao.codDoacao <> :codDoacao ");
			}
			if (doacao.getStatusTermo() != null && doacao.getStatusTermo().getCodStatusTermo() >= 0) {
				sbQuery.append(" AND statusTermo.codStatusTermo = :codStatusTermo ");
			}
			if (doacao.getBemImovel() != null && doacao.getBemImovel().getCodBemImovel() > 0) {
				sbQuery.append(" AND bemImovel.codBemImovel = :codBemImovel ");
			}

			sbQuery.append("ORDER BY bemImovel.codBemImovel ASC");

			Query query = session.createQuery(sbQuery.toString());	

			if (doacao.getCodDoacao() != null && doacao.getCodDoacao() > 0) {
				query.setInteger("codDoacao", doacao.getCodDoacao());
			}
			if (doacao.getStatusTermo() != null && doacao.getStatusTermo().getCodStatusTermo() >= 0) {
				query.setInteger("codStatusTermo", doacao.getStatusTermo().getCodStatusTermo());
			}
			if (doacao.getBemImovel() != null && doacao.getBemImovel().getCodBemImovel() > 0) {
				query.setInteger("codBemImovel", doacao.getBemImovel().getCodBemImovel());
			}

			return (List<Doacao>)query.list();

		}catch (ApplicationException ae) {
			log4j.debug(ae.getMessage(),ae);
			throw ae;
		}catch (Exception e) {
			log4j.error(e.getMessage(),e);
			throw new ApplicationException("ERRO.1", new String[]{DoacaoHibernateDAO.class.getSimpleName()+".listarDoacao()"}, 
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
	public Doacao salvarDoacao(Doacao doacao) throws ApplicationException {
		Doacao retorno = new Doacao();
		// Validando parâmetro
		if (doacao == null){
			throw new ApplicationException("mensagem.erro.9002", new String[]{"DoacaoHibernateDAO.salvarDoacao()"}, ApplicationException.ICON_ERRO);
		}
		
		try {
			log4j.debug("Obtendo sessao corrente...");
			Session session = HibernateUtil.currentSession();
			session.clear();
			Serializable chave = session.save(doacao);
			session.flush();
			log4j.info("SALVAR:" + doacao.getClass().getName() + " salvo.");
			retorno = (Doacao) session.get(Doacao.class, chave);
			log4j.info("GET:" + doacao.getClass().getName() + " q acabou de ser salvo.");
						
		} catch (ConstraintViolationException cve) {
			log4j.error("DoacaoHibernateDAO.salvarDoacao()" + cve);
			throw new ApplicationException("mensagem.erro.9003", new String[]{doacao.getClass().getSimpleName()}, ApplicationException.ICON_AVISO);
		} catch (HibernateException he) {
			log4j.error("DoacaoHibernateDAO.salvarDoacao()" + he);
			throw new ApplicationException("mensagem.erro.9001", new String[]{"DoacaoHibernateDAO.salvarDoacao()"}, he, ApplicationException.ICON_ERRO);
		} catch (Exception e) {
			log4j.error("DoacaoHibernateDAO.salvarDoacao()" + e);
			throw new ApplicationException("mensagem.erro.9001",new String[]{"DoacaoHibernateDAO.salvarDoacao()"}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				log4j.debug("Fechando sessao corrente.");
				HibernateUtil.closeSession();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.9003", new String[]{doacao.getClass().getSimpleName()}, ApplicationException.ICON_AVISO);
			}
		}
		
		return retorno;
	}

	@Override
	public Doacao obterDoacaoCompleto(Integer codDoacao) throws ApplicationException {
		try {
			Session session = HibernateUtil.currentSession();
			StringBuffer hql = new StringBuffer();
			hql.append(" FROM Doacao a ")
			.append(" JOIN FETCH a.bemImovel bemImovel")	
			.append(" JOIN FETCH a.orgaoResponsavel ")
			.append(" JOIN FETCH a.statusTermo ")
			.append(" LEFT JOIN FETCH bemImovel.classificacaoBemImovel ")
			.append(" LEFT JOIN FETCH bemImovel.situacaoLegalCartorial ")
			.append(" LEFT JOIN FETCH bemImovel.situacaoImovel ")
			.append(" LEFT JOIN FETCH a.leiBemImovel ")
			.append(" LEFT JOIN FETCH a.orgaoProprietario ")
			.append(" LEFT JOIN FETCH a.listaAssinaturaDoacao ass")
			.append(" LEFT JOIN FETCH ass.assinatura assinatura")	
			.append(" LEFT JOIN FETCH assinatura.cargoAssinatura")
			.append(" LEFT JOIN FETCH assinatura.orgao")
			.append(" LEFT JOIN FETCH a.listaItemDoacao ")	
			.append(" WHERE a.codDoacao = :codDoacao ");
			
			Query q = session.createQuery(hql.toString());
			q.setInteger("codDoacao", codDoacao );

			return (Doacao) q.uniqueResult();

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

}
