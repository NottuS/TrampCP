
package gov.pr.celepar.abi.form;

import org.apache.struts.validator.ValidatorForm;


/**
 * @author pialarissi
 * @version 1.0
 * @since 25/03/2010
 * 
 * Responsável por encapsular os dados de telas de Situação Legal Cartorial.
 */
public class SituacaoLegalCartorialForm extends ValidatorForm {

	private static final long serialVersionUID = -6617625238614092217L;
	private String actionType;
	private String codSituacaoLegalCartorial;
	private String descricao;
	
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String getCodSituacaoLegalCartorial() {
		return codSituacaoLegalCartorial;
	}
	public void setCodSituacaoLegalCartorial(String codSituacaoLegalCartorial) {
		this.codSituacaoLegalCartorial = codSituacaoLegalCartorial;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


}
