package gov.pr.celepar.abi.dto;

import gov.pr.celepar.abi.pojo.BemImovel;
import gov.pr.celepar.abi.pojo.Usuario;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class FiltroRelatorioBemImovelDTO implements Serializable{

	
	private static final long serialVersionUID = -6121184509394849750L;
	
	private List<BemImovel> listaBensImoveis = new ArrayList <BemImovel>();
	private String usuario;
	private String filtroMunicipio="";
	private String filtroClassificacao="";
	private String filtroSituacao="";
	private String filtroTerreno;
	private String filtroRelatorio;
	private String filtroAdministracao;
	private String filtroSituacaoLegalCartorial="";
	private String codMunicipio;
	private String uf="";
	private String codClassificacao;
	private String codSituacao;
	private String codSituacaoLegalCartorial;
	private BigDecimal filtroAreaDe;
	private BigDecimal filtroAreaAte;
	private String orgaoOcupante;
	private String orgao;
	private Usuario usuarioS;
	private Integer indOperadorOrgao;
	private boolean admGeral;
	private String codInstituicao;
	
	public String getOrgao() {
		return orgao;
	}
	public void setOrgao(String orgao) {
		this.orgao = orgao;
	}
	public String getCodClassificacao() {
		return codClassificacao;
	}
	public void setCodClassificacao(String codClassificacao) {
		this.codClassificacao = codClassificacao;
	}
	public String getCodSituacao() {
		return codSituacao;
	}
	public void setCodSituacao(String codSituacao) {
		this.codSituacao = codSituacao;
	}
	public String getCodSituacaoLegalCartorial() {
		return codSituacaoLegalCartorial;
	}
	public void setCodSituacaoLegalCartorial(String codSituacaoLegalCartorial) {
		this.codSituacaoLegalCartorial = codSituacaoLegalCartorial;
	}
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
	
	public void setFiltroSituacaoLegalCartorial(
			String filtroSituacaoLegalCartorial) {
		this.filtroSituacaoLegalCartorial = filtroSituacaoLegalCartorial;
	}
	public String getFiltroSituacaoLegalCartorial() {
		return filtroSituacaoLegalCartorial;
	}
	public void setCodMunicipio(String codMunicipio) {
		this.codMunicipio = codMunicipio;
	}
	public String getCodMunicipio() {
		return codMunicipio;
	}
	public void setListaBensImoveis(List<BemImovel> listaBensImoveis) {
		this.listaBensImoveis = listaBensImoveis;
	}
	public List<BemImovel> getListaBensImoveis() {
		return listaBensImoveis;
	}
	public void setFiltroAreaAte(BigDecimal filtroAreaAte) {
		this.filtroAreaAte = filtroAreaAte;
	}
	public BigDecimal getFiltroAreaAte() {
		return filtroAreaAte;
	}
	public void setFiltroAreaDe(BigDecimal filtroAreaDe) {
		this.filtroAreaDe = filtroAreaDe;
	}
	public BigDecimal getFiltroAreaDe() {
		return filtroAreaDe;
	}
	public void setFiltroAdministracao(String filtroAdministracao) {
		this.filtroAdministracao = filtroAdministracao;
	}
	public String getFiltroAdministracao() {
		return filtroAdministracao;
	}
	public void setOrgaoOcupante(String filtroOrgaoOcupante) {
		this.orgaoOcupante = filtroOrgaoOcupante;
	}
	public String getOrgaoOcupante() {
		return orgaoOcupante;
	}
	public Integer getIndOperadorOrgao() {
		return indOperadorOrgao;
	}
	public void setIndOperadorOrgao(Integer indOperadorOrgao) {
		this.indOperadorOrgao = indOperadorOrgao;
	}
	public Usuario getUsuarioS() {
		return usuarioS;
	}
	public void setUsuarioS(Usuario usuarioS) {
		this.usuarioS = usuarioS;
	}
	public void setAdmGeral(boolean admGeral) {
		this.admGeral = admGeral;
	}
	public boolean isAdmGeral() {
		return admGeral;
	}
	
	public void setCodInstituicao(String codInstituicao) {
		this.codInstituicao = codInstituicao;
	}
	public String getCodInstituicao() {
		return codInstituicao;
	}
	
	
	
	
}
