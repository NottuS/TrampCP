package gov.pr.celepar.abi.dao.implementation;

import gov.pr.celepar.abi.dao.BemImovelDAO;
import gov.pr.celepar.abi.dto.BemImovelPesquisaDTO;
import gov.pr.celepar.abi.dto.BemImovelVistoriaDTO;
import gov.pr.celepar.abi.dto.FiltroRelatorioAreaBemImovelDTO;
import gov.pr.celepar.abi.dto.FiltroRelatorioBemImovelDTO;
import gov.pr.celepar.abi.dto.ImpressaoBemImovelDTO;
import gov.pr.celepar.abi.dto.RelatorioBemImovelDTO;
import gov.pr.celepar.abi.facade.CadastroFacade;
import gov.pr.celepar.abi.pojo.BemImovel;
import gov.pr.celepar.abi.pojo.Documentacao;
import gov.pr.celepar.abi.pojo.Ocupacao;
import gov.pr.celepar.abi.pojo.Orgao;
import gov.pr.celepar.abi.pojo.ParametroAgenda;
import gov.pr.celepar.abi.pojo.UsuarioOrgao;
import gov.pr.celepar.abi.util.Dominios;
import gov.pr.celepar.abi.util.Dominios.statusTermo;
import gov.pr.celepar.abi.util.Util;
import gov.pr.celepar.framework.database.GenericHibernateDAO;
import gov.pr.celepar.framework.database.HibernateUtil;
import gov.pr.celepar.framework.exception.ApplicationException;
import gov.pr.celepar.framework.util.StringUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
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

/**
 * Classe de manipulacao de objetos da classe BemImovel.
 * 
 * @author ??? - Esta classe não foi gerada automaticamente
 * @since 1.0
 * @version 1.0, 24/12/2009 
 *
 */
public class BemImovelHibernateDAO extends GenericHibernateDAO<BemImovel, Integer> implements BemImovelDAO {

	private static final Logger log4j = Logger.getLogger(BemImovelDAO.class);
	protected static final String LOGINICIO = "Abrindo Sessao Hibernate";
	protected static final String LOGFIM = "Fechando Sessao Hibernate";
	protected static final String LOGERROFECHARSESSAO = "Problema ao tentar fechar sessao do Hibernate";
	
	private Query montarQueryPesquisa(StringBuffer hql, BemImovelPesquisaDTO bemDTO , Session session) throws ApplicationException {

		try{

			if(hql==null){
				hql = new StringBuffer();
			}
			hql.append(" FROM BemImovel b ")
			.append(" LEFT JOIN FETCH b.instituicao ")
			.append(" LEFT JOIN FETCH b.classificacaoBemImovel cla ")	
			.append(" LEFT JOIN FETCH b.edificacaos edi ")
			.append(" LEFT JOIN FETCH b.orgao ")
			.append(" LEFT JOIN FETCH edi.ocupacaos ocu ")
			.append(" LEFT JOIN FETCH b.ocupacaosTerreno ocuTerreno ")
			.append(" LEFT JOIN FETCH b.documentacaos doc ")
			.append(" LEFT JOIN FETCH b.quadras qua ")
			.append(" LEFT JOIN FETCH qua.lotes lot ");
			//tratamento de usuario de orgao
			if (bemDTO.getListaCodOrgao() != null && !bemDTO.getListaCodOrgao().isEmpty()){
				hql.append(" LEFT JOIN FETCH ocuTerreno.orgao orgTer ") ;
				hql.append(" LEFT JOIN FETCH ocu.orgao orgOcu ");
				hql.append(" LEFT JOIN FETCH b.listaTransferencia transferencia ") ;
				hql.append(" LEFT JOIN FETCH transferencia.orgaoCessionario orgaoTrans ") ;	
				//
			}
			
			hql.append("WHERE 1=1 ");

			if ( bemDTO.getCodInstituicao() != null && bemDTO.getCodInstituicao() > 0) {
				hql.append("AND b.instituicao.codInstituicao = :codInstituicao ");
			}		
			if ( bemDTO.getNrBemImovel() != null ) {
				hql.append("AND b.nrBemImovel = :nrBemImovel ");
			}

			if ( bemDTO.getAdministracao() != null ) {
				hql.append("AND b.administracao = :administracao ");
			}		
			if ( bemDTO.getCodOrgao() != null ) {
				hql.append("AND b.orgao.codOrgao = :codOrgao ");
			}		
			if ( bemDTO.getCodOrgaoResponsavel() != null ) {
				hql.append("AND (ocu.orgao.codOrgao = :codOrgaoResponsavel ");
				hql.append("OR ocuTerreno.orgao.codOrgao = :codOrgaoResponsavel) ");
			}		
			if ( bemDTO.getCodClassificacaoBemImovel() != null ) {
				hql.append("AND b.classificacaoBemImovel.codClassificacaoBemImovel = :codClassificacaoBemImovel ");
			}	

			if ( bemDTO.getNirf() != null) {
				hql.append("AND UPPER(doc.nirf) LIKE :nirf ");
			}
			if ( bemDTO.getNiif() != null) {
				hql.append("AND UPPER(doc.niif) LIKE :niif ");
			}
			if ( bemDTO.getIncra() != null) {
				hql.append("AND UPPER(doc.incra) LIKE :incra ");
			}

			if ( bemDTO.getUf() != null) {
				hql.append("AND b.uf = :uf ");
			}
			if ( bemDTO.getCodMunicipio() != null) {
				hql.append("AND b.codMunicipio = :codMunicipio ");
			}
			if ( bemDTO.getLogradouro() != null) {
				hql.append("AND UPPER(b.logradouro) LIKE :logradouro ");
			}
			if ( bemDTO.getOcupante() != null) {
				hql.append("AND UPPER(ocu.descricao) LIKE :ocupante ");
			}
			if ( bemDTO.getBairroDistrito() != null) {
				hql.append("AND UPPER(b.bairroDistrito) LIKE :bairroDistrito ");
			}
			if ( bemDTO.getCodSituacaoLegalCartorial() != null ) {
				hql.append("AND b.situacaoLegalCartorial.codSituacaoLegalCartorial = :codSituacaoLegalCartorial ");
			}
			if ( bemDTO.getCodCartorio() != null ) {
				hql.append("AND doc.cartorio.codCartorio = :codCartorio ");
			}
			if ( bemDTO.getNumeroDocumentoCartorial() != null ) {
				hql.append("AND doc.numeroDocumentoCartorial LIKE :numeroDocumentoCartorial ");
			}
			if ( bemDTO.getCodTabelionato() != null ) {
				hql.append("AND doc.tabelionato.codTabelionato = :codTabelionato ");
			}
			if ( bemDTO.getNumeroDocumentoTabelional() != null ) {
				hql.append("AND doc.numeroDocumentoTabelional LIKE :numeroDocumentoTabelional ");
			}
			if ( bemDTO.getCodFormaIncorporacao() != null ) {
				hql.append("AND b.formaIncorporacao.codFormaIncorporacao = :codFormaIncorporacao ");
			}
			if ( bemDTO.getAreaTerrenoIni() != null ) {
				hql.append("AND b.areaTerreno >= :areaTerrenoIni ");
			}
			if ( bemDTO.getAreaTerrenoFim() != null ) {
				hql.append("AND b.areaTerreno <= :areaTerrenoFim ");
			}		
			if ( bemDTO.getCodTipoConstrucao() != null) {
				hql.append("AND edi.tipoConstrucao.codTipoConstrucao = :codTipoConstrucao ");
			}
			if ( bemDTO.getCodTipoEdificacao() != null) {
				hql.append("AND edi.tipoEdificacao.codTipoEdificacao = :codTipoEdificacao ");
			}
			if ( bemDTO.getCodTipoDocumentacao() != null) {
				hql.append("AND doc.tipoDocumentacao.codTipoDocumentacao = :codTipoDocumentacao ");
			}
			if ( bemDTO.getCodSituacaoOcupacao() != null) {
				hql.append("AND ocu.situacaoOcupacao.codSituacaoOcupacao = :codSituacaoOcupacao ");
			}			
			if ( bemDTO.getLote() != null) {
				hql.append("AND UPPER(lot.descricao) LIKE :lote ");
			}
			if ( bemDTO.getQuadra() != null) {
				hql.append("AND UPPER(qua.descricao) LIKE :quadra ");
			}
			if ( bemDTO.getCodDenominacaoImovel() != null ) {
				hql.append("AND b.denominacaoImovel.codDenominacaoImovel = :codDenominacaoImovel ");
			}
			if ( bemDTO.getAverbado() != null ) {
				if ( bemDTO.getAverbado()) {
					hql.append("AND edi.dataAverbacao IS NOT NULL ");
				}else{
					hql.append("AND b.edificacaos IS NOT EMPTY AND edi.dataAverbacao IS NULL ");
				}
			}	

			if ( bemDTO.getCodSituacaoImovel() != null ) {
				hql.append("AND b.situacaoImovel.codSituacaoImovel = :codSituacaoImovel ");
			} else {
				hql.append("AND ( b.situacaoImovel.codSituacaoImovel <> 2 OR b.situacaoImovel.codSituacaoImovel IS NULL) ");

			}

			if ( bemDTO.getOrgaoOcupante() != null ) {
				if ( bemDTO.getOrgaoOcupante().equals("T")) {
					hql.append("AND b.somenteTerreno = 'S' ");
				} else if ( bemDTO.getOrgaoOcupante().equals("E")) {
					hql.append("AND b.somenteTerreno = 'N' ");
				}
			}	
			//Incluido para adequar a disponibilizacao para orgaos
			if (bemDTO.getListaCodOrgao() != null && !bemDTO.getListaCodOrgao().isEmpty()){
				hql.append(" AND (");
				hql.append(" (orgTer.codOrgao IN (:listaCodOrgao)) OR");
				hql.append(" (orgOcu.codOrgao IN (:listaCodOrgao)) OR");
				hql.append(" (transferencia.statusTermo.codStatusTermo = :codStatusTermo AND transferencia.orgaoCessionario.codOrgao IN (:listaCodOrgao))");
				hql.append(") ");
			}
			//
			hql.append(" ORDER BY b.codBemImovel ");
			Query q = session.createQuery(hql.toString());

			// Seta os valores dos parâmetros
			if ( bemDTO.getCodInstituicao() != null && bemDTO.getCodInstituicao() > 0) {
				q.setInteger("codInstituicao", bemDTO.getCodInstituicao());
			}		
			if ( bemDTO.getNrBemImovel() != null ) {
				q.setInteger("nrBemImovel", bemDTO.getNrBemImovel());
			}	
			if ( bemDTO.getAdministracao() != null ) {
				q.setInteger("administracao", bemDTO.getAdministracao());
			}
			if ( bemDTO.getCodOrgao() != null ) {
				q.setInteger("codOrgao", bemDTO.getCodOrgao());
			}
			if ( bemDTO.getCodOrgaoResponsavel() != null ) {
				q.setInteger("codOrgaoResponsavel", bemDTO.getCodOrgaoResponsavel());
			}
			if ( bemDTO.getCodClassificacaoBemImovel() != null ) {
				q.setInteger("codClassificacaoBemImovel", bemDTO.getCodClassificacaoBemImovel());
			}	

			if ( bemDTO.getNirf() != null) {
				q.setString("nirf", bemDTO.getNirf().toUpperCase() );
			}	
			if ( bemDTO.getNiif() != null) {
				q.setString("niif", bemDTO.getNiif().toUpperCase() );
			}	
			if ( bemDTO.getIncra() != null) {
				q.setString("incra", bemDTO.getIncra().toUpperCase() );
			}	
			if ( bemDTO.getOcupante() != null) {
				q.setString("ocupante", bemDTO.getOcupante().toUpperCase() );
			}

			if ( bemDTO.getUf() != null) {
				q.setString("uf", bemDTO.getUf());
			}		
			if ( bemDTO.getCodMunicipio() != null) {
				q.setInteger("codMunicipio", bemDTO.getCodMunicipio());
			}		
			if ( bemDTO.getLogradouro() != null) {
				q.setString("logradouro", bemDTO.getLogradouro().toUpperCase() );
			}		
			if ( bemDTO.getBairroDistrito() != null) {
				q.setString("bairroDistrito", bemDTO.getBairroDistrito().toUpperCase() );
			}
			if ( bemDTO.getCodSituacaoLegalCartorial() != null ) {
				q.setInteger("codSituacaoLegalCartorial", bemDTO.getCodSituacaoLegalCartorial());
			}		
			if ( bemDTO.getCodCartorio() != null ) {
				q.setInteger("codCartorio", bemDTO.getCodCartorio());
			}
			if ( bemDTO.getNumeroDocumentoCartorial() != null ) {
				q.setString("numeroDocumentoCartorial", bemDTO.getNumeroDocumentoCartorial());
			}
			if ( bemDTO.getCodTabelionato() != null ) {
				q.setInteger("codTabelionato", bemDTO.getCodTabelionato());
			}
			if ( bemDTO.getNumeroDocumentoTabelional() != null ) {
				q.setString("numeroDocumentoTabelional", bemDTO.getNumeroDocumentoTabelional());
			}
			if ( bemDTO.getCodFormaIncorporacao() != null ) {
				q.setInteger("codFormaIncorporacao", bemDTO.getCodFormaIncorporacao());
			}
			if ( bemDTO.getAreaTerrenoIni() != null) {
				q.setBigDecimal("areaTerrenoIni", bemDTO.getAreaTerrenoIni());		
			}
			if ( bemDTO.getAreaTerrenoFim() != null ) {			
				q.setBigDecimal("areaTerrenoFim", bemDTO.getAreaTerrenoFim());
			}

			if ( bemDTO.getCodTipoConstrucao() != null) {
				q.setInteger("codTipoConstrucao", bemDTO.getCodTipoConstrucao());
			}		
			if ( bemDTO.getCodTipoEdificacao() != null) {
				q.setInteger("codTipoEdificacao", bemDTO.getCodTipoEdificacao());
			}		
			if ( bemDTO.getCodTipoDocumentacao() != null) {
				q.setInteger("codTipoDocumentacao", bemDTO.getCodTipoDocumentacao());
			}
			if ( bemDTO.getLote() != null) {
				q.setString("lote", bemDTO.getLote().toUpperCase() );
			}		
			if ( bemDTO.getQuadra() != null) {
				q.setString("quadra", bemDTO.getQuadra().toUpperCase() );
			}
			if ( bemDTO.getCodSituacaoOcupacao() != null) {
				q.setInteger("codSituacaoOcupacao", bemDTO.getCodSituacaoOcupacao());
			}					
			if ( bemDTO.getCodDenominacaoImovel() != null ) {
				q.setInteger("codDenominacaoImovel", bemDTO.getCodDenominacaoImovel());
			}
			if ( bemDTO.getCodSituacaoImovel() != null ) {
				q.setInteger("codSituacaoImovel", bemDTO.getCodSituacaoImovel());
			}
			//Incluido para adequar a disponibilizacao para orgaos
			if (bemDTO.getListaCodOrgao() != null && !bemDTO.getListaCodOrgao().isEmpty()){
				q.setInteger("codStatusTermo", statusTermo.VIGENTE.getIndex());
				q.setParameterList("listaCodOrgao", bemDTO.getListaCodOrgao());
			}
			//
			return q;

		} catch (HibernateException he) {
			throw new ApplicationException("mensagem.erro", new String[]{"montar Query Pesquisas"}, he);
		} catch (Exception e) {
			throw new ApplicationException("mensagem.erro", new String[]{"montar Query Pesquisas "}, e, ApplicationException.ICON_ERRO);
		} 
	}


