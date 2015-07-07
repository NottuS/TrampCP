package gov.pr.celepar.abi.dao.implementation;

import gov.pr.celepar.abi.dao.DenominacaoImovelDAO;
import gov.pr.celepar.abi.pojo.DenominacaoImovel;
import gov.pr.celepar.framework.database.GenericHibernateDAO;
import gov.pr.celepar.framework.database.HibernateUtil;
import gov.pr.celepar.framework.exception.ApplicationException;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Classe de manipulação de objetos da classe DenominacaoImovel.
 * 
 * @author CodeGenerator - Esta classe foi gerada automaticamente
 * @since 1.0
 * @version 1.0, 24/12/2009 
 *
 */
public class DenominacaoImovelHibernateDAO extends GenericHibernateDAO<DenominacaoImovel, Integer> implements DenominacaoImovelDAO {

	private static final Logger log4j = Logger.getLogger(DenominacaoImovelHibernateDAO.class);
	protected static final String LOGINICIO = "Abrindo Sessao Hibernate";
	protected static final String LOGFIM = "Fechando Sessao Hibernate";
	protected static final String LOGERROFECHARSESSAO = "Problema ao tentar fechar sessao do Hibernate";
	
	/**
	 * Metodo para listar denominacaoimovel, exceto lista parametro.<br>
	 * @author oksana
	 * @since 30/06/2011
	 * @param listaCodDenominacaoImovel : List<Integer>
	 * @return List<DenominacaoImovel> 
	 * @throws ApplicationException
	 */
	public List<DenominacaoImovel> listarExceto(List<Integer> listaCodDenominacaoImovel) throws ApplicationException {
		try {
			log4j.debug("Obtendo sessao corrente...");
			Session session = HibernateUtil.currentSession();
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append(" SELECT denominacaoImovel FROM DenominacaoImovel denominacaoImovel WHERE 1=1 ");
			
			if (listaCodDenominacaoImovel != null && !listaCodDenominacaoImovel.isEmpty()) {
				sbQuery.append(" AND denominacaoImovel.codDenominacaoImovel NOT IN (:lista)");
			}

			sbQuery.append(" ORDER BY denominacaoImovel.descricao");
				
			Query query = session.createQuery(sbQuery.toString());
			if (listaCodDenominacaoImovel != null && !listaCodDenominacaoImovel.isEmpty()) {
				query.setParameterList("lista", listaCodDenominacaoImovel);
			}
			return (List<DenominacaoImovel>)query.list();
			
		} catch (ApplicationException ae) {
			throw ae;
		} catch (Exception e) {
			throw new ApplicationException("mensagem.erro.9001", new String[]{DenominacaoImovelHibernateDAO.class.getSimpleName()+".listar()"}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				HibernateUtil.closeSession();
			}catch (Exception e) {
				log4j.error("Problema ao tentar fechar conexao com o banco de dados: listarExceto", e);
			}
		}
	}

	/**
	 * Metodo para listar denominacaoimovel, contenha lista parametro.<br>
	 * @author oksana
	 * @since 30/06/2011
	 * @param listaCodDenominacaoImovel : List<Integer>
	 * @return List<DenominacaoImovel> 
	 * @throws ApplicationException
	 */
	public List<DenominacaoImovel> listarContenha(List<Integer> listaCodDenominacaoImovel) throws ApplicationException {
		try {
			log4j.debug("Obtendo sessao corrente...");
			Session session = HibernateUtil.currentSession();
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append(" SELECT denominacaoImovel FROM DenominacaoImovel denominacaoImovel WHERE 1=1 ");
			
			if (listaCodDenominacaoImovel != null && !listaCodDenominacaoImovel.isEmpty()) {
				sbQuery.append(" AND denominacaoImovel.codDenominacaoImovel IN (:lista)");
			}

			sbQuery.append(" ORDER BY denominacaoImovel.descricao");
				
			Query query = session.createQuery(sbQuery.toString());
			if (listaCodDenominacaoImovel != null && !listaCodDenominacaoImovel.isEmpty()) {
				query.setParameterList("lista", listaCodDenominacaoImovel);
			}
			return (List<DenominacaoImovel>)query.list();
			
		} catch (ApplicationException ae) {
			throw ae;
		} catch (Exception e) {
			throw new ApplicationException("mensagem.erro.9001", new String[]{DenominacaoImovelHibernateDAO.class.getSimpleName()+".listar()"}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				HibernateUtil.closeSession();
			}catch (Exception e) {
				log4j.error("Problema ao tentar fechar conexao com o banco de dados: listarContenha", e);
			}
		}
	}
}
