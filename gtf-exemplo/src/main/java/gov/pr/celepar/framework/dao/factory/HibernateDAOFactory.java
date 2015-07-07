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

package gov.pr.celepar.framework.dao.factory;

import gov.pr.celepar.framework.dao.AlunoDAO;
import gov.pr.celepar.framework.dao.SerieDAO;
import gov.pr.celepar.framework.dao.implementation.AlunoHibernateDAO;
import gov.pr.celepar.framework.dao.implementation.SerieHibernateDAO;

/**
 * @author Grupo Framework - Componentes
 * @version 1.0
 * @since 18/01/2005
 * 
 */
public class HibernateDAOFactory extends DAOFactory {	

	public AlunoDAO getAlunoDAO() {
		return new AlunoHibernateDAO();
	}
	
	public SerieDAO getSerieDAO() {
		return new SerieHibernateDAO();
	}

}