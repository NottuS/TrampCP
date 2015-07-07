package gov.pr.celepar.abi.dto;

import gov.pr.celepar.abi.pojo.Edificacao;
import gov.pr.celepar.abi.pojo.Instituicao;
import gov.pr.celepar.abi.pojo.Ocupacao;
import gov.pr.celepar.framework.util.Valores;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;

import org.hibernate.Hibernate;

public class RelatorioEdificacaoDTO  implements Serializable {

	private static final long serialVersionUID = 553267010282259657L;

	private String uf;
	private String municipio;
	private Integer codBemImovel;
	private Integer codEdificacao;
	
	private String tipoEdificacao;
	private String tipoConstrucao;
	private String logradouro;
    private String areaConstruida;
    private String areaUtilizada;
    private String dataAverbacao;
    private String especificacao;
    private List<Ocupacao> listaOcupacao;
//  private Integer codOcupacao;
//  private String orgaoSiglaDescricao;
//	private String situacaoOcupacao;
//	private String ocupacaoDescricao;
//	private String ocupacaoMetroQuadrado;
    private Instituicao instituicao;
    private Integer nrBemImovel;
	
	
	
	public Integer getCodEdificacao() {
		return codEdificacao;
	}
	public void setCodEdificacao(Integer codEdificacao) {
		this.codEdificacao = codEdificacao;
	}
	
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public String getMunicipio() {
		return municipio;
	}
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	public Integer getCodBemImovel() {
		return codBemImovel;
	}
	public void setCodBemImovel(Integer codBemImovel) {
		this.codBemImovel = codBemImovel;
	}
	public String getTipoEdificacao() {
		return tipoEdificacao;
	}
	public void setTipoEdificacao(String tipoEdificacao) {
		this.tipoEdificacao = tipoEdificacao;
	}
	public String getTipoConstrucao() {
		return tipoConstrucao;
	}
	public void setTipoConstrucao(String tipoConstrucao) {
		this.tipoConstrucao = tipoConstrucao;
	}
	public String getLogradouro() {
		return logradouro;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	
	public String getAreaConstruida() {
		return areaConstruida;
	}
	public void setAreaConstruida(String areaConstruida) {
		this.areaConstruida = areaConstruida;
	}
	public String getAreaUtilizada() {
		 
		return areaUtilizada;
	}
	public void setAreaUtilizada(String areaUtilizada) {
		this.areaUtilizada = areaUtilizada;
	}
	public String getDataAverbacao() {
		return dataAverbacao;
	}
	public void setDataAverbacao(String dataAverbacao) {
		this.dataAverbacao = dataAverbacao;
	}
	public String getEspecificacao() {
		return especificacao;
	}
	public void setEspecificacao(String especificacao) {
		this.especificacao = especificacao;
	}
	public List<Ocupacao> getListaOcupacao() {
		return listaOcupacao;
	}
	public void setListaOcupacao(List<Ocupacao> listaOcupacao) {
		this.listaOcupacao = listaOcupacao;
	}
	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}
	public Instituicao getInstituicao() {
		return instituicao;
	}
	
	public RelatorioEdificacaoDTO(Edificacao edificacao, String uf, String municipio, Integer codBemImovel, 
			String tipoEdificacao, String tipoConstrucao, 	Instituicao instituicao) {
		super();
		this.uf = uf;
		this.municipio = municipio;
		this.codBemImovel = codBemImovel;
		this.codEdificacao = edificacao.getCodEdificacao();
		this.tipoEdificacao = tipoEdificacao;
		this.tipoConstrucao = tipoConstrucao;
		this.logradouro = edificacao.getLogradouro();
		if (edificacao.getAreaConstruida()!=null){
			this.setAreaConstruida(Valores.formatarParaDecimal(edificacao.getAreaConstruida(), 2));
		}
		if (edificacao.getAreaUtilizada()!=null){
			this.setAreaUtilizada(Valores.formatarParaDecimal(edificacao.getAreaUtilizada(), 2));
		}
		if (edificacao.getDataAverbacao() != null){
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			this.dataAverbacao =  sdf.format(edificacao.getDataAverbacao());
		}
		this.especificacao = edificacao.getEspecificacao();
		if (edificacao.getOcupacaos() != null && edificacao.getOcupacaos().size() >0){
			this.listaOcupacao = gov.pr.celepar.abi.util.Util.setToList(edificacao.getOcupacaos()) ;
			for (Ocupacao o : listaOcupacao){
				Hibernate.initialize(o.getSituacaoOcupacao());
			}
		}
		this.instituicao = instituicao;
	}
	public RelatorioEdificacaoDTO() {
		
	}
	public void setNrBemImovel(Integer nrBemImovel) {
		this.nrBemImovel = nrBemImovel;
	}
	public Integer getNrBemImovel() {
		return nrBemImovel;
	}
	
	
	
}
