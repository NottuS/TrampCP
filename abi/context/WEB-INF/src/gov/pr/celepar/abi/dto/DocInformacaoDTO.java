package gov.pr.celepar.abi.dto;

import java.util.ArrayList;
import java.util.List;

public class DocInformacaoDTO {

	private Integer codBemImovel;
	private String numeroTermo;
	private String dataPorExtenso;
	private String siglaCessionario;
	private String protocolo;
	private String textoDocInformacao;
	private String assinaturaRespMaximo;
	private String cargoRespMaximo;
    private List<AssinaturaDTO> listaAssinaturaDTO = new ArrayList<AssinaturaDTO>(0);
		
	public Integer getCodBemImovel() {
		return codBemImovel;
	}
	public void setCodBemImovel(Integer codBemImovel) {
		this.codBemImovel = codBemImovel;
	}
	public String getNumeroTermo() {
		return numeroTermo;
	}
	public void setNumeroTermo(String numeroTermo) {
		this.numeroTermo = numeroTermo;
	}
	public String getDataPorExtenso() {
		return dataPorExtenso;
	}
	public void setDataPorExtenso(String dataPorExtenso) {
		this.dataPorExtenso = dataPorExtenso;
	}
	public String getSiglaCessionario() {
		return siglaCessionario;
	}
	public void setSiglaCessionario(String siglaCessionario) {
		this.siglaCessionario = siglaCessionario;
	}
	public String getProtocolo() {
		return protocolo;
	}
	public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
	}
	public String getTextoDocInformacao() {
		return textoDocInformacao;
	}
	public void setTextoDocInformacao(String textoDocInformacao) {
		this.textoDocInformacao = textoDocInformacao;
	}
	public void setListaAssinaturaDTO(List<AssinaturaDTO> listaAssinaturaDTO) {
		this.listaAssinaturaDTO = listaAssinaturaDTO;
	}
	public List<AssinaturaDTO> getListaAssinaturaDTO() {
		return listaAssinaturaDTO;
	}
	public void setAssinaturaRespMaximo(String assinaturaRespMaximo) {
		this.assinaturaRespMaximo = assinaturaRespMaximo;
	}
	public String getAssinaturaRespMaximo() {
		return assinaturaRespMaximo;
	}
	public void setCargoRespMaximo(String cargoRespMaximo) {
		this.cargoRespMaximo = cargoRespMaximo;
	}
	public String getCargoRespMaximo() {
		return cargoRespMaximo;
	}

}
