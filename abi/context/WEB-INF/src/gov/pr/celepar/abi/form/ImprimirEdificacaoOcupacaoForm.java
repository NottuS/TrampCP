
package gov.pr.celepar.abi.form;

import org.apache.struts.validator.ValidatorForm;


/**
 * @author Luciana R. Bélico
 * @version 1.0
 * @since 23/03/2010
 * 
 * Responsável por encapsular os dados da tela de filtro do Relatório de  Edificação com Ocupacao.
 */
public class ImprimirEdificacaoOcupacaoForm extends ValidatorForm {

	private static final long serialVersionUID = -5255681889641711894L;

	private String actionType;
	
	private String orgao;
	private String municipio;
	private String codMunicipio;
	private String radRelatorio;
	private String uf;
	private String codOrgao;
	private Boolean indOperadorOrgao;
	private String codInstituicao;
	
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String getOrgao() {
		return orgao;
	}
	public void setOrgao(String orgao) {
		this.orgao = orgao;
	}
	public String getMunicipio() {
		return municipio;
	}
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	public String getCodMunicipio() {
		return codMunicipio;
	}
	public void setCodMunicipio(String codMunicipio) {
		this.codMunicipio = codMunicipio;
	}
	public String getRadRelatorio() {
		return radRelatorio;
	}
	public void setRadRelatorio(String radRelatorio) {
		this.radRelatorio = radRelatorio;
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
	public Boolean getIndOperadorOrgao() {
		return indOperadorOrgao;
	}
	public void setIndOperadorOrgao(Boolean indOperadorOrgao) {
		this.indOperadorOrgao = indOperadorOrgao;
	}
	public void setCodInstituicao(String codInstituicao) {
		this.codInstituicao = codInstituicao;
	}
	public String getCodInstituicao() {
		return codInstituicao;
	}
	
		
}
