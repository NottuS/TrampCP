package gov.pr.celepar.abi.enumeration;


public enum GrupoSentinela{
	ADM_GERAL(1,"ADM - ABI"),
	LEI_ABI(2,"LEI - ABI"),
	OPE_ABI(3,"OPE - ABI"),
	OPE_ORG_ABI(4,"OPEORG - ABI"),
	OPE_CESSAO(5,"OPECESSAO - ABI"),
	OPE_DOACAO(6,"OPEDOACAO - ABI"),
	ADM_INST(7,"ADM - INSTITUICAO - ABI");

	private final Integer codigo;		
	private final String descricao;

	private GrupoSentinela(Integer codigo, String descricao) {			
		this.codigo = codigo;
		this.descricao = descricao;
	}		

	public static GrupoSentinela getGrupoSentinela(Integer valor){	    	
	   	for (GrupoSentinela tc : GrupoSentinela.values()){
	   		if(tc.codigo.equals(valor)){
	   			return tc;
	   		}
		}
	   	return null;
	}
	
	public Integer getCodigo(){
		return codigo;
	}
	
	public String getDescricao(){
		return descricao;
	}
	
	public static String getDescricao(Integer codigo){	    	
       	for (GrupoSentinela tc : GrupoSentinela.values()){
       		if(tc.codigo.equals(codigo)){
       			return tc.descricao;
       		}
    	}
       	return null;
    }

}
