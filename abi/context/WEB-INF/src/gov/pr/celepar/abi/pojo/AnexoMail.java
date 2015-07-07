package gov.pr.celepar.abi.pojo;

public class AnexoMail implements java.io.Serializable {

	private static final long serialVersionUID = -34238311918403519L;

	private String nomeArquivo;
	private String conteudoArquivo;
	
	public AnexoMail(String nomeArquivo, String conteudoArquivo) {
		super();
		this.conteudoArquivo = conteudoArquivo;
		this.nomeArquivo = nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}
	public String getNomeArquivo() {
		return nomeArquivo;
	}
	public void setConteudoArquivo(String conteudoArquivo) {
		this.conteudoArquivo = conteudoArquivo;
	}
	public String getConteudoArquivo() {
		return conteudoArquivo;
	}
	
}
