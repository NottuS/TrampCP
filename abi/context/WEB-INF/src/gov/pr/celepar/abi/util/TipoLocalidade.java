package gov.pr.celepar.abi.util;

public enum TipoLocalidade{
	
	MUNICIPIO("M", "Município"),
	POVOADO("P", "Povoado"),
	DISTRITO("D", "Distrito"),
	REGIAO("R", "Região");
	

	private final String codigo;		
	private final String descricao;
	
	
	
	private TipoLocalidade(String codigo, String descricao) {			
		this.codigo = codigo;
		this.descricao = descricao;
	}		

	public static TipoLocalidade getTipoLocalidade (String valor){	    	
	   	for (TipoLocalidade tipoLocalidade  : TipoLocalidade.values()){
	   		if(tipoLocalidade .codigo.equals(valor)){
	   			return tipoLocalidade ;
	   		}
		}
	   	return null;
	}
	
	public String getCodigo(){
		return codigo;
	}
	
	public static String getDescricao(String codigo){	    	
       	for (TipoLocalidade tipoLocalidade  : TipoLocalidade.values()){
       		if(tipoLocalidade .codigo.equals(codigo)){
       			return tipoLocalidade .descricao;
       		}
    	}
       	return null;
    }

}