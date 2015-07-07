package gov.pr.celepar.abi.dao.implementation;

import gov.pr.celepar.abi.dao.AssinaturaDAO;
import gov.pr.celepar.abi.pojo.Assinatura;
import gov.pr.celepar.framework.database.GenericHibernateDAO;
import gov.pr.celepar.framework.database.HibernateUtil;
import gov.pr.celepar.framework.exception.ApplicationException;

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
public class AssinaturaHibernateDAO extends GenericHibernateDAO<Assinatura, Integer> implements AssinaturaDAO {

	private static final Logger log4j = Logger.getLogger(AssinaturaHibernateDAO.class);

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Assinatura> listar(Integer qtdPagina, Integer numPagina, Assinatura assinatura) throws ApplicationException {
		try {
			Session session = HibernateUtil.currentSession();
			StringBuffer hql = new StringBuffer();
			hql.append(" FROM Assinatura a ")
			.append(" JOIN FETCH a.orgao orgao ")	
			.append(" JOIN FETCH a.cargoAssinatura cargoAssinatura ");
			hql.append("WHERE 1=1 ");
			
			if (assinatura.getCpf() != null && assinatura.getCpf().length() > 0) {
				hql.append("AND a.cpf = :cpf ");
			}
			if (assinatura.getNome() != null && assinatura.getNome().length() > 0) {
				hql.append("AND UPPER(a.nome) LIKE :nome ");
			}

			hql.append(" ORDER BY a.codAssinatura ");
			Query q = session.createQuery(hql.toString());

			if (assinatura.getCpf() != null && assinatura.getCpf().length() > 0) {
				q.setString("cpf", assinatura.getCpf() );
			}	
			if (assinatura.getNome() != null && assinatura.getNome().length() > 0) {
				q.setString("nome", "%" + assinatura.getNome().toUpperCase() + "%");
			}	

			if (qtdPagina != null && numPagina != null) {
				q.setMaxResults(qtdPagina.intValue());
				q.setFirstResult( (numPagina.intValue()-1) * qtdPagina.intValue() );
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
	public Assinatura obterCompleto(Integer codAssinatura) throws ApplicationException {
		try {
			Session session = HibernateUtil.currentSession();
			StringBuffer hql = new StringBuffer();
			hql.append(" FROM Assinatura a ")
			.append(" JOIN FETCH a.orgao ")	
			.append(" JOIN FETCH a.instituicao ")	
			.append(" JOIN FETCH a.cargoAssinatura ")
			.append(" LEFT JOIN FETCH a.listaAssinaturaDoacao ")	
			.append(" LEFT JOIN FETCH a.listaAssinaturaTransferencia ");	
			hql.append("WHERE a.codAssinatura = :codAssinatura ");
			
			Query q = session.createQuery(hql.toString());
			q.setInteger("codAssinatura", codAssinatura );

			return (Assinatura) q.uniqueResult();

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
	public Collection<Assinatura> listarAssinaturaByInstituicao(Assinatura assinatura) throws ApplicationException {
		try {
			Session session = HibernateUtil.currentSession();
			StringBuffer hql = new StringBuffer();
			hql.append(" FROM Assinatura a ")
			.append(" JOIN FETCH a.orgao orgao ")	
			.append(" JOIN FETCH orgao.instituicao instituicao ")	
			.append(" JOIN FETCH a.cargoAssinatura cargoAssinatura ");
			hql.append("WHERE ind_ativo = true ");
			
			if (assinatura.getCpf() != null && assinatura.getCpf().length() > 0) {
				hql.append("AND a.cpf = :cpf ");
			}
			if (assinatura.getOrgao() != null && assinatura.getOrgao().getCodOrgao() > 0) {
				hql.append("AND a.orgao.codOrgao = :orgao ");
			}
			if (assinatura.getInstituicao() != null && assinatura.getInstituicao().getCodInstituicao() > 0) {
				hql.append("AND a.orgao.instituicao.codInstituicao = :codInstituicao ");
			}
			if (assinatura.getCargoAssinatura() != null && assinatura.getCargoAssinatura().getCodCargoAssinatura() > 0) {
				hql.append("AND a.cargoAssinatura.codCargoAssinatura = :cargoAssinatura ");
			}
			
			hql.append(" ORDER BY a.codAssinatura ");
			Query q = session.createQuery(hql.toString());

			if (assinatura.getCpf() != null && assinatura.getCpf().length() > 0) {
				q.setString("cpf", assinatura.getCpf());
			}	
			if (assinatura.getOrgao() != null && assinatura.getOrgao().getCodOrgao() > 0) {
				q.setInteger("orgao", assinatura.getOrgao().getCodOrgao());
			}
			if (assinatura.getInstituicao() != null && assinatura.getInstituicao().getCodInstituicao() > 0) {
				q.setInteger("codInstituicao", assinatura.getInstituicao().getCodInstituicao());
			}
			if (assinatura.getCargoAssinatura() != null && assinatura.getCargoAssinatura().getCodCargoAssinatura() > 0) {
				q.setInteger("cargoAssinatura", assinatura.getCargoAssinatura().getCodCargoAssinatura());
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
	public Collection<Assinatura> listarByInstituicao(Integer qtdPagina, Integer numPagina, Assinatura assinatura) throws ApplicationException {
		try {
			Session session = HibernateUtil.currentSession();
			StringBuffer hql = new StringBuffer();
			hql.append(" FROM Assinatura a ")
			.append(" JOIN FETCH a.orgao orgao ")	
			.append(" JOIN FETCH a.cargoAssinatura cargoAssinatura ");
			hql.append("WHERE 1=1 ");
			
			if (assinatura.getCpf() != null && assinatura.getCpf().length() > 0) {
				hql.append("AND a.cpf = :cpf ");
			}
			if (assinatura.getNome() != null && assinatura.getNome().length() > 0) {
				hql.append("AND UPPER(a.nome) LIKE :nome ");
			}
			if (assinatura.getInstituicao() != null && assinatura.getInstituicao().getCodInstituicao() > 0) {
				hql.append("AND a.instituicao.codInstituicao = :codInstituicao ");
			}

			hql.append(" ORDER BY a.codAssinatura ");
			Query q = session.createQuery(hql.toString());

			if (assinatura.getCpf() != null && assinatura.getCpf().length() > 0) {
				q.setString("cpf", assinatura.getCpf() );
			}	
			if (assinatura.getNome() != null && assinatura.getNome().length() > 0) {
				q.setString("nome", "%" + assinatura.getNome().toUpperCase() + "%");
			}	
			if (assinatura.getInstituicao() != null && assinatura.getInstituicao().getCodInstituicao() > 0) {
				q.setInteger("codInstituicao", assinatura.getInstituicao().getCodInstituicao());
			}	

			if (qtdPagina != null && numPagina != null) {
				q.setMaxResults(qtdPagina.intValue());
				q.setFirstResult( (numPagina.intValue()-1) * qtdPagina.intValue() );
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
