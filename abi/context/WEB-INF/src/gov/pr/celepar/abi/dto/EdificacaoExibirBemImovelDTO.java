package gov.pr.celepar.abi.dto;

import gov.pr.celepar.abi.pojo.BemImovel;
import gov.pr.celepar.abi.pojo.Ocupacao;
import gov.pr.celepar.framework.util.Pagina;

import java.math.BigDecimal;
import java.util.List;

public class EdificacaoExibirBemImovelDTO {

	private BemImovel bemImovel;
	private Integer codBemImovel;
    private String lotes;	
	private String uf;
	private String municipio;
	private String logradouro;
	private Integer codEdificacao;
	private String tipoConstrucao;
	private String tipoEdificacao;
    private String especificacao;
    private BigDecimal areaConstruida;
    private BigDecimal areaUtilizada;
    private String dataAverbacao;

    private List<Ocupacao> listaOcupacao;
    private Pagina paginaOcupacao;
    
    /**/
    
	public BemImovel getBemImovel() {
		return bemImovel;
	}
	public Integer getCodEdificacao() {
		return codEdificacao;
	}
	public String getTipoConstrucao() {
		return tipoConstrucao;
	}
	public String getTipoEdificacao() {
		return tipoEdificacao;
	}
	public String getEspecificacao() {
		return especificacao;
	}
	public BigDecimal getAreaConstruida() {
		return areaConstruida;
	}
	public void setBemImovel(BemImovel bemImovel) {
		this.bemImovel = bemImovel;
	}
	public void setCodEdificacao(Integer codEdificacao) {
		this.codEdificacao = codEdificacao;
	}
	public void setTipoConstrucao(String tipoConstrucao) {
		this.tipoConstrucao = tipoConstrucao;
	}
	public void setTipoEdificacao(String tipoEdificacao) {
		this.tipoEdificacao = tipoEdificacao;
	}
	public void setEspecificacao(String especificacao) {
		this.especificacao = especificacao;
	}
	public void setAreaConstruida(BigDecimal areaConstruida) {
		this.areaConstruida = areaConstruida;
	}
	public String getLotes() {
		return lotes;
	}
	public void setLotes(String lotes) {
		this.lotes = lotes;
	}
	public Integer getCodBemImovel() {
		return codBemImovel;
	}
	public String getUf() {
		return uf;
	}
	public String getMunicipio() {
		return municipio;
	}
	public String getLogradouro() {
		return logradouro;
	}
	public String getDataAverbacao() {
		return dataAverbacao;
	}
	public List<Ocupacao> getListaOcupacao() {
		return listaOcupacao;
	}
	public void setCodBemImovel(Integer codBemImovel) {
		this.codBemImovel = codBemImovel;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	public void setDataAverbacao(String dataAverbacao) {
		this.dataAverbacao = dataAverbacao;
	}
	public void setListaOcupacao(List<Ocupacao> listaOcupacao) {
		this.listaOcupacao = listaOcupacao;
	}
	public Pagina getPaginaOcupacao() {
		return paginaOcupacao;
	}
	public void setPaginaOcupacao(Pagina paginaOcupacao) {
		this.paginaOcupacao = paginaOcupacao;
	}
	public BigDecimal getAreaUtilizada() {
		return areaUtilizada;
	}
	public void setAreaUtilizada(BigDecimal areaUtilizada) {
		this.areaUtilizada = areaUtilizada;
	}
	
	
	
}
