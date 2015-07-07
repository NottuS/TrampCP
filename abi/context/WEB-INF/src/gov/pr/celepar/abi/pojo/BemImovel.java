package gov.pr.celepar.abi.pojo;

// Generated 24/12/2009 11:14:35 by Hibernate Tools 3.2.0.CR1
import gov.pr.celepar.abi.dto.DocumentacaoNotificacaoExibirBemImovelDTO;
import gov.pr.celepar.abi.dto.DocumentacaoSemNotificacaoExibirBemImovelDTO;
import gov.pr.celepar.abi.dto.LoteExibirBemImovelDTO;
import gov.pr.celepar.abi.dto.OcorrenciaDocumentacaoExibirBemImovelDTO;
import gov.pr.celepar.abi.util.Dominios;
import gov.pr.celepar.abi.util.Dominios.administracaoImovel;
import gov.pr.celepar.abi.util.Util;
import gov.pr.celepar.framework.util.Valores;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Transient;


/**
 * BemImovel generated by hbm2java
 */
public class BemImovel implements java.io.Serializable {
    
	private static final long serialVersionUID = 5679163195830509727L;
	private Integer codBemImovel; // codigo sequencial
	private Integer nrBemImovel; // codigo informado pelo usuario no cadastro
    private ClassificacaoBemImovel classificacaoBemImovel;
    private SituacaoImovel situacaoImovel;
    private DenominacaoImovel denominacaoImovel;
    private SituacaoLegalCartorial situacaoLegalCartorial;
    private Orgao orgao;
    private FormaIncorporacao formaIncorporacao;
    private Integer situacaoLocal;
    private Long numeroProcessoSpi;
    private String cep;
    private String uf;
    private String municipio;
    private Integer codMunicipio;
    private String logradouro;
    private String numero;
    private String bairroDistrito;
    private String complemento;
    private Date dataIncorporacao;
    private BigDecimal areaTerreno;
    private BigDecimal areaConstruida;
    private BigDecimal areaDispoNivel;
    private String observacoesMigracao;
    private Date tsInclusao;
    private Date tsAtualizacao;
    private String cpfResponsavel;
    private Integer administracao;
    private String somenteTerreno;
    private Instituicao instituicao;
    private Set<OcorrenciaDocumentacao> ocorrenciaDocumentacaos = new HashSet<OcorrenciaDocumentacao>(0);
    private Set<CoordenadaUtm> coordenadaUtms = new HashSet<CoordenadaUtm>(0);
    private Set<Confrontante> confrontantes = new HashSet<Confrontante>(0);
    private Set<Avaliacao> avaliacaos = new HashSet<Avaliacao>(0);
    private Set<Documentacao> documentacaos = new HashSet<Documentacao>(0);
    private Set<Edificacao> edificacaos = new HashSet<Edificacao>(0);
    private Set<Quadra> quadras = new HashSet<Quadra>(0);
    private Set<LeiBemImovel> leiBemImovels = new HashSet<LeiBemImovel>(0);
    private Set<Ocupacao> ocupacaosTerreno = new HashSet<Ocupacao>(0);
    private Set<Doacao> listaDoacao = new HashSet<Doacao>(0);
    private Set<Vistoria> listaVistoria = new HashSet<Vistoria>(0);
    private Set<Transferencia> listaTransferencia = new HashSet<Transferencia>(0);

    public BemImovel() {
    }
    
    

    public BemImovel(Integer codBemImovel, Integer nrBemImovel, String uf, String municipio,
			Integer codMunicipio) {
		super();
		this.codBemImovel = codBemImovel;
		this.nrBemImovel = nrBemImovel;
		this.uf = uf;
		this.municipio = municipio;
		this.codMunicipio = codMunicipio;
	}



	public BemImovel(Integer codBemImovel, Integer nrBemImovel,
        ClassificacaoBemImovel classificacaoBemImovel,
        SituacaoImovel situacaoImovel, DenominacaoImovel denominacaoImovel,
        SituacaoLegalCartorial situacaoLegalCartorial,
        Orgao orgao, FormaIncorporacao formaIncorporacao,
        Integer situacaoLocal, String uf,
        String municipio, String observacoesMigracao, Date tsInclusao,
        Date tsAtualizacao, String cpfResponsavel) {
        this.codBemImovel = codBemImovel;
        this.nrBemImovel = nrBemImovel;
        this.classificacaoBemImovel = classificacaoBemImovel;
        this.situacaoImovel = situacaoImovel;
        this.denominacaoImovel = denominacaoImovel;
        this.situacaoLegalCartorial = situacaoLegalCartorial;
        this.orgao = orgao;
        this.formaIncorporacao = formaIncorporacao;
        this.situacaoLocal = situacaoLocal;
        this.uf = uf;
        this.municipio = municipio;
        this.observacoesMigracao = observacoesMigracao;
        this.tsInclusao = tsInclusao;
        this.tsAtualizacao = tsAtualizacao;
        this.cpfResponsavel = cpfResponsavel;
    }

