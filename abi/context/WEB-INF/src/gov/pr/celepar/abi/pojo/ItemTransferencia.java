package gov.pr.celepar.abi.pojo;

import gov.pr.celepar.abi.util.Dominios;
import gov.pr.celepar.framework.util.Valores;

import java.math.BigDecimal;

import javax.persistence.Transient;

public class ItemTransferencia implements java.io.Serializable {

	private static final long serialVersionUID = -685984952876526108L;
	private Integer codItemTransferencia;
    private Edificacao edificacao;
    private Transferencia transferencia;
    private BigDecimal transferenciaMetros;
    private BigDecimal transferenciaPercentual;
    private String utilizacao;
    private String observacao;
    private String caracteristica;
    private String situacaoDominial;
    private Boolean somenteTerreno;
    private Integer tipo;
    
    
    public ItemTransferencia() {
	}
    
	public ItemTransferencia(Integer codItemTransferencia, Edificacao edificacao,
			Transferencia transferencia, BigDecimal transferenciaMetros,
			BigDecimal transferenciaPercentual, String utilizacao, String observacao,
			Boolean somenteTerreno, String situacaoDominial, String caracteristica,
			Integer tipo) {
		super();
		this.codItemTransferencia = codItemTransferencia;
		this.edificacao = edificacao;
		this.transferencia = transferencia;
		this.transferenciaMetros = transferenciaMetros;
		this.transferenciaPercentual = transferenciaPercentual;
		this.utilizacao = utilizacao;
		this.observacao = observacao;
		this.somenteTerreno = somenteTerreno;
		this.caracteristica = caracteristica;
		this.situacaoDominial = situacaoDominial;
		this.tipo = tipo;
	}

	public Integer getCodItemTransferencia() {
		return codItemTransferencia;
	}

	public void setCodItemTransferencia(Integer codItemTransferencia) {
		this.codItemTransferencia = codItemTransferencia;
	}

	public Edificacao getEdificacao() {
		return edificacao;
	}

	public void setEdificacao(Edificacao edificacao) {
		this.edificacao = edificacao;
	}

	public Transferencia getTransferencia() {
		return transferencia;
	}

	public void setTransferencia(Transferencia transferencia) {
		this.transferencia = transferencia;
	}

	public BigDecimal getTransferenciaMetros() {
		return transferenciaMetros;
	}

	public void setTransferenciaMetros(BigDecimal transferenciaMetros) {
		this.transferenciaMetros = transferenciaMetros;
	}

	public BigDecimal getTransferenciaPercentual() {
		return transferenciaPercentual;
	}

	public void setTransferenciaPercentual(BigDecimal transferenciaPercentual) {
		this.transferenciaPercentual = transferenciaPercentual;
	}

	public String getUtilizacao() {
		return utilizacao;
	}

	public void setUtilizacao(String utilizacao) {
		this.utilizacao = utilizacao;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Boolean getSomenteTerreno() {
		return somenteTerreno;
	}

	public void setSomenteTerreno(Boolean somenteTerreno) {
		this.somenteTerreno = somenteTerreno;
	}

	public String getCaracteristica() {
		return caracteristica;
	}

	public void setCaracteristica(String caracteristica) {
		this.caracteristica = caracteristica;
	}

	public String getSituacaoDominial() {
		return situacaoDominial;
	}

	public void setSituacaoDominial(String situacaoDominial) {
		this.situacaoDominial = situacaoDominial;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemTransferencia other = (ItemTransferencia) obj;
		if (codItemTransferencia == null) {
			if (other.codItemTransferencia != null)
				return false;
		} else if (!codItemTransferencia.equals(other.codItemTransferencia))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codItemTransferencia == null) ? 0 : codItemTransferencia.hashCode());
		return result;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public Integer getTipo() {
		return tipo;
	}
	
	@Transient
	public String getOutrasInformacoes () {
		String aux = "";
		if (this.getEdificacao() != null && this.getEdificacao().getCodEdificacao() > 0) {
			aux = "Edificação: ".concat(this.getEdificacao().getEspecificacao());
		} else {
			if (this.getSomenteTerreno()) {
				aux = "Somente terreno: Sim";
			} else {
				aux = "Somente terreno: Não";
			}
		}
		
		return aux;
	}

	@Transient
	public String getTipoDescricao() {
		return Dominios.tipoOperacaoBemImovel.getTipoOperacaoBemImovelByIndex(tipo).getLabel();
	}

	@Transient
	public String getTransferenciaPercentualFormatado() {
		String aux = String.valueOf(this.transferenciaPercentual);
		aux = aux.replace(".", ",");
		return aux;
	}

	@Transient
	public String getTransferenciaMetrosFormatado() {
		return Valores.formatarParaDecimal(this.transferenciaMetros, 2);
	}

}
