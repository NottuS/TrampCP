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

package gov.pr.celepar.framework.dao.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import gov.pr.celepar.framework.dao.AlunoDAO;
import gov.pr.celepar.framework.dao.SerieDAO;


/**
 * @author Grupo Framework - Componentes
 * @version 1.0
 * @since 18/01/2005
 * 
 */
public class JdbcDAOFactory extends DAOFactory {	
	public static final String DRIVER="org.postgresql.Driver";
	public static final String DBURL="jdbc:postgresql://bancos.pinhao.desenvolvimento.eparana.parana/pinhao?schema=exemplo";

	public static Connection createConnection() throws Exception {
		Class.forName(DRIVER);
		return DriverManager.getConnection(DBURL);
	}
	
	public AlunoDAO getAlunoDAO() {
		return null;
	}
	
	public SerieDAO getSerieDAO() {
		return null;
	}

}