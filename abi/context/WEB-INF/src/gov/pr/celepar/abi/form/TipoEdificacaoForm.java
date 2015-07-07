
package gov.pr.celepar.abi.form;

import org.apache.struts.validator.ValidatorForm;


/**
 * @author Luciana R. B�lico
 * @version 1.0
 * @since 10/02/2010
 * 
 * Respons�vel por encapsular os dados das telas de Tipo de Edifica��o.
 */
public class TipoEdificacaoForm extends ValidatorForm {
	
	
	private static final long serialVersionUID = 8813578506882544712L;
	private String actionType;
	private String codTipoEdificacao;
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
	public void setCodTipoEdificacao(String codTipoEdificacao) {
		this.codTipoEdificacao = codTipoEdificacao;
	}
	public String getCodTipoEdificacao() {
		return codTipoEdificacao;
	}
	
	
	


}
