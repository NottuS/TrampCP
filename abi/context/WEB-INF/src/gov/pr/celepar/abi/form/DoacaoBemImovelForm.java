package gov.pr.celepar.abi.form;

import org.apache.struts.validator.ValidatorForm;

public class DoacaoBemImovelForm extends ValidatorForm {

	private static final long serialVersionUID = -8744706963800385192L;

    private String actionType;
	private String pesqExec; 
	private String isGpAdmGeralUsuarioLogado; 
	
	private String conInstituicao;
	private String conNrTermo;
	private String conCodBemImovel;
	private String conNrBemImovel;
	private String conProtocolo;
	private String conSituacao;
	private String conAdministracao;
	private String conOrgaoResponsavel;
    private String uf;
    private String codMunicipio;
    
	private String instituicao;
	private String instituicaoDesc;
    private String codAssinaturaDoacao;
    private String codItemDoacao;
	private String codDoacao;
	private String nrBemImovel;
	private String codBemImovel;
	private String administracao;
	private String orgaoResponsavel;
	private String orgaoResponsavelDesc;
	private String tipoDoacao;
	private String edificacao;
	private String utilizacao;
	private String doacaoMetros;
	private String doacaoPercentual;
	private String observacao;
	private String dtInicioVigencia;
	private String dtFimVigencia;
	private String protocolo;
	private String projetoLei;
	private String codLei;
	private String numeroLei;
	private String dataAssinaturaLei;
	private String dataPublicacaoLei;
	private String nrDioeLei;
	private String orgaoAssinatura;
	private String cargoAssinatura;
	private String nomeAssinatura;
	private String ordemAssinatura;
	private String status;
	private String itemTotal;
	private String qtdItens;
	
	private String incluidoPor;
	private String alteradoPor;
	private String excluidoPor;
	
	private String tipoRevogDevDesc;
	private String projetoLeiDesc;
	private String administracaoDesc;
	
	private String tipoRevogDev;
	private String motivo;
	private String nrOficio; 
	
	private String ucsDestino;
	private String ucsChamador;
	private String ucsRetorno;
	
	public String getConNrTermo() {
		return conNrTermo;
	}

	public void setConNrTermo(String conNrTermo) {
		this.conNrTermo = conNrTermo;
	}

	public String getConProtocolo() {
		return conProtocolo;
	}

	public void setConProtocolo(String conProtocolo) {
		this.conProtocolo = conProtocolo;
	}

	public String getConSituacao() {
		return conSituacao;
	}

	public void setConSituacao(String conSituacao) {
		this.conSituacao = conSituacao;
	}

	public String getConAdministracao() {
		return conAdministracao;
	}

	public void setConAdministracao(String conAdministracao) {
		this.conAdministracao = conAdministracao;
	}

	public String getConOrgaoResponsavel() {
		return conOrgaoResponsavel;
	}

	public void setConOrgaoResponsavel(String conOrgaoResponsavel) {
		this.conOrgaoResponsavel = conOrgaoResponsavel;
	}

	public String getCodDoacao() {
		return codDoacao;
	}

	public void setCodDoacao(String codDoacao) {
		this.codDoacao = codDoacao;
	}

	public String getAdministracao() {
		return administracao;
	}

	public void setAdministracao(String administracao) {
		this.administracao = administracao;
	}

	public String getTipoDoacao() {
		return tipoDoacao;
	}

	public void setTipoDoacao(String tipoDoacao) {
		this.tipoDoacao = tipoDoacao;
	}

	public String getUtilizacao() {
		return utilizacao;
	}

	public void setUtilizacao(String utilizacao) {
		this.utilizacao = utilizacao;
	}

	public String getDoacaoMetros() {
		return doacaoMetros;
	}

	public void setDoacaoMetros(String doacaoMetros) {
		this.doacaoMetros = doacaoMetros;
	}

	public String getDoacaoPercentual() {
		return doacaoPercentual;
	}

