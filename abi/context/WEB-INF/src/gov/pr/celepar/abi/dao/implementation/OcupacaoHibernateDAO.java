package gov.pr.celepar.abi.dao.implementation;

import gov.pr.celepar.abi.dao.OcupacaoDAO;
import gov.pr.celepar.abi.dto.FiltroRelatorioEdificacaoOcupacaoDTO;
import gov.pr.celepar.abi.dto.OcupacaoExibirBemImovelDTO;
import gov.pr.celepar.abi.dto.OcupacaoListaDTO;
import gov.pr.celepar.abi.dto.OcupacaoOrgaoResponsavelListaDTO;
import gov.pr.celepar.abi.dto.RelatorioEdificacaoOcupacaoDTO;
import gov.pr.celepar.abi.pojo.BemImovel;
import gov.pr.celepar.abi.pojo.Ocupacao;
import gov.pr.celepar.abi.pojo.Orgao;
import gov.pr.celepar.abi.util.Dominios;
import gov.pr.celepar.abi.util.Dominios.statusTermo;
import gov.pr.celepar.framework.database.GenericHibernateDAO;
import gov.pr.celepar.framework.database.HibernateUtil;
import gov.pr.celepar.framework.exception.ApplicationException;
import gov.pr.celepar.framework.util.StringUtil;
import gov.pr.celepar.framework.util.Valores;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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
 * Classe de manipulacao de objetos da classe Ocupacao.
 * 
 * @author CodeGenerator - Esta classe foi gerada automaticamente
 * @since 1.0
 * @version 1.0, 24/12/2009
 *
 */
public class OcupacaoHibernateDAO extends GenericHibernateDAO<Ocupacao, Integer> implements OcupacaoDAO {
	private static Logger log4j = Logger.getLogger(OcupacaoHibernateDAO.class);

