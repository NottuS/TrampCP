package gov.pr.celepar.abi.dao.implementation;

import gov.pr.celepar.abi.dao.AssinaturaCessaoDeUsoDAO;
import gov.pr.celepar.abi.pojo.AssinaturaCessaoDeUso;
import gov.pr.celepar.framework.database.GenericHibernateDAO;
import gov.pr.celepar.framework.database.HibernateUtil;
import gov.pr.celepar.framework.exception.ApplicationException;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Classe de manipulação de objetos da classe AssinaturaCessaoDeUso.
 * 
 * @author CodeGenerator - Esta classe foi gerada automaticamente
 * @since 1.0
 * @version 1.0, 24/12/2009
 *
 */
public class AssinaturaCessaoDeUsoHibernateDAO extends GenericHibernateDAO<AssinaturaCessaoDeUso, Integer> implements AssinaturaCessaoDeUsoDAO {

	private static Logger log4j = Logger.getLogger(AssinaturaCessaoDeUsoHibernateDAO.class);

	@Override
	public Collection<AssinaturaCessaoDeUso> listaVerificacao(AssinaturaCessaoDeUso assinaturaCessaoDeUso) throws ApplicationException {
		try {
			Session session = HibernateUtil.currentSession();
			StringBuffer hql = new StringBuffer();
			hql.append(" FROM AssinaturaCessaoDeUso a ")
			.append(" JOIN FETCH a.assinatura assinatura ")	
			.append(" JOIN FETCH assinatura.cargoAssinatura cargoAssinatura ")
			.append(" JOIN FETCH assinatura.orgao orgao ")
			.append(" WHERE a.cessaoDeUso.codCessaoDeUso = :codCessaoDeUso ");
			
			Query q = session.createQuery(hql.toString());
			q.setInteger("codCessaoDeUso", assinaturaCessaoDeUso.getCessaoDeUso().getCodCessaoDeUso());
				
			return q.list();

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

	@Override
	public Collection<AssinaturaCessaoDeUso> listar(Integer codCessaoDeUso) throws ApplicationException {
		try {
			Session session = HibernateUtil.currentSession();
			StringBuffer hql = new StringBuffer();
			hql.append(" FROM AssinaturaCessaoDeUso a ")
			.append(" JOIN FETCH a.assinatura assinatura ")	
			.append(" JOIN FETCH assinatura.cargoAssinatura cargoAssinatura ")
			.append(" JOIN FETCH assinatura.orgao orgao ")
			.append(" WHERE a.cessaoDeUso.codCessaoDeUso = :codCessaoDeUso ");
			
			Query q = session.createQuery(hql.toString());
			q.setInteger("codCessaoDeUso", codCessaoDeUso );
				
			return q.list();

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
