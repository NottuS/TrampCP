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
import gov.pr.celepar.abi.dao.implementation.AssinaturaCessaoDeUsoHibernateDAO;
import gov.pr.celepar.abi.dao.implementation.AssinaturaDoacaoHibernateDAO;
import gov.pr.celepar.abi.dao.implementation.AssinaturaDocTransferenciaHibernateDAO;
import gov.pr.celepar.abi.dao.implementation.AssinaturaHibernateDAO;
import gov.pr.celepar.abi.dao.implementation.AssinaturaTransferenciaHibernateDAO;
import gov.pr.celepar.abi.dao.implementation.AvaliacaoHibernateDAO;
import gov.pr.celepar.abi.dao.implementation.BemImovelHibernateDAO;
import gov.pr.celepar.abi.dao.implementation.CargoAssinaturaHibernateDAO;
import gov.pr.celepar.abi.dao.implementation.CartorioHibernateDAO;
import gov.pr.celepar.abi.dao.implementation.CessaoDeUsoHibernateDAO;
import gov.pr.celepar.abi.dao.implementation.ClassificacaoBemImovelHibernateDAO;
import gov.pr.celepar.abi.dao.implementation.ConfrontanteHibernateDAO;
import gov.pr.celepar.abi.dao.implementation.CoordenadaUtmHibernateDAO;
import gov.pr.celepar.abi.dao.implementation.DenominacaoImovelHibernateDAO;
import gov.pr.celepar.abi.dao.implementation.DoacaoHibernateDAO;
import gov.pr.celepar.abi.dao.implementation.DocumentacaoHibernateDAO;
import gov.pr.celepar.abi.dao.implementation.EdificacaoHibernateDAO;
import gov.pr.celepar.abi.dao.implementation.FormaIncorporacaoHibernateDAO;
import gov.pr.celepar.abi.dao.implementation.GrupoSentinelaHibernateDAO;
import gov.pr.celepar.abi.dao.implementation.InstituicaoHibernateDAO;
import gov.pr.celepar.abi.dao.implementation.ItemCessaoDeUsoHibernateDAO;
import gov.pr.celepar.abi.dao.implementation.ItemDoacaoHibernateDAO;
import gov.pr.celepar.abi.dao.implementation.ItemTransferenciaHibernateDAO;
import gov.pr.celepar.abi.dao.implementation.ItemVistoriaHibernateDAO;
import gov.pr.celepar.abi.dao.implementation.LeiBemImovelHibernateDAO;
import gov.pr.celepar.abi.dao.implementation.LoteHibernateDAO;
import gov.pr.celepar.abi.dao.implementation.NotificacaoHibernateDAO;
import gov.pr.celepar.abi.dao.implementation.OcorrenciaDocumentacaoHibernateDAO;
import gov.pr.celepar.abi.dao.implementation.OcupacaoHibernateDAO;
import gov.pr.celepar.abi.dao.implementation.OrgaoHibernateDAO;
import gov.pr.celepar.abi.dao.implementation.ParametroAgendaEmailHibernateDAO;
import gov.pr.celepar.abi.dao.implementation.ParametroAgendaHibernateDAO;
import gov.pr.celepar.abi.dao.implementation.ParametroVistoriaDenominacaoImovelHibernateDAO;
import gov.pr.celepar.abi.dao.implementation.ParametroVistoriaDominioHibernateDAO;
import gov.pr.celepar.abi.dao.implementation.ParametroVistoriaHibernateDAO;
import gov.pr.celepar.abi.dao.implementation.QuadraHibernateDAO;
import gov.pr.celepar.abi.dao.implementation.SituacaoImovelHibernateDAO;
import gov.pr.celepar.abi.dao.implementation.SituacaoLegalCartorialHibernateDAO;
import gov.pr.celepar.abi.dao.implementation.SituacaoOcupacaoHibernateDAO;
import gov.pr.celepar.abi.dao.implementation.StatusTermoHibernateDAO;
import gov.pr.celepar.abi.dao.implementation.StatusVistoriaHibernateDAO;
import gov.pr.celepar.abi.dao.implementation.TabelionatoHibernateDAO;
import gov.pr.celepar.abi.dao.implementation.TipoConstrucaoHibernateDAO;
import gov.pr.celepar.abi.dao.implementation.TipoDocumentacaoHibernateDAO;
import gov.pr.celepar.abi.dao.implementation.TipoEdificacaoHibernateDAO;
import gov.pr.celepar.abi.dao.implementation.TipoLeiBemImovelHibernateDAO;
import gov.pr.celepar.abi.dao.implementation.TransferenciaHibernateDAO;
import gov.pr.celepar.abi.dao.implementation.UsuarioGrupoSentinelaHibernateDAO;
import gov.pr.celepar.abi.dao.implementation.UsuarioHibernateDAO;
import gov.pr.celepar.abi.dao.implementation.UsuarioOrgaoHibernateDAO;
import gov.pr.celepar.abi.dao.implementation.VistoriaHibernateDAO;
import gov.pr.celepar.abi.dao.implementation.VistoriadorHibernateDAO;


