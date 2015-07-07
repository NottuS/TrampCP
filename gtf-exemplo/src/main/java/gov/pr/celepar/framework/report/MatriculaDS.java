/*
 * Este programa � licenciado de acordo com a
 * LPG-AP (LICEN�A P�BLICA GERAL PARA PROGRAMAS DE COMPUTADOR DA ADMINISTRA��O P�BLICA),
 * vers�o 1.1 ou qualquer vers�o posterior.
 * A LPG-AP deve acompanhar todas PUBLICA��ES, DISTRIBUI��ES e REPRODU��ES deste Programa.
 * Caso uma c�pia da LPG-AP n�o esteja dispon�vel junto com este Programa, voc�
 * pode contatar o LICENCIANTE ou ent�o acessar diretamente:
 * http://www.celepar.pr.gov.br/licenca/LPG-AP.pdf
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa
 * � preciso estar de acordo com os termos da LPG-AP.
 */

package gov.pr.celepar.framework.report;

import gov.pr.celepar.framework.pojo.Matricula;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


public class MatriculaDS extends ReportDS {
	
	@SuppressWarnings("unchecked")
	public MatriculaDS(Object listaSubReport) {		
   		if( listaSubReport instanceof Collection ) {		
   			super.setList( this.executarSubMatriculas((Collection<Matricula>)listaSubReport) );
   		}		
	}	
	
	private ArrayList<HashMap<String, Object>> executarSubMatriculas(Collection<Matricula> listaSubReport) {
		ArrayList<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();		
		HashMap<String, Object> mapa = null;		
				
		for(Matricula matricula : listaSubReport) {		
			mapa = new HashMap<String, Object>();
	   		   		
	   		mapa.put("ano", matricula.getComp_id().getAnoMatricula().toString());
			mapa.put("nomeAluno", matricula.getAluno().getNomeAluno());
			mapa.put("cpfAluno", matricula.getAluno().getCpfAluno());
			mapa.put("mae", matricula.getAluno().getMaeAluno());
			mapa.put("pai", matricula.getAluno().getPaiAluno());
			mapa.put("logradouroAluno", matricula.getAluno().getEndereco().getLogradouroEndereco());
			mapa.put("numeroAluno", matricula.getAluno().getEndereco().getNumeroEndereco());
			mapa.put("complementoAluno", matricula.getAluno().getEndereco().getComplementoEndereco());
			mapa.put("cepAluno", matricula.getAluno().getEndereco().getCepEndereco());
			mapa.put("bairroAluno", matricula.getAluno().getEndereco().getBairroEndereco());
			mapa.put("cidadeAluno", matricula.getAluno().getEndereco().getCidadeEndereco());
			mapa.put("ufAluno", matricula.getAluno().getEndereco().getUfEndereco());
			mapa.put("nomeEscola", matricula.getEscola().getNomeEscola());
			mapa.put("logradouroEscola", matricula.getEscola().getEndereco().getLogradouroEndereco());
			mapa.put("numeroEscola", matricula.getEscola().getEndereco().getNumeroEndereco());
			mapa.put("complementoEscola", matricula.getEscola().getEndereco().getComplementoEndereco());
			mapa.put("cepEscola", matricula.getEscola().getEndereco().getCepEndereco());
			mapa.put("bairroEscola", matricula.getEscola().getEndereco().getBairroEndereco());
			mapa.put("cidadeEscola", matricula.getEscola().getEndereco().getCidadeEndereco());
			mapa.put("ufEscola", matricula.getEscola().getEndereco().getUfEndereco());
			
   			resultList.add(mapa);
		}
		
		return resultList;
	}	
}
