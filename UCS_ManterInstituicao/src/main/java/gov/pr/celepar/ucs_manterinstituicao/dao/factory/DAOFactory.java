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

/* ************************************************* 
   Inserir aqui os imports das interfaces dos DAOs.
   Ex: 
   import gov.pr.celepar.framework.dao.AlunoDAO;
   import gov.pr.celepar.framework.dao.SerieDAO;
* **************************************************/

/**
 * @author Framework
 * @version 1.0
 * @since 18/01/2006
 * 
 */
public abstract class DAOFactory {
	public static final int HIBERNATE = 1;
	public static final int JDBC = 2;
	
	public static DAOFactory getDAOFactory(int whichFactory) {  
		switch (whichFactory) {
	    	case HIBERNATE: 
	    		return new HibernateDAOFactory();
	    	case JDBC:
	    		return new JdbcDAOFactory();
	    	default: 
	    		return null;
	    }
	}
	
	/* **************************************************************
	   Inserir aqui as declarações abstratas de recuperação dos DAOs
	   Ex:	   
	   public abstract AlunoDAO getAlunoDAO();
	   public abstract SerieDAO getSerieDAO();
	* ***************************************************************/	
	
}