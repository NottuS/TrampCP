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

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import gov.pr.celepar.framework.exception.ApplicationException;
import gov.pr.celepar.framework.facade.MatriculaFacade;
import gov.pr.celepar.framework.pojo.Serie;
import gov.pr.celepar.framework.report.ReportDefinition;


public class MatriculaGenerator extends ReportDefinition {

    /**
     * @param HashMap filterParameters
     * @param File jasperFile
     * @param String pathBaseDir
     */
    public MatriculaGenerator(HashMap<String, Object> filterParameters, File jasperFile, String pathBaseDir) {
        super(filterParameters, jasperFile, pathBaseDir);
    }

    public void execute() throws ApplicationException {
        this.putJasperParameter("pathSubRel",filterParameters.get("pathSubRel"));
        this.putJasperParameter("image1",filterParameters.get("image1"));
        this.putJasperParameter("image2",filterParameters.get("image2"));
    	List<HashMap<String, Object>> dados = gerarDadosRelatorio();
        this.setList((ArrayList<?>) dados);
    }

    private List<HashMap<String, Object>> gerarDadosRelatorio() throws ApplicationException {
        List<HashMap<String, Object>> listMap = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> map = null;
        Collection <Serie> collSeries = null;        
                        
        try {
        	// Recupera a colecao de Series ...
        	collSeries = MatriculaFacade.listarSeriesComRelacionamentos();
        	
        	for(Serie serie : collSeries) {
        		map  = new HashMap<String, Object>();
	            map.put("serie", serie.getNomeSerie());
	            map.put("idSerie", serie.getIdSerie());
	            map.put("matriculas", serie.getMatriculas());
	            
	            listMap.add(map);
        	}
        } catch (Exception e) {
            throw new ApplicationException("mensagem.erro.matricula.relatorio", e, ApplicationException.ICON_ERRO);
        }
        
        return listMap;
    }       
}