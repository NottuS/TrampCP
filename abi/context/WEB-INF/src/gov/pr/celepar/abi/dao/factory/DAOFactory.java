package gov.pr.celepar.abi.dao.factory;

import gov.pr.celepar.abi.dao.AssinaturaCessaoDeUsoDAO;
import gov.pr.celepar.abi.dao.AssinaturaDAO;
import gov.pr.celepar.abi.dao.AssinaturaDoacaoDAO;
import gov.pr.celepar.abi.dao.AssinaturaDocTransferenciaDAO;
import gov.pr.celepar.abi.dao.AssinaturaTransferenciaDAO;
import gov.pr.celepar.abi.dao.AvaliacaoDAO;
import gov.pr.celepar.abi.dao.BemImovelDAO;
import gov.pr.celepar.abi.dao.CargoAssinaturaDAO;
import gov.pr.celepar.abi.dao.CartorioDAO;
import gov.pr.celepar.abi.dao.CessaoDeUsoDAO;
import gov.pr.celepar.abi.dao.ClassificacaoBemImovelDAO;
import gov.pr.celepar.abi.dao.ConfrontanteDAO;
import gov.pr.celepar.abi.dao.CoordenadaUtmDAO;
import gov.pr.celepar.abi.dao.DenominacaoImovelDAO;
import gov.pr.celepar.abi.dao.DoacaoDAO;
import gov.pr.celepar.abi.dao.DocumentacaoDAO;
import gov.pr.celepar.abi.dao.EdificacaoDAO;
import gov.pr.celepar.abi.dao.FormaIncorporacaoDAO;
import gov.pr.celepar.abi.dao.GrupoSentinelaDAO;
import gov.pr.celepar.abi.dao.InstituicaoDAO;
import gov.pr.celepar.abi.dao.ItemCessaoDeUsoDAO;
import gov.pr.celepar.abi.dao.ItemDoacaoDAO;
import gov.pr.celepar.abi.dao.ItemTransferenciaDAO;
import gov.pr.celepar.abi.dao.ItemVistoriaDAO;
import gov.pr.celepar.abi.dao.LeiBemImovelDAO;
import gov.pr.celepar.abi.dao.LoteDAO;
import gov.pr.celepar.abi.dao.NotificacaoDAO;
import gov.pr.celepar.abi.dao.OcorrenciaDocumentacaoDAO;
import gov.pr.celepar.abi.dao.OcupacaoDAO;
import gov.pr.celepar.abi.dao.OrgaoDAO;
import gov.pr.celepar.abi.dao.ParametroAgendaDAO;
import gov.pr.celepar.abi.dao.ParametroAgendaEmailDAO;
import gov.pr.celepar.abi.dao.ParametroVistoriaDAO;
import gov.pr.celepar.abi.dao.ParametroVistoriaDenominacaoImovelDAO;
import gov.pr.celepar.abi.dao.ParametroVistoriaDominioDAO;
import gov.pr.celepar.abi.dao.QuadraDAO;
import gov.pr.celepar.abi.dao.SituacaoImovelDAO;
import gov.pr.celepar.abi.dao.SituacaoLegalCartorialDAO;
import gov.pr.celepar.abi.dao.SituacaoOcupacaoDAO;
import gov.pr.celepar.abi.dao.StatusTermoDAO;
import gov.pr.celepar.abi.dao.StatusVistoriaDAO;
import gov.pr.celepar.abi.dao.TabelionatoDAO;
import gov.pr.celepar.abi.dao.TipoConstrucaoDAO;
import gov.pr.celepar.abi.dao.TipoDocumentacaoDAO;
import gov.pr.celepar.abi.dao.TipoEdificacaoDAO;
import gov.pr.celepar.abi.dao.TipoLeiBemImovelDAO;
import gov.pr.celepar.abi.dao.TransferenciaDAO;
import gov.pr.celepar.abi.dao.UsuarioDAO;
import gov.pr.celepar.abi.dao.UsuarioGrupoSentinelaDAO;
import gov.pr.celepar.abi.dao.UsuarioOrgaoDAO;
import gov.pr.celepar.abi.dao.VistoriaDAO;
import gov.pr.celepar.abi.dao.VistoriadorDAO;

/**
 * Classe Factory.
 * 
 * @author CodeGenerator - Esta classe foi gerada automaticamente
 * @since 1.0
 * @version 1.0, Thu Dec 24 11:17:32 BRST 2009
 *
 */
