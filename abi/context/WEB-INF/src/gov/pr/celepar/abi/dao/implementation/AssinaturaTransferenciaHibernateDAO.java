package gov.pr.celepar.abi.dao.implementation;

import gov.pr.celepar.abi.dao.AssinaturaTransferenciaDAO;
import gov.pr.celepar.abi.pojo.AssinaturaTransferencia;
import gov.pr.celepar.framework.database.GenericHibernateDAO;
import gov.pr.celepar.framework.database.HibernateUtil;
import gov.pr.celepar.framework.exception.ApplicationException;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Classe de manipulação de objetos da classe Transferencia.
 * 
 * @author CodeGenerator - Esta classe foi gerada automaticamente
 * @since 1.0
 * @version 1.0, 24/12/2009
 *
 */
public class AssinaturaTransferenciaHibernateDAO extends GenericHibernateDAO<AssinaturaTransferencia, Integer> implements AssinaturaTransferenciaDAO {

	private static Logger log4j = Logger.getLogger(AssinaturaTransferenciaHibernateDAO.class);

	@Override
	public Collection<AssinaturaTransferencia> listar(Integer codTransferencia) throws ApplicationException {
		try {
			Session session = HibernateUtil.currentSession();
			StringBuffer hql = new StringBuffer();
			hql.append(" FROM AssinaturaTransferencia a ")
			.append(" JOIN FETCH a.assinatura assinatura ")	
			.append(" JOIN FETCH assinatura.cargoAssinatura cargoAssinatura ")
			.append(" JOIN FETCH assinatura.orgao orgao ")
			.append(" WHERE a.transferencia.codTransferencia = :codTransferencia ");
			
			Query q = session.createQuery(hql.toString());
			q.setInteger("codTransferencia", codTransferencia );
				
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

	public Collection<AssinaturaTransferencia> listaVerificacao(AssinaturaTransferencia assinaturaTransferencia) throws ApplicationException {
		try {
			Session session = HibernateUtil.currentSession();
			StringBuffer hql = new StringBuffer();
			hql.append(" FROM AssinaturaTransferencia a ")
			.append(" JOIN FETCH a.assinatura assinatura ")	
			.append(" JOIN FETCH assinatura.cargoAssinatura cargoAssinatura ")
			.append(" JOIN FETCH assinatura.orgao orgao ")
			.append(" WHERE a.transferencia.codTransferencia = :codTransferencia ");
			
			Query q = session.createQuery(hql.toString());
			q.setInteger("codTransferencia", assinaturaTransferencia.getTransferencia().getCodTransferencia());
				
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
