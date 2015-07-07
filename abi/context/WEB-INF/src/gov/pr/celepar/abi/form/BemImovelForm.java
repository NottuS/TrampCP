package gov.pr.celepar.abi.form;

import gov.pr.celepar.abi.util.Dominios.administracaoImovel;
import gov.pr.celepar.abi.util.Util;

import org.apache.struts.validator.ValidatorForm;



/**
 * @author pialarissi
 * @version 1.0
 * @since 19/01/2010
 * 
 * Responsável por encapsular os dados das telas de Cadastro de Bens de Imóveis.
 */
public class BemImovelForm extends ValidatorForm {
	
	
	private static final long serialVersionUID = 793452875062817985L;
	private String actionType;
	private String actionTypeOcupResp;
	private String isGpAdmGeralUsuarioLogado;
	
	private String codBemImovel;
	private String nrBemImovel;
	private String instituicao;
	private String instituicaoDesc;
	private String classificacaoBemImovel;
	private String situacaoImovel;
	private String denominacaoImovel;
	private String situacaoLegalCartorial;
	private String orgao;
	private String formaIncorporacao;
	private String administracao;
	private String situacaoLocal = "1";
	private String numeroProcessoSpi;
	private String cep;
    private String uf;
    private String codMunicipio;
    private String logradouro;
    private String numero;
    private String bairroDistrito;
    private String complemento;
	private String dataIncorporacao;
	private String areaTerreno;
	private String areaTerrenoIni;
	private String areaTerrenoFim;
	private String areaConstruida;
	private String areaDispoNivel;
    private String observacoesMigracao;
    private String tipoConstrucao;
    private String tipoEdificacao;
    private String tipoDocumentacao;
    private String lote;
    private String quadra;
    private String situacaoOcupacao;    
    private String municipio;
    private String areaOriginal;
    private String medidaOriginal;
    private String averbado; 
    private String somenteTerreno;
    private String orgaoResp;
    
    //campos da tela pesquisa
    protected String conInstituicao;
    protected String conCodBemImovel;
	protected String conNrBemImovel;
    protected String conOrgao;
    protected String conOrgaoResp;
    protected String conClassificacaoBemImovel;
    protected String conNirf;
    protected String conNiif;
	protected String conIncra;
    protected String conMunicipio;
    protected String conLogradouro;
    protected String conBairroDistrito;
    protected String conSituacaoLegalCartorial;
    protected String conCartorio;
    protected String conNumeroDocumentoCartorial;
    protected String conTabelionato;
    protected String conNumeroDocumentoTabelional;
    protected String conFormaIncorporacao;
    protected String conAreaTerrenoIni;
    protected String conAreaTerrenoFim;
    protected String conTipoConstrucao;
    protected String conTipoEdificacao;
    protected String conTipoDocumentacao;
    protected String conSituacaoOcupacao;
    protected String conLote;
    protected String conQuadra;
    protected String conDenominacaoImovel;
    protected String conAverbado;
    protected String conSituacaoImovel;
    protected String conAdministracao;
    protected String conOcupante;
	protected String conCodUf;
    protected String conCodMunicipio;
    protected String conOcupacao;

    // Seção de órgão responsável
    private String qtdRegEdificacao;
    private String qtdRegResponsavelAtivo;
    private String qtdRegResponsavelInativo;
    private String or_codOcupacao;
	private String or_codOrgao;
	private String or_administracao;
	private String or_codBemImovel;
	private String or_descricao;
	private String or_ocupacaoMetroQuadrado;
	private String or_ocupacaoPercentual;
	private String or_dataOcupacao;
	private String or_termoTransferencia;
	private String or_dataLei;
	private String or_vigenciaLei;
	private String or_numeroLei;
	private String or_numeroNotificacao;
	private String or_prazoNotificacao;
    private String or_protocoloNotificacaoSpi;
    private String or_especificacao;
    private String or_situacaoOcupacao;
    private String permReativar;
    private Boolean indOperadorOrgao;

