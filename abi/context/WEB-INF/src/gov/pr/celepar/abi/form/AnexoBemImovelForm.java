package gov.pr.celepar.abi.form;

import org.apache.struts.upload.FormFile;


/**
 * @author 
 * @version 1.0
 * @since xx/01/2010
 * 
 * Responsável por encapsular os dados das telas de Anexo.
 */
public class AnexoBemImovelForm extends BemImovelForm {

	private static final long serialVersionUID = -765099217953006343L;
	private String actionType;
    private String tipoDocumentacaoAnexo;
    private String selBemImovel;
	private String edificacao;    
	private String codDocumentacao;
    private String descricao;
    private String descricaoAnexo;
    private String documentacao;
	private String codNotificacao;
    private String tsNotificacao;
	private String tsPrazoNotificacao;
	private String descricaoNotificacao;
	private String tsSolucao;
	private String motivoSolucao;
    private String responsavelDocumentacao;
	private FormFile anexo;
	
	public String getTsNotificacao() {
		return tsNotificacao;
	}
	public void setTsNotificacao(String tsNotificacao) {
		this.tsNotificacao = tsNotificacao;
	}
	public String getTsPrazoNotificacao() {
		return tsPrazoNotificacao;
	}
	public void setTsPrazoNotificacao(String tsPrazoNotificacao) {
		this.tsPrazoNotificacao = tsPrazoNotificacao;
	}
	public String getDescricaoNotificacao() {
		return descricaoNotificacao;
	}
	public void setDescricaoNotificacao(String descricaoNotificacao) {
		this.descricaoNotificacao = descricaoNotificacao;
	}
	public String getTsSolucao() {
		return tsSolucao;
	}
	public void setTsSolucao(String tsSolucao) {
		this.tsSolucao = tsSolucao;
	}
	public String getMotivoSolucao() {
		return motivoSolucao;
	}
	public void setMotivoSolucao(String motivoSolucao) {
		this.motivoSolucao = motivoSolucao;
	}
	public String getSelBemImovel() {
		return selBemImovel;
	}
	public void setSelBemImovel(String selBemImovel) {
		this.selBemImovel = selBemImovel;
	}
    
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String getCodDocumentacao() {
		return codDocumentacao;
	}
	public void setCodDocumentacao(String codDocumentacao) {
		this.codDocumentacao = codDocumentacao;
	}

	public String getEdificacao() {
		return edificacao;
	}
	public void setEdificacao(String edificacao) {
		this.edificacao = edificacao;
	}
	public String getTipoDocumentacaoAnexo() {
		return tipoDocumentacaoAnexo;
	}
	public void setTipoDocumentacaoAnexo(String tipoDocumentacao) {
		this.tipoDocumentacaoAnexo = tipoDocumentacao;
	}
	public String getDocumentacao() {
		return documentacao;
	}
	public void setDocumentacao(String documentacao) {
		this.documentacao = documentacao;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getResponsavelDocumentacao() {
		return responsavelDocumentacao;
	}
	public void setResponsavelDocumentacao(String responsavelDocumentacao) {
		this.responsavelDocumentacao = responsavelDocumentacao;
	}
	public void setAnexo(FormFile anexo) {
		this.anexo = anexo;
	}
	public FormFile getAnexo() {
		return anexo;
	}
	public void setCodNotificacao(String codNotificacao) {
		this.codNotificacao = codNotificacao;
	}
	public String getCodNotificacao() {
		return codNotificacao;
	}
	public void setDescricaoAnexo(String descricaoAnexo) {
		this.descricaoAnexo = descricaoAnexo;
	}
	public String getDescricaoAnexo() {
		return descricaoAnexo;
	}
	
	public void limparForm() {
	    this.setTipoDocumentacaoAnexo(null);
	    this.setSelBemImovel(null);
	    this.setEdificacao(null);    
	    this.setCodDocumentacao(null);
	    this.setDescricao(null);
	    this.setDocumentacao(null);
	    this.setCodNotificacao(null);
	    this.setTsNotificacao(null);
	    this.setTsPrazoNotificacao(null);
	    this.setDescricaoNotificacao(null);
	    this.setTsSolucao(null);
	    this.setMotivoSolucao(null);
	    this.setResponsavelDocumentacao(null);
	    this.setAnexo(null);
	    this.setDescricaoAnexo(null);
	}

}
