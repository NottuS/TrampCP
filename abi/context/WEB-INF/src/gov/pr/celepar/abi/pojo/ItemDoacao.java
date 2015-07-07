package gov.pr.celepar.abi.pojo;

import gov.pr.celepar.abi.util.Dominios;

import java.math.BigDecimal;

import javax.persistence.Transient;

public class ItemDoacao implements java.io.Serializable {

	private static final long serialVersionUID = -1257079438209532945L;
    private Integer tipo;
	private Integer codItemDoacao;
    private Edificacao edificacao;
    private Doacao doacao;
    private BigDecimal doacaoMetros;
    private BigDecimal doacaoPercentual;
    private String utilizacao;
    private String observacao;
    private Boolean somenteTerreno;
    
	public ItemDoacao(Integer codItemDoacao, Edificacao edificacao,
			Doacao doacao, BigDecimal doacaoMetros,
			BigDecimal doacaoPercentual, String utilizacao, String observacao,
			Boolean somenteTerreno, Integer tipo) {
		super();
		this.codItemDoacao = codItemDoacao;
		this.edificacao = edificacao;
		this.doacao = doacao;
		this.doacaoMetros = doacaoMetros;
		this.doacaoPercentual = doacaoPercentual;
		this.utilizacao = utilizacao;
		this.observacao = observacao;
		this.somenteTerreno = somenteTerreno;
		this.tipo = tipo;
	}

	public ItemDoacao() {
		// TODO Auto-generated constructor stub
	}

	public Integer getCodItemDoacao() {
		return codItemDoacao;
	}

	public void setCodItemDoacao(Integer codItemDoacao) {
		this.codItemDoacao = codItemDoacao;
	}

	public Edificacao getEdificacao() {
		return edificacao;
	}

	public void setEdificacao(Edificacao edificacao) {
		this.edificacao = edificacao;
	}

	public Doacao getDoacao() {
		return doacao;
	}

	public void setDoacao(Doacao doacao) {
		this.doacao = doacao;
	}

	public BigDecimal getDoacaoMetros() {
		return doacaoMetros;
	}

	public void setDoacaoMetros(BigDecimal doacaoMetros) {
		this.doacaoMetros = doacaoMetros;
	}

	public BigDecimal getDoacaoPercentual() {
		return doacaoPercentual;
	}

	public void setDoacaoPercentual(BigDecimal doacaoPercentual) {
		this.doacaoPercentual = doacaoPercentual;
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

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
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

	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemDoacao other = (ItemDoacao) obj;
		if (codItemDoacao == null) {
			if (other.codItemDoacao != null)
				return false;
		} else if (!codItemDoacao.equals(other.codItemDoacao))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codItemDoacao == null) ? 0 : codItemDoacao.hashCode());
		return result;
	}

	@Transient
	public String getDoacaoPercentualFormatado() {
		String aux = String.valueOf(this.doacaoPercentual);
		aux = aux.replace(".", ",");
		return aux;
	}

}
