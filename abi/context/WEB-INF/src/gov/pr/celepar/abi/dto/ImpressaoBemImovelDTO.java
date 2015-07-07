package gov.pr.celepar.abi.dto;

import gov.pr.celepar.abi.pojo.Avaliacao;
import gov.pr.celepar.abi.pojo.Confrontante;
import gov.pr.celepar.abi.pojo.CoordenadaUtm;
import gov.pr.celepar.abi.pojo.Edificacao;
import gov.pr.celepar.abi.pojo.LeiBemImovel;
import gov.pr.celepar.abi.pojo.Ocupacao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ImpressaoBemImovelDTO  implements Serializable{

	private static final long serialVersionUID = -4593271802141559216L;
    private Set<LeiBemImovel> leiBemImovels = new HashSet<LeiBemImovel>(0);
    private Set<Confrontante> confrontantes = new HashSet<Confrontante>(0);
    private Set<Avaliacao> avaliacaos = new HashSet<Avaliacao>(0);
    private Set<CoordenadaUtm> coordenadaUtms = new HashSet<CoordenadaUtm>(0);
    private Set<Edificacao> edificacaos = new HashSet<Edificacao>(0);
    private Set<Ocupacao> ocupacaosTerreno = new HashSet<Ocupacao>(0);

    private List<LoteExibirBemImovelDTO> listaQuadrasLotes = new ArrayList<LoteExibirBemImovelDTO>(0);
    private List<DocumentacaoNotificacaoExibirBemImovelDTO> listaDocumentacaoNotificacao = new ArrayList<DocumentacaoNotificacaoExibirBemImovelDTO>(0);
    private List<DocumentacaoSemNotificacaoExibirBemImovelDTO> listaDocumentacaoSemNotificacao = new ArrayList<DocumentacaoSemNotificacaoExibirBemImovelDTO>(0);
    private List<OcorrenciaDocumentacaoExibirBemImovelDTO> listaOcorrencias = new ArrayList<OcorrenciaDocumentacaoExibirBemImovelDTO>(0);

    private String usuario;	
	private Integer codBemImovel;
	private Integer administracao;
	private String administracaoDireta;
	private String orgao;
	private String classificacaoBemImovel;
	private String situacaoLocal;
	private String situacaoLegalCartorial;
	private String numeroProcessoSpi;
	private String cep;
	private String uf;
	private String codMunicipio;
	private String bairroDistrito;
	private String formaIncorporacao;
	private String dataIncorporacao;
	private String situacaoImovel;
	private String municipio;


	private String logradouro;
	private String numero;
	private String complemento;
	private String denominacaoImovel;
	private BigDecimal areaTerreno;
	private String somenteTerreno;

	// Filtro do que deve ser impresso
	private String filtroLeis="";
	private String filtroLotes="";
	private String filtroCoordenadasUtm="";
	private String filtroAvaliacoes="";
	private String filtroConfrontantes="";
	private String filtroEdificacoes="";
	private String filtroDocumentacoesNotificacao="";
	private String filtroDocumentacoesSemNotificacao="";
	private String filtroOcorrencias="";
	private String filtroQuadra="";
	private String filtroOcupacaoTerreno="";
	private String observacoesMigracao;
	
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public Integer getCodBemImovel() {
		return codBemImovel;
	}
	
	public String getOrgao() {
		return orgao;
	}
	public String getClassificacaoBemImovel() {
		return classificacaoBemImovel;
	}
	public String getSituacaoLocal() {
		return situacaoLocal;
	}
	public String getSituacaoLegalCartorial() {
		return situacaoLegalCartorial;
	}
	public String getNumeroProcessoSpi() {
		return numeroProcessoSpi;
	}
	public String getCep() {
		return cep;
	}
	public String getUf() {
		return uf;
	}
	public String getCodMunicipio() {
		return codMunicipio;
	}
	public String getBairroDistrito() {
		return bairroDistrito;
	}
	
	public String getFormaIncorporacao() {
		return formaIncorporacao;
	}
	public String getSituacaoImovel() {
		return situacaoImovel;
	}
	public String getLogradouro() {
		return logradouro;
	}
	public String getNumero() {
		return numero;
	}
	public String getComplemento() {
		return complemento;
	}
	public String getDenominacaoImovel() {
		return denominacaoImovel;
	}
	public void setCodBemImovel(Integer codBemImovel) {
		this.codBemImovel = codBemImovel;
	}
	
	public void setOrgao(String orgao) {
		this.orgao = orgao;
	}
	public void setClassificacaoBemImovel(String classificacaoBemImovel) {
		this.classificacaoBemImovel = classificacaoBemImovel;
	}
	public void setSituacaoLocal(String situacaoLocal) {
		this.situacaoLocal = situacaoLocal;
	}
	public void setSituacaoLegalCartorial(String situacaoLegalCartorial) {
		this.situacaoLegalCartorial = situacaoLegalCartorial;
	}
	public void setNumeroProcessoSpi(String numeroProcessoSpi) {
		this.numeroProcessoSpi = numeroProcessoSpi;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public void setCodMunicipio(String codMunicipio) {
		this.codMunicipio = codMunicipio;
	}
	public void setBairroDistrito(String bairroDistrito) {
		this.bairroDistrito = bairroDistrito;
	}
	
	public void setFormaIncorporacao(String formaIncorporacao) {
		this.formaIncorporacao = formaIncorporacao;
	}
	public void setSituacaoImovel(String situacaoImovel) {
		this.situacaoImovel = situacaoImovel;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	public void setDenominacaoImovel(String denominacaoImovel) {
		this.denominacaoImovel = denominacaoImovel;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getFiltroLeis() {
		return filtroLeis;
	}
	public String getFiltroLotes() {
		return filtroLotes;
	}
	public String getFiltroCoordenadasUtm() {
		return filtroCoordenadasUtm;
	}
	public String getFiltroAvaliacoes() {
		return filtroAvaliacoes;
	}
	public String getFiltroConfrontantes() {
		return filtroConfrontantes;
	}
	public String getFiltroEdificacoes() {
		return filtroEdificacoes;
	}
	public String getFiltroDocumentacoesNotificacao() {
		return filtroDocumentacoesNotificacao;
	}
	public String getFiltroDocumentacoesSemNotificacao() {
		return filtroDocumentacoesSemNotificacao;
	}
	public String getFiltroOcorrencias() {
		return filtroOcorrencias;
	}
	public void setFiltroLeis(String filtroLeis) {
		this.filtroLeis = filtroLeis;
	}
	public void setFiltroLotes(String filtroLotes) {
		this.filtroLotes = filtroLotes;
	}
	public void setFiltroCoordenadasUtm(String filtroCoordenadasUtm) {
		this.filtroCoordenadasUtm = filtroCoordenadasUtm;
	}
	public void setFiltroAvaliacoes(String filtroAvaliacoes) {
		this.filtroAvaliacoes = filtroAvaliacoes;
	}
	public void setFiltroConfrontantes(String filtroConfrontantes) {
		this.filtroConfrontantes = filtroConfrontantes;
	}
	public void setFiltroEdificacoes(String filtroEdificacoes) {
		this.filtroEdificacoes = filtroEdificacoes;
	}
	public void setFiltroDocumentacoesNotificacao(
			String filtroDocumentacoesNotificacao) {
		this.filtroDocumentacoesNotificacao = filtroDocumentacoesNotificacao;
	}
	public void setFiltroDocumentacoesSemNotificacao(
			String filtroDocumentacoesSemNotificacao) {
		this.filtroDocumentacoesSemNotificacao = filtroDocumentacoesSemNotificacao;
	}
	public void setFiltroOcorrencias(String filtroOcorrencias) {
		this.filtroOcorrencias = filtroOcorrencias;
	}
	public BigDecimal getAreaTerreno() {
		return areaTerreno;
	}
	public void setAreaTerreno(BigDecimal areaTerreno) {
		this.areaTerreno = areaTerreno;
	}
	public String getDataIncorporacao() {
		return dataIncorporacao;
	}
	public void setDataIncorporacao(String dataIncorporacao) {
		this.dataIncorporacao = dataIncorporacao;
	}
	public String getObservacoesMigracao() {
		return observacoesMigracao;
	}
	public void setObservacoesMigracao(String observacoesMigracao) {
		this.observacoesMigracao = observacoesMigracao;
	}
	public String getFiltroQuadra() {
		return filtroQuadra;
	}
	public void setFiltroQuadra(String filtroQuadra) {
		this.filtroQuadra = filtroQuadra;
	}
	
	public void setAdministracao(Integer administracao) {
		this.administracao = administracao;
	}
	public Integer getAdministracao() {
		return administracao;
	}
	public void setAdministracaoDireta(String administracaoDireta) {
		this.administracaoDireta = administracaoDireta;
	}
	public String getAdministracaoDireta() {
		return administracaoDireta;
	}
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	public String getMunicipio() {
		return municipio;
	}
	public void setSomenteTerreno(String somenteTerreno) {
		this.somenteTerreno = somenteTerreno;
	}
	public String getSomenteTerreno() {
		return somenteTerreno;
	}
	public void setFiltroOcupacaoTerreno(String filtroOcupacaoTerreno) {
		this.filtroOcupacaoTerreno = filtroOcupacaoTerreno;
	}
	public String getFiltroOcupacaoTerreno() {
		return filtroOcupacaoTerreno;
	}
	public Set<CoordenadaUtm> getCoordenadaUtms() {
		return coordenadaUtms;
	}
	public void setCoordenadaUtms(Set<CoordenadaUtm> coordenadaUtms) {
		this.coordenadaUtms = coordenadaUtms;
	}
	public Set<Confrontante> getConfrontantes() {
		return confrontantes;
	}
	public void setConfrontantes(Set<Confrontante> confrontantes) {
		this.confrontantes = confrontantes;
	}
	public Set<Avaliacao> getAvaliacaos() {
		return avaliacaos;
	}
	public void setAvaliacaos(Set<Avaliacao> avaliacaos) {
		this.avaliacaos = avaliacaos;
	}
	public Set<Edificacao> getEdificacaos() {
		return edificacaos;
	}
	public void setEdificacaos(Set<Edificacao> edificacaos) {
		this.edificacaos = edificacaos;
	}
	public Set<LeiBemImovel> getLeiBemImovels() {
		return leiBemImovels;
	}
	public void setLeiBemImovels(Set<LeiBemImovel> leiBemImovels) {
		this.leiBemImovels = leiBemImovels;
	}
	public Set<Ocupacao> getOcupacaosTerreno() {
		return ocupacaosTerreno;
	}
	public void setOcupacaosTerreno(Set<Ocupacao> ocupacaosTerreno) {
		this.ocupacaosTerreno = ocupacaosTerreno;
	}
	public void setListaDocumentacaoNotificacao(
			List<DocumentacaoNotificacaoExibirBemImovelDTO> listaDocumentacaoNotificacao) {
		this.listaDocumentacaoNotificacao = listaDocumentacaoNotificacao;
	}
	public List<DocumentacaoNotificacaoExibirBemImovelDTO> getListaDocumentacaoNotificacao() {
		return listaDocumentacaoNotificacao;
	}
	public void setListaOcorrencias(List<OcorrenciaDocumentacaoExibirBemImovelDTO> listaOcorrencias) {
		this.listaOcorrencias = listaOcorrencias;
	}
	public List<OcorrenciaDocumentacaoExibirBemImovelDTO> getListaOcorrencias() {
		return listaOcorrencias;
	}
	public void setListaDocumentacaoSemNotificacao(
			List<DocumentacaoSemNotificacaoExibirBemImovelDTO> listaDocumentacaoSemNotificacao) {
		this.listaDocumentacaoSemNotificacao = listaDocumentacaoSemNotificacao;
	}
	public List<DocumentacaoSemNotificacaoExibirBemImovelDTO> getListaDocumentacaoSemNotificacao() {
		return listaDocumentacaoSemNotificacao;
	}
	public void setListaQuadrasLotes(List<LoteExibirBemImovelDTO> listaQuadrasLotes) {
		this.listaQuadrasLotes = listaQuadrasLotes;
	}
	public List<LoteExibirBemImovelDTO> getListaQuadrasLotes() {
		return listaQuadrasLotes;
	}
	
}
