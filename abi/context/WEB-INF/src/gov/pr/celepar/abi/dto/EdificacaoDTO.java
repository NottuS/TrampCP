package gov.pr.celepar.abi.dto;

import java.math.BigDecimal;

public class EdificacaoDTO {

	private Integer codEdificacao;
	private String tipoConstrucao;
	private String tipoEdificacao;
	private String especificacao;
	private String ocupacao;
	private BigDecimal areaConstruidaDisponivelMetroQuadrado;
	private BigDecimal areaConstruidaDisponivelPercentual;
	
	public Integer getCodEdificacao() {
		return codEdificacao;
	}
	public void setCodEdificacao(Integer codEdificacao) {
		this.codEdificacao = codEdificacao;
	}
	public String getTipoConstrucao() {
		return tipoConstrucao;
	}
	public void setTipoConstrucao(String tipoConstrucao) {
		this.tipoConstrucao = tipoConstrucao;
	}
	public String getTipoEdificacao() {
		return tipoEdificacao;
	}
	public void setTipoEdificacao(String tipoEdificacao) {
		this.tipoEdificacao = tipoEdificacao;
	}
	public String getEspecificacao() {
		return especificacao;
	}
	public void setEspecificacao(String especificacao) {
		this.especificacao = especificacao;
	}
	public BigDecimal getAreaConstruidaDisponivelMetroQuadrado() {
		return areaConstruidaDisponivelMetroQuadrado;
	}
	public void setAreaConstruidaDisponivelMetroQuadrado(
			BigDecimal areaConstruidaDisponivelMetroQuadrado) {
		this.areaConstruidaDisponivelMetroQuadrado = areaConstruidaDisponivelMetroQuadrado;
	}
	public BigDecimal getAreaConstruidaDisponivelPercentual() {
		return areaConstruidaDisponivelPercentual;
	}
	public void setAreaConstruidaDisponivelPercentual(
			BigDecimal areaConstruidaDisponivelPercentual) {
		this.areaConstruidaDisponivelPercentual = areaConstruidaDisponivelPercentual;
	}
	public void setOcupacao(String ocupacao) {
		this.ocupacao = ocupacao;
	}
	public String getOcupacao() {
		return ocupacao;
	}
	
}
