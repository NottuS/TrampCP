package gov.pr.celepar.abi.pojo;

import java.util.HashSet;
import java.util.Set;

public class GrupoSentinela implements java.io.Serializable {

	private static final long serialVersionUID = 2523487008098981779L;
	
	private Integer codGrupoSentinela;
	private String descricaoSentinela;
	private String descricaoGrupo;
	private Set<UsuarioGrupoSentinela> listaUsuarioGrupoSentinela = new HashSet<UsuarioGrupoSentinela>(0);
	
	public void setCodGrupoSentinela(Integer codGrupoSentinela) {
		this.codGrupoSentinela = codGrupoSentinela;
	}
	public Integer getCodGrupoSentinela() {
		return codGrupoSentinela;
	}
	public void setDescricaoSentinela(String descricaoSentinela) {
		this.descricaoSentinela = descricaoSentinela;
	}
	public String getDescricaoSentinela() {
		return descricaoSentinela;
	}
	public void setDescricaoGrupo(String descricaoGrupo) {
		this.descricaoGrupo = descricaoGrupo;
	}
	public String getDescricaoGrupo() {
		return descricaoGrupo;
	}
	public void setListaUsuarioGrupoSentinela(
			Set<UsuarioGrupoSentinela> listaUsuarioGrupoSentinela) {
		this.listaUsuarioGrupoSentinela = listaUsuarioGrupoSentinela;
	}
	public Set<UsuarioGrupoSentinela> getListaUsuarioGrupoSentinela() {
		return listaUsuarioGrupoSentinela;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((codGrupoSentinela == null) ? 0 : codGrupoSentinela
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
		GrupoSentinela other = (GrupoSentinela) obj;
		if (codGrupoSentinela == null) {
			if (other.codGrupoSentinela != null)
				return false;
		} else if (!codGrupoSentinela.equals(other.codGrupoSentinela))
			return false;
		return true;
	}
	
	
	
}
