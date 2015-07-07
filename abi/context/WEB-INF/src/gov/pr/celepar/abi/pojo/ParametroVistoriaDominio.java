package gov.pr.celepar.abi.pojo;



public class ParametroVistoriaDominio implements java.io.Serializable {

	private static final long serialVersionUID = 973962474672049417L;
	private Integer codParametroVistoriaDominio;
	private ParametroVistoria parametroVistoria;
    private String descricao;
    
    public ParametroVistoriaDominio() {
    }

	public ParametroVistoriaDominio(Integer codParametroVistoriaDominio, ParametroVistoria parametroVistoria,
			String descricao) {
		super();
		this.codParametroVistoriaDominio = codParametroVistoriaDominio;
		this.parametroVistoria = parametroVistoria;
		this.descricao = descricao;
	}


	public Integer getCodParametroVistoriaDominio() {
		return codParametroVistoriaDominio;
	}

	public void setCodParametroVistoriaDominio(Integer codParametroVistoriaDominio) {
		this.codParametroVistoriaDominio = codParametroVistoriaDominio;
	}

	public ParametroVistoria getParametroVistoria() {
		return parametroVistoria;
	}

	public void setParametroVistoria(ParametroVistoria parametroVistoria) {
		this.parametroVistoria = parametroVistoria;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ParametroVistoriaDominio other = (ParametroVistoriaDominio) obj;
		if (codParametroVistoriaDominio == null) {
			if (other.codParametroVistoriaDominio != null)
				return false;
		} else if (!codParametroVistoriaDominio.equals(other.codParametroVistoriaDominio))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codParametroVistoriaDominio == null) ? 0 : codParametroVistoriaDominio.hashCode());
		return result;
	}
}
