package gov.pr.celepar.abi.dto;

import gov.pr.celepar.abi.pojo.Instituicao;
import gov.pr.celepar.abi.pojo.Usuario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FiltroRelatorioEdificacaoDTO implements Serializable{

	private static final long serialVersionUID = -2766689564904426178L;
	
	private List<RelatorioEdificacaoDTO> listaBensImoveis = new ArrayList <RelatorioEdificacaoDTO>();
	private String usuario;
	private String uf = "";
	private String codMunicipio;
	private String filtroMunicipio = "";
	private String codTipoConstrucao;
	private String filtroTipoConstrucao = "";
	private String filtroTipoEdificacao = "";
	private String incluirOcupacoes;
	private String codOrgao;
	private String filtroOrgaoSiglaDescricao="";
	private String codSituacaoOcupacao;
	private String filtroSituacaoOcupacao = "";
	private String filtroAverbacao;
	private String filtroRelatorio = "";
	private String filtroAdministracao = "";
	private String descricaoOcupacao;
	private Usuario usuarioS;
	private List<Integer> listaCodOrgao;
	private List<Integer> listaCodTipoEdificacao;
	private Float areaMin;
	private Float areaMax;
	private String filtroAreaEdificacao;
	private Instituicao instituicao;
	
	public List<RelatorioEdificacaoDTO> getListaBensImoveis() {
		return listaBensImoveis;
	}
	public void setListaBensImoveis(List<RelatorioEdificacaoDTO> listaBensImoveis) {
		this.listaBensImoveis = listaBensImoveis;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
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
	public String getFiltroMunicipio() {
		return filtroMunicipio;
	}
	public void setFiltroMunicipio(String filtroMunicipio) {
		this.filtroMunicipio = filtroMunicipio;
	}
	public String getCodTipoConstrucao() {
		return codTipoConstrucao;
	}
	public void setCodTipoConstrucao(String codTipoConstrucao) {
		this.codTipoConstrucao = codTipoConstrucao;
	}
	public String getFiltroTipoConstrucao() {
		return filtroTipoConstrucao;
	}
	public void setFiltroTipoConstrucao(String filtroTipoConstrucao) {
		this.filtroTipoConstrucao = filtroTipoConstrucao;
	}
	public String getFiltroTipoEdificacao() {
		return filtroTipoEdificacao;
	}
	public void setFiltroTipoEdificacao(String filtroTipoEdificacao) {
		this.filtroTipoEdificacao = filtroTipoEdificacao;
	}
	public String getIncluirOcupacoes() {
		return incluirOcupacoes;
	}
	public void setIncluirOcupacoes(String incluirOcupacoes) {
		this.incluirOcupacoes = incluirOcupacoes;
	}
	public String getCodOrgao() {
		return codOrgao;
	}
	public void setCodOrgao(String codOrgao) {
		this.codOrgao = codOrgao;
	}
	public String getFiltroOrgaoSiglaDescricao() {
		return filtroOrgaoSiglaDescricao;
	}
	public void setFiltroOrgaoSiglaDescricao(String filtroOrgaoSiglaDescricao) {
		this.filtroOrgaoSiglaDescricao = filtroOrgaoSiglaDescricao;
	}
	public String getCodSituacaoOcupacao() {
		return codSituacaoOcupacao;
	}
	public void setCodSituacaoOcupacao(String codSituacaoOcupacao) {
		this.codSituacaoOcupacao = codSituacaoOcupacao;
	}
	public String getFiltroSituacaoOcupacao() {
		return filtroSituacaoOcupacao;
	}
	public void setFiltroSituacaoOcupacao(String filtroSituacaoOcupacao) {
		this.filtroSituacaoOcupacao = filtroSituacaoOcupacao;
	}
	public String getFiltroAverbacao() {
		return filtroAverbacao;
	}
	public void setFiltroAverbacao(String filtroAverbacao) {
		this.filtroAverbacao = filtroAverbacao;
	}
	public String getFiltroRelatorio() {
		return filtroRelatorio;
	}
	public void setFiltroRelatorio(String filtroRelatorio) {
		this.filtroRelatorio = filtroRelatorio;
	}
	public void setDescricaoOcupacao(String descricaoOcupacao) {
		this.descricaoOcupacao = descricaoOcupacao;
	}
	public String getDescricaoOcupacao() {
		return descricaoOcupacao;
	}
	public void setFiltroAdministracao(String filtroAdministracao) {
		this.filtroAdministracao = filtroAdministracao;
	}
	public String getFiltroAdministracao() {
		return filtroAdministracao;
	}
	public Usuario getUsuarioS() {
		return usuarioS;
	}
	public void setUsuarioS(Usuario usuarioS) {
		this.usuarioS = usuarioS;
	}
	public List<Integer> getListaCodOrgao() {
		return listaCodOrgao;
	}
	public void setListaCodOrgao(List<Integer> listaCodOrgao) {
		this.listaCodOrgao = listaCodOrgao;
	}
	public List<Integer> getListaCodTipoEdificacao() {
		return listaCodTipoEdificacao;
	}
	public void setListaCodTipoEdificacao(List<Integer> listaCodTipoEdificacao) {
		this.listaCodTipoEdificacao = listaCodTipoEdificacao;
	}
	public Float getAreaMin() {
		return areaMin;
	}
	public void setAreaMin(Float areaMin) {
		this.areaMin = areaMin;
	}
	public Float getAreaMax() {
		return areaMax;
	}
	public void setAreaMax(Float areaMax) {
		this.areaMax = areaMax;
	}
	public void setFiltroAreaEdificacao(String filtroAreaEdificacao) {
		this.filtroAreaEdificacao = filtroAreaEdificacao;
	}
	public String getFiltroAreaEdificacao() {
		return filtroAreaEdificacao;
	}
	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}
	public Instituicao getInstituicao() {
		return instituicao;
	}
	
}
