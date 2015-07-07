package gov.pr.celepar.abi.dto;

/**
 * Classe criada para formatar a exibicao da lista ItemCessaoDeUso no relatorio do UCS_GerarTermoCessaoDeUso.<BR>
 * @author ginaalmeida
 * @since 01/08/2011
 */
public class ItemCessaoDeUsoDTO {

    private String edificacao;
    private String areaEmMetros;
    private String areaEmPercentual;
    private String caracteristica;
    private String utilizacao;
    private String observacao;
	private String situacao;

	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	public String getCaracteristica() {
		return caracteristica;
	}
	public void setCaracteristica(String caracteristica) {
		this.caracteristica = caracteristica;
	}
	public String getAreaEmMetros() {
		return areaEmMetros;
	}
	public void setAreaEmMetros(String areaEmMetros) {
		this.areaEmMetros = areaEmMetros;
	}
	public String getAreaEmPercentual() {
		return areaEmPercentual;
	}
	public void setAreaEmPercentual(String areaEmPercentual) {
		this.areaEmPercentual = areaEmPercentual;
	}
	public String getEdificacao() {
		return edificacao;
	}
	public void setEdificacao(String edificacao) {
		this.edificacao = edificacao;
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
    
}