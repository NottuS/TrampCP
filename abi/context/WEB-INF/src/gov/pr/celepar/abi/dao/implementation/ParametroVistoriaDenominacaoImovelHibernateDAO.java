package gov.pr.celepar.abi.dao.implementation;

import gov.pr.celepar.abi.dao.ParametroVistoriaDenominacaoImovelDAO;
import gov.pr.celepar.abi.pojo.ParametroVistoriaDenominacaoImovel;
import gov.pr.celepar.framework.database.GenericHibernateDAO;
import gov.pr.celepar.framework.database.HibernateUtil;
import gov.pr.celepar.framework.exception.ApplicationException;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Classe de manipulacao de objetos da classe ParametroVistoriaDominio.
 * 

 *
 */
public class ParametroVistoriaDenominacaoImovelHibernateDAO extends GenericHibernateDAO<ParametroVistoriaDenominacaoImovel, Integer> implements ParametroVistoriaDenominacaoImovelDAO {

	private static final Logger log4j = Logger.getLogger(ParametroVistoriaDenominacaoImovel.class);
	protected static final String LOGINICIO = "Abrindo Sessao Hibernate";
	protected static final String LOGFIM = "Fechando Sessao Hibernate";
	protected static final String LOGERROFECHARSESSAO = "Problema ao tentar fechar sessao do Hibernate";
	
	public void excluirPorParametroVistoria(Integer codParametroVistoria) throws ApplicationException{
		try{
			log4j.debug(LOGINICIO);
			Session session = HibernateUtil.currentSession();
			
			
			StringBuffer hql = new StringBuffer();
			hql.append(" DELETE from ParametroVistoriaDenominacaoImovel ");
			hql.append(" WHERE  parametroVistoria.codParametroVistoria= :codParametroVistoria");
			
			Query query = session.createQuery(hql.toString());
			
			query.setInteger("codParametroVistoria", codParametroVistoria);
			
			query.executeUpdate();
			
		}catch (ApplicationException ae) {
			log4j.debug(ae.getMessage(),ae);
			throw ae;
		}catch (Exception e) {
			log4j.error(e.getMessage());
			throw new ApplicationException("mensagem.erro.9001", new String[]{ParametroVistoriaDenominacaoImovelHibernateDAO.class.getSimpleName().concat(".excluirPorParametroVistoria()")}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				HibernateUtil.closeSession();	
			} catch (Exception e) {
				log4j.error(LOGERROFECHARSESSAO, e);
			}
		}
	}	
}
