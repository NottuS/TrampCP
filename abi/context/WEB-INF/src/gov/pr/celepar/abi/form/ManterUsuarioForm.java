package gov.pr.celepar.abi.form;

import org.apache.struts.validator.ValidatorForm;

public class ManterUsuarioForm extends ValidatorForm {

	private static final long serialVersionUID = 8738230987445722195L;

	private String actionType;
	private String pesqExec; 
	
	private String conGrupo;
	private String conAdministracao;
	private String conOrgao;
	private String conCpf;
	private String conNome;
	private String conSituacao;
	private String conInstituicao;
	
	private String codUsuario;
	private String idSentinela;
	private String cpf;
	private String nome;
	private String login;
	private String mail;
	private String situacao;
	private String grupo;
	private String administracao;
	private String orgao;
	private String incluidoPor;
	private String alteradoPor;
	private String excluidoPor;
	private String instituicao;
	
	private String desabilitaCampo;
	private String desabilitaOrgao;
	private String situacaoDesc;
	private String desabilitaInstituicao;
	private String isAdmGeral;
	private String instituicaoDesc;

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public String getPesqExec() {
		return pesqExec;
	}

	public void setPesqExec(String pesqExec) {
		this.pesqExec = pesqExec;
	}

	public String getConGrupo() {
		return conGrupo;
	}

	public void setConGrupo(String conGrupo) {
		this.conGrupo = conGrupo;
	}

	public String getConOrgao() {
		return conOrgao;
	}

	public void setConOrgao(String conOrgao) {
		this.conOrgao = conOrgao;
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

	public String getConSituacao() {
		return conSituacao;
	}

	public void setConSituacao(String conSituacao) {
		this.conSituacao = conSituacao;
	}

	public String getConAdministracao() {
		return conAdministracao;
	}

	public void setConAdministracao(String conAdministracao) {
		this.conAdministracao = conAdministracao;
	}

	public String getCodUsuario() {
		return codUsuario;
	}

	public void setCodUsuario(String codUsuario) {
		this.codUsuario = codUsuario;
	}

	public String getIdSentinela() {
		return idSentinela;
	}

	public void setIdSentinela(String idSentinela) {
		this.idSentinela = idSentinela;
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

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public String getAdministracao() {
		return administracao;
	}

	public void setAdministracao(String administracao) {
		this.administracao = administracao;
	}

	public String getOrgao() {
		return orgao;
	}

	public void setOrgao(String orgao) {
		this.orgao = orgao;
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

	public void setDesabilitaCampo(String desabilitaCampo) {
		this.desabilitaCampo = desabilitaCampo;
	}

	public String getDesabilitaCampo() {
		return desabilitaCampo;
	}

	public void setDesabilitaOrgao(String desabilitaOrgao) {
		this.desabilitaOrgao = desabilitaOrgao;
	}

	public String getDesabilitaOrgao() {
		return desabilitaOrgao;
	}

	public void setSituacaoDesc(String situacaoDesc) {
		this.situacaoDesc = situacaoDesc;
	}

	public String getSituacaoDesc() {
		return situacaoDesc;
	}

	public void limparCampos() {
		this.setIdSentinela("");
		this.setCpf("");
		this.setNome("");
		this.setLogin("");
		this.setMail("");
		this.setSituacao("");
		this.setGrupo("");
		this.setAdministracao("");
		this.setOrgao("");
		this.setIncluidoPor("");
		this.setAlteradoPor("");
		this.setExcluidoPor("");
		this.setDesabilitaCampo("true");
		this.setDesabilitaOrgao("true");
		this.setSituacaoDesc("");
	}

	public void setConInstituicao(String conInstituicao) {
		this.conInstituicao = conInstituicao;
	}

	public String getConInstituicao() {
		return conInstituicao;
	}

	public void setInstituicao(String instituicao) {
		this.instituicao = instituicao;
	}

	public String getInstituicao() {
		return instituicao;
	}

	public void setDesabilitaInstituicao(String desabilitaInstituicao) {
		this.desabilitaInstituicao = desabilitaInstituicao;
	}

	public String getDesabilitaInstituicao() {
		return desabilitaInstituicao;
	}

	public void setIsAdmGeral(String isAdmGeral) {
		this.isAdmGeral = isAdmGeral;
	}

	public String getIsAdmGeral() {
		return isAdmGeral;
	}

	public void setInstituicaoDesc(String instituicaoDesc) {
		this.instituicaoDesc = instituicaoDesc;
	}

	public String getInstituicaoDesc() {
		return instituicaoDesc;
	}

}
