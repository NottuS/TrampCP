
package gov.pr.celepar.abi.form;

import org.apache.struts.validator.ValidatorForm;


/**
 * @author Oksana
 * @version 1.0
 * @since 17/06/2011
 * 
 * Responsável por encapsular os dados das telas Localizar BEm Imóvel Simplificado
 */
public class LocalizarBemImovelSimplificadoForm extends ValidatorForm {

	private static final long serialVersionUID = 793452875062817985L;
	private String actionType;
	private String actionTypeOcupResp;
	private String cep;
    private String uf;
    private String codMunicipio;
    private String logradouro;
    private String numero;
    private String bairroDistrito;
    private String complemento;
    private String municipio;

    //campos da tela pesquisa
    private String conMunicipio;
    private String conLogradouro;
    private String conBairroDistrito;
    private String conDenominacaoImovel;
    private String conCodUf;
    private String conCodMunicipio;
    private String conOcupacao;
    private String conObservacao;
    private String conInstituicao;
    
    private String camposPesquisaUCOrigem;
    private String actionUCOrigem;
    private String indGrupoSentinela;
    private String adm;
    
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String getActionTypeOcupResp() {
		return actionTypeOcupResp;
	}
	public void setActionTypeOcupResp(String actionTypeOcupResp) {
		this.actionTypeOcupResp = actionTypeOcupResp;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
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
	public String getLogradouro() {
		return logradouro;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getBairroDistrito() {
		return bairroDistrito;
	}
	public void setBairroDistrito(String bairroDistrito) {
		this.bairroDistrito = bairroDistrito;
	}
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	public String getMunicipio() {
		return municipio;
	}
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	public String getConMunicipio() {
		return conMunicipio;
	}
	public void setConMunicipio(String conMunicipio) {
		this.conMunicipio = conMunicipio;
	}
	public String getConLogradouro() {
		return conLogradouro;
	}
	public void setConLogradouro(String conLogradouro) {
		this.conLogradouro = conLogradouro;
	}
	public String getConBairroDistrito() {
		return conBairroDistrito;
	}
	public void setConBairroDistrito(String conBairroDistrito) {
		this.conBairroDistrito = conBairroDistrito;
	}
	public String getConDenominacaoImovel() {
		return conDenominacaoImovel;
	}
	public void setConDenominacaoImovel(String conDenominacaoImovel) {
		this.conDenominacaoImovel = conDenominacaoImovel;
	}
	public String getConCodUf() {
		return conCodUf;
	}
	public void setConCodUf(String conCodUf) {
		this.conCodUf = conCodUf;
	}
	public String getConCodMunicipio() {
		return conCodMunicipio;
	}
	public void setConCodMunicipio(String conCodMunicipio) {
		this.conCodMunicipio = conCodMunicipio;
	}
	public String getConOcupacao() {
		return conOcupacao;
	}
	public void setConOcupacao(String conOcupacao) {
		this.conOcupacao = conOcupacao;
	}
	public void setCamposPesquisaUCOrigem(String camposPesquisaUCOrigem) {
		this.camposPesquisaUCOrigem = camposPesquisaUCOrigem;
	}
	public String getCamposPesquisaUCOrigem() {
		return camposPesquisaUCOrigem;
	}
	public void setActionUCOrigem(String actionUCOrigem) {
		this.actionUCOrigem = actionUCOrigem;
	}
	public String getActionUCOrigem() {
		return actionUCOrigem;
	}
	public String getConObservacao() {
		return conObservacao;
	}
	public void setConObservacao(String conObservacao) {
		this.conObservacao = conObservacao;
	}
	public String getIndGrupoSentinela() {
		return indGrupoSentinela;
	}
	public void setIndGrupoSentinela(String indGrupoSentinela) {
		this.indGrupoSentinela = indGrupoSentinela;
	}
	public String getConInstituicao() {
		return conInstituicao;
	}
	public void setConInstituicao(String conInstituicao) {
		this.conInstituicao = conInstituicao;
	}
	public String getAdm() {
		return adm;
	}
	public void setAdm(String adm) {
		this.adm = adm;
	}

}
