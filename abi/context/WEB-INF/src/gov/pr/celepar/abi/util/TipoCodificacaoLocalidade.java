package gov.pr.celepar.abi.util;


public enum TipoCodificacaoLocalidade{
	
	CORREIOS(0, "Correios"),
	SERPRO(1, "SERPRO"),
	IBGE(2, "IBGE");
	

	private final Integer codigo;		
	private final String descricao;
	
	
	
	private TipoCodificacaoLocalidade(Integer codigo, String descricao) {			
		this.codigo = codigo;
		this.descricao = descricao;
	}		

	public static TipoCodificacaoLocalidade getTipoCodificacaoLocalidade (Integer valor){	    	
	   	for (TipoCodificacaoLocalidade tipoCodificacaoLocalidade  : TipoCodificacaoLocalidade.values()){
	   		if(tipoCodificacaoLocalidade .codigo.equals(valor)){
	   			return tipoCodificacaoLocalidade ;
	   		}
		}
	   	return null;
	}
	
	public Integer getCodigo(){
		return codigo;
	}
	
	public static String getDescricao(Integer codigo){	    	
       	for (TipoCodificacaoLocalidade tipoCodificacaoLocalidade  : TipoCodificacaoLocalidade.values()){
       		if(tipoCodificacaoLocalidade .codigo.equals(codigo)){
       			return tipoCodificacaoLocalidade .descricao;
       		}
    	}
       	return null;
    }

}