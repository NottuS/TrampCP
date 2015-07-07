/*
 * Este programa é licenciado de acordo com a
 * LPG-AP (LICENÇA PÚBLICA GERAL PARA PROGRAMAS DE COMPUTADOR DA ADMINISTRAÇÃO PÚBLICA),
 * versão 1.1 ou qualquer versão posterior.
 * A LPG-AP deve acompanhar todas PUBLICACÕES, DISTRIBUICÕES e REPRODUCÕES deste Programa.
 * Caso uma copia da LPG-AP nao esteja disponivel junto com este Programa, você
 * pode contatar o LICENCIANTE ou então acessar diretamente:
 * http://www.celepar.pr.gov.br/licenca/LPG-AP.pdf
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa
 * e preciso estar de acordo com os termos da LPG-AP.
 */

package gov.pr.celepar.ucs_manterinstituicao.dao.factory;

/* ********************************************************************
Inserir aqui os imports das interfaces e das implementações dos DAOs.
Ex:
import gov.pr.celepar.framework.dao.AlunoDAO;
import gov.pr.celepar.framework.dao.SerieDAO;
import gov.pr.celepar.framework.dao.implementation.AlunoJdbcDAO;
import gov.pr.celepar.framework.dao.implementation.SerieJdbcDAO;
* *********************************************************************/

/**
 * @author Framework
 * @version 1.0
 * @since 18/01/2006
 * 
 */
public class JdbcDAOFactory extends DAOFactory {	
		
	/* ****************************************************
	 Inserir aqui a implementação para recuperação das DAOs, 
	 conforme definido na super-classe. 
	
	 public AlunoDAO getAlunoDAO() {
		return new AlunoJdbcDAO();
	 }
	
	 public SerieDAO getSerieDAO() {
		return new SerieJdbDAO();
	 }
	* ******************************************************/

}