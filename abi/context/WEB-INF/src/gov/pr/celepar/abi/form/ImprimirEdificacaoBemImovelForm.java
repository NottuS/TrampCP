package gov.pr.celepar.abi.form;

import org.apache.struts.validator.ValidatorForm;

/**
 * @author Sr. Fain
 * @version 1.0
 * @since 23/03/2010
 * 
 * Responsável por encapsular os dados da tela de filtro do Relatório de  Edificação Bem Imóvel.
 */
public class ImprimirEdificacaoBemImovelForm extends ValidatorForm {

	private static final long serialVersionUID = 2675936793943900448L;
	private String actionType;

	private String uf;
	private String codMunicipio;
	private String municipioDescricao;
	private String codTipoConstrucao;
	private String tipoConstrucaoDescricao;
	private String tipoEdificacaoDescricao;
	private String radIncluirOcupacoes = "1";
	private String codOrgao;
	private String orgaoSiglaDescricao;
	private String codSituacaoOcupacao;
	private String situacaoOcupacaoDescricao;
	private String radAverbacao = "1";
	private String radRelatorio = "1";
	private String radAdministracao = "";
	private String descricaoOcupacao;
	private Boolean indOperadorOrgao;
	private String areaMin;
	private String areaMax;
	private String[] listaTipoEdificacaoSelecionada;
	private String codInstituicao;

	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public String getCodMunicipio() {
		return codMunicipio;
	}
	public void setCodMunicipio(String codMunicipio) {
		this.codMunicipio = codMunicipio;
	}
	public String getMunicipioDescricao() {
		return municipioDescricao;
	}
	public void setMunicipioDescricao(String municipioDescricao) {
		this.municipioDescricao = municipioDescricao;
	}
	public String getCodTipoConstrucao() {
		return codTipoConstrucao;
	}
	public void setCodTipoConstrucao(String codTipoConstrucao) {
		this.codTipoConstrucao = codTipoConstrucao;
	}
	public String getTipoConstrucaoDescricao() {
		return tipoConstrucaoDescricao;
	}
	public void setTipoConstrucaoDescricao(String tipoConstrucaoDescricao) {
		this.tipoConstrucaoDescricao = tipoConstrucaoDescricao;
	}
	public String getTipoEdificacaoDescricao() {
		return tipoEdificacaoDescricao;
	}
	public void setTipoEdificacaoDescricao(String tipoEdificacaoDescricao) {
		this.tipoEdificacaoDescricao = tipoEdificacaoDescricao;
	}
	public String getRadIncluirOcupacoes() {
		return radIncluirOcupacoes;
	}
	public void setRadIncluirOcupacoes(String radIncluirOcupacoes) {
		this.radIncluirOcupacoes = radIncluirOcupacoes;
	}
	public String getCodOrgao() {
		return codOrgao;
	}
	public void setCodOrgao(String codOrgao) {
		this.codOrgao = codOrgao;
	}
	public String getOrgaoSiglaDescricao() {
		return orgaoSiglaDescricao;
	}
	public void setOrgaoSiglaDescricao(String orgaoSiglaDescricao) {
		this.orgaoSiglaDescricao = orgaoSiglaDescricao;
	}
	public String getCodSituacaoOcupacao() {
		return codSituacaoOcupacao;
	}
	public void setCodSituacaoOcupacao(String codSituacaoOcupacao) {
		this.codSituacaoOcupacao = codSituacaoOcupacao;
	}
	public String getSituacaoOcupacaoDescricao() {
		return situacaoOcupacaoDescricao;
	}
	public void setSituacaoOcupacaoDescricao(String situacaoOcupacaoDescricao) {
		this.situacaoOcupacaoDescricao = situacaoOcupacaoDescricao;
	}
	public String getRadAverbacao() {
		return radAverbacao;
	}
	public void setRadAverbacao(String radAverbacao) {
		this.radAverbacao = radAverbacao;
	}
	public String getRadRelatorio() {
		return radRelatorio;
	}
	public void setRadRelatorio(String radRelatorio) {
		this.radRelatorio = radRelatorio;
	}
	public void setDescricaoOcupacao(String descricaoOcupacao) {
		this.descricaoOcupacao = descricaoOcupacao;
	}
	public String getDescricaoOcupacao() {
		return descricaoOcupacao;
	}
	public void setRadAdministracao(String radAdministracao) {
		this.radAdministracao = radAdministracao;
	}
	public String getRadAdministracao() {
		return radAdministracao;
	}
	public Boolean getIndOperadorOrgao() {
		return indOperadorOrgao;
	}
	public void setIndOperadorOrgao(Boolean indOperadorOrgao) {
		this.indOperadorOrgao = indOperadorOrgao;
	}
	public void setAreaMin(String areaMin) {
		this.areaMin = areaMin;
	}
	public String getAreaMin() {
		return areaMin;
	}
	public void setAreaMax(String areaMax) {
		this.areaMax = areaMax;
	}
	public String getAreaMax() {
		return areaMax;
	}
	public void setListaTipoEdificacaoSelecionada(
			String[] listaTipoEdificacaoSelecionada) {
		this.listaTipoEdificacaoSelecionada = listaTipoEdificacaoSelecionada;
	}
	public String[] getListaTipoEdificacaoSelecionada() {
		return listaTipoEdificacaoSelecionada;
	}
	public void setCodInstituicao(String codInstituicao) {
		this.codInstituicao = codInstituicao;
	}
	public String getCodInstituicao() {
		return codInstituicao;
	}
	
}