	@SuppressWarnings("unchecked")
	public Collection<OcupacaoExibirBemImovelDTO> listarPorBemImovelExibir(Integer codEdificacao) throws ApplicationException {

		Collection<OcupacaoExibirBemImovelDTO> coll = new ArrayList<OcupacaoExibirBemImovelDTO>();
		Collection<Ocupacao> lista = null;

		try {
			Session session = HibernateUtil.currentSession();
			StringBuffer hql = new StringBuffer();
			hql.append(" FROM Ocupacao o ")
			.append(" INNER JOIN FETCH o.orgao ")
			.append(" WHERE o.edificacao.codEdificacao = :codEdificacao");

			Query q = session.createQuery(hql.toString());
			q.setInteger("codEdificacao", codEdificacao);
			lista = q.list();
			if(lista != null && !lista.isEmpty()) {
				for(Ocupacao ocupacao: lista) {
					OcupacaoExibirBemImovelDTO dto = new OcupacaoExibirBemImovelDTO();
					dto.setOrgaoSiglaDescricao(ocupacao.getOrgao().getSigla().concat( " - ").concat(ocupacao.getOrgao().getDescricao()));
					dto.setDescricao(ocupacao.getDescricao());
					dto.setCodOcupacao(ocupacao.getCodOcupacao());
					dto.setEdificacao(ocupacao.getEdificacao().getEspecificacao());
					dto.setOcupacaoMetroQuadrado(ocupacao.getOcupacaoMetroQuadrado());
					dto.setOcupacaoPercentual(ocupacao.getOcupacaoPercentual());
					dto.setSituacaoOcupacao(ocupacao.getSituacaoOcupacao().getDescricao());
					

					int codigoSituacaoOcupacao = ocupacao.getSituacaoOcupacao().getCodSituacaoOcupacao().intValue();
					StringBuilder sb = new StringBuilder();
					
					// Preenche 'outras informações' de acordo com a situacao da ocupacao
					if(codigoSituacaoOcupacao == Dominios.SITUACAO_OCUPACAO_ADMINISTRACAO_ESTADUAL) {
						sb.append("Data da ocupação: ");						
						sb.append("<br>");  
						sb.append("Termo de transferência: ");								
						sb.append(ocupacao.getTermoTransferencia());
					}
					else if((codigoSituacaoOcupacao == Dominios.SITUACAO_OCUPACAO_ADMINISTRACAO_FEDERAL) ||
							(codigoSituacaoOcupacao == Dominios.SITUACAO_OCUPACAO_ADMINISTRACAO_MUNICIPAL) ||
							(codigoSituacaoOcupacao == Dominios.SITUACAO_OCUPACAO_TERCEIRO_AUTORIZADO)) {
						sb.append("Nº da Lei Autorizatória: ");	
						if (ocupacao.getNumeroLei() != null){
							sb.append(ocupacao.getNumeroLei().toString());	
						}
						sb.append("<br>");
						sb.append("Data da lei: ");
						if (ocupacao.getDataLei() != null){
							sb.append(ocupacao.getDataLei().toString());	
						}
						sb.append("<br>");
						sb.append("Vigência: ");	
						sb.append(ocupacao.getVigenciaLei());
					}
					else if(codigoSituacaoOcupacao == Dominios.SITUACAO_OCUPACAO_TERCEIRO_NAO_AUTORIZADO) {
						sb.append("Nº da Notificacao Extrajudicial: ");	
						if (ocupacao.getNumeroNotificacao() != null){
							sb.append(ocupacao.getNumeroNotificacao().toString());	
						}
						sb.append("<br>");
						sb.append("Data: ");
						if (ocupacao.getDataNotificacao() != null){
							sb.append(ocupacao.getDataNotificacao().toString());	
						}
						sb.append("<br>");
						sb.append("Prazo: ");		
						if (ocupacao.getPrazoNotificacao() != null){
							sb.append(ocupacao.getPrazoNotificacao().toString());	
						}
						sb.append("<br>");
						sb.append("Protocolo: ");
						if (ocupacao.getProtocoloNotificacaoSpi() != null){
							sb.append(ocupacao.getProtocoloNotificacaoSpi().toString());	
						}
						
					}
					
					dto.setOutrasInformacoes(sb.toString());
									
					coll.add(dto);
				}
			}			
			
		}
		catch(HibernateException he) {
			log4j.error("Erro:", he);
			throw new ApplicationException("ERRO.201",  new String[]{"ao listar Ocupação por Bem Imóvel"});
		}
		catch(Exception e) {
			log4j.error("Erro:", e);
			throw new ApplicationException("ERRO.201",  new String[]{"ao listar Ocupação por Bem Imóvel"}, e, ApplicationException.ICON_ERRO);
		}
		finally {
			try {
				HibernateUtil.closeSession();
			}
			catch(Exception e) {
				log4j.error("Problema ao tentar fechar conexao com o banco de dados: listarOcupacao", e);
			}		
		}

		return coll;
	}
	
