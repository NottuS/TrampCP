package gov.pr.celepar.abi.pojo;


public class ParametroVistoriaDenominacaoImovel implements java.io.Serializable {

	private static final long serialVersionUID = 3734691625154601973L;
	
	private ParametroVistoria parametroVistoria;
    private DenominacaoImovel denominacaoImovel;
    
	public ParametroVistoria getParametroVistoria() {
		return parametroVistoria;
	}
	public void setParametroVistoria(ParametroVistoria parametroVistoria) {
		this.parametroVistoria = parametroVistoria;
	}
	public DenominacaoImovel getDenominacaoImovel() {
		return denominacaoImovel;
	}
	public void setDenominacaoImovel(DenominacaoImovel denominacaoImovel) {
		this.denominacaoImovel = denominacaoImovel;
	}
    
	
    
}
