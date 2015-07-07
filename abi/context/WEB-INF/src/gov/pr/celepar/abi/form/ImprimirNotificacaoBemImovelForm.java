
package gov.pr.celepar.abi.form;

import org.apache.struts.validator.ValidatorForm;


/**
 * @author Luciana R. Bélico
 * @version 1.0
 * @since 10/02/2010
 * 
 * Responsável por encapsular os dados das telas de Tipo de Lei de Bem Imovel.
 */
public class ImprimirNotificacaoBemImovelForm extends ValidatorForm {
	
	private static final long serialVersionUID = -7113977698035970667L;

	private String actionType;
	
	private String situacaoImovel;
	private String codSituacao;
	private String codClassificacao;
	private String classificacaoBemImovel;
	private String tsNotificacaoDe;
	private String tsNotificacaoAte;
	private String radTerreno;
	private String municipio;
	private String codMunicipio;
	private String radRelatorio;
	private String radAdministracao;
	private String uf;
	private String cep;
	private String codInstituicao;
	
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	
	public void setSituacaoImovel(String situacaoImovel) {
		this.situacaoImovel = situacaoImovel;
	}
	public String getSituacaoImovel() {
		return situacaoImovel;
	}
	public void setClassificacaoBemImovel(String classificacaoBemImovel) {
		this.classificacaoBemImovel = classificacaoBemImovel;
	}
	public String getClassificacaoBemImovel() {
		return classificacaoBemImovel;
	}
	public void setTsNotificacaoDe(String tsNotificacaoDe) {
		this.tsNotificacaoDe = tsNotificacaoDe;
	}
	public String getTsNotificacaoDe() {
		return tsNotificacaoDe;
	}
	public void setTsNotificacaoAte(String tsNotificacaoAte) {
		this.tsNotificacaoAte = tsNotificacaoAte;
	}
	public String getTsNotificacaoAte() {
		return tsNotificacaoAte;
	}
	public void setRadTerreno(String radTerreno) {
		this.radTerreno = radTerreno;
	}
	public String getRadTerreno() {
		return radTerreno;
	}
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	public String getMunicipio() {
		return municipio;
	}
	public void setRadRelatorio(String radRelatorio) {
		this.radRelatorio = radRelatorio;
	}
	public String getRadRelatorio() {
		return radRelatorio;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public String getUf() {
		return uf;
	}
	
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getCep() {
		return cep;
	}
	public void setCodMunicipio(String codMunicipio) {
		this.codMunicipio = codMunicipio;
	}
	public String getCodMunicipio() {
		return codMunicipio;
	}
	public void setCodSituacao(String codSituacao) {
		this.codSituacao = codSituacao;
	}
	public String getCodSituacao() {
		return codSituacao;
	}
	public void setCodClassificacao(String codClassificacao) {
		this.codClassificacao = codClassificacao;
	}
	public String getCodClassificacao() {
		return codClassificacao;
	}
	public void setRadAdministracao(String radAdministracao) {
		this.radAdministracao = radAdministracao;
	}
	public String getRadAdministracao() {
		return radAdministracao;
	}
	public void setCodInstituicao(String codInstituicao) {
		this.codInstituicao = codInstituicao;
	}
	public String getCodInstituicao() {
		return codInstituicao;
	}
	
	
	
	


}
