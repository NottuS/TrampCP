package gov.pr.celepar.abi.pojo;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

public class Doacao implements java.io.Serializable {

	private static final long serialVersionUID = -255227520874123072L;
	private Integer codDoacao;
    private BemImovel bemImovel;
    private Orgao orgaoProprietario;
    private Integer administracao;
    private Orgao orgaoResponsavel;
    private String protocolo;
    private LeiBemImovel leiBemImovel;
    private Instituicao instituicao;
    private Date tsInclusao;
    private String cpfResponsavelInclusao;
    private Date tsAtualizacao;
    private String cpfResponsavelAlteracao;
    private Date tsExclusao;
    private String cpfResponsavelExclusao;
    private String motivoRevogacao;
    private Integer nrOficio;
    private Date dataRegistro;
    private StatusTermo statusTermo;
    private Date dtInicioVigencia;
    private Date dtFimVigencia;
    private Integer nrProjetoLei;
    private Set<AssinaturaDoacao> listaAssinaturaDoacao = new HashSet<AssinaturaDoacao>(0);
    private Set<ItemDoacao> listaItemDoacao = new HashSet<ItemDoacao>(0);
    
    public Doacao(){
    	
    }
    public Doacao(Integer codDoacao, BemImovel bemImovel,
			Orgao orgaoProprietario, Integer administracao,
			Orgao orgaoResponsavel, Date tsInclusao,
			String cpfResponsavelInclusao) {
		super();
		this.codDoacao = codDoacao;
		this.bemImovel = bemImovel;
		this.orgaoProprietario = orgaoProprietario;
		this.administracao = administracao;
		this.orgaoResponsavel = orgaoResponsavel;
		this.tsInclusao = tsInclusao;
		this.cpfResponsavelInclusao = cpfResponsavelInclusao;
	}


	public Doacao(Integer codDoacao, BemImovel bemImovel,
			Orgao orgaoProprietario, Integer administracao,
			Orgao orgaoResponsavel, String protocolo,
			LeiBemImovel leiBemImovel, Date tsInclusao,
			String cpfResponsavelInclusao, Date tsAtualizacao,
			String cpfResponsavelAlteracao, Date tsExclusao,
			String cpfResponsavelExclusao, String motivoRevogacao,
			Integer nrOficio, Date dataRegistro, StatusTermo statusTermo,
			Date dtInicioVigencia, Date dtFimVigencia,
			Integer nrProjetoLei) {
		super();
		this.codDoacao = codDoacao;
		this.bemImovel = bemImovel;
		this.orgaoProprietario = orgaoProprietario;
		this.administracao = administracao;
		this.orgaoResponsavel = orgaoResponsavel;
		this.protocolo = protocolo;
		this.leiBemImovel = leiBemImovel;
		this.tsInclusao = tsInclusao;
		this.cpfResponsavelInclusao = cpfResponsavelInclusao;
		this.tsAtualizacao = tsAtualizacao;
		this.cpfResponsavelAlteracao = cpfResponsavelAlteracao;
		this.tsExclusao = tsExclusao;
		this.cpfResponsavelExclusao = cpfResponsavelExclusao;
		this.motivoRevogacao = motivoRevogacao;
		this.nrOficio = nrOficio;
		this.dataRegistro = dataRegistro;
		this.statusTermo = statusTermo;
		this.dtInicioVigencia = dtInicioVigencia;
		this.dtFimVigencia = dtFimVigencia;
		this.nrProjetoLei = nrProjetoLei;
	}

	public Integer getCodDoacao() {
		return codDoacao;
	}

	public void setCodDoacao(Integer codDoacao) {
		this.codDoacao = codDoacao;
	}

	public BemImovel getBemImovel() {
		return bemImovel;
	}

	public void setBemImovel(BemImovel bemImovel) {
		this.bemImovel = bemImovel;
	}

	public Orgao getOrgaoProprietario() {
		return orgaoProprietario;
	}

	public void setOrgaoProprietario(Orgao orgaoProprietario) {
		this.orgaoProprietario = orgaoProprietario;
	}

	public Integer getAdministracao() {
		return administracao;
	}

	public void setAdministracao(Integer administracao) {
		this.administracao = administracao;
	}

	public Orgao getOrgaoResponsavel() {
		return orgaoResponsavel;
	}

	public void setOrgaoResponsavel(Orgao orgaoResponsavel) {
		this.orgaoResponsavel = orgaoResponsavel;
	}

	public LeiBemImovel getLeiBemImovel() {
		return leiBemImovel;
	}

	public void setLeiBemImovel(LeiBemImovel leiBemImovel) {
		this.leiBemImovel = leiBemImovel;
	}

	public String getMotivoRevogacao() {
		return motivoRevogacao;
	}

	public void setMotivoRevogacao(String motivoRevogacao) {
		this.motivoRevogacao = motivoRevogacao;
	}

	public Integer getNrOficio() {
		return nrOficio;
	}

	public void setNrOficio(Integer nrOficio) {
		this.nrOficio = nrOficio;
	}

	public Date getDataRegistro() {
		return dataRegistro;
	}

	public void setDataRegistro(Date dataRegistro) {
		this.dataRegistro = dataRegistro;
	}

	public Date getTsInclusao() {
		return tsInclusao;
	}

	public void setTsInclusao(Date tsInclusao) {
		this.tsInclusao = tsInclusao;
	}

	public Date getTsAtualizacao() {
		return tsAtualizacao;
	}

	public void setTsAtualizacao(Date tsAtualizacao) {
		this.tsAtualizacao = tsAtualizacao;
	}

