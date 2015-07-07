package gov.pr.celepar.abi.dao.implementation;

import gov.pr.celepar.abi.dao.OcorrenciaDocumentacaoDAO;
import gov.pr.celepar.abi.dto.OcorrenciaDocumentacaoExibirBemImovelDTO;
import gov.pr.celepar.abi.pojo.OcorrenciaDocumentacao;
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
 * Classe de manipulação de objetos da classe OcorrenciaDocumentacao.
 * 
 * @author CodeGenerator - Esta classe foi gerada automaticamente
 * @since 1.0
 * @version 1.0, 24/12/2009 
 *
 */
public class OcorrenciaDocumentacaoHibernateDAO extends GenericHibernateDAO<OcorrenciaDocumentacao, Integer> implements OcorrenciaDocumentacaoDAO {
	private static Logger log4j = Logger.getLogger(OcorrenciaDocumentacaoHibernateDAO.class);
	
	@SuppressWarnings("unchecked")
	public Collection<OcorrenciaDocumentacao> listarComRelacionamentos(Integer codBemImovel, Integer qtdPagina, Integer numPagina) throws ApplicationException {
		Collection<OcorrenciaDocumentacao> coll = new ArrayList<OcorrenciaDocumentacao>();		

		try {
			Session session = HibernateUtil.currentSession();

			Query q = session.createQuery("from OcorrenciaDocumentacao o  left join fetch o.documentacao where o.bemImovel.codBemImovel=:codBemImovel order by o.tsInclusao");
			q.setInteger("codBemImovel", codBemImovel);
			if (qtdPagina != null && numPagina != null) {
				q.setMaxResults(qtdPagina.intValue());
				q.setFirstResult( ((numPagina.intValue()-1) * qtdPagina.intValue()));
			}
			coll = q.list();	


		} catch (HibernateException he) {
			throw new ApplicationException("ERRO.201",  new String[]{"ao listar Ocorrência da Documentação"});
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201",  new String[]{"ao listar Ocorrência da Documentação"}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				HibernateUtil.closeSession();
			}catch (Exception e) {
				log4j.error("Problema ao tentar fechar conexao com o banco de dados: listarOcorrenciaDocumentacaoComRelacionamentos", e);
			}		
		}
		
		return coll;
	}
	

	@SuppressWarnings("unchecked")								
	public Collection<OcorrenciaDocumentacaoExibirBemImovelDTO> listarOcorrenciaDocumentacaoPorBemImovelExibir(Integer codBemImovel) throws ApplicationException {
		Collection<OcorrenciaDocumentacaoExibirBemImovelDTO> coll = null;


		try {
			Session session = HibernateUtil.currentSession();
			StringBuffer hql = new StringBuffer();
			hql.append(" SELECT o.codOcorrenciaDocumentacao as codOcorrenciaDocumentacao, o.documentacao.anexo as anexo, " )
			.append(" o.descricao as descricao, o.tsInclusao as tsInclusao, d.codDocumentacao as codDocumentacao, ")
			.append(" d.tipoDocumentacao.descricao as tipoDocumentacao, d.cartorio.descricao as cartorio, d.tabelionato.descricao as tabelionato, d.descricao as descricaoDocumentacao,")
			.append(" d.numeroDocumentoCartorial as numDocCartorial, d.numeroDocumentoTabelional as numDocTabelional, ")
			.append(" d.nirf as nirf, d.niif as niif, d.incra as incra, d.bemImovel.classificacaoBemImovel.codClassificacaoBemImovel as classificacaoBemImovel ")
			//.append(" o.nomeResponsavel as nomeResponsavel ")
			.append(" FROM OcorrenciaDocumentacao o ")
			.append(" LEFT JOIN o.documentacao as d LEFT JOIN d.cartorio LEFT JOIN d.tabelionato LEFT JOIN d.tipoDocumentacao LEFT JOIN d.bemImovel left join d.tipoDocumentacao")			
			.append(" WHERE o.bemImovel.codBemImovel = :codBemImovel")
			.append(" ORDER BY o.tsInclusao ");

			Query q = session.createQuery(hql.toString());
			q.setInteger("codBemImovel", codBemImovel);
			q.setResultTransformer(Transformers.aliasToBean(OcorrenciaDocumentacaoExibirBemImovelDTO.class));
			coll = q.list();				

		} catch (HibernateException he) {
			log4j.error("Erro:", he);
			throw new ApplicationException("ERRO.201",  new String[]{"ao listar Ocorrência da Documentação por Bem Imóvel"});
		} catch (Exception e) {
			log4j.error("Erro:", e);
			throw new ApplicationException("ERRO.201",  new String[]{"ao listar Ocorrência da Documentação por Bem Imóvel"}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				HibernateUtil.closeSession();
			}catch (Exception e) {
				log4j.error("Problema ao tentar fechar conexao com o banco de dados: listarOcupacao", e);
			}		
		}

		return coll;
	}


	@Override
	public OcorrenciaDocumentacao salvarOcorrenciaDocumentacao(OcorrenciaDocumentacao ocorrenciaDocumentacao) throws ApplicationException {
		OcorrenciaDocumentacao retorno = new OcorrenciaDocumentacao();
		// Validando parâmetro
		if (ocorrenciaDocumentacao == null){
			throw new ApplicationException("mensagem.erro.9002", new String[]{"OcorrenciaDocumentacaoHibernateDAO.salvarOcorrenciaDocumentacao()"}, ApplicationException.ICON_ERRO);
		}
		
		try {
			log4j.debug("Obtendo sessao corrente...");
			Session session = HibernateUtil.currentSession();
			session.clear();
			Serializable chave = session.save(ocorrenciaDocumentacao);
			session.flush();
			log4j.info("SALVAR:" + ocorrenciaDocumentacao.getClass().getName() + " salvo.");
			retorno = (OcorrenciaDocumentacao) session.get(OcorrenciaDocumentacao.class, chave);
			log4j.info("GET:" + ocorrenciaDocumentacao.getClass().getName() + " q acabou de ser salvo.");
						
		} catch (ConstraintViolationException cve) {
			log4j.error("OcorrenciaDocumentacaoHibernateDAO.salvarOcorrenciaDocumentacao()" + cve);
			throw new ApplicationException("mensagem.erro.9003", new String[]{ocorrenciaDocumentacao.getClass().getSimpleName()}, ApplicationException.ICON_AVISO);
		} catch (HibernateException he) {
			log4j.error("OcorrenciaDocumentacaoHibernateDAO.salvarOcorrenciaDocumentacao()" + he);
			throw new ApplicationException("mensagem.erro.9001", new String[]{"OcorrenciaDocumentacaoHibernateDAO.salvarOcorrenciaDocumentacao()"}, he, ApplicationException.ICON_ERRO);
		} catch (Exception e) {
			log4j.error("OcorrenciaDocumentacaoHibernateDAO.salvarOcorrenciaDocumentacao()" + e);
			throw new ApplicationException("mensagem.erro.9001",new String[]{"OcorrenciaDocumentacaoHibernateDAO.salvarOcorrenciaDocumentacao()"}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				log4j.debug("Fechando sessao corrente.");
				HibernateUtil.closeSession();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.9003", new String[]{ocorrenciaDocumentacao.getClass().getSimpleName()}, ApplicationException.ICON_AVISO);
			}
		}
		
		return retorno;
	}


}
