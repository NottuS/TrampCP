package gov.pr.celepar.abi.dao.implementation;

import gov.pr.celepar.abi.dao.VistoriadorDAO;
import gov.pr.celepar.abi.pojo.Vistoriador;
import gov.pr.celepar.framework.database.GenericHibernateDAO;
import gov.pr.celepar.framework.database.HibernateUtil;
import gov.pr.celepar.framework.exception.ApplicationException;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class VistoriadorHibernateDAO extends GenericHibernateDAO<Vistoriador, Integer> implements VistoriadorDAO {
	
	private static final Logger log4j = Logger.getLogger(VistoriadorDAO.class);
	protected static final String LOGINICIO = "Abrindo Sessao Hibernate";
	protected static final String LOGFIM = "Fechando Sessao Hibernate";
	protected static final String LOGERROFECHARSESSAO = "Problema ao tentar fechar sessao do Hibernate";
	
	public Collection<Vistoriador> listar(Integer qtdPagina, Integer numPagina, String cpf, String nome) throws ApplicationException {
		try {
			Session session = HibernateUtil.currentSession();
			StringBuffer hql = new StringBuffer();
			hql.append(" FROM Vistoriador v ");
			hql.append("WHERE 1=1 ");
			
			if (cpf != null && cpf.length() > 0) {
				hql.append("AND v.cpf = :cpf ");
			}
			if (nome != null && nome.length() > 0) {
				hql.append("AND UPPER(v.nome) LIKE :nome ");
			}

			hql.append(" ORDER BY v.instituicao.codInstituicao, v.codVistoriador ");
			Query q = session.createQuery(hql.toString());

			if (cpf != null && cpf.length() > 0) {
				q.setString("cpf", cpf );
			}	
			if (nome != null && nome.length() > 0) {
				q.setString("nome", "%" + nome.toUpperCase() + "%");
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
	/**
	 * Objetivo Verificar se existe vistoriador duplicado.<br>
 	 * @param sigla String
	 * @param denominacao String
	 * @param codOrgao : Integer
	 * @return boolean
	 * @throws ApplicationException
	 */
	public boolean existeVistoriador(String cpf, Integer codInstituicao) throws ApplicationException {
		try {
			Session session = HibernateUtil.currentSession();

			StringBuffer sbQuery = new StringBuffer(); 
			sbQuery.append(" SELECT 1 FROM Vistoriador vistoriador WHERE 1=1 ");
			sbQuery.append(" AND vistoriador.instituicao.codInstituicao = :codInstituicao ");
			
			if (cpf != null){
				sbQuery.append(" AND UPPER(TRIM(vistoriador.cpf)) = :cpf");		
			}

			Query query = session.createQuery(sbQuery.toString());
			
			query.setInteger("codInstituicao", codInstituicao);
			if (cpf != null){
				query.setString("cpf", cpf.trim().toUpperCase());		
			}

			Integer retorno = (Integer)query.uniqueResult();

			if (retorno != null){
				return true;
			}

			return false;
		} catch (ApplicationException ae) {
			throw ae;
		} catch (Exception e) {
			throw new ApplicationException("mensagem.erro.9001", new String[]{OrgaoHibernateDAO.class.getSimpleName()+".existeVistoriador()"}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				HibernateUtil.closeSession();
			}catch (Exception e) {
			}
		}
	}	

	private Query montarQueryPesquisa(StringBuffer hql, String cpf, String nome, Integer codInstituicao , Session session) throws ApplicationException {
		try{
			if(hql==null){
				hql = new StringBuffer();
			}
			hql.append(" FROM Vistoriador v ")
			.append(" LEFT JOIN FETCH v.instituicao i ");	
			
			hql.append("WHERE 1=1 ");

			if (cpf != null ) {
				hql.append(" AND UPPER(v.cpf) = :cpf ");
			}
			if (nome != null ) {
				hql.append(" AND UPPER(v.nome) LIKE :nome ");
			}
			if (codInstituicao != null ) {
				hql.append(" AND i.codInstituicao = :codInstituicao ");
			}
			hql.append(" ORDER BY v.instituicao.codInstituicao, v.nome ");
			Query q = session.createQuery(hql.toString());

			// Seta os valores dos parâmetros
			if (codInstituicao != null ) {
				q.setInteger("codInstituicao", codInstituicao);
			}	
			if (cpf != null ) {
				q.setString("cpf", cpf.toUpperCase());
			}
			if (nome != null ) {
				q.setString("nome", "%".concat(nome.toUpperCase()).concat("%"));
			}
			return q;

		} catch (HibernateException he) {
			throw new ApplicationException("mensagem.erro", new String[]{"montar Query Pesquisas"}, he);
		} catch (Exception e) {
			throw new ApplicationException("mensagem.erro", new String[]{"montar Query Pesquisas "}, e, ApplicationException.ICON_ERRO);
		} 
	}

	public Collection<Vistoriador> listar(Integer qtdPagina, Integer numPagina, String cpf, String nome, Integer codInstituicao) throws ApplicationException {
		try {
			Session session = HibernateUtil.currentSession();
			Query q = montarQueryPesquisa(null, cpf, nome, codInstituicao, session);
			
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
	
	public Integer obterQuantidadeLista(Integer qtdPagina, Integer numPagina, String cpf, String nome, Integer codInstituicao) throws ApplicationException {
		try {
			Session session = HibernateUtil.currentSession();
			StringBuffer sb = new StringBuffer();
			sb = sb.append("SELECT distinct(v) ");
			Query q = montarQueryPesquisa(sb, cpf, nome, codInstituicao, session);
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
	
}
