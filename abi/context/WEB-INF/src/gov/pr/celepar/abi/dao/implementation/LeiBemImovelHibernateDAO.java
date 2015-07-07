package gov.pr.celepar.abi.dao.implementation;

import gov.pr.celepar.abi.dao.LeiBemImovelDAO;
import gov.pr.celepar.abi.dto.LeiBemImovelExibirBemImovelDTO;
import gov.pr.celepar.abi.pojo.LeiBemImovel;
import gov.pr.celepar.framework.database.GenericHibernateDAO;
import gov.pr.celepar.framework.database.HibernateUtil;
import gov.pr.celepar.framework.exception.ApplicationException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.transform.Transformers;

/**
 * Classe de manipulação de objetos da classe LeiBemImovel.
 * 
 * @author CodeGenerator - Esta classe foi gerada automaticamente
 * @since 1.0
 * @version 1.0, 24/12/2009
 *
 */
public class LeiBemImovelHibernateDAO extends GenericHibernateDAO<LeiBemImovel, Integer> implements LeiBemImovelDAO {


	private static Logger log4j = Logger.getLogger(LeiBemImovelHibernateDAO.class);

	@SuppressWarnings("unchecked")
	public Collection<LeiBemImovel> listarComRelacionamentos(Integer codBemImovel, Integer qtdPagina, Integer numPagina) throws ApplicationException {
		Collection<LeiBemImovel> coll = new ArrayList<LeiBemImovel>();


		try {
			Session session = HibernateUtil.currentSession();

			Query q = session.createQuery("from LeiBemImovel l left join fetch l.bemImovel left join fetch l.tipoLeiBemImovel  where l.bemImovel.codBemImovel=:codBemImovel ORDER BY l.dataAssinatura, l.dataPublicacao, l.tipoLeiBemImovel.descricao ");
			q.setInteger("codBemImovel", codBemImovel);
			if (qtdPagina != null && numPagina != null) {
				q.setMaxResults(qtdPagina.intValue());
				q.setFirstResult( ((numPagina.intValue()-1) * qtdPagina.intValue()));
			}
			coll = q.list();	


		} catch (HibernateException he) {
			throw new ApplicationException("ERRO.201",  new String[]{"ao listar Lei do Bem Imóvel"});
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201",  new String[]{"ao listar Lei do Bem Imóvel"}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				HibernateUtil.closeSession();
			}catch (Exception e) {
				log4j.error("Problema ao tentar fechar conexao com o banco de dados: listarLeiBemImovelComRelacionamentos", e);
			}		
		}

