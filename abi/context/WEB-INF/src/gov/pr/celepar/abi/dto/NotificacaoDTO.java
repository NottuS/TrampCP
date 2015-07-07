package gov.pr.celepar.abi.dto;


public class NotificacaoDTO {

	private Integer codNotificacao;
	private Integer codBemImovel;
    private String prazo;
    private String descricao;
    
    
    public NotificacaoDTO() {
    }


	public Integer getCodNotificacao() {
		return codNotificacao;
	}


	public void setCodNotificacao(Integer codNotificacao) {
		this.codNotificacao = codNotificacao;
	}


	public Integer getCodBemImovel() {
		return codBemImovel;
	}


	public void setCodBemImovel(Integer codBemImovel) {
		this.codBemImovel = codBemImovel;
	}


	public String getPrazo() {
		return prazo;
	}


	public void setPrazo(String prazo) {
		this.prazo = prazo;
	}


	public String getDescricao() {
		return descricao;
	}


	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


}
