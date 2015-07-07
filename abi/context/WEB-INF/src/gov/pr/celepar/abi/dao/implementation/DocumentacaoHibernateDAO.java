package gov.pr.celepar.abi.dao.implementation;

import gov.pr.celepar.abi.dao.DocumentacaoDAO;
import gov.pr.celepar.abi.dto.DocumentacaoNotificacaoExibirBemImovelDTO;
import gov.pr.celepar.abi.dto.DocumentacaoSemNotificacaoExibirBemImovelDTO;
import gov.pr.celepar.abi.pojo.Documentacao;
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
 * Classe de manipulacao de objetos da classe Documentacao.
 * 
 * @author CodeGenerator - Esta classe foi gerada automaticamente
 * @since 1.0
 * @version 1.0, 24/12/2009 
 *
 */
public class DocumentacaoHibernateDAO extends GenericHibernateDAO<Documentacao, Integer> implements DocumentacaoDAO {

	private static Logger log4j = Logger.getLogger(DocumentacaoHibernateDAO.class);
	
	/**Retornar lista de documentacao do bem imovel que nao possui realação com a tabela de notificações
	 * @param codBemImovel
	 * @return Collection
	 * @throws ApplicationException 
	 */
	@SuppressWarnings("unchecked")
	public List<Documentacao> listarDocumentacoesSemNotificacao(Integer codImovel) throws ApplicationException {  
		List<Documentacao> documentacoes = new ArrayList<Documentacao>();
		try {
			Session session = HibernateUtil.currentSession();
			Query q = session.createQuery("from Documentacao as doc where doc.notificacaos is empty and doc.bemImovel.codBemImovel=:codImovel order by doc.descricao");
			q.setInteger("codImovel", codImovel);
			
		
			documentacoes = q.list();
		} catch (Exception e) {
			throw new ApplicationException("mensagem.erro.genericDAO.listar", new String[]{"documentações sem notificação"}, e, ApplicationException.ICON_ERRO);		
		} finally {
			try {
				HibernateUtil.closeSession();
			}catch (Exception e) {
				log4j.error("Problema ao tentar fechar conexao com o banco de dados: listarDocumentacoesSemNotificacao", e);
			}
		}
		return documentacoes;
	}  
	
	/**Retornar lista de documentacao do bem imovel que nao possui realação com a tabela de ocorrencia documentação
	 * em 24/01 a pedido do Douglas, deve retornar mesmo as documentações que possuem ocorrencia.
	 * @param codBemImovel
	 * @return Collection
	 * @throws ApplicationException 
	 */
	@SuppressWarnings("unchecked")
	public List<Documentacao> listarDocumentacaoSemOcorrencia( Integer codBemImovel, Integer qtdPagina, Integer numPagina, boolean listarAnexos) throws ApplicationException {  
		List<Documentacao> documentacoes = new ArrayList<Documentacao>();
		try {
			Session session = HibernateUtil.currentSession();
			StringBuffer hql = new StringBuffer();
			hql.append("from Documentacao as doc ")
			.append("left join fetch doc.notificacaos as notificacao ")
			.append("left join fetch doc.tipoDocumentacao ")
			.append("left join fetch doc.cartorio ")
			.append("left join fetch doc.tabelionato ")
			.append("left join fetch doc.bemImovel ")
			.append("where doc.bemImovel.codBemImovel= :codBemImovel ");
			if (listarAnexos) {
				hql.append(" and doc.anexo is not NULL ");
			} else {
				hql.append(" and doc.anexo is NULL ");
			}
			hql.append(" order by doc.tsInclusao");

			Query q = session.createQuery(hql.toString());

			q.setInteger("codBemImovel", codBemImovel);
			
			//tipoDocumentacao
			if (qtdPagina != null && numPagina != null) {
				q.setMaxResults(qtdPagina.intValue());
				q.setFirstResult( ((numPagina.intValue()-1) * qtdPagina.intValue()));
			}
			documentacoes = q.list();
		} catch (Exception e) {
			throw new ApplicationException("mensagem.erro.genericDAO.listar", new String[]{"documentações sem ocorrência documentação"}, e, ApplicationException.ICON_ERRO);		
		} finally {
			try {
				HibernateUtil.closeSession();
			}catch (Exception e) {
				log4j.error("Problema ao tentar fechar conexao com o banco de dados: listarDocumentacoesSemOcorrencia", e);
			}
		}
		return documentacoes;
	}  
	
