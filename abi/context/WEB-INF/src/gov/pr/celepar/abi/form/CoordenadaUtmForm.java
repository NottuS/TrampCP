package gov.pr.celepar.abi.form;

import gov.pr.celepar.abi.util.Dominios.administracaoImovel;
import gov.pr.celepar.abi.util.Util;


/**
 * @author 
 * @version 1.0
 * @since 19/01/2010
 * 
 * Responsável por encapsular os dados das telas de Quadras.
 */
public class CoordenadaUtmForm extends BemImovelForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8459714494231918682L;
	private String codBemImovel;
	private String actionType;
	private String codCoordenadaUtm;
	private String coordenadaX;
	private String coordenadaY;
	private String administracao;
    private String somenteTerreno;
	
	public String getCodBemImovel() {
		return codBemImovel;
	}
	public void setCodBemImovel(String codBemImovel) {
		this.codBemImovel = codBemImovel;
	}
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String getCodCoordenadaUtm() {
		return codCoordenadaUtm;
	}
	public void setCodCoordenadaUtm(String codCoordenadaUtm) {
		this.codCoordenadaUtm = codCoordenadaUtm;
	}
	public String getCoordenadaX() {
		return coordenadaX;
	}
	public void setCoordenadaX(String coordenadaX) {
		this.coordenadaX = coordenadaX;
	}
	public String getCoordenadaY() {
		return coordenadaY;
	}
	public void setCoordenadaY(String coordenadaY) {
		this.coordenadaY = coordenadaY;
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

}
