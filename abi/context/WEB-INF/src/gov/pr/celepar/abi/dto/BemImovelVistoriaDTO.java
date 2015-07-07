package gov.pr.celepar.abi.dto;

import java.util.Date;

public class BemImovelVistoriaDTO {

	private Integer codBemImovel;
    private String denominacao;
    private Date dataUltimaVistoria;
    
    
    public BemImovelVistoriaDTO(Integer codBemImovel, String denominacao, Date dataUltimaVistoria) {
        this.codBemImovel = codBemImovel;
        this.denominacao = denominacao;
        this.dataUltimaVistoria = dataUltimaVistoria;
    }

	public Integer getCodBemImovel() {
		return codBemImovel;
	}
	public void setCodBemImovel(Integer codBemImovel) {
		this.codBemImovel = codBemImovel;
	}
	public String getDenominacao() {
		return denominacao;
	}
	public void setDenominacao(String denominacao) {
		this.denominacao = denominacao;
	}
	public Date getDataUltimaVistoria() {
		return dataUltimaVistoria;
	}
	public void setDataUltimaVistoria(Date dataUltimaVistoria) {
		this.dataUltimaVistoria = dataUltimaVistoria;
	}    
    

	
	
}