	/** Retornar quantidade de registros da lista de documentacao do bem imovel que nao possui realação com a tabela de ocorrencia documentação
	 * 
	 * @param codBemImovel
	 * @return Long
	 * @throws ApplicationException
	 */
	public Long buscarQtdListaDocumentacaoSemOcorrencia( Integer codBemImovel,  boolean listarAnexos) throws ApplicationException {  
		Long qtd= null;
		try {
			Session session = HibernateUtil.currentSession();
			StringBuffer hql = new StringBuffer();
			hql.append("select count(*) from Documentacao as doc " )
			.append("where doc.ocorrenciaDocumentacaos is empty ")
			.append("and doc.bemImovel.codBemImovel=:codBemImovel");
			if (listarAnexos) {
				hql.append(" and doc.anexo is not NULL ");
			} else {
				hql.append(" and doc.anexo is NULL ");
			}

			Query q = session.createQuery(hql.toString());
			q.setInteger("codBemImovel", codBemImovel);
			qtd = (Long)q.uniqueResult();
		} catch (Exception e) {
			throw new ApplicationException("mensagem.erro.genericDAO.listar", new String[]{"documentações sem ocorrência documentação"}, e, ApplicationException.ICON_ERRO);		
		} finally {
			try {
				HibernateUtil.closeSession();
			}catch (Exception e) {
				log4j.error("Problema ao tentar fechar conexao com o banco de dados: listarDocumentacoesSemOcorrencia", e);
			}
		}
		return qtd;
	}  
	
