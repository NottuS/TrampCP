
package gov.pr.celepar.abi.form;

import org.apache.struts.validator.ValidatorForm;


/**
 * @author Luciana R. Bélico
 * @version 1.0
 * @since 10/02/2010
 * 
 * Responsável por encapsular os dados das telas de Tipo de Lei de Bem Imovel.
 */
public class ImpressaoBemImovelForm extends ValidatorForm {
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -8077684531185632817L;

	private String actionType;
	
	private String codBemImovel;
	private String municipio;
	private String completo;	
	private String personalizado;
	private String identificacao;
	private String leis;
	private String quadras;	
	private String confrontante;
	private String avaliacao;
	private String coordenadaUTM;
	private String edificacao;
	private String situacaoOcupacao;
	private String documentacao;
	private String ocorrencia;
	private String documentacaoNotificacao;
	private String documentacaoSemNotificacao;
	private String tipoRelatorio;
	private String ocupacaoTerreno;
	
	
	public String getLeis() {
		return leis;
	}
	public void setLeis(String leis) {
		this.leis = leis;
	}
	public void setQuadras(String quadras) {
		this.quadras = quadras;
	}
	public String getQuadras() {
		return quadras;
	}
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	
	public String getCodBemImovel() {
		return codBemImovel;
	}
	public void setCodBemImovel(String codBemImovel) {
		this.codBemImovel = codBemImovel;
	}
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	public String getMunicipio() {
		return municipio;
	}
	
	public String getCompleto() {
		return completo;
	}
	public String getPersonalizado() {
		return personalizado;
	}
	public void setCompleto(String completo) {
		this.completo = completo;
	}
	public void setPersonalizado(String personalizado) {
		this.personalizado = personalizado;
	}
	public String getIdentificacao() {
		return identificacao;
	}
	public String getConfrontante() {
		return confrontante;
	}
	public String getAvaliacao() {
		return avaliacao;
	}
	public String getCoordenadaUTM() {
		return coordenadaUTM;
	}
	public String getEdificacao() {
		return edificacao;
	}
	public String getSituacaoOcupacao() {
		return situacaoOcupacao;
	}
	public String getDocumentacao() {
		return documentacao;
	}
	public String getOcorrencia() {
		return ocorrencia;
	}
	public void setIdentificacao(String identificacao) {
		this.identificacao = identificacao;
	}
	public void setConfrontante(String confrontante) {
		this.confrontante = confrontante;
	}
	public void setAvaliacao(String avaliacao) {
		this.avaliacao = avaliacao;
	}
	public void setCoordenadaUTM(String coordenadaUTM) {
		this.coordenadaUTM = coordenadaUTM;
	}
	public void setEdificacao(String edificacao) {
		this.edificacao = edificacao;
	}
	public void setSituacaoOcupacao(String situacaoOcupacao) {
		this.situacaoOcupacao = situacaoOcupacao;
	}
	public void setDocumentacao(String documentacao) {
		this.documentacao = documentacao;
	}
	public void setOcorrencia(String ocorrencia) {
		this.ocorrencia = ocorrencia;
	}
	public String getDocumentacaoNotificacao() {
		return documentacaoNotificacao;
	}
	public String getDocumentacaoSemNotificacao() {
		return documentacaoSemNotificacao;
	}
	public void setDocumentacaoNotificacao(String documentacaoNotificacao) {
		this.documentacaoNotificacao = documentacaoNotificacao;
	}
	public void setDocumentacaoSemNotificacao(String documentacaoSemNotificacao) {
		this.documentacaoSemNotificacao = documentacaoSemNotificacao;
	}
	public String getTipoRelatorio() {
		return tipoRelatorio;
	}
	public void setTipoRelatorio(String tipoRelatorio) {
		this.tipoRelatorio = tipoRelatorio;
	}
	public void setOcupacaoTerreno(String ocupacaoTerreno) {
		this.ocupacaoTerreno = ocupacaoTerreno;
	}
	public String getOcupacaoTerreno() {
		return ocupacaoTerreno;
	}
	
	
	
	


}