	@SuppressWarnings("unchecked")

	public Collection<BemImovel> listar(Integer qtdPagina, Integer numPagina, BemImovelPesquisaDTO bemDTO ) throws ApplicationException {
		try {
			Session session = HibernateUtil.currentSession();
			Query q = montarQueryPesquisa(null, bemDTO, session);
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
	
	public Integer obterQuantidadeLista(Integer qtdPagina, Integer numPagina, BemImovelPesquisaDTO bemDTO) throws ApplicationException {
		try {
			Session session = HibernateUtil.currentSession();
			Query q = montarQueryPesquisa(new StringBuffer("SELECT distinct(b) "), bemDTO, session);
			return q.list().size();
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

	/**
	 * Obter BemImovel e objetos filhos inicializados.<br>
	 * @author pialarissi
	 * @since 09/02/2008
	 * @param Integer codBemImovel
	 * @return BemImovel
	 * @throws ApplicationException
	 */
	public BemImovel obterExibir(Integer codBemImovel) throws ApplicationException {
		try {
			Session session = HibernateUtil.currentSession();
			Criteria c = session.createCriteria(BemImovel.class)
								.add(Restrictions.eq("codBemImovel", codBemImovel))
								.setFetchMode("orgao", FetchMode.JOIN)
								.setFetchMode("instituicao", FetchMode.JOIN)
								.setFetchMode("classificacaoBemImovel", FetchMode.JOIN)
								.setFetchMode("situacaoLocal", FetchMode.JOIN)
								.setFetchMode("situacaoLegalCartorial", FetchMode.JOIN)
								.setFetchMode("tabelionato", FetchMode.JOIN)
								.setFetchMode("formaIncorporacao", FetchMode.JOIN)								
								.setFetchMode("situacaoImovel", FetchMode.JOIN)
								.setFetchMode("cartorio", FetchMode.JOIN)
								.setFetchMode("denominacaoImovel", FetchMode.JOIN)
								.setFetchMode("avaliacaos", FetchMode.JOIN)
								.setFetchMode("coordenadaUtms", FetchMode.JOIN);
			return (BemImovel) c.uniqueResult();
		} catch (HibernateException he) {
			throw new ApplicationException("mensagem.erro", new String[]{"obterExibir"}, he);
		} catch (Exception e) {
			throw new ApplicationException("mensagem.erro", new String[]{"obterExibir "}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (Exception e) {
				log4j.error("Erro ao Fechar Conexao com o Hibernate: ", e);				
			}
		}
	}
	
	/**
	 * Obter BemImovel e objetos filhos inicializados.<br>
	 * @author pialarissi
	 * @since 09/02/2008
	 * @param Integer codBemImovel
	 * @return BemImovel
	 * @throws ApplicationException
	 */
	public BemImovel obterCompleto(Integer codBemImovel) throws ApplicationException {
		try {
			Session session = HibernateUtil.currentSession();
			Criteria c = session.createCriteria(BemImovel.class)
			.add(Restrictions.eq("codBemImovel", codBemImovel))
			.setFetchMode("confrontantes", FetchMode.JOIN)
			.setFetchMode("coordenadaUtms", FetchMode.JOIN);
			c.createCriteria("quadras","quadra", Criteria.LEFT_JOIN);
			c.createCriteria("quadra.lotes", "lote", Criteria.LEFT_JOIN);
			c.createCriteria("edificacaos", "edificacao", Criteria.LEFT_JOIN)
			.createCriteria("edificacao.ocupacaos", "ocupacao", Criteria.LEFT_JOIN)
			.createCriteria("edificacao.documentacaos", "documentacao", Criteria.LEFT_JOIN)
			.createCriteria("edificacao.avaliacaos", "avaliacao", Criteria.LEFT_JOIN);

			return (BemImovel) c.uniqueResult();
		} catch (HibernateException he) {
			throw new ApplicationException("mensagem.erro", new String[]{"obterCompleto"}, he);
		} catch (Exception e) {
			throw new ApplicationException("mensagem.erro", new String[]{"obterCompleto "}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (Exception e) {
				log4j.error("Erro ao Fechar Conexao com o Hibernate: ", e);				
			}
		}
	}
	
	
	/**
	 * Obter BemImovel e objetos filhos inicializados.<br>
	 * @author pialarissi
	 * @since 09/02/2008
	 * @param Integer codBemImovel
	 * @return BemImovel
	 * @throws ApplicationException
	 */
	public BemImovel obterComEdificacaoes(Integer codBemImovel) throws ApplicationException {
		try {
			Session session = HibernateUtil.currentSession();
			Criteria c = session.createCriteria(BemImovel.class)
								.add(Restrictions.eq("codBemImovel", codBemImovel))
								.setFetchMode("documentacaos", FetchMode.JOIN)
								.setFetchMode("edificacaos", FetchMode.JOIN);
			return (BemImovel) c.uniqueResult();
		} catch (HibernateException he) {
			throw new ApplicationException("mensagem.erro", new String[]{"obter Bem Imóvel com Edificações"}, he);
		} catch (Exception e) {
			throw new ApplicationException("mensagem.erro", new String[]{"obter Bem Imóvel com Edificações "}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (Exception e) {
				log4j.error("Erro ao Fechar Conexao com o Hibernate: ", e);				
			}
		}
	}
	
	/**
	 * Obter Lista de Bens Imoveis e objetos filhos inicializados conforme parametros do relatorio.<br>
	 * @author lucianabelico
	 * @since 13/02/2010
	 * @param FiltroRelatorioBemImovelDTO rbiDTO
	 * @return BemImovel
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public Collection<BemImovel> listarRelatorioBensImoveis(FiltroRelatorioBemImovelDTO rbiDTO) throws ApplicationException {
		
		Collection<BemImovel> coll = new ArrayList<BemImovel>();
		
		try {
			Session session = HibernateUtil.currentSession();

			StringBuffer hql = new StringBuffer();
			hql.append("SELECT DISTINCT b FROM BemImovel b ")
			.append(" LEFT JOIN FETCH b.classificacaoBemImovel ")	
			.append(" LEFT JOIN FETCH b.leiBemImovels lb ")
			.append(" LEFT JOIN FETCH lb.tipoLeiBemImovel ")
			.append(" LEFT JOIN FETCH b.confrontantes ")
			.append(" LEFT JOIN FETCH b.avaliacaos ")
			.append(" LEFT JOIN FETCH b.coordenadaUtms ")
			.append(" LEFT JOIN FETCH b.edificacaos edi ")
			.append(" LEFT JOIN FETCH edi.ocupacaos ocu ")
			.append(" LEFT JOIN FETCH ocu.orgao orgOcu ")
			.append(" LEFT JOIN FETCH edi.tipoEdificacao ")
			.append(" LEFT JOIN FETCH edi.tipoConstrucao ")
			.append(" LEFT JOIN FETCH b.documentacaos doc ")
			.append(" LEFT JOIN FETCH doc.notificacaos ")
			.append(" LEFT JOIN FETCH doc.ocorrenciaDocumentacaos ")
			.append(" LEFT JOIN FETCH doc.tabelionato ")
			.append(" LEFT JOIN FETCH doc.cartorio ")
			.append(" LEFT JOIN FETCH doc.tipoDocumentacao ")
			.append(" LEFT JOIN FETCH b.quadras qua ")
			.append(" LEFT JOIN FETCH qua.lotes lot ")
			.append(" LEFT JOIN FETCH b.orgao ")
			.append(" LEFT JOIN FETCH b.situacaoLegalCartorial ")
			.append(" LEFT JOIN FETCH b.formaIncorporacao ")
			.append(" LEFT JOIN FETCH b.situacaoImovel ")
			.append(" LEFT JOIN FETCH b.denominacaoImovel ");
			
			//Incluido para adequar a disponibilizacao para orgaos
			hql.append(	" left join fetch b.ocupacaosTerreno ot") ;
			hql.append(	" left join fetch ot.orgao orgTer") ;
			hql.append(	" left join fetch b.listaTransferencia transferencia") ;
			hql.append(	" left join fetch transferencia.orgaoCessionario orgaoTrans ") ;
			
			hql.append(" LEFT JOIN b.instituicao i ");
			hql.append(" LEFT JOIN  i.listaUsuario lu ");
			//

			hql.append(" WHERE 1=1 ");
			
			
			if (!rbiDTO.isAdmGeral() &&  !StringUtil.stringNotNull(rbiDTO.getOrgao())){
				hql.append(" AND lu.idSentinela=:codUsuarioSentinela ");
			}
			if (StringUtil.stringNotNull(rbiDTO.getCodInstituicao())){
				hql.append(" AND i.codInstituicao= :codInstituicao ");
			}
			
			//Incluido para adequar a disponibilizacao para orgaos
			if (rbiDTO.getIndOperadorOrgao() != null && rbiDTO.getIndOperadorOrgao().equals(1) &&  !StringUtil.stringNotNull(rbiDTO.getOrgao())){
				hql.append("AND (");
				hql.append(" (orgTer.codOrgao IN (:listaCodOrgao)) OR");
				hql.append(" (orgOcu.codOrgao IN (:listaCodOrgao)) OR");
				hql.append(" (transferencia.statusTermo.codStatusTermo = :codStatusTermo AND transferencia.orgaoCessionario.codOrgao IN (:listaCodOrgao))");
				hql.append(") ");
			}
			//
			
			if ( StringUtil.stringNotNull(rbiDTO.getUf()) && !rbiDTO.getUf().equals("0")) {
				hql.append("AND b.uf =:uf ");
			
			}
			
			if ( StringUtil.stringNotNull(rbiDTO.getCodMunicipio()) && !rbiDTO.getCodMunicipio().equals("0") ) {
				if (Dominios.faixasPesquisasMunicipios.FAIXA_MENOR_C.getIndex().equals(Integer.valueOf(rbiDTO.getCodMunicipio()))) {
					hql.append("AND UPPER(SUBSTRING(b.municipio,1,1)) <= 'C' AND UPPER(b.municipio) <> 'CURITIBA' ");
				} else 
					if (Dominios.faixasPesquisasMunicipios.FAIXA_CURITIBA.getIndex().equals(Integer.valueOf(rbiDTO.getCodMunicipio()))) {
						hql.append("AND UPPER(b.municipio) = 'CURITIBA' ");
				} else 
					if (Dominios.faixasPesquisasMunicipios.FAIXA_D_F.getIndex().equals(Integer.valueOf(rbiDTO.getCodMunicipio()))) {
						hql.append("AND UPPER(SUBSTRING(b.municipio,1,1)) >= 'D' and UPPER(SUBSTRING(b.municipio,1,1)) <= 'F' ");
				} else 
					if (Dominios.faixasPesquisasMunicipios.FAIXA_G_I.getIndex().equals(Integer.valueOf(rbiDTO.getCodMunicipio()))) {
						hql.append("AND UPPER(SUBSTRING(b.municipio,1,1)) >= 'G' and UPPER(SUBSTRING(b.municipio,1,1)) <= 'I' ");
				} else 
					if (Dominios.faixasPesquisasMunicipios.FAIXA_J_M.getIndex().equals(Integer.valueOf(rbiDTO.getCodMunicipio()))) {
						hql.append("AND UPPER(SUBSTRING(b.municipio,1,1)) >= 'J' and UPPER(SUBSTRING(b.municipio,1,1)) <= 'M' ");
				} else 
					if (Dominios.faixasPesquisasMunicipios.FAIXA_N_P.getIndex().equals(Integer.valueOf(rbiDTO.getCodMunicipio()))) {
						hql.append("AND UPPER(SUBSTRING(b.municipio,1,1)) >= 'N' and UPPER(SUBSTRING(b.municipio,1,1)) <= 'P' ");
				} else 
					if (Dominios.faixasPesquisasMunicipios.FAIXA_Q_S.getIndex().equals(Integer.valueOf(rbiDTO.getCodMunicipio()))) {
						hql.append("AND UPPER(SUBSTRING(b.municipio,1,1)) >= 'Q' and UPPER(SUBSTRING(b.municipio,1,1)) <= 'S' ");
				} else 
					if (Dominios.faixasPesquisasMunicipios.FAIXA_T_Z.getIndex().equals(Integer.valueOf(rbiDTO.getCodMunicipio()))) {
						hql.append("AND UPPER(SUBSTRING(b.municipio,1,1)) >= 'T' ");
				} else {
					hql.append("AND b.codMunicipio = :codMunicipio ");
				}
			}

			if ( StringUtil.stringNotNull(rbiDTO.getCodClassificacao())) {
				hql.append("AND b.classificacaoBemImovel.codClassificacaoBemImovel =:codClassificacao ");
				
			}
			
			if ( StringUtil.stringNotNull(rbiDTO.getCodSituacao())) {
				hql.append("AND b.situacaoImovel.codSituacaoImovel =:codSituacao ");
				 
			}
			if ( StringUtil.stringNotNull(rbiDTO.getCodSituacaoLegalCartorial())) {
				hql.append("AND b.situacaoLegalCartorial.codSituacaoLegalCartorial =:codSituacaoLegalCartorial ");
				
			}
			if ( StringUtil.stringNotNull(rbiDTO.getFiltroTerreno())) {
				if (rbiDTO.getFiltroTerreno().equals("2")) //com edificações
				{
					hql.append("AND b.edificacaos is not empty ");
				}else if (rbiDTO.getFiltroTerreno().equals("3")) //sem edificações
				{
					hql.append("AND b.edificacaos is empty ");
				}
			}
			if ( StringUtil.stringNotNull(rbiDTO.getFiltroAdministracao())) {
				hql.append("AND b.administracao =:administracao ");
			}
			
			if ( rbiDTO.getOrgaoOcupante() != null ) {
				if ( rbiDTO.getOrgaoOcupante().equals("T")) {
					hql.append("AND b.somenteTerreno = 'S' ");
				} else if ( rbiDTO.getOrgaoOcupante().equals("E")) {
					hql.append("AND b.somenteTerreno = 'N' ");
				}
			}
			
			if ( StringUtil.stringNotNull(rbiDTO.getOrgao())) {
				hql.append("AND (b.orgao.codOrgao = :codOrgao OR ocu.orgao.codOrgao = :codOrgao )");
				 
			}

			hql.append(" order by b.uf, b.municipio, b.codBemImovel ");
			
			Query q = session.createQuery(hql.toString());
			
			if ( StringUtil.stringNotNull(rbiDTO.getUf()) && !rbiDTO.getUf().equals("0")) {
				q.setString("uf", rbiDTO.getUf());
			}
			
			if ( StringUtil.stringNotNull(rbiDTO.getCodMunicipio()) && !rbiDTO.getCodMunicipio().equals("0") && !(Integer.valueOf(rbiDTO.getCodMunicipio()) < 0)  ) {
				q.setInteger("codMunicipio", Integer.valueOf(rbiDTO.getCodMunicipio()));
			}

			if ( StringUtil.stringNotNull(rbiDTO.getCodClassificacao())) {
				q.setInteger("codClassificacao", Integer.valueOf(rbiDTO.getCodClassificacao()));
			}
			
			if ( StringUtil.stringNotNull(rbiDTO.getCodSituacao())) {
				q.setInteger("codSituacao", Integer.valueOf(rbiDTO.getCodSituacao()));
			}
			if ( StringUtil.stringNotNull(rbiDTO.getCodSituacaoLegalCartorial())) {
				q.setInteger("codSituacaoLegalCartorial", Integer.valueOf(rbiDTO.getCodSituacaoLegalCartorial()));
			}
			if ( StringUtil.stringNotNull(rbiDTO.getFiltroAdministracao())) {
				q.setInteger("administracao", Integer.valueOf(rbiDTO.getFiltroAdministracao()));
			}
			if ( StringUtil.stringNotNull(rbiDTO.getOrgao())) {
				q.setInteger("codOrgao", Integer.valueOf(rbiDTO.getOrgao()));
			}
			if (!rbiDTO.isAdmGeral() &&  !StringUtil.stringNotNull(rbiDTO.getOrgao())){
				q.setLong("codUsuarioSentinela", rbiDTO.getUsuarioS().getIdSentinela());
			}
			//Incluido para adequar a disponibilizacao para orgaos
			if (rbiDTO.getIndOperadorOrgao() != null && rbiDTO.getIndOperadorOrgao().equals(1) &&  !StringUtil.stringNotNull(rbiDTO.getOrgao())){
				q.setInteger("codStatusTermo", statusTermo.VIGENTE.getIndex());
				List<Integer> listaCodOrgao = new ArrayList<Integer>();
				for (UsuarioOrgao o : rbiDTO.getUsuarioS().getListaUsuarioOrgao()){
					listaCodOrgao.add(o.getOrgao().getCodOrgao());
				}
				q.setParameterList("listaCodOrgao", listaCodOrgao);
			}
			//
			
			if (StringUtil.stringNotNull(rbiDTO.getCodInstituicao())){
				q.setInteger("codInstituicao", Integer.valueOf(rbiDTO.getCodInstituicao()));
				
			}
			Collection<BemImovel> coll2 = q.list();
			
			for (BemImovel bemImovel : coll2) {
				Set<Ocupacao> listOcupacao = new HashSet<Ocupacao>(); 
				listOcupacao.addAll(CadastroFacade.listarOcupacaoPorBemImovelTerreno(bemImovel.getCodBemImovel()));
				bemImovel.setOcupacaosTerreno(listOcupacao);
				coll.add(bemImovel);
			}
			
		} catch (HibernateException he) {
			throw new ApplicationException("mensagem.erro", new String[]{"gerar relatório Bem Imóvel"}, he);
		} catch (Exception e) {
			throw new ApplicationException("mensagem.erro", new String[]{"gerar relatório Bem Imóvel "}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (Exception e) {
				log4j.error("Erro ao Fechar Conexao com o Hibernate: ", e);				
			}
		}
		
		return coll;
	}
	
	/**
	 * Obter Lista de Bens Imoveis e objetos filhos inicializados conforme parametros do relatorio.<br>
	 * @author lucianabelico
	 * @since 13/02/2010
	 * @param FiltroRelatorioBemImovelDTO rbiDTO
	 * @return BemImovel
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public Collection<BemImovel> listarRelatorioGerencialBensImoveis(FiltroRelatorioBemImovelDTO rbiDTO) throws ApplicationException {

		Collection<BemImovel> coll = new ArrayList<BemImovel>();
		
		try {

			Session session = HibernateUtil.currentSession();
			StringBuffer hql = new StringBuffer();
			hql.append("SELECT new BemImovel(b.codBemImovel as codBemImovel,  ")
			.append(" b.uf as uf, b.municipio as municipio , b.codMunicipio as codMunicipio) FROM BemImovel b ")	;
			
			//
			hql.append("WHERE 1=1 ");
			
			if ( StringUtil.stringNotNull(rbiDTO.getUf()) && !rbiDTO.getUf().equals("0")) {
				hql.append("AND b.uf =:uf ");
			
			}
			
			if ( StringUtil.stringNotNull(rbiDTO.getCodMunicipio()) && !rbiDTO.getCodMunicipio().equals("0") ) {
				if (Dominios.faixasPesquisasMunicipios.FAIXA_MENOR_C.getIndex().equals(Integer.valueOf(rbiDTO.getCodMunicipio()))) {
					hql.append("AND UPPER(SUBSTRING(b.municipio,1,1)) <= 'C' AND UPPER(b.municipio) <> 'CURITIBA' ");
				} else 
					if (Dominios.faixasPesquisasMunicipios.FAIXA_CURITIBA.getIndex().equals(Integer.valueOf(rbiDTO.getCodMunicipio()))) {
						hql.append("AND UPPER(b.municipio) = 'CURITIBA' ");
				} else 
					if (Dominios.faixasPesquisasMunicipios.FAIXA_D_F.getIndex().equals(Integer.valueOf(rbiDTO.getCodMunicipio()))) {
						hql.append("AND UPPER(SUBSTRING(b.municipio,1,1)) >= 'D' and UPPER(SUBSTRING(b.municipio,1,1)) <= 'F' ");
				} else 
					if (Dominios.faixasPesquisasMunicipios.FAIXA_G_I.getIndex().equals(Integer.valueOf(rbiDTO.getCodMunicipio()))) {
						hql.append("AND UPPER(SUBSTRING(b.municipio,1,1)) >= 'G' and UPPER(SUBSTRING(b.municipio,1,1)) <= 'I' ");
				} else 
					if (Dominios.faixasPesquisasMunicipios.FAIXA_J_M.getIndex().equals(Integer.valueOf(rbiDTO.getCodMunicipio()))) {
						hql.append("AND UPPER(SUBSTRING(b.municipio,1,1)) >= 'J' and UPPER(SUBSTRING(b.municipio,1,1)) <= 'M' ");
				} else 
					if (Dominios.faixasPesquisasMunicipios.FAIXA_N_P.getIndex().equals(Integer.valueOf(rbiDTO.getCodMunicipio()))) {
						hql.append("AND UPPER(SUBSTRING(b.municipio,1,1)) >= 'N' and UPPER(SUBSTRING(b.municipio,1,1)) <= 'P' ");
				} else 
					if (Dominios.faixasPesquisasMunicipios.FAIXA_Q_S.getIndex().equals(Integer.valueOf(rbiDTO.getCodMunicipio()))) {
						hql.append("AND UPPER(SUBSTRING(b.municipio,1,1)) >= 'Q' and UPPER(SUBSTRING(b.municipio,1,1)) <= 'S' ");
				} else 
					if (Dominios.faixasPesquisasMunicipios.FAIXA_T_Z.getIndex().equals(Integer.valueOf(rbiDTO.getCodMunicipio()))) {
						hql.append("AND UPPER(SUBSTRING(b.municipio,1,1)) >= 'T' ");
				} else {
					hql.append("AND b.codMunicipio = :codMunicipio ");
				}
			}

			if ( StringUtil.stringNotNull(rbiDTO.getCodClassificacao())) {
				hql.append("AND b.classificacaoBemImovel.codClassificacaoBemImovel =:codClassificacao ");
				
			}
			
			if ( StringUtil.stringNotNull(rbiDTO.getCodSituacao())) {
				hql.append("AND b.situacaoImovel.codSituacaoImovel =:codSituacao ");
				 
			}
			if ( StringUtil.stringNotNull(rbiDTO.getCodSituacaoLegalCartorial())) {
				hql.append("AND b.situacaoLegalCartorial.codSituacaoLegalCartorial =:codSituacaoLegalCartorial ");
				
			}
			if ( StringUtil.stringNotNull(rbiDTO.getFiltroTerreno())) {
				if (rbiDTO.getFiltroTerreno().equals("2")) //com edificações
				{
					hql.append("AND b.edificacaos is not empty ");
				}else if (rbiDTO.getFiltroTerreno().equals("3")) //sem edificações
				{
					hql.append("AND b.edificacaos is empty ");
				}
			}
			if ( StringUtil.stringNotNull(rbiDTO.getFiltroAdministracao())) {
				hql.append("AND b.administracao =:administracao ");
			}
			
			
			hql.append(" order by b.uf, b.municipio, b.codBemImovel");
			
			
			Query q = session.createQuery(hql.toString());
			
			if ( StringUtil.stringNotNull(rbiDTO.getUf()) && !rbiDTO.getUf().equals("0")) {
				q.setString("uf", rbiDTO.getUf());
			}
			
			if ( StringUtil.stringNotNull(rbiDTO.getCodMunicipio()) && !rbiDTO.getCodMunicipio().equals("0") && !(Integer.valueOf(rbiDTO.getCodMunicipio()) < 0)  ) {
				q.setInteger("codMunicipio", Integer.valueOf(rbiDTO.getCodMunicipio()));
			}

			if ( StringUtil.stringNotNull(rbiDTO.getCodClassificacao())) {
				q.setInteger("codClassificacao", Integer.valueOf(rbiDTO.getCodClassificacao()));
			}
			
			if ( StringUtil.stringNotNull(rbiDTO.getCodSituacao())) {
				q.setInteger("codSituacao", Integer.valueOf(rbiDTO.getCodSituacao()));
			}
			if ( StringUtil.stringNotNull(rbiDTO.getCodSituacaoLegalCartorial())) {
				q.setInteger("codSituacaoLegalCartorial", Integer.valueOf(rbiDTO.getCodSituacaoLegalCartorial()));
			}
			if ( StringUtil.stringNotNull(rbiDTO.getFiltroAdministracao())) {
				q.setInteger("administracao", Integer.valueOf(rbiDTO.getFiltroAdministracao()));
			}
			coll = q.list();
			
			
		} catch (HibernateException he) {
			throw new ApplicationException("mensagem.erro", new String[]{"gerar relatório Bem Imóvel"}, he);
		} catch (Exception e) {
			throw new ApplicationException("mensagem.erro", new String[]{"gerar relatório Bem Imóvel "}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (Exception e) {
				log4j.error("Erro ao Fechar Conexao com o Hibernate: ", e);				
			}
		}
		
		return coll;
			
	}
	
	
	/**
	 * Obter Lista de Bens Imoveis e objetos filhos inicializados conforme parametros do relatorio.<br>
	 * @author lucianabelico
	 * @since 13/02/2010
	 * @param FiltroRelatorioBemImovelDTO rbiDTO
	 * @return BemImovel
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public Collection<RelatorioBemImovelDTO> listarRelatorioAreaBensImoveis(FiltroRelatorioAreaBemImovelDTO rbiDTO) throws ApplicationException {
		
		Collection<RelatorioBemImovelDTO> listaRelatorio = new ArrayList<RelatorioBemImovelDTO>();
		Collection<BemImovel> coll = new ArrayList<BemImovel>();
		
		try {
			Session session = HibernateUtil.currentSession();
			StringBuffer hql = new StringBuffer();
			hql.append("SELECT DISTINCT b FROM BemImovel b ")
			.append(" LEFT JOIN FETCH b.classificacaoBemImovel classificacaoBemImovel")
			.append(" LEFT JOIN FETCH b.situacaoLegalCartorial situacaoLegalCartorial")
			.append(" LEFT JOIN FETCH b.formaIncorporacao formaIncorporacao")
			.append(" LEFT JOIN FETCH b.situacaoImovel situacaoImovel")
			.append(" LEFT JOIN FETCH b.documentacaos documentacaos")
			.append(" LEFT JOIN FETCH b.denominacaoImovel denominacaoImovel");
			if (rbiDTO.getListaCodOrgao() != null){
				//Incluido para adequar a disponibilizacao para orgaos
				hql.append(" LEFT JOIN FETCH b.ocupacaosTerreno ot") ;
				hql.append(" LEFT JOIN FETCH ot.orgao orgTer") ;
				hql.append(" LEFT JOIN FETCH b.edificacaos edi");
				hql.append(" LEFT JOIN FETCH edi.ocupacaos ocu ");
				hql.append(" LEFT JOIN FETCH ocu.orgao orgOcu ");
				hql.append(" LEFT JOIN FETCH b.listaTransferencia transferencia") ;
				hql.append(" LEFT JOIN FETCH transferencia.orgaoCessionario orgaoTrans ") ;
			}
			
			hql.append(" LEFT JOIN FETCH b.instituicao i ") ;
			
			hql.append(" WHERE 1=1 ");
			
			
			
			if (StringUtil.stringNotNull(rbiDTO.getUf()) && !rbiDTO.getUf().equals("0")){
				hql.append(" AND b.uf = :uf ");
			}
			if (StringUtil.stringNotNull(rbiDTO.getCodMunicipio())&& !rbiDTO.getCodMunicipio().equals("0")){
				hql.append(" AND b.codMunicipio = :codMunicipio ");
			}
			if (StringUtil.stringNotNull(rbiDTO.getFiltroTerreno())){
				if (rbiDTO.getFiltroTerreno().equals("2")) //com edificações
				{
					hql.append(" AND EXISTS (select e.codEdificacao FROM Edificacao e WHERE e.bemImovel.codBemImovel = b.codBemImovel) ");
				}else if (rbiDTO.getFiltroTerreno().equals("3")) //sem edificações
				{
					hql.append(" AND NOT EXISTS (select e.codEdificacao FROM Edificacao e WHERE e.bemImovel.codBemImovel = b.codBemImovel) ");
				}
			}
			
			if(rbiDTO.getFiltroAreaDe() != null){
				hql.append(" AND b.areaTerreno >= :areaDe ");
			}
			if( rbiDTO.getFiltroAreaAte()!=null){
				hql.append(" AND b.areaTerreno <= :areaAte ");
			}
			
			if ( StringUtil.stringNotNull(rbiDTO.getFiltroAdministracao())) {				
				hql.append(" AND b.administracao= :adm ");
			}
			
			//Incluido para adequar a disponibilizacao para orgaos
			if (rbiDTO.getListaCodOrgao() != null){
				hql.append(" AND (");
				hql.append(" (orgTer.codOrgao IN (:listaCodOrgao)) OR");
				hql.append(" (orgOcu.codOrgao IN (:listaCodOrgao)) OR");
				hql.append(" (transferencia.statusTermo.codStatusTermo = :codStatusTermo AND transferencia.orgaoCessionario.codOrgao IN (:listaCodOrgao))");
				hql.append(")");
			}
			if (rbiDTO.getInstituicao()!= null){
				hql.append(" AND i.codInstituicao= :codInstituicao ") ;
			}
			
			hql.append(" ORDER BY i.codInstituicao, b.uf, b.municipio, b.codBemImovel");
			
			Query q = session.createQuery(hql.toString());
			    
			if (StringUtil.stringNotNull(rbiDTO.getUf()) && !rbiDTO.getUf().equals("0")){
				q.setString("uf", rbiDTO.getUf());
			}
			if (StringUtil.stringNotNull(rbiDTO.getCodMunicipio())&& !rbiDTO.getCodMunicipio().equals("0")){
				q.setInteger("codMunicipio", Integer.parseInt(rbiDTO.getCodMunicipio()));
			}

			if(rbiDTO.getFiltroAreaDe() != null){
				hql.append(" AND b.areaTerreno >= :areaDe ");
				q.setBigDecimal("areaDe", rbiDTO.getFiltroAreaDe());
			}
			if( rbiDTO.getFiltroAreaAte()!=null){
				q.setBigDecimal("areaAte", rbiDTO.getFiltroAreaAte());
			}

			if ( StringUtil.stringNotNull(rbiDTO.getFiltroAdministracao())) {
				q.setInteger("adm", Integer.parseInt(rbiDTO.getFiltroAdministracao()));
			}
			//Incluido para adequar a disponibilizacao para orgaos
			if (rbiDTO.getListaCodOrgao() != null){
				q.setInteger("codStatusTermo", statusTermo.VIGENTE.getIndex());
				q.setParameterList("listaCodOrgao", rbiDTO.getListaCodOrgao());
			}
			//
			if (rbiDTO.getInstituicao()!= null){
				q.setInteger("codInstituicao", rbiDTO.getInstituicao().getCodInstituicao());
			}
			coll= q.list();
			
			
			 for (BemImovel bemImovel :coll){
				 RelatorioBemImovelDTO relatorio = new RelatorioBemImovelDTO();
				 relatorio.setCodBemImovel(bemImovel.getCodBemImovel());
				 if(bemImovel.getClassificacaoBemImovel()!= null){
					 relatorio.setClassificacaoBemImovel(bemImovel.getClassificacaoBemImovel().getDescricao());
				 }
				 relatorio.setMunicipio(bemImovel.getMunicipio());
				 StringBuffer aux = new StringBuffer();
				 for (Documentacao d : bemImovel.getDocumentacaos()){
					 if (!Util.strEmBranco(d.getNumeroDocumentoCartorial())){
						 aux.append(d.getNumeroDocumentoCartorial()).append("\n");
					 }
				 }
				 relatorio.setNumeroDocumentoCartorial(aux.toString());
				 if (bemImovel.getSituacaoImovel()!= null){
					 relatorio.setSituacaoImovel(bemImovel.getSituacaoImovel().getDescricao());
				 }
				 relatorio.setUf(bemImovel.getUf());
				 relatorio.setAreaTerreno(bemImovel.getAreaTerreno());
				 if (StringUtil.stringNotNull(bemImovel.getLogradouro())){
					 relatorio.setLogradouro(bemImovel.getLogradouro());
				 }else if (bemImovel.getDenominacaoImovel() != null){
					 relatorio.setLogradouro(bemImovel.getDenominacaoImovel().getDescricao());
				 }
				 if (bemImovel.getInstituicao()!= null){
					 relatorio.setInstituicao(bemImovel.getInstituicao());
				 }
				 relatorio.setNrBemImovel(bemImovel.getNrBemImovel());
				 listaRelatorio.add(relatorio);
			 }
			
		} catch (HibernateException he) {
			throw new ApplicationException("mensagem.erro", new String[]{"gerar relatório Bem Imóvel"}, he);
		} catch (Exception e) {
			throw new ApplicationException("mensagem.erro", new String[]{"gerar relatório Bem Imóvel "}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (Exception e) {
				log4j.error("Erro ao Fechar Conexao com o Hibernate: ", e);				
			}
		}
		
		return listaRelatorio;
			
	}
	
	public Integer baixarBemImovel(Integer codBemImovel) throws ApplicationException {

		try{
		Session session = HibernateUtil.currentSession();
		StringBuffer hql = new StringBuffer();
		hql.append(" UPDATE BemImovel b ")
			.append(" SET b.situacaoImovel.codSituacaoImovel = 2 ")	
			.append(" WHERE b.codBemImovel = :codBemImovel ");
		
		Query q = session.createQuery(hql.toString());
		
		
		// Seta os valores dos parâmetros
		if ( codBemImovel != null ) {
			q.setInteger("codBemImovel", codBemImovel);
		}		
			return q.list().size();

		} catch (HibernateException he) {
			throw new ApplicationException("mensagem.erro", new String[]{"baixar Bem Imóvel"}, he);
		} catch (Exception e) {
			throw new ApplicationException("mensagem.erro", new String[]{"baixar Bem Imóvel "}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (Exception e) {
				log4j.error("Erro ao Fechar Conexao com o Hibernate: ", e);				
			}
		}
	}

	/**
	 * Obter BemImovel e objetos filhos inicializados.<br>
	 * @author pialarissi
	 * @since 09/02/2008
	 * @param Integer codBemImovel
	 * @return BemImovel
	 * @throws ApplicationException
	 */
	public ImpressaoBemImovelDTO obterImpressaoBemImovel(Integer codBemImovel) throws ApplicationException {
		try {
			
			Session session = HibernateUtil.currentSession();
			Criteria c = session.createCriteria(BemImovel.class)
								.add(Restrictions.eq("codBemImovel", codBemImovel))
								.setFetchMode("orgao", FetchMode.JOIN)
								.setFetchMode("classificacaoBemImovel", FetchMode.JOIN)
								.setFetchMode("situacaoLocal", FetchMode.JOIN)
								.setFetchMode("situacaoLegalCartorial", FetchMode.JOIN)
								.setFetchMode("tabelionato", FetchMode.JOIN)
								.setFetchMode("formaIncorporacao", FetchMode.JOIN)								
								.setFetchMode("situacaoImovel", FetchMode.JOIN)
								.setFetchMode("cartorio", FetchMode.JOIN)
								.setFetchMode("denominacaoImovel", FetchMode.JOIN)
								.setFetchMode("documentacaos", FetchMode.JOIN)
								.setFetchMode("edificacaos", FetchMode.JOIN);
			return (ImpressaoBemImovelDTO) c.uniqueResult();
		} catch (HibernateException he) {
			throw new ApplicationException("mensagem.erro", new String[]{"obterImpressaoBemImovel"}, he);
		} catch (Exception e) {
			throw new ApplicationException("mensagem.erro", new String[]{"obterImpressaoBemImovel "}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (Exception e) {
				log4j.error("Erro ao Fechar Conexao com o Hibernate: ", e);				
			}
		}
	}
	
	/**
	 * Obter Bens Imoveis pelo código.<br>
	 * @author oksana
	 * @since 24/03/2011
	 * @param codBemImovel
	 * @return BemImovel
	 * @throws ApplicationException
	 */
	public BemImovel obterParaMigracaoPorCodigo(Integer codBemImovel) throws ApplicationException {
		try {
			Session session = HibernateUtil.currentSession();

			StringBuffer hql = new StringBuffer();
			hql.append("SELECT b FROM BemImovel b ")
			.append(" LEFT JOIN FETCH b.confrontantes ")
			.append(" LEFT JOIN FETCH b.coordenadaUtms ")
			.append(" LEFT JOIN FETCH b.edificacaos edi ")
			.append(" LEFT JOIN FETCH edi.ocupacaos ocu ")
			.append(" LEFT JOIN FETCH edi.tipoEdificacao ")
			.append(" LEFT JOIN FETCH ocu.orgao ")
			.append(" LEFT JOIN FETCH ocu.situacaoOcupacao ")
			.append(" LEFT JOIN FETCH b.documentacaos doc ")
			.append(" LEFT JOIN FETCH b.quadras qua ")
			.append(" LEFT JOIN FETCH qua.lotes lot ")
			.append(" LEFT JOIN FETCH b.orgao ")
			.append(" WHERE b.codBemImovel = :codBemImovel ");
			Query q = session.createQuery(hql.toString());
			
			q.setInteger("codBemImovel", codBemImovel);
			
			return (BemImovel) q.uniqueResult();
		} catch (HibernateException he) {
			throw new ApplicationException("mensagem.erro", new String[]{"obter Bem Imóvel"}, he);
		} catch (Exception e) {
			throw new ApplicationException("mensagem.erro", new String[]{"obter Bem Imóvel "}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (Exception e) {
				log4j.error("Erro ao Fechar Conexao com o Hibernate: ", e);				
			}
		}
	}

	
	/**
	 * Fazer o merge dos objetos.<br>
	 * @author pozzatti
	 * @since 21/01/2009
	 * @param fornecedorDomicilioBancario : FornecedorDomicilioBancario
	 * @return DocumentacaoExigidaDados
	 * @throws ApplicationException
	 */	
	public BemImovel merge(BemImovel bemImovel) throws ApplicationException{
		try{
			log4j.debug(LOGINICIO);
			Session session = HibernateUtil.currentSession();
			bemImovel = (BemImovel) session.merge(bemImovel);
			log4j.debug(LOGFIM);
			return bemImovel;
		}catch (ApplicationException ae) {
			log4j.debug(ae.getMessage(),ae);
			throw ae;
		}catch (Exception e) {
			log4j.error(e.getMessage());
			throw new ApplicationException("mensagem.erro.9001", new String[]{BemImovelHibernateDAO.class.getSimpleName().concat(".merge()")}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				HibernateUtil.closeSession();	
			} catch (Exception e) {
				log4j.error(LOGERROFECHARSESSAO, e);
			}
		}
	}


	@Override
	public BemImovel salvarBemImovel(BemImovel bemImovel) throws ApplicationException {
		BemImovel retorno = new BemImovel();
		// Validando parâmetro
		if (bemImovel == null){
			throw new ApplicationException("mensagem.erro.9002", new String[]{"BemImovelHibernateDAO.salvarBemImovel()"}, ApplicationException.ICON_ERRO);
		}
		
		try {
			log4j.debug("Obtendo sessao corrente...");
			Session session = HibernateUtil.currentSession();
			session.clear();
			Serializable chave = session.save(bemImovel);
			session.flush();
			log4j.info("SALVAR:" + bemImovel.getClass().getName() + " salvo.");
			retorno = (BemImovel) session.get(BemImovel.class, chave);
			log4j.info("GET:" + bemImovel.getClass().getName() + " q acabou de ser salvo.");
						
		} catch (ConstraintViolationException cve) {
			log4j.error("BemImovelHibernateDAO.salvarBemImovel()" + cve);
			throw new ApplicationException("mensagem.erro.9003", new String[]{bemImovel.getClass().getSimpleName()}, ApplicationException.ICON_AVISO);
		} catch (HibernateException he) {
			log4j.error("BemImovelHibernateDAO.salvarBemImovel()" + he);
			throw new ApplicationException("mensagem.erro.9001", new String[]{"BemImovelHibernateDAO.salvarBemImovel()"}, he, ApplicationException.ICON_ERRO);
		} catch (Exception e) {
			log4j.error("BemImovelHibernateDAO.salvarBemImovel()" + e);
			throw new ApplicationException("mensagem.erro.9001",new String[]{"BemImovelHibernateDAO.salvarBemImovel()"}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				log4j.debug("Fechando sessao corrente.");
				HibernateUtil.closeSession();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.9003", new String[]{bemImovel.getClass().getSimpleName()}, ApplicationException.ICON_AVISO);
			}
		}
		
		return retorno;
	}

	public Integer obterQuantidadeListaSimplificado(Integer qtdPagina, Integer numPagina, BemImovelPesquisaDTO bemDTO) throws ApplicationException {
		try {
			Session session = HibernateUtil.currentSession();
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append(" SELECT DISTINCT bemImovel FROM BemImovel bemImovel ");
			//Incluido para adequar a disponibilizacao para orgaos
			sbQuery.append(	" left join fetch bemImovel.ocupacaosTerreno ot") ;
			sbQuery.append(	" left join fetch ot.orgao orgTer") ;
			sbQuery.append(	" left join fetch bemImovel.edificacaos edi");
			sbQuery.append(" LEFT JOIN FETCH edi.ocupacaos ocu ");
			sbQuery.append(" LEFT JOIN FETCH ocu.orgao orgOcu ");
			sbQuery.append(	" left join fetch bemImovel.listaTransferencia transferencia") ;
			sbQuery.append(	" left join fetch transferencia.orgaoCessionario orgaoTrans") ;
			//
			sbQuery.append(" WHERE 1=1 ");
			Integer retorno = null;
			if (bemDTO.getCodInstituicao() != null){
				sbQuery.append(" AND bemImovel.instituicao.codInstituicao = :codInstituicao");
			}
			if (bemDTO.getCodBemImovel() != null){
				sbQuery.append(" AND bemImovel.codBemImovel = :codBemImovel");
				//Incluido para adequar a disponibilizacao para orgaos
				if (bemDTO.getIndOperadorOrgao() != null && bemDTO.getIndOperadorOrgao()){
					sbQuery.append(" AND (");
					sbQuery.append(" (orgTer.codOrgao IN (:listaCodOrgao)) OR");
					sbQuery.append(" (orgOcu.codOrgao IN (:listaCodOrgao)) OR");
					sbQuery.append(" (transferencia.statusTermo.codStatusTermo = :codStatusTermo AND transferencia.orgaoCessionario.codOrgao IN (:listaCodOrgao))");
					sbQuery.append(")");
				}
				//
				Query query = session.createQuery(sbQuery.toString());
				query.setInteger("codBemImovel", bemDTO.getCodBemImovel());
				//Incluido para adequar a disponibilizacao para orgaos
				if (bemDTO.getIndOperadorOrgao() != null && bemDTO.getIndOperadorOrgao()){
					query.setInteger("codStatusTermo", statusTermo.VIGENTE.getIndex());
					List<Integer> listaCodOrgao = new ArrayList<Integer>();
					for (Orgao o : bemDTO.getListaOrgao()){
						listaCodOrgao.add(o.getCodOrgao());
					}
					query.setParameterList("listaCodOrgao", listaCodOrgao);
				}
				//
				List<BemImovel> lista =  (List<BemImovel>)query.list();
				retorno = lista.size();
			}else{
				if (bemDTO.getUf() != null) {
					sbQuery.append(" AND bemImovel.uf = :uf");
				}
				if (bemDTO.getCodMunicipio() != null) {
					sbQuery.append(" AND bemImovel.codMunicipio = :codMunicipio");
				}
				if (bemDTO.getCodDenominacaoImovel() != null) {
					sbQuery.append(" AND bemImovel.denominacaoImovel.codDenominacaoImovel = :codDenominacaoImovel");
				}
				if (bemDTO.getOcupante().equals("1") || bemDTO.getOcupante().equals("2") ){
					if (bemDTO.getOcupante().equals("1")){
						sbQuery.append(" AND bemImovel.somenteTerreno = 'S'");
					}else{
						sbQuery.append(" AND bemImovel.somenteTerreno = 'N'");
					}
				}
				if (bemDTO.getObservacao() != null){
					sbQuery.append(" AND UPPER(bemImovel.observacoesMigracao) LIKE :observacao ");
				}
				if (bemDTO.getIndOperadorOrgao() != null && bemDTO.getIndOperadorOrgao()){
					sbQuery.append(" AND (");
					sbQuery.append(" (orgTer.codOrgao IN (:listaCodOrgao)) OR");
					sbQuery.append(" (orgOcu.codOrgao IN (:listaCodOrgao)) OR");
					sbQuery.append(" (transferencia.statusTermo.codStatusTermo = :codStatusTermo AND transferencia.orgaoCessionario.codOrgao IN (:listaCodOrgao))");
					sbQuery.append(")");
				}
				
				Query query = session.createQuery(sbQuery.toString());
				
				if (bemDTO.getUf()  != null) {
					query.setString("uf", bemDTO.getUf());
				}
				if (bemDTO.getCodMunicipio()  != null) {
					query.setInteger("codMunicipio", bemDTO.getCodMunicipio());
				}
				if (bemDTO.getCodDenominacaoImovel()  != null) {
					query.setInteger("codDenominacaoImovel", bemDTO.getCodDenominacaoImovel());
				}
				if (bemDTO.getObservacao()  != null) {
					query.setString("observacao", "%".concat(bemDTO.getObservacao().toUpperCase()).concat("%"));
				}
				//Incluido para adequar a disponibilizacao para orgaos
				if (bemDTO.getIndOperadorOrgao() != null && bemDTO.getIndOperadorOrgao()){
					query.setInteger("codStatusTermo", statusTermo.VIGENTE.getIndex());
					List<Integer> listaCodOrgao = new ArrayList<Integer>();
					for (Orgao o : bemDTO.getListaOrgao()){
						listaCodOrgao.add(o.getCodOrgao());
					}
					query.setParameterList("listaCodOrgao", listaCodOrgao);
				}
				if (bemDTO.getCodInstituicao() != null){
					query.setInteger("codInstituicao", bemDTO.getCodInstituicao());
				}
				//
				List<BemImovel> lista =  (List<BemImovel>)query.list();
				retorno = lista.size();
			}
			
			return retorno.intValue();
		} catch (HibernateException he) {
			throw new ApplicationException("mensagem.erro.banco.obterQuantidadeListaSimplificado", he);
		} catch (Exception e) {
			throw new ApplicationException("mensagem.erro.banco.obterQuantidadeListaSimplificado", e, ApplicationException.ICON_ERRO);
		}finally {
			try {
				HibernateUtil.closeSession();
			} catch (Exception e) {
				log4j.error("Erro ao Fechar Conexao com o Hibernate: ", e);				
			}
		}
	}

	@SuppressWarnings("unchecked")

	public Collection<BemImovel> listarSimplificado(Integer qtdPagina, Integer numPagina, BemImovelPesquisaDTO bemDTO ) throws ApplicationException {
		try {
			Session session = HibernateUtil.currentSession();
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append(" SELECT bemImovel FROM BemImovel bemImovel ");
			sbQuery.append(" LEFT JOIN FETCH bemImovel.classificacaoBemImovel classificacaoBemImovel ");
			sbQuery.append(" LEFT JOIN FETCH bemImovel.edificacaos edi ");
			sbQuery.append(" LEFT JOIN FETCH bemImovel.orgao orgao ");
			sbQuery.append(" LEFT JOIN FETCH edi.ocupacaos ocu ");
			sbQuery.append(" LEFT JOIN FETCH ocu.orgao orgOcu ");
			sbQuery.append(" LEFT JOIN FETCH edi.tipoConstrucao tipoConstrucao ");
			//Incluido para adequar a disponibilizacao para orgaos
			sbQuery.append(	" left join fetch bemImovel.ocupacaosTerreno ot") ;
			sbQuery.append(	" left join fetch ot.orgao orgTer") ;	
			sbQuery.append(	" left join fetch bemImovel.listaTransferencia transferencia") ;
			sbQuery.append(	" left join fetch transferencia.orgaoCessionario orgaoTrans") ;
			//
			sbQuery.append(" WHERE 1=1");
			if (bemDTO.getCodInstituicao() != null){
				sbQuery.append(" AND bemImovel.instituicao.codInstituicao = :codInstituicao");
			}
			if (bemDTO.getCodBemImovel() != null){
				sbQuery.append(" AND bemImovel.codBemImovel = :codBemImovel");
				//Incluido para adequar a disponibilizacao para orgaos
				if (bemDTO.getIndOperadorOrgao() != null && bemDTO.getIndOperadorOrgao()){
					sbQuery.append("AND (");
					sbQuery.append(" (orgTer.codOrgao IN (:listaCodOrgao)) OR");
					sbQuery.append(" (orgOcu.codOrgao IN (:listaCodOrgao)) OR");
					sbQuery.append(" (transferencia.statusTermo.codStatusTermo = :codStatusTermo AND transferencia.orgaoCessionario.codOrgao IN (:listaCodOrgao))");
					sbQuery.append(")");
				}
				//
				Query query = session.createQuery(sbQuery.toString());
				query.setInteger("codBemImovel", bemDTO.getCodBemImovel());
				//Incluido para adequar a disponibilizacao para orgaos
				if (bemDTO.getIndOperadorOrgao() != null && bemDTO.getIndOperadorOrgao()){
					query.setInteger("codStatusTermo", statusTermo.VIGENTE.getIndex());
					List<Integer> listaCodOrgao = new ArrayList<Integer>();
					for (Orgao o : bemDTO.getListaOrgao()){
						listaCodOrgao.add(o.getCodOrgao());
					}
					query.setParameterList("listaCodOrgao", listaCodOrgao);
				}
				//
				if(qtdPagina != null && numPagina != null) {
					query.setMaxResults(qtdPagina.intValue());
					query.setFirstResult((numPagina.intValue() -1) * qtdPagina.intValue());
				}
				
				return (List<BemImovel>)query.list();
			}else{	
				if (bemDTO.getUf() != null) {
					sbQuery.append(" AND bemImovel.uf = :uf");
				}
				if (bemDTO.getCodMunicipio() != null) {
					sbQuery.append(" AND bemImovel.codMunicipio = :codMunicipio");
				}
				if (bemDTO.getCodDenominacaoImovel() != null) {
					sbQuery.append(" AND bemImovel.denominacaoImovel.codDenominacaoImovel = :codDenominacaoImovel");
				}
				if (bemDTO.getOcupante().equals("1") || bemDTO.getOcupante().equals("2") ){
					if (bemDTO.getOcupante().equals("1")){
						sbQuery.append(" AND bemImovel.somenteTerreno = 'S'");
					}else{
						sbQuery.append(" AND bemImovel.somenteTerreno = 'N'");
					}
				}
				if (bemDTO.getObservacao() != null){
					sbQuery.append(" AND UPPER(bemImovel.observacoesMigracao) like :observacao ");
				}
				//Incluido para adequar a disponibilizacao para orgaos
				if (bemDTO.getIndOperadorOrgao() != null && bemDTO.getIndOperadorOrgao()){
					sbQuery.append(" AND (");
					sbQuery.append(" (orgTer.codOrgao IN (:listaCodOrgao)) OR");
					sbQuery.append(" (orgOcu.codOrgao IN (:listaCodOrgao)) OR");
					sbQuery.append(" (transferencia.statusTermo.codStatusTermo = :codStatusTermo AND transferencia.orgaoCessionario.codOrgao IN (:listaCodOrgao))");
					sbQuery.append(")");
				}
				//
				sbQuery.append(" ORDER BY bemImovel.codBemImovel ");
				Query query = session.createQuery(sbQuery.toString());
				
				if (bemDTO.getUf()  != null) {
					query.setString("uf", bemDTO.getUf());
				}
				if (bemDTO.getCodMunicipio()  != null) {
					query.setInteger("codMunicipio", bemDTO.getCodMunicipio());
				}
				if (bemDTO.getCodDenominacaoImovel()  != null) {
					query.setInteger("codDenominacaoImovel", bemDTO.getCodDenominacaoImovel());
				}
				if (bemDTO.getObservacao()  != null) {
					query.setString("observacao", "%".concat(bemDTO.getObservacao().toUpperCase()).concat("%"));
				}
				//Incluido para adequar a disponibilizacao para orgaos
				if (bemDTO.getIndOperadorOrgao() != null && bemDTO.getIndOperadorOrgao()){
					query.setInteger("codStatusTermo", statusTermo.VIGENTE.getIndex());
					List<Integer> listaCodOrgao = new ArrayList<Integer>();
					for (Orgao o : bemDTO.getListaOrgao()){
						listaCodOrgao.add(o.getCodOrgao());
					}
					query.setParameterList("listaCodOrgao", listaCodOrgao);
				}
				//
				if (bemDTO.getCodInstituicao() != null){
					query.setInteger("codInstituicao", bemDTO.getCodInstituicao());
				}
				if(qtdPagina != null && numPagina != null) {
					query.setMaxResults(qtdPagina.intValue());
					query.setFirstResult((numPagina.intValue() -1) * qtdPagina.intValue());
				}
				
				return (List<BemImovel>)query.list();
			}			


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


	@SuppressWarnings("unchecked")
	public Collection<BemImovelVistoriaDTO> listarBemImovelVistoriaDTO(ParametroAgenda parametroAgenda) throws ApplicationException {
		Collection<BemImovelVistoriaDTO> result = new ArrayList<BemImovelVistoriaDTO>();
		try {
			Session session = HibernateUtil.currentSession();
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append(" select ");
			sbQuery.append(" b.cod_bem_imovel, d.descricao, (select MAX(data_vistoria) from tb_vistoria v1 where v1.cod_bem_imovel = b.cod_bem_imovel) as dataUltimaVistoria ");
			sbQuery.append(" from tb_bem_imovel b");
			sbQuery.append(" left join tb_denominacao_imovel d using(cod_denominacao_imovel)");
			sbQuery.append(" where 1=1 ");
			sbQuery.append(" AND b.cod_instituicao = ".concat(parametroAgenda.getInstituicao().getCodInstituicao().toString()));
			sbQuery.append(" AND b.cod_bem_imovel  NOT IN (");
			sbQuery.append(" select v.cod_bem_imovel from tb_vistoria v");
			sbQuery.append(" where v.cod_bem_imovel = b.cod_bem_imovel and v.cod_status_vistoria = 2");
			sbQuery.append(" and (data_vistoria +");
			sbQuery.append(parametroAgenda.getNumeroDiasVencimentoVistoria().toString());
			sbQuery.append(") > current_date");
			sbQuery.append(" ) order by b.cod_bem_imovel");

			Query query = session.createSQLQuery(sbQuery.toString());
			List<Object[]> retornoQuery = (ArrayList<Object[]>)query.list();
			if (retornoQuery != null){
				for (Iterator<Object[]> iter = retornoQuery.iterator(); iter.hasNext();) {
					Object[] element = (Object[]) iter.next();
					Integer codBemImovel = (Integer)element[0];
					String denominacao = "";
					Date dataUltimaAvaliacao = null;
					if (element[1] != null){
						denominacao = (String)element[1];
					}
					if (element[2] != null){
						dataUltimaAvaliacao = (Date)element[2];
					}
					BemImovelVistoriaDTO bemImovelVistoriaDTO = new BemImovelVistoriaDTO(codBemImovel, denominacao, dataUltimaAvaliacao);
					result.add(bemImovelVistoriaDTO);
				}				
			}
			return result;

		} catch (HibernateException he) {
			log4j.error("Erro:", he);
			throw new ApplicationException("ERRO.201",  new String[]{"ao listar Bem Imóvel para Vistoria"});
		} catch (Exception e) {
			log4j.error("Erro:", e);
			throw new ApplicationException("ERRO.201",  new String[]{"ao listar Bem Imóvel para Vistoria"}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				HibernateUtil.closeSession();
			}catch (Exception e) {
				log4j.error("Problema ao tentar fechar conexao com o banco de dados: listarBemImovelVistoriaDTO", e);
			}		
		}
	}

	/**
	 * Obter BemImovel e objetos filhos inicializados.<br>
	 * @author pialarissi
	 * @since 09/02/2008
	 * @param Integer codBemImovel
	 * @return BemImovel
	 * @throws ApplicationException
	 */
	public BemImovel obterSimplificado(Integer codBemImovel) throws ApplicationException {
		try {
			Session session = HibernateUtil.currentSession();
			StringBuffer sbQuery = new StringBuffer();

			sbQuery.append(" SELECT bemImovel FROM BemImovel bemImovel ");
			sbQuery.append(" LEFT JOIN FETCH bemImovel.orgao orgao ");
			sbQuery.append(" LEFT JOIN FETCH bemImovel.classificacaoBemImovel classificacaoBemImovel ");
			sbQuery.append(" LEFT JOIN FETCH bemImovel.situacaoLegalCartorial situacaoLegalCartorial ");
			sbQuery.append(" LEFT JOIN FETCH bemImovel.situacaoImovel situacaoImovel ");
			sbQuery.append(" WHERE bemImovel.codBemImovel = :codBemImovel");

			Query query = session.createQuery(sbQuery.toString());
			query.setInteger("codBemImovel", codBemImovel);
			
			return (BemImovel) query.uniqueResult();
				
		} catch (HibernateException he) {
			throw new ApplicationException("mensagem.erro", new String[]{"obterExibir"}, he);
		} catch (Exception e) {
			throw new ApplicationException("mensagem.erro", new String[]{"obterExibir "}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (Exception e) {
				log4j.error("Erro ao Fechar Conexao com o Hibernate: ", e);				
			}
		}
	}


	@Override
	public BemImovel listarRelatorioBemImovel(ImpressaoBemImovelDTO ibiDTO, Integer codBemImovel) throws ApplicationException {
		BemImovel coll = new BemImovel();
		
		try {
			Session session = HibernateUtil.currentSession();

			StringBuffer hql = new StringBuffer();
			hql.append("SELECT DISTINCT b FROM BemImovel b ");
			hql.append(" LEFT JOIN FETCH b.classificacaoBemImovel ");	

			if (ibiDTO.getFiltroLeis().equalsIgnoreCase("T")) {
				hql.append(" LEFT JOIN FETCH b.leiBemImovels lb ");
				hql.append(" LEFT JOIN FETCH lb.tipoLeiBemImovel ");
			}
			if (ibiDTO.getFiltroConfrontantes().equalsIgnoreCase("T")) {
				hql.append(" LEFT JOIN FETCH b.confrontantes ");
			}
			if (ibiDTO.getFiltroAvaliacoes().equalsIgnoreCase("T")) {
				hql.append(" LEFT JOIN FETCH b.avaliacaos ");
			}
			if (ibiDTO.getFiltroCoordenadasUtm().equalsIgnoreCase("T")) {
				hql.append(" LEFT JOIN FETCH b.coordenadaUtms ");
			}
			if (ibiDTO.getFiltroEdificacoes().equalsIgnoreCase("T")) {
				hql.append(" LEFT JOIN FETCH b.edificacaos edi ");
				hql.append(" LEFT JOIN FETCH edi.ocupacaos ocu ");
				hql.append(" LEFT JOIN FETCH ocu.situacaoOcupacao ");
				hql.append(" LEFT JOIN FETCH ocu.orgao orgOcu ");
				hql.append(" LEFT JOIN FETCH edi.tipoEdificacao ");
				hql.append(" LEFT JOIN FETCH edi.tipoConstrucao ");
			}
			if (ibiDTO.getFiltroDocumentacoesNotificacao().equalsIgnoreCase("T") ||ibiDTO.getFiltroDocumentacoesSemNotificacao().equalsIgnoreCase("T") || 
					ibiDTO.getFiltroOcorrencias().equalsIgnoreCase("T")) {
				hql.append(" LEFT JOIN FETCH b.documentacaos doc ");
			}
			if (ibiDTO.getFiltroDocumentacoesNotificacao().equalsIgnoreCase("T") ||ibiDTO.getFiltroDocumentacoesSemNotificacao().equalsIgnoreCase("T")) {
				hql.append(" LEFT JOIN FETCH doc.notificacaos ");
			}
			if (ibiDTO.getFiltroDocumentacoesNotificacao().equalsIgnoreCase("T") ||ibiDTO.getFiltroDocumentacoesSemNotificacao().equalsIgnoreCase("T") || 
					ibiDTO.getFiltroOcorrencias().equalsIgnoreCase("T")) {
				hql.append(" LEFT JOIN FETCH doc.ocorrenciaDocumentacaos ");
				hql.append(" LEFT JOIN FETCH doc.tabelionato ");
				hql.append(" LEFT JOIN FETCH doc.cartorio ");
				hql.append(" LEFT JOIN FETCH doc.tipoDocumentacao ");
			}
			
			if (ibiDTO.getFiltroQuadra().equalsIgnoreCase("T")) {
				hql.append(" LEFT JOIN FETCH b.quadras qua ");
				hql.append(" LEFT JOIN FETCH qua.lotes lot ");
			}
			hql.append(" LEFT JOIN FETCH b.orgao ");
			hql.append(" LEFT JOIN FETCH b.situacaoLegalCartorial ");
			hql.append(" LEFT JOIN FETCH b.formaIncorporacao ");
			hql.append(" LEFT JOIN FETCH b.situacaoImovel ");
			hql.append(" LEFT JOIN FETCH b.denominacaoImovel ");

			hql.append("WHERE b.codBemImovel =:codBemImovel ");
			if (ibiDTO.getFiltroDocumentacoesNotificacao().equalsIgnoreCase("T") || ibiDTO.getFiltroDocumentacoesSemNotificacao().equalsIgnoreCase("T")) {
				hql.append(" AND doc.ocorrenciaDocumentacaos is empty ");
			}
			if (ibiDTO.getFiltroDocumentacoesSemNotificacao().equalsIgnoreCase("T")) {
				hql.append(" AND doc.notificacaos is empty ");
			}
			
			Query q = session.createQuery(hql.toString());
			q.setInteger("codBemImovel", codBemImovel);

			coll = (BemImovel) q.uniqueResult();
			
			if (ibiDTO.getFiltroOcupacaoTerreno().equalsIgnoreCase("T")) {
				Set<Ocupacao> listOcupacao = new HashSet<Ocupacao>(); 
				listOcupacao.addAll(CadastroFacade.listarOcupacaoPorBemImovelTerreno(coll.getCodBemImovel()));
				coll.setOcupacaosTerreno(listOcupacao);
			}
			
		} catch (HibernateException he) {
			throw new ApplicationException("mensagem.erro", new String[]{"gerar relatório Bem Imóvel"}, he);
		} catch (Exception e) {
			throw new ApplicationException("mensagem.erro", new String[]{"gerar relatório Bem Imóvel "}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (Exception e) {
				log4j.error("Erro ao Fechar Conexao com o Hibernate: ", e);				
			}
		}
		
		return coll;
	}
	

	
	public BemImovel obterParaUsuarioOrgao(Integer codBemImovel, List<Integer> listaCodOrgao) throws ApplicationException {
		try {
			Session session = HibernateUtil.currentSession();
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append(" SELECT DISTINCT bemImovel FROM BemImovel bemImovel ");
			//Incluido para adequar a disponibilizacao para orgaos
			sbQuery.append(	" left join fetch bemImovel.ocupacaosTerreno ot") ;
			sbQuery.append(	" left join fetch ot.orgao orgTer") ;
			sbQuery.append(	" left join fetch bemImovel.edificacaos edi");
			sbQuery.append(" LEFT JOIN FETCH edi.ocupacaos ocu ");
			sbQuery.append(" LEFT JOIN FETCH ocu.orgao orgOcu ");
			sbQuery.append(	" left join fetch bemImovel.listaTransferencia transferencia") ;
			sbQuery.append(	" left join fetch transferencia.orgaoCessionario orgaoTrans") ;
			//
			sbQuery.append(" WHERE 1=1 ");
			sbQuery.append(" AND bemImovel.codBemImovel = :codBemImovel");
			//Incluido para adequar a disponibilizacao para orgaos
			sbQuery.append(" AND (");
			sbQuery.append(" (orgTer.codOrgao IN (:listaCodOrgao)) OR");
			sbQuery.append(" (orgOcu.codOrgao IN (:listaCodOrgao)) OR");
			sbQuery.append(" (transferencia.statusTermo.codStatusTermo = :codStatusTermo AND transferencia.orgaoCessionario.codOrgao IN (:listaCodOrgao))");
			sbQuery.append(")");
			
			Query query = session.createQuery(sbQuery.toString());
			query.setInteger("codBemImovel", codBemImovel);
			//Incluido para adequar a disponibilizacao para orgaos
			query.setInteger("codStatusTermo", statusTermo.VIGENTE.getIndex());
			query.setParameterList("listaCodOrgao", listaCodOrgao);
			//
			return  (BemImovel)query.uniqueResult();
		} catch (HibernateException he) {
			throw new ApplicationException("mensagem.erro.banco.obterParaUsuarioOrgao", he);
		} catch (Exception e) {
			throw new ApplicationException("mensagem.erro.banco.obterParaUsuarioOrgao", e, ApplicationException.ICON_ERRO);
		}finally {
			try {
				HibernateUtil.closeSession();
			} catch (Exception e) {
				log4j.error("Erro ao Fechar Conexao com o Hibernate: ", e);				
			}
		}
	}
	
	/**
	 * Obter BemImovel e objetos filhos inicializados.<br>
	 * @author pialarissi
	 * @since 09/02/2008
	 * @param Integer codBemImovel
	 * @return BemImovel
	 * @throws ApplicationException
	 */
	public BemImovel obterExibirPorInstituicao(Integer nrBemImovel, Integer codInstituicao) throws ApplicationException {
		try {
			Session session = HibernateUtil.currentSession();
			Criteria c = session.createCriteria(BemImovel.class)
								.add(Restrictions.eq("nrBemImovel", nrBemImovel))
								.add(Restrictions.eq("instituicao.codInstituicao", codInstituicao))
								.setFetchMode("orgao", FetchMode.JOIN)
								.setFetchMode("classificacaoBemImovel", FetchMode.JOIN)
								.setFetchMode("situacaoLocal", FetchMode.JOIN)
								.setFetchMode("situacaoLegalCartorial", FetchMode.JOIN)
								.setFetchMode("tabelionato", FetchMode.JOIN)
								.setFetchMode("formaIncorporacao", FetchMode.JOIN)								
								.setFetchMode("situacaoImovel", FetchMode.JOIN)
								.setFetchMode("cartorio", FetchMode.JOIN)
								.setFetchMode("denominacaoImovel", FetchMode.JOIN)
								.setFetchMode("avaliacaos", FetchMode.JOIN)
								.setFetchMode("coordenadaUtms", FetchMode.JOIN);
			return (BemImovel) c.uniqueResult();
		} catch (HibernateException he) {
			throw new ApplicationException("mensagem.erro", new String[]{"obterExibir"}, he);
		} catch (Exception e) {
			throw new ApplicationException("mensagem.erro", new String[]{"obterExibir "}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (Exception e) {
				log4j.error("Erro ao Fechar Conexao com o Hibernate: ", e);				
			}
		}
	}

}