	public void setDoacaoPercentual(String doacaoPercentual) {
		this.doacaoPercentual = doacaoPercentual;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
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

	public String getOrgaoAssinatura() {
		return orgaoAssinatura;
	}

	public void setOrgaoAssinatura(String orgaoAssinatura) {
		this.orgaoAssinatura = orgaoAssinatura;
	}

	public String getCargoAssinatura() {
		return cargoAssinatura;
	}

	public void setCargoAssinatura(String cargoAssinatura) {
		this.cargoAssinatura = cargoAssinatura;
	}

	public String getNomeAssinatura() {
		return nomeAssinatura;
	}

	public void setNomeAssinatura(String nomeAssinatura) {
		this.nomeAssinatura = nomeAssinatura;
	}

	public String getOrdemAssinatura() {
		return ordemAssinatura;
	}

	public void setOrdemAssinatura(String ordemAssinatura) {
		this.ordemAssinatura = ordemAssinatura;
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

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public String getPesqExec() {
		return pesqExec;
	}

	public void setPesqExec(String pesqExec) {
		this.pesqExec = pesqExec;
	}

	public void setEdificacao(String edificacao) {
		this.edificacao = edificacao;
	}

	public String getEdificacao() {
		return edificacao;
	}

	public String getCodAssinaturaDoacao() {
		return codAssinaturaDoacao;
	}

	public void setCodAssinaturaDoacao(String codAssinaturaDoacao) {
		this.codAssinaturaDoacao = codAssinaturaDoacao;
	}

	public String getCodItemDoacao() {
		return codItemDoacao;
	}

	public void setCodItemDoacao(String codItemDoacao) {
		this.codItemDoacao = codItemDoacao;
	}

	public String getIncluidoPor() {
		return incluidoPor;
	}

	public void setIncluidoPor(String incluidoPor) {
		this.incluidoPor = incluidoPor;
	}

	public String getAlteradoPor() {
		return alteradoPor;
	}

	public void setAlteradoPor(String alteradoPor) {
		this.alteradoPor = alteradoPor;
	}

	public String getExcluidoPor() {
		return excluidoPor;
	}

	public void setExcluidoPor(String excluidoPor) {
		this.excluidoPor = excluidoPor;
	}

	public void setCodLei(String codLei) {
		this.codLei = codLei;
	}

	public String getCodLei() {
		return codLei;
	}

	public void setAdministracaoDesc(String administracaoDesc) {
		this.administracaoDesc = administracaoDesc;
	}

	public String getAdministracaoDesc() {
		return administracaoDesc;
	}

	public void setOrgaoResponsavel(String orgaoResponsavel) {
		this.orgaoResponsavel = orgaoResponsavel;
	}

	public String getOrgaoResponsavel() {
		return orgaoResponsavel;
	}

	public void setProjetoLeiDesc(String projetoLeiDesc) {
		this.projetoLeiDesc = projetoLeiDesc;
	}

	public String getProjetoLeiDesc() {
		return projetoLeiDesc;
	}

	public void setTipoRevogDevDesc(String tipoRevogDevDesc) {
		this.tipoRevogDevDesc = tipoRevogDevDesc;
	}

	public String getTipoRevogDevDesc() {
		return tipoRevogDevDesc;
	}

	public void setOrgaoResponsavelDesc(String orgaoResponsavelDesc) {
		this.orgaoResponsavelDesc = orgaoResponsavelDesc;
	}

	public String getOrgaoResponsavelDesc() {
		return orgaoResponsavelDesc;
	}

	public String getTipoRevogDev() {
		return tipoRevogDev;
	}

	public void setTipoRevogDev(String tipoRevogDev) {
		this.tipoRevogDev = tipoRevogDev;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getNrOficio() {
		return nrOficio;
	}

	public void setNrOficio(String nrOficio) {
		this.nrOficio = nrOficio;
	}
	
	public String getUcsDestino() {
		return ucsDestino;
	}
	public void setUcsDestino(String ucsDestino) {
		this.ucsDestino = ucsDestino;
	}
	public String getUcsChamador() {
		return ucsChamador;
	}
	public void setUcsChamador(String ucsChamador) {
		this.ucsChamador = ucsChamador;
	}
	public String getUcsRetorno() {
		return ucsRetorno;
	}
	public void setUcsRetorno(String ucsRetorno) {
		this.ucsRetorno = ucsRetorno;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setItemTotal(String itemTotal) {
		this.itemTotal = itemTotal;
	}

	public String getItemTotal() {
		return itemTotal;
	}

	public void setQtdItens(String qtdItens) {
		this.qtdItens = qtdItens;
	}

	public String getQtdItens() {
		return qtdItens;
	}

	public void setConInstituicao(String conInstituicao) {
		this.conInstituicao = conInstituicao;
	}

	public String getConInstituicao() {
		return conInstituicao;
	}

	public void setInstituicao(String instituicao) {
		this.instituicao = instituicao;
	}

	public String getInstituicao() {
		return instituicao;
	}

	public void setIsGpAdmGeralUsuarioLogado(String isGpAdmGeralUsuarioLogado) {
		this.isGpAdmGeralUsuarioLogado = isGpAdmGeralUsuarioLogado;
	}

	public String getIsGpAdmGeralUsuarioLogado() {
		return isGpAdmGeralUsuarioLogado;
	}

	public void setInstituicaoDesc(String instituicaoDesc) {
		this.instituicaoDesc = instituicaoDesc;
	}

	public String getInstituicaoDesc() {
		return instituicaoDesc;
	}

	public void setConNrBemImovel(String conNrBemImovel) {
		this.conNrBemImovel = conNrBemImovel;
	}

	public String getConNrBemImovel() {
		return conNrBemImovel;
	}

	public void setConCodBemImovel(String conCodBemImovel) {
		this.conCodBemImovel = conCodBemImovel;
	}

	public String getConCodBemImovel() {
		return conCodBemImovel;
	}

	public void setNrBemImovel(String nrBemImovel) {
		this.nrBemImovel = nrBemImovel;
	}

	public String getNrBemImovel() {
		return nrBemImovel;
	}

	public void setCodBemImovel(String codBemImovel) {
		this.codBemImovel = codBemImovel;
	}

	public String getCodBemImovel() {
		return codBemImovel;
	}

	public void limparCampos() {
		setCodBemImovel("");
		setNrBemImovel("");
		setAdministracao("");
		setOrgaoResponsavel("");
		setDtInicioVigencia("");
		setDtFimVigencia("");
		setProtocolo("");
		setProjetoLei("");
		setNumeroLei("");
		setDataAssinaturaLei("");
		setDataPublicacaoLei("");
		setNrDioeLei("");
		setCodLei("");
		setAdministracaoDesc("");
		setOrgaoResponsavelDesc("");
		setTipoRevogDev("");
		setMotivo("");
		setNrOficio("");
		setInstituicao("");
		setInstituicaoDesc("");
		limparCamposItem();
		limparCamposAssinatura();
	}

	public void limparCamposItem() {
		setTipoDoacao("");
		setEdificacao("");
		setUtilizacao("");
		setDoacaoMetros("");
		setDoacaoPercentual("");
		setObservacao("");
		setCodItemDoacao("");
		setItemTotal("");
		setQtdItens("");
	}
	public void limparCamposAssinatura() {
		setOrgaoAssinatura("");
		setCargoAssinatura("");
		setNomeAssinatura("");
		setOrdemAssinatura("");
		setCodAssinaturaDoacao("");
	}

}
