package gov.pr.celepar.abi.dto;


public class UsuarioOrgaoDTO implements java.io.Serializable {

	private static final long serialVersionUID = 6953580190543892776L;

	private Integer codUsuario;
	private Integer codOrgao;
	private String descricao;
	
	public Integer getCodUsuario() {
		return codUsuario;
	}
	public void setCodUsuario(Integer codUsuario) {
		this.codUsuario = codUsuario;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setCodOrgao(Integer codOrgao) {
		this.codOrgao = codOrgao;
	}
	public Integer getCodOrgao() {
		return codOrgao;
	}

}
