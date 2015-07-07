package gov.pr.celepar.abi.dao.implementation;

import gov.pr.celepar.abi.dao.InstituicaoDAO;
import gov.pr.celepar.abi.pojo.Instituicao;
import gov.pr.celepar.framework.database.GenericHibernateDAO;
import gov.pr.celepar.framework.database.HibernateUtil;
import gov.pr.celepar.framework.exception.ApplicationException;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Classe de manipulação de objetos da classe Instituicao.
 * 
 * @author CodeGenerator - Esta classe foi gerada automaticamente
 * @since 1.0
 * @version 1.0, 24/12/2009 
 *
 */
public class InstituicaoHibernateDAO extends GenericHibernateDAO<Instituicao, Integer> implements InstituicaoDAO {

	
	private static Logger log = Logger.getLogger(InstituicaoHibernateDAO.class);
	protected static final String LOGERROFECHARSESSAO = "Problema ao tentar fechar sessao do Hibernate";
	protected static final String LOGINICIO = "Abrindo Sessao Hibernate";
	protected static final String LOGFIM = "Fechando Sessao Hibernate";

	@Override
	public Instituicao obterByCodUsuarioSentinela(Long codUsuarioSentinela) throws ApplicationException {
		log.info("Método obterByCodUsuarioSentinela processando...");
		try {
			Session session = HibernateUtil.currentSession();
						
			StringBuffer hql = new StringBuffer();
			hql.append(" select instituicao FROM Instituicao instituicao ")
			.append(" INNER JOIN instituicao.listaUsuario u ")
			.append(" WHERE u.idSentinela = :idSentinela ")
			.append(" AND u.situacao = 1 ");

			Query q = session.createQuery(hql.toString());
			q.setLong("idSentinela", codUsuarioSentinela);
			
			Instituicao retorno = (Instituicao)q.uniqueResult();
			
			return retorno;
		} catch(HibernateException he) {
			throw new ApplicationException("mensagem.erro", new String[]{"obterByCodUsuarioSentinela"}, he);
		} catch(Exception e) {
			throw new ApplicationException("mensagem.erro", new String[]{"obterByCodUsuarioSentinela"}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				HibernateUtil.closeSession();
			} catch(Exception e) {
				log.error("Erro ao Fechar Conexao com o Hibernate: ", e);				
			}
		}
	}
	
	
	
}
