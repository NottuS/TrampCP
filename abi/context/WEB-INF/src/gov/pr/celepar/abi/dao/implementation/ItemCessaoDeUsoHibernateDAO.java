package gov.pr.celepar.abi.dao.implementation;

import gov.pr.celepar.abi.dao.ItemCessaoDeUsoDAO;
import gov.pr.celepar.abi.pojo.CessaoDeUso;
import gov.pr.celepar.abi.pojo.ItemCessaoDeUso;
import gov.pr.celepar.abi.pojo.Orgao;
import gov.pr.celepar.abi.pojo.StatusTermo;
import gov.pr.celepar.abi.util.Dominios;
import gov.pr.celepar.framework.database.GenericHibernateDAO;
import gov.pr.celepar.framework.database.HibernateUtil;
import gov.pr.celepar.framework.exception.ApplicationException;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Classe de manipulação de objetos da classe ItemCessaoDeUso.
 * 
 * @author CodeGenerator - Esta classe foi gerada automaticamente
 * @since 1.0
 * @version 1.0, 24/12/2009
 *
 */
public class ItemCessaoDeUsoHibernateDAO extends GenericHibernateDAO<ItemCessaoDeUso, Integer> implements ItemCessaoDeUsoDAO {

	private static Logger log4j = Logger.getLogger(ItemCessaoDeUsoHibernateDAO.class);

	@Override
	public Collection<ItemCessaoDeUso> listar(Integer codCessaoDeUso) throws ApplicationException {
		try {
			Session session = HibernateUtil.currentSession();
			StringBuffer hql = new StringBuffer();
			hql.append(" FROM ItemCessaoDeUso item ")
			.append(" LEFT JOIN FETCH item.edificacao edificacao ")	
			.append(" WHERE item.cessaoDeUso.codCessaoDeUso = :codCessaoDeUso ");
			
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

	/**
	 * Verifica qual o percentual que foi cedido do imóvel.
	 * sendo 100% é retornado os órgãos que estão utilizando-o.
	 * @param codBemImovel
	 * @return String 
	 */
	public Collection<Orgao> verificaPercentualCedido(CessaoDeUso cessaoDeUso) throws ApplicationException {
		Collection<ItemCessaoDeUso> lista = null;
		Collection<Orgao> listResult = new ArrayList<Orgao>();
		Double percentualCedido = new Double(0);

		try {
			Session session = HibernateUtil.currentSession();
			StringBuffer hql = new StringBuffer();
			hql.append(" FROM ItemCessaoDeUso i ");
			hql.append(" INNER JOIN FETCH i.cessaoDeUso.orgaoCessionario o ");
			hql.append(" WHERE i.cessaoDeUso.bemImovel.codBemImovel = :codBemImovel");
			hql.append(" AND i.cessaoDeUso.statusTermo.codStatusTermo = :codStatusTermo");

			if (cessaoDeUso.getCodCessaoDeUso() != null && cessaoDeUso.getCodCessaoDeUso() > 0) {
				hql.append(" AND i.cessaoDeUso.codCessaoDeUso <> :codCessaoDeUso ");
			}

			Query q = session.createQuery(hql.toString());
			q.setInteger("codBemImovel", cessaoDeUso.getBemImovel().getCodBemImovel());
			q.setInteger("codStatusTermo", Dominios.statusTermo.VIGENTE.getIndex());
			if (cessaoDeUso.getCodCessaoDeUso() != null && cessaoDeUso.getCodCessaoDeUso() > 0) {
				q.setInteger("codCessaoDeUso", cessaoDeUso.getCodCessaoDeUso());
			}
			lista = q.list();

			if(lista!=null){
				for (ItemCessaoDeUso item : lista) {
					if (item != null && item.getAreaPercentual() != null) {
						percentualCedido = percentualCedido + new Double (item.getAreaPercentual().doubleValue());
						listResult.add(item.getCessaoDeUso().getOrgaoCessionario());
					}
				}
			}
			
			if (percentualCedido.compareTo(new Double(100)) != 0) {
				listResult = null;
			}
			
		} catch (HibernateException he) {
			log4j.error("Erro:", he);
			throw new ApplicationException("mensagem.erro.9001", new String[]{"ItemCessaoDeUsoHibernateDAO.cedidoTotalmente()"}, he, ApplicationException.ICON_ERRO);
		} catch (Exception e) {
			log4j.error("Erro:", e);
			throw new ApplicationException("mensagem.erro.9001", new String[]{"ItemCessaoDeUsoHibernateDAO.cedidoTotalmente()"}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				HibernateUtil.closeSession();
			}catch (Exception e) {
				log4j.error("Problema ao tentar fechar conexao com o banco de dados: cedidoTotalmente", e);
			}		
		}

		return listResult;
	}

	public Collection<ItemCessaoDeUso> listarByBemImovelEdificacaoStatus(Integer codBemImovel, Integer codEdificacao, StatusTermo status) throws ApplicationException {
		try {
			Session session = HibernateUtil.currentSession();
			StringBuffer hql = new StringBuffer();
			hql.append(" FROM ItemCessaoDeUso item ")
			.append(" JOIN FETCH item.cessaoDeUso cessaoDeUso ")	
			.append(" JOIN FETCH cessaoDeUso.orgaoCessionario orgaoCessionario ")	
			.append(" LEFT JOIN FETCH item.edificacao edificacao ")	
			.append(" WHERE item.cessaoDeUso.statusTermo.codStatusTermo = :codStatusTermo ");
			if (codBemImovel != null && codBemImovel > 0) {
				hql.append(" AND item.cessaoDeUso.bemImovel.codBemImovel = :codBemImovel ");
			}
			if (codEdificacao != null && codEdificacao > 0) {
				hql.append(" AND edificacao.codEdificacao = :codEdificacao ");
			}
			
			Query q = session.createQuery(hql.toString());
			q.setInteger("codStatusTermo", status.getCodStatusTermo() );
			if (codBemImovel != null && codBemImovel > 0) {
				q.setInteger("codBemImovel", codBemImovel);
			}
			if (codEdificacao != null && codEdificacao > 0) {
				q.setInteger("codEdificacao", codEdificacao);
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
