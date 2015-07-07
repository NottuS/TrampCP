package gov.pr.celepar.abi.dao.implementation;

import gov.pr.celepar.abi.dao.CargoAssinaturaDAO;
import gov.pr.celepar.abi.pojo.CargoAssinatura;
import gov.pr.celepar.framework.database.GenericHibernateDAO;
import gov.pr.celepar.framework.database.HibernateUtil;
import gov.pr.celepar.framework.exception.ApplicationException;
import gov.pr.celepar.framework.util.Pagina;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Classe de manipulacao de objetos da classe CargoAssinatura.
 * 
 * @author vanessak
 * @since 1.0
 * @version 1.0, 29/06/2011 
 *
 */
public class CargoAssinaturaHibernateDAO extends GenericHibernateDAO<CargoAssinatura, Integer> implements CargoAssinaturaDAO {

	private static final Logger log4j = Logger.getLogger(CargoAssinaturaHibernateDAO.class);

	@Override
	public Collection<CargoAssinatura> listarCargoAssinatura(CargoAssinatura cargoAssinatura, Pagina pag) throws ApplicationException {
		log4j.info("Método listarCargoAssinatura processando...");
		try {
			Session session = HibernateUtil.currentSession();
			StringBuffer hql = new StringBuffer();
			hql.append(" FROM CargoAssinatura a ");
			hql.append("WHERE 1=1 ");
			
			if (cargoAssinatura != null && cargoAssinatura.getDescricao() != null && cargoAssinatura.getDescricao().trim().length() > 0) {
				hql.append("AND UPPER(a.descricao) LIKE :descricao ");
			}
			
			if(cargoAssinatura!= null && cargoAssinatura.getInstituicao() != null)
			{
				hql.append("AND a.instituicao.codInstituicao = :codInstituicao");
			}
			
			hql.append(" ORDER BY a.instituicao.codInstituicao, a.codCargoAssinatura ");
			Query q = session.createQuery(hql.toString());

			if (cargoAssinatura != null && cargoAssinatura.getDescricao() != null && cargoAssinatura.getDescricao().trim().length() > 0) {
				q.setString("descricao", "%" + cargoAssinatura.getDescricao().toUpperCase() + "%");
			}
			
			if(cargoAssinatura != null && cargoAssinatura.getInstituicao() != null)
			{
				q.setInteger("codInstituicao", cargoAssinatura.getInstituicao().getCodInstituicao());
			}

			if (pag != null && pag.getQuantidade() != null && pag.getPaginaAtual() != null) {
				q.setMaxResults(pag.getPaginaAtual().intValue());
				q.setFirstResult( (pag.getPaginaAtual().intValue()-1) * pag.getPaginaAtual().intValue() );
			}

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
	public Collection<CargoAssinatura> listarCargoAssinaturaByOrgaoInstituicao(Integer codOrgao, Integer codInstituicao) throws ApplicationException {
		log4j.info("Método listarCargoAssinaturaByOrgaoInstituicao processando...");
		try {
			Session session = HibernateUtil.currentSession();
			StringBuffer hql = new StringBuffer();
			hql.append(" SELECT DISTINCT a FROM CargoAssinatura a ")
			.append(" JOIN FETCH a.listaAssinatura assinatura ")	
			.append(" JOIN FETCH assinatura.orgao orgao ")	
			.append(" JOIN FETCH a.instituicao instituicao ");
			hql.append("WHERE ind_ativo = true ");
			
			if (codOrgao != null && codOrgao > 0) {
				hql.append("AND orgao.codOrgao = :codOrgao ");
			}
			if (codInstituicao != null && codInstituicao > 0) {
				hql.append("AND a.instituicao.codInstituicao = :codInstituicao ");
			}
			
			hql.append(" ORDER BY a.instituicao.codInstituicao, a.codCargoAssinatura ");
			
			Query q = session.createQuery(hql.toString());

			if (codOrgao != null && codOrgao > 0) {
				q.setInteger("codOrgao", codOrgao);
			}
			if (codInstituicao != null && codInstituicao > 0) {
				q.setInteger("codInstituicao", codInstituicao);
			}

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
	public Collection<CargoAssinatura> listarCargoAssinaturaByInstituicao(CargoAssinatura cargo) throws ApplicationException {
		log4j.info("Método Collection<CargoAssinatura> listarCargoAssinaturaByInstituicao processando...");
		try {
			Session session = HibernateUtil.currentSession();
			StringBuffer hql = new StringBuffer();
			hql.append(" FROM CargoAssinatura a ");
			hql.append(" JOIN FETCH a.instituicao instituicao ");
			hql.append("WHERE 1=1 ");
			
			if (cargo.getInstituicao() != null && cargo.getInstituicao().getCodInstituicao() > 0) {
				hql.append("AND a.instituicao.codInstituicao = :codInstituicao ");
			}
			if (cargo.getDescricao() != null && cargo.getDescricao().trim().length() > 0) {
				hql.append("AND a.descricao = :descricao ");
			}
			
			hql.append(" ORDER BY a.codCargoAssinatura ");
			Query q = session.createQuery(hql.toString());

			if (cargo.getInstituicao() != null && cargo.getInstituicao().getCodInstituicao() > 0) {
				q.setInteger("codInstituicao", cargo.getInstituicao().getCodInstituicao());
			}	
			if (cargo.getDescricao() != null && cargo.getDescricao().trim().length() > 0) {
				q.setString("descricao", cargo.getDescricao());
			}

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
