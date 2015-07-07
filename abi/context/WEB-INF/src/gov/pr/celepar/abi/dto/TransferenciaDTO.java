package gov.pr.celepar.abi.dto;

import java.util.ArrayList;
import java.util.List;


/**
 * Classe criada para encapsular os dados a ser exibido no relatorio do UCS_GerarTermoTransferenciaBemImovel.<BR>
 * @author ginaalmeida
 * @since 02/08/2011
 */
public class TransferenciaDTO {

	private Integer codTransferencia;
	private Integer codBemImovel;
	private String numeroAnoTermo;
	private String numeroTermo;
	private String cedente;
	private String cessionario;
	private String motivoRevogacao;
	private String numOficio;
	private String dataPorExtenso;
	private String dataRegistre;
    private List<ItemTransferenciaDTO> listaItemTransferenciaDTO = new ArrayList<ItemTransferenciaDTO>(0);
    private List<AssinaturaDTO> listaAssinaturaDTO = new ArrayList<AssinaturaDTO>(0);
    private List<AssinaturaDTO> listaAssinaturaCpeDTO = new ArrayList<AssinaturaDTO>(0);
    
    private List<DocInformacaoDTO> listaDocInformacaoDTO = new ArrayList<DocInformacaoDTO>(0);

    public TransferenciaDTO() {
    }

	public String getDataRegistre() {
		return dataRegistre;
	}
	public void setDataRegistre(String dataRegistre) {
		this.dataRegistre = dataRegistre;
	}
	public Integer getCodTransferencia() {
		return codTransferencia;
	}
	public void setCodTransferencia(Integer codTransferencia) {
		this.codTransferencia = codTransferencia;
	}
	public Integer getCodBemImovel() {
		return codBemImovel;
	}
	public void setCodBemImovel(Integer codBemImovel) {
		this.codBemImovel = codBemImovel;
	}
	public String getNumeroAnoTermo() {
		return numeroAnoTermo;
	}
	public void setNumeroAnoTermo(String numeroAnoTermo) {
		this.numeroAnoTermo = numeroAnoTermo;
	}
	public String getNumeroTermo() {
		return numeroTermo;
	}
	public void setNumeroTermo(String numeroTermo) {
		this.numeroTermo = numeroTermo;
	}
	public String getCedente() {
		return cedente;
	}
	public void setCedente(String cedente) {
		this.cedente = cedente;
	}
	public String getCessionario() {
		return cessionario;
	}
	public void setCessionario(String cessionario) {
		this.cessionario = cessionario;
	}
	public String getDataPorExtenso() {
		return dataPorExtenso;
	}
	public void setDataPorExtenso(String dataPorExtenso) {
		this.dataPorExtenso = dataPorExtenso;
	}
	public List<ItemTransferenciaDTO> getListaItemTransferenciaDTO() {
		return listaItemTransferenciaDTO;
	}
	public void setListaItemTransferenciaDTO(
			List<ItemTransferenciaDTO> listaItemTransferenciaDTO) {
		this.listaItemTransferenciaDTO = listaItemTransferenciaDTO;
	}
	public String getMotivoRevogacao() {
		return motivoRevogacao;
	}
	public void setMotivoRevogacao(String motivoRevogacao) {
		this.motivoRevogacao = motivoRevogacao;
	}
	public String getNumOficio() {
		return numOficio;
	}
	public void setNumOficio(String numOficio) {
		this.numOficio = numOficio;
	}
	public List<AssinaturaDTO> getListaAssinaturaDTO() {
		return listaAssinaturaDTO;
	}
	public void setListaAssinaturaDTO(List<AssinaturaDTO> listaAssinaturaDTO) {
		this.listaAssinaturaDTO = listaAssinaturaDTO;
	}
	public List<AssinaturaDTO> getListaAssinaturaCpeDTO() {
		return listaAssinaturaCpeDTO;
	}
	public void setListaAssinaturaCpeDTO(List<AssinaturaDTO> listaAssinaturaCpeDTO) {
		this.listaAssinaturaCpeDTO = listaAssinaturaCpeDTO;
	}

	public void setListaDocInformacaoDTO(List<DocInformacaoDTO> listaDocInformacaoDTO) {
		this.listaDocInformacaoDTO = listaDocInformacaoDTO;
	}

	public List<DocInformacaoDTO> getListaDocInformacaoDTO() {
		return listaDocInformacaoDTO;
	}

}