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

package gov.pr.celepar.framework.testes;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import gov.pr.celepar.framework.dao.SerieDAO;
import gov.pr.celepar.framework.dao.factory.DAOFactory;
import gov.pr.celepar.framework.exception.ApplicationException;
import gov.pr.celepar.framework.pojo.Serie;

public class SerieHibernateDAOTest {

	private static SerieDAO serieDAO;
	
	
	@BeforeClass
	public static void executarAntesDosTestes() throws Exception {
		serieDAO = DAOFactory.getDAOFactory(DAOFactory.HIBERNATE).getSerieDAO();
	}

	@AfterClass
	public static void executarDepoisDosTestes() throws Exception {
		serieDAO = null;
	}
	
	
	@Test
	public void listarSeriesComRelacionamentos() {
		try {
			
			Collection<Serie> coll = serieDAO.listarComRelacionamentos();
			assertNotNull(coll);
			assertTrue(coll.size() > 0);
			
		} catch (ApplicationException appEx) {
			fail("Lan�ou uma app exception - " + appEx.getMessage());
		} catch (Exception e) {
			fail("Lan�ou uma exception - " + e.getMessage());
		}
	}

}
