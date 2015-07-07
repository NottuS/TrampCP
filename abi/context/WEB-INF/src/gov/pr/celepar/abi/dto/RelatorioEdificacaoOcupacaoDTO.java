package gov.pr.celepar.abi.dto;

import gov.pr.celepar.abi.pojo.Instituicao;

import java.io.Serializable;

public class RelatorioEdificacaoOcupacaoDTO implements Serializable {
	
	private static final long serialVersionUID = -8034558859049591146L;
	private Integer codBemImovel;
	private String logradouro;
	private String orgaoSiglaDescricao;
	private String situacaoOcupacao;
	private String descricao;
	private String especificacao;
	private String uf;
	private String municipio;
	private String ocupacaoMetroQuadrado;
	private Instituicao instituicao;
	private Integer nrBemImovel;
	
	
	public Integer getCodBemImovel() {
		return codBemImovel;
	}
	public void setCodBemImovel(Integer codBemImovel) {
		this.codBemImovel = codBemImovel;
	}
	public String getLogradouro() {
		return logradouro;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	public String getOrgaoSiglaDescricao() {
		return orgaoSiglaDescricao;
	}
	public void setOrgaoSiglaDescricao(String orgaoSiglaDescricao) {
		this.orgaoSiglaDescricao = orgaoSiglaDescricao;
	}
	public String getSituacaoOcupacao() {
		return situacaoOcupacao;
	}
	public void setSituacaoOcupacao(String situacaoOcupacao) {
		this.situacaoOcupacao = situacaoOcupacao;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getEspecificacao() {
		return especificacao;
	}
	public void setEspecificacao(String especificacao) {
		this.especificacao = especificacao;
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
	public String getOcupacaoMetroQuadrado() {
		return ocupacaoMetroQuadrado;
	}
	public void setOcupacaoMetroQuadrado(String ocupacaoMetroQuadrado) {
		this.ocupacaoMetroQuadrado = ocupacaoMetroQuadrado;
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
