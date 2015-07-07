package gov.pr.celepar.abi.dao.implementation;

import gov.pr.celepar.abi.dao.ParametroVistoriaDAO;
import gov.pr.celepar.abi.dto.ParametroVistoriaDTO;
import gov.pr.celepar.abi.pojo.ParametroVistoria;
import gov.pr.celepar.framework.database.GenericHibernateDAO;
import gov.pr.celepar.framework.database.HibernateUtil;
import gov.pr.celepar.framework.exception.ApplicationException;

import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Classe de manipulacao de objetos da classe ParametroVistoria.
 * 

 *
 */
public class ParametroVistoriaHibernateDAO extends GenericHibernateDAO<ParametroVistoria, Integer> implements ParametroVistoriaDAO {

	private static final Logger log4j = Logger.getLogger(ParametroVistoriaDAO.class);
	protected static final String LOGINICIO = "Abrindo Sessao Hibernate";
	protected static final String LOGFIM = "Fechando Sessao Hibernate";
	protected static final String LOGERROFECHARSESSAO = "Problema ao tentar fechar sessao do Hibernate";
	
	@SuppressWarnings("unchecked")

	public Collection<ParametroVistoria> listar(ParametroVistoriaDTO parametroVistoriaDTO ) throws ApplicationException {
		try {
			Session session = HibernateUtil.currentSession();
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append(" SELECT distinct parametroVistoria FROM ParametroVistoria parametroVistoria ");
			sbQuery.append(" LEFT JOIN FETCH parametroVistoria.listaParametroVistoriaDominio parametroVistoriaDominio ");
			sbQuery.append(" LEFT JOIN FETCH parametroVistoria.listaParametroVistoriaDenominacaoImovel parametroVistoriaDenominacaoImovel ");
			sbQuery.append(" LEFT JOIN FETCH parametroVistoria.listaItemVistoria itemVistoria ");
			sbQuery.append(" LEFT JOIN FETCH parametroVistoria.instituicao instituicao ");
			sbQuery.append(" LEFT JOIN FETCH parametroVistoriaDenominacaoImovel.denominacaoImovel denominacaoImovel ");
			sbQuery.append(" WHERE 1=1");
			if (parametroVistoriaDTO.getDescricao() != null){
				sbQuery.append(" AND UPPER(parametroVistoria.descricao) LIKE '%' ||:descricao|| '%' ");
			}
			if (parametroVistoriaDTO.getCodDenominacaoImovel() != null){
				sbQuery.append(" AND (denominacaoImovel.codDenominacaoImovel = :codDenominacaoImovel)");
			} 
			if (parametroVistoriaDTO.getIndAtivo() != null && !parametroVistoriaDTO.getIndAtivo().equals(3)){
				sbQuery.append(" AND (parametroVistoria.indAtivo = :indAtivo)");	
			} 
			if (parametroVistoriaDTO.getCodInstituicao() != null){
				sbQuery.append(" AND (parametroVistoria.instituicao.codInstituicao = :codInstituicao)");
			}
			sbQuery.append(" ORDER BY instituicao.sigla, parametroVistoria.descricao ");
			
			Query query = session.createQuery(sbQuery.toString());
			
			if (parametroVistoriaDTO.getDescricao() != null){
				query.setString("descricao", parametroVistoriaDTO.getDescricao().toUpperCase());	
			}
			if (parametroVistoriaDTO.getCodDenominacaoImovel() != null){
				query.setInteger("codDenominacaoImovel", parametroVistoriaDTO.getCodDenominacaoImovel());	
			}
			if (parametroVistoriaDTO.getIndAtivo() != null && !parametroVistoriaDTO.getIndAtivo().equals(3)){
				if (parametroVistoriaDTO.getIndAtivo().equals(1)){ //ativo
					query.setBoolean("indAtivo", true);	
				}
				if (parametroVistoriaDTO.getIndAtivo().equals(2)){ //inativo
					query.setBoolean("indAtivo", false);
				}	
			}
				
			if (parametroVistoriaDTO.getCodInstituicao() != null){
				query.setInteger("codInstituicao", parametroVistoriaDTO.getCodInstituicao());	
			}
			return (List<ParametroVistoria>)query.list();
			

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
	
	public List<ParametroVistoria> listarParametroVistoriaComDenominacaoBemImovelExceto(Integer codInstituicao, Integer codDenomincaoImovel,  List<Integer> listaCodParametroVistoria) throws ApplicationException {
		try { 
			Session session = HibernateUtil.currentSession();
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append(" SELECT distinct parametroVistoria FROM ParametroVistoria parametroVistoria ");
			sbQuery.append(" LEFT JOIN FETCH parametroVistoria.listaParametroVistoriaDominio parametroVistoriaDominio ");
			sbQuery.append(" LEFT JOIN FETCH parametroVistoria.listaParametroVistoriaDenominacaoImovel parametroVistoriaDenominacaoImovel ");
			sbQuery.append(" LEFT JOIN FETCH parametroVistoriaDenominacaoImovel.denominacaoImovel denominacaoImovel ");
			sbQuery.append(" WHERE parametroVistoria.indAtivo = TRUE ");
			sbQuery.append(" AND parametroVistoria.instituicao.codInstituicao = :codInstituicao AND denominacaoImovel.codDenominacaoImovel = :codDenominacaoImovel");
			if (listaCodParametroVistoria != null && !listaCodParametroVistoria.isEmpty()) {
				sbQuery.append(" AND parametroVistoria.codParametroVistoria NOT IN (:lista)");
			}
			
			sbQuery.append(" ORDER BY parametroVistoria.ordemApresentacao, parametroVistoria.descricao ");
			
			Query query = session.createQuery(sbQuery.toString());
			
			query.setInteger("codInstituicao", codInstituicao);
			query.setInteger("codDenominacaoImovel", codDenomincaoImovel);
			
			if (listaCodParametroVistoria != null && !listaCodParametroVistoria.isEmpty()) {
				query.setParameterList("lista", listaCodParametroVistoria);
			}
			
			return (List<ParametroVistoria>)query.list();
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
	
	public List<ParametroVistoria> listarParametroVistoriaComDenominacaoBemImovel(Integer codDenominacaoImovel) throws ApplicationException {
		try {
			Session session = HibernateUtil.currentSession();
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append(" SELECT distinct parametroVistoria FROM ParametroVistoria parametroVistoria ");
			sbQuery.append(" LEFT JOIN FETCH parametroVistoria.listaParametroVistoriaDominio parametroVistoriaDominio ");
			sbQuery.append(" LEFT JOIN FETCH parametroVistoria.listaParametroVistoriaDenominacaoImovel parametroVistoriaDenominacaoImovel ");
			sbQuery.append(" LEFT JOIN FETCH parametroVistoriaDenominacaoImovel.denominacaoImovel denominacaoImovel ");
			sbQuery.append(" LEFT JOIN FETCH denominacaoImovel.bemImovels bemImovel ");
			sbQuery.append(" WHERE parametroVistoria.indAtivo = TRUE AND denominacaoImovel.codDenominacaoImovel = :codDenominacaoImovel ");
			sbQuery.append(" ORDER BY parametroVistoria.ordemApresentacao, parametroVistoria.descricao ");
			
			Query query = session.createQuery(sbQuery.toString());
			
			query.setInteger("codDenominacaoImovel", codDenominacaoImovel);
			
			return (List<ParametroVistoria>)query.list();
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

	

	public Boolean existeDescricao(Integer codParametroVistoria, String descricao, Integer codInstituicao) throws ApplicationException {
		try {
			Session session = HibernateUtil.currentSession();
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append(" SELECT distinct parametroVistoria FROM ParametroVistoria parametroVistoria ");
			sbQuery.append(" WHERE 1=1");
			if (codParametroVistoria != null){
				sbQuery.append(" AND parametroVistoria.codParametroVistoria <> :codParametroVistoria ");
			}
			sbQuery.append(" AND parametroVistoria.descricao = :descricao");
			sbQuery.append(" AND parametroVistoria.instituicao.codInstituicao = :codInstituicao)");
			
			Query query = session.createQuery(sbQuery.toString());
			
			if (codParametroVistoria != null){
				query.setInteger("codParametroVistoria", codParametroVistoria);	
			}
			query.setString("descricao", descricao);
			query.setInteger("codInstituicao", codInstituicao);
				
			List<ParametroVistoria> list = (List<ParametroVistoria>) query.list();

			if (list.size() > 0){
				return true;
			}else{
				return false;
			}
			

		} catch (HibernateException he) {
			throw new ApplicationException("mensagem.erro.banco.existeDescricao", he);
		} catch (Exception e) {
			throw new ApplicationException("mensagem.erro.banco.existeDescricao", e, ApplicationException.ICON_ERRO);
		}finally {
			try {
				HibernateUtil.closeSession();
			} catch (Exception e) {
				log4j.error("Erro ao Fechar Conexao com o Hibernate: ", e);				
			}
		}
	}

	/**
	 * Fazer o merge dos objetos.<br>
	 * @param ParametroVistoria : ParametroVistoria
	 * @return ParametroVistoria
	 * @throws ApplicationException
	 */	
	public ParametroVistoria merge(ParametroVistoria parametroVistoria) throws ApplicationException {
		try{
			Session session = HibernateUtil.currentSession();
			parametroVistoria = (ParametroVistoria) session.merge(parametroVistoria);
			return parametroVistoria;
		}catch (ApplicationException ae) {
			log4j.debug(ae.getMessage(),ae);
			throw ae;
		}catch (Exception e) {
			log4j.error(e.getMessage());
			throw new ApplicationException("mensagem.erro.9001", new String[]{ParametroVistoriaHibernateDAO.class.getSimpleName().concat(".merge()")}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				HibernateUtil.closeSession();	
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.9003", new String[]{parametroVistoria.getClass().getSimpleName()}, ApplicationException.ICON_AVISO);
			}
		}
	}

}