	@SuppressWarnings("unchecked")
	public Collection<RelatorioEdificacaoOcupacaoDTO> listarRelatorioEdificacaoOcupacao(FiltroRelatorioEdificacaoOcupacaoDTO freDTO)
			throws ApplicationException {

		Collection<RelatorioEdificacaoOcupacaoDTO> listaRelatorio = new ArrayList<RelatorioEdificacaoOcupacaoDTO>();

		try {
			Session session = HibernateUtil.currentSession();
						
			StringBuffer hql = new StringBuffer();
			hql.append(" from Ocupacao as o LEFT JOIN FETCH o.orgao as org")
			.append(" LEFT JOIN FETCH o.edificacao edi ");
			if (freDTO.getListaCodOrgao() != null){
				//Incluido para adequar a disponibilizacao para orgaos
				hql.append(" LEFT JOIN FETCH o.orgao orgao ");
				hql.append(" LEFT JOIN FETCH o.bemImovel b1 ");
				hql.append(	" left join fetch b1.listaTransferencia transferencia1") ;
				hql.append(	" left join fetch transferencia1.orgaoCessionario orgaoTrans1") ;

				hql.append(" LEFT JOIN FETCH edi.bemImovel b2 ");
				hql.append(	" left join fetch b2.listaTransferencia transferencia2") ;
				hql.append(	" left join fetch transferencia2.orgaoCessionario orgaoTrans2") ;

				//
				
			}
		
			hql.append(" LEFT JOIN FETCH o.bemImovel.instituicao i ") ;
			
			
			hql.append(" WHERE 1=1 ");
			
			if(StringUtil.stringNotNull(freDTO.getUf()) && !freDTO.getUf().equals("0")) {
				hql.append("AND edi.bemImovel.uf ='")
				   .append(freDTO.getUf())
				   .append("' ");
			}
			
			if(StringUtil.stringNotNull(freDTO.getCodMunicipio()) && !freDTO.getCodMunicipio().equals("0")) {
				hql.append("AND edi.bemImovel.codMunicipio =")
				   .append(freDTO.getCodMunicipio())
				   .append(" ");
			}
			if(StringUtil.stringNotNull(freDTO.getCodOrgao())) {
				hql.append("AND org.codOrgao=")
				   .append(freDTO.getCodOrgao())
				   .append(" ");
			}

			//Incluido para adequar a disponibilizacao para orgaos
			if (freDTO.getListaCodOrgao() != null){
				hql.append("AND (");
				hql.append(" (orgao.codOrgao IN (:listaCodOrgao)) OR");
				hql.append(" (transferencia2.statusTermo.codStatusTermo = :codStatusTermo AND transferencia2.orgaoCessionario.codOrgao IN (:listaCodOrgao)) OR");
				hql.append(" (transferencia1.statusTermo.codStatusTermo = :codStatusTermo AND transferencia1.orgaoCessionario.codOrgao IN (:listaCodOrgao))");
				hql.append(")");
			}
			//
			if (freDTO.getInstituicao()!= null){
				hql.append(" AND i.codInstituicao= :codInstituicao ") ;
			}
			hql.append(" order by edi.bemImovel.uf, edi.bemImovel.municipio, edi.bemImovel.codBemImovel, edi.codEdificacao ");
			
			Query q = session.createQuery(hql.toString());
			//Incluido para adequar a disponibilizacao para orgaos
			if (freDTO.getListaCodOrgao() != null){
				q.setInteger("codStatusTermo", statusTermo.VIGENTE.getIndex());
				q.setParameterList("listaCodOrgao", freDTO.getListaCodOrgao());
			}
			//
			if (freDTO.getInstituicao()!= null){
				q.setInteger("codInstituicao", freDTO.getInstituicao().getCodInstituicao());

			}
			Set<Ocupacao> coll = new LinkedHashSet<Ocupacao>(q.list());
			
			for(Ocupacao ocupacao : coll) {
				RelatorioEdificacaoOcupacaoDTO relatorio = new RelatorioEdificacaoOcupacaoDTO();
				relatorio.setCodBemImovel(ocupacao.getEdificacao().getBemImovel().getCodBemImovel());
				relatorio.setOrgaoSiglaDescricao(ocupacao.getOrgao() != null ? ocupacao.getOrgao().getSigla().concat( " - ").concat( ocupacao.getOrgao().getDescricao()) : null);
				relatorio.setDescricao(ocupacao.getDescricao());
				relatorio.setOcupacaoMetroQuadrado(Valores.formatarParaDecimal(ocupacao.getOcupacaoMetroQuadrado(), 2));
				relatorio.setLogradouro(ocupacao.getEdificacao().getLogradouro());
				relatorio.setMunicipio(ocupacao.getEdificacao().getBemImovel().getMunicipio());
				relatorio.setUf(ocupacao.getEdificacao().getBemImovel().getUf());
				relatorio.setEspecificacao(ocupacao.getEdificacao().getEspecificacao());
				relatorio.setInstituicao(ocupacao.getBemImovel().getInstituicao());
				relatorio.setNrBemImovel(ocupacao.getEdificacao().getBemImovel().getNrBemImovel());
				if (ocupacao.getSituacaoOcupacao() != null){
					relatorio.setSituacaoOcupacao(ocupacao.getSituacaoOcupacao().getDescricao());
				}
				listaRelatorio.add(relatorio);
			 }
		}
		catch(HibernateException he) {
			throw new ApplicationException("mensagem.erro", new String[]{"gerar relatório Edificação Ocupação"}, he);
		}
		catch(Exception e) {
			throw new ApplicationException("mensagem.erro", new String[]{"gerar relatório Edificação Ocupação"}, e, ApplicationException.ICON_ERRO);
		}
		finally {
			try {
				HibernateUtil.closeSession();
			}
			catch(Exception e) {
				log4j.error("Erro ao Fechar Conexao com o Hibernate: ", e);				
			}
		}
		
		return listaRelatorio;	
	}
	
