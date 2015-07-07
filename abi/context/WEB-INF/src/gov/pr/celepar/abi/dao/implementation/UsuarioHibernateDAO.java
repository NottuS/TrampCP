package gov.pr.celepar.abi.dao.implementation;

import gov.pr.celepar.abi.dao.UsuarioDAO;
import gov.pr.celepar.abi.pojo.Usuario;
import gov.pr.celepar.framework.database.GenericHibernateDAO;
import gov.pr.celepar.framework.database.HibernateUtil;
import gov.pr.celepar.framework.exception.ApplicationException;

import java.io.Serializable;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

/**
 * Classe de manipulação de objetos da classe Usuario.
 * 
 * @author CodeGenerator - Esta classe foi gerada automaticamente
 * @since 1.0
 * @version 1.0, 24/12/2009 
 *
 */
public class UsuarioHibernateDAO extends GenericHibernateDAO<Usuario, Integer> implements UsuarioDAO {

	private static Logger log4j = Logger.getLogger(UsuarioHibernateDAO.class);
	
	public Usuario obterPorIdSentinela(Long idSentinela) throws ApplicationException {
		try {
			Session session = HibernateUtil.currentSession();
						
			StringBuffer hql = new StringBuffer();
			hql.append(" FROM Usuario usuario ")
			.append(" LEFT JOIN FETCH usuario.listaUsuarioOrgao listaUsuarioOrgao ")
			.append(" LEFT JOIN FETCH listaUsuarioOrgao.orgao orgao ")
			.append(" LEFT JOIN FETCH usuario.instituicao ")
			.append(" WHERE usuario.idSentinela = :idSentinela ")
			.append(" AND usuario.situacao = 1 ");

			Query q = session.createQuery(hql.toString());
			q.setLong("idSentinela", idSentinela);
			
			Usuario retorno = (Usuario)q.uniqueResult();
			
			return retorno;
		} catch(HibernateException he) {
			throw new ApplicationException("mensagem.erro", new String[]{"obterPorIdSentinela"}, he);
		} catch(Exception e) {
			throw new ApplicationException("mensagem.erro", new String[]{"obterPorIdSentinela"}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				HibernateUtil.closeSession();
			} catch(Exception e) {
				log4j.error("Erro ao Fechar Conexao com o Hibernate: ", e);				
			}
		}
	}

