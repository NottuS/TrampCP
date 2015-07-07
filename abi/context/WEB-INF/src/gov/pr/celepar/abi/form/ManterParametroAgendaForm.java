
package gov.pr.celepar.abi.form;

import gov.pr.celepar.abi.generico.form.BaseForm;


/**
 * @author Oksana
 * @version 1.0
 * @since 05/07/2011
 * 
 * Responsável por encapsular os dados das telas ManterParametroAgenda
 */
public class ManterParametroAgendaForm extends BaseForm {

	private static final long serialVersionUID = 793452884062817985L;
	private String codParametroAgenda;
	private String numeroDiasVencimentoNotificacao;
	private String numeroDiasVencimentoCessaoDeUso;
	private String numeroDiasVencimentoDoacao;
	private String numeroDiasVencimentoVistoria;
	private String tempoCessao;
	private String email;
	private String indGrupoSentinela;
	private String adm;
	private String conInstituicao;
	private String codInstituicao;
	private String instituicao;
	private String[] listaEmail;
	
	
	public String getCodParametroAgenda() {
		return codParametroAgenda;
	}
	public void setCodParametroAgenda(String codParametroAgenda) {
		this.codParametroAgenda = codParametroAgenda;
	}
	public String getNumeroDiasVencimentoNotificacao() {
		return numeroDiasVencimentoNotificacao;
	}
	public void setNumeroDiasVencimentoNotificacao(
			String numeroDiasVencimentoNotificacao) {
		this.numeroDiasVencimentoNotificacao = numeroDiasVencimentoNotificacao;
	}
	public String getNumeroDiasVencimentoCessaoDeUso() {
		return numeroDiasVencimentoCessaoDeUso;
	}
	public void setNumeroDiasVencimentoCessaoDeUso(
			String numeroDiasVencimentoCessaoDeUso) {
		this.numeroDiasVencimentoCessaoDeUso = numeroDiasVencimentoCessaoDeUso;
	}
	public String getNumeroDiasVencimentoDoacao() {
		return numeroDiasVencimentoDoacao;
	}
	public void setNumeroDiasVencimentoDoacao(String numeroDiasVencimentoDoacao) {
		this.numeroDiasVencimentoDoacao = numeroDiasVencimentoDoacao;
	}
	public String getNumeroDiasVencimentoVistoria() {
		return numeroDiasVencimentoVistoria;
	}
	public void setNumeroDiasVencimentoVistoria(String numeroDiasVencimentoVistoria) {
		this.numeroDiasVencimentoVistoria = numeroDiasVencimentoVistoria;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String[] getListaEmail() {
		return listaEmail;
	}
	public void setListaEmail(String[] listaEmail) {
		this.listaEmail = listaEmail;
	}
	public void setTempoCessao(String tempoCessao) {
		this.tempoCessao = tempoCessao;
	}
	public String getTempoCessao() {
		return tempoCessao;
	}
	public String getIndGrupoSentinela() {
		return indGrupoSentinela;
	}
	public void setIndGrupoSentinela(String indGrupoSentinela) {
		this.indGrupoSentinela = indGrupoSentinela;
	}
	public String getAdm() {
		return adm;
	}
	public void setAdm(String adm) {
		this.adm = adm;
	}
	public String getConInstituicao() {
		return conInstituicao;
	}
	public void setConInstituicao(String conInstituicao) {
		this.conInstituicao = conInstituicao;
	}
	public String getCodInstituicao() {
		return codInstituicao;
	}
	public void setCodInstituicao(String codInstituicao) {
		this.codInstituicao = codInstituicao;
	}
	public String getInstituicao() {
		return instituicao;
	}
	public void setInstituicao(String instituicao) {
		this.instituicao = instituicao;
	}
	
	

	
}
