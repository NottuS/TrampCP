package gov.pr.celepar.abi.dto;

import gov.pr.celepar.abi.pojo.Instituicao;
import gov.pr.celepar.abi.pojo.Usuario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FiltroRelatorioEdificacaoOcupacaoDTO implements Serializable{

	private static final long serialVersionUID = 7907552231835916618L;
	private List<RelatorioEdificacaoOcupacaoDTO> listaBensImoveis = new ArrayList <RelatorioEdificacaoOcupacaoDTO>();
	private String usuario;
	private String filtroMunicipio="";
	private String filtroRelatorio;
	private String filtroOrgao="";
	private String codMunicipio;
	private String uf="";
	private String codOrgao;
	private Usuario usuarioS;
	private List<Integer> listaCodOrgao;
	private Instituicao instituicao;
	
	public List<RelatorioEdificacaoOcupacaoDTO> getListaBensImoveis() {
		return listaBensImoveis;
	}
	public void setListaBensImoveis(List<RelatorioEdificacaoOcupacaoDTO> listaBensImoveis) {
		this.listaBensImoveis = listaBensImoveis;
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
	public String getFiltroRelatorio() {
		return filtroRelatorio;
	}
	public void setFiltroRelatorio(String filtroRelatorio) {
		this.filtroRelatorio = filtroRelatorio;
	}
	public String getFiltroOrgao() {
		return filtroOrgao;
	}
	public void setFiltroOrgao(String filtroOrgao) {
		this.filtroOrgao = filtroOrgao;
	}
	public String getCodMunicipio() {
		return codMunicipio;
	}
	public void setCodMunicipio(String codMunicipio) {
		this.codMunicipio = codMunicipio;
	}
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public String getCodOrgao() {
		return codOrgao;
	}
	public void setCodOrgao(String codOrgao) {
		this.codOrgao = codOrgao;
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
	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}
	public Instituicao getInstituicao() {
		return instituicao;
	}
	
}
