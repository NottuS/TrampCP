package gov.pr.celepar.abi.form;

import org.apache.struts.validator.ValidatorForm;

public class CessaoDeUsoBemImovelForm extends ValidatorForm {

	private static final long serialVersionUID = -5698492674339197853L;
	private String actionType;
	private String pesqExec; 
	private String isGpAdmGeralUsuarioLogado; 
	
	private String conInstituicao;
	private String conNrTermo;
	private String conCodBemImovel;
	private String conNrBemImovel;
	private String conProtocolo;
	private String conSituacao;
	private String conOrgaoCessionario;
    private String uf;
    private String codMunicipio;
    private String conDtInicioVigencia;
    private String conDtFimVigencia;
    
	private String instituicao;
	private String instituicaoDesc;
    private String codAssinaturaCessaoDeUso;
    private String codItemCessaoDeUso;
	private String codCessaoDeUso;
	private String codCessaoDeUsoOrginal;
	private String nrBemImovel;
	private String codBemImovel;
	private String orgaoCedente;
	private String orgaoCessionario;
	private String codOrgaoCessionario;
	private String dtInicioVigencia;
	private String dtFimVigencia;
	private String protocolo;
	private String tipoCessaoDeUso;
	private String edificacao;
	private String utilizacao;
	private String situacaoDominial;
	private String caracteristicas;
	private String cessaoDeUsoMetros;
	private String cessaoDeUsoPercentual;
	private String observacao;
	private String projetoLei;
	private String projetoLeiDesc;
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
	private String parametroAgendaTempoCessao;
	private String codStatus;
	private String itemTotal;
	private String qtdItens;
	
	public String getItemTotal() {
		return itemTotal;
	}

	public void setItemTotal(String itemTotal) {
		this.itemTotal = itemTotal;
	}

	public String getQtdItens() {
		return qtdItens;
	}

	public void setQtdItens(String qtdItens) {
		this.qtdItens = qtdItens;
	}

	private String incluidoPor;
	private String alteradoPor;
	private String excluidoPor;
	
	private String tipoRevogDev;
	private String tipoRevogDevDesc;
	private String motivo;
	
	private String ucsDestino;
	private String ucsChamador;
	private String ucsRetorno;
	
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

	public String getConOrgaoCessionario() {
		return conOrgaoCessionario;
	}

