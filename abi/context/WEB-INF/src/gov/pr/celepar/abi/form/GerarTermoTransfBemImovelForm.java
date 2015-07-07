
package gov.pr.celepar.abi.form;

import gov.pr.celepar.abi.generico.form.BaseForm;


/**
 * Classe responsavel por permitir a emissao de Termos de Transferencia de Bens Imoveis.<BR>
 * @author ginaalmeida
 * @version 1.0
 * @since 02/08/2011
 */
public class GerarTermoTransfBemImovelForm extends BaseForm {

	private static final long serialVersionUID = -4019275731432618350L;
	
	private String isGpAdmGeralUsuarioLogado; 

	private String codTransferencia;
	private String numTermo;
	private String instituicao;
	private String instituicaoDesc;
	private String codBemImovel;
	private String nrBemImovel;
	private String orgao;
	private String protocolo;
	private String dtInicioVigencia;
	private String dtFimVigencia;
	private String status;
	private String actionType;
	private String textoDocInformacao;
	private String imprimir;
	private String codAssinatura;

	public String getCodTransferencia() {
		return codTransferencia;
	}

	public void setCodTransferencia(String codTransferencia) {
		this.codTransferencia = codTransferencia;
	}

	public String getNumTermo() {
		return numTermo;
	}

	public void setNumTermo(String numTermo) {
		this.numTermo = numTermo;
	}

	public String getOrgao() {
		return orgao;
	}

	public void setOrgao(String orgao) {
		this.orgao = orgao;
	}

	public String getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
	}

	public String getDtInicioVigencia() {
		return dtInicioVigencia;
	}

	public void setDtInicioVigencia(String dtInicioVigencia) {
		this.dtInicioVigencia = dtInicioVigencia;
	}

	public String getDtFimVigencia() {
		return dtFimVigencia;
	}

	public void setDtFimVigencia(String dtFimVigencia) {
		this.dtFimVigencia = dtFimVigencia;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public String getImprimir() {
		return imprimir;
	}

	public void setImprimir(String imprimir) {
		this.imprimir = imprimir;
	}

	public void setTextoDocInformacao(String textoDocInformacao) {
		this.textoDocInformacao = textoDocInformacao;
	}

	public String getTextoDocInformacao() {
		return textoDocInformacao;
	}

	public void setIsGpAdmGeralUsuarioLogado(String isGpAdmGeralUsuarioLogado) {
		this.isGpAdmGeralUsuarioLogado = isGpAdmGeralUsuarioLogado;
	}

	public String getIsGpAdmGeralUsuarioLogado() {
		return isGpAdmGeralUsuarioLogado;
	}

	public void setInstituicao(String instituicao) {
		this.instituicao = instituicao;
	}

	public String getInstituicao() {
		return instituicao;
	}

	public void setInstituicaoDesc(String instituicaoDesc) {
		this.instituicaoDesc = instituicaoDesc;
	}

	public String getInstituicaoDesc() {
		return instituicaoDesc;
	}

	public void setCodBemImovel(String codBemImovel) {
		this.codBemImovel = codBemImovel;
	}

	public String getCodBemImovel() {
		return codBemImovel;
	}

	public void setNrBemImovel(String nrBemImovel) {
		this.nrBemImovel = nrBemImovel;
	}

	public String getNrBemImovel() {
		return nrBemImovel;
	}

	public void setCodAssinatura(String codAssinatura) {
		this.codAssinatura = codAssinatura;
	}

	public String getCodAssinatura() {
		return codAssinatura;
	}

}