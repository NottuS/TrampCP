package gov.pr.celepar.abi.dao.implementation;

import gov.pr.celepar.abi.dao.ParametroVistoriaDominioDAO;
import gov.pr.celepar.abi.pojo.ParametroVistoriaDominio;
import gov.pr.celepar.framework.database.GenericHibernateDAO;
import gov.pr.celepar.framework.database.HibernateUtil;
import gov.pr.celepar.framework.exception.ApplicationException;

import org.apache.log4j.Logger;
import org.hibernate.Session;

/**
 * Classe de manipulacao de objetos da classe ParametroVistoriaDominio.
 * 

 *
 */
public class ParametroVistoriaDominioHibernateDAO extends GenericHibernateDAO<ParametroVistoriaDominio, Integer> implements ParametroVistoriaDominioDAO {

	private static final Logger log4j = Logger.getLogger(ParametroVistoriaDominioDAO.class);
	protected static final String LOGINICIO = "Abrindo Sessao Hibernate";
	protected static final String LOGFIM = "Fechando Sessao Hibernate";
	protected static final String LOGERROFECHARSESSAO = "Problema ao tentar fechar sessao do Hibernate";
	
	/**
	 * Fazer o merge dos objetos.<br>
	 * @author pozzatti
	 * @since 21/01/2009
	 * @param parametroVistoriaDominio : ParametroVistoriaDominio
	 * @return DocumentacaoExigidaDados
	 * @throws ApplicationException
	 */	
	public ParametroVistoriaDominio merge(ParametroVistoriaDominio parametroVistoriaDominio) throws ApplicationException{
		try{
			log4j.debug(LOGINICIO);
			Session session = HibernateUtil.currentSession();
			parametroVistoriaDominio = (ParametroVistoriaDominio) session.merge(parametroVistoriaDominio);
			log4j.debug(LOGFIM);
			return parametroVistoriaDominio;
		}catch (ApplicationException ae) {
			log4j.debug(ae.getMessage(),ae);
			throw ae;
		}catch (Exception e) {
			log4j.error(e.getMessage());
			throw new ApplicationException("mensagem.erro.9001", new String[]{ParametroVistoriaDominioHibernateDAO.class.getSimpleName().concat(".merge()")}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				HibernateUtil.closeSession();	
			} catch (Exception e) {
				log4j.error(LOGERROFECHARSESSAO, e);
			}
		}
	}	
}