	@Override
	public Collection<Usuario> listar(Integer qtdPagina, Integer numPagina, Usuario objPesquisa) throws ApplicationException {

		try {
			Session session = HibernateUtil.currentSession();
			StringBuffer hql = new StringBuffer();
			hql.append(" FROM Usuario a ")
			.append(" LEFT JOIN FETCH a.listaUsuarioOrgao listaUsuarioOrgao ")	
			.append(" LEFT JOIN FETCH listaUsuarioOrgao.orgao orgao ")	
			.append(" LEFT JOIN FETCH a.listaUsuarioGrupoSentinela listaUsuarioGrupoSentinela ")	
			.append(" LEFT JOIN FETCH listaUsuarioGrupoSentinela.grupoSentinela grupoSentinela ")
			.append(" LEFT JOIN FETCH a.instituicao i ")	;
			hql.append("WHERE 1=1 ");
			
			if (objPesquisa.getListaUsuarioGrupoSentinela() != null && objPesquisa.getListaUsuarioGrupoSentinela().size() > 0) {
				hql.append("AND listaUsuarioGrupoSentinela.grupoSentinela.descricaoSentinela = :descricaoSentinela ");
			}

			if (objPesquisa.getListaUsuarioOrgao() != null && objPesquisa.getListaUsuarioOrgao().size() > 0) {
				hql.append("AND orgao.codOrgao = :codOrgao ");
			}

			if (objPesquisa.getCpf() != null && objPesquisa.getCpf().trim().length() > 0) {
				hql.append("AND a.cpf = :cpf ");
			}

			if (objPesquisa.getNome() != null && objPesquisa.getNome().trim().length() > 0) {
				hql.append("AND UPPER(a.nome) LIKE :nome ");
			}

			if (objPesquisa.getSituacao() != null && objPesquisa.getSituacao() > 0) {
				hql.append("AND a.situacao = :situacao ");
			}
			if (objPesquisa.getInstituicao() != null ) {
				hql.append("AND i.codInstituicao = :codInstituicao ");
			}

			hql.append(" ORDER BY a.nome ");
			
			Query q = session.createQuery(hql.toString());

			if (objPesquisa.getListaUsuarioGrupoSentinela() != null && objPesquisa.getListaUsuarioGrupoSentinela().size() > 0) {
				q.setString("descricaoSentinela", objPesquisa.getListaUsuarioGrupoSentinela().iterator().next().getGrupoSentinela().getDescricaoSentinela());
			}

			if (objPesquisa.getListaUsuarioOrgao() != null && objPesquisa.getListaUsuarioOrgao().size() > 0) {
				q.setInteger("codOrgao", objPesquisa.getListaUsuarioOrgao().iterator().next().getOrgao().getCodOrgao());
			}

			if (objPesquisa.getCpf() != null && objPesquisa.getCpf().trim().length() > 0) {
				q.setString("cpf", objPesquisa.getCpf());
			}

			if (objPesquisa.getNome() != null && objPesquisa.getNome().trim().length() > 0) {
				q.setString("nome", objPesquisa.getNome());
			}

			if (objPesquisa.getSituacao() != null && objPesquisa.getSituacao() > 0) {
				q.setInteger("situacao", objPesquisa.getSituacao());
			}
			if (objPesquisa.getInstituicao() != null ) {
				q.setInteger("codInstituicao", objPesquisa.getInstituicao().getCodInstituicao());
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
	public Usuario salvarUsuario(Usuario usuario) throws ApplicationException {
		Usuario retorno = new Usuario();
		// Validando parâmetro
		if (usuario == null){
			throw new ApplicationException("mensagem.erro.9002", new String[]{"UsuarioHibernateDAO.salvarUsuario()"}, ApplicationException.ICON_ERRO);
		}
		
		try {
			log4j.debug("Obtendo sessao corrente...");
			Session session = HibernateUtil.currentSession();
			session.clear();
			Serializable chave = session.save(usuario);
			session.flush();
			log4j.info("SALVAR:" + usuario.getClass().getName() + " salvo.");
			retorno = (Usuario) session.get(Usuario.class, chave);
			log4j.info("GET:" + usuario.getClass().getName() + " q acabou de ser salvo.");
						
		} catch (ConstraintViolationException cve) {
			log4j.error("UsuarioHibernateDAO.salvarUsuario()" + cve);
			throw new ApplicationException("mensagem.erro.9003", new String[]{usuario.getClass().getSimpleName()}, ApplicationException.ICON_AVISO);
		} catch (HibernateException he) {
			log4j.error("UsuarioHibernateDAO.salvarUsuario()" + he);
			throw new ApplicationException("mensagem.erro.9001", new String[]{"UsuarioHibernateDAO.salvarUsuario()"}, he, ApplicationException.ICON_ERRO);
		} catch (Exception e) {
			log4j.error("UsuarioHibernateDAO.salvarUsuario()" + e);
			throw new ApplicationException("mensagem.erro.9001",new String[]{"UsuarioHibernateDAO.salvarUsuario()"}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				log4j.debug("Fechando sessao corrente.");
				HibernateUtil.closeSession();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.9003", new String[]{usuario.getClass().getSimpleName()}, ApplicationException.ICON_AVISO);
			}
		}
		
		return retorno;
	}

	@Override
	public Usuario obterByCPF(String cpf) throws ApplicationException {
		try {
			Session session = HibernateUtil.currentSession();
						
			StringBuffer hql = new StringBuffer();
			hql.append(" FROM Usuario usuario ")
			.append(" LEFT JOIN FETCH usuario.listaUsuarioOrgao listaUsuarioOrgao ")
			.append(" LEFT JOIN FETCH listaUsuarioOrgao.orgao orgao ")
			.append(" WHERE usuario.cpf = :cpf ")
			.append(" AND usuario.situacao = 1 ");

			Query q = session.createQuery(hql.toString());
			q.setString("cpf", cpf);
			
			Usuario retorno = (Usuario)q.uniqueResult();
			
			return retorno;
		} catch(HibernateException he) {
			throw new ApplicationException("mensagem.erro", new String[]{"obterByCPF"}, he);
		} catch(Exception e) {
			throw new ApplicationException("mensagem.erro", new String[]{"obterByCPF"}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				HibernateUtil.closeSession();
			} catch(Exception e) {
				log4j.error("Erro ao Fechar Conexao com o Hibernate: ", e);				
			}
		}
	}

	@Override
	public Usuario obterCompleto(Integer codUsuario) throws ApplicationException {
		try {
			Session session = HibernateUtil.currentSession();
						
			StringBuffer hql = new StringBuffer();
			hql.append(" FROM Usuario usuario ")
			.append(" LEFT JOIN FETCH usuario.listaUsuarioOrgao listaUsuarioOrgao ")
			.append(" LEFT JOIN FETCH listaUsuarioOrgao.orgao orgao ")
			.append(" LEFT JOIN FETCH usuario.listaUsuarioGrupoSentinela listaUsuarioGrupoSentinela ")
			.append(" LEFT JOIN FETCH listaUsuarioGrupoSentinela.grupoSentinela grupoSentinela ")
			.append(" WHERE usuario.codUsuario = :codUsuario ");

			Query q = session.createQuery(hql.toString());
			q.setLong("codUsuario", codUsuario);
			
			Usuario retorno = (Usuario)q.uniqueResult();
			
			return retorno;
		} catch(HibernateException he) {
			throw new ApplicationException("mensagem.erro", new String[]{"obterPorIdSentinela"}, he);
		} catch(Exception e) {
			throw new ApplicationException("mensagem.erro", new String[]{"obterPorIdSentinela"}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				HibernateUtil.closeSession();
			} catch(Exception e) {
				log4j.error("Erro ao Fechar Conexao com o Hibernate: ", e);				
			}
		}
	}

}
