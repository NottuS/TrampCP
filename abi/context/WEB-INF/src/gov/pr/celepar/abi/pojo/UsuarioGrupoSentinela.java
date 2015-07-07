package gov.pr.celepar.abi.pojo;

public class UsuarioGrupoSentinela implements java.io.Serializable {

	private static final long serialVersionUID = -7155893642301313363L;

	private Usuario usuario;
	private GrupoSentinela grupoSentinela;
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public void setGrupoSentinela(GrupoSentinela grupoSentinela) {
		this.grupoSentinela = grupoSentinela;
	}
	public GrupoSentinela getGrupoSentinela() {
		return grupoSentinela;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioGrupoSentinela other = (UsuarioGrupoSentinela) obj;
		if (grupoSentinela == null) {
			if (other.grupoSentinela != null)
				return false;
		} else if (!grupoSentinela.equals(other.grupoSentinela))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((grupoSentinela == null) ? 0 : grupoSentinela.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
		return result;
	}
	
}
