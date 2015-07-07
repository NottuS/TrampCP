package gov.pr.celepar.abi.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Transient;

public class ParametroVistoria implements java.io.Serializable {

	private static final long serialVersionUID = 973963474672049417L;
	private Integer codParametroVistoria;
    private Integer indTipoParametro;
    private Integer ordemApresentacao;
    private Boolean indAtivo;
    private String descricao;
    private Date tsInclusao;
    private Date tsAtualizacao;
    private String cpfResponsavel;
    private Instituicao instituicao;
    private Set<ParametroVistoriaDominio> listaParametroVistoriaDominio = new HashSet<ParametroVistoriaDominio>(0);
    private Set<ParametroVistoriaDenominacaoImovel> listaParametroVistoriaDenominacaoImovel = new HashSet<ParametroVistoriaDenominacaoImovel>(0);
    private Set<ItemVistoria> listaItemVistoria = new HashSet<ItemVistoria>(0);
    
    @Transient
    private Boolean parametroChecado;
    
    public ParametroVistoria() {
    }

	public ParametroVistoria(Integer codParametroVistoria, Integer indTipoParametro,
			Boolean indAtivo, String descricao,
			Date tsInclusao, Date tsAtualizacao,
			String cpfResponsavel) {
		super();
		this.codParametroVistoria = codParametroVistoria;
		this.indTipoParametro = indTipoParametro;
		this.indAtivo = indAtivo;
		this.descricao = descricao;
		this.tsInclusao = tsInclusao;
		this.tsAtualizacao = tsAtualizacao;
		this.cpfResponsavel = cpfResponsavel;
	}


	
	public Integer getCodParametroVistoria() {
		return codParametroVistoria;
	}

	public void setCodParametroVistoria(Integer codParametroVistoria) {
		this.codParametroVistoria = codParametroVistoria;
	}

	public Integer getIndTipoParametro() {
		return indTipoParametro;
	}

	public void setIndTipoParametro(Integer indTipoParametro) {
		this.indTipoParametro = indTipoParametro;
	}

	
	public Integer getOrdemApresentacao() {
		return ordemApresentacao;
	}

	public void setOrdemApresentacao(Integer ordemApresentacao) {
		this.ordemApresentacao = ordemApresentacao;
	}

	public Boolean getIndAtivo() {
		return indAtivo;
	}

	public void setIndAtivo(Boolean indAtivo) {
		this.indAtivo = indAtivo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getTsInclusao() {
		return tsInclusao;
	}

	public void setTsInclusao(Date tsInclusao) {
		this.tsInclusao = tsInclusao;
	}

	public Date getTsAtualizacao() {
		return tsAtualizacao;
	}

	public void setTsAtualizacao(Date tsAtualizacao) {
		this.tsAtualizacao = tsAtualizacao;
	}

	public String getCpfResponsavel() {
		return cpfResponsavel;
	}

	public void setCpfResponsavel(String cpfResponsavel) {
		this.cpfResponsavel = cpfResponsavel;
	}

	
	public Set<ParametroVistoriaDominio> getListaParametroVistoriaDominio() {
		return listaParametroVistoriaDominio;
	}

	public void setListaParametroVistoriaDominio(
			Set<ParametroVistoriaDominio> listaParametroVistoriaDominio) {
		this.listaParametroVistoriaDominio = listaParametroVistoriaDominio;
	}

	
	public Set<ParametroVistoriaDenominacaoImovel> getListaParametroVistoriaDenominacaoImovel() {
		return listaParametroVistoriaDenominacaoImovel;
	}

	public void setListaParametroVistoriaDenominacaoImovel(
			Set<ParametroVistoriaDenominacaoImovel> listaParametroVistoriaDenominacaoImovel) {
		this.listaParametroVistoriaDenominacaoImovel = listaParametroVistoriaDenominacaoImovel;
	}

	

	public ParametroVistoria getInstanciaAtual() {
		return this;
	}

	public void setListaItemVistoria(Set<ItemVistoria> listaItemVistoria) {
		this.listaItemVistoria = listaItemVistoria;
	}

	public Set<ItemVistoria> getListaItemVistoria() {
		return listaItemVistoria;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ParametroVistoria other = (ParametroVistoria) obj;
		if (this.codParametroVistoria == null) {
			if (other.codParametroVistoria != null)
				return false;
		} else if (!this.codParametroVistoria.equals(other.codParametroVistoria))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((this.codParametroVistoria == null) ? 0 : this.codParametroVistoria.hashCode());
		return result;
	}

	@Transient
	public void setParametroChecado(Boolean parametroChecado) {
		this.parametroChecado = parametroChecado;
	}

	@Transient
	public Boolean getParametroChecado() {
		return parametroChecado;
	}

	public Instituicao getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}
	
	
}