    public BemImovel(Integer codBemImovel, Integer nrBemImovel,  
        ClassificacaoBemImovel classificacaoBemImovel,
        SituacaoImovel situacaoImovel, DenominacaoImovel denominacaoImovel,
        SituacaoLegalCartorial situacaoLegalCartorial,
        Orgao orgao, FormaIncorporacao formaIncorporacao,
         Integer situacaoLocal,
        Long numeroProcessoSpi, String cep, String uf, String municipio,
        String logradouro, String numero, String bairroDistrito,
        String complemento, 
         Date dataIncorporacao,
        BigDecimal areaTerreno, BigDecimal areaConstruida,
        BigDecimal areaDispoNivel, String observacoesMigracao, Date tsInclusao,
        Date tsAtualizacao, String cpfResponsavel,
        Set<OcorrenciaDocumentacao> ocorrenciaDocumentacaos,
        Set<CoordenadaUtm> coordenadaUtms, Set<Confrontante> confrontantes,
        Set<Avaliacao> avaliacaos, Set<Documentacao> documentacaos,
        Set<Edificacao> edificacaos, Set<Quadra> quadras,
        Set<LeiBemImovel> leiBemImovels, Set<Ocupacao> ocupacaos,
        String somenteTerreno) {
        this.codBemImovel = codBemImovel;
        this.nrBemImovel = nrBemImovel;
        this.classificacaoBemImovel = classificacaoBemImovel;
        this.situacaoImovel = situacaoImovel;
        this.denominacaoImovel = denominacaoImovel;
        this.situacaoLegalCartorial = situacaoLegalCartorial;
        this.orgao = orgao;
        this.formaIncorporacao = formaIncorporacao;
        this.situacaoLocal = situacaoLocal;
        this.numeroProcessoSpi = numeroProcessoSpi;
        this.cep = cep;
        this.uf = uf;
        this.municipio = municipio;
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairroDistrito = bairroDistrito;
        this.complemento = complemento;
        this.dataIncorporacao = dataIncorporacao;
        this.areaTerreno = areaTerreno;
        this.areaConstruida = areaConstruida;
        this.areaDispoNivel = areaDispoNivel;
        this.observacoesMigracao = observacoesMigracao;
        this.tsInclusao = tsInclusao;
        this.tsAtualizacao = tsAtualizacao;
        this.cpfResponsavel = cpfResponsavel;
        this.ocorrenciaDocumentacaos = ocorrenciaDocumentacaos;
        this.coordenadaUtms = coordenadaUtms;
        this.confrontantes = confrontantes;
        this.avaliacaos = avaliacaos;
        this.documentacaos = documentacaos;
        this.edificacaos = edificacaos;
        this.quadras = quadras;
        this.leiBemImovels = leiBemImovels;
        this.ocupacaosTerreno = ocupacaos;
        this.somenteTerreno = somenteTerreno;
    }

    public Integer getCodBemImovel() {
        return this.codBemImovel;
    }

    public void setCodBemImovel(Integer codBemImovel) {
        this.codBemImovel = codBemImovel;
    }

    public ClassificacaoBemImovel getClassificacaoBemImovel() {
        return this.classificacaoBemImovel;
    }

    public void setClassificacaoBemImovel(
        ClassificacaoBemImovel classificacaoBemImovel) {
        this.classificacaoBemImovel = classificacaoBemImovel;
    }


    public SituacaoImovel getSituacaoImovel() {
        return this.situacaoImovel;
    }

    public void setSituacaoImovel(SituacaoImovel situacaoImovel) {
        this.situacaoImovel = situacaoImovel;
    }

    public DenominacaoImovel getDenominacaoImovel() {
        return this.denominacaoImovel;
    }

    public void setDenominacaoImovel(DenominacaoImovel denominacaoImovel) {
        this.denominacaoImovel = denominacaoImovel;
    }

 

    public SituacaoLegalCartorial getSituacaoLegalCartorial() {
        return this.situacaoLegalCartorial;
    }

