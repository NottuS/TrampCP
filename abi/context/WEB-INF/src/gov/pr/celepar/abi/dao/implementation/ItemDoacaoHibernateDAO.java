package gov.pr.celepar.abi.dao.implementation;

import gov.pr.celepar.abi.dao.ItemDoacaoDAO;
import gov.pr.celepar.abi.pojo.ItemDoacao;
import gov.pr.celepar.abi.pojo.StatusTermo;
import gov.pr.celepar.framework.database.GenericHibernateDAO;
import gov.pr.celepar.framework.database.HibernateUtil;
import gov.pr.celepar.framework.exception.ApplicationException;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Classe de manipulação de objetos da classe Doacao.
 * 
 * @author CodeGenerator - Esta classe foi gerada automaticamente
 * @since 1.0
 * @version 1.0, 24/12/2009
 *
 */
public class ItemDoacaoHibernateDAO extends GenericHibernateDAO<ItemDoacao, Integer> implements ItemDoacaoDAO {

	private static Logger log4j = Logger.getLogger(ItemDoacaoHibernateDAO.class);

	@Override
	public Collection<ItemDoacao> listar(Integer codDoacao) throws ApplicationException {
		try {
			Session session = HibernateUtil.currentSession();
			StringBuffer hql = new StringBuffer();
			hql.append(" FROM ItemDoacao a ")
			.append(" LEFT JOIN FETCH a.edificacao edificacao ")	
			.append(" WHERE a.doacao.codDoacao = :codDoacao ");
			
			Query q = session.createQuery(hql.toString());
			q.setInteger("codDoacao", codDoacao );
				
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
	public Collection<ItemDoacao> listarByBemImovelEdificacaoStatus(Integer codBemImovel, Integer codEdificacao, StatusTermo status) throws ApplicationException {
		try {
			Session session = HibernateUtil.currentSession();
			StringBuffer hql = new StringBuffer();
			hql.append(" FROM ItemDoacao a ")
			.append(" JOIN FETCH a.doacao doacao ")	
			.append(" JOIN FETCH doacao.orgaoResponsavel orgaoResponsavel ")	
			.append(" LEFT JOIN FETCH a.edificacao edificacao ")	
			.append(" WHERE a.doacao.statusTermo.codStatusTermo = :codStatusTermo ");
			if (codBemImovel != null && codBemImovel > 0) {
				hql.append(" AND a.doacao.bemImovel.codBemImovel = :codBemImovel ");
			}
			if (codEdificacao != null && codEdificacao > 0) {
				hql.append(" AND edificacao.codEdificacao = :codEdificacao ");
			}

			Query q = session.createQuery(hql.toString());
			q.setInteger("codStatusTermo", status.getCodStatusTermo());
			if (codBemImovel != null && codBemImovel > 0) {
				q.setInteger("codBemImovel", codBemImovel);
			}
			if (codEdificacao != null && codEdificacao > 0) {
				q.setInteger("codEdificacao", codEdificacao );
			}
				
			return q.list();

		} catch (HibernateException he) {
			throw new ApplicationException("mensagem.erro.banco.listarByBemImovelEdificacaoStatus", he);
		} catch (Exception e) {
			throw new ApplicationException("mensagem.erro.banco.listarByBemImovelEdificacaoStatus", e, ApplicationException.ICON_ERRO);
		}finally {
			try {
				HibernateUtil.closeSession();
			} catch (Exception e) {
				log4j.error("Erro ao Fechar Conexao com o Hibernate: ", e);				
			}
		}
	}

}
