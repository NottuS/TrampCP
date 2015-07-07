package gov.pr.celepar.abi.dto;

import gov.pr.celepar.abi.pojo.Instituicao;

import java.io.Serializable;
import java.math.BigDecimal;

public class RelatorioBemImovelDTO  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4679916277842994096L;
	private Integer codBemImovel;
	private String classificacaoBemImovel;
	private String situacaoImovel;
	private String situacaoLegalCartorial;
	private String numeroDocumentoCartorial;
	private BigDecimal avaliacao;
	private String uf;
	private String municipio;
	private BigDecimal areaTerreno;
	private String logradouro;
	private Instituicao instituicao;
	private Integer nrBemImovel;
	
	public Integer getCodBemImovel() {
		return codBemImovel;
	}
	public void setCodBemImovel(Integer codBemImovel) {
		this.codBemImovel = codBemImovel;
	}
	
	public String getClassificacaoBemImovel() {
		return classificacaoBemImovel;
	}
	public void setClassificacaoBemImovel(String classificacaoBemImovel) {
		this.classificacaoBemImovel = classificacaoBemImovel;
	}
	public String getSituacaoImovel() {
		return situacaoImovel;
	}
	public void setSituacaoImovel(String situacaoImovel) {
		this.situacaoImovel = situacaoImovel;
	}
	public String getSituacaoLegalCartorial() {
		return situacaoLegalCartorial;
	}
	public void setSituacaoLegalCartorial(String situacaoLegalCartorial) {
		this.situacaoLegalCartorial = situacaoLegalCartorial;
	}
	public String getNumeroDocumentoCartorial() {
		return numeroDocumentoCartorial;
	}
	public void setNumeroDocumentoCartorial(String numeroDocumentoCartorial) {
		this.numeroDocumentoCartorial = numeroDocumentoCartorial;
	}
	
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public String getMunicipio() {
		return municipio;
	}
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	public void setAvaliacao(BigDecimal avaliacao) {
		this.avaliacao = avaliacao;
	}
	public BigDecimal getAvaliacao() {
		return avaliacao;
	}
	public void setAreaTerreno(BigDecimal areaTerreno) {
		this.areaTerreno = areaTerreno;
	}
	public BigDecimal getAreaTerreno() {
		return areaTerreno;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	public String getLogradouro() {
		return logradouro;
	}
	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}
	public Instituicao getInstituicao() {
		return instituicao;
	}
	public void setNrBemImovel(Integer nrBemImovel) {
		this.nrBemImovel = nrBemImovel;
	}
	public Integer getNrBemImovel() {
		return nrBemImovel;
	}

	
	
}
