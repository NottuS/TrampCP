package gov.pr.celepar.abi.dto;

/**
 * Classe criada para formatar a exibicao da lista ItemDoacao no relatorio do UCS_GerarTermoDoacaoBemImovel.<BR>
 * @author ginaalmeida
 * @since 28/07/2011
 */
public class ItemDoacaoDTO {

    private String edificacao;
    private String utilizacao;
    private String observacao;
    
    
    
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