	public void setConOrgaoCessionario(String conOrgaoCessionario) {
		this.conOrgaoCessionario = conOrgaoCessionario;
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

	public String getConDtInicioVigencia() {
		return conDtInicioVigencia;
	}

	public void setConDtInicioVigencia(String conDtInicioVigencia) {
		this.conDtInicioVigencia = conDtInicioVigencia;
	}

	public String getConDtFimVigencia() {
		return conDtFimVigencia;
	}

	public void setConDtFimVigencia(String conDtFimVigencia) {
		this.conDtFimVigencia = conDtFimVigencia;
	}

	public String getCodAssinaturaCessaoDeUso() {
		return codAssinaturaCessaoDeUso;
	}

	public void setCodAssinaturaCessaoDeUso(String codAssinaturaCessaoDeUso) {
		this.codAssinaturaCessaoDeUso = codAssinaturaCessaoDeUso;
	}

	public String getCodItemCessaoDeUso() {
		return codItemCessaoDeUso;
	}

	public void setCodItemCessaoDeUso(String codItemCessaoDeUso) {
		this.codItemCessaoDeUso = codItemCessaoDeUso;
	}

	public String getCodCessaoDeUso() {
		return codCessaoDeUso;
	}

	public void setCodCessaoDeUso(String codCessaoDeUso) {
		this.codCessaoDeUso = codCessaoDeUso;
	}

	public String getOrgaoCedente() {
		return orgaoCedente;
	}

	public void setOrgaoCedente(String orgaoCedente) {
		this.orgaoCedente = orgaoCedente;
	}

	public String getOrgaoCessionario() {
		return orgaoCessionario;
	}

	public void setOrgaoCessionario(String orgaoCessionario) {
		this.orgaoCessionario = orgaoCessionario;
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

	public String getTipoCessaoDeUso() {
		return tipoCessaoDeUso;
	}

	public void setTipoCessaoDeUso(String tipoCessaoDeUso) {
		this.tipoCessaoDeUso = tipoCessaoDeUso;
	}

	public String getEdificacao() {
		return edificacao;
	}

	public void setEdificacao(String edificacao) {
		this.edificacao = edificacao;
	}

	public String getUtilizacao() {
		return utilizacao;
	}

	public void setUtilizacao(String utilizacao) {
		this.utilizacao = utilizacao;
	}

	public String getSituacaoDominial() {
		return situacaoDominial;
	}

	public void setSituacaoDominial(String situacaoDominial) {
		this.situacaoDominial = situacaoDominial;
	}

	public String getCaracteristicas() {
		return caracteristicas;
	}

	public void setCaracteristicas(String caracteristicas) {
		this.caracteristicas = caracteristicas;
	}

	public String getCessaoDeUsoMetros() {
		return cessaoDeUsoMetros;
	}

	public void setCessaoDeUsoMetros(String cessaoDeUsoMetros) {
		this.cessaoDeUsoMetros = cessaoDeUsoMetros;
	}

	public String getCessaoDeUsoPercentual() {
		return cessaoDeUsoPercentual;
	}

	public void setCessaoDeUsoPercentual(String cessaoDeUsoPercentual) {
		this.cessaoDeUsoPercentual = cessaoDeUsoPercentual;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getProjetoLei() {
		return projetoLei;
	}

	public void setProjetoLei(String projetoLei) {
		this.projetoLei = projetoLei;
	}

	public String getProjetoLeiDesc() {
		return projetoLeiDesc;
	}

	public void setProjetoLeiDesc(String projetoLeiDesc) {
		this.projetoLeiDesc = projetoLeiDesc;
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

	public String getTipoRevogDev() {
		return tipoRevogDev;
	}

	public void setTipoRevogDev(String tipoRevogDev) {
		this.tipoRevogDev = tipoRevogDev;
	}

	public String getTipoRevogDevDesc() {
		return tipoRevogDevDesc;
	}

	public void setTipoRevogDevDesc(String tipoRevogDevDesc) {
		this.tipoRevogDevDesc = tipoRevogDevDesc;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
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

	public void setParametroAgendaTempoCessao(String parametroAgendaTempoCessao) {
		this.parametroAgendaTempoCessao = parametroAgendaTempoCessao;
	}

	public String getParametroAgendaTempoCessao() {
		return parametroAgendaTempoCessao;
	}

	public void setCodStatus(String codStatus) {
		this.codStatus = codStatus;
	}

	public String getCodStatus() {
		return codStatus;
	}

	public void setCodCessaoDeUsoOrginal(String codCessaoDeUsoOrginal) {
		this.codCessaoDeUsoOrginal = codCessaoDeUsoOrginal;
	}

	public String getCodCessaoDeUsoOrginal() {
		return codCessaoDeUsoOrginal;
	}

	public void setCodOrgaoCessionario(String codOrgaoCessionario) {
		this.codOrgaoCessionario = codOrgaoCessionario;
	}

	public String getCodOrgaoCessionario() {
		return codOrgaoCessionario;
	}

	public String getIsGpAdmGeralUsuarioLogado() {
		return isGpAdmGeralUsuarioLogado;
	}

	public void setIsGpAdmGeralUsuarioLogado(String isGpAdmGeralUsuarioLogado) {
		this.isGpAdmGeralUsuarioLogado = isGpAdmGeralUsuarioLogado;
	}

	public String getConInstituicao() {
		return conInstituicao;
	}

	public void setConInstituicao(String conInstituicao) {
		this.conInstituicao = conInstituicao;
	}

	public String getConCodBemImovel() {
		return conCodBemImovel;
	}

	public void setConCodBemImovel(String conCodBemImovel) {
		this.conCodBemImovel = conCodBemImovel;
	}

	public String getConNrBemImovel() {
		return conNrBemImovel;
	}

	public void setConNrBemImovel(String conNrBemImovel) {
		this.conNrBemImovel = conNrBemImovel;
	}

	public String getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(String instituicao) {
		this.instituicao = instituicao;
	}

	public String getInstituicaoDesc() {
		return instituicaoDesc;
	}

	public void setInstituicaoDesc(String instituicaoDesc) {
		this.instituicaoDesc = instituicaoDesc;
	}

	public String getNrBemImovel() {
		return nrBemImovel;
	}

	public void setNrBemImovel(String nrBemImovel) {
		this.nrBemImovel = nrBemImovel;
	}

	public String getCodBemImovel() {
		return codBemImovel;
	}

	public void setCodBemImovel(String codBemImovel) {
		this.codBemImovel = codBemImovel;
	}

	public void limparCampos() {
		setNrBemImovel("");
		setCodBemImovel("");
		setInstituicao("");
		setInstituicaoDesc("");
		setOrgaoCedente("");
		setOrgaoCessionario("");
		setDtInicioVigencia("");
		setDtFimVigencia("");
		setProtocolo("");
		setProjetoLei("");
		setNumeroLei("");
		setDataAssinaturaLei("");
		setDataPublicacaoLei("");
		setNrDioeLei("");
		setCodLei("");
		setTipoRevogDev("");
		setMotivo("");
		limparCamposItem();
		limparCamposAssinatura();
		setParametroAgendaTempoCessao("");
	}

	public void limparCamposItem() {
		setTipoCessaoDeUso("");
		setEdificacao("");
		setUtilizacao("");
		setCaracteristicas("");
		setSituacaoDominial("");
		setCessaoDeUsoMetros("");
		setCessaoDeUsoPercentual("");
		setObservacao("");
		setCodItemCessaoDeUso("");
		setItemTotal("");
		setQtdItens("");
	}
	public void limparCamposAssinatura() {
		setOrgaoAssinatura("");
		setCargoAssinatura("");
		setNomeAssinatura("");
		setOrdemAssinatura("");
		setCodAssinaturaCessaoDeUso("");
	}

}
