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


import gov.pr.celepar.framework.dao.AlunoDAO;
import gov.pr.celepar.framework.dao.factory.DAOFactory;
import gov.pr.celepar.framework.exception.ApplicationException;
import gov.pr.celepar.framework.pojo.Aluno;
import java.util.Collection;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


public class AlunoDAOTest {
	
	private static AlunoDAO dao;
	private Integer idAluno = 2;
	private String nomeAluno = "Karin";
	
	@BeforeClass
	public static void executarAntesDosTestes() throws Exception {
		dao = DAOFactory.getDAOFactory(DAOFactory.HIBERNATE).getAlunoDAO();
	}

	@AfterClass
	public static void executarDepoisDosTestes() throws Exception {
		dao = null;
	}

	@Test
	public void buscarAlunoPorPK() {
		try {
			Aluno al = dao.obter(idAluno);
			assertNotNull(al);
			assertEquals(idAluno, al.getIdAluno());
			
		} catch (ApplicationException e) {
			fail("Lançou uma app exception - " + e.getMessage());
		} catch (Exception e) {
			fail("Lançou uma exception - " + e.getMessage());
		}
	}

	@Test
	public void pesquisarAlunos() {
		Aluno aluno = new Aluno();
		aluno.setNomeAluno(nomeAluno);		
		try {
			Collection<Aluno> ls = dao.listar(aluno, 5, 1);
			assertNotNull(ls);
			if(ls != null){
				assertEquals(1, ls.size());
			}
		} catch (ApplicationException e) {
			fail("Lançou uma app exception - " + e.getMessage());
		} catch (Exception e) {
			fail("Lançou uma exception - " + e.getMessage());
		}
	}

	@Test
	public void buscarQtdAlunos() {
		Aluno aluno = new Aluno();
		aluno.setNomeAluno(nomeAluno);		
		try {
			Long tmp = dao.buscarQtdLista(aluno);
			assertNotNull(tmp);
			assertTrue(tmp > 0);
			
		} catch (ApplicationException e) {
			fail("Lançou uma app exception - " + e.getMessage());
		} catch (Exception e) {
			fail("Lançou uma exception - " + e.getMessage());
		}
	}

//	@Test
	public void consultaNatural() {
		try {
			assertNotNull(dao.consultarMainFrame());
			
		} catch (ApplicationException e) {
			e.printStackTrace();
			fail("Lançou uma app exception - " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			fail("Lançou uma exception - " + e.getMessage());
		}
	}

}