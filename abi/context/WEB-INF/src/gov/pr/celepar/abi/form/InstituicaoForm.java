
package gov.pr.celepar.abi.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.ValidatorForm;


/**
 * @author Luciana R. Bélico
 * @since 06/12/2011
 * 
 * Responsável por encapsular os dados das telas de instituicao
 */
public class InstituicaoForm extends ValidatorForm {

	private static final long serialVersionUID = 5917021287170220868L;
	
	private String actionType;
	private String codInstituicao;
	private String sigla;
	private String nome;
	private String descricaoRelatorio;
	
	
	private String municipio;
	private String codMunicipio;
	private String uf;
	private String logradouro;
	private String complemento;
	private String bairro;
	private String cep;
	private String numero;
	private String contato;
	private String telDdd;
	private String telNumero;
	private String email;
	
	private FormFile logotipo;
	
	private String conSigla;
	private String conNome;
	
	
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String getConSigla() {
		return conSigla;
	}
	public void setConSigla(String conSigla) {
		this.conSigla = conSigla;
	}
	public String getConNome() {
		return conNome;
	}
	public void setConNome(String conNome) {
		this.conNome = conNome;
	}
	public String getCodInstituicao() {
		return codInstituicao;
	}
	public void setCodInstituicao(String codInstituicao) {
		this.codInstituicao = codInstituicao;
	}
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescricaoRelatorio() {
		return descricaoRelatorio;
	}
	public void setDescricaoRelatorio(String descricaoRelatorio) {
		this.descricaoRelatorio = descricaoRelatorio;
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
	public String getContato() {
		return contato;
	}
	public void setContato(String contato) {
		this.contato = contato;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setLogotipo(FormFile logotipo) {
		this.logotipo = logotipo;
	}
	public FormFile getLogotipo() {
		return logotipo;
	}

	
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		
		this.bairro=null;
		this.cep=null;
		this.codInstituicao=null;
		this.codMunicipio=null;
		this.complemento=null;
		this.contato=null;
		this.descricaoRelatorio=null;
		this.email=null;
		this.logotipo=null;
		this.logradouro=null;
		this.municipio=null;
		
	}
	

	}
