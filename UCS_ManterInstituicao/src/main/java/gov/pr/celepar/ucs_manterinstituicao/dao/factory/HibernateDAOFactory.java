/*
 * Este programa � licenciado de acordo com a
 * LPG-AP (LICEN�A P�BLICA GERAL PARA PROGRAMAS DE COMPUTADOR DA ADMINISTRA��O P�BLICA),
 * vers�o 1.1 ou qualquer vers�o posterior.
 * A LPG-AP deve acompanhar todas PUBLICA��ES, DISTRIBUI��ES e REPRODU��ES deste Programa.
 * Caso uma copia da LPG-AP nao esteja disponivel junto com este Programa, voc�
 * pode contatar o LICENCIANTE ou ent�o acessar diretamente:
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