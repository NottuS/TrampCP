package gov.pr.celepar.abi.form;

import gov.pr.celepar.abi.util.Dominios.administracaoImovel;
import gov.pr.celepar.abi.util.Util;


/**
 * @author pialarissi
 * @version 1.0
 * @since 19/01/2010
 * 
 * Responsável por encapsular os dados das telas de Lotes.
 */
public class QuadraLoteForm extends BemImovelForm {

	private static final long serialVersionUID = 8561041510965753671L;
	private String actionType;
	private String codBemImovel;
    private String codLote;
	private String codQuadra;
    private String descricaoLote;
    private String descricaoQuadra;
    private String administracao;
    private String somenteTerreno;
    
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getCodBemImovel() {
		return codBemImovel;
	}
	public void setCodBemImovel(String codBemImovel) {
		this.codBemImovel = codBemImovel;
	}
	public String getCodLote() {
		return codLote;
	}
	public void setCodLote(String codLote) {
		this.codLote = codLote;
	}
	public String getDescricaoAdministracao(){
		if (!Util.strEmBranco( this.administracao)){
			return administracaoImovel.getAdministracaoImovelByIndex(Integer.valueOf(this.administracao)).getLabel();
		}
		else{
			return "";
		}
	}
	public String getAdministracao() {
		return administracao;
	}
	public void setAdministracao(String administracao) {
		this.administracao = administracao;
	}

    public String getSomenteTerreno() {
		return somenteTerreno;
	}
	public void setSomenteTerreno(String somenteTerreno) {
		this.somenteTerreno = somenteTerreno;
	}
	public void setCodQuadra(String codQuadra) {
		this.codQuadra = codQuadra;
	}
	public String getCodQuadra() {
		return codQuadra;
	}
	public void setDescricaoLote(String descricaoLote) {
		this.descricaoLote = descricaoLote;
	}
	public String getDescricaoLote() {
		return descricaoLote;
	}
	public void setDescricaoQuadra(String descricaoQuadra) {
		this.descricaoQuadra = descricaoQuadra;
	}
	public String getDescricaoQuadra() {
		return descricaoQuadra;
	}

	public void limparCampos() {
		this.setActionType("");
		this.setCodBemImovel("");
		this.setCodLote("");
	    this.setCodQuadra("");
		this.setDescricaoLote("");
	    this.setDescricaoQuadra("");
	    this.setAdministracao("");
	    this.setSomenteTerreno("");
	}
}
