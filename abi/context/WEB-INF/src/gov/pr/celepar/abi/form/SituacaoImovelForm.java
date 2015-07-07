
package gov.pr.celepar.abi.form;

import org.apache.struts.validator.ValidatorForm;


/**
 * @author Luciana R. B�lico
 * @version 1.0
 * @since 05/02/2010
 * 
 * Respons�vel por encapsular os dados das telas de Situa��o de Im�veis.
 */
public class SituacaoImovelForm extends ValidatorForm {
	
	
	private static final long serialVersionUID = -3524958001834231201L;
	private String actionType;
	private String codSituacaoImovel;
	private String descricao;
	
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
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
	public void setCodSituacaoImovel(String codSituacaoImovel) {
		this.codSituacaoImovel = codSituacaoImovel;
	}
	public String getCodSituacaoImovel() {
		return codSituacaoImovel;
	}

	
	


}
