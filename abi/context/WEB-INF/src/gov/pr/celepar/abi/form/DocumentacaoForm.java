package gov.pr.celepar.abi.form;



/**
 * @author 
 * @version 1.0
 * @since xx/01/2010
 * 
 * Responsável por encapsular os dados das telas de Documentacao.
 */
public class DocumentacaoForm extends BemImovelForm {

	private static final long serialVersionUID = -1765637479951828097L;
	private String actionType;
	private String codDocumentacao;
    private String descricao;
    private String responsavelDocumentacao;
	private String cartorio;
	private String tabelionato;
	private String numeroDocumentoCartorial;
	private String numeroDocumentoTabelional;
	private String nirf;
    private String niif;
    private String incra;
	
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
	public String getNumeroDocumentoCartorial() {
		return numeroDocumentoCartorial;
	}
	public void setNumeroDocumentoCartorial(String numeroDocumentoCartorial) {
		this.numeroDocumentoCartorial = numeroDocumentoCartorial;
	}
	public String getNumeroDocumentoTabelional() {
		return numeroDocumentoTabelional;
	}
	public void setNumeroDocumentoTabelional(String numeroDocumentoTabelional) {
		this.numeroDocumentoTabelional = numeroDocumentoTabelional;
	}
	public String getNirf() {
		return nirf;
	}
	public void setNirf(String nirf) {
		this.nirf = nirf;
	}
	public String getNiif() {
		return niif;
	}
	public void setNiif(String niif) {
		this.niif = niif;
	}
	public String getIncra() {
		return incra;
	}
	public void setIncra(String incra) {
		this.incra = incra;
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

	public void limparForm() {
		this.setCodDocumentacao(null);
		this.setDescricao(null);
		this.setResponsavelDocumentacao(null);
		this.setCartorio(null);
		this.setTabelionato(null);
		this.setNumeroDocumentoCartorial(null);
		this.setNumeroDocumentoTabelional(null);
		this.setNirf(null);
		this.setNiif(null);
		this.setIncra(null);
	}
	
}
