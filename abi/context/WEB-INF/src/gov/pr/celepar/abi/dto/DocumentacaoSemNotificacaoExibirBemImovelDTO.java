package gov.pr.celepar.abi.dto;

import gov.pr.celepar.abi.util.Dominios;
import gov.pr.celepar.abi.util.Util;

import java.util.Date;

public class DocumentacaoSemNotificacaoExibirBemImovelDTO {

	private Integer codDocumentacao;
    private String descricao;
    private String anexo;    
    private Date tsInclusao;
    private Date tsAtualizacao;
    private String responsavelDocumentacao;
    private String tipoDocumentacao;
    private String detalhes;
    private String cartorio;
    private String tabelionato;
    private String numDocCartorial;
    private String numDocTabelional;
    private String niif;
    private String nirf;
    private String incra;
    private Integer classificacaoBemImovel;
    private String detalhesRelatorio;
    
    
    public String getCartorio() {
		return cartorio;
	}
	public void setCartorio(String cartorio) {
		this.cartorio = cartorio;
	}
	public String getTabelionato() {
		return tabelionato;
	}
	public void setTabelionato(String tabelionato) {
		this.tabelionato = tabelionato;
	}
	public String getNumDocCartorial() {
		return numDocCartorial;
	}
	public void setNumDocCartorial(String numDocCartorial) {
		this.numDocCartorial = numDocCartorial;
	}
	
	public String getNiif() {
		return niif;
	}
	public void setNiif(String niif) {
		this.niif = niif;
	}
	public String getNirf() {
		return nirf;
	}
	public void setNirf(String nirf) {
		this.nirf = nirf;
	}
	public String getIncra() {
		return incra;
	}
	public void setIncra(String incra) {
		this.incra = incra;
	}
	public DocumentacaoSemNotificacaoExibirBemImovelDTO getInstanciaAtual(){
    	return this;
    }
	public Integer getCodDocumentacao() {
		return codDocumentacao;
	}
	public String getAnexo() {
		return anexo;
	}
	public String getDescricao() {
		return descricao;
	}
	public Date getTsInclusao() {
		return tsInclusao;
	}
	public Date getTsAtualizacao() {
		return tsAtualizacao;
	}
	public String getResponsavelDocumentacao() {
		return responsavelDocumentacao;
	}
	public void setCodDocumentacao(Integer codDocumentacao) {
		this.codDocumentacao = codDocumentacao;
	}
	public void setAnexo(String anexo) {
		this.anexo = anexo;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public void setTsInclusao(Date tsInclusao) {
		this.tsInclusao = tsInclusao;
	}
	public void setTsAtualizacao(Date tsAtualizacao) {
		this.tsAtualizacao = tsAtualizacao;
	}
	public void setResponsavelDocumentacao(String responsavelDocumentacao) {
		this.responsavelDocumentacao = responsavelDocumentacao;
	}
	
	public void setTipoDocumentacao(String tipoDocumentacao) {
		this.tipoDocumentacao = tipoDocumentacao;
	}
	public String getTipoDocumentacao() {
		return tipoDocumentacao;
	}
	public void setDetalhes(String detalhes) {
		this.detalhes = detalhes;
	}
	
	public String getDescricaoFormatada(){
		String aux = "<b>".concat(descricao.replace("\n", "<b><br>"));
		aux = aux.replace(":", "</b>:");
		return aux;
	}
	
	public String getDetalhes() {
		StringBuffer aux = new StringBuffer();
		if (!Util.strEmBranco(getCartorio())){
			aux.append("<b>Cartório:</b> ").append(getCartorio()).append("<br>");
			
		}
		if(!Util.strEmBranco(getNumDocCartorial())){
			aux.append("<b>Número Documento Cartorial:</b> ").append(getNumDocCartorial()).append("<br>");
		}
		if (this.classificacaoBemImovel != null && this.classificacaoBemImovel.equals(Dominios.classificacaoImovel.CLASSIFICACAO_RURAL.getIndex())){
			if (!Util.strEmBranco(nirf)){
				aux.append("<b>Nirf:</b> ").append(getNirf()).append("<br>");
			}
			if (!Util.strEmBranco(incra)){
				aux.append("<b>Incra:</b> ").append(getIncra()).append("<br>");
			}
		}else{
			if (!Util.strEmBranco(niif)){
				aux.append("<b>Niif:</b> ").append(getNiif()).append("<br>");
			}
		}
		if (!Util.strEmBranco(getTabelionato())){
			aux.append("<b>Tabelionato:</b> ").append(getTabelionato()).append("<br>");
			
		}
		if(!Util.strEmBranco(getNumDocTabelional())){
			aux.append("<b>Número Documento Tabelional:</b> ").append(getNumDocTabelional()).append("<br>");
		}
		this.detalhes= aux.toString();
		return detalhes;
	}
	public void setClassificacaoBemImovel(Integer classificacaoBemImovel) {
		this.classificacaoBemImovel = classificacaoBemImovel;
	}
	public Integer getClassificacaoBemImovel() {
		return classificacaoBemImovel;
	}
	public void setNumDocTabelional(String numDocTabelional) {
		this.numDocTabelional = numDocTabelional;
	}
	public String getNumDocTabelional() {
		return numDocTabelional;
	}
	public void setDetalhesRelatorio(String detalhesRelatorio) {
		this.detalhesRelatorio = detalhesRelatorio;
	}
	public String getDetalhesRelatorio() {
		StringBuffer aux = new StringBuffer();
		if (!Util.strEmBranco(getCartorio())){
			aux.append("Cartório: ").append(getCartorio()).append("\n");
			
		}
		if(!Util.strEmBranco(getNumDocCartorial())){
			aux.append("Número Documento Cartorial: ").append(getNumDocCartorial()).append("\n");
		}
		if (this.classificacaoBemImovel != null && this.classificacaoBemImovel.equals(Dominios.classificacaoImovel.CLASSIFICACAO_RURAL.getIndex())){
			if (!Util.strEmBranco(nirf)){
				aux.append("Nirf: ").append(getNirf()).append("\n");
			}
			if (!Util.strEmBranco(incra)){
				aux.append("Incra: ").append(getIncra()).append("\n");
			}
		}else{
			if (!Util.strEmBranco(niif)){
				aux.append("Niif: ").append(getNiif()).append("\n");
			}
		}
		if (!Util.strEmBranco(getTabelionato())){
			aux.append("Tabelionato: ").append(getTabelionato()).append("\n");
			
		}
		if(!Util.strEmBranco(getNumDocTabelional())){
			aux.append("Número Documento Tabelional: ").append(getNumDocTabelional());
		}
		this.detalhesRelatorio= aux.toString();
		return detalhesRelatorio;
	}	
	
}