/**
 * Classe Factory para DAOs que utilizam Hibernate.
 * 
 * @author CodeGenerator - Esta classe foi gerada automaticamente
 * @since 1.0
 * @version 1.0, Thu Dec 24 11:17:32 BRST 2009
 *
 */
public class HibernateDAOFactory extends DAOFactory {	

	public BemImovelDAO getBemImovelDAO() {
		 return new BemImovelHibernateDAO();
	}

	public AvaliacaoDAO getAvaliacaoDAO() {
		 return new AvaliacaoHibernateDAO();
	}

	public EdificacaoDAO getEdificacaoDAO() {
		 return new EdificacaoHibernateDAO();
	}

	public TipoDocumentacaoDAO getTipoDocumentacaoDAO() {
		 return new TipoDocumentacaoHibernateDAO();
	}

	public DocumentacaoDAO getDocumentacaoDAO() {
		 return new DocumentacaoHibernateDAO();
	}

	public OcorrenciaDocumentacaoDAO getOcorrenciaDocumentacaoDAO() {
		 return new OcorrenciaDocumentacaoHibernateDAO();
	}

	public CoordenadaUtmDAO getCoordenadaUtmDAO() {
		 return new CoordenadaUtmHibernateDAO();
	}

	public SituacaoLegalCartorialDAO getSituacaoLegalCartorialDAO() {
		 return new SituacaoLegalCartorialHibernateDAO();
	}

	public DenominacaoImovelDAO getDenominacaoImovelDAO() {
		 return new DenominacaoImovelHibernateDAO();
	}

	public TabelionatoDAO getTabelionatoDAO() {
		 return new TabelionatoHibernateDAO();
	}

	public OrgaoDAO getOrgaoDAO() {
		 return new OrgaoHibernateDAO();
	}

	public TipoConstrucaoDAO getTipoConstrucaoDAO() {
		 return new TipoConstrucaoHibernateDAO();
	}

	public OcupacaoDAO getOcupacaoDAO() {
		 return new OcupacaoHibernateDAO();
	}

	public ConfrontanteDAO getConfrontanteDAO() {
		 return new ConfrontanteHibernateDAO();
	}

	public ClassificacaoBemImovelDAO getClassificacaoBemImovelDAO() {
		 return new ClassificacaoBemImovelHibernateDAO();
	}

	public TipoLeiBemImovelDAO getTipoLeiBemImovelDAO() {
		 return new TipoLeiBemImovelHibernateDAO();
	}

	public CartorioDAO getCartorioDAO() {
		 return new CartorioHibernateDAO();
	}

	public SituacaoImovelDAO getSituacaoImovelDAO() {
		 return new SituacaoImovelHibernateDAO();
	}

	public NotificacaoDAO getNotificacaoDAO() {
		 return new NotificacaoHibernateDAO();
	}

	public TipoEdificacaoDAO getTipoEdificacaoDAO() {
		 return new TipoEdificacaoHibernateDAO();
	}

	public LeiBemImovelDAO getLeiBemImovelDAO() {
		 return new LeiBemImovelHibernateDAO();
	}

	public QuadraDAO getQuadraDAO() {
		 return new QuadraHibernateDAO();
	}

