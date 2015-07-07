package gov.pr.celepar.abi.dao.implementation;

import gov.pr.celepar.abi.dao.ConfrontanteDAO;
import gov.pr.celepar.abi.dto.ConfrontanteExibirBemImovelDTO;
import gov.pr.celepar.abi.pojo.Confrontante;
import gov.pr.celepar.framework.database.GenericHibernateDAO;
import gov.pr.celepar.framework.database.HibernateUtil;
import gov.pr.celepar.framework.exception.ApplicationException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.transform.Transformers;

/**
 * Classe de manipulação de objetos da classe Confrontante.
 * 
 * @author CodeGenerator - Esta classe foi gerada automaticamente
 * @since 1.0
 * @version 1.0, 24/12/2009
 *
 */
public class ConfrontanteHibernateDAO extends GenericHibernateDAO<Confrontante, Integer> implements ConfrontanteDAO {

	private static Logger log4j = Logger.getLogger(ConfrontanteHibernateDAO.class);


	@SuppressWarnings("unchecked")
	public Collection<ConfrontanteExibirBemImovelDTO> listarPorBemImovelExibir(Integer codBemImovel) throws ApplicationException {
		Collection<ConfrontanteExibirBemImovelDTO> coll = null;


		try {
			Session session = HibernateUtil.currentSession();
			StringBuffer hql = new StringBuffer();
			hql.append(" SELECT c.descricao as descricao ")
			.append(" FROM Confrontante c ")
			.append(" WHERE c.bemImovel.codBemImovel = :codBemImovel")
			.append(" ORDER BY c.descricao ");
			Query q = session.createQuery(hql.toString());
			q.setInteger("codBemImovel", codBemImovel);
			q.setResultTransformer(Transformers.aliasToBean(ConfrontanteExibirBemImovelDTO.class));
			coll = q.list();				

		} catch (HibernateException he) {
			log4j.error("Erro:", he);
			throw new ApplicationException("ERRO.201",  new String[]{"ao listar Confrontante"});
		} catch (Exception e) {
			log4j.error("Erro:", e);
			throw new ApplicationException("ERRO.201",  new String[]{"ao listar Confrontante"}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				HibernateUtil.closeSession();
			}catch (Exception e) {
				log4j.error("Problema ao tentar fechar conexao com o banco de dados: listarConfrontanteExibir", e);
			}		
		}

		return coll;
	}

	@SuppressWarnings("unchecked")
	public Collection<Confrontante> listarPorBemImovel(Integer bemImovel)
			throws ApplicationException {
		List<Confrontante> confrontantes = new ArrayList<Confrontante>();
		try {
			Session session = HibernateUtil.currentSession();
			Query q = session.createQuery("from Confrontante as confrontante where confrontante.bemImovel.codBemImovel=:codBemImovel ORDER BY descricao");
			q.setInteger("codBemImovel", bemImovel);
			confrontantes = q.list();
			
		} catch (Exception e) {
			throw new ApplicationException("mensagem.erro.genericDAO.listar", new String[]{"confrontantes por bem imóvel"}, e, ApplicationException.ICON_ERRO);		
		} finally {
			try {
				HibernateUtil.closeSession();
			}catch (Exception e) {
				log4j.error("Problema ao tentar fechar conexao com o banco de dados: listarPorBemImovel", e);
			}
		}
		return confrontantes;
	}

	@Override
	public Confrontante salvarConfrontante(Confrontante confrontante) throws ApplicationException {
		Confrontante retorno = new Confrontante();
		// Validando parâmetro
		if (confrontante == null){
			throw new ApplicationException("mensagem.erro.9002", new String[]{"ConfrontanteHibernateDAO.salvarConfrontante()"}, ApplicationException.ICON_ERRO);
		}
		
		try {
			log4j.debug("Obtendo sessao corrente...");
			Session session = HibernateUtil.currentSession();
			session.clear();
			Serializable chave = session.save(confrontante);
			session.flush();
			log4j.info("SALVAR:" + confrontante.getClass().getName() + " salvo.");
			retorno = (Confrontante) session.get(Confrontante.class, chave);
			log4j.info("GET:" + confrontante.getClass().getName() + " q acabou de ser salvo.");
						
		} catch (ConstraintViolationException cve) {
			log4j.error("ConfrontanteHibernateDAO.salvarConfrontante()" + cve);
			throw new ApplicationException("mensagem.erro.9003", new String[]{confrontante.getClass().getSimpleName()}, ApplicationException.ICON_AVISO);
		} catch (HibernateException he) {
			log4j.error("ConfrontanteHibernateDAO.salvarConfrontante()" + he);
			throw new ApplicationException("mensagem.erro.9001", new String[]{"ConfrontanteHibernateDAO.salvarConfrontante()"}, he, ApplicationException.ICON_ERRO);
		} catch (Exception e) {
			log4j.error("ConfrontanteHibernateDAO.salvarConfrontante()" + e);
			throw new ApplicationException("mensagem.erro.9001",new String[]{"ConfrontanteHibernateDAO.salvarConfrontante()"}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				log4j.debug("Fechando sessao corrente.");
				HibernateUtil.closeSession();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.9003", new String[]{confrontante.getClass().getSimpleName()}, ApplicationException.ICON_AVISO);
			}
		}
		
		return retorno;
	}	
	 

}
