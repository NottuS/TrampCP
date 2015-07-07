
package gov.pr.celepar.abi.form;

import org.apache.struts.validator.ValidatorForm;

public class VistoriadorForm extends ValidatorForm {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4670234579838664427L;
	
	public String conCpf;
	public String conNome;
	public String actionType;	
	public String codVistoriador;
	public String cpf;
	public String nome;
	public String tsInclusao;
	public String tsAtualizacao;
	public String cpfResponsavel;
	public String adm;
	public String indGrupoSentinela;
	public String conInstituicao;
	public String instituicao;
	private String instituicaoDesc;

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
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String getCodVistoriador() {
		return codVistoriador;
	}
	public void setCodVistoriador(String codVistoriador) {
		this.codVistoriador = codVistoriador;
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
	public String getTsInclusao() {
		return tsInclusao;
	}
	public void setTsInclusao(String tsInclusao) {
		this.tsInclusao = tsInclusao;
	}
	public String getTsAtualizacao() {
		return tsAtualizacao;
	}
	public void setTsAtualizacao(String tsAtualizacao) {
		this.tsAtualizacao = tsAtualizacao;
	}
	public String getCpfResponsavel() {
		return cpfResponsavel;
	}
	public void setCpfResponsavel(String cpfResponsavel) {
		this.cpfResponsavel = cpfResponsavel;
	}

	public void limpaCampos() {
		this.setCpf("");
		this.setNome("");
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
	
}