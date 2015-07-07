package gov.pr.celepar.abi.dao.implementation;

import gov.pr.celepar.abi.dao.OrgaoDAO;
import gov.pr.celepar.abi.dto.ItemComboDTO;
import gov.pr.celepar.abi.pojo.Orgao;
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
 * Classe de manipulação de objetos da classe Orgao.
 * 
 * @author CodeGenerator - Esta classe foi gerada automaticamente
 * @since 1.0
 * @version 1.0, 24/12/2009 
 *
 */
public class OrgaoHibernateDAO extends GenericHibernateDAO<Orgao, Integer> implements OrgaoDAO {
	
	private static Logger log = Logger.getLogger(OrgaoHibernateDAO.class);
	protected static final String LOGERROFECHARSESSAO = "Problema ao tentar fechar sessao do Hibernate";
	protected static final String LOGINICIO = "Abrindo Sessao Hibernate";
	protected static final String LOGFIM = "Fechando Sessao Hibernate";
	
	/**
	 * Obtem o orgao atraves da descricao
	 * @param descOrgao
	 * @return Orgao
	 * @throws ApplicationException 
	 */
	public Orgao obterOrgaoPorDescricao(String descOrgao) throws ApplicationException {
		log.info("Método obterOrgaoPorDescricao processando...");
		
		try {
			Session session = HibernateUtil.currentSession();
			
			// Montando o SQL					
			StringBuffer sql = new StringBuffer();
			sql.append(" FROM Orgao o ");
			sql.append(" WHERE UPPER(o.descricao) LIKE :descOrgao");
			
			
			// Montando Query e fazendo a busca
			Query q = session.createQuery(sql.toString());
			q.setMaxResults(1);
			q.setString("descOrgao", descOrgao.toUpperCase());

			Orgao orgao = (Orgao) q.uniqueResult();
			
			return orgao;

		} catch (HibernateException he) {
			throw new ApplicationException("mensagem.erro.9001", new String[]{"OrgaoHibernateDAO.obterOrgaoPorDescricao()"}, he, ApplicationException.ICON_ERRO);
		}  catch (Exception e) {
			throw new ApplicationException("mensagem.erro.9001", new String[]{"OrgaoHibernateDAO.obterOrgaoPorDescricao()"}, e, ApplicationException.ICON_ERRO);	
		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (Exception e) {
				log.warn(LOGERROFECHARSESSAO);
			}
		}
	}

	public Collection<Orgao> listarByTipoAdm(Integer tipoAdm) throws ApplicationException {
		log.info("Método listarByTipoAdm processando...");
		
		try {
			Session session = HibernateUtil.currentSession();
			
			// Montando o SQL					
			StringBuffer sql = new StringBuffer();
			sql.append("FROM Orgao o ");
			sql.append(" WHERE o.indTipoAdministracao = :indTipoAdministracao ");
			sql.append(" order by o.sigla asc");
			
			// Montando Query e fazendo a busca
			Query q = session.createQuery(sql.toString());
			q.setInteger("indTipoAdministracao", tipoAdm);
			
			Collection <Orgao> listOrgao = q.list();
			
			return listOrgao;

		} catch (HibernateException he) {
			throw new ApplicationException("mensagem.erro.9001", new String[]{"OrgaoHibernateDAO.listarByTipoAdm()"}, he, ApplicationException.ICON_ERRO);
		}  catch (Exception e) {
			throw new ApplicationException("mensagem.erro.9001", new String[]{"OrgaoHibernateDAO.listarByTipoAdm()"}, e, ApplicationException.ICON_ERRO);	
		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (Exception e) {
				log.warn(LOGERROFECHARSESSAO);
			}
		}
	}

	@Override
	public Orgao obterOrgaoPorSigla(String sigla) throws ApplicationException {
		log.info("Método obterOrgaoPorSigla processando...");
		
		try {
			Session session = HibernateUtil.currentSession();
			
			// Montando o SQL					
			StringBuffer sql = new StringBuffer();
			sql.append(" FROM Orgao o ");
			sql.append(" WHERE UPPER(o.sigla) = :sigla");
			
			
			// Montando Query e fazendo a busca
			Query q = session.createQuery(sql.toString());
			q.setMaxResults(1);
			q.setString("sigla", sigla.toUpperCase());

			Orgao orgao = (Orgao) q.uniqueResult();
			
			return orgao;

		} catch (HibernateException he) {
			throw new ApplicationException("mensagem.erro.9001", new String[]{"OrgaoHibernateDAO.obterOrgaoPorSigla()"}, he, ApplicationException.ICON_ERRO);
		}  catch (Exception e) {
			throw new ApplicationException("mensagem.erro.9001", new String[]{"OrgaoHibernateDAO.obterOrgaoPorSigla()"}, e, ApplicationException.ICON_ERRO);	
		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (Exception e) {
				log.warn(LOGERROFECHARSESSAO);
			}
		}
	}