    public String getConOcupante() {
		return conOcupante;
	}
	public void setConOcupante(String conOcupante) {
		this.conOcupante = conOcupante;
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
	public String getConCodBemImovel() {
		return conCodBemImovel;
	}
	public void setConCodBemImovel(String conCodBemImovel) {
		this.conCodBemImovel = conCodBemImovel;
	}
	public String getConClassificacaoBemImovel() {
		return conClassificacaoBemImovel;
	}
	public void setConClassificacaoBemImovel(String conClassificacaoBemImovel) {
		this.conClassificacaoBemImovel = conClassificacaoBemImovel;
	}
	public String getConCartorio() {
		return conCartorio;
	}
	public void setConCartorio(String conCartorio) {
		this.conCartorio = conCartorio;
	}
	public String getConSituacaoImovel() {
		return conSituacaoImovel;
	}
	public void setConSituacaoImovel(String conSituacaoImovel) {
		this.conSituacaoImovel = conSituacaoImovel;
	}
	public String getConDenominacaoImovel() {
		return conDenominacaoImovel;
	}
	public void setConDenominacaoImovel(String conDenominacaoImovel) {
		this.conDenominacaoImovel = conDenominacaoImovel;
	}
	public String getConTabelionato() {
		return conTabelionato;
	}
	public void setConTabelionato(String conTabelionato) {
		this.conTabelionato = conTabelionato;
	}
	public String getConSituacaoLegalCartorial() {
		return conSituacaoLegalCartorial;
	}
	public void setConSituacaoLegalCartorial(String conSituacaoLegalCartorial) {
		this.conSituacaoLegalCartorial = conSituacaoLegalCartorial;
	}
	public String getConOrgao() {
		return conOrgao;
	}
	public void setConOrgao(String conOrgao) {
		this.conOrgao = conOrgao;
	}
	public String getConFormaIncorporacao() {
		return conFormaIncorporacao;
	}
	public void setConFormaIncorporacao(String conFormaIncorporacao) {
		this.conFormaIncorporacao = conFormaIncorporacao;
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
	public String getConNumeroDocumentoCartorial() {
		return conNumeroDocumentoCartorial;
	}
	public void setConNumeroDocumentoCartorial(String conNumeroDocumentoCartorial) {
		this.conNumeroDocumentoCartorial = conNumeroDocumentoCartorial;
	}
	public String getConNumeroDocumentoTabelional() {
		return conNumeroDocumentoTabelional;
	}
	public void setConNumeroDocumentoTabelional(String conNumeroDocumentoTabelional) {
		this.conNumeroDocumentoTabelional = conNumeroDocumentoTabelional;
	}
	public String getConAreaTerrenoIni() {
		return conAreaTerrenoIni;
	}
	public void setConAreaTerrenoIni(String conAreaTerrenoIni) {
		this.conAreaTerrenoIni = conAreaTerrenoIni;
	}
	public String getConAreaTerrenoFim() {
		return conAreaTerrenoFim;
	}
	public void setConAreaTerrenoFim(String conAreaTerrenoFim) {
		this.conAreaTerrenoFim = conAreaTerrenoFim;
	}
	public String getConTipoConstrucao() {
		return conTipoConstrucao;
	}
	public void setConTipoConstrucao(String conTipoConstrucao) {
		this.conTipoConstrucao = conTipoConstrucao;
	}
	public String getConTipoEdificacao() {
		return conTipoEdificacao;
	}
	public void setConTipoEdificacao(String conTipoEdificacao) {
		this.conTipoEdificacao = conTipoEdificacao;
	}
	public String getConTipoDocumentacao() {
		return conTipoDocumentacao;
	}
	public void setConTipoDocumentacao(String conTipoDocumentacao) {
		this.conTipoDocumentacao = conTipoDocumentacao;
	}
	public String getConLote() {
		return conLote;
	}
	public void setConLote(String conLote) {
		this.conLote = conLote;
	}
	public String getConQuadra() {
		return conQuadra;
	}
	public void setConQuadra(String conQuadra) {
		this.conQuadra = conQuadra;
	}
	public String getConSituacaoOcupacao() {
		return conSituacaoOcupacao;
	}
	public void setConSituacaoOcupacao(String conSituacaoOcupacao) {
		this.conSituacaoOcupacao = conSituacaoOcupacao;
	}
	public String getConAverbado() {
		return conAverbado;
	}
	public void setConAverbado(String conAverbado) {
		this.conAverbado = conAverbado;
	}
	public String getConNirf() {
		return conNirf;
	}
	public void setConNirf(String conNirf) {
		this.conNirf = conNirf;
	}
	public String getConNiif() {
		return conNiif;
	}
	public void setConNiif(String conNiif) {
		this.conNiif = conNiif;
	}
	public String getConIncra() {
		return conIncra;
	}
	public void setConIncra(String conIncra) {
		this.conIncra = conIncra;
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
	public String getCodBemImovel() {
		return codBemImovel;
	}
	public void setCodBemImovel(String codBemImovel) {
		this.codBemImovel = codBemImovel;
	}
	public String getClassificacaoBemImovel() {
		return classificacaoBemImovel;
	}
	public void setClassificacaoBemImovel(String classificacaoBemImovel) {
		this.classificacaoBemImovel = classificacaoBemImovel;
	}
	
	public String getSituacaoImovel() {
		return situacaoImovel;
	}
	public void setSituacaoImovel(String situacaoImovel) {
		this.situacaoImovel = situacaoImovel;
	}
	public String getDenominacaoImovel() {
		return denominacaoImovel;
	}
	public void setDenominacaoImovel(String denominacaoImovel) {
		this.denominacaoImovel = denominacaoImovel;
	}

	public String getSituacaoLegalCartorial() {
		return situacaoLegalCartorial;
	}
	public void setSituacaoLegalCartorial(String situacaoLegalCartorial) {
		this.situacaoLegalCartorial = situacaoLegalCartorial;
	}
	public String getOrgao() {
		return orgao;
	}
	public void setOrgao(String orgao) {
		this.orgao = orgao;
	}
	public String getFormaIncorporacao() {
		return formaIncorporacao;
	}
	public void setFormaIncorporacao(String formaIncorporacao) {
		this.formaIncorporacao = formaIncorporacao;
	}
	public String getSituacaoLocal() {
		return situacaoLocal;
	}
	public String getAdministracao() {
		return administracao;
	}
	public String getDescricaoAdministracao(){
		if (!Util.strEmBranco( this.administracao)){
			return administracaoImovel.getAdministracaoImovelByIndex(Integer.valueOf(this.administracao)).getLabel();
		}
		else{
			return "";
		}
	}
	public void setAdministracao(String administracao) {
		this.administracao = administracao;
	}
	public void setSituacaoLocal(String situacaoLocal) {
		this.situacaoLocal = situacaoLocal;
	}
	public String getNumeroProcessoSpi() {
		return numeroProcessoSpi;
	}
	public void setNumeroProcessoSpi(String numeroProcessoSpi) {
		this.numeroProcessoSpi = numeroProcessoSpi;
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
	
	public String getDataIncorporacao() {
		return dataIncorporacao;
	}
	public void setDataIncorporacao(String dataIncorporacao) {
		this.dataIncorporacao = dataIncorporacao;
	}
	public String getAreaTerreno() {
		return areaTerreno;
	}
	public void setAreaTerreno(String areaTerreno) {
		this.areaTerreno = areaTerreno;
	}
	public String getAreaConstruida() {
		return areaConstruida;
	}
	public void setAreaConstruida(String areaConstruida) {
		this.areaConstruida = areaConstruida;
	}
	public String getAreaDispoNivel() {
		return areaDispoNivel;
	}
	public void setAreaDispoNivel(String areaDispoNivel) {
		this.areaDispoNivel = areaDispoNivel;
	}
	public String getObservacoesMigracao() {
		return observacoesMigracao;
	}
	public void setObservacoesMigracao(String observacoesMigracao) {
		this.observacoesMigracao = observacoesMigracao;
	}
	public String getTipoConstrucao() {
		return tipoConstrucao;
	}
	public void setTipoConstrucao(String tipoConstrucao) {
		this.tipoConstrucao = tipoConstrucao;
	}
	public String getTipoEdificacao() {
		return tipoEdificacao;
	}
	public void setTipoEdificacao(String tipoEdificacao) {
		this.tipoEdificacao = tipoEdificacao;
	}
	public String getTipoDocumentacao() {
		return tipoDocumentacao;
	}
	public void setTipoDocumentacao(String tipoDocumentacao) {
		this.tipoDocumentacao = tipoDocumentacao;
	}
	public String getSituacaoOcupacao() {
		return situacaoOcupacao;
	}
	public void setSituacaoOcupacao(String situacaoOcupacao) {
		this.situacaoOcupacao = situacaoOcupacao;
	}
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	public String getMunicipio() {
		return municipio;
	}
	public void setAreaOriginal(String areaOriginal) {
		this.areaOriginal = areaOriginal;
	}
	public String getAreaOriginal() {
		return areaOriginal;
	}
	public void setMedidaOriginal(String medidaOriginal) {
		this.medidaOriginal = medidaOriginal;
	}
	public String getMedidaOriginal() {
		return medidaOriginal;
	}
	public void setAreaTerrenoIni(String areaTerrenoIni) {
		this.areaTerrenoIni = areaTerrenoIni;
	}
	public String getAreaTerrenoIni() {
		return areaTerrenoIni;
	}
	public void setAreaTerrenoFim(String areaTerrenoFim) {
		this.areaTerrenoFim = areaTerrenoFim;
	}
	public String getAreaTerrenoFim() {
		return areaTerrenoFim;
	}
	public void setLote(String lote) {
		this.lote = lote;
	}
	public String getLote() {
		return lote;
	}
	public void setAverbado(String averbado) {
		this.averbado = averbado;
	}
	public String getAverbado() {
		return averbado;
	}
	public void setQuadra(String quadra) {
		this.quadra = quadra;
	}
	public String getQuadra() {
		return quadra;
	}

	public void setConMunicipio(String conMunicipio) {
		this.conMunicipio = conMunicipio;
	}
	public String getConMunicipio() {
		return conMunicipio;
	}
	public void setConAdministracao(String conAdministracao) {
		this.conAdministracao = conAdministracao;
	}
	public String getConAdministracao() {
		return conAdministracao;
	}
    public String getConOrgaoResp() {
		return conOrgaoResp;
	}
	public void setConOrgaoResp(String conOrgaoResp) {
		this.conOrgaoResp = conOrgaoResp;
	}
	public void setSomenteTerreno(String somenteTerreno) {
		this.somenteTerreno = somenteTerreno;
	}
	public String getSomenteTerreno() {
		return somenteTerreno;
	}
	public void setOrgaoResp(String orgaoResp) {
		this.orgaoResp = orgaoResp;
	}
	public String getOrgaoResp() {
		return orgaoResp;
	}
	
    // Seção de órgão responsável
	public String getOr_codOcupacao() {
		return or_codOcupacao;
	}
	public void setOr_codOcupacao(String orCodOcupacao) {
		or_codOcupacao = orCodOcupacao;
	}
	public String getOr_codOrgao() {
		return or_codOrgao;
	}
	public void setOr_codOrgao(String orCodOrgao) {
		or_codOrgao = orCodOrgao;
	}
	public String getOr_administracao() {
		return or_administracao;
	}
	public void setOr_administracao(String orAdministracao) {
		or_administracao = orAdministracao;
	}
	public String getOr_codBemImovel() {
		return or_codBemImovel;
	}
	public void setOr_codBemImovel(String orCodBemImovel) {
		or_codBemImovel = orCodBemImovel;
	}
	public String getOr_descricao() {
		return or_descricao;
	}
	public void setOr_descricao(String orDescricao) {
		or_descricao = orDescricao;
	}
	public String getOr_ocupacaoMetroQuadrado() {
		return or_ocupacaoMetroQuadrado;
	}
	public void setOr_ocupacaoMetroQuadrado(String orOcupacaoMetroQuadrado) {
		or_ocupacaoMetroQuadrado = orOcupacaoMetroQuadrado;
	}
	public String getOr_ocupacaoPercentual() {
		return or_ocupacaoPercentual;
	}
	public void setOr_ocupacaoPercentual(String orOcupacaoPercentual) {
		or_ocupacaoPercentual = orOcupacaoPercentual;
	}
	public String getOr_dataOcupacao() {
		return or_dataOcupacao;
	}
	public void setOr_dataOcupacao(String orDataOcupacao) {
		or_dataOcupacao = orDataOcupacao;
	}
	public String getOr_termoTransferencia() {
		return or_termoTransferencia;
	}
	public void setOr_termoTransferencia(String orTermoTransferencia) {
		or_termoTransferencia = orTermoTransferencia;
	}
	public String getOr_dataLei() {
		return or_dataLei;
	}
	public void setOr_dataLei(String orDataLei) {
		or_dataLei = orDataLei;
	}
	public String getOr_vigenciaLei() {
		return or_vigenciaLei;
	}
	public void setOr_vigenciaLei(String orVigenciaLei) {
		or_vigenciaLei = orVigenciaLei;
	}
	public String getOr_numeroLei() {
		return or_numeroLei;
	}
	public void setOr_numeroLei(String orNumeroLei) {
		or_numeroLei = orNumeroLei;
	}
	public String getOr_numeroNotificacao() {
		return or_numeroNotificacao;
	}
	public void setOr_numeroNotificacao(String orNumeroNotificacao) {
		or_numeroNotificacao = orNumeroNotificacao;
	}
	public String getOr_prazoNotificacao() {
		return or_prazoNotificacao;
	}
	public void setOr_prazoNotificacao(String orPrazoNotificacao) {
		or_prazoNotificacao = orPrazoNotificacao;
	}
	public String getOr_protocoloNotificacaoSpi() {
		return or_protocoloNotificacaoSpi;
	}
	public void setOr_protocoloNotificacaoSpi(String orProtocoloNotificacaoSpi) {
		or_protocoloNotificacaoSpi = orProtocoloNotificacaoSpi;
	}
	public String getOr_especificacao() {
		return or_especificacao;
	}
	public void setOr_especificacao(String orEspecificacao) {
		or_especificacao = orEspecificacao;
	}
	public String getOr_situacaoOcupacao() {
		return or_situacaoOcupacao;
	}
	public void setOr_situacaoOcupacao(String orSituacaoOcupacao) {
		or_situacaoOcupacao = orSituacaoOcupacao;
	}
	public void setActionTypeOcupResp(String actionTypeOcupResp) {
		this.actionTypeOcupResp = actionTypeOcupResp;
	}
	public String getActionTypeOcupResp() {
		return actionTypeOcupResp;
	}
	public void setQtdRegEdificacao(String qtdRegEdificacao) {
		this.qtdRegEdificacao = qtdRegEdificacao;
	}
	public String getQtdRegEdificacao() {
		return qtdRegEdificacao;
	}
	public void setQtdRegResponsavelAtivo(String qtdRegResponsavelAtivo) {
		this.qtdRegResponsavelAtivo = qtdRegResponsavelAtivo;
	}
	public String getQtdRegResponsavelAtivo() {
		return qtdRegResponsavelAtivo;
	}
	public void setPermReativar(String permReativar) {
		this.permReativar = permReativar;
	}
	public String getPermReativar() {
		return permReativar;
	}
	public void setQtdRegResponsavelInativo(String qtdRegResponsavelInativo) {
		this.qtdRegResponsavelInativo = qtdRegResponsavelInativo;
	}
	public String getQtdRegResponsavelInativo() {
		return qtdRegResponsavelInativo;
	}
	public String getConOcupacao() {
		return conOcupacao;
	}
	public void setConOcupacao(String conOcupacao) {
		this.conOcupacao = conOcupacao;
	}
	public Boolean getIndOperadorOrgao() {
		return indOperadorOrgao;
	}
	public void setIndOperadorOrgao(Boolean indOperadorOrgao) {
		this.indOperadorOrgao = indOperadorOrgao;
	}
	public void setNrBemImovel(String nrBemImovel) {
		this.nrBemImovel = nrBemImovel;
	}
	public String getNrBemImovel() {
		return nrBemImovel;
	}
    public String getConNrBemImovel() {
		return conNrBemImovel;
	}
	public void setConNrBemImovel(String conNrBemImovel) {
		this.conNrBemImovel = conNrBemImovel;
	}
	public String getConInstituicao() {
		return conInstituicao;
	}
	public void setConInstituicao(String conInstituicao) {
		this.conInstituicao = conInstituicao;
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

}
