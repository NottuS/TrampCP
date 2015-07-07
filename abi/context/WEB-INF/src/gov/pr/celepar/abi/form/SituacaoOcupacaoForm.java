
package gov.pr.celepar.abi.form;

import org.apache.struts.validator.ValidatorForm;


/**
 * @author pialarissi
 * @version 1.0
 * @since 06/01/2010
 * 
 * Respons�vel por encapsular os dados das telas de Denomina��o de Bens de Im�veis.
 */
public class SituacaoOcupacaoForm extends ValidatorForm {
	
	private static final long serialVersionUID = 6166421697962301696L;
	private String actionType;
	private String codSituacaoOcupacao;
	private String descricao;
	
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getCodSituacaoOcupacao() {
		return codSituacaoOcupacao;
	}
	public void setCodSituacaoOcupacao(String codSituacaoOcupacao) {
		this.codSituacaoOcupacao = codSituacaoOcupacao;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


}