	/**
	 * Obter Ocupacao e objetos filhos inicializados.<br>
	 * @author Luciana R. Belico
	 * @since 10/03/2011
	 * @param Integer codOcupacao
	 * @return Ocupacao
	 * @throws ApplicationException
	 */
	public Ocupacao obterComEdificacao(Integer codOcupacao) throws ApplicationException {
		try {
			Session session = HibernateUtil.currentSession();
			Criteria c = session.createCriteria(Ocupacao.class)
								.add(Restrictions.eq("codOcupacao", codOcupacao))
								.setFetchMode("orgao", FetchMode.JOIN)
								.setFetchMode("bemImovel", FetchMode.JOIN)
								.setFetchMode("bemImovel.instituicao", FetchMode.JOIN)
								.setFetchMode("edificacao", FetchMode.JOIN);
								
			return (Ocupacao) c.uniqueResult();
		} catch (HibernateException he) {
			throw new ApplicationException("mensagem.erro", new String[]{"obterComEdificacao"}, he);
		} catch (Exception e) {
			throw new ApplicationException("mensagem.erro", new String[]{"obterComEdificacao "}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (Exception e) {
				log4j.error("Erro ao Fechar Conexao com o Hibernate: ", e);				
			}
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public Collection<OcupacaoListaDTO> listarporEdificacao(Integer codEdificacao, Integer qtdPagina, Integer numPagina)
			throws ApplicationException {

		Collection<OcupacaoListaDTO> coll = new ArrayList<OcupacaoListaDTO>();

		try {
			Session session = HibernateUtil.currentSession();
						
			StringBuffer hql = new StringBuffer();
			hql.append(" SELECT o.codOcupacao as codOcupacao, ( org.sigla ||' - '|| org.descricao)as orgaoSiglaDescricao, " )
			.append(" o.descricao as descricao, o.ocupacaoMetroQuadrado as ocupacaoMetroQuadrado,")
			.append(" o.ocupacaoPercentual as ocupacaoPercentual ")
			.append(" from Ocupacao as o LEFT JOIN  o.orgao as org")
			.append(" LEFT JOIN  o.edificacao e ");
			
			hql.append(" WHERE e.codEdificacao= :codEdificacao ");
			
			
			
			Query q = session.createQuery(hql.toString());
			q.setInteger("codEdificacao", codEdificacao);
			
			if (qtdPagina != null && numPagina != null) {
				q.setMaxResults(qtdPagina.intValue());
				q.setFirstResult( ((numPagina.intValue()-1) * qtdPagina.intValue()));
			}
			
			q.setResultTransformer(Transformers.aliasToBean(OcupacaoListaDTO.class));
			coll = q.list();
		}
		catch(HibernateException he) {
			throw new ApplicationException("mensagem.erro", new String[]{"listarporEdificacao"}, he);
		}
		catch(Exception e) {
			throw new ApplicationException("mensagem.erro", new String[]{"listarporEdificacao"}, e, ApplicationException.ICON_ERRO);
		}
		finally {
			try {
				HibernateUtil.closeSession();
			}
			catch(Exception e) {
				log4j.error("Erro ao Fechar Conexao com o Hibernate: ", e);				
			}
		}
		
		return coll;	
	}


	public Collection<OcupacaoOrgaoResponsavelListaDTO> listarPorBemImovelTerreno(Integer codBemImovel) throws ApplicationException {

		Collection<OcupacaoOrgaoResponsavelListaDTO> result = new ArrayList<OcupacaoOrgaoResponsavelListaDTO>();

		try {
			Session session = HibernateUtil.currentSession();
						
			StringBuffer hql = new StringBuffer();
			hql.append(" SELECT o.codOcupacao as codOcupacao, org.sigla , s.descricao as situacao, " )
			.append(" o.descricao as descricao, o.ocupacaoMetroQuadrado as ocupacaoMetroQuadrado,")
			.append(" o.ocupacaoPercentual as ocupacaoPercentual, s.codSituacaoOcupacao, ")
			.append(" o.dataOcupacao, o.termoTransferencia, ")
			.append(" o.numeroLei, o.dataLei, o.vigenciaLei, o.numeroNotificacao, o.dataNotificacao, ")
			.append(" o.prazoNotificacao, o.protocoloNotificacaoSpi, org.descricao, o.ativo ")
			.append(" from Ocupacao as o JOIN o.orgao as org")
			.append(" JOIN o.bemImovel b ")
			.append(" JOIN o.situacaoOcupacao s ");
			
			hql.append(" WHERE b.codBemImovel= :codBemImovel ");
			hql.append(" AND o.edificacao is null ");

			Query q = session.createQuery(hql.toString());
			q.setInteger("codBemImovel", codBemImovel);
			
			List<Object[]> retornoQuery = (ArrayList<Object[]>)q.list();
			if (retornoQuery != null){
				for (Iterator<Object[]> iter = retornoQuery.iterator(); iter.hasNext();) {
					Object[] element = (Object[]) iter.next();

					OcupacaoOrgaoResponsavelListaDTO obj = new OcupacaoOrgaoResponsavelListaDTO();
					obj.setCodOcupacao(Integer.valueOf(element[0].toString()));
					obj.setOrgao(element[1].toString() + " - " + element[16].toString() );
					obj.setSituacao(element[2].toString());
					obj.setDescricao(element[3].toString());
					if (element[4] != null){
						obj.setOcupacaoMetroQuadrado(new BigDecimal (element[4].toString()));	
					}
					if (element[5] != null){
						obj.setOcupacaoPercentual(new BigDecimal (element[5].toString()));	
					}
					obj.setAtivo(new Boolean(element[17].toString()));
					result.add(obj);
				}				
			}
			
			
		} catch(HibernateException he) {
			throw new ApplicationException("mensagem.erro", new String[]{"listarPorBemImovel"}, he);
		} catch(Exception e) {
			throw new ApplicationException("mensagem.erro", new String[]{"listarPorBemImovel"}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				HibernateUtil.closeSession();
			} catch(Exception e) {
				log4j.error("Erro ao Fechar Conexao com o Hibernate: ", e);				
			}
		}
		
		return result;	
	}

	public Integer obterQtdPorBemImovel(Integer codBemImovel, Boolean ativo) throws ApplicationException {

		Integer result;
		try {
			Session session = HibernateUtil.currentSession();
						
			StringBuffer hql = new StringBuffer();
			hql.append(" SELECT o.codOcupacao from Ocupacao as o ")
			.append(" JOIN o.bemImovel b ")
			.append(" WHERE o.bemImovel.codBemImovel= :codBemImovel ");
			if (ativo != null) {
				hql.append(" AND o.ativo = :ativo ");
			}
			
			Query q = session.createQuery(hql.toString());
			q.setInteger("codBemImovel", codBemImovel);
			if (ativo != null) {
				q.setBoolean("ativo", ativo);
			}
			
			List<Object[]> retornoQuery = (ArrayList<Object[]>)q.list();
			
			result = Integer.valueOf(retornoQuery.size());
			
		} catch(HibernateException he) {
			throw new ApplicationException("mensagem.erro", new String[]{"obterQtdPorBemImovel"}, he);
		} catch(Exception e) {
			throw new ApplicationException("mensagem.erro", new String[]{"obterQtdPorBemImovel"}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				HibernateUtil.closeSession();
			} catch(Exception e) {
				log4j.error("Erro ao Fechar Conexao com o Hibernate: ", e);				
			}
		}
		
		return result;	
	}

	public Integer verificarDuplicidadeOrgaoOcupacaoTerreno(Orgao orgao, BemImovel bemImovel) throws ApplicationException {

		Integer result;
		try {
			Session session = HibernateUtil.currentSession();
						
			StringBuffer hql = new StringBuffer();
			hql.append(" SELECT o.codOcupacao from Ocupacao as o ")
			.append(" JOIN o.bemImovel as b ")
			.append(" LEFT JOIN o.orgao as org")
			.append(" WHERE o.bemImovel.codBemImovel= :codBemImovel ")
			.append("AND org.codOrgao= :codOrgao ");

			Query q = session.createQuery(hql.toString());
			q.setInteger("codBemImovel", bemImovel.getCodBemImovel());
			q.setInteger("codOrgao", orgao.getCodOrgao());
			
			List<Object[]> retornoQuery = (ArrayList<Object[]>)q.list();
			
			result = Integer.valueOf(retornoQuery.size());
			
		} catch(HibernateException he) {
			throw new ApplicationException("mensagem.erro", new String[]{"verificarDuplicidadeOrgaoOcupacaoTerreno"}, he);
		} catch(Exception e) {
			throw new ApplicationException("mensagem.erro", new String[]{"verificarDuplicidadeOrgaoOcupacaoTerreno"}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				HibernateUtil.closeSession();
			} catch(Exception e) {
				log4j.error("Erro ao Fechar Conexao com o Hibernate: ", e);				
			}
		}
		
		return result;	
	}

	@Override
	public Ocupacao salvarOcupacao(Ocupacao ocupacao) throws ApplicationException {
		Ocupacao retorno = new Ocupacao();
		// Validando parâmetro
		if (ocupacao == null){
			throw new ApplicationException("mensagem.erro.9002", new String[]{"OcupacaoHibernateDAO.salvarOcupacao()"}, ApplicationException.ICON_ERRO);
		}
		
		try {
			log4j.debug("Obtendo sessao corrente...");
			Session session = HibernateUtil.currentSession();
			session.clear();
			Serializable chave = session.save(ocupacao);
			session.flush();
			log4j.info("SALVAR:" + ocupacao.getClass().getName() + " salvo.");
			retorno = (Ocupacao) session.get(Ocupacao.class, chave);
			log4j.info("GET:" + ocupacao.getClass().getName() + " q acabou de ser salvo.");
						
		} catch (ConstraintViolationException cve) {
			log4j.error("OcupacaoHibernateDAO.salvarOcupacao()" + cve);
			throw new ApplicationException("mensagem.erro.9003", new String[]{ocupacao.getClass().getSimpleName()}, ApplicationException.ICON_AVISO);
		} catch (HibernateException he) {
			log4j.error("OcupacaoHibernateDAO.salvarOcupacao()" + he);
			throw new ApplicationException("mensagem.erro.9001", new String[]{"OcupacaoHibernateDAO.salvarOcupacao()"}, he, ApplicationException.ICON_ERRO);
		} catch (Exception e) {
			log4j.error("OcupacaoHibernateDAO.salvarOcupacao()" + e);
			throw new ApplicationException("mensagem.erro.9001",new String[]{"OcupacaoHibernateDAO.salvarOcupacao()"}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				log4j.debug("Fechando sessao corrente.");
				HibernateUtil.closeSession();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.9003", new String[]{ocupacao.getClass().getSimpleName()}, ApplicationException.ICON_AVISO);
			}
		}
		
		return retorno;
	}

	@Override
	public Collection<Ocupacao> listarRelPorBemImovelTerreno(Integer codBemImovel) throws ApplicationException {
		try {
			Session session = HibernateUtil.currentSession();
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append(" SELECT ocupacao FROM Ocupacao ocupacao ");
			sbQuery.append(" LEFT JOIN FETCH ocupacao.situacaoOcupacao situacaoOcupacao ");
			sbQuery.append(" LEFT JOIN FETCH ocupacao.orgao orgao ");
			sbQuery.append(" LEFT JOIN FETCH ocupacao.bemImovel bemImovel ");
			sbQuery.append(" WHERE ocupacao.edificacao is null AND bemImovel.codBemImovel = :codBemImovel");

			Query query = session.createQuery(sbQuery.toString());
			query.setInteger("codBemImovel", codBemImovel);
				
			return (List<Ocupacao>)query.list();

		} catch (HibernateException he) {
			throw new ApplicationException("mensagem.erro.banco.listarSimplificado", he);
		} catch (Exception e) {
			throw new ApplicationException("mensagem.erro.banco.listarSimplificado", e, ApplicationException.ICON_ERRO);
		}finally {
			try {
				HibernateUtil.closeSession();
			} catch (Exception e) {
				log4j.error("Erro ao Fechar Conexao com o Hibernate: ", e);				
			}
		}
	}

}
