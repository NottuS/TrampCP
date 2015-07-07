package gov.pr.celepar.abi.dto;

import java.util.Date;

public class LeiBemImovelExibirBemImovelDTO {

    private Integer codLeiBemImovel;
	private String tipoLei;
	private Long numero;
	private Date dataAssinatura;
	private Date dataPublicacao;
	private Long nrDioe;
	
	public Integer getCodLeiBemImovel() {
		return codLeiBemImovel;
	}
	public void setCodLeiBemImovel(Integer codLeiBemImovel) {
		this.codLeiBemImovel = codLeiBemImovel;
	}
	public Long getNumero() {
		return numero;
	}
	public void setNumero(Long numero) {
	this.numero = numero;
}	
	public Date getDataAssinatura() {
		return dataAssinatura;
	}
	public Date getDataPublicacao() {
		return dataPublicacao;
	}

	public void setDataAssinatura(Date dataAssinatura) {
		this.dataAssinatura = dataAssinatura;
	}
	public void setDataPublicacao(Date dataPublicacao) {
		this.dataPublicacao = dataPublicacao;
	}
	public String getTipoLei() {
		return tipoLei;
	}
	public void setTipoLei(String tipoLei) {
		this.tipoLei = tipoLei;
	}
	public void setNrDioe(Long nrDioe) {
		this.nrDioe = nrDioe;
	}
	public Long getNrDioe() {
		return nrDioe;
	}

	
	
}
