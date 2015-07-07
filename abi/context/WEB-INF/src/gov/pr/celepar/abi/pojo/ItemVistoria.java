package gov.pr.celepar.abi.pojo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Transient;

public class ItemVistoria implements Serializable {

	private static final long serialVersionUID = 7591405264031671909L;
	private Integer codItemVistoria;
	private Vistoria vistoria;
	private ParametroVistoria parametroVistoria;
	private String descricao;
	private Integer indTipoParametro;
	private String textoDominio;
	private Set<ItemVistoriaDominio> listaItemVistoriaDominio = new HashSet<ItemVistoriaDominio>(0);
	
	public ItemVistoria() {
		super();
	}

	public ItemVistoria(Integer codItemVistoria, Vistoria vistoria,
			ParametroVistoria parametroVistoria, String descricao,
			Integer indTipoParametro, String textoDominio,
			Set<ItemVistoriaDominio> listaItemVistoriaDominio) {
		super();
		this.codItemVistoria = codItemVistoria;
		this.vistoria = vistoria;
		this.parametroVistoria = parametroVistoria;
		this.descricao = descricao;
		this.indTipoParametro = indTipoParametro;
		this.textoDominio = textoDominio;
		this.listaItemVistoriaDominio = listaItemVistoriaDominio;
	}

	public Integer getCodItemVistoria() {
		return codItemVistoria;
	}

	public void setCodItemVistoria(Integer codItemVistoria) {
		this.codItemVistoria = codItemVistoria;
	}

	public Vistoria getVistoria() {
		return vistoria;
	}

	public void setVistoria(Vistoria vistoria) {
		this.vistoria = vistoria;
	}

	public ParametroVistoria getParametroVistoria() {
		return parametroVistoria;
	}

	public void setParametroVistoria(ParametroVistoria parametroVistoria) {
		this.parametroVistoria = parametroVistoria;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getIndTipoParametro() {
		return indTipoParametro;
	}

	public void setIndTipoParametro(Integer indTipoParametro) {
		this.indTipoParametro = indTipoParametro;
	}

	public String getTextoDominio() {
		return textoDominio;
	}

	public void setTextoDominio(String textoDominio) {
		this.textoDominio = textoDominio;
	}

	public void setListaItemVistoriaDominio(Set<ItemVistoriaDominio> listaItemVistoriaDominio) {
		this.listaItemVistoriaDominio = listaItemVistoriaDominio;
	}

	public Set<ItemVistoriaDominio> getListaItemVistoriaDominio() {
		return listaItemVistoriaDominio;
	}
	
	@Transient
	public ItemVistoria getInstanciaAtual() {
		return this;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codItemVistoria == null) ? 0 : codItemVistoria.hashCode());
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
		ItemVistoria other = (ItemVistoria) obj;
		if (codItemVistoria == null) {
			if (other.codItemVistoria != null)
				return false;
		} else if (!codItemVistoria.equals(other.codItemVistoria))
			return false;
		return true;
	}

}
