
package gov.pr.celepar.abi.form;

import org.apache.struts.validator.ValidatorForm;


/**
 * @author Luciana R. Bélico
 * @version 1.0
 * @since 17/03/2010
 * 
 * Responsável por encapsular os dados das telas de filtro do relatorio de Area de Bem Imovel.
 */
public class ImprimirAreaBemImovelForm extends ValidatorForm {
	
	

	
	private static final long serialVersionUID = 9108282313930097928L;


	private String actionType;
	
	private String areaDe;
	private String areaAte;
	private String radTerreno;
	private String municipio;
	private String codMunicipio;
	private String radRelatorio;
	private String radAdministracao;
	private String uf;
	private Boolean indOperadorOrgao;
	private String codInstituicao;
	
	
	
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
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
	public void setCodMunicipio(String codMunicipio) {
		this.codMunicipio = codMunicipio;
	}
	public String getCodMunicipio() {
		return codMunicipio;
	}
	public void setAreaDe(String areaDe) {
		this.areaDe = areaDe;
	}
	public String getAreaDe() {
		return areaDe;
	}
	public void setAreaAte(String areaAte) {
		this.areaAte = areaAte;
	}
	public String getAreaAte() {
		return areaAte;
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
	public void setCodInstituicao(String codInstituicao) {
		this.codInstituicao = codInstituicao;
	}
	public String getCodInstituicao() {
		return codInstituicao;
	}
	


}
