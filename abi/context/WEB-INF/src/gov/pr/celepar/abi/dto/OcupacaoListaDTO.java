package gov.pr.celepar.abi.dto;

import java.math.BigDecimal;

public class OcupacaoListaDTO {
	private Integer codOcupacao;
	private String orgaoSiglaDescricao;
	private String descricao;
	private BigDecimal ocupacaoMetroQuadrado;
	private BigDecimal ocupacaoPercentual;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codOcupacao == null) ? 0 : codOcupacao.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		OcupacaoListaDTO other = (OcupacaoListaDTO)obj;
		if(codOcupacao == null) {
			if(other.codOcupacao != null) return false;
		}
		else if(!codOcupacao.equals(other.codOcupacao)) {
			return false;
		}
		return true;
	}
}
