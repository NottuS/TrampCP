package gov.pr.celepar.abi.dto;

import java.util.ArrayList;
import java.util.List;


public class DoacaoDTO {

	private Integer codDoacao;
	private Integer codBemImovel;
	private String protocolo;
    private String dataInicioFormatada;
    private String dataFimFormatada;
    private String orgao;
    
    
    //usando no relatorio UCS_GerarTermoDoacaoBemImovel
    private String numeroAnoTermo;
    private String numeroTermo;
    private String numeroLei;
    private String dataPublicacaoLei;
    private String numeroDioe;
    private String dataPublicacaoDioe;
    private String cedente;
    private String cessionario;
    private String dataPorExtenso;
    private String motivoRevogacao;
    private String numOficio;
	private String dataRegistre;
    private List<ItemDoacaoDTO> listaItemDoacaoDTO = new ArrayList<ItemDoacaoDTO>(0);
    private List<AssinaturaDTO> listaAssinaturaDTO = new ArrayList<AssinaturaDTO>(0);
	private List<AssinaturaDTO> listaAssinaturaCpeDTO = new ArrayList<AssinaturaDTO>(0);
    
    public DoacaoDTO() {
    }


	public Integer getCodDoacao() {
		return codDoacao;
	}


	public void setCodDoacao(Integer codDoacao) {
		this.codDoacao = codDoacao;
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
	public List<ItemDoacaoDTO> getListaItemDoacaoDTO() {
		return listaItemDoacaoDTO;
	}
	public void setListaItemDoacaoDTO(List<ItemDoacaoDTO> listaItemDoacaoDTO) {
		this.listaItemDoacaoDTO = listaItemDoacaoDTO;
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


	public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
	}


	public String getProtocolo() {
		return protocolo;
	}


	public void setDataRegistre(String dataRegistre) {
		this.dataRegistre = dataRegistre;
	}


	public String getDataRegistre() {
		return dataRegistre;
	}


	public void setListaAssinaturaCpeDTO(List<AssinaturaDTO> listaAssinaturaCpeDTO) {
		this.listaAssinaturaCpeDTO = listaAssinaturaCpeDTO;
	}


	public List<AssinaturaDTO> getListaAssinaturaCpeDTO() {
		return listaAssinaturaCpeDTO;
	}
	
}