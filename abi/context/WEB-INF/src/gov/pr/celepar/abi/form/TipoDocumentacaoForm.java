
package gov.pr.celepar.abi.form;

import org.apache.struts.validator.ValidatorForm;


/**
 * @author Luciana R. Bélico
 * @version 1.0
 * @since 20/01/2010
 * 
 * Responsável por encapsular os dados das telas de Tipo Documentacao.
 */
public class TipoDocumentacaoForm extends ValidatorForm {
	
	
	private static final long serialVersionUID = -6014527845773285504L;
	private String actionType;
	private String codTipoDocumentacao;
	private String descricao;
	
	public String getCodTipoDocumentacao() {
		return codTipoDocumentacao;
	}
	public void setCodTipoDocumentacao(String codTipoDocumentacao) {
		this.codTipoDocumentacao = codTipoDocumentacao;
	}
		
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

	
	


}
