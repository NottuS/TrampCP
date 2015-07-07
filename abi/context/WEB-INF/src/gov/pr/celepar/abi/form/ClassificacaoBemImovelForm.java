
package gov.pr.celepar.abi.form;

import org.apache.struts.validator.ValidatorForm;


/**
 * @author claudiofain
 * @version 1.0
 * @since 06/01/2010
 *
 * Responsável por encapsular os dados das telas de Classificação de Bens Imóveis.
 */
public class ClassificacaoBemImovelForm extends ValidatorForm {

	private static final long serialVersionUID = 46373530472654092L;
	private String actionType;
	private String codClassificacaoBemImovel;
	private String descricao;

	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String getCodClassificacaoBemImovel() {
		return codClassificacaoBemImovel;
	}
	public void setCodClassificacaoBemImovel(String codClassificacaoBemImovel) {
		this.codClassificacaoBemImovel = codClassificacaoBemImovel;
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
