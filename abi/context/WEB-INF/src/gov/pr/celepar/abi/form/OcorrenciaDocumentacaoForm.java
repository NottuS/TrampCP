package gov.pr.celepar.abi.form;

import org.apache.struts.upload.FormFile;


/**
 * @author Luciana R. Bélico
 * @version 1.0
 * @since 19/01/2010
 * 
 * Responsável por encapsular os dados da tela de ocorrencia de documentacao.
 */
public class OcorrenciaDocumentacaoForm extends BemImovelForm {


	private static final long serialVersionUID = -3527096916504195684L;
	private String actionType;
	private String tsInclusao;
	private String tipoDocumentacao ;
	private String relDocumentacao;
	private String docExistente;
	private String codBemImovel;
	private String documentacao; //guarda dados do campo documentação
	private String documentacaoOriginal;
	private String tsNotificacao;
	private String tsPrazoNotificacao;
	private String descricaoNotificacao;
	private String tsSolucao;
	private String motivoSolucao;
	private String descricaoOcorrencia;
	private FormFile anexo;
	
	
	
	
	public FormFile getAnexo() {
		return anexo;
	}
	public void setAnexo(FormFile anexo) {
		this.anexo = anexo;
	}
	public String getTsNotificacao() {
		return tsNotificacao;
	}
	public void setTsNotificacao(String tsNotificacao) {
		this.tsNotificacao = tsNotificacao;
	}
	
	public String getTsInclusao() {
		return tsInclusao;
	}
	public void setTsInclusao(String tsInclusao) {
		this.tsInclusao = tsInclusao;
	}
	public String getDescricaoOcorrencia() {
		return descricaoOcorrencia;
	}
	public void setDescricaoOcorrencia(String descricaoOcorrencia) {
		this.descricaoOcorrencia = descricaoOcorrencia;
	}
	public String getMotivoSolucao() {
		return motivoSolucao;
	}
	public void setMotivoSolucao(String motivoSolucao) {
		this.motivoSolucao = motivoSolucao;
	}
	public String getTsSolucao() {
		return tsSolucao;
	}
	public void setTsSolucao(String tsSolucao) {
		this.tsSolucao = tsSolucao;
	}
	public String getDescricaoNotificacao() {
		return descricaoNotificacao;
	}
	public void setDescricaoNotificacao(String descricaoNotificacao) {
		this.descricaoNotificacao = descricaoNotificacao;
	}
	public String getTsPrazoNotificacao() {
		return tsPrazoNotificacao;
	}
	public void setTsPrazoNotificacao(String tsPrazoNotificacao) {
		this.tsPrazoNotificacao = tsPrazoNotificacao;
	}
	
	public String getDocumentacaoOriginal() {
		return documentacaoOriginal;
	}
	public void setDocumentacaoOriginal(String documentacaoOriginal) {
		this.documentacaoOriginal = documentacaoOriginal;
	}
	public String getDocumentacao() {
		return documentacao;
	}
	public void setDocumentacao(String documentacao) {
		this.documentacao = documentacao;
	}
	public String getCodBemImovel() {
		return codBemImovel;
	}
	public void setCodBemImovel(String codBemImovel) {
		this.codBemImovel = codBemImovel;
	}
	public String getDocExistente() {
		return docExistente;
	}
	public void setDocExistente(String docExistente) {
		this.docExistente = docExistente;
	}
	public String getRelDocumentacao() {
		return relDocumentacao;
	}
	public void setRelDocumentacao(String relDocumentacao) {
		this.relDocumentacao = relDocumentacao;
	}
	public String getTipoDocumentacao() {
		return tipoDocumentacao;
	}
	public void setTipoDocumentacao(String tipoDocumentacao) {
		this.tipoDocumentacao = tipoDocumentacao;
	}
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

}
