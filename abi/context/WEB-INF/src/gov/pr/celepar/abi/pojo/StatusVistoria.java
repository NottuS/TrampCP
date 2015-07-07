package gov.pr.celepar.abi.pojo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class StatusVistoria implements Serializable {

	private static final long serialVersionUID = -5223840580292814499L;
	private Integer codStatusVistoria;
	private String descricao;
	private Set<Vistoria> listaVistoria = new HashSet<Vistoria>(0);
	
	public StatusVistoria(Integer codStatusVistoria, String descricao,
			Set<Vistoria> listaVistoria) {
		super();
		this.codStatusVistoria = codStatusVistoria;
		this.descricao = descricao;
		this.listaVistoria = listaVistoria;
	}

	public StatusVistoria() {
		super();
	}

	public Integer getCodStatusVistoria() {
		return codStatusVistoria;
	}

	public void setCodStatusVistoria(Integer codStatusVistoria) {
		this.codStatusVistoria = codStatusVistoria;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setListaVistoria(Set<Vistoria> listaVistoria) {
		this.listaVistoria = listaVistoria;
	}

	public Set<Vistoria> getListaVistoria() {
		return listaVistoria;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
		* result
		+ ((codStatusVistoria == null) ? 0 : codStatusVistoria
				.hashCode());
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
		StatusVistoria other = (StatusVistoria) obj;
		if (codStatusVistoria == null) {
			if (other.codStatusVistoria != null)
				return false;
		} else if (!codStatusVistoria.equals(other.codStatusVistoria))
			return false;
		return true;
	}
}
