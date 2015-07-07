package gov.pr.celepar.abi.pojo;

import gov.pr.celepar.abi.util.Dominios;
import gov.pr.celepar.framework.util.Valores;

import java.math.BigDecimal;

import javax.persistence.Transient;

public class ItemCessaoDeUso implements java.io.Serializable {

	private static final long serialVersionUID = 6150805199987002409L;
	private Integer codItemCessaoDeUso;
    private Edificacao edificacao;
    private CessaoDeUso cessaoDeUso;
    private Boolean indTerreno;
    private BigDecimal areaMetroQuadrado;
    private BigDecimal areaPercentual;
	private String situacaoDominial;
    private String caracteristica;
    private String utilizacao;
    private String observacao;
    private Integer tipo;


	public ItemCessaoDeUso() {
		// TODO Auto-generated constructor stub
	}

	public Integer getCodItemCessaoDeUso() {
		return codItemCessaoDeUso;
	}

	public void setCodItemCessaoDeUso(Integer codItemCessaoDeUso) {
		this.codItemCessaoDeUso = codItemCessaoDeUso;
	}

	public Edificacao getEdificacao() {
		return edificacao;
	}

	public void setEdificacao(Edificacao edificacao) {
		this.edificacao = edificacao;
	}

	public CessaoDeUso getCessaoDeUso() {
		return cessaoDeUso;
	}

	public void setCessaoDeUso(CessaoDeUso cessaoDeUso) {
		this.cessaoDeUso = cessaoDeUso;
	}

	public Boolean getIndTerreno() {
		return indTerreno;
	}

	public void setIndTerreno(Boolean indTerreno) {
		this.indTerreno = indTerreno;
	}

	public String getCaracteristica() {
		return caracteristica;
	}

	public void setCaracteristica(String caracteristica) {
		this.caracteristica = caracteristica;
	}

	public BigDecimal getAreaMetroQuadrado() {
		return areaMetroQuadrado;
	}

	public void setAreaMetroQuadrado(BigDecimal areaMetroQuadrado) {
		this.areaMetroQuadrado = areaMetroQuadrado;
	}

	public BigDecimal getAreaPercentual() {
		return areaPercentual;
	}

	public void setAreaPercentual(BigDecimal areaPercentual) {
		this.areaPercentual = areaPercentual;
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
		ItemCessaoDeUso other = (ItemCessaoDeUso) obj;
		if (codItemCessaoDeUso == null) {
			if (other.codItemCessaoDeUso != null)
				return false;
		} else if (!codItemCessaoDeUso.equals(other.codItemCessaoDeUso))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codItemCessaoDeUso == null) ? 0 : codItemCessaoDeUso.hashCode());
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
			if (this.getIndTerreno()) {
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
	public String getAreaPercentualFormatado() {
		String aux = String.valueOf(this.areaPercentual);
		aux = aux.replace(".", ",");
		return aux;
	}
	
    @Transient
    public String getAreaMetroQuadradoFormatado(){
    	return Valores.formatarParaDecimal(this.areaMetroQuadrado, 2);
    }

}