	/**
	 * Obter Documentacao e objetos filhos inicializados.<br>
	
	 * @param Integer codDocumentacao
	 * @return Documentacao
	 * @throws ApplicationException
	 */
	public Documentacao obterComRelacionamentos(Integer codDocumentacao) throws ApplicationException, Exception {
		try {
			Session session = HibernateUtil.currentSession();
			Criteria c = session.createCriteria(Documentacao.class)
						.add(Restrictions.eq("codDocumentacao", codDocumentacao))
						.setFetchMode("tipoDocumentacao", FetchMode.JOIN)
						.setFetchMode("edificacao", FetchMode.JOIN)
						.setFetchMode("documentacao", FetchMode.JOIN)
						.setFetchMode("documentacaos", FetchMode.JOIN)
						.setFetchMode("notificacaos", FetchMode.JOIN);
			return (Documentacao) c.uniqueResult();
		} catch (HibernateException he) {
			throw new ApplicationException("mensagem.erro", new String[]{"obter Documentação com Relacionamentos"}, he);
		} catch (Exception e) {
			throw new ApplicationException("mensagem.erro", new String[]{"obter Documentação com Relacionamentos"}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (Exception e) {
				log4j.error("Erro ao Fechar Conexao com o Hibernate: ", e);				
			}
		}
	}


	@SuppressWarnings("unchecked")
	public Collection<DocumentacaoNotificacaoExibirBemImovelDTO> listarDocumentacaoNotificacaoPorBemImovelExibir(Integer codBemImovel) throws ApplicationException {
		Collection<DocumentacaoNotificacaoExibirBemImovelDTO> coll = null;


		try {
			Session session = HibernateUtil.currentSession();
			StringBuffer hql = new StringBuffer();
			hql.append(" SELECT d.codDocumentacao as codDocumentacao, d.anexo as anexo, " )
			.append(" d.descricao as descricao, d.tsInclusao as tsInclusao, ")
			.append(" n.tsInclusao as tsInclusao, n.dataNotificacao as dataNotificacao, ")
			.append(" n.prazoNotificacao as prazoNotificacao, n.dataNotificacao as dataNotificacao, ")
			.append(" n.dataSolucao as dataSolucao, d.responsavelDocumentacao as responsavelDocumentacao ,")
			.append(" d.tipoDocumentacao.descricao as tipoDocumentacao, d.cartorio.descricao as cartorio, d.tabelionato.descricao as tabelionato, ")
			.append(" d.numeroDocumentoCartorial as numDocCartorial, d.numeroDocumentoTabelional as numDocTabelional, ")
			.append(" d.nirf as nirf, d.niif as niif, d.incra as incra, d.bemImovel.classificacaoBemImovel.codClassificacaoBemImovel as classificacaoBemImovel ")
			.append(" FROM Documentacao d left join d.cartorio left join d.tabelionato left join d.tipoDocumentacao")
			.append(" INNER JOIN d.notificacaos n ")
			.append(" WHERE d.bemImovel.codBemImovel = :codBemImovel and d.ocorrenciaDocumentacaos is empty")
			.append(" ORDER BY d.tsInclusao ");

			Query q = session.createQuery(hql.toString());
			q.setInteger("codBemImovel", codBemImovel);
			q.setResultTransformer(Transformers.aliasToBean(DocumentacaoNotificacaoExibirBemImovelDTO.class));
			coll = q.list();				

		} catch (HibernateException he) {
			log4j.error("Erro:", he);
			throw new ApplicationException("ERRO.201",  new String[]{"ao listar Documentação com Notificação"});
		} catch (Exception e) {
			log4j.error("Erro:", e);
			throw new ApplicationException("ERRO.201",  new String[]{"ao listar Documentação com Notificação"}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				HibernateUtil.closeSession();
			}catch (Exception e) {
				log4j.error("Problema ao tentar fechar conexao com o banco de dados: listarDocumentacaoNotificacao", e);
			}		
		}

		return coll;
	}


	@SuppressWarnings("unchecked")
	public Collection<DocumentacaoSemNotificacaoExibirBemImovelDTO> listarDocumentacaoSemNotificacaoPorBemImovelExibir(Integer codBemImovel) throws ApplicationException {
		Collection<DocumentacaoSemNotificacaoExibirBemImovelDTO> coll = null;


		try {
			Session session = HibernateUtil.currentSession();
			StringBuffer hql = new StringBuffer();
			hql.append(" SELECT d.codDocumentacao as codDocumentacao, d.anexo as anexo, " )
			.append(" d.descricao as descricao, d.tsInclusao as tsInclusao,")
			.append(" d.tsAtualizacao as tsAtualizacao, d.responsavelDocumentacao as responsavelDocumentacao, ")
			.append(" d.tipoDocumentacao.descricao as tipoDocumentacao, d.cartorio.descricao as cartorio, d.tabelionato.descricao as tabelionato, ")
			.append(" d.numeroDocumentoCartorial as numDocCartorial, d.numeroDocumentoTabelional as numDocTabelional, ")
			.append(" d.nirf as nirf, d.niif as niif, d.incra as incra, d.bemImovel.classificacaoBemImovel.codClassificacaoBemImovel as classificacaoBemImovel ")
			.append(" FROM Documentacao d left join d.cartorio left join d.tabelionato left join d.tipoDocumentacao ")
			.append(" WHERE d.bemImovel.codBemImovel = :codBemImovel and d.notificacaos is empty and d.ocorrenciaDocumentacaos is empty");

			Query q = session.createQuery(hql.toString());
			q.setInteger("codBemImovel", codBemImovel);
			q.setResultTransformer(Transformers.aliasToBean(DocumentacaoSemNotificacaoExibirBemImovelDTO.class));
			coll = q.list();				

		} catch (HibernateException he) {
			log4j.error("Erro:", he);
			throw new ApplicationException("ERRO.201",  new String[]{"ao listar Documentação"});
		} catch (Exception e) {
			log4j.error("Erro:", e);
			throw new ApplicationException("ERRO.201",  new String[]{"ao listar Documentação"}, e, ApplicationException.ICON_ERRO);
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
	public Documentacao salvarDocumentacao(Documentacao documentacao) throws ApplicationException {
		Documentacao retorno = new Documentacao();
		// Validando parâmetro
		if (documentacao == null){
			throw new ApplicationException("mensagem.erro.9002", new String[]{"DocumentacaoHibernateDAO.salvarDocumentacao()"}, ApplicationException.ICON_ERRO);
		}
		
		try {
			log4j.debug("Obtendo sessao corrente...");
			Session session = HibernateUtil.currentSession();
			session.clear();
			Serializable chave = session.save(documentacao);
			session.flush();
			log4j.info("SALVAR:" + documentacao.getClass().getName() + " salvo.");
			retorno = (Documentacao) session.get(Documentacao.class, chave);
			log4j.info("GET:" + documentacao.getClass().getName() + " q acabou de ser salvo.");
						
		} catch (ConstraintViolationException cve) {
			log4j.error("DocumentacaoHibernateDAO.salvarDocumentacao()" + cve);
			throw new ApplicationException("mensagem.erro.9003", new String[]{documentacao.getClass().getSimpleName()}, ApplicationException.ICON_AVISO);
		} catch (HibernateException he) {
			log4j.error("DocumentacaoHibernateDAO.salvarDocumentacao()" + he);
			throw new ApplicationException("mensagem.erro.9001", new String[]{"DocumentacaoHibernateDAO.salvarDocumentacao()"}, he, ApplicationException.ICON_ERRO);
		} catch (Exception e) {
			log4j.error("DocumentacaoHibernateDAO.salvarDocumentacao()" + e);
			throw new ApplicationException("mensagem.erro.9001",new String[]{"DocumentacaoHibernateDAO.salvarDocumentacao()"}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				log4j.debug("Fechando sessao corrente.");
				HibernateUtil.closeSession();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.9003", new String[]{documentacao.getClass().getSimpleName()}, ApplicationException.ICON_AVISO);
			}
		}
		
		return retorno;
	}	
	
	

}