    public void setSituacaoLegalCartorial(
        SituacaoLegalCartorial situacaoLegalCartorial) {
        this.situacaoLegalCartorial = situacaoLegalCartorial;
    }

    public Orgao getOrgao() {
        return this.orgao;
    }

    public void setOrgao(Orgao orgao) {
        this.orgao = orgao;
    }

    public FormaIncorporacao getFormaIncorporacao() {
        return this.formaIncorporacao;
    }

    public void setFormaIncorporacao(FormaIncorporacao formaIncorporacao) {
        this.formaIncorporacao = formaIncorporacao;
    }

    public Integer getSituacaoLocal() {
        return this.situacaoLocal;
    }

    public void setSituacaoLocal(Integer situacaoLocal) {
        this.situacaoLocal = situacaoLocal;
    }

    public Long getNumeroProcessoSpi() {
        return this.numeroProcessoSpi;
    }

    public void setNumeroProcessoSpi(Long numeroProcessoSpi) {
        this.numeroProcessoSpi = numeroProcessoSpi;
    }

    public String getCep() {
        return this.cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getUf() {
        return this.uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getMunicipio() {
        return this.municipio;
    }
    
  
    
	
	@Transient
    public String getEnderecoMapa(){
		if (!Util.strEmBranco(this.logradouro)){
			StringBuffer aux = new StringBuffer();
			aux.append("'").append(this.logradouro).append(", ");
			if (!Util.strEmBranco(this.numero)){
				aux.append(this.numero).append(", ");
			}
			aux.append(this.municipio);
			aux.append(", ").append(this.uf).append(", brasil").append("'");

			return aux.toString().replaceAll(" ", "+");
		}
		return "";
    }
    @Transient
    public String getMunicipioUF(){
    	if (getMunicipio() == null){
    		setMunicipio("");
    	}
    	if (getUf() == null){
    		setUf("");
    	}
    	if(getLogradouro() != null && !Util.strEmBranco(getLogradouro())){
    		if (getNumero() != null && !Util.strEmBranco(getNumero())){
    			return getMunicipio().concat("/").concat(getUf()).concat("<br>&nbsp<br>").concat(getLogradouro()).concat(", ").concat(getNumero());
    		}
    		return getMunicipio().concat("/").concat(getUf()).concat("<br>&nbsp<br>").concat(getLogradouro());
    		
    	}
    	else{
    		return   getMunicipio().concat("/").concat(getUf());
    	}
    }
    
    @Transient
    public String getDescricaoOcupacaos(){
    	StringBuffer aux = new StringBuffer();
    	if (getEdificacaos()!=null){
    		for( Edificacao e : getEdificacaos()){ 
    			if (e.getOcupacaos()!= null){
    				for (Ocupacao o : e.getOcupacaos()){
    					aux.append(o.getDescricao()).append("<br>");
    				}
    			}
    		}
    	}
    	return aux.toString();
    }
    
    @Transient
    public String getNumDoctoCartorial(){
    	StringBuffer aux = new StringBuffer();
    	if (getDocumentacaos()!= null){
    		for( Documentacao d : getDocumentacaos()){ 
    			if (!Util.strEmBranco(d.getNumeroDocumentoCartorial())){
    				aux.append(d.getNumeroDocumentoCartorial()).append("<br>");
    			}
    		}
    	}
    	return aux.toString();
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getLogradouro() {
        return this.logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return this.numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairroDistrito() {
        return this.bairroDistrito;
    }

    public void setBairroDistrito(String bairroDistrito) {
        this.bairroDistrito = bairroDistrito;
    }

    public String getComplemento() {
        return this.complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

   

    public Date getDataIncorporacao() {
        return this.dataIncorporacao;
    }

    public void setDataIncorporacao(Date dataIncorporacao) {
        this.dataIncorporacao = dataIncorporacao;
    }

    public BigDecimal getAreaTerreno() {
        return this.areaTerreno;
    }

    public void setAreaTerreno(BigDecimal areaTerreno) {
        this.areaTerreno = areaTerreno;
    }

    public BigDecimal getAreaConstruida() {
        return this.areaConstruida;
    }

    public void setAreaConstruida(BigDecimal areaConstruida) {
        this.areaConstruida = areaConstruida;
    }

    public BigDecimal getAreaDispoNivel() {
        return this.areaDispoNivel;
    }

    public void setAreaDispoNivel(BigDecimal areaDispoNivel) {
        this.areaDispoNivel = areaDispoNivel;
    }

    public String getObservacoesMigracao() {
        return this.observacoesMigracao;
    }

    public void setObservacoesMigracao(String observacoesMigracao) {
        this.observacoesMigracao = observacoesMigracao;
    }

    public Date getTsInclusao() {
        return this.tsInclusao;
    }

    public void setTsInclusao(Date tsInclusao) {
        this.tsInclusao = tsInclusao;
    }

    public Date getTsAtualizacao() {
        return this.tsAtualizacao;
    }

    public void setTsAtualizacao(Date tsAtualizacao) {
        this.tsAtualizacao = tsAtualizacao;
    }

    public String getCpfResponsavel() {
		return cpfResponsavel;
	}
    
    public void setCpfResponsavel(String cpfResponsavel) {
		this.cpfResponsavel = cpfResponsavel;
	}

    public Set<OcorrenciaDocumentacao> getOcorrenciaDocumentacaos() {
        return this.ocorrenciaDocumentacaos;
    }

    public void setOcorrenciaDocumentacaos(
        Set<OcorrenciaDocumentacao> ocorrenciaDocumentacaos) {
        this.ocorrenciaDocumentacaos = ocorrenciaDocumentacaos;
    }

    public Set<CoordenadaUtm> getCoordenadaUtms() {
        return this.coordenadaUtms;
    }

    public void setCoordenadaUtms(Set<CoordenadaUtm> coordenadaUtms) {
        this.coordenadaUtms = coordenadaUtms;
    }

    public Set<Confrontante> getConfrontantes() {
        return this.confrontantes;
    }

    public void setConfrontantes(Set<Confrontante> confrontantes) {
        this.confrontantes = confrontantes;
    }

    public Set<Avaliacao> getAvaliacaos() {
        return this.avaliacaos;
    }

    public void setAvaliacaos(Set<Avaliacao> avaliacaos) {
        this.avaliacaos = avaliacaos;
    }

    public Set<Documentacao> getDocumentacaos() {
        return this.documentacaos;
    }

    public void setDocumentacaos(Set<Documentacao> documentacaos) {
        this.documentacaos = documentacaos;
    }

    public Set<Edificacao> getEdificacaos() {
        return this.edificacaos;
    }

    public void setEdificacaos(Set<Edificacao> edificacaos) {
        this.edificacaos = edificacaos;
    }

    public Set<Quadra> getQuadras() {
        return this.quadras;
    }

    public void setQuadras(Set<Quadra> quadras) {
        this.quadras = quadras;
    }

    public Set<LeiBemImovel> getLeiBemImovels() {
        return this.leiBemImovels;
    }

    public void setLeiBemImovels(Set<LeiBemImovel> leiBemImovels) {
        this.leiBemImovels = leiBemImovels;
    }

    public Set<Ocupacao> getOcupacaosTerreno() {
        return this.ocupacaosTerreno;
    }

    public void setOcupacaosTerreno(Set<Ocupacao> ocupacaos) {
        this.ocupacaosTerreno = ocupacaos;
    }
    

    @Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BemImovel other = (BemImovel) obj;
		if (codBemImovel == null) {
			if (other.codBemImovel != null)
				return false;
		} else if (!codBemImovel.equals(other.codBemImovel))
			return false;
		return true;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codBemImovel == null) ? 0 : codBemImovel.hashCode());
		return result;
	}
    
    @Transient
	public String getDescricaoSituacaoLocal() {
    	return Dominios.situacaoLocal.getSituacaoLocalByIndex(Integer.valueOf(this.situacaoLocal)).getLabel();
    }
    
   	public void setCodMunicipio(Integer codMunicipio) {
		this.codMunicipio = codMunicipio;
	}

	public Integer getCodMunicipio() {
		return codMunicipio;
	}

	public void setAdministracao(Integer administracao) {
		this.administracao = administracao;
	}

	public Integer getAdministracao() {
		return administracao;
	}
	
	public String getDescricaoAdministracao(){
		if ( this.administracao!= null){
			return administracaoImovel.getAdministracaoImovelByIndex(this.administracao).getLabel();
		}
		else{
			return "";
		}
	}

	@Transient
	public String getDescricaoOrgao() {
		if (this.orgao != null) {
			return this.orgao.getSigla().concat(" - ").concat(this.orgao.getDescricao());
		}
		return "";
	}
	
	@Transient
	public List<OcorrenciaDocumentacaoExibirBemImovelDTO> getListaOcorrencias() {
		List<OcorrenciaDocumentacaoExibirBemImovelDTO> listaOcorrenciaDocumentacao = new ArrayList<OcorrenciaDocumentacaoExibirBemImovelDTO>();
		if (this.documentacaos != null) {
			for (Documentacao documentacao : this.documentacaos) {
				for (OcorrenciaDocumentacao ocorrencia : documentacao.getOcorrenciaDocumentacaos()) {
					OcorrenciaDocumentacaoExibirBemImovelDTO ocorrenciaDocumentacao = new OcorrenciaDocumentacaoExibirBemImovelDTO();
					ocorrenciaDocumentacao.setDescricao(ocorrencia.getDescricao());
					ocorrenciaDocumentacao.setDetalhesRelatorio(documentacao.getDetalhesRelatorio());
					ocorrenciaDocumentacao.setDescricaoDocumentacao(documentacao.getDescricao());
					ocorrenciaDocumentacao.setTsInclusao(documentacao.getTsInclusao());
					ocorrenciaDocumentacao.setCartorio(documentacao.getCartorio() != null ? documentacao.getCartorio().getDescricao() : "");
					ocorrenciaDocumentacao.setNumDocCartorial(documentacao.getNumeroDocumentoCartorial());
					ocorrenciaDocumentacao.setAnexo(documentacao.getAnexo());
					ocorrenciaDocumentacao.setNiif(documentacao.getNiif());
					ocorrenciaDocumentacao.setNirf(documentacao.getNirf());
					ocorrenciaDocumentacao.setNumDocTabelional(documentacao.getNumeroDocumentoTabelional());
					ocorrenciaDocumentacao.setIncra(documentacao.getIncra());
					ocorrenciaDocumentacao.setTabelionato(documentacao.getTabelionato() != null ? documentacao.getTabelionato().getDescricao() : "");
					listaOcorrenciaDocumentacao.add(ocorrenciaDocumentacao);
				}
			}
		}
		return listaOcorrenciaDocumentacao;
	}
	
	@Transient
	public List<DocumentacaoNotificacaoExibirBemImovelDTO> getListaDocumentacaoNotificacao() {
		List<DocumentacaoNotificacaoExibirBemImovelDTO> listaDocumentacaoNotificacao = new ArrayList<DocumentacaoNotificacaoExibirBemImovelDTO>();
		if (this.documentacaos != null) {
			for (Documentacao documentacao : this.documentacaos) {
				for (Notificacao notificacao : documentacao.getNotificacaos()) {
					DocumentacaoNotificacaoExibirBemImovelDTO documentacaoNotificacao = new DocumentacaoNotificacaoExibirBemImovelDTO();
					documentacaoNotificacao.setDescricao(documentacao.getDescricao());
					documentacaoNotificacao.setDetalhesRelatorio(documentacao.getDetalhesRelatorio());
					documentacaoNotificacao.setDataNotificacao(notificacao.getDataNotificacao());
					documentacaoNotificacao.setDataSolucao(notificacao.getDataSolucao());
					documentacaoNotificacao.setPrazoNotificacao(notificacao.getPrazoNotificacao());
					documentacaoNotificacao.setCartorio(documentacao.getCartorio() != null ? documentacao.getCartorio().getDescricao() : "");
					documentacaoNotificacao.setNumDocCartorial(documentacao.getNumeroDocumentoCartorial());
					documentacaoNotificacao.setAnexo(documentacao.getAnexo());
					documentacaoNotificacao.setNiif(documentacao.getNiif());
					documentacaoNotificacao.setNirf(documentacao.getNirf());
					documentacaoNotificacao.setNumDocTabelional(documentacao.getNumeroDocumentoTabelional());
					documentacaoNotificacao.setIncra(documentacao.getIncra());
					documentacaoNotificacao.setTabelionato(documentacao.getTabelionato() != null ? documentacao.getTabelionato().getDescricao() : "");
					listaDocumentacaoNotificacao.add(documentacaoNotificacao);
				}
			}
		}
		return listaDocumentacaoNotificacao;
	}
	
	@Transient
	public List<DocumentacaoSemNotificacaoExibirBemImovelDTO> getListaDocumentacaoSemNotificacao() {
		List<DocumentacaoSemNotificacaoExibirBemImovelDTO> listaDocumentacaoSemNotificacao = new ArrayList<DocumentacaoSemNotificacaoExibirBemImovelDTO>();
		if (this.documentacaos != null) {
			for (Documentacao documentacao : this.documentacaos) {
				if (!Util.validarLista(documentacao.getOcorrenciaDocumentacaos()) && !Util.validarLista(documentacao.getNotificacaos())) {
					DocumentacaoSemNotificacaoExibirBemImovelDTO documentacaoSemNotificacao = new DocumentacaoSemNotificacaoExibirBemImovelDTO();
					documentacaoSemNotificacao.setDescricao(documentacao.getDescricao().trim());
					documentacaoSemNotificacao.setDetalhesRelatorio(documentacao.getDetalhesRelatorio().trim());
					documentacaoSemNotificacao.setTsInclusao(documentacao.getTsInclusao());
					documentacaoSemNotificacao.setCartorio(documentacao.getCartorio() != null ? documentacao.getCartorio().getDescricao() : "");
					documentacaoSemNotificacao.setNumDocCartorial(documentacao.getNumeroDocumentoCartorial());
					documentacaoSemNotificacao.setNiif(documentacao.getNiif());
					documentacaoSemNotificacao.setNirf(documentacao.getNirf());
					documentacaoSemNotificacao.setNumDocTabelional(documentacao.getNumeroDocumentoTabelional());
					documentacaoSemNotificacao.setAnexo(documentacao.getAnexo());
					documentacaoSemNotificacao.setIncra(documentacao.getIncra());
					documentacaoSemNotificacao.setTabelionato(documentacao.getTabelionato() != null ? documentacao.getTabelionato().getDescricao() : "");
					listaDocumentacaoSemNotificacao.add(documentacaoSemNotificacao);
				}	
			}
		}
		return listaDocumentacaoSemNotificacao;
	}
	
	@Transient
	public List<LoteExibirBemImovelDTO> getListaQuadrasLotes() {
		List<LoteExibirBemImovelDTO> listaQuadrasLotes = new ArrayList<LoteExibirBemImovelDTO>();
		if (this.quadras != null) {
			for (Quadra quadra : this.quadras) {
				for (Lote lote : quadra.getLotes()) {
					LoteExibirBemImovelDTO loteBemImovel = new LoteExibirBemImovelDTO();
					loteBemImovel.setCodLote(lote.getCodLote());
					loteBemImovel.setLote(lote.getDescricao());
					loteBemImovel.setQuadra(quadra.getDescricao());
					listaQuadrasLotes.add(loteBemImovel);
				}
			}
		}
		return listaQuadrasLotes;
	}

	public void setSomenteTerreno(String somenteTerreno) {
		this.somenteTerreno = somenteTerreno;
	}



	public String getSomenteTerreno() {
		return somenteTerreno;
	}

	
	public BemImovel getInstanciaAtual() {
		return this;
	}



	public void setListaDoacao(Set<Doacao> listaDoacao) {
		this.listaDoacao = listaDoacao;
	}



	public Set<Doacao> getListaDoacao() {
		return listaDoacao;
	}



	public Set<Vistoria> getListaVistoria() {
		return listaVistoria;
	}



	public void setListaVistoria(Set<Vistoria> listaVistoria) {
		this.listaVistoria = listaVistoria;
	}



	public void setListaTransferencia(Set<Transferencia> listaTransferencia) {
		this.listaTransferencia = listaTransferencia;
	}



	public Set<Transferencia> getListaTransferencia() {
		return listaTransferencia;
	}
	
    @Transient
    public String getMunicipioEstado(){
    	if (getMunicipio() == null){
    		setMunicipio("");
    	}
    	if (getUf() == null){
    		setUf("");
    	}
		return   getMunicipio().concat("/").concat(getUf());
    }

    @Transient
    public String getSomenteTerrenoDescricao(){
    	String ret = "";
    	if (getSomenteTerreno() == null){
    		ret = " - "; 
    	}
    	if (getSomenteTerreno().equals("S")){
    		ret = "Sim"; 
    	}
    	if (getSomenteTerreno().equals("N")){
    		ret = "N�o"; 
    	}
    	return ret;
    }

    @Transient
    public String getAreaTerrenoFormatado(){
    	return Valores.formatarParaDecimal(this.areaTerreno, 2);
    }

    @Transient
    public String getAreaConstruidaFormatado(){
    	return Valores.formatarParaDecimal(this.areaConstruida, 2);
    }



	public Instituicao getInstituicao() {
		return instituicao;
	}



	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}



	public void setNrBemImovel(Integer nrBemImovel) {
		this.nrBemImovel = nrBemImovel;
	}



	public Integer getNrBemImovel() {
		return nrBemImovel;
	}

}
