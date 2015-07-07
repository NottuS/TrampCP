package gov.pr.celepar.abi.dao.implementation;

import gov.pr.celepar.abi.dao.LoteDAO;
import gov.pr.celepar.abi.dto.LoteExibirBemImovelDTO;
import gov.pr.celepar.abi.pojo.Lote;
import gov.pr.celepar.framework.database.GenericHibernateDAO;
import gov.pr.celepar.framework.database.HibernateUtil;
import gov.pr.celepar.framework.exception.ApplicationException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.transform.Transformers;

/**
 * Classe de manipulação de objetos da classe Lote.
 * 
 * @author CodeGenerator - Esta classe foi gerada automaticamente
 * @since 1.0
 * @version 1.0, 24/12/2009
 *
 */
public class LoteHibernateDAO extends GenericHibernateDAO<Lote, Integer> implements LoteDAO {
	private static Logger log4j = Logger.getLogger(LoteHibernateDAO.class);
	
	@SuppressWarnings("unchecked")
	public Collection<Lote> listarPorBemImovel(Integer bemImovel)
			throws ApplicationException {
		List<Lote> lotes = new ArrayList<Lote>();
		try {
			Session session = HibernateUtil.currentSession();
			Query q = session.createQuery("from Lote as lote left join fetch lote.quadra where lote.quadra.bemImovel.codBemImovel=:codBemImovel");
			q.setInteger("codBemImovel", bemImovel);
			lotes = q.list();
			

		} catch (Exception e) {
			throw new ApplicationException("mensagem.erro.genericDAO.listar", new String[]{"lotes por bem imóvel"}, e, ApplicationException.ICON_ERRO);		
		} finally {
			try {
				HibernateUtil.closeSession();
			}catch (Exception e) {
				log4j.error("Problema ao tentar fechar conexao com o banco de dados: listarPorBemImovel", e);
			}
		}
		return lotes;
	}

	
	public Lote obterComRelacionamento(Integer codLote)
			throws ApplicationException {
		Lote lote = new Lote();
		try {
			Session session = HibernateUtil.currentSession();
			Criteria c = session.createCriteria(Lote.class)
			.add(Restrictions.eq("codLote", codLote))
			.setFetchMode("quadra", FetchMode.JOIN);
			lote = (Lote)c.uniqueResult();
			
		} catch (Exception e) {
			throw new ApplicationException("mensagem.erro.genericDAO.listar", new String[]{"lote por bem imóvel"}, e, ApplicationException.ICON_ERRO);		
		} finally {
			try {
				HibernateUtil.closeSession();
			}catch (Exception e) {
				log4j.error("Problema ao tentar fechar conexao com o banco de dados: obterComRelacionamentoPorBemImovel", e);
			}
		}
		return lote;
	}


	@SuppressWarnings("unchecked")
	public Collection<Lote> listarComRelacionamentos(Integer codBemImovel) throws ApplicationException {
		Collection<Lote> coll = new ArrayList<Lote>();
		

		try {
			Session session = HibernateUtil.currentSession();

			Query q = session.createQuery("from Lote as lote left join fetch lote.quadra where lote.quadra.bemImovel.codBemImovel=:codBemImovel ORDER BY lote.quadra.descricao, lote.descricao" );
			q.setInteger("codBemImovel", codBemImovel);
			coll = q.list();	

		} catch (HibernateException he) {
			throw new ApplicationException("ERRO.201",  new String[]{"ao listar Lote"});
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201",  new String[]{"ao listar Lote"}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				HibernateUtil.closeSession();
			}catch (Exception e) {
				log4j.error("Problema ao tentar fechar conexao com o banco de dados: listarEdificacaoComRelacionamentos", e);
			}		
		}
		