	public SituacaoOcupacaoDAO getSituacaoOcupacaoDAO() {
		 return new SituacaoOcupacaoHibernateDAO();
	}

	public FormaIncorporacaoDAO getFormaIncorporacaoDAO() {
		 return new FormaIncorporacaoHibernateDAO();
	}

	public LoteDAO getLoteDAO() {
		 return new LoteHibernateDAO();
	}
	
	public StatusVistoriaDAO getStatusVistoriaDAO() {
		return new StatusVistoriaHibernateDAO();
	}
	
	public VistoriaDAO getVistoriaDAO() {
		return new VistoriaHibernateDAO();
	}
	
	public ItemVistoriaDAO getItemVistoriaDAO() {
		return new ItemVistoriaHibernateDAO();
	}
	
	public VistoriadorDAO getVistoriadorDAO() {
		 return new VistoriadorHibernateDAO();
	}

	public ParametroVistoriaDAO getParametroVistoriaDAO() {
		 return new ParametroVistoriaHibernateDAO();
	}
	
	public ParametroVistoriaDominioDAO getParametroVistoriaDominioDAO() {
		 return new ParametroVistoriaDominioHibernateDAO();
	}

	public CargoAssinaturaDAO getCargoAssinaturaDAO() {
		 return new CargoAssinaturaHibernateDAO();
	}

	public AssinaturaDAO getAssinaturaDAO() {
		 return new AssinaturaHibernateDAO();
	}

	public ParametroAgendaDAO getParametroAgendaDAO() {
		 return new ParametroAgendaHibernateDAO();
	}
	
	public ParametroAgendaEmailDAO getParametroAgendaEmailDAO() {
		 return new ParametroAgendaEmailHibernateDAO();
	}
	public DoacaoDAO getDoacaoDAO() {
		 return new DoacaoHibernateDAO();
	}

	public StatusTermoDAO getStatusTermoDAO() {
		 return new StatusTermoHibernateDAO();
	}

	public ItemDoacaoDAO getItemDoacaoDAO() {
		 return new ItemDoacaoHibernateDAO();
	}

	public AssinaturaDoacaoDAO getAssinaturaDoacaoDAO() {
		 return new AssinaturaDoacaoHibernateDAO();
	}

	public CessaoDeUsoDAO getCessaoDeUsoDAO() {
		 return new CessaoDeUsoHibernateDAO();
	}
	public ItemCessaoDeUsoDAO getItemCessaoDeUsoDAO() {
		 return new ItemCessaoDeUsoHibernateDAO();
	}
	public AssinaturaCessaoDeUsoDAO getAssinaturaCessaoDeUsoDAO() {
		 return new AssinaturaCessaoDeUsoHibernateDAO();
	}
	public TransferenciaDAO getTransferenciaDAO() {
		 return new TransferenciaHibernateDAO();
	}
	
	public ItemTransferenciaDAO getItemTransferenciaDAO() {
		 return new ItemTransferenciaHibernateDAO();
	}

	public AssinaturaTransferenciaDAO getAssinaturaTransferenciaDAO() {
		 return new AssinaturaTransferenciaHibernateDAO();
	}
	
	public UsuarioDAO getUsuarioDAO() {
		 return new UsuarioHibernateDAO();
	}

	public UsuarioOrgaoDAO getUsuarioOrgaoDAO() {
		 return new UsuarioOrgaoHibernateDAO();
	}

	public UsuarioGrupoSentinelaDAO getUsuarioGrupoSentinelaDAO() {
		 return new UsuarioGrupoSentinelaHibernateDAO();
	}

	public GrupoSentinelaDAO getGrupoSentinelaDAO() {
		 return new GrupoSentinelaHibernateDAO();
	}

	@Override
	public InstituicaoDAO getInstituicaoDAO() {
		
		return new InstituicaoHibernateDAO();
	}

	@Override
	public ParametroVistoriaDenominacaoImovelDAO getParametroVistoriaDenominacaoImovelDAO() {
		return new ParametroVistoriaDenominacaoImovelHibernateDAO();
	}

	@Override
	public AssinaturaDocTransferenciaDAO getAssinaturaDocTransferenciaDAO() {
		return new AssinaturaDocTransferenciaHibernateDAO();
	}

}