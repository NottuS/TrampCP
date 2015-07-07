package gov.pr.celepar.abi.form;

import org.apache.struts.validator.ValidatorForm;

public class TransferenciaBemImovelForm extends ValidatorForm {

	private static final long serialVersionUID = -998573789504335987L;
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
    
	private String instituicao;
	private String instituicaoDesc;
    private String codAssinaturaTransferencia;
    private String codItemTransferencia;
	private String codTransferencia;
	private String nrBemImovel;
	private String codBemImovel;
	private String orgaoCedente;
	private String orgaoCessionario;
	private String tipoTransferencia;
	private String edificacao;
	private String utilizacao;
	private String caracteristicas;
	private String situacaoDominial;
	private String transferenciaMetros;
	private String transferenciaPercentual;
	private String observacao;
	private String dtInicioVigencia;
	private String dtFimVigencia;
	private String protocolo;
	private String orgaoAssinatura;
	private String cargoAssinatura;
	private String nomeAssinatura;
	private String ordemAssinatura;
	private String status;
	private String itemTotal;
	private String qtdItens;
	private Integer indOperadorOrgao;
	private String codStatus;
	private String textoDocInformacao;
	
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

	public String getConOrgaoCessionario() {
		return conOrgaoCessionario;
	}

	public void setConOrgaoCessionario(String conOrgaoCessionario) {
		this.conOrgaoCessionario = conOrgaoCessionario;
	}

	public String getCodTransferencia() {
		return codTransferencia;
	}

	public void setCodTransferencia(String codTransferencia) {
		this.codTransferencia = codTransferencia;
	}

	public String getOrgaoCedente() {
		return orgaoCedente;
	}

	public void setOrgaoCedente(String orgaoCedente) {
		this.orgaoCedente = orgaoCedente;
	}

	public String getTipoTransferencia() {
		return tipoTransferencia;
	}

	public void setTipoTransferencia(String tipoTransferencia) {
		this.tipoTransferencia = tipoTransferencia;
	}

	public String getUtilizacao() {
		return utilizacao;
	}

	public void setUtilizacao(String utilizacao) {
		this.utilizacao = utilizacao;
	}

	public String getTransferenciaMetros() {
		return transferenciaMetros;
	}

	public void setTransferenciaMetros(String transferenciaMetros) {
		this.transferenciaMetros = transferenciaMetros;
	}

	public String getTransferenciaPercentual() {
		return transferenciaPercentual;
	}

	public void setTransferenciaPercentual(String transferenciaPercentual) {
		this.transferenciaPercentual = transferenciaPercentual;
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

	public String getCodAssinaturaTransferencia() {
		return codAssinaturaTransferencia;
	}

	public void setCodAssinaturaTransferencia(String codAssinaturaTransferencia) {
		this.codAssinaturaTransferencia = codAssinaturaTransferencia;
	}

	public String getCodItemTransferencia() {
		return codItemTransferencia;
	}

	public void setCodItemTransferencia(String codItemTransferencia) {
		this.codItemTransferencia = codItemTransferencia;
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
	
	public void setOrgaoCessionario(String orgaoCessionario) {
		this.orgaoCessionario = orgaoCessionario;
	}

	public String getOrgaoCessionario() {
		return orgaoCessionario;
	}

	public void setTipoRevogDevDesc(String tipoRevogDevDesc) {
		this.tipoRevogDevDesc = tipoRevogDevDesc;
	}

	public String getTipoRevogDevDesc() {
		return tipoRevogDevDesc;
	}

	public void setCaracteristicas(String caracteristicas) {
		this.caracteristicas = caracteristicas;
	}

	public String getCaracteristicas() {
		return caracteristicas;
	}

	public void setSituacaoDominial(String situacaoDominial) {
		this.situacaoDominial = situacaoDominial;
	}

	public String getSituacaoDominial() {
		return situacaoDominial;
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

	public Integer getIndOperadorOrgao() {
		return indOperadorOrgao;
	}

	public void setIndOperadorOrgao(Integer indOperadorOrgao) {
		this.indOperadorOrgao = indOperadorOrgao;
	}

	public void setCodStatus(String codStatus) {
		this.codStatus = codStatus;
	}

	public String getCodStatus() {
		return codStatus;
	}

	public void setTextoDocInformacao(String textoDocInformacao) {
		this.textoDocInformacao = textoDocInformacao;
	}

	public String getTextoDocInformacao() {
		return textoDocInformacao;
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
		setCodBemImovel("");
		setNrBemImovel("");
		setInstituicao("");
		setInstituicaoDesc("");
		setOrgaoCedente("");
		setDtInicioVigencia("");
		setDtFimVigencia("");
		setProtocolo("");
		setOrgaoCessionario("");
		setTipoRevogDev("");
		setMotivo("");
		setNrOficio("");
		limparCamposItem();
		limparCamposAssinatura();
	}

	public void limparCamposItem() {
		setTipoTransferencia("");
		setEdificacao("");
		setUtilizacao("");
		setCaracteristicas("");
		setSituacaoDominial("");
		setUtilizacao("");
		setTransferenciaMetros("");
		setTransferenciaPercentual("");
		setObservacao("");
		setCodItemTransferencia("");
		setItemTotal("");
		setQtdItens("");
	}
	public void limparCamposAssinatura() {
		setOrgaoAssinatura("");
		setCargoAssinatura("");
		setNomeAssinatura("");
		setOrdemAssinatura("");
		setCodAssinaturaTransferencia("");
	}

}
