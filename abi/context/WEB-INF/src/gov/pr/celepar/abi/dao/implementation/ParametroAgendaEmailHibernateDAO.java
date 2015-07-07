package gov.pr.celepar.abi.dao.implementation;

import gov.pr.celepar.abi.dao.ParametroAgendaEmailDAO;
import gov.pr.celepar.abi.pojo.ParametroAgendaEmail;
import gov.pr.celepar.framework.database.GenericHibernateDAO;
import gov.pr.celepar.framework.database.HibernateUtil;
import gov.pr.celepar.framework.exception.ApplicationException;

import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Classe de manipulacao de objetos da classe ParametroAgendaEmail
 * 

 *
 */
public class ParametroAgendaEmailHibernateDAO extends GenericHibernateDAO<ParametroAgendaEmail, Integer> implements ParametroAgendaEmailDAO {

	private static final Logger log4j = Logger.getLogger(ParametroAgendaEmailDAO.class);
	protected static final String LOGINICIO = "Abrindo Sessao Hibernate";
	protected static final String LOGFIM = "Fechando Sessao Hibernate";
	protected static final String LOGERROFECHARSESSAO = "Problema ao tentar fechar sessao do Hibernate";
	
	@SuppressWarnings("unchecked")

	public Collection<ParametroAgendaEmail> listar(Integer codParametroAgenda) throws ApplicationException {
		try {
			Session session = HibernateUtil.currentSession();
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append(" SELECT parametroAgendaEmail FROM ParametroAgendaEmail parametroAgendaEmail ");
			sbQuery.append(" LEFT JOIN FETCH parametroAgendaEmail.parametroAgenda parametroAgenda ");
			sbQuery.append(" WHERE 1=1");
			if (codParametroAgenda != null){
				sbQuery.append(" AND parametroAgenda.codParametroAgenda = :codParametroAgenda");
			}
			Query query = session.createQuery(sbQuery.toString());
			
			if (codParametroAgenda != null){
				query.setInteger("codParametroAgenda", codParametroAgenda);	
			}
				
			return (List<ParametroAgendaEmail>)query.list();
			

		} catch (HibernateException he) {
			throw new ApplicationException("mensagem.erro.banco.listar", he);
		} catch (Exception e) {
			throw new ApplicationException("mensagem.erro.banco.listar", e, ApplicationException.ICON_ERRO);
		}finally {
			try {
				HibernateUtil.closeSession();
			} catch (Exception e) {
				log4j.error("Erro ao Fechar Conexao com o Hibernate: ", e);				
			}
		}
	}

}
