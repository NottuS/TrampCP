package gov.pr.celepar.abi.enumeration;


public enum TipoAdministracao{
	ADM_DIRETA(1,"Administração Direta"),
	ADM_INDIRETA(2,"Administração Indireta"),
	ADM_TERCEIROS(3,"Terceiros");

	private final Integer codigo;		
	private final String descricao;

	private TipoAdministracao(Integer codigo, String descricao) {			
		this.codigo = codigo;
		this.descricao = descricao;
	}		

	public static TipoAdministracao getTipoAdministracao(Integer valor){	    	
	   	for (TipoAdministracao tc : TipoAdministracao.values()){
	   		if(tc.codigo.equals(valor)){
	   			return tc;
	   		}
		}
	   	return null;
	}
	
	public Integer getCodigo(){
		return codigo;
	}
	
	public static String getDescricao(Integer codigo){	    	
       	for (TipoAdministracao tc : TipoAdministracao.values()){
       		if(tc.codigo.equals(codigo)){
       			return tc.descricao;
       		}
    	}
       	return null;
    }

}
