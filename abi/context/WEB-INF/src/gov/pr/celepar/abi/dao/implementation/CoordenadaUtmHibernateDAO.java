package gov.pr.celepar.abi.dao.implementation;

import gov.pr.celepar.abi.dao.CoordenadaUtmDAO;
import gov.pr.celepar.abi.dto.CoordenadaUtmExibirBemImovelDTO;
import gov.pr.celepar.abi.pojo.CoordenadaUtm;
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
import org.hibernate.transform.Transformers;

/**
 * Classe de manipulação de objetos da classe CoordenadaUtm.
 * 
 * @author CodeGenerator - Esta classe foi gerada automaticamente
 * @since 1.0
 * @version 1.0, 24/12/2009 
 *
 */
public class CoordenadaUtmHibernateDAO extends GenericHibernateDAO<CoordenadaUtm, Integer> implements CoordenadaUtmDAO {

	private static Logger log4j = Logger.getLogger(CoordenadaUtmHibernateDAO.class);
	
	@SuppressWarnings("unchecked")
	public Collection<CoordenadaUtmExibirBemImovelDTO> listarPorBemImovelExibir(Integer codBemImovel) throws ApplicationException {
		Collection<CoordenadaUtmExibirBemImovelDTO> coll = null;


		try {
			Session session = HibernateUtil.currentSession();
			StringBuffer hql = new StringBuffer();
			hql.append(" SELECT c.coordenadaX as coordenadaX, c.coordenadaY as coordenadaY ")
			.append(" FROM CoordenadaUtm c ")
			.append(" WHERE c.bemImovel.codBemImovel = :codBemImovel")
			.append(" ORDER BY c.coordenadaX, c.coordenadaY ");

			Query q = session.createQuery(hql.toString());
			q.setInteger("codBemImovel", codBemImovel);
			q.setResultTransformer(Transformers.aliasToBean(CoordenadaUtmExibirBemImovelDTO.class));
			coll = q.list();				

		} catch (HibernateException he) {
			log4j.error("Erro:", he);
			throw new ApplicationException("ERRO.201",  new String[]{"ao listar Coordenada Utm"});
		} catch (Exception e) {
			log4j.error("Erro:", e);
			throw new ApplicationException("ERRO.201",  new String[]{"ao listar Coordenada Utm"}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				HibernateUtil.closeSession();
			}catch (Exception e) {
				log4j.error("Problema ao tentar fechar conexao com o banco de dados: listarCoordenadaUtm", e);
			}		
		}

		return coll;
	}

	@Override
	public CoordenadaUtm salvarCoordenadaUtm(CoordenadaUtm coordenadaUtm) throws ApplicationException {
		CoordenadaUtm retorno = new CoordenadaUtm();
		// Validando parâmetro
		if (coordenadaUtm == null){
			throw new ApplicationException("mensagem.erro.9002", new String[]{"CoordenadaUtmHibernateDAO.salvarCoordenadaUtm()"}, ApplicationException.ICON_ERRO);
		}
		
		try {
			log4j.debug("Obtendo sessao corrente...");
			Session session = HibernateUtil.currentSession();
			session.clear();
			Serializable chave = session.save(coordenadaUtm);
			session.flush();
			log4j.info("SALVAR:" + coordenadaUtm.getClass().getName() + " salvo.");
			retorno = (CoordenadaUtm) session.get(CoordenadaUtm.class, chave);
			log4j.info("GET:" + coordenadaUtm.getClass().getName() + " q acabou de ser salvo.");
						
		} catch (ConstraintViolationException cve) {
			log4j.error("CoordenadaUtmHibernateDAO.salvarCoordenadaUtm()" + cve);
			throw new ApplicationException("mensagem.erro.9003", new String[]{coordenadaUtm.getClass().getSimpleName()}, ApplicationException.ICON_AVISO);
		} catch (HibernateException he) {
			log4j.error("CoordenadaUtmHibernateDAO.salvarCoordenadaUtm()" + he);
			throw new ApplicationException("mensagem.erro.9001", new String[]{"CoordenadaUtmHibernateDAO.salvarCoordenadaUtm()"}, he, ApplicationException.ICON_ERRO);
		} catch (Exception e) {
			log4j.error("CoordenadaUtmHibernateDAO.salvarCoordenadaUtm()" + e);
			throw new ApplicationException("mensagem.erro.9001",new String[]{"CoordenadaUtmHibernateDAO.salvarCoordenadaUtm()"}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				log4j.debug("Fechando sessao corrente.");
				HibernateUtil.closeSession();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.9003", new String[]{coordenadaUtm.getClass().getSimpleName()}, ApplicationException.ICON_AVISO);
			}
		}
		
		return retorno;
	}
	 

}
