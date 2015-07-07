package gov.pr.celepar.abi.dao.implementation;

import gov.pr.celepar.abi.dao.EdificacaoDAO;
import gov.pr.celepar.abi.dto.EdificacaoDTO;
import gov.pr.celepar.abi.dto.EdificacaoExibirBemImovelDTO;
import gov.pr.celepar.abi.dto.FiltroRelatorioEdificacaoDTO;
import gov.pr.celepar.abi.dto.RelatorioEdificacaoDTO;
import gov.pr.celepar.abi.facade.CadastroFacade;
import gov.pr.celepar.abi.pojo.Edificacao;
import gov.pr.celepar.abi.pojo.Lote;
import gov.pr.celepar.abi.pojo.Ocupacao;
import gov.pr.celepar.abi.util.Dominios.statusTermo;
import gov.pr.celepar.abi.util.Util;
import gov.pr.celepar.framework.database.GenericHibernateDAO;
import gov.pr.celepar.framework.database.HibernateUtil;
import gov.pr.celepar.framework.exception.ApplicationException;
import gov.pr.celepar.framework.util.Pagina;
import gov.pr.celepar.framework.util.StringUtil;
import gov.pr.celepar.framework.util.Valores;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

/**
 * Classe de manipulação de objetos da classe Edificacao.
 * 
 * @author CodeGenerator - Esta classe foi gerada automaticamente
 * @since 1.0
 * @version 1.0, 24/12/2009 
 *
 */
public class EdificacaoHibernateDAO extends GenericHibernateDAO<Edificacao, Integer> implements EdificacaoDAO {

	private static Logger log4j = Logger.getLogger(EdificacaoHibernateDAO.class);
	
	@SuppressWarnings("unchecked")
	public Collection<EdificacaoDTO> listarComRelacionamentos(Integer codBemImovel, Integer qtdPagina, Integer numPagina) throws ApplicationException {
		Collection<EdificacaoDTO> coll = new ArrayList<EdificacaoDTO>();
		Collection<Edificacao> lista = null;

		try {
			Session session = HibernateUtil.currentSession();

			StringBuffer query = new StringBuffer();
			query.append("from Edificacao e ")
			.append("left join fetch e.tipoConstrucao ")
			.append("left join fetch e.tipoEdificacao ")
			.append("left join fetch e.bemImovel ")
			.append("left join fetch e.ocupacaos ocupacaos ")
			.append("left join fetch ocupacaos.orgao ")
			.append("where e.bemImovel.codBemImovel=:codBemImovel");
			Query q = session.createQuery(query.toString() );
			q.setInteger("codBemImovel", codBemImovel);
					
			if (qtdPagina != null && numPagina != null) {
				q.setMaxResults(qtdPagina.intValue());
				q.setFirstResult( ((numPagina.intValue()-1) * qtdPagina.intValue()));
			}
			lista = q.list();
			
			if(lista != null){
				for (Edificacao edificacao : lista) {
					EdificacaoDTO dto = new EdificacaoDTO();
					dto.setCodEdificacao(edificacao.getCodEdificacao() );
					dto.setTipoEdificacao(edificacao.getTipoEdificacao().getDescricao() );
					dto.setTipoConstrucao(edificacao.getTipoConstrucao().getDescricao() );
					dto.setEspecificacao(edificacao.getEspecificacao() );
					dto.setAreaConstruidaDisponivelMetroQuadrado(edificacao.getAreaConstruida());
					
					Collection<Ocupacao> ocups = edificacao.getOcupacaos();
					if(ocups!=null){
						StringBuilder sb = new StringBuilder();				
						for (Ocupacao ocup : edificacao.getOcupacaos()) {
							if(sb.length() != 0 ){
								sb.append(", ");
							}
							sb.append(ocup.getOrgao().getSigla()).append(" - ");
							sb.append(ocup.getDescricao());
							if (ocup.getOcupacaoMetroQuadrado() != null && ocup.getOcupacaoMetroQuadrado().intValue() > 0) {
								sb.append(" - ");
								sb.append(Valores.formatarParaDecimal(ocup.getOcupacaoMetroQuadrado(), 2)).append(" m²");
							}
							sb.append(".");
						}
						dto.setOcupacao(sb.toString());
					}
					coll.add(dto);
				}
			}

		} catch (HibernateException he) {
			throw new ApplicationException("ERRO.201",  new String[]{"ao listar Edificação"});
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201",  new String[]{"ao listar Edificação"}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				HibernateUtil.closeSession();
			}catch (Exception e) {
				log4j.error("Problema ao tentar fechar conexao com o banco de dados: listarEdificacaoComRelacionamentos", e);
			}		
		}
		
