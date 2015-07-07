package gov.pr.celepar.abi.dto;


public class VistoriaDTO {

	private Integer codVistoria;
	private Integer codBemImovel;
    private String dataVistoriaFormatada;
    private String vistoriador;
    private String denominacao;
    
    
    public VistoriaDTO() {
    }


	public Integer getCodVistoria() {
		return codVistoria;
	}


	public void setCodVistoria(Integer codVistoria) {
		this.codVistoria = codVistoria;
	}


	public Integer getCodBemImovel() {
		return codBemImovel;
	}


	public void setCodBemImovel(Integer codBemImovel) {
		this.codBemImovel = codBemImovel;
	}


	public String getDataVistoriaFormatada() {
		return dataVistoriaFormatada;
	}


	public void setDataVistoriaFormatada(String dataVistoriaFormatada) {
		this.dataVistoriaFormatada = dataVistoriaFormatada;
	}


	public String getVistoriador() {
		return vistoriador;
	}


	public void setVistoriador(String vistoriador) {
		this.vistoriador = vistoriador;
	}


	public String getDenominacao() {
		return denominacao;
	}


	public void setDenominacao(String denominacao) {
		this.denominacao = denominacao;
	}
    

	
}
