
package gov.pr.celepar.abi.form;

import org.apache.struts.validator.ValidatorForm;


/**
 * @author Luciana R. Bélico
 * @version 1.0
 * @since 10/02/2010
 * 
 * Responsável por encapsular os dados das telas de Tipo de Lei de Bem Imovel.
 */
public class TipoLeiBemImovelForm extends ValidatorForm {
	
	private static final long serialVersionUID = 7600931876463408742L;
	private String actionType;
	private String codTipoLeiBemImovel;
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
	public void setCodTipoLeiBemImovel(String codTipoLeiBemImovel) {
		this.codTipoLeiBemImovel = codTipoLeiBemImovel;
	}
	public String getCodTipoLeiBemImovel() {
		return codTipoLeiBemImovel;
	}
	
	
	
	


}
