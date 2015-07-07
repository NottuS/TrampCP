package gov.pr.celepar.abi.enumeration;

import gov.pr.celepar.abi.util.Enumerador;

import java.util.ArrayList;
import java.util.List;

public enum SituacaoVistoria {
	
	ABERTA(1, "Aberta"), 
	FINALIZADA(2, "Finalizada");

	private final Integer id;
	private final String descricao;

	private SituacaoVistoria(Integer id, String descricao) {			
		this.id = id;
		this.descricao = descricao;
	}		

	public Integer getId(){
		return id;
	}
	
	public static String getDescricao(Integer id) {	    	
       	for (SituacaoVistoria s : SituacaoVistoria.values()) {
       		if(s.id.equals(id)){
       			return s.descricao;
       		}
    	}
       	return null;
    }

    public static List<Enumerador> listar() {
    	List<Enumerador> lst = new ArrayList<Enumerador>();
       	for (SituacaoVistoria s : SituacaoVistoria.values()){
       		lst.add(new Enumerador(s.id, s.descricao));
    	}
   		return lst;
    }
}
