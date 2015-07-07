
package gov.pr.celepar.abi.form;

import org.apache.struts.validator.ValidatorForm;


/**
 * @author claudiofain
 * @version 1.0
 * @since 06/01/2010
 * 
 * Responsável por encapsular os dados das telas de Tabelionatos.
 */
public class TabelionatoForm extends ValidatorForm {
	
	private static final long serialVersionUID = -2927846874936573265L;
	private String actionType;
	private String codTabelionato;
	private String descricao;
	private String cep;
	private String uf;
	private String municipio;
	private String logradouro;
	private String numero;
	private String bairro;
	private String complemento;
	private String nomeContato;
	private String telDdd;
	private String telNumero;
	private String codMunicipio;
	
	//campos da tela de pesquisa
	private String conDescricao;
	private String conUF;
	private String conMunicipio;
	
	public String getConDescricao() {
		return conDescricao;
	}

	public void setConDescricao(String conDescricao) {
		this.conDescricao = conDescricao;
	}

	public String getConUF() {
		return conUF;
	}

	public void setConUF(String conUF) {
		this.conUF = conUF;
	}

	public String getConMunicipio() {
		return conMunicipio;
	}

	public void setConMunicipio(String conMunicipio) {
		this.conMunicipio = conMunicipio;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public String getCodTabelionato() {
		return codTabelionato;
	}
	public void setCodTabelionato(String codTabelionato) {
		this.codTabelionato = codTabelionato;
	}

	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
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

	public String getMunicipio() {
		return municipio;
	}
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
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
	
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
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

	public void setCodMunicipio(String codMunicipio) {
		this.codMunicipio = codMunicipio;
	}

	public String getCodMunicipio() {
		return codMunicipio;
	}
}
