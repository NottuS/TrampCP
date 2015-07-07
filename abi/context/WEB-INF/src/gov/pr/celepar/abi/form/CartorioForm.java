
package gov.pr.celepar.abi.form;

import org.apache.struts.validator.ValidatorForm;


/**
 * @author Luciana R. Bélico
 * @since 26/03/2010
 * 
 * Responsável por encapsular os dados das telas de cartório
 */
public class CartorioForm extends ValidatorForm {

	private static final long serialVersionUID = -6254148544000314476L;
	private String actionType;
	private String codCartorio;
	private String descricao;
	private String municipio;
	private String codMunicipio;
	private String uf;
	private String logradouro;
	private String complemento;
	private String bairro;
	private String cep;
	private String numero;
	private String nomeContato;
	private String telDdd;
	private String telNumero;
	
	private String conDescricao;
	private String conUf;
	private String conCodMunicipio;
	
	

	public String getConDescricao() {
		return conDescricao;
	}
	public void setConDescricao(String conDescricao) {
		this.conDescricao = conDescricao;
	}
	public String getConUf() {
		return conUf;
	}
	public void setConUf(String conUf) {
		this.conUf = conUf;
	}
	public String getConCodMunicipio() {
		return conCodMunicipio;
	}
	public void setConCodMunicipio(String conCodMunicipio) {
		this.conCodMunicipio = conCodMunicipio;
	}
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String getCodCartorio() {
		return codCartorio;
	}
	public void setCodCartorio(String codCartorio) {
		this.codCartorio = codCartorio;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
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
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public String getLogradouro() {
		return logradouro;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getNomeContato() {
		return nomeContato;
	}
	public void setNomeContato(String nomeContato) {
		this.nomeContato = nomeContato;
	}
	public String getTelDdd() {
		return telDdd;
	}
	public void setTelDdd(String telDdd) {
		this.telDdd = telDdd;
	}
	public String getTelNumero() {
		return telNumero;
	}
	public void setTelNumero(String telNumero) {
		this.telNumero = telNumero;
	}
}
