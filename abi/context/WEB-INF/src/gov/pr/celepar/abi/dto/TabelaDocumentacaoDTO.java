package gov.pr.celepar.abi.dto;

import gov.pr.celepar.abi.util.Dominios;
import gov.pr.celepar.abi.util.Util;

import java.io.Serializable;
import java.util.Date;


/**
 * @author Luciana Bélico
 */
public class TabelaDocumentacaoDTO implements Serializable{
	
	
	private static final long serialVersionUID = -95205333597017538L;
	
	private Integer codDocumentacao;
	private Integer codNotificacao;
	private String anexo;
	private String descricao;
	private Date tsInclusao;
	private Date tsAtualizacao;
	private Date tsNotificacao;
	private Date tsPrazoNotificacao;
	private String responsavelDocumentacao;
	private String tipoDocumentacao;
	private String descricaoAnexo;
	private String cartorio;
	private String tabelionato;
	private String numDocCartorial;
	private String numDocTabelional;
	private String niif;
	private String nirf;
	private String incra;
	private Integer classificacaoBemImovel;
	private String detalhes;
	
	    
	    
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

	public String getNumDocTabelional() {
		return numDocTabelional;
	}

	public void setNumDocTabelional(String numDocTabelional) {
		this.numDocTabelional = numDocTabelional;
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

	public Integer getClassificacaoBemImovel() {
		return classificacaoBemImovel;
	}

	public void setClassificacaoBemImovel(Integer classificacaoBemImovel) {
		this.classificacaoBemImovel = classificacaoBemImovel;
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
	
	public TabelaDocumentacaoDTO getInstanciaAtual(){
		return this;
	}
	
	public Integer getCodDocumentacao() {
		return codDocumentacao;
	}
	public void setCodDocumentacao(Integer codDocumentacao) {
		this.codDocumentacao = codDocumentacao;
	}
	public Integer getCodNotificacao() {
		return codNotificacao;
	}
	public void setCodNotificacao(Integer codNotificacao) {
		this.codNotificacao = codNotificacao;
	}
	public String getAnexo() {
		return anexo;
	}
	public void setAnexo(String anexo) {
		this.anexo = anexo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Date getTsInclusao() {
		return tsInclusao;
	}
	public void setTsInclusao(Date tsInclusao) {
		this.tsInclusao = tsInclusao;
	}
	public Date getTsAtualizacao() {
		return tsAtualizacao;
	}
	public void setTsAtualizacao(Date tsAtualizacao) {
		this.tsAtualizacao = tsAtualizacao;
	}
	public Date getTsNotificacao() {
		return tsNotificacao;
	}
	public void setTsNotificacao(Date tsNotificacao) {
		this.tsNotificacao = tsNotificacao;
	}
	public Date getTsPrazoNotificacao() {
		return tsPrazoNotificacao;
	}
	public void setTsPrazoNotificacao(Date tsPrazoNotificacao) {
		this.tsPrazoNotificacao = tsPrazoNotificacao;
	}
	public String getResponsavelDocumentacao() {
		return responsavelDocumentacao;
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

	public void setDescricaoAnexo(String descricaoAnexo) {
		this.descricaoAnexo = descricaoAnexo;
	}

	public String getDescricaoAnexo() {
		return descricaoAnexo;
	}
	
	
	
	
}
