package gov.pr.celepar.abi.dto;

import java.math.BigDecimal;

import javax.persistence.Transient;

public class OcupacaoOrgaoResponsavelListaDTO {
	
	private Integer codOcupacao;
	private String orgao;
	private String situacao;
	private String descricao;
	private BigDecimal ocupacaoMetroQuadrado;
	private BigDecimal ocupacaoPercentual;
	private String complemento;
    private Boolean ativo;

	public Integer getCodOcupacao() {
		return codOcupacao;
	}
	public void setCodOcupacao(Integer codOcupacao) {
		this.codOcupacao = codOcupacao;
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
	public String getOrgao() {
		return orgao;
	}
	public void setOrgao(String orgao) {
		this.orgao = orgao;
	}
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	public Boolean getAtivo() {
		return ativo;
	}

    @Transient
    public OcupacaoOrgaoResponsavelListaDTO getInstanciaAtual(){
    	return this;
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
		OcupacaoOrgaoResponsavelListaDTO other = (OcupacaoOrgaoResponsavelListaDTO)obj;
		if(codOcupacao == null) {
			if(other.codOcupacao != null) return false;
		}
		else if(!codOcupacao.equals(other.codOcupacao)) {
			return false;
		}
		return true;
	}
}
