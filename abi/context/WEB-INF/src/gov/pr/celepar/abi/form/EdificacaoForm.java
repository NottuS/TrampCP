package gov.pr.celepar.abi.form;

import gov.pr.celepar.abi.util.Dominios.administracaoImovel;
import gov.pr.celepar.abi.util.Util;


/**
 * @author 
 * @version 1.0
 * @since 19/01/2010
 * 
 * Responsável por encapsular os dados das telas de Edificacao.
 */
public class EdificacaoForm extends BemImovelForm {
	
	private static final long serialVersionUID = -743256314553799158L;
	
	private String administracao;
	private String actionType;
	private String codBemImovel;
	private String tipoConstrucao;
	private String tipoEdificacao;
	private String especificacao;
	private String logradouro;
	private String areaConstruida;
	private String areaUtilizada;
	private String dataAverbacao;
	private String lote;
	private String codEdificacao;
	private String municipio;
	private String uf;

	
	
	

	public String getAreaConstruida() {
		return areaConstruida;
	}
	public void setAreaConstruida(String areaConstruida) {
		this.areaConstruida = areaConstruida;
	}
	public String getAreaUtilizada() {
		return areaUtilizada;
	}
	public void setAreaUtilizada(String areaUtilizada) {
		this.areaUtilizada = areaUtilizada;
	}
	public String getDataAverbacao() {
		return dataAverbacao;
	}
	public void setDataAverbacao(String dataAverbacao) {
		this.dataAverbacao = dataAverbacao;
	}
	public String getLote() {
		return lote;
	}
	public void setLote(String lote) {
		this.lote = lote;
	}
	public String getTipoConstrucao() {
		return tipoConstrucao;
	}
	public void setTipoConstrucao(String tipoConstrucao) {
		this.tipoConstrucao = tipoConstrucao;
	}
	public String getTipoEdificacao() {
		return tipoEdificacao;
	}
	public void setTipoEdificacao(String tipoEdificacao) {
		this.tipoEdificacao = tipoEdificacao;
	}
	public String getEspecificacao() {
		return especificacao;
	}
	public void setEspecificacao(String especificacao) {
		this.especificacao = especificacao;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	public String getLogradouro() {
		return logradouro;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String getActionType() {
		return actionType;
	}
	public void setCodBemImovel(String codBemImovel) {
		this.codBemImovel = codBemImovel;
	}
	public String getCodBemImovel() {
		return codBemImovel;
	}
	public String getDescricaoAdministracao(){
		if (!Util.strEmBranco( this.administracao)){
			return administracaoImovel.getAdministracaoImovelByIndex(Integer.valueOf(this.administracao)).getLabel();
		}
		else{
			return "";
		}
	}
	public String getAdministracao() {
		return administracao;
	}
	public void setAdministracao(String administracao) {
		this.administracao = administracao;
	}
    
	public void setCodEdificacao(String codEdificacao) {
		this.codEdificacao = codEdificacao;
	}
	public String getCodEdificacao() {
		return codEdificacao;
	}
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	public String getMunicipio() {
		return municipio;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public String getUf() {
		return uf;
	}

}
