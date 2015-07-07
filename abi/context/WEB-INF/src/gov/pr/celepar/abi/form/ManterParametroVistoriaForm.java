
package gov.pr.celepar.abi.form;

import gov.pr.celepar.abi.generico.form.BaseForm;


/**
 * @author Oksana
 * @version 1.0
 * @since 17/06/2011
 * 
 * Responsável por encapsular os dados das telas ManterParametroVistoria
 */
public class ManterParametroVistoriaForm extends BaseForm {

	private static final long serialVersionUID = 793452888062817985L;
	private String conDescricao;
	private String conDenominacaoImovel;
	private String conAtivo;
	private String actionType;
	private String acao;
	
	private String codParametroVistoria;
	private String descricao;
	private String ordemApresentacao;
	private String indTipoParametro;
	private String dominio;
	private String dominioPreenchido;
	
	public String adm;
	public String indGrupoSentinela;
	public String conInstituicao;
	public String instituicao;
	
	private String[] listaDenominacaoImovelSelecionada;
	
	
	
	public String getConDescricao() {
		return conDescricao;
	}
	public void setConDescricao(String conDescricao) {
		this.conDescricao = conDescricao;
	}
	public String getConDenominacaoImovel() {
		return conDenominacaoImovel;
	}
	public void setConDenominacaoImovel(String conDenominacaoImovel) {
		this.conDenominacaoImovel = conDenominacaoImovel;
	}
	public String getConAtivo() {
		return conAtivo;
	}
	public void setConAtivo(String conAtivo) {
		this.conAtivo = conAtivo;
	}
	
	public String getAcao() {
		return acao;
	}
	public void setAcao(String acao) {
		this.acao = acao;
	}
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String getCodParametroVistoria() {
		return codParametroVistoria;
	}
	public void setCodParametroVistoria(String codParametroVistoria) {
		this.codParametroVistoria = codParametroVistoria;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getOrdemApresentacao() {
		return ordemApresentacao;
	}
	public void setOrdemApresentacao(String ordemApresentacao) {
		this.ordemApresentacao = ordemApresentacao;
	}
	public String getIndTipoParametro() {
		return indTipoParametro;
	}
	public void setIndTipoParametro(String indTipoParametro) {
		this.indTipoParametro = indTipoParametro;
	}
	public String getDominio() {
		return dominio;
	}
	public void setDominio(String dominio) {
		this.dominio = dominio;
	}
	public String getDominioPreenchido() {
		return dominioPreenchido;
	}
	public void setDominioPreenchido(String dominioPreenchido) {
		this.dominioPreenchido = dominioPreenchido;
	}
	public String[] getListaDenominacaoImovelSelecionada() {
		return listaDenominacaoImovelSelecionada;
	}
	public void setListaDenominacaoImovelSelecionada(String[] listaDenominacaoImovelSelecionada) {
		this.listaDenominacaoImovelSelecionada = listaDenominacaoImovelSelecionada;
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
	
}
