
package gov.pr.celepar.abi.form;

import org.apache.struts.validator.ValidatorForm;


/**
 * @author Vanessa M R Koppe
 * @version 1.0
 * @since 29/06/2010
 * 
 * Responsável por encapsular os dados das telas de Assinatura
 */
public class AssinaturaForm extends ValidatorForm {
	
	private static final long serialVersionUID = 8684613197392970629L;
	private String actionType;
	private String pesqExec; 
	private String isGpAdmGeralUsuarioLogado;
	
	private String conInstituicao;
	private String conCpf;
	private String conNome;
	
	private String instituicao;
	private String indRespMaximo;
	private String indRespMaximoDesc;
	private String instituicaoDesc;
	private String codAssinatura;
	private String cpf;
	private String nome;
	private String administracao;
	private String orgao;
	private String cargo;
	
	private String administracaoDescricao;
	private String orgaoDescricao;
	private String cargoDescricao;

	private String incluidoPor;
	private String alteradoPor;
	private String excluidoPor;
	
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String getCodAssinatura() {
		return codAssinatura;
	}
	public void setCodAssinatura(String codAssinatura) {
		this.codAssinatura = codAssinatura;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getOrgao() {
		return orgao;
	}
	public void setOrgao(String orgao) {
		this.orgao = orgao;
	}
	public String getCargo() {
		return cargo;
	}
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	public String getConCpf() {
		return conCpf;
	}
	public void setConCpf(String conCpf) {
		this.conCpf = conCpf;
	}
	public String getConNome() {
		return conNome;
	}
	public void setConNome(String conNome) {
		this.conNome = conNome;
	}
	public void setAdministracao(String administracao) {
		this.administracao = administracao;
	}
	public String getAdministracao() {
		return administracao;
	}
	public String getIncluidoPor() {
		return incluidoPor;
	}
	public void setIncluidoPor(String incluidoPor) {
		this.incluidoPor = incluidoPor;
	}
	public String getAlteradoPor() {
		return alteradoPor;
	}
	public void setAlteradoPor(String alteradoPor) {
		this.alteradoPor = alteradoPor;
	}
	public String getExcluidoPor() {
		return excluidoPor;
	}
	public void setExcluidoPor(String excluidoPor) {
		this.excluidoPor = excluidoPor;
	}
	public String getAdministracaoDescricao() {
		return administracaoDescricao;
	}
	public void setAdministracaoDescricao(String administracaoDescricao) {
		this.administracaoDescricao = administracaoDescricao;
	}
	public String getOrgaoDescricao() {
		return orgaoDescricao;
	}
	public void setOrgaoDescricao(String orgaoDescricao) {
		this.orgaoDescricao = orgaoDescricao;
	}
	public String getCargoDescricao() {
		return cargoDescricao;
	}
	public void setCargoDescricao(String cargoDescricao) {
		this.cargoDescricao = cargoDescricao;
	}
	public String getPesqExec() {
		return pesqExec;
	}
	public void setPesqExec(String pesqExec) {
		this.pesqExec = pesqExec;
	}
	public void setConInstituicao(String conInstituicao) {
		this.conInstituicao = conInstituicao;
	}
	public String getConInstituicao() {
		return conInstituicao;
	}
	public void limparCampos() {
		setCodAssinatura("");
		setCpf("");
		setNome("");
		setAdministracao("");
		setOrgao("");
		setCargo("");
		setAdministracaoDescricao("");
		setOrgaoDescricao("");
		setCargoDescricao("");
		setIncluidoPor("");
		setAlteradoPor("");
		setExcluidoPor("");
		setInstituicao("");
		setInstituicaoDesc("");
		setIndRespMaximo("");
		setIndRespMaximoDesc("");
	}
	public void setInstituicao(String instituicao) {
		this.instituicao = instituicao;
	}
	public String getInstituicao() {
		return instituicao;
	}
	public void setInstituicaoDesc(String instituicaoDesc) {
		this.instituicaoDesc = instituicaoDesc;
	}
	public String getInstituicaoDesc() {
		return instituicaoDesc;
	}
	public void setIsGpAdmGeralUsuarioLogado(String isGpAdmGeralUsuarioLogado) {
		this.isGpAdmGeralUsuarioLogado = isGpAdmGeralUsuarioLogado;
	}
	public String getIsGpAdmGeralUsuarioLogado() {
		return isGpAdmGeralUsuarioLogado;
	}
	public void setIndRespMaximo(String indRespMaximo) {
		this.indRespMaximo = indRespMaximo;
	}
	public String getIndRespMaximo() {
		return indRespMaximo;
	}
	public void setIndRespMaximoDesc(String indRespMaximoDesc) {
		this.indRespMaximoDesc = indRespMaximoDesc;
	}
	public String getIndRespMaximoDesc() {
		return indRespMaximoDesc;
	}
}
