package gov.pr.celepar.abi.dao.implementation;

import gov.pr.celepar.abi.dao.AvaliacaoDAO;
import gov.pr.celepar.abi.dto.AvaliacaoExibirBemImovelDTO;
import gov.pr.celepar.abi.pojo.Avaliacao;
import gov.pr.celepar.framework.database.GenericHibernateDAO;
import gov.pr.celepar.framework.database.HibernateUtil;
import gov.pr.celepar.framework.exception.ApplicationException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.transform.Transformers;

/**
 * Classe de manipulação de objetos da classe Avaliacao.
 * 
 * @author CodeGenerator - Esta classe foi gerada automaticamente
 * @since 1.0
 * @version 1.0, 24/12/2009 
 *
 */
public class AvaliacaoHibernateDAO extends GenericHibernateDAO<Avaliacao, Integer> implements AvaliacaoDAO {

	private static Logger log4j = Logger.getLogger(AvaliacaoHibernateDAO.class);
	
	@SuppressWarnings("unchecked")
	public Collection<Avaliacao> listarComRelacionamentos(Integer codBemImovel, Integer qtdPagina, Integer numPagina) throws ApplicationException {
		Collection<Avaliacao> coll = new ArrayList<Avaliacao>();

		try {
			Session session = HibernateUtil.currentSession();
			

			Query q = session.createQuery("from Avaliacao av left join fetch av.bemImovel left join fetch av.edificacao where av.bemImovel.codBemImovel=:codBemImovel order by av.dataAvaliacao");
			q.setInteger("codBemImovel", codBemImovel);
			if (qtdPagina != null && numPagina != null) {
				q.setMaxResults(qtdPagina.intValue());
				q.setFirstResult( ((numPagina.intValue()-1) * qtdPagina.intValue()));
			}
			coll = q.list();	


		} catch (HibernateException he) {
			throw new ApplicationException("ERRO.201",  new String[]{"ao listar Avaliação"});
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201",  new String[]{"ao listar Avaliação"}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				HibernateUtil.closeSession();
			}catch (Exception e) {
				log4j.error("Problema ao tentar fechar conexao com o banco de dados: listarAvaliacoesComRelacionamentos", e);
			}		
		}
		
		return coll;
	}



	@SuppressWarnings("unchecked")
	public Collection<AvaliacaoExibirBemImovelDTO> listarPorBemImovelExibir(Integer codBemImovel) throws ApplicationException {
		Collection<AvaliacaoExibirBemImovelDTO> coll = null;


		try {
			Session session = HibernateUtil.currentSession();
			StringBuffer hql = new StringBuffer();
			hql.append(" SELECT a.codAvaliacao as codAvaliacao, a.dataAvaliacao as dataAvaliacao, a.valor as valor, a.indTipoAvaliacao as indTipoAvaliacao")
			.append(" FROM Avaliacao a ")
			.append(" WHERE a.bemImovel.codBemImovel = :codBemImovel")
			.append(" ORDER BY a.dataAvaliacao ");
			Query q = session.createQuery(hql.toString());
			q.setInteger("codBemImovel", codBemImovel);
			q.setResultTransformer(Transformers.aliasToBean(AvaliacaoExibirBemImovelDTO.class));
			coll = q.list();				

		} catch (HibernateException he) {
			log4j.error("Erro:", he);
			throw new ApplicationException("ERRO.201",  new String[]{"ao listar Avaliação"});
		} catch (Exception e) {
			log4j.error("Erro:", e);
			throw new ApplicationException("ERRO.201",  new String[]{"ao listar Avaliação"}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				HibernateUtil.closeSession();
			}catch (Exception e) {
				log4j.error("Problema ao tentar fechar conexao com o banco de dados: listarConfrontanteExibir", e);
			}		
		}

		return coll;
	}

	@Override
	public Avaliacao salvarAvaliacao(Avaliacao avaliacao) throws ApplicationException {
		Avaliacao retorno = new Avaliacao();
		// Validando parâmetro
		if (avaliacao == null){
			throw new ApplicationException("mensagem.erro.9002", new String[]{"AvaliacaoHibernateDAO.salvarAvaliacao()"}, ApplicationException.ICON_ERRO);
		}
		
		try {
			log4j.debug("Obtendo sessao corrente...");
			Session session = HibernateUtil.currentSession();
			session.clear();
			Serializable chave = session.save(avaliacao);
			session.flush();
			log4j.info("SALVAR:" + avaliacao.getClass().getName() + " salvo.");
			retorno = (Avaliacao) session.get(Avaliacao.class, chave);
			log4j.info("GET:" + avaliacao.getClass().getName() + " q acabou de ser salvo.");
						
		} catch (ConstraintViolationException cve) {
			log4j.error("AvaliacaoHibernateDAO.salvarAvaliacao()" + cve);
			throw new ApplicationException("mensagem.erro.9003", new String[]{avaliacao.getClass().getSimpleName()}, ApplicationException.ICON_AVISO);
		} catch (HibernateException he) {
			log4j.error("AvaliacaoHibernateDAO.salvarAvaliacao()" + he);
			throw new ApplicationException("mensagem.erro.9001", new String[]{"AvaliacaoHibernateDAO.salvarAvaliacao()"}, he, ApplicationException.ICON_ERRO);
		} catch (Exception e) {
			log4j.error("AvaliacaoHibernateDAO.salvarAvaliacao()" + e);
			throw new ApplicationException("mensagem.erro.9001",new String[]{"AvaliacaoHibernateDAO.salvarAvaliacao()"}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				log4j.debug("Fechando sessao corrente.");
				HibernateUtil.closeSession();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.9003", new String[]{avaliacao.getClass().getSimpleName()}, ApplicationException.ICON_AVISO);
			}
		}
		
		return retorno;
	}	

}
