package gov.pr.celepar.abi.dao.implementation;

import gov.pr.celepar.abi.dao.UsuarioGrupoSentinelaDAO;
import gov.pr.celepar.abi.pojo.Usuario;
import gov.pr.celepar.abi.pojo.UsuarioGrupoSentinela;
import gov.pr.celepar.framework.database.GenericHibernateDAO;
import gov.pr.celepar.framework.database.HibernateUtil;
import gov.pr.celepar.framework.exception.ApplicationException;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class UsuarioGrupoSentinelaHibernateDAO extends GenericHibernateDAO<UsuarioGrupoSentinela, Integer> implements UsuarioGrupoSentinelaDAO {

	private static Logger log4j = Logger.getLogger(UsuarioGrupoSentinelaHibernateDAO.class);

	@Override
	public Collection<UsuarioGrupoSentinela> listar(Integer qtdPagina, Integer numPagina, Usuario usuario) throws ApplicationException {
		try {
			Session session = HibernateUtil.currentSession();
			StringBuffer hql = new StringBuffer();
			hql.append(" FROM UsuarioGrupoSentinela a ")
			.append(" LEFT JOIN FETCH a.usuario usuario ")	
			.append(" LEFT JOIN FETCH a.grupoSentinela grupoSentinela ");	
			hql.append("WHERE 1=1 ");
			
			if (usuario.getCodUsuario() != null && usuario.getCodUsuario() > 0) {
				hql.append("AND a.usuario.codUsuario = :codUsuario ");
			}

			hql.append(" ORDER BY grupoSentinela.descricaoGrupo ");
			
			Query q = session.createQuery(hql.toString());

			if (usuario.getCodUsuario() != null && usuario.getCodUsuario() > 0) {
				q.setInteger("codUsuario", usuario.getCodUsuario());
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
	public UsuarioGrupoSentinela obter(UsuarioGrupoSentinela aux) throws ApplicationException {
		try {
			Session session = HibernateUtil.currentSession();
			StringBuffer hql = new StringBuffer();
			hql.append(" FROM UsuarioGrupoSentinela a ")
			.append(" LEFT JOIN FETCH a.usuario usuario ")	
			.append(" LEFT JOIN FETCH a.grupoSentinela grupoSentinela ");	
			hql.append("WHERE 1=1 ");
			
			if (aux.getUsuario() != null && aux.getUsuario().getCodUsuario() > 0) {
				hql.append("AND a.usuario.codUsuario = :codUsuario ");
			}

			if (aux.getGrupoSentinela() != null && aux.getGrupoSentinela().getCodGrupoSentinela() > 0) {
				hql.append("AND a.grupoSentinela.codGrupoSentinela = :codGrupoSentinela ");
			}


			Query q = session.createQuery(hql.toString());

			if (aux.getUsuario() != null && aux.getUsuario().getCodUsuario() > 0) {
				q.setInteger("codUsuario", aux.getUsuario().getCodUsuario());
			}
			if (aux.getGrupoSentinela() != null && aux.getGrupoSentinela().getCodGrupoSentinela() > 0) {
				q.setInteger("codGrupoSentinela", aux.getGrupoSentinela().getCodGrupoSentinela());
			}

			return (UsuarioGrupoSentinela)q.uniqueResult();

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
	

	public void excluirPorUsuario( Usuario usuario) throws ApplicationException {
		try {
			Session session = HibernateUtil.currentSession();
			StringBuffer hql = new StringBuffer();
			hql.append("DELETE FROM UsuarioGrupoSentinela a ");
			//.append(" LEFT JOIN  a.usuario u ")	;

			hql.append(" WHERE a.usuario.codUsuario = :codUsuario ");
			Query q = session.createQuery(hql.toString());
			q.setInteger("codUsuario", usuario.getCodUsuario());

			
			q.executeUpdate();
			return ;

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
