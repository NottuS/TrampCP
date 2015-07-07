
package gov.pr.celepar.abi.form;

import org.apache.struts.validator.ValidatorForm;


/**
 * @author Vanessa M R Koppe
 * @version 1.0
 * @since 29/06/2010
 * 
 * Responsável por encapsular os dados das telas de Cargo da Assinatura
 */
public class CargoAssinaturaForm extends ValidatorForm {
	
	private static final long serialVersionUID = 607992540227075890L;
	private String actionType;
	private String codCargoAssinatura;
	private String descricao;
	private String adm;
	private String indGrupoSentinela;
	private String conInstituicao;
	private String instituicao;
	private String instituicaoDesc;
	
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
	public void setCodCargoAssinatura(String codCargoAssinatura) {
		this.codCargoAssinatura = codCargoAssinatura;
	}
	public String getCodCargoAssinatura() {
		return codCargoAssinatura;
	}
	public String getAdm() {
		return adm;
	}
	public void setAdm(String adm) {
		this.adm = adm;
	}
	public String getIndGrupoSentinela() {
		return indGrupoSentinela;
	}
	public void setIndGrupoSentinela(String indGrupoSentinela) {
		this.indGrupoSentinela = indGrupoSentinela;
	}
	public String getConInstituicao() {
		return conInstituicao;
	}
	public void setConInstituicao(String conInstituicao) {
		this.conInstituicao = conInstituicao;
	}
	public String getInstituicao() {
		return instituicao;
	}
	public void setInstituicao(String instituicao) {
		this.instituicao = instituicao;
	}
	public void setInstituicaoDesc(String instituicaoDesc) {
		this.instituicaoDesc = instituicaoDesc;
	}
	public String getInstituicaoDesc() {
		return instituicaoDesc;
	}
	public void limparFormCadastro() {
		setDescricao(null);
	}
	
}
