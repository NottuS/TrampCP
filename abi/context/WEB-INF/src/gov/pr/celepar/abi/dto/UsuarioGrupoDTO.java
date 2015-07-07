package gov.pr.celepar.abi.dto;


public class UsuarioGrupoDTO implements java.io.Serializable {

	private static final long serialVersionUID = -5263442586029257287L;

	private Integer codUsuario;
	private Integer codGrupo;
	private String descricao;
	
	public Integer getCodUsuario() {
		return codUsuario;
	}
	public void setCodUsuario(Integer codUsuario) {
		this.codUsuario = codUsuario;
	}
	public void setCodGrupo(Integer codGrupo) {
		this.codGrupo = codGrupo;
	}
	public Integer getCodGrupo() {
		return codGrupo;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getDescricao() {
		return descricao;
	}

}
