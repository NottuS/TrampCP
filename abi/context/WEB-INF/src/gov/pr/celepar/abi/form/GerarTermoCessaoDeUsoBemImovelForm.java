
package gov.pr.celepar.abi.form;

import gov.pr.celepar.abi.generico.form.BaseForm;


/**
 * Classe responsavel por permitir a emissao de Termos de Cessão de Uso de Bens Imoveis.<BR>
 * @version 1.0
 */
public class GerarTermoCessaoDeUsoBemImovelForm extends BaseForm {

	private static final long serialVersionUID = -764000503573168466L;

	private String isGpAdmGeralUsuarioLogado; 

	private String codCessaoDeUso;
	private String numTermo;
	private String instituicao;
	private String instituicaoDesc;
	private String codBemImovel;
	private String nrBemImovel;
	private String orgao;
	private String protocolo;
	private String dtInicioVigencia;
	private String dtFimVigencia;
	private String projetoLei;
	private String codLei;
	private String numeroLei;
	private String dataAssinaturaLei;
	private String dataPublicacaoLei;
	private String nrDioeLei;
	private String status;
	private String actionType;

	private String imprimir;
	
	public String getCodCessaoDeUso() {
		return codCessaoDeUso;
	}
	public void setCodCessaoDeUso(String codCessaoDeUso) {
		this.codCessaoDeUso = codCessaoDeUso;
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
	public String getProjetoLei() {
		return projetoLei;
	}
	public void setProjetoLei(String projetoLei) {
		this.projetoLei = projetoLei;
	}
	public String getCodLei() {
		return codLei;
	}
	public void setCodLei(String codLei) {
		this.codLei = codLei;
	}
	public String getNumeroLei() {
		return numeroLei;
	}
	public void setNumeroLei(String numeroLei) {
		this.numeroLei = numeroLei;
	}
	public String getDataAssinaturaLei() {
		return dataAssinaturaLei;
	}
	public void setDataAssinaturaLei(String dataAssinaturaLei) {
		this.dataAssinaturaLei = dataAssinaturaLei;
	}
	public String getDataPublicacaoLei() {
		return dataPublicacaoLei;
	}
	public void setDataPublicacaoLei(String dataPublicacaoLei) {
		this.dataPublicacaoLei = dataPublicacaoLei;
	}
	public String getNrDioeLei() {
		return nrDioeLei;
	}
	public void setNrDioeLei(String nrDioeLei) {
		this.nrDioeLei = nrDioeLei;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setNumTermo(String numTermo) {
		this.numTermo = numTermo;
	}
	public String getNumTermo() {
		return numTermo;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String getActionType() {
		return actionType;
	}
	public void setDtInicioVigencia(String dtInicioVigencia) {
		this.dtInicioVigencia = dtInicioVigencia;
	}
	public String getDtInicioVigencia() {
		return dtInicioVigencia;
	}
	public void setDtFimVigencia(String dtFimVigencia) {
		this.dtFimVigencia = dtFimVigencia;
	}
	public String getDtFimVigencia() {
		return dtFimVigencia;
	}
	public void setImprimir(String imprimir) {
		this.imprimir = imprimir;
	}
	public String getImprimir() {
		return imprimir;
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
	
}