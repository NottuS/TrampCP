package gov.pr.celepar.ucs_manterinstituicao.dao.implementation;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import gov.pr.celepar.framework.database.GenericHibernateDAO;
import gov.pr.celepar.framework.database.HibernateUtil;
import gov.pr.celepar.framework.exception.ApplicationException;
import gov.pr.celepar.ucs_manterinstituicao.dao.dao.TelefoneDAO;
import gov.pr.celepar.ucs_manterinstituicao.pojo.Telefone;

public class TelefoneHibernateDAO extends GenericHibernateDAO<Telefone, Long> implements TelefoneDAO{
	private static Logger log = Logger.getLogger(TelefoneHibernateDAO.class);
	@Override
	public void delete(Collection<Telefone> telefones)
			throws ApplicationException {
		try {
			Session session = HibernateUtil.currentSession();
			for (Telefone telefone : telefones) {
				session.delete(telefone);
			}
			
		}catch (Exception e) {
			log.debug("Problema ao realizar a funcao: deleteTelefone", e);
			throw new ApplicationException("mensagem.erro.telefone.delete", e);
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			try {
				HibernateUtil.closeSession();
			}catch (Exception e) {
				log.error("Problema ao tentar fechar conexao com o banco de dados: delete", e);
			}
		}
	}

}
