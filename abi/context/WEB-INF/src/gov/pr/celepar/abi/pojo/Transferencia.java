package gov.pr.celepar.abi.pojo;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

public class Transferencia implements java.io.Serializable {

	private static final long serialVersionUID = 7129373458515002165L;
	private Integer codTransferencia;
    private BemImovel bemImovel;
    private Instituicao instituicao;
    private Orgao orgaoCedente;
    private Orgao orgaoCessionario;
    private String protocolo;
    private StatusTermo statusTermo;
    private Date tsInclusao;
    private Date tsAtualizacao;
    private Date tsExclusao;
    private String cpfResponsavelInclusao;
    private String cpfResponsavelAlteracao;
    private String cpfResponsavelExclusao;
    private String motivoRevogacao;
    private Integer nrOficio;
    private Date dataRegistro;
    private Date dtInicioVigencia;
    private Date dtFimVigencia;
    private String textoDocInformacao;
    private Set<AssinaturaTransferencia> listaAssinaturaTransferencia = new HashSet<AssinaturaTransferencia>(0);
    private Set<AssinaturaDocTransferencia> listaAssinaturaDocTransferencia = new HashSet<AssinaturaDocTransferencia>(0);
    private Set<ItemTransferencia> listaItemTransferencia = new HashSet<ItemTransferencia>(0);
    
    public Transferencia(){
    	
    }
 
	public Transferencia(Integer codTransferencia, BemImovel bemImovel,
			Orgao orgaoCedente, Orgao orgaoCessionario, Date tsInclusao,  
			String cpfResponsavelInclusao) {
		super();
		this.codTransferencia = codTransferencia;
		this.bemImovel = bemImovel;
		this.orgaoCedente = orgaoCedente;
		this.orgaoCessionario = orgaoCessionario;
		this.tsInclusao = tsInclusao;
		this.cpfResponsavelInclusao = cpfResponsavelInclusao;
	}

	
	public Transferencia(Integer codTransferencia, BemImovel bemImovel,
			Orgao orgaoCedente, Orgao orgaoCessionario, String protocolo,
			StatusTermo statusTermo, Date tsInclusao, Date tsAtualizacao,
			Date tsExclusao, String cpfResponsavelInclusao,
			String cpfResponsavelAlteracao, String cpfResponsavelExclusao,
			String motivoRevogacao, Integer nrOficio, Date dataRegistro,
			Date dtInicioVigencia, Date dtFimVigencia) {
		super();
		this.codTransferencia = codTransferencia;
		this.bemImovel = bemImovel;
		this.orgaoCedente = orgaoCedente;
		this.orgaoCessionario = orgaoCessionario;
		this.protocolo = protocolo;
		this.statusTermo = statusTermo;
		this.tsInclusao = tsInclusao;
		this.tsAtualizacao = tsAtualizacao;
		this.tsExclusao = tsExclusao;
		this.cpfResponsavelInclusao = cpfResponsavelInclusao;
		this.cpfResponsavelAlteracao = cpfResponsavelAlteracao;
		this.cpfResponsavelExclusao = cpfResponsavelExclusao;
		this.motivoRevogacao = motivoRevogacao;
		this.nrOficio = nrOficio;
		this.dataRegistro = dataRegistro;
		this.dtInicioVigencia = dtInicioVigencia;
		this.dtFimVigencia = dtFimVigencia;
	}


	public Integer getCodTransferencia() {
		return codTransferencia;
	}

	public void setCodTransferencia(Integer codTransferencia) {
		this.codTransferencia = codTransferencia;
	}

	public BemImovel getBemImovel() {
		return bemImovel;
	}

	public void setBemImovel(BemImovel bemImovel) {
		this.bemImovel = bemImovel;
	}

	public Orgao getOrgaoCedente() {
		return orgaoCedente;
	}

	public void setOrgaoCedente(Orgao orgaoCedente) {
		this.orgaoCedente = orgaoCedente;
	}

	public Orgao getOrgaoCessionario() {
		return orgaoCessionario;
	}

	public void setOrgaoCessionario(Orgao orgaoCessionario) {
		this.orgaoCessionario = orgaoCessionario;
	}

	public String getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
	}

	public StatusTermo getStatusTermo() {
		return statusTermo;
	}

	public void setStatusTermo(StatusTermo statusTermo) {
		this.statusTermo = statusTermo;
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

	public Set<AssinaturaTransferencia> getListaAssinaturaTransferencia() {
		return listaAssinaturaTransferencia;
	}

	public void setListaAssinaturaTransferencia(
			Set<AssinaturaTransferencia> listaAssinaturaTransferencia) {
		this.listaAssinaturaTransferencia = listaAssinaturaTransferencia;
	}

	public Set<ItemTransferencia> getListaItemTransferencia() {
		return listaItemTransferencia;
	}

	public void setListaItemTransferencia(
			Set<ItemTransferencia> listaItemTransferencia) {
		this.listaItemTransferencia = listaItemTransferencia;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transferencia other = (Transferencia) obj;
		if (codTransferencia == null) {
			if (other.codTransferencia != null)
				return false;
		} else if (!codTransferencia.equals(other.codTransferencia))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codTransferencia == null) ? 0 : codTransferencia.hashCode());
		return result;
	}
	
	/**
	 * Faz mascara para o atributo protocolo '999.999.999-0'.<BR>
	 * @author ginaalmeida
	 * @since 28/07/2011
	 * @return String
	 */
	@Transient
	public String getProtocoloFormatado() {
		
		if(StringUtils.isNotBlank(this.protocolo)){
			
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
		return codTransferencia.toString().concat("/".concat(ano.toString()));
	}

	@Transient
	public Transferencia getInstanciaAtual() {
		return this;
	}

	public void setTextoDocInformacao(String textoDocInformacao) {
		this.textoDocInformacao = textoDocInformacao;
	}

	public String getTextoDocInformacao() {
		return textoDocInformacao;
	}

	public Instituicao getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}

	public void setListaAssinaturaDocTransferencia(
			Set<AssinaturaDocTransferencia> listaAssinaturaDocTransferencia) {
		this.listaAssinaturaDocTransferencia = listaAssinaturaDocTransferencia;
	}

	public Set<AssinaturaDocTransferencia> getListaAssinaturaDocTransferencia() {
		return listaAssinaturaDocTransferencia;
	}
	
}