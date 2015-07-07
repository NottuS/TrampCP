package gov.pr.celepar.abi.dto;

import java.math.BigDecimal;
import java.util.Date;

public class AvaliacaoExibirBemImovelDTO {

	private Integer codAvaliacao;
    private BigDecimal valor;
    private Date dataAvaliacao;    
    private Integer indTipoAvaliacao;
    
    
	public Integer getCodAvaliacao() {
		return codAvaliacao;
	}
	public void setCodAvaliacao(Integer codAvaliacao) {
		this.codAvaliacao = codAvaliacao;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public Date getDataAvaliacao() {
		return dataAvaliacao;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public void setDataAvaliacao(Date dataAvaliacao) {
		this.dataAvaliacao = dataAvaliacao;
	}
	public Integer getIndTipoAvaliacao() {
		return indTipoAvaliacao;
	}
	public void setIndTipoAvaliacao(Integer indTipoAvaliacao) {
		this.indTipoAvaliacao = indTipoAvaliacao;
	}

	
	
}
