/*
 * Este programa é licenciado de acordo com a
 * LPG-AP (LICENÇA PÚBLICA GERAL PARA PROGRAMAS DE COMPUTADOR DA ADMINISTRAÇÃO PÚBLICA),
 * versão 1.1 ou qualquer versão posterior.
 * A LPG-AP deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e REPRODUÇÕES deste Programa.
 * Caso uma copia da LPG-AP nao esteja disponivel junto com este Programa, você
 * pode contatar o LICENCIANTE ou então acessar diretamente:
 * http://www.celepar.pr.gov.br/licenca/LPG-AP.pdf
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa
 * e preciso estar de acordo com os termos da LPG-AP.
 */

package gov.pr.celepar.ucs_manterinstituicao.dao.factory;

import gov.pr.celepar.ucs_manterinstituicao.dao.dao.InstituicaoDAO;
import gov.pr.celepar.ucs_manterinstituicao.dao.dao.NaturezaJuridicaDAO;
import gov.pr.celepar.ucs_manterinstituicao.dao.implementation.AreaInteresseHibernateDAO;
import gov.pr.celepar.ucs_manterinstituicao.dao.implementation.InstituicaoHibernateDAO;
import gov.pr.celepar.ucs_manterinstituicao.dao.implementation.NaturezaJuridicaHibernateDAO;
import gov.pr.celepar.ucs_manterinstituicao.dao.implementation.TelefoneHibernateDAO;

/* ********************************************************************
  Inserir aqui os imports das interfaces e das implementacoes dos DAOs.
  Ex:
  import gov.pr.celepar.framework.dao.AlunoDAO;
  import gov.pr.celepar.framework.dao.SerieDAO;
  import gov.pr.celepar.framework.dao.implementation.AlunoHibernateDAO;
  import gov.pr.celepar.framework.dao.implementation.SerieHibernateDAO;
* *********************************************************************/

/**
 * @author Framework
 * @version 1.0
 * @since 18/01/2006
 * 
 */
public class HibernateDAOFactory extends DAOFactory {	
	
	public NaturezaJuridicaDAO getNaturezaJuridicaDAO() {
		return new NaturezaJuridicaHibernateDAO();
	}
	
	public InstituicaoDAO getInstituicaoDAO() {
		return new InstituicaoHibernateDAO();
	}
	
	public AreaInteresseHibernateDAO getAreaInteresseDAO() {
		return new AreaInteresseHibernateDAO();
	}
	
	public TelefoneHibernateDAO getTelefoneDAO() {
		return new TelefoneHibernateDAO();
	}
}