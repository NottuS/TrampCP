package gov.pr.celepar.abi.dto;


public class UsuarioDTO implements java.io.Serializable {

	private static final long serialVersionUID = 6869671681230445932L;

	private Integer codUsuario;
	private String usuario;
	private String grupo;
	private String orgao;
	private String situacao;
	private String instituicao;
	
	public Integer getCodUsuario() {
		return codUsuario;
	}
	public void setCodUsuario(Integer codUsuario) {
		this.codUsuario = codUsuario;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getGrupo() {
		return grupo;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	public String getOrgao() {
		return orgao;
	}
	public void setOrgao(String orgao) {
		this.orgao = orgao;
	}
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	public void setInstituicao(String instituicao) {
		this.instituicao = instituicao;
	}
	public String getInstituicao() {
		return instituicao;
	}
	

}
