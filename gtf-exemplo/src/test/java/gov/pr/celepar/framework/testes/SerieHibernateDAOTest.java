/*
 * Este programa é licenciado de acordo com a
 * LPG-AP (LICENÇA PÚBLICA GERAL PARA PROGRAMAS DE COMPUTADOR DA ADMINISTRAÇÃO PÚBLICA),
 * versão 1.1 ou qualquer versão posterior.
 * A LPG-AP deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e REPRODUÇÕES deste Programa.
 * Caso uma cópia da LPG-AP não esteja disponível junto com este Programa, você
 * pode contatar o LICENCIANTE ou então acessar diretamente:
 * http://www.celepar.pr.gov.br/licenca/LPG-AP.pdf
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa
 * é preciso estar de acordo com os termos da LPG-AP.
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
			fail("Lançou uma app exception - " + appEx.getMessage());
		} catch (Exception e) {
			fail("Lançou uma exception - " + e.getMessage());
		}
	}

}
