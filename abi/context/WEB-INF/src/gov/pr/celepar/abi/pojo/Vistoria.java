package gov.pr.celepar.abi.pojo;

import gov.pr.celepar.framework.util.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Transient;

public class Vistoria implements Serializable {

	private static final long serialVersionUID = 8534000048052360998L;
	private Integer codVistoria;
	private BemImovel bemImovel;
	private Edificacao edificacao;
	private StatusVistoria statusVistoria;
	private Vistoriador vistoriador;
	private Date dataVistoria;
	private Integer idadeAparente;
	private String observacao;
	private Date tsInclusao;
    private Date tsAtualizacao;
    private String cpfResponsavel;
    private Set<ItemVistoria> listaItemVistoria = new HashSet<ItemVistoria>(0);
    @Transient
    private Boolean permissaoExclusaoVistoria;
    
	public Vistoria() {
		super();
	}
	
	public Vistoria(Integer codVistoria, BemImovel bemImovel,
			Edificacao edificacao, StatusVistoria statusVistoria,
			Vistoriador vistoriador, Date dataVistoria, Integer idadeAparente,
			String observacao, Date tsInclusao, Date tsAtualizacao,
			String cpfResponsavel, Set<ItemVistoria> listaItemVistoria) {
		super();
		this.codVistoria = codVistoria;
		this.bemImovel = bemImovel;
		this.edificacao = edificacao;
		this.statusVistoria = statusVistoria;
		this.vistoriador = vistoriador;
		this.dataVistoria = dataVistoria;
		this.idadeAparente = idadeAparente;
		this.observacao = observacao;
		this.tsInclusao = tsInclusao;
		this.tsAtualizacao = tsAtualizacao;
		this.cpfResponsavel = cpfResponsavel;
		this.listaItemVistoria = listaItemVistoria;
	}

	public Integer getCodVistoria() {
		return codVistoria;
	}
	public void setCodVistoria(Integer codVistoria) {
		this.codVistoria = codVistoria;
	}
	public StatusVistoria getStatusVistoria() {
		return statusVistoria;
	}
	public void setStatusVistoria(StatusVistoria statusVistoria) {
		this.statusVistoria = statusVistoria;
	}
	public BemImovel getBemImovel() {
		return bemImovel;
	}
	public void setBemImovel(BemImovel bemImovel) {
		this.bemImovel = bemImovel;
	}
	public Edificacao getEdificacao() {
		return edificacao;
	}
	public void setEdificacao(Edificacao edificacao) {
		this.edificacao = edificacao;
	}
	public Date getDataVistoria() {
		return dataVistoria;
	}
	public void setDataVistoria(Date dataVistoria) {
		this.dataVistoria = dataVistoria;
	}
	public Integer getIdadeAparente() {
		return idadeAparente;
	}
	public void setIdadeAparente(Integer idadeAparente) {
		this.idadeAparente = idadeAparente;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
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
	public void setListaItemVistoria(Set<ItemVistoria> listaItemVistoria) {
		this.listaItemVistoria = listaItemVistoria;
	}
	public Set<ItemVistoria> getListaItemVistoria() {
		return listaItemVistoria;
	}
	public void setVistoriador(Vistoriador vistoriador) {
		this.vistoriador = vistoriador;
	}
	public Vistoriador getVistoriador() {
		return vistoriador;
	}
	
	@Transient
	public Vistoria getInstanciaAtual() {
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
		+ ((codVistoria == null) ? 0 : codVistoria.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vistoria other = (Vistoria) obj;
		if (codVistoria == null) {
			if (other.codVistoria != null)
				return false;
		} else if (!codVistoria.equals(other.codVistoria))
			return false;
		return true;
	}

	@Transient
	public void setPermissaoExclusaoVistoria(Boolean permissaoExclusaoVistoria) {
		this.permissaoExclusaoVistoria = permissaoExclusaoVistoria;
	}
	@Transient
	public Boolean getPermissaoExclusaoVistoria() {
		return permissaoExclusaoVistoria;
	}
	@Transient
	public String getDataVistoriaFormatada() {
		return Data.formataData(this.dataVistoria, "dd/MM/yyyy");
	}

}
