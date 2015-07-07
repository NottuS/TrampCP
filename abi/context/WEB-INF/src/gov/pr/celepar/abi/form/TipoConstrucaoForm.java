
package gov.pr.celepar.abi.form;

import org.apache.struts.validator.ValidatorForm;


/**
 * @author pialarissi
 * @version 1.0
 * @since 25/03/2010
 * 
 * Responsável por encapsular os dados das telas de Tipo de Construção.
 */
public class TipoConstrucaoForm extends ValidatorForm {
	
	private static final long serialVersionUID = -533392528079456205L;
	private String actionType;
	private String codTipoConstrucao;
	private String descricao;
	
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String getCodTipoConstrucao() {
		return codTipoConstrucao;
	}
	public void setCodTipoConstrucao(String codTipoConstrucao) {
		this.codTipoConstrucao = codTipoConstrucao;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


}