		return coll;
	}


	@SuppressWarnings("unchecked")
	public Collection<LeiBemImovel> listarPorBemImovelTipo(LeiBemImovel lei) throws ApplicationException {
		Collection<LeiBemImovel> coll = new ArrayList<LeiBemImovel>();


		try {
			Session session = HibernateUtil.currentSession();

			StringBuffer hql = new StringBuffer();
			hql.append("from LeiBemImovel l ");
			hql.append("left join fetch l.bemImovel ");
			hql.append("left join fetch l.tipoLeiBemImovel ");
			hql.append("where l.bemImovel.codBemImovel=:codBemImovel ");
			hql.append("and l.tipoLeiBemImovel.codTipoLeiBemImovel=:codTipoLeiBemImovel ");
			hql.append("ORDER BY l.dataAssinatura, l.dataPublicacao, l.tipoLeiBemImovel.descricao ");
			
			Query q = session.createQuery(hql.toString());
			q.setInteger("codBemImovel", lei.getBemImovel().getCodBemImovel());
			q.setInteger("codTipoLeiBemImovel", lei.getTipoLeiBemImovel().getCodTipoLeiBemImovel());
			
			coll = q.list();	

		} catch (HibernateException he) {
			throw new ApplicationException("ERRO.201",  new String[]{"ao listar Lei do Bem Imóvel Por Tipo"});
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201",  new String[]{"ao listar Lei do Bem Imóvel Por Tipo"}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				HibernateUtil.closeSession();
			}catch (Exception e) {
				log4j.error("Problema ao tentar fechar conexao com o banco de dados: listarPorBemImovelTipo", e);
			}		
		}

		return coll;
	}

	@SuppressWarnings("unchecked")
	public Collection<LeiBemImovelExibirBemImovelDTO> listarPorBemImovel(Integer codBemImovel) throws ApplicationException {
		Collection<LeiBemImovelExibirBemImovelDTO> coll = null;


		try {
			Session session = HibernateUtil.currentSession();
			

			StringBuffer hql = new StringBuffer();
		

			hql.append(" SELECT l.tipoLeiBemImovel.descricao as tipoLei, l.numero as numero, l.dataAssinatura as dataAssinatura, " +
					"l.dataPublicacao as dataPublicacao, l.nrDioe as nrDioe ")
			.append(" FROM LeiBemImovel l ")
			.append(" WHERE l.bemImovel.codBemImovel = :codBemImovel")
			.append(" ORDER BY dataAssinatura, dataPublicacao ");

			Query q = session.createQuery(hql.toString());
			q.setInteger("codBemImovel", codBemImovel);
			q.setResultTransformer(Transformers.aliasToBean(LeiBemImovelExibirBemImovelDTO.class));
			coll = q.list();				

		} catch (HibernateException he) {
			log4j.error("Erro:", he);
			throw new ApplicationException("ERRO.201",  new String[]{"ao listar Lei do Bem Imóvel por Bem Imóvel"});
		} catch (Exception e) {
			log4j.error("Erro:", e);
			throw new ApplicationException("ERRO.201",  new String[]{"ao listar Lei do Bem Imóvel por Bem Imóvel"}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				HibernateUtil.closeSession();
			}catch (Exception e) {
				log4j.error("Problema ao tentar fechar conexao com o banco de dados: listarLeiBemImovelComRelacionamentos", e);
			}		
		}

		return coll;
	}


	public LeiBemImovel obterCompleto(Integer codLeiBemImovel) throws ApplicationException {
		try {
			Session session = HibernateUtil.currentSession();

			StringBuffer hql = new StringBuffer();
			hql.append("from LeiBemImovel l ");
			hql.append("left join fetch l.bemImovel ");
			hql.append("left join fetch l.tipoLeiBemImovel ");
			hql.append("where l.codLeiBemImovel=:codLeiBemImovel ");
			
			Query q = session.createQuery(hql.toString());
			q.setInteger("codLeiBemImovel", codLeiBemImovel);
			
			return (LeiBemImovel) q.uniqueResult();	

		} catch (HibernateException he) {
			throw new ApplicationException("ERRO.201",  new String[]{"ao obter Completo de Lei do Bem Imóvel"});
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201",  new String[]{"ao obter Completo de Lei do Bem Imóvel"}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				HibernateUtil.closeSession();
			}catch (Exception e) {
				log4j.error("Problema ao tentar fechar conexao com o banco de dados: obterCompleto", e);
			}		
		}
	}


	@Override
	public LeiBemImovel salvarLeiBemImovel(LeiBemImovel leiBemImovel) throws ApplicationException {
		LeiBemImovel retorno = new LeiBemImovel();
		// Validando parâmetro
		if (leiBemImovel == null){
			throw new ApplicationException("mensagem.erro.9002", new String[]{"LeiBemImovelHibernateDAO.salvarLeiBemImovel()"}, ApplicationException.ICON_ERRO);
		}
		
		try {
			log4j.debug("Obtendo sessao corrente...");
			Session session = HibernateUtil.currentSession();
			session.clear();
			Serializable chave = session.save(leiBemImovel);
			session.flush();
			log4j.info("SALVAR:" + leiBemImovel.getClass().getName() + " salvo.");
			retorno = (LeiBemImovel) session.get(LeiBemImovel.class, chave);
			log4j.info("GET:" + leiBemImovel.getClass().getName() + " q acabou de ser salvo.");
						
		} catch (ConstraintViolationException cve) {
			log4j.error("LeiBemImovelHibernateDAO.salvarLeiBemImovel()" + cve);
			throw new ApplicationException("mensagem.erro.9003", new String[]{leiBemImovel.getClass().getSimpleName()}, ApplicationException.ICON_AVISO);
		} catch (HibernateException he) {
			log4j.error("LeiBemImovelHibernateDAO.salvarLeiBemImovel()" + he);
			throw new ApplicationException("mensagem.erro.9001", new String[]{"LeiBemImovelHibernateDAO.salvarLeiBemImovel()"}, he, ApplicationException.ICON_ERRO);
		} catch (Exception e) {
			log4j.error("LeiBemImovelHibernateDAO.salvarLeiBemImovel()" + e);
			throw new ApplicationException("mensagem.erro.9001",new String[]{"LeiBemImovelHibernateDAO.salvarLeiBemImovel()"}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				log4j.debug("Fechando sessao corrente.");
				HibernateUtil.closeSession();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.9003", new String[]{leiBemImovel.getClass().getSimpleName()}, ApplicationException.ICON_AVISO);
			}
		}
		
		return retorno;
	}


	@Override
	public Collection<LeiBemImovel> listarPorExcetoTipoLei(LeiBemImovel lei) throws ApplicationException {
		Collection<LeiBemImovel> coll = new ArrayList<LeiBemImovel>();

		try {
			Session session = HibernateUtil.currentSession();

			StringBuffer hql = new StringBuffer();
			hql.append("from LeiBemImovel l ");
			hql.append("left join fetch l.bemImovel ");
			hql.append("left join fetch l.tipoLeiBemImovel ");
			hql.append("where l.bemImovel.codBemImovel=:codBemImovel ");
			hql.append("and l.tipoLeiBemImovel.codTipoLeiBemImovel!=:codTipoLeiBemImovel ");
			hql.append("ORDER BY l.tipoLeiBemImovel.descricao, l.dataAssinatura, l.dataPublicacao");
			
			Query q = session.createQuery(hql.toString());
			q.setInteger("codBemImovel", lei.getBemImovel().getCodBemImovel());
			q.setInteger("codTipoLeiBemImovel", lei.getTipoLeiBemImovel().getCodTipoLeiBemImovel());
			
			coll = q.list();	

		} catch (HibernateException he) {
			throw new ApplicationException("ERRO.201",  new String[]{"ao listar Lei do Bem Imóvel Exceto pelo Tipo"});
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201",  new String[]{"ao listar Lei do Bem Imóvel Exceto pelo Tipo"}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				HibernateUtil.closeSession();
			}catch (Exception e) {
				log4j.error("Problema ao tentar fechar conexao com o banco de dados: listarPorBemImovelTipo", e);
			}		
		}

		return coll;
	}


	@Override
	public LeiBemImovel obterByNumero(Long numeroLei) throws ApplicationException {
		try {
			Session session = HibernateUtil.currentSession();

			StringBuffer hql = new StringBuffer();
			hql.append("from LeiBemImovel l ");
			hql.append("left join fetch l.bemImovel ");
			hql.append("left join fetch l.tipoLeiBemImovel ");
			hql.append("where l.numero=:numeroLei ");
			
			Query q = session.createQuery(hql.toString());
			q.setLong("numeroLei", numeroLei);
			
			return (LeiBemImovel) q.uniqueResult();	

		} catch (HibernateException he) {
			throw new ApplicationException("ERRO.201",  new String[]{"ao obter Lei do Bem Imóvel por Número"});
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201",  new String[]{"ao obter Lei do Bem Imóvel por Número"}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				HibernateUtil.closeSession();
			}catch (Exception e) {
				log4j.error("Problema ao tentar fechar conexao com o banco de dados: obterByNumero", e);
			}		
		}
	}

}
