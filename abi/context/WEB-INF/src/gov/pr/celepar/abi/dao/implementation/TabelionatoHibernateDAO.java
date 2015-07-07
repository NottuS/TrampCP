package gov.pr.celepar.abi.dao.implementation;

import gov.pr.celepar.abi.dao.TabelionatoDAO;
import gov.pr.celepar.abi.pojo.Tabelionato;
import gov.pr.celepar.framework.database.GenericHibernateDAO;
import gov.pr.celepar.framework.database.HibernateUtil;
import gov.pr.celepar.framework.exception.ApplicationException;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;


/**
 * Classe de manipulação de objetos da classe Tabelionato.
 * 
 * @author CodeGenerator - Esta classe foi gerada automaticamente
 * @since 1.0
 * @version 1.0, 24/12/2009 
 *
 */
public class TabelionatoHibernateDAO extends GenericHibernateDAO<Tabelionato, Integer> implements TabelionatoDAO {
	private static Logger log = Logger.getLogger(TabelionatoHibernateDAO.class);
	protected static final String LOGERROFECHARSESSAO = "Problema ao tentar fechar sessao do Hibernate";
	protected static final String LOGINICIO = "Abrindo Sessao Hibernate";
	protected static final String LOGFIM = "Fechando Sessao Hibernate";
	
	
	/**
	 * Obtem o tabelionato atraves da descricao
	 * @param descTabelionato
	 * @return Tabelionato
	 * @throws ApplicationException 
	 */
	public Tabelionato obterTabelionatoPorDescricao(String descTabelionato) throws ApplicationException {
		log.info("Método obterCartorioPorDescricao processando...");
		
		try {
			Session session = HibernateUtil.currentSession();
			
			// Montando o SQL					
			StringBuffer sql = new StringBuffer();
			sql.append(" FROM Tabelionato t ");
			sql.append(" WHERE UPPER(t.descricao) LIKE :descTabelionato");
			
			
			// Montando Query e fazendo a busca
			Query q = session.createQuery(sql.toString());
			q.setMaxResults(1);
			q.setString("descTabelionato", descTabelionato.toUpperCase());

			Tabelionato tabelionato = (Tabelionato) q.uniqueResult();
			
			return tabelionato;

		} catch (HibernateException he) {
			throw new ApplicationException("mensagem.erro.9001", new String[]{"TabelionatoHibernateDAO.obterCartorioPorDescricao()"}, he, ApplicationException.ICON_ERRO);
		}  catch (Exception e) {
			throw new ApplicationException("mensagem.erro.9001", new String[]{"TabelionatoHibernateDAO.obterCartorioPorDescricao()"}, e, ApplicationException.ICON_ERRO);	
		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (Exception e) {
				log.warn(LOGERROFECHARSESSAO);
			}
		}
	}

}
