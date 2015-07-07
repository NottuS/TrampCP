package gov.pr.celepar.abi.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe criada para formatar a exibicao dos dados para gerar TermoCessaoDeUso.<BR>
 * @author ginaalmeida
 * @since 01/08/2011
 */
public class CessaoDeUsoDTO {

	private String numOficio;
    private Integer codCessaoDeUso;
	private String protocolo;
    private String dataInicioFormatada;
    private String dataFimFormatada;
    private String orgao;
	private String numeroAnoTermo;
    
    //Utilizados no UCS_GerarTermoCessaoDeUso
	private String numeroTermo;
	private Integer codBemImovel;
	private String numeroLei;
	private String dataPublicacaoLei;
	private String numeroDioe;
	private String dataPublicacaoDioe;
	private String cedente;
	private String cessionario;
	private String dataPorExtenso;
	private String dataRegistre;
	private String motivoRevogadaDevolucao;
	
	private List<ItemCessaoDeUsoDTO> listaItemCessaoDeUsoDTO = new ArrayList<ItemCessaoDeUsoDTO>(0);
	private List<AssinaturaDTO> listaAssinaturaDTO = new ArrayList<AssinaturaDTO>(0);
	private List<AssinaturaDTO> listaAssinaturaCpeDTO = new ArrayList<AssinaturaDTO>(0);
	
	
    

    
    
    public CessaoDeUsoDTO() {
    }


	public Integer getCodBemImovel() {
		return codBemImovel;
	}
	public void setCodBemImovel(Integer codBemImovel) {
		this.codBemImovel = codBemImovel;
	}
	public String getDataInicioFormatada() {
		return dataInicioFormatada;
	}
	public void setDataInicioFormatada(String dataInicioFormatada) {
		this.dataInicioFormatada = dataInicioFormatada;
	}


	public String getDataFimFormatada() {
		return dataFimFormatada;
	}


	public void setDataFimFormatada(String dataFimFormatada) {
		this.dataFimFormatada = dataFimFormatada;
	}


	public String getOrgao() {
		return orgao;
	}


	public void setOrgao(String orgao) {
		this.orgao = orgao;
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
	public String getNumeroLei() {
		return numeroLei;
	}
	public void setNumeroLei(String numeroLei) {
		this.numeroLei = numeroLei;
	}
	public String getDataPublicacaoLei() {
		return dataPublicacaoLei;
	}
	public void setDataPublicacaoLei(String dataPublicacaoLei) {
		this.dataPublicacaoLei = dataPublicacaoLei;
	}
	public String getNumeroDioe() {
		return numeroDioe;
	}
	public void setNumeroDioe(String numeroDioe) {
		this.numeroDioe = numeroDioe;
	}
	public String getDataPublicacaoDioe() {
		return dataPublicacaoDioe;
	}
	public void setDataPublicacaoDioe(String dataPublicacaoDioe) {
		this.dataPublicacaoDioe = dataPublicacaoDioe;
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
	public List<ItemCessaoDeUsoDTO> getListaItemCessaoDeUsoDTO() {
		return listaItemCessaoDeUsoDTO;
	}
	public void setListaItemCessaoDeUsoDTO(
			List<ItemCessaoDeUsoDTO> listaItemCessaoDeUsoDTO) {
		this.listaItemCessaoDeUsoDTO = listaItemCessaoDeUsoDTO;
	}
	public String getMotivoRevogadaDevolucao() {
		return motivoRevogadaDevolucao;
	}
	public void setMotivoRevogadaDevolucao(String motivoRevogadaDevolucao) {
		this.motivoRevogadaDevolucao = motivoRevogadaDevolucao;
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
	public String getDataRegistre() {
		return dataRegistre;
	}
	public void setDataRegistre(String dataRegistre) {
		this.dataRegistre = dataRegistre;
	}
	public List<AssinaturaDTO> getListaAssinaturaCpeDTO() {
		return listaAssinaturaCpeDTO;
	}
	public void setListaAssinaturaCpeDTO(List<AssinaturaDTO> listaAssinaturaCpeDTO) {
		this.listaAssinaturaCpeDTO = listaAssinaturaCpeDTO;
	}
	public Integer getCodCessaoDeUso() {
		return codCessaoDeUso;
	}
	public void setCodCessaoDeUso(Integer codCessaoDeUso) {
		this.codCessaoDeUso = codCessaoDeUso;
	}


	public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
	}


	public String getProtocolo() {
		return protocolo;
	}
	
}