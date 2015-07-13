/**
 * 
 */
package gov.pr.celepar.ucs_manterinstituicao.facade;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import gov.pr.celepar.framework.database.HibernateUtil;
import gov.pr.celepar.framework.exception.ApplicationException;
import gov.pr.celepar.framework.util.Pagina;
import gov.pr.celepar.ucs_manterinstituicao.dao.factory.DAOFactory;
import gov.pr.celepar.ucs_manterinstituicao.dao.factory.HibernateDAOFactory;
import gov.pr.celepar.ucs_manterinstituicao.pojo.Colaborador;

import org.apache.log4j.Logger;

/**
 * @author eholiveira
 *
 */
public class ManterColaboradorFacade {
	private static Logger log = Logger.getLogger(ManterColaboradorFacade.class);
	
	public static Pagina listarColaborador(Pagina pagina,
			Colaborador colaborador, Date dataInicial, Date dataFinal, String situacao) throws ApplicationException{
		HibernateDAOFactory hibernateFactory = (HibernateDAOFactory) DAOFactory.getDAOFactory(DAOFactory.HIBERNATE);
		try {
			if (pagina.getTotalRegistros() == 0) {
			 	pagina.setTotalRegistros(hibernateFactory.getColaboradorDAO().buscarQtdLista(colaborador, dataInicial, dataFinal, situacao).intValue());
			}
			pagina.setRegistros(hibernateFactory.getColaboradorDAO().listar(colaborador, dataInicial, dataFinal, situacao, pagina.getQuantidade(), pagina.getPaginaAtual()));
		} catch(ApplicationException ae) {
			throw ae;
		}catch (Exception e) {
			throw new ApplicationException("mensagem.erro.colaborador.listar", e);
		}
		return pagina;
	}
	
	public static Colaborador obterColaborador(Integer idColaborador) throws ApplicationException{
		HibernateDAOFactory hibernateFactory = (HibernateDAOFactory) DAOFactory.getDAOFactory(DAOFactory.HIBERNATE);
		Colaborador colaborador = new Colaborador();
		try {
			colaborador = hibernateFactory.getColaboradorDAO().obter(idColaborador);
		} catch(ApplicationException ae) {
			throw ae;
		}catch (Exception e) {
			throw new ApplicationException("mensagem.erro.colaborador.obter", e);
		}
		return colaborador;
	}
	
	public static void excluirColaborador(Colaborador colaborador) throws ApplicationException{
		HibernateDAOFactory hibernateFactory = (HibernateDAOFactory) DAOFactory.getDAOFactory(DAOFactory.HIBERNATE);
		try {
			hibernateFactory.getColaboradorDAO().excluir(colaborador);;
		} catch(ApplicationException ae) {
			throw ae;
		}catch (Exception e) {
			throw new ApplicationException("mensagem.erro.colaborador.obter", e);
		}
	}
	
	public static void incluirColaborador(Colaborador colaborador) throws ApplicationException{
		HibernateDAOFactory hibernateFactory = (HibernateDAOFactory) DAOFactory.getDAOFactory(DAOFactory.HIBERNATE);
		try {
			HibernateUtil.currentTransaction();
			
			hibernateFactory.getColaboradorDAO().salvar(colaborador);
			
			HibernateUtil.commitTransaction(); //Fecha sessão e transação

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				log.error("Problema ao tentar realizar rollback na transacao: incluirColaborador", e);
			}
			throw appEx;
			
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				log.error("Problema ao tentar realizar rollback na transacao: incluirColaborador", e);
			}
			throw new ApplicationException("mensagem.erro.colaborador.incluir", ex, ApplicationException.ICON_ERRO);						
		}
	}
	
	public static void alterarColaborador(Colaborador colaborador) throws ApplicationException{
		HibernateDAOFactory hibernateFactory = (HibernateDAOFactory) DAOFactory.getDAOFactory(DAOFactory.HIBERNATE);
		try {
			HibernateUtil.currentTransaction();
			
			hibernateFactory.getColaboradorDAO().alterar(colaborador);
			
			HibernateUtil.commitTransaction(); //Fecha sessão e transação

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				log.error("Problema ao tentar realizar rollback na transacao: incluirInstituicao", e);
			}
			throw appEx;
			
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				log.error("Problema ao tentar realizar rollback na transacao: incluirInstituicao", e);
			}
			throw new ApplicationException("mensagem.erro.colaborador.alterar", ex, ApplicationException.ICON_ERRO);						
		}
	}

	public static Collection<Colaborador> listarColaborador() throws ApplicationException {
		HibernateDAOFactory hibernateFactory = (HibernateDAOFactory) DAOFactory.getDAOFactory(DAOFactory.HIBERNATE);
		Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
		try {
			colaboradores = hibernateFactory.getColaboradorDAO().listar();
		} catch(ApplicationException ae) {
			throw ae;
		}catch (Exception e) {
			throw new ApplicationException("mensagem.erro.colaborador.listar", e);
		}
		return colaboradores;
	}
}