	public Date getTsExclusao() {
		return tsExclusao;
	}

	public void setTsExclusao(Date tsExclusao) {
		this.tsExclusao = tsExclusao;
	}

	public String getCpfResponsavelInclusao() {
		return cpfResponsavelInclusao;
	}

	public void setCpfResponsavelInclusao(String cpfResponsavelInclusao) {
		this.cpfResponsavelInclusao = cpfResponsavelInclusao;
	}

	public String getCpfResponsavelAlteracao() {
		return cpfResponsavelAlteracao;
	}

	public void setCpfResponsavelAlteracao(String cpfResponsavelAlteracao) {
		this.cpfResponsavelAlteracao = cpfResponsavelAlteracao;
	}

	public String getCpfResponsavelExclusao() {
		return cpfResponsavelExclusao;
	}

	public void setCpfResponsavelExclusao(String cpfResponsavelExclusao) {
		this.cpfResponsavelExclusao = cpfResponsavelExclusao;
	}

	public StatusTermo getStatusTermo() {
		return statusTermo;
	}

	public void setStatusTermo(StatusTermo statusTermo) {
		this.statusTermo = statusTermo;
	}

	public String getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
	}

	public Date getDtInicioVigencia() {
		return dtInicioVigencia;
	}

	public void setDtInicioVigencia(Date dtInicioVigencia) {
		this.dtInicioVigencia = dtInicioVigencia;
	}

	public Date getDtFimVigencia() {
		return dtFimVigencia;
	}

	public void setDtFimVigencia(Date dtFimVigencia) {
		this.dtFimVigencia = dtFimVigencia;
	}

	public Integer getNrProjetoLei() {
		return nrProjetoLei;
	}

	public void setNrProjetoLei(Integer nrProjetoLei) {
		this.nrProjetoLei = nrProjetoLei;
	}

	public Set<AssinaturaDoacao> getListaAssinaturaDoacao() {
		return listaAssinaturaDoacao;
	}

	public void setListaAssinaturaDoacao(Set<AssinaturaDoacao> listaAssinaturaDoacao) {
		this.listaAssinaturaDoacao = listaAssinaturaDoacao;
	}

	public Set<ItemDoacao> getListaItemDoacao() {
		return listaItemDoacao;
	}

	public void setListaItemDoacao(Set<ItemDoacao> listaItemDoacao) {
		this.listaItemDoacao = listaItemDoacao;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Doacao other = (Doacao) obj;
		if (codDoacao == null) {
			if (other.codDoacao != null)
				return false;
		} else if (!codDoacao.equals(other.codDoacao))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codDoacao == null) ? 0 : codDoacao.hashCode());
		return result;
	}
	
	
	@Transient
	public Doacao getInstanciaAtual() {
		return this;
	}

	/**
	 * Faz mascara para o atributo protocolo '999.999.999-0'.<BR>
	 * @author ginaalmeida
	 * @since 28/07/2011
	 * @return String
	 */
	@Transient
	public String getProtocoloFormatado() {
		
		if(StringUtils.isNotBlank(protocolo)){
			
			Integer tamanho = this.protocolo.length();
			
			String digito = "";
			String parte1 = "";
			String parte2 = "";
			String parte3 = "";
			
			if(tamanho < Integer.valueOf(5)){
				digito = this.protocolo.substring(this.protocolo.length()-1, this.protocolo.length());
				
				String protocoloAux = this.protocolo.substring(0, this.protocolo.length()-1);
				parte1 = protocoloAux;
				
			} else if(tamanho < Integer.valueOf(8)){
				
				digito = this.protocolo.substring(this.protocolo.length()-1, this.protocolo.length());
				
				String protocoloAux = this.protocolo.substring(0, this.protocolo.length()-1);
				parte1 = protocoloAux.substring(protocoloAux.length()-3, protocoloAux.length());
				
				protocoloAux = protocoloAux.substring(0, protocoloAux.length()-3);
				parte2 = protocoloAux;
				
			} else{
				
				digito = this.protocolo.substring(this.protocolo.length()-1, this.protocolo.length());
				
				String protocoloAux = this.protocolo.substring(0, this.protocolo.length()-1);
				parte1 = protocoloAux.substring(protocoloAux.length()-3, protocoloAux.length());
				
				protocoloAux = protocoloAux.substring(0, protocoloAux.length()-3);
				parte2 = protocoloAux.substring(protocoloAux.length()-3, protocoloAux.length());
				
				protocoloAux = protocoloAux.substring(0, protocoloAux.length()-3);
				parte3 = protocoloAux;
				
			}
			
			StringBuffer retorno = new StringBuffer();
			
			if(StringUtils.isNotBlank(parte3)){
				retorno.append(parte3);
				retorno.append(".");
			}
			if(StringUtils.isNotBlank(parte2)){
				retorno.append(parte2);
				retorno.append(".");
			}
			if(StringUtils.isNotBlank(parte1)){
				retorno.append(parte1);
				retorno.append("-");
			}
			if(StringUtils.isNotBlank(digito)){
				retorno.append(digito);
			}
			
			return retorno.toString();
			
		} else{
			
			return "";
		}
		
	}
	
	@Transient
	public String getNumeroTermo() {
		Calendar cal = new GregorianCalendar();
		if (this.getDtInicioVigencia() != null){
			cal.setTime(this.getDtInicioVigencia());	
		}else{
			cal.setTime(this.getTsInclusao());
		}
		Integer ano = cal.get(Calendar.YEAR);
		return codDoacao.toString().concat("/".concat(ano.toString()));
	}
	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}
	public Instituicao getInstituicao() {
		return instituicao;
	}
	
}