	@Override
	public Collection<Orgao> listarByTipoAdmUsuario(Integer tipoAdm, Integer codUsuario) throws ApplicationException {
		log.info("Método listarByTipoAdmUsuario processando...");
		
		try {
			Session session = HibernateUtil.currentSession();
			
			// Montando o SQL					
			StringBuffer hql = new StringBuffer();
			hql.append(" SELECT gp FROM Orgao gp ")
			.append(" WHERE gp.codOrgao not in (")
			.append(" SELECT ugp.orgao.codOrgao FROM UsuarioOrgao ugp ")
			.append(" where ugp.usuario.codUsuario =:codUsuario )");
			
			// Montando Query e fazendo a busca
			Query q = session.createQuery(hql.toString());
			q.setInteger("codUsuario", codUsuario);
			
			Collection <Orgao> listOrgao = q.list();
			
			return listOrgao;

		} catch (HibernateException he) {
			throw new ApplicationException("mensagem.erro.9001", new String[]{"OrgaoHibernateDAO.listarByTipoAdmUsuario()"}, he, ApplicationException.ICON_ERRO);
		}  catch (Exception e) {
			throw new ApplicationException("mensagem.erro.9001", new String[]{"OrgaoHibernateDAO.listarByTipoAdmUsuario()"}, e, ApplicationException.ICON_ERRO);	
		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (Exception e) {
				log.warn(LOGERROFECHARSESSAO);
			}
		}
	}
	
	
	@SuppressWarnings("unchecked")
	
	private Query montarQueryPesquisa(StringBuffer hql, String sigla, String descricao, Integer codInstituicao , Session session) throws ApplicationException {
		try{
			if(hql==null){
				hql = new StringBuffer();
			}
			hql.append(" FROM Orgao o ")
			.append(" LEFT JOIN FETCH o.instituicao i ");	
			
			hql.append("WHERE 1=1 ");

			if (sigla != null ) {
				hql.append(" AND UPPER(o.sigla) LIKE :sigla ");
			}
			if (descricao != null ) {
				hql.append(" AND UPPER(o.descricao) LIKE :descricao ");
			}
			if (codInstituicao != null ) {
				hql.append(" AND i.codInstituicao = :codInstituicao ");
			}
			hql.append(" ORDER BY o.sigla ");
			Query q = session.createQuery(hql.toString());

			// Seta os valores dos parâmetros
			if (codInstituicao != null ) {
				q.setInteger("codInstituicao", codInstituicao);
			}	
			if (sigla != null ) {
				q.setString("sigla", "%".concat(sigla.toUpperCase()).concat("%"));
			}
			if (descricao != null ) {
				q.setString("descricao", "%".concat(descricao.toUpperCase()).concat("%"));
			}
			return q;

		} catch (HibernateException he) {
			throw new ApplicationException("mensagem.erro", new String[]{"montar Query Pesquisas"}, he);
		} catch (Exception e) {
			throw new ApplicationException("mensagem.erro", new String[]{"montar Query Pesquisas "}, e, ApplicationException.ICON_ERRO);
		} 
	}

