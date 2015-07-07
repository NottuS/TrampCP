package gov.pr.celepar.abi.dao.implementation;

import gov.pr.celepar.abi.dao.GrupoSentinelaDAO;
import gov.pr.celepar.abi.pojo.GrupoSentinela;
import gov.pr.celepar.framework.database.GenericHibernateDAO;
import gov.pr.celepar.framework.database.HibernateUtil;
import gov.pr.celepar.framework.exception.ApplicationException;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class GrupoSentinelaHibernateDAO extends GenericHibernateDAO<GrupoSentinela, Integer> implements GrupoSentinelaDAO {

	private static Logger log4j = Logger.getLogger(GrupoSentinelaHibernateDAO.class);

	@Override
	public GrupoSentinela obterByDescSentinela(String grupo) throws ApplicationException {
		try {
			Session session = HibernateUtil.currentSession();
						
			StringBuffer hql = new StringBuffer();
			hql.append(" FROM GrupoSentinela gp ")
			.append(" WHERE gp.descricaoSentinela = :descricaoSentinela ");

			Query q = session.createQuery(hql.toString());
			q.setString("descricaoSentinela", grupo);
			
			GrupoSentinela retorno = (GrupoSentinela)q.uniqueResult();
			
			return retorno;
		} catch(HibernateException he) {
			throw new ApplicationException("mensagem.erro", new String[]{"obterByDescSentinela"}, he);
		} catch(Exception e) {
			throw new ApplicationException("mensagem.erro", new String[]{"obterByDescSentinela"}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				HibernateUtil.closeSession();
			} catch(Exception e) {
				log4j.error("Erro ao Fechar Conexao com o Hibernate: ", e);				
			}
		}
	}

	@Override
	public Collection<GrupoSentinela> listarByUsuario(Integer codUsuario) throws ApplicationException {
		try {
			Session session = HibernateUtil.currentSession();
						
			StringBuffer hql = new StringBuffer();
			hql.append(" SELECT gp FROM GrupoSentinela gp ")
			.append(" WHERE gp.codGrupoSentinela not in (")
			.append(" SELECT ugp.grupoSentinela.codGrupoSentinela FROM UsuarioGrupoSentinela ugp ")
			.append(" where ugp.usuario.codUsuario =:codUsuario )");

			Query q = session.createQuery(hql.toString());
			q.setInteger("codUsuario", codUsuario);
			
			return q.list();
			
		} catch(HibernateException he) {
			throw new ApplicationException("mensagem.erro", new String[]{"listarByUsuario"}, he);
		} catch(Exception e) {
			throw new ApplicationException("mensagem.erro", new String[]{"listarByUsuario"}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				HibernateUtil.closeSession();
			} catch(Exception e) {
				log4j.error("Erro ao Fechar Conexao com o Hibernate: ", e);				
			}
		}
	}

}
