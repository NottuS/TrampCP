package gov.pr.celepar.abi.dto;

import java.util.Date;


public class RelatorioNotificacaoDTO {

	private Integer codNotificacao;
	private Integer codBemImovel;
    private Date prazoNotificacao;
    private Date dataNotificacao;
    private String situacaoImovel;
    private String situacaoLegalCartorial;
    private String classificacaoImovel;
    private String documentoCartorial;
    private Integer nrBemImovel;
    private Integer codInstituicao;
    private String siglaNomeInstituicao;
    private String uf;
    private String municipio;
    
    
    public RelatorioNotificacaoDTO() {
    }


	public Integer getCodNotificacao() {
		return codNotificacao;
	}


	public void setCodNotificacao(Integer codNotificacao) {
		this.codNotificacao = codNotificacao;
	}


	public Integer getCodBemImovel() {
		return codBemImovel;
	}


	public void setCodBemImovel(Integer codBemImovel) {
		this.codBemImovel = codBemImovel;
	}


	public Date getPrazoNotificacao() {
		return prazoNotificacao;
	}


	public void setPrazoNotificacao(Date prazoNotificacao) {
		this.prazoNotificacao = prazoNotificacao;
	}


	public Date getDataNotificacao() {
		return dataNotificacao;
	}


	public void setDataNotificacao(Date dataNotificacao) {
		this.dataNotificacao = dataNotificacao;
	}


	public String getSituacaoImovel() {
		return situacaoImovel;
	}


	public void setSituacaoImovel(String situacaoImovel) {
		this.situacaoImovel = situacaoImovel;
	}


	public String getSituacaoLegalCartorial() {
		return situacaoLegalCartorial;
	}


	public void setSituacaoLegalCartorial(String situacaoLegalCartorial) {
		this.situacaoLegalCartorial = situacaoLegalCartorial;
	}


	public String getClassificacaoImovel() {
		return classificacaoImovel;
	}


	public void setClassificacaoImovel(String classificacaoImovel) {
		this.classificacaoImovel = classificacaoImovel;
	}


	public String getDocumentoCartorial() {
		return documentoCartorial;
	}


	public void setDocumentoCartorial(String documentoCartorial) {
		this.documentoCartorial = documentoCartorial;
	}


	public Integer getNrBemImovel() {
		return nrBemImovel;
	}


	public void setNrBemImovel(Integer nrBemImovel) {
		this.nrBemImovel = nrBemImovel;
	}


	


	

	public RelatorioNotificacaoDTO(Integer codNotificacao, Integer codBemImovel, Date prazoNotificacao,
			Date dataNotificacao, String situacaoImovel, String situacaoLegalCartorial, String classificacaoImovel,
			String documentoCartorial, Integer nrBemImovel, Integer codInstituicao, String siglaNomeInstituicao,
			String uf, String municipio) {
		super();
		this.codNotificacao = codNotificacao;
		this.codBemImovel = codBemImovel;
		this.prazoNotificacao = prazoNotificacao;
		this.dataNotificacao = dataNotificacao;
		this.situacaoImovel = situacaoImovel;
		this.situacaoLegalCartorial = situacaoLegalCartorial;
		this.classificacaoImovel = classificacaoImovel;
		this.documentoCartorial = documentoCartorial;
		this.nrBemImovel = nrBemImovel;
		this.codInstituicao = codInstituicao;
		this.siglaNomeInstituicao = siglaNomeInstituicao;
		this.uf = uf;
		this.municipio = municipio;
	}


	public void setSiglaNomeInstituicao(String siglaNomeInstituicao) {
		this.siglaNomeInstituicao = siglaNomeInstituicao;
	}


	public String getSiglaNomeInstituicao() {
		return siglaNomeInstituicao;
	}


	public void setCodInstituicao(Integer codInstituicao) {
		this.codInstituicao = codInstituicao;
	}


	public Integer getCodInstituicao() {
		return codInstituicao;
	}


	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}


	public String getMunicipio() {
		return municipio;
	}


	public void setUf(String uf) {
		this.uf = uf;
	}


	public String getUf() {
		return uf;
	}


	
}