		return coll;
	}
	
	@SuppressWarnings("unchecked")
	public Collection<LoteExibirBemImovelDTO> listarPorBemImovelExibir(Integer codBemImovel) throws ApplicationException {
		Collection<LoteExibirBemImovelDTO> coll = null;
	
	
		try {
			Session session = HibernateUtil.currentSession();
			StringBuffer hql = new StringBuffer();
			hql.append(" SELECT l.descricao as lote, l.quadra.descricao as quadra ")
			.append(" FROM Lote l ")
			.append(" WHERE l.quadra.bemImovel.codBemImovel = :codBemImovel")
			.append(" ORDER BY l.quadra.descricao, l.descricao ");
	
			Query q = session.createQuery(hql.toString());
			q.setInteger("codBemImovel", codBemImovel);
			q.setResultTransformer(Transformers.aliasToBean(LoteExibirBemImovelDTO.class));
			coll = q.list();				
	
		} catch (HibernateException he) {
			log4j.error("Erro:", he);
			throw new ApplicationException("ERRO.201",  new String[]{"ao listar Lote por Bem Imóvel"});
		} catch (Exception e) {
			log4j.error("Erro:", e);
			throw new ApplicationException("ERRO.201",  new String[]{"ao listar Lote por Bem Imóvel"}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				HibernateUtil.closeSession();
			}catch (Exception e) {
				log4j.error("Problema ao tentar fechar conexao com o banco de dados: listarLote", e);
			}		
		}
	
		return coll;
	}
	
	@SuppressWarnings("unchecked")
	public Collection<Lote> listarPorQuadra(Integer quadra)
			throws ApplicationException {
		List<Lote> lotes = new ArrayList<Lote>();
		try {
			Session session = HibernateUtil.currentSession();
			Query q = session.createQuery("from Lote as lote left join fetch lote.quadra where lote.quadra.codQuadra=:codQuadra");
			q.setInteger("codQuadra", quadra);
			lotes = q.list();
			
			
		} catch (Exception e) {
			throw new ApplicationException("mensagem.erro.genericDAO.listar", new String[]{"lotes por quadra"}, e, ApplicationException.ICON_ERRO);		
		} finally {
			try {
				HibernateUtil.closeSession();
			}catch (Exception e) {
				log4j.error("Problema ao tentar fechar conexao com o banco de dados: listarPorQuadra", e);
			}
		}
		return lotes;
	}


	@Override
	public Lote salvarLote(Lote lote) throws ApplicationException {
		Lote retorno = new Lote();
		// Validando parâmetro
		if (lote == null){
			throw new ApplicationException("mensagem.erro.9002", new String[]{"LoteHibernateDAO.salvarLote()"}, ApplicationException.ICON_ERRO);
		}
		
		try {
			log4j.debug("Obtendo sessao corrente...");
			Session session = HibernateUtil.currentSession();
			session.clear();
			Serializable chave = session.save(lote);
			session.flush();
			log4j.info("SALVAR:" + lote.getClass().getName() + " salvo.");
			retorno = (Lote) session.get(Lote.class, chave);
			log4j.info("GET:" + lote.getClass().getName() + " q acabou de ser salvo.");
						
		} catch (ConstraintViolationException cve) {
			log4j.error("LoteHibernateDAO.salvarLote()" + cve);
			throw new ApplicationException("mensagem.erro.9003", new String[]{lote.getClass().getSimpleName()}, ApplicationException.ICON_AVISO);
		} catch (HibernateException he) {
			log4j.error("LoteHibernateDAO.salvarLote()" + he);
			throw new ApplicationException("mensagem.erro.9001", new String[]{"LoteHibernateDAO.salvarLote()"}, he, ApplicationException.ICON_ERRO);
		} catch (Exception e) {
			log4j.error("LoteHibernateDAO.salvarLote()" + e);
			throw new ApplicationException("mensagem.erro.9001",new String[]{"LoteHibernateDAO.salvarLote()"}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				log4j.debug("Fechando sessao corrente.");
				HibernateUtil.closeSession();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.9003", new String[]{lote.getClass().getSimpleName()}, ApplicationException.ICON_AVISO);
			}
		}
		
		return retorno;
	}
	
}
