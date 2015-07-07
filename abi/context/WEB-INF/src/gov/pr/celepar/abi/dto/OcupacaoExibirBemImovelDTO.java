package gov.pr.celepar.abi.dto;

import java.math.BigDecimal;

public class OcupacaoExibirBemImovelDTO {

    private Integer codOcupacao;
    private String edificacao;
    private String orgaoSiglaDescricao;
    private String descricao;
    private BigDecimal ocupacaoMetroQuadrado;
    private BigDecimal ocupacaoPercentual;
    private String outrasInformacoes;
	private String situacaoOcupacao;

	public String getSituacaoOcupacao() {
		return situacaoOcupacao;
	}
	public void setSituacaoOcupacao(String situacaoOcupacao) {
		this.situacaoOcupacao = situacaoOcupacao;
	}
    
    
	public Integer getCodOcupacao() {
		return codOcupacao;
	}
	public void setCodOcupacao(Integer codOcupacao) {
		this.codOcupacao = codOcupacao;
	}
	public String getEdificacao() {
		return edificacao;
	}
	public void setEdificacao(String edificacao) {
		this.edificacao = edificacao;
	}
	public String getOrgaoSiglaDescricao() {
		return orgaoSiglaDescricao;
	}
	public void setOrgaoSiglaDescricao(String orgaoSiglaDescricao) {
		this.orgaoSiglaDescricao = orgaoSiglaDescricao;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public BigDecimal getOcupacaoMetroQuadrado() {
		return ocupacaoMetroQuadrado;
	}
	public void setOcupacaoMetroQuadrado(BigDecimal ocupacaoMetroQuadrado) {
		this.ocupacaoMetroQuadrado = ocupacaoMetroQuadrado;
	}
	public BigDecimal getOcupacaoPercentual() {
		return ocupacaoPercentual;
	}
	public void setOcupacaoPercentual(BigDecimal ocupacaoPercentual) {
		this.ocupacaoPercentual = ocupacaoPercentual;
	}
	public String getOutrasInformacoes() {
		return outrasInformacoes;
	}
	public void setOutrasInformacoes(String outrasInformacoes) {
		this.outrasInformacoes = outrasInformacoes;
	}
}
