package gov.pr.celepar.abi.dto;

/**
 * Classe criada para formatar a exibicao da lista de Assinaturas no relatorio do UCS_GerarTermoDoacaoBemImovel e UCS_GerarTermoCessaoDeUso.<BR>
 * @author ginaalmeida
 * @since 29/07/2011
 */
public class AssinaturaDTO {

    private String nome;
    private String cargo;
    
    //usando no UCS_GeratTermoCessaoDeUso
    private String orgao;
    
    //usando no UCS_GeratTermoTransferencia
    private String respMaximo;
    private Integer codigo;
    
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCargo() {
		return cargo;
	}
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	public String getOrgao() {
		return orgao;
	}
	public void setOrgao(String orgao) {
		this.orgao = orgao;
	}
	public void setRespMaximo(String respMaximo) {
		this.respMaximo = respMaximo;
	}
	public String getRespMaximo() {
		return respMaximo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	public Integer getCodigo() {
		return codigo;
	}
    
}