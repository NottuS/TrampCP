/*
 * Este programa é licenciado de acordo com a
 * LPG-AP (LICENÇA PÚBLICA GERAL PARA PROGRAMAS DE COMPUTADOR DA ADMINISTRAÇÃO PÚBLICA),
 * versão 1.1 ou qualquer versão posterior.
 * A LPG-AP deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e REPRODUÇÕES deste Programa.
 * Caso uma cópia da LPG-AP não esteja disponível junto com este Programa, você
 * pode contatar o LICENCIANTE ou então acessar diretamente:
 * http://www.celepar.pr.gov.br/licenca/LPG-AP.pdf
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa
 * é preciso estar de acordo com os termos da LPG-AP.
 */

package gov.pr.celepar.framework.form;

import org.apache.struts.validator.ValidatorForm;


/**
 * @author Grupo Framework - Componentes
 * @version 1.0
 * @since 18/01/2005
 * 
 * Classe Exemplo:
 * Responsável por encapsular os dados das telas de matrícula.
 */
public class MatriculaForm extends ValidatorForm {
	
	private String actionType;
		
	private String idAluno;
	private String nomeAluno;
	private String nomePaiAluno;
	private String nomeMaeAluno;
	private String dtNascAluno;
	private String rgAluno;
	private String cpfAluno;
	private String logradouro;
	private String complemento;
	private String numero;
	private String bairro;
	private String cep;
	private String cidade;
	private String uf;
	private String idLocalidade;
	
	
	/**
	 * @return Returns the bairro.
	 */
	public String getBairro() {
		return bairro;
	}
	
	/**
	 * @param bairro The bairro to set.
	 */
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	
	/**
	 * @return Returns the cdAluno.
	 */
	public String getIdAluno() {
		return idAluno;
	}
	
	/**
	 * @param cdAluno The cdAluno to set.
	 */
	public void setIdAluno(String idAluno) {
		this.idAluno = idAluno;
	}
	
	/**
	 * @return Returns the cdCidade.
	 */
	public String getCidade() {
		return cidade;
	}
	
	/**
	 * @param cdCidade The cdCidade to set.
	 */
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	
	/**
	 * @return Returns the cep.
	 */
	public String getCep() {
		return cep;
	}
	
	/**
	 * @param cep The cep to set.
	 */
	public void setCep(String cep) {
		this.cep = cep;
	}
	
	/**
	 * @return Returns the complemento.
	 */
	public String getComplemento() {
		return complemento;
	}
	
	/**
	 * @param complemento The complemento to set.
	 */
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	
	/**
	 * @return Returns the cpfAluno.
	 */
	public String getCpfAluno() {
		return cpfAluno;
	}
	
	/**
	 * @param cpfAluno The cpfAluno to set.
	 */
	public void setCpfAluno(String cpfAluno) {
		this.cpfAluno = cpfAluno;
	}
	
	/**
	 * @return Returns the dtNascAluno.
	 */
	public String getDtNascAluno() {
		return dtNascAluno;
	}
	
	/**
	 * @param dtNascAluno The dtNascAluno to set.
	 */
	public void setDtNascAluno(String dtNascAluno) {
		this.dtNascAluno = dtNascAluno;
	}
	
	/**
	 * @return Returns the logradouro.
	 */
	public String getLogradouro() {
		return logradouro;
	}
	
	/**
	 * @param logradouro The logradouro to set.
	 */
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	
	/**
	 * @return Returns the nomeAluno.
	 */
	public String getNomeAluno() {
		return nomeAluno;
	}
	
	/**
	 * @param nomeAluno The nomeAluno to set.
	 */
	public void setNomeAluno(String nomeAluno) {
		this.nomeAluno = nomeAluno;
	}
	
	/**
	 * @return Returns the nomeMaeAluno.
	 */
	public String getNomeMaeAluno() {
		return nomeMaeAluno;
	}
	
	/**
	 * @param nomeMaeAluno The nomeMaeAluno to set.
	 */
	public void setNomeMaeAluno(String nomeMaeAluno) {
		this.nomeMaeAluno = nomeMaeAluno;
	}
	
	/**
	 * @return Returns the nomePaiAluno.
	 */
	public String getNomePaiAluno() {
		return nomePaiAluno;
	}
	
	/**
	 * @param nomePaiAluno The nomePaiAluno to set.
	 */
	public void setNomePaiAluno(String nomePaiAluno) {
		this.nomePaiAluno = nomePaiAluno;
	}
	
	/**
	 * @return Returns the numero.
	 */
	public String getNumero() {
		return numero;
	}
	
	/**
	 * @param numero The numero to set.
	 */
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	/**
	 * @return Returns the rgAluno.
	 */
	public String getRgAluno() {
		return rgAluno;
	}
	
	/**
	 * @param rgAluno The rgAluno to set.
	 */
	public void setRgAluno(String rgAluno) {
		this.rgAluno = rgAluno;
	}
	
	/**
	 * @return Returns the actionType.
	 */
	public String getActionType() {
		return actionType;
	}
	
	/**
	 * @param actionType The actionType to set.
	 */
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getIdLocalidade() {
		return idLocalidade;
	}

	public void setIdLocalidade(String idLocalidade) {
		this.idLocalidade = idLocalidade;
	}


}