	public Collection<Orgao> listar(Integer qtdPagina, Integer numPagina, String sigla, String descricao, Integer codInstituicao) throws ApplicationException {
		try {
			Session session = HibernateUtil.currentSession();
			Query q = montarQueryPesquisa(null, sigla, descricao, codInstituicao, session);
			
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
			}
		}
	}
	
	public Integer obterQuantidadeLista(Integer qtdPagina, Integer numPagina, String sigla, String descricao, Integer codInstituicao) throws ApplicationException {
		try {
			Session session = HibernateUtil.currentSession();
			StringBuffer sb = new StringBuffer();
			sb = sb.append("SELECT distinct(o) ");
			Query q = montarQueryPesquisa(sb, sigla, descricao, codInstituicao, session);
			return q.list().size();
		} catch (HibernateException he) {
			throw new ApplicationException("mensagem.erro.banco.listar", he);
		} catch (Exception e) {
			throw new ApplicationException("mensagem.erro.banco.listar", e, ApplicationException.ICON_ERRO);
		}finally {
			try {
				HibernateUtil.closeSession();
			} catch (Exception e) {
			}
		}
	}
	
	/**
	 * Objetivo Verificar se existe orgao com sigla ou descricao informado.<br>
 	 * @param sigla String
	 * @param denominacao String
	 * @param codOrgao : Integer
	 * @return boolean
	 * @throws ApplicationException
	 */
	public boolean existeSiglaDescricao(String sigla, String descricao, Integer codOrgao, Integer codInstituicao) throws ApplicationException {
		try {
			Session session = HibernateUtil.currentSession();

			StringBuffer sbQuery = new StringBuffer(); 
			sbQuery.append(" SELECT 1 FROM Orgao orgao WHERE 1=1 ");
			sbQuery.append(" AND orgao.instituicao.codInstituicao = :codInstituicao ");
			
			if (sigla != null){
				sbQuery.append(" AND UPPER(TRIM(orgao.sigla)) = :sigla");		
			}
			if (descricao != null){
				sbQuery.append(" AND UPPER(TRIM(orgao.descricao)) = :descricao");		
			}
			
			if (codOrgao != null){
				sbQuery.append(" AND orgao.codOrgao <> :codOrgao");
			}

			Query query = session.createQuery(sbQuery.toString());
			
			query.setInteger("codInstituicao", codInstituicao);
			if (sigla != null){
				query.setString("sigla", sigla.trim().toUpperCase());		
			}
			if (descricao != null){
				query.setString("descricao", descricao.trim().toUpperCase());		
			}
			if (codOrgao != null){
				query.setInteger("codOrgao", codOrgao);
			}

			Integer retorno = (Integer)query.uniqueResult();

			if (retorno != null){
				return true;
			}

			return false;
		} catch (ApplicationException ae) {
			throw ae;
		} catch (Exception e) {
			throw new ApplicationException("mensagem.erro.9001", new String[]{OrgaoHibernateDAO.class.getSimpleName()+".existeSiglaDescricao()"}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				HibernateUtil.closeSession();
			}catch (Exception e) {
			}
		}
	}	
	
	/**
	 * Lista de Orgaos por tipo de Administracao e Instituicao vinculada ao usuario logado.<br>
 	 * @param tipoAdm Integer
	 * @param codUsuarioSentinela Long
	 * @return Collection<ItemComboDTO>
	 * @throws ApplicationException
	 */
	
	public Collection<ItemComboDTO> listarPorTipoAdmEUsuarioSentinela(Integer tipoAdm, Long codUsuarioSentinela) throws ApplicationException {
		log.info("Método listarPorTipoAdmEUsuarioSentinela processando...");
		
		try {
			Session session = HibernateUtil.currentSession();
			
			// Montando o SQL					
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT new gov.pr.celepar.abi.dto.ItemComboDTO (o.codOrgao, o.sigla ||' - ' || o.descricao) ");
			sql.append("FROM Orgao o ");
			sql.append("INNER JOIN o.instituicao i ");
			sql.append("INNER JOIN  i.listaUsuario u ");
			sql.append(" WHERE 1=1 ");
			if (tipoAdm!= null){
				sql.append(" AND o.indTipoAdministracao = :indTipoAdministracao ");
			}
			sql.append(" AND u.idSentinela=:codUsuarioSentinela ");
			sql.append(" order by o.sigla asc");
			
			// Montando Query e fazendo a busca
			Query q = session.createQuery(sql.toString());
			if (tipoAdm!= null){
				q.setInteger("indTipoAdministracao", tipoAdm);
			}
			q.setLong("codUsuarioSentinela", codUsuarioSentinela);
			
			Collection <ItemComboDTO> listOrgao = q.list();
			
			return listOrgao;

		} catch (HibernateException he) {
			throw new ApplicationException("mensagem.erro.9001", new String[]{"OrgaoHibernateDAO.listarPorTipoAdmEUsuarioSentinela()"}, he, ApplicationException.ICON_ERRO);
		}  catch (Exception e) {
			throw new ApplicationException("mensagem.erro.9001", new String[]{"OrgaoHibernateDAO.listarPorTipoAdmEUsuarioSentinela()"}, e, ApplicationException.ICON_ERRO);				
		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (Exception e) {
				log.warn(LOGERROFECHARSESSAO);
			}
		}
	}
	
	
	/**
	 * Lista de Orgaos por tipo de Administracao e Instituicao selecionada.<br>
 	 * @param tipoAdm Integer
	 * @param codInstituicao Integer
	 * @return Collection<ItemComboDTO>
	 * @throws ApplicationException
	 */
	public Collection<ItemComboDTO> listarPorTipoAdmECodInstituicao(Integer tipoAdm, Integer codInstituicao) throws ApplicationException {
		log.info("Método listarPorTipoAdmECodInstituicao processando...");
		
		try {
			Session session = HibernateUtil.currentSession();
			
			// Montando o SQL					
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT new gov.pr.celepar.abi.dto.ItemComboDTO (o.codOrgao, o.sigla ||' - ' || o.descricao) ");
			sql.append("FROM Orgao o ");
			sql.append("INNER JOIN o.instituicao i ");
			sql.append(" WHERE ");
			if (tipoAdm != null)
				sql.append(" o.indTipoAdministracao = :indTipoAdministracao AND ");
			sql.append(" i.codInstituicao=:codInstituicao ");
			sql.append(" order by o.sigla asc");
			
			// Montando Query e fazendo a busca
			Query q = session.createQuery(sql.toString());
			if (tipoAdm != null)
				q.setInteger("indTipoAdministracao", tipoAdm);
			q.setLong("codInstituicao", codInstituicao);
			
			Collection <ItemComboDTO> listOrgao = q.list();
			
			return listOrgao;

		} catch (HibernateException he) {
			throw new ApplicationException("mensagem.erro.9001", new String[]{"OrgaoHibernateDAO.listarPorTipoAdmECodInstituicao()"}, he, ApplicationException.ICON_ERRO);
		}  catch (Exception e) {
			throw new ApplicationException("mensagem.erro.9001", new String[]{"OrgaoHibernateDAO.listarPorTipoAdmECodInstituicao()"}, e, ApplicationException.ICON_ERRO);	
		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (Exception e) {
				log.warn(LOGERROFECHARSESSAO);
			}
		}
	}

	@Override
	public Collection<ItemComboDTO> listarParaAssinaturaPorTipoAdmECodInstituicao(int tipoAdm, Integer codInstituicao) throws ApplicationException {
		log.info("Método listarParaAssinaturaPorTipoAdmECodInstituicao processando...");
		
		try {
			Session session = HibernateUtil.currentSession();
			
			// Montando o SQL					
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT DISTINCT o FROM Orgao o ");
			sql.append("INNER JOIN o.instituicao i ");
			sql.append("INNER JOIN o.listaAssinatura assinatura ");
			sql.append(" WHERE o.indTipoAdministracao = :indTipoAdministracao ");
			sql.append(" AND i.codInstituicao=:codInstituicao ");
			sql.append(" order by o.sigla asc");
			
			// Montando Query e fazendo a busca
			Query q = session.createQuery(sql.toString());
			q.setInteger("indTipoAdministracao", tipoAdm);
			q.setLong("codInstituicao", codInstituicao);
			
			Collection <Orgao> list = q.list();
			Collection <ItemComboDTO> listOrgao = new ArrayList<ItemComboDTO>();
			
			for (Orgao o : list) {
				ItemComboDTO dto = new ItemComboDTO();
				dto.setCodigo(o.getCodOrgao().toString());
				dto.setDescricao(o.getSiglaDescricao());
				listOrgao.add(dto);
			}
			
			return listOrgao;

		} catch (HibernateException he) {
			throw new ApplicationException("mensagem.erro.9001", new String[]{"OrgaoHibernateDAO.listarParaAssinaturaPorTipoAdmECodInstituicao()"}, he, ApplicationException.ICON_ERRO);
		}  catch (Exception e) {
			throw new ApplicationException("mensagem.erro.9001", new String[]{"OrgaoHibernateDAO.listarParaAssinaturaPorTipoAdmECodInstituicao()"}, e, ApplicationException.ICON_ERRO);	
		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (Exception e) {
				log.warn(LOGERROFECHARSESSAO);
			}
		}
	}

}
