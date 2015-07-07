package gov.pr.celepar.abi.dao.implementation;

import gov.pr.celepar.abi.dao.ParametroAgendaDAO;
import gov.pr.celepar.abi.pojo.ParametroAgenda;
import gov.pr.celepar.framework.database.GenericHibernateDAO;
import gov.pr.celepar.framework.database.HibernateUtil;
import gov.pr.celepar.framework.exception.ApplicationException;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Classe de manipulacao de objetos da classe ParametroAgenda
 * 

 *
 */
public class ParametroAgendaHibernateDAO extends GenericHibernateDAO<ParametroAgenda, Integer> implements ParametroAgendaDAO {

	private static final Logger log4j = Logger.getLogger(ParametroAgendaDAO.class);
	protected static final String LOGINICIO = "Abrindo Sessao Hibernate";
	protected static final String LOGFIM = "Fechando Sessao Hibernate";
	protected static final String LOGERROFECHARSESSAO = "Problema ao tentar fechar sessao do Hibernate";
	
	public ParametroAgenda obterUnico(Integer codInstituicao) throws ApplicationException {
		try {
			Session session = HibernateUtil.currentSession();
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append(" SELECT parametroAgenda FROM ParametroAgenda parametroAgenda ");
			sbQuery.append(" LEFT JOIN FETCH parametroAgenda.listaParametroAgendaEmail parametroAgendaEmail ");
			sbQuery.append(" WHERE 1=1  ");
			sbQuery.append(" AND parametroAgenda.instituicao.codInstituicao = :codInstituicao  ");
			sbQuery.append(" AND parametroAgenda.codParametroAgenda in ");
			sbQuery.append("(SELECT MAX(p.codParametroAgenda) ");
			sbQuery.append("FROM ParametroAgenda p where p.instituicao.codInstituicao = :codInstituicao)");
			
			Query query = session.createQuery(sbQuery.toString());
			
			query.setInteger("codInstituicao", codInstituicao);
			
			return (ParametroAgenda)query.uniqueResult();
		} catch (HibernateException he) {
			throw new ApplicationException("mensagem.erro.banco.obterUnico", he);
		} catch (Exception e) {
			throw new ApplicationException("mensagem.erro.banco.obterUnico", e, ApplicationException.ICON_ERRO);
		}finally {
			try {
				HibernateUtil.closeSession();
			} catch (Exception e) {
				log4j.error("Erro ao Fechar Conexao com o Hibernate: ", e);				
			}
		}
	}

}