public abstract class DAOFactory {
	public static final int HIBERNATE = 1;
	/* public static final int POSTGRESQL = 2; */

	public static DAOFactory getDAOFactory(int whichFactory) {  
		switch (whichFactory) {
	    	case HIBERNATE: 
	    		return new HibernateDAOFactory();
	    	/* case POSTGRESQL:
	    		return new PostgreDAOFactory(); */
	    	default: 
	    		return null;
	    }
	}
	
	public abstract BemImovelDAO getBemImovelDAO();
	public abstract AvaliacaoDAO getAvaliacaoDAO();
	public abstract EdificacaoDAO getEdificacaoDAO();
	public abstract TipoDocumentacaoDAO getTipoDocumentacaoDAO();
	public abstract DocumentacaoDAO getDocumentacaoDAO();
	public abstract OcorrenciaDocumentacaoDAO getOcorrenciaDocumentacaoDAO();
	public abstract CoordenadaUtmDAO getCoordenadaUtmDAO();
	public abstract SituacaoLegalCartorialDAO getSituacaoLegalCartorialDAO();
	public abstract DenominacaoImovelDAO getDenominacaoImovelDAO();
	public abstract TabelionatoDAO getTabelionatoDAO();
	public abstract OrgaoDAO getOrgaoDAO();
	public abstract TipoConstrucaoDAO getTipoConstrucaoDAO();
	public abstract OcupacaoDAO getOcupacaoDAO();
	public abstract ConfrontanteDAO getConfrontanteDAO();
	public abstract ClassificacaoBemImovelDAO getClassificacaoBemImovelDAO();
	public abstract TipoLeiBemImovelDAO getTipoLeiBemImovelDAO();
	public abstract CartorioDAO getCartorioDAO();
	public abstract SituacaoImovelDAO getSituacaoImovelDAO();
	public abstract NotificacaoDAO getNotificacaoDAO();
	public abstract TipoEdificacaoDAO getTipoEdificacaoDAO();
	public abstract LeiBemImovelDAO getLeiBemImovelDAO();
	public abstract QuadraDAO getQuadraDAO();
	public abstract ItemVistoriaDAO getItemVistoriaDAO();
	public abstract StatusVistoriaDAO getStatusVistoriaDAO();
	public abstract SituacaoOcupacaoDAO getSituacaoOcupacaoDAO();
	public abstract FormaIncorporacaoDAO getFormaIncorporacaoDAO();
	public abstract VistoriaDAO getVistoriaDAO();
	public abstract VistoriadorDAO getVistoriadorDAO();
	public abstract LoteDAO getLoteDAO();
	public abstract ParametroVistoriaDAO getParametroVistoriaDAO();
	public abstract ParametroVistoriaDominioDAO getParametroVistoriaDominioDAO();
	public abstract CargoAssinaturaDAO getCargoAssinaturaDAO();
	public abstract AssinaturaDAO getAssinaturaDAO();
	public abstract ParametroAgendaDAO getParametroAgendaDAO();
	public abstract ParametroAgendaEmailDAO getParametroAgendaEmailDAO();
	public abstract StatusTermoDAO getStatusTermoDAO();
	public abstract DoacaoDAO getDoacaoDAO();
	public abstract ItemDoacaoDAO getItemDoacaoDAO();
	public abstract AssinaturaDoacaoDAO getAssinaturaDoacaoDAO();
	public abstract CessaoDeUsoDAO getCessaoDeUsoDAO();
	public abstract ItemCessaoDeUsoDAO getItemCessaoDeUsoDAO();
	public abstract AssinaturaCessaoDeUsoDAO getAssinaturaCessaoDeUsoDAO();
	public abstract TransferenciaDAO getTransferenciaDAO();
	public abstract ItemTransferenciaDAO getItemTransferenciaDAO();
	public abstract AssinaturaTransferenciaDAO getAssinaturaTransferenciaDAO();
	public abstract UsuarioDAO getUsuarioDAO();
	public abstract UsuarioOrgaoDAO getUsuarioOrgaoDAO();
	public abstract UsuarioGrupoSentinelaDAO getUsuarioGrupoSentinelaDAO();
	public abstract GrupoSentinelaDAO getGrupoSentinelaDAO();
	public abstract InstituicaoDAO getInstituicaoDAO();
	public abstract ParametroVistoriaDenominacaoImovelDAO getParametroVistoriaDenominacaoImovelDAO();
	public abstract AssinaturaDocTransferenciaDAO getAssinaturaDocTransferenciaDAO();
	
}