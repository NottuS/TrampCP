
package gov.pr.celepar.abi.form;

import org.apache.struts.validator.ValidatorForm;


/**
 * @author Luciana R. Bélico
 * @version 1.0
 * @since 19/01/2010
 * 
 * Responsável por encapsular os dados das telas de Forma de Incorporação de Imóveis.
 */
public class FormaIncorporacaoForm extends ValidatorForm {
	
	private static final long serialVersionUID = 2448401180184675084L;
	private String actionType;
	private String codFormaIncorporacao;
	private String descricao;
	
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String getCodFormaIncorporacao() {
		return codFormaIncorporacao;
	}
	public void setCodFormaIncorporacao(String codFormaIncorporacao) {
		this.codFormaIncorporacao = codFormaIncorporacao;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	
	


}
