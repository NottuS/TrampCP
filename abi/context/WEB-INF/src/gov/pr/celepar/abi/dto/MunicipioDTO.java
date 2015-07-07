package gov.pr.celepar.abi.dto;

import java.io.Serializable;


/**
 * @author pialarissi
 */
public class MunicipioDTO implements Serializable{
	
	private static final long serialVersionUID = 672226236521362408L;		
	
	private String codigo;
	private String nome;
	
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
