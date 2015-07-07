package gov.pr.celepar.abi.pojo;

import java.io.Serializable;

public class ItemVistoriaDominio implements Serializable {

	private static final long serialVersionUID = 2343810244535438871L;
	private Integer codItemVistoriaDominio;
	private ItemVistoria itemVistoria;
	private String descricao;
	private Boolean indSelecionado;
	
	public ItemVistoriaDominio() {
		super();
	}

	public ItemVistoriaDominio(Integer codItemVistoriaDominio,
			ItemVistoria itemVistoria, String descricao, Boolean indSelecionado) {
		super();
		this.codItemVistoriaDominio = codItemVistoriaDominio;
		this.itemVistoria = itemVistoria;
		this.descricao = descricao;
		this.indSelecionado = indSelecionado;
	}

	public Integer getCodItemVistoriaDominio() {
		return codItemVistoriaDominio;
	}

	public void setCodItemVistoriaDominio(Integer codItemVistoriaDominio) {
		this.codItemVistoriaDominio = codItemVistoriaDominio;
	}

	public ItemVistoria getItemVistoria() {
		return itemVistoria;
	}

	public void setItemVistoria(ItemVistoria itemVistoria) {
		this.itemVistoria = itemVistoria;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Boolean getIndSelecionado() {
		return indSelecionado;
	}

	public void setIndSelecionado(Boolean indSelecionado) {
		this.indSelecionado = indSelecionado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((codItemVistoriaDominio == null) ? 0
						: codItemVistoriaDominio.hashCode());
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
		ItemVistoriaDominio other = (ItemVistoriaDominio) obj;
		if (codItemVistoriaDominio == null) {
			if (other.codItemVistoriaDominio != null)
				return false;
		} else if (!codItemVistoriaDominio.equals(other.codItemVistoriaDominio))
			return false;
		return true;
	}
	
	
	
}
