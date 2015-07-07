package gov.pr.celepar.abi.dto;

/**
 * Classe criada para formatar a exibicao da lista ItemTransferencia no relatorio do UCS_GerarTermoTransferenciaBemImovel.<BR>
 * @author ginaalmeida
 * @since 02/08/2011
 */
public class ItemTransferenciaDTO {

    private String edificacao;
    private String caracteristica;
    private String situacao;
    private String utilizacao;
    private String observacao;
    
    
    
    
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