		return coll;
	}
	

	public Edificacao obter(Integer codEdificacao)throws ApplicationException {
		Edificacao edificacao = null;
		try{
			Session session = HibernateUtil.currentSession();
			Criteria c= session.createCriteria(Edificacao.class)
				.add(Restrictions.eq("codEdificacao", codEdificacao))
				.setFetchMode("lotes", FetchMode.JOIN)
				.setFetchMode("documentacaos", FetchMode.JOIN)
				.setFetchMode("bemImovel", FetchMode.JOIN)
				.setFetchMode("bemImovel.instituicao", FetchMode.JOIN);
			edificacao=(Edificacao)c.uniqueResult();
			

		} catch (HibernateException he) {
			throw new ApplicationException("ERRO.201",  new String[]{"ao obter Edificação"});
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201",  new String[]{"ao obter Edificação"}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				HibernateUtil.closeSession();
			}catch (Exception e) {
				log4j.error("Problema ao tentar fechar conexao com o banco de dados: obterEdificacaoComRelacionamentos", e);
			}		
		}
		return edificacao;
	}
	
	
	public Edificacao obterExibir(Integer codEdificacao)throws ApplicationException {
		Edificacao edificacao = null;
		try{
			Session session = HibernateUtil.currentSession();
			Criteria c= session.createCriteria(Edificacao.class)
				.add(Restrictions.eq("codEdificacao", codEdificacao))
				.setFetchMode("lotes", FetchMode.JOIN)
				.setFetchMode("tipoEdificacao", FetchMode.JOIN)
				.setFetchMode("situacaoOcupacao", FetchMode.JOIN)
				.setFetchMode("tipoConstrucao", FetchMode.JOIN)
				.setFetchMode("bemImovel", FetchMode.JOIN);
			edificacao=(Edificacao)c.uniqueResult();
			

		} catch (HibernateException he) {
			throw new ApplicationException("ERRO.201",  new String[]{"ao obter Edificação"});
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201",  new String[]{"ao obter Edificação"}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				HibernateUtil.closeSession();
			}catch (Exception e) {
				log4j.error("Problema ao tentar fechar conexao com o banco de dados: obterEdificacaoComRelacionamentos", e);
			}		
		}
		return edificacao;
	}
	
	
	@SuppressWarnings("unchecked")
	public Collection<EdificacaoExibirBemImovelDTO> listarPorBemImovelExibir(Integer codBemImovel) throws ApplicationException {
		Collection<EdificacaoExibirBemImovelDTO> coll = new ArrayList<EdificacaoExibirBemImovelDTO>();
		Collection<Edificacao> lista = null;


		try {
			Session session = HibernateUtil.currentSession();
			StringBuffer hql = new StringBuffer();
			hql.append(" FROM Edificacao e ")
			.append("  INNER JOIN FETCH e.tipoConstrucao ")
			.append("  INNER JOIN FETCH e.tipoEdificacao ")
			.append("  LETF JOIN FETCH e.lotes ")
			.append(" WHERE e.bemImovel.codBemImovel = :codBemImovel");

			Query q = session.createQuery(hql.toString());
			q.setInteger("codBemImovel", codBemImovel);
			lista = q.list();
			if(lista!=null){
				for (Edificacao edificacao : lista) {
					EdificacaoExibirBemImovelDTO dto = new EdificacaoExibirBemImovelDTO();
					dto.setAreaConstruida( edificacao.getAreaConstruida() );
					dto.setBemImovel( edificacao.getBemImovel() );
					dto.setCodEdificacao( edificacao.getCodEdificacao() );
					dto.setEspecificacao( edificacao.getEspecificacao() );
					dto.setTipoConstrucao( edificacao.getTipoConstrucao().getDescricao() );
					dto.setTipoEdificacao( edificacao.getTipoEdificacao().getDescricao() );
					
					Collection<Lote> lotes = edificacao.getLotes();
					if(lotes!=null){
						StringBuilder sb = new StringBuilder();				
						for (Lote lote : edificacao.getLotes()) {
							if(sb.length() != 0 ){
								sb.append(", ");
							}
							sb.append(lote.getDescricao());
						}
						dto.setLotes( sb.toString() );
					}
					coll.add(dto);
				}
			}
		} catch (HibernateException he) {
			log4j.error("Erro:", he);
			throw new ApplicationException("ERRO.201",  new String[]{"ao listar Edificação e Lote por Bem Imóvel"});
		} catch (Exception e) {
			log4j.error("Erro:", e);
			throw new ApplicationException("ERRO.201",  new String[]{"ao listar Edificação e Lote por Bem Imóvel"}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				HibernateUtil.closeSession();
			}catch (Exception e) {
				log4j.error("Problema ao tentar fechar conexao com o banco de dados: listarLote", e);
			}		
		}

		return coll;
	}
	
	
	
	/**
	 * Obter Lista de Bens Imoveis e objetos filhos inicializados conforme parametros do relatorio.<br>
	 * @author lucianabelico
	 * @since 23/03/2010
	 * @param FiltroRelatorioEdificacaoDTO reDTO
	 * @return Collection
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public Collection<RelatorioEdificacaoDTO> listarRelatorioEdificacaoBensImoveis(FiltroRelatorioEdificacaoDTO reDTO) throws ApplicationException {
		
		Collection<RelatorioEdificacaoDTO> listaRelatorio = new ArrayList<RelatorioEdificacaoDTO>();
		Collection<Edificacao> coll = new ArrayList<Edificacao>();
		try {
			Session session = HibernateUtil.currentSession();
			
			StringBuffer hql = new StringBuffer();
		
			
			hql.append("select distinct(edificacao) from Edificacao as edificacao ")
			.append(" LEFT JOIN FETCH edificacao.bemImovel as bemImovel ")
			.append(" LEFT JOIN FETCH edificacao.tipoEdificacao as tipoEdificacao ")
			.append(" LEFT JOIN FETCH edificacao.tipoConstrucao as tipoConstrucao ");
			hql.append(" LEFT JOIN FETCH edificacao.ocupacaos as ocupacaos ");
			hql.append(" LEFT JOIN FETCH  ocupacaos.orgao orgaoOcupacao ");
			hql.append(" LEFT JOIN FETCH ocupacaos.situacaoOcupacao as situacaoOcupacao ");

			if (reDTO.getListaCodOrgao() != null){
				//Incluido para adequar a disponibilizacao para orgaos
				hql.append(" LEFT JOIN FETCH bemImovel.ocupacaosTerreno ocupacaoTerreno ");
				hql.append(" LEFT JOIN FETCH ocupacaoTerreno.orgao orgaoTerreno ");
				hql.append(" LEFT JOIN FETCH bemImovel.listaTransferencia transferencia ");
				hql.append(" LEFT JOIN FETCH transferencia.orgaoCessionario orgaoTransferencia") ;
				//
			}
			
			hql.append(" LEFT JOIN FETCH bemImovel.instituicao i ") ;
			
			hql.append(" WHERE 1=1 ");
			//filtros
			if (StringUtil.stringNotNull(reDTO.getCodSituacaoOcupacao())){
				hql.append(" AND situacaoOcupacao.codSituacaoOcupacao = :codSituacaoOcupacao ") ;
			}
			if (StringUtil.stringNotNull(reDTO.getCodOrgao())){
				hql.append(" AND orgaoOcupacao.codOrgao  = :codOrgaoOcupacao ") ;
			}
			if (reDTO.getInstituicao()!= null){
				hql.append(" AND i.codInstituicao= :codInstituicao ") ;
			}
			if (reDTO.getListaCodOrgao() != null){
				//Incluido para adequar a disponibilizacao para orgaos
				hql.append(" AND (");
				hql.append(" (orgaoOcupacao.codOrgao IN (:listaCodOrgao)) OR");
				hql.append(" (orgaoTerreno.codOrgao IN (:listaCodOrgao)) OR");
				hql.append(" (transferencia.statusTermo.codStatusTermo = :codStatusTermo AND transferencia.orgaoCessionario.codOrgao IN (:listaCodOrgao))");
				hql.append(") ");
			}
			
			if (StringUtil.stringNotNull(reDTO.getDescricaoOcupacao())){
				hql.append(" AND UPPER(ocupacao.descricao)  LIKE '%' ||:descricaoOcupacao|| '%' ");
			}
			if (StringUtil.stringNotNull(reDTO.getUf()) && !reDTO.getUf().equals("0")){
				if (StringUtil.stringNotNull(reDTO.getCodMunicipio())&& !reDTO.getCodMunicipio().equals("0")){
					hql.append(" AND bemImovel.codMunicipio  = :codMunicipio ") ;
				}else{
					hql.append(" AND bemImovel.uf  = :uf ") ;
				}
			}
			if (StringUtil.stringNotNull(reDTO.getCodTipoConstrucao())){
				hql.append(" AND tipoConstrucao.codTipoConstrucao  = :codTipoConstrucao ") ;
			}
			//filtrar varios tipos de edificacao
			if (reDTO.getListaCodTipoEdificacao() != null && !reDTO.getListaCodTipoEdificacao().isEmpty()){
				hql.append(" AND tipoEdificacao.codTipoEdificacao  IN (:listaCodTipoEdificacao) ") ;
			}
			
			if ( StringUtil.stringNotNull(reDTO.getFiltroAdministracao())) {
				hql.append(" AND bemImovel.administracao  = :administracao ") ;
			}
			if (StringUtil.stringNotNull(reDTO.getFiltroAverbacao())){
				if (reDTO.getFiltroAverbacao().equals("2")) //averbado
				{
					hql.append(" AND (NOT edificacao.dataAverbacao is null) ") ;
				}else if (reDTO.getFiltroAverbacao().equals("3")) //não averbado
				{
					hql.append(" AND (edificacao.dataAverbacao is null) ") ;
				}
			}
			//filtro por area de edificacao
			if (reDTO.getAreaMin() != null){
				hql.append(" AND (edificacao.areaConstruida >= :areaMin) ") ;
			}
			if (reDTO.getAreaMax() != null){
				hql.append(" AND (edificacao.areaConstruida <= :areaMax) ") ;
			}
			
			Query q = session.createQuery(hql.toString());
			//q.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			
			if (StringUtil.stringNotNull(reDTO.getCodSituacaoOcupacao())){
				q.setInteger("codSituacaoOcupacao", Integer.parseInt(reDTO.getCodSituacaoOcupacao()));
			}
			if (StringUtil.stringNotNull(reDTO.getCodOrgao())){
				q.setInteger("codOrgaoOcupacao", Integer.parseInt(reDTO.getCodOrgao()));
			}
			if (reDTO.getInstituicao()!= null){
				q.setInteger("codInstituicao", reDTO.getInstituicao().getCodInstituicao());

			}
			//Incluido para adequar a disponibilizacao para orgaos
			if (reDTO.getListaCodOrgao() != null){
				q.setInteger("codStatusTermo", statusTermo.VIGENTE.getIndex());
				q.setParameterList("listaCodOrgao", reDTO.getListaCodOrgao());
			}
			
			if (StringUtil.stringNotNull(reDTO.getDescricaoOcupacao())){
				q.setString("descricaoOcupacao", reDTO.getDescricaoOcupacao().toUpperCase());
			}
			if (StringUtil.stringNotNull(reDTO.getUf()) && !reDTO.getUf().equals("0")){
				if (StringUtil.stringNotNull(reDTO.getCodMunicipio())&& !reDTO.getCodMunicipio().equals("0")){
					q.setInteger("codMunicipio", Integer.parseInt(reDTO.getCodMunicipio()));
				}else{
					q.setString("uf", reDTO.getUf());
				}
			}
			if (StringUtil.stringNotNull(reDTO.getCodTipoConstrucao())){
				q.setInteger("codTipoConstrucao", Integer.parseInt(reDTO.getCodTipoConstrucao()));
			}
			if ( StringUtil.stringNotNull(reDTO.getFiltroAdministracao())) {
				q.setInteger("administracao",Integer.parseInt(reDTO.getFiltroAdministracao()));
			} 
			
			if (reDTO.getListaCodTipoEdificacao() != null && !reDTO.getListaCodTipoEdificacao().isEmpty()){
				q.setParameterList("listaCodTipoEdificacao", reDTO.getListaCodTipoEdificacao());
			}
			//filtro por area de edificacao
			if (reDTO.getAreaMin() != null){
				
				q.setFloat("areaMin", reDTO.getAreaMin());
			}
			if (reDTO.getAreaMax() != null){
				
				q.setFloat("areaMax", reDTO.getAreaMax());
			}
			coll = q.list();
			
			for (Edificacao edificacao :coll){
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				RelatorioEdificacaoDTO relatorio = new RelatorioEdificacaoDTO();
				
				relatorio.setMunicipio(edificacao.getBemImovel().getMunicipio());
				relatorio.setUf(edificacao.getBemImovel().getUf());
				relatorio.setCodBemImovel(edificacao.getBemImovel().getCodBemImovel());
				relatorio.setCodEdificacao(edificacao.getCodEdificacao());

				if(edificacao.getTipoConstrucao()!= null){
					relatorio.setTipoConstrucao(edificacao.getTipoConstrucao().getDescricao());
				}
				if (edificacao.getTipoEdificacao()!= null){
					relatorio.setTipoEdificacao(edificacao.getTipoEdificacao().getDescricao());
				}
				relatorio.setLogradouro(edificacao.getLogradouro());
				
				relatorio.setEspecificacao(edificacao.getEspecificacao());
				if (edificacao.getAreaConstruida() != null){
					relatorio.setAreaConstruida(Valores.formatarParaDecimal(edificacao.getAreaConstruida(), 2));
				}
				if (edificacao.getAreaUtilizada()!= null){
					relatorio.setAreaUtilizada(Valores.formatarParaDecimal(edificacao.getAreaUtilizada(), 2));
				}
				if(edificacao.getDataAverbacao()!=null){
					relatorio.setDataAverbacao(sdf.format(edificacao.getDataAverbacao()));
				}
				if(StringUtil.stringNotNull(reDTO.getIncluirOcupacoes())&& reDTO.getIncluirOcupacoes().equals("1")){ //com ocupacoes
					if (edificacao.getOcupacaos()!= null){
						relatorio.setListaOcupacao(new ArrayList<Ocupacao>(edificacao.getOcupacaos()));
					}
				}
				relatorio.setInstituicao(edificacao.getBemImovel().getInstituicao());
				relatorio.setNrBemImovel(edificacao.getBemImovel().getNrBemImovel());
				listaRelatorio.add(relatorio);

			}

			
		} catch (HibernateException he) {
			throw new ApplicationException("mensagem.erro", new String[]{"gerar relatório Edificação Bem Imóvel"}, he);
		} catch (Exception e) {
			throw new ApplicationException("mensagem.erro", new String[]{"gerar relatório Edificação Bem Imóvel "}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (Exception e) {
				log4j.error("Erro ao Fechar Conexao com o Hibernate: ", e);				
			}
		}
		
		return listaRelatorio;
			
	}

	/**
	 * Obter Lista de Bens Imoveis e objetos filhos inicializados conforme parametros do relatorio.<br>
	 * @author lucianabelico
	 * @since 23/03/2010
	 * @param FiltroRelatorioEdificacaoDTO reDTO
	 * @return Collection
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public Collection<EdificacaoExibirBemImovelDTO> listarImpressaoEdificacaoBensImoveis(Integer codBemImovel) throws ApplicationException {
		
		Collection<EdificacaoExibirBemImovelDTO> listaEdificacoes = new ArrayList<EdificacaoExibirBemImovelDTO>();
		Collection<Edificacao> coll = new ArrayList<Edificacao>();
	
		
		try {
			Session session = HibernateUtil.currentSession();
			Criteria c = session.createCriteria(Edificacao.class);
		
			c.createAlias("bemImovel", "bI")
			.setResultTransformer(Criteria. DISTINCT_ROOT_ENTITY)
			.setFetchMode("tipoEdificacao", FetchMode.JOIN)
			.setFetchMode("tipoConstrucao", FetchMode.JOIN)
			.setFetchMode("bemImovel", FetchMode.JOIN)
			.addOrder(Order.asc("codEdificacao"))
			.createCriteria("ocupacaos", "ocupacao", Criteria.LEFT_JOIN)
			.createCriteria("ocupacao.situacaoOcupacao", "situacao", Criteria.LEFT_JOIN)
			.createCriteria("ocupacao.orgao", "orgao", Criteria.LEFT_JOIN);
			
			if (codBemImovel!=null){
				c.add(Restrictions.eq("bemImovel.codBemImovel", codBemImovel));
			}
			
			coll= c.list();
			
			for (Edificacao edificacao :coll){

				EdificacaoExibirBemImovelDTO relatorio = new EdificacaoExibirBemImovelDTO();
				relatorio.setPaginaOcupacao(new Pagina(null, null, null, Util.htmlEncodeCollection(CadastroFacade.listarOcupacaosExibir(edificacao.getCodEdificacao()))));
				
				relatorio.setMunicipio(edificacao.getBemImovel().getMunicipio());
				relatorio.setUf(edificacao.getBemImovel().getUf());
				relatorio.setCodBemImovel(edificacao.getBemImovel().getCodBemImovel());
				relatorio.setCodEdificacao(edificacao.getCodEdificacao());
				relatorio.setLogradouro(edificacao.getLogradouro());
				relatorio.setEspecificacao(edificacao.getEspecificacao());
				
				if(edificacao.getAreaConstruida()!= null){
					relatorio.setAreaConstruida(edificacao.getAreaConstruida());
				}
				
				if(edificacao.getAreaUtilizada()!= null){				
					relatorio.setAreaUtilizada(edificacao.getAreaUtilizada());
				}

				if(edificacao.getTipoConstrucao()!= null){
					relatorio.setTipoConstrucao(edificacao.getTipoConstrucao().getDescricao());
				}
				if (edificacao.getTipoEdificacao()!= null){
					relatorio.setTipoEdificacao(edificacao.getTipoEdificacao().getDescricao());
				}

				if (edificacao.getOcupacaos()!= null){
					relatorio.setListaOcupacao(new ArrayList<Ocupacao>(edificacao.getOcupacaos()));
				}
				
				listaEdificacoes.add(relatorio);

			}

			
		} catch (HibernateException he) {
			throw new ApplicationException("mensagem.erro", new String[]{"gerar relatório Edificação Bem Imóvel"}, he);
		} catch (Exception e) {
			throw new ApplicationException("mensagem.erro", new String[]{"gerar relatório Edificação Bem Imóvel "}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (Exception e) {
				log4j.error("Erro ao Fechar Conexao com o Hibernate: ", e);				
			}
		}
		
		return listaEdificacoes;
			
	}


	@Override
	public Integer obterQtdPorBemImovel(Integer codBemImovel) throws ApplicationException {
		Integer result;
		try {
			Session session = HibernateUtil.currentSession();
						
			StringBuffer hql = new StringBuffer();
			hql.append(" SELECT e.codEdificacao from Edificacao as e ")
			.append(" JOIN e.bemImovel b ")
			.append(" WHERE e.bemImovel.codBemImovel= :codBemImovel ");

			Query q = session.createQuery(hql.toString());
			q.setInteger("codBemImovel", codBemImovel);
			
			@SuppressWarnings("unchecked")
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


	@Override
	public Edificacao salvarEdificacao(Edificacao edificacao) throws ApplicationException {
		Edificacao retorno = new Edificacao();
		// Validando parâmetro
		if (edificacao == null){
			throw new ApplicationException("mensagem.erro.9002", new String[]{"EdificacaoHibernateDAO.salvarEdificacao()"}, ApplicationException.ICON_ERRO);
		}
		
		try {
			log4j.debug("Obtendo sessao corrente...");
			Session session = HibernateUtil.currentSession();
			session.clear();
			Serializable chave = session.save(edificacao);
			session.flush();
			log4j.info("SALVAR:" + edificacao.getClass().getName() + " salvo.");
			retorno = (Edificacao) session.get(Edificacao.class, chave);
			log4j.info("GET:" + edificacao.getClass().getName() + " q acabou de ser salvo.");
						
		} catch (ConstraintViolationException cve) {
			log4j.error("EdificacaoHibernateDAO.salvarEdificacao()" + cve);
			throw new ApplicationException("mensagem.erro.9003", new String[]{edificacao.getClass().getSimpleName()}, ApplicationException.ICON_AVISO);
		} catch (HibernateException he) {
			log4j.error("EdificacaoHibernateDAO.salvarEdificacao()" + he);
			throw new ApplicationException("mensagem.erro.9001", new String[]{"EdificacaoHibernateDAO.salvarEdificacao()"}, he, ApplicationException.ICON_ERRO);
		} catch (Exception e) {
			log4j.error("EdificacaoHibernateDAO.salvarEdificacao()" + e);
			throw new ApplicationException("mensagem.erro.9001",new String[]{"EdificacaoHibernateDAO.salvarEdificacao()"}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				log4j.debug("Fechando sessao corrente.");
				HibernateUtil.closeSession();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.9003", new String[]{edificacao.getClass().getSimpleName()}, ApplicationException.ICON_AVISO);
			}
		}
		
		return retorno;
	}


	@SuppressWarnings("unchecked")
	@Override
	public Collection<Edificacao> listarByBemImovel(Integer codBemImovel) throws ApplicationException {
		Collection<Edificacao> coll = new ArrayList<Edificacao>();
		

		try {
			Session session = HibernateUtil.currentSession();

			StringBuffer query = new StringBuffer();
			query.append("from Edificacao e ")
			.append("left join fetch e.tipoConstrucao ")
			.append("left join fetch e.tipoEdificacao ")
			.append("left join fetch e.bemImovel ")
			.append("where e.bemImovel.codBemImovel=:codBemImovel");
			Query q = session.createQuery(query.toString() );
			q.setInteger("codBemImovel", codBemImovel);
					
			coll = (Collection<Edificacao>) q.list();	

		} catch (HibernateException he) {
			throw new ApplicationException("ERRO.201",  new String[]{"ao listar Edificação por Bem Imóvel"});
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201",  new String[]{"ao listar Edificação por Bem Imóvel"}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				HibernateUtil.closeSession();
			}catch (Exception e) {
				log4j.error("Problema ao tentar fechar conexao com o banco de dados: listarByBemImovel", e);
			}		
		}
		
		return coll;
	}

}
