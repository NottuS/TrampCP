package gov.pr.celepar.abi.dto;

import gov.pr.celepar.abi.pojo.Orgao;

import java.math.BigDecimal;
import java.util.List;

public class BemImovelPesquisaDTO {

    private Integer codBemImovel;
    private Integer nrBemImovel;
    private Integer codOrgao;	
    private Integer codOrgaoResponsavel;	
	private Integer codClassificacaoBemImovel;
	private String uf;
	private Integer codMunicipio;
	private String logradouro;
	private String bairroDistrito;
	private Integer codSituacaoLegalCartorial;
	private Integer codCartorio;
	private String numeroDocumentoCartorial;
	private Integer codTabelionato;
	private String numeroDocumentoTabelional;
	private Integer codFormaIncorporacao;
	private BigDecimal areaTerrenoIni;
	private BigDecimal areaTerrenoFim;
	private Integer codTipoConstrucao;
	private Integer codTipoEdificacao;
	private Integer codTipoDocumentacao;
	private Integer codSituacaoOcupacao;
	private String lote;
	private String quadra;
	private Integer codDenominacaoImovel;
	private Boolean averbado;
	private Integer codSituacaoImovel;
	private String nirf;
    private String niif;
    private String incra;
    private Integer administracao;
    private String ocupante;
    private String orgaoOcupante;
    private String observacao;
    private Boolean indOperadorOrgao;
    private Integer codInstituicao;
    private List<Orgao> listaOrgao;
    private List<Integer> listaCodOrgao;
    
    
	public Integer getCodOrgao() {
		return codOrgao;
	}
	public void setCodOrgao(Integer codOrgao) {
		this.codOrgao = codOrgao;
	}
	public Integer getCodClassificacaoBemImovel() {
		return codClassificacaoBemImovel;
	}
	public void setCodClassificacaoBemImovel(Integer codClassificacaoBemImovel) {
		this.codClassificacaoBemImovel = codClassificacaoBemImovel;
	}
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public Integer getCodMunicipio() {
		return codMunicipio;
	}
	public void setCodMunicipio(Integer codMunicipio) {
		this.codMunicipio = codMunicipio;
	}
	public String getLogradouro() {
		return logradouro;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	public String getBairroDistrito() {
		return bairroDistrito;
	}
	public void setBairroDistrito(String bairroDistrito) {
		this.bairroDistrito = bairroDistrito;
	}
	public Integer getCodSituacaoLegalCartorial() {
		return codSituacaoLegalCartorial;
	}
	public void setCodSituacaoLegalCartorial(Integer codSituacaoLegalCartorial) {
		this.codSituacaoLegalCartorial = codSituacaoLegalCartorial;
	}
	public Integer getCodCartorio() {
		return codCartorio;
	}
	public void setCodCartorio(Integer codCartorio) {
		this.codCartorio = codCartorio;
	}
	public String getNumeroDocumentoCartorial() {
		return numeroDocumentoCartorial;
	}
	public void setNumeroDocumentoCartorial(String numeroDocumentoCartorial) {
		this.numeroDocumentoCartorial = numeroDocumentoCartorial;
	}
	public Integer getCodTabelionato() {
		return codTabelionato;
	}
	public void setCodTabelionato(Integer codTabelionato) {
		this.codTabelionato = codTabelionato;
	}
	public String getNumeroDocumentoTabelional() {
		return numeroDocumentoTabelional;
	}
	public void setNumeroDocumentoTabelional(String numeroDocumentoTabelional) {
		this.numeroDocumentoTabelional = numeroDocumentoTabelional;
	}
	public Integer getCodFormaIncorporacao() {
		return codFormaIncorporacao;
	}
	public void setCodFormaIncorporacao(Integer codFormaIncorporacao) {
		this.codFormaIncorporacao = codFormaIncorporacao;
	}
	public BigDecimal getAreaTerrenoIni() {
		return areaTerrenoIni;
	}
	public void setAreaTerrenoIni(BigDecimal areaTerrenoIni) {
		this.areaTerrenoIni = areaTerrenoIni;
	}
	public BigDecimal getAreaTerrenoFim() {
		return areaTerrenoFim;
	}
	public void setAreaTerrenoFim(BigDecimal areaTerrenoFim) {
		this.areaTerrenoFim = areaTerrenoFim;
	}
	public Integer getCodTipoConstrucao() {
		return codTipoConstrucao;
	}
	public void setCodTipoConstrucao(Integer codTipoConstrucao) {
		this.codTipoConstrucao = codTipoConstrucao;
	}
	public Integer getCodTipoEdificacao() {
		return codTipoEdificacao;
	}
	public void setCodTipoEdificacao(Integer codTipoEdificacao) {
		this.codTipoEdificacao = codTipoEdificacao;
	}
	public Integer getCodTipoDocumentacao() {
		return codTipoDocumentacao;
	}
	public void setCodTipoDocumentacao(Integer codTipoDocumentacao) {
		this.codTipoDocumentacao = codTipoDocumentacao;
	}
	public Integer getCodSituacaoOcupacao() {
		return codSituacaoOcupacao;
	}
	public void setCodSituacaoOcupacao(Integer codSituacaoOcupacao) {
		this.codSituacaoOcupacao = codSituacaoOcupacao;
	}	
	public String getLote() {
		return lote;
	}
	public void setLote(String lote) {
		this.lote = lote;
	}
	public String getQuadra() {
		return quadra;
	}
	public void setQuadra(String quadra) {
		this.quadra = quadra;
	}
	public Integer getCodDenominacaoImovel() {
		return codDenominacaoImovel;
	}
	public void setCodDenominacaoImovel(Integer codDenominacaoImovel) {
		this.codDenominacaoImovel = codDenominacaoImovel;
	}
	
	public Boolean getAverbado() {
		return averbado;
	}
	public void setAverbado(Boolean averbado) {
		this.averbado = averbado;
	}
	public Integer getCodSituacaoImovel() {
		return codSituacaoImovel;
	}
	public void setCodSituacaoImovel(Integer codSituacaoImovel) {
		this.codSituacaoImovel = codSituacaoImovel;
	}
	public void setIncra(String incra) {
		this.incra = incra;
	}
	public String getIncra() {
		return incra;
	}
	public void setNiif(String niif) {
		this.niif = niif;
	}
	public String getNiif() {
		return niif;
	}
	public void setNirf(String nirf) {
		this.nirf = nirf;
	}
	public String getNirf() {
		return nirf;
	}
	public void setAdministracao(Integer administracao) {
		this.administracao = administracao;
	}
	public Integer getAdministracao() {
		return administracao;
	}
	public void setOcupante(String ocupante) {
		this.ocupante = ocupante;
	}
	public String getOcupante() {
		return ocupante;
	}
	public void setCodOrgaoResponsavel(Integer codOrgaoResponsavel) {
		this.codOrgaoResponsavel = codOrgaoResponsavel;
	}
	public Integer getCodOrgaoResponsavel() {
		return codOrgaoResponsavel;
	}
	public String getOrgaoOcupante() {
		return orgaoOcupante;
	}
	public void setOrgaoOcupante(String orgaoOcupante) {
		this.orgaoOcupante = orgaoOcupante;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public Boolean getIndOperadorOrgao() {
		return indOperadorOrgao;
	}
	public void setIndOperadorOrgao(Boolean indOperadorOrgao) {
		this.indOperadorOrgao = indOperadorOrgao;
	}
	public List<Orgao> getListaOrgao() {
		return listaOrgao;
	}
	public void setListaOrgao(List<Orgao> listaOrgao) {
		this.listaOrgao = listaOrgao;
	}
	public List<Integer> getListaCodOrgao() {
		return listaCodOrgao;
	}
	public void setListaCodOrgao(List<Integer> listaCodOrgao) {
		this.listaCodOrgao = listaCodOrgao;
	}
	public Integer getCodInstituicao() {
		return codInstituicao;
	}
	public void setCodInstituicao(Integer codInstituicao) {
		this.codInstituicao = codInstituicao;
	}
	public void setNrBemImovel(Integer nrBemImovel) {
		this.nrBemImovel = nrBemImovel;
	}
	public Integer getNrBemImovel() {
		return nrBemImovel;
	}
	public void setCodBemImovel(Integer codBemImovel) {
		this.codBemImovel = codBemImovel;
	}
	public Integer getCodBemImovel() {
		return codBemImovel;
	}

	
	
}

