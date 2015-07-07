package gov.pr.celepar.abi.dto;

import gov.pr.celepar.abi.pojo.Instituicao;
import gov.pr.celepar.abi.pojo.Orgao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FiltroRelatorioNotificacaoBemImovelDTO implements Serializable{

	
	private static final long serialVersionUID = -6121184509394849750L;
	
	private List<RelatorioNotificacaoDTO> listaNotificacoes ;
	private String usuario;
	private String filtroMunicipio="";
	private String filtroClassificacao="";
	private String filtroDataNotificacaoDe;
	private String filtroDataNotificacaoAte;
	private String filtroSituacao="";
	private String filtroTerreno;
	private String filtroRelatorio;
	private String filtroAdministracao;
	private String uf;
	private String codMunicipio;
	private String codClassificacao;
	private String codSituacao;
	private Boolean indOperadorOrgao;
	private List<Orgao> listaOrgao = new ArrayList <Orgao>();
	private Instituicao instituicao;
	
	
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getFiltroMunicipio() {
		return filtroMunicipio;
	}
	public void setFiltroMunicipio(String filtroMunicipio) {
		this.filtroMunicipio = filtroMunicipio;
	}
	public String getFiltroClassificacao() {
		return filtroClassificacao;
	}
	public void setFiltroClassificacao(String filtroClassificacao) {
		this.filtroClassificacao = filtroClassificacao;
	}
	public String getFiltroDataNotificacaoDe() {
		return filtroDataNotificacaoDe;
	}
	public void setFiltroDataNotificacaoDe(String filtroDataNotificacaoDe) {
		this.filtroDataNotificacaoDe = filtroDataNotificacaoDe;
	}
	public String getFiltroDataNotificacaoAte() {
		return filtroDataNotificacaoAte;
	}
	public void setFiltroDataNotificacaoAte(String filtroDataNotificacaoAte) {
		this.filtroDataNotificacaoAte = filtroDataNotificacaoAte;
	}
	public String getFiltroSituacao() {
		return filtroSituacao;
	}
	public void setFiltroSituacao(String filtroSituacao) {
		this.filtroSituacao = filtroSituacao;
	}
	public String getFiltroTerreno() {
		return filtroTerreno;
	}
	public void setFiltroTerreno(String filtroTerreno) {
		this.filtroTerreno = filtroTerreno;
	}
	public String getFiltroRelatorio() {
		return filtroRelatorio;
	}
	public void setFiltroRelatorio(String filtroRelatorio) {
		this.filtroRelatorio = filtroRelatorio;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public String getUf() {
		return uf;
	}
	public void setCodMunicipio(String codMunicipio) {
		this.codMunicipio = codMunicipio;
	}
	public String getCodMunicipio() {
		return codMunicipio;
	}
	public void setCodClassificacao(String codClassificacao) {
		this.codClassificacao = codClassificacao;
	}
	public String getCodClassificacao() {
		return codClassificacao;
	}
	public void setCodSituacao(String codSituacao) {
		this.codSituacao = codSituacao;
	}
	public String getCodSituacao() {
		return codSituacao;
	}
	public void setFiltroAdministracao(String filtroAdministracao) {
		this.filtroAdministracao = filtroAdministracao;
	}
	public String getFiltroAdministracao() {
		return filtroAdministracao;
	}
	public List<Orgao> getListaOrgao() {
		return listaOrgao;
	}
	public void setListaOrgao(List<Orgao> listaOrgao) {
		this.listaOrgao = listaOrgao;
	}
	public Boolean getIndOperadorOrgao() {
		return indOperadorOrgao;
	}
	public void setIndOperadorOrgao(Boolean indOperadorOrgao) {
		this.indOperadorOrgao = indOperadorOrgao;
	}
	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}
	public Instituicao getInstituicao() {
		return instituicao;
	}
	public void setListaNotificacoes(List<RelatorioNotificacaoDTO> listaNotificacoes) {
		this.listaNotificacoes = listaNotificacoes;
	}
	public List<RelatorioNotificacaoDTO> getListaNotificacoes() {
		return listaNotificacoes;
	}
	
}
