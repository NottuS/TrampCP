
package gov.pr.celepar.abi.form;

import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.ValidatorForm;


/**
 * @author Luciana R. Bélico
 * @version 1.0
 * @since 10/02/2011
 * 
 * Responsável por encapsular os dados das telas de Migração de dados.
 */
public class MigracaoForm extends ValidatorForm {
	

	private static final long serialVersionUID = -4741608638875787749L;
	
	
	private String actionType;
	private String senha;
	private FormFile arquivo;
	private String senhaOK;
	
	
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public FormFile getArquivo() {
		return arquivo;
	}
	public void setArquivo(FormFile arquivo) {
		this.arquivo = arquivo;
	}
	public void setSenhaOK(String senhaOK) {
		this.senhaOK = senhaOK;
	}
	public String getSenhaOK() {
		return senhaOK;
	}
	
	
}
