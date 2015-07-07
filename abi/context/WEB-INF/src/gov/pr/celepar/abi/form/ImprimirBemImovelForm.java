
package gov.pr.celepar.abi.form;

import org.apache.struts.validator.ValidatorForm;


/**
 * @author Luciana R. Bélico
 * @version 1.0
 * @since 10/02/2010
 * 
 * Responsável por encapsular os dados das telas de Tipo de Lei de Bem Imovel.
 */
public class ImprimirBemImovelForm extends ValidatorForm {
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -8077684531185632817L;

	private String actionType;
	
	private String situacaoImovel;
	private String classificacaoBemImovel;
	private String situacaoLegalCartorial;
	private String radTerreno;
	private String municipio;
	private String codMunicipio;
	private String radRelatorio;
	private String radAdministracao;
	private String uf;
	private String codSituacaoImovel;
	private String codClassificacaoBemImovel;
	private String codSituacaoLegalCartorial;
    private String conOcupacao;
	private String radAdministracaoOrgao;
    private String orgao;
    private String tipoMunicipio;
    private String codFaixaMunicipio;
    private Integer indOperadorOrgao;
    private String codInstituicao;
	
	public String getOrgao() {
		return orgao;
	}
	public void setOrgao(String orgao) {
		this.orgao = orgao;
	}
	public String getCodSituacaoImovel() {
		return codSituacaoImovel;
	}
	public void setCodSituacaoImovel(String codSituacaoImovel) {
		this.codSituacaoImovel = codSituacaoImovel;
	}
	public String getCodClassificacaoBemImovel() {
		return codClassificacaoBemImovel;
	}
	public void setCodClassificacaoBemImovel(String codClassificacaoBemImovel) {
		this.codClassificacaoBemImovel = codClassificacaoBemImovel;
	}
	public String getCodSituacaoLegalCartorial() {
		return codSituacaoLegalCartorial;
	}
	public void setCodSituacaoLegalCartorial(String codSituacaoLegalCartorial) {
		this.codSituacaoLegalCartorial = codSituacaoLegalCartorial;
	}
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
	public void setSituacaoLegalCartorial(String situacaoLegalCartorial) {
		this.situacaoLegalCartorial = situacaoLegalCartorial;
	}
	public String getSituacaoLegalCartorial() {
		return situacaoLegalCartorial;
	}
	public void setRadAdministracao(String radAdministracao) {
		this.radAdministracao = radAdministracao;
	}
	public String getRadAdministracao() {
		return radAdministracao;
	}
	public void setConOcupacao(String conOcupacao) {
		this.conOcupacao = conOcupacao;
	}
	public String getConOcupacao() {
		return conOcupacao;
	}
	public void setTipoMunicipio(String tipoMunicipio) {
		this.tipoMunicipio = tipoMunicipio;
	}
	public String getTipoMunicipio() {
		return tipoMunicipio;
	}
	public void setCodFaixaMunicipio(String codFaixaMunicipio) {
		this.codFaixaMunicipio = codFaixaMunicipio;
	}
	public String getCodFaixaMunicipio() {
		return codFaixaMunicipio;
	}
	public void setRadAdministracaoOrgao(String radAdministracaoOrgao) {
		this.radAdministracaoOrgao = radAdministracaoOrgao;
	}
	public String getRadAdministracaoOrgao() {
		return radAdministracaoOrgao;
	}
	public Integer getIndOperadorOrgao() {
		return indOperadorOrgao;
	}
	public void setIndOperadorOrgao(Integer indOperadorOrgao) {
		this.indOperadorOrgao = indOperadorOrgao;
	}
	public void setCodInstituicao(String codInstituicao) {
		this.codInstituicao = codInstituicao;
	}
	public String getCodInstituicao() {
		return codInstituicao;
	}
	
}
