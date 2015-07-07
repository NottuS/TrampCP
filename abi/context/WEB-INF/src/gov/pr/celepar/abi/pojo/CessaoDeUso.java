package gov.pr.celepar.abi.pojo;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

public class CessaoDeUso implements java.io.Serializable {

	private static final long serialVersionUID = -255227520874123072L;
	private Integer codCessaoDeUso;
	private LeiBemImovel leiBemImovel;
	private Instituicao instituicao;
	private String numeroProjetoDeLei;
	private CessaoDeUso cessaoDeUsoOriginal;
	private String protocolo;
    private Date dataInicioVigencia;
    private Date dataFinalVigenciaPrevisao;
    private Date dataFinalVigencia;
	private String motivoRevogacaoDevolucao;
	private Date tsInclusao;
	private Date tsAlteracao;
	private String cpfResponsavelInclusao;
	private String cpfResponsavelAlteracao;
	private Orgao orgaoCedente;
	private Orgao orgaoCessionario;
    private BemImovel bemImovel;
    private StatusTermo statusTermo;
    private Date dataRegistro;
    private Set<AssinaturaCessaoDeUso> listaAssinaturaCessaoDeUso = new HashSet<AssinaturaCessaoDeUso>(0);
    private Set<ItemCessaoDeUso> listaItemCessaoDeUso = new HashSet<ItemCessaoDeUso>(0);
    
    public CessaoDeUso(){
    	
    }

	public Integer getCodCessaoDeUso() {
		return codCessaoDeUso;
	}

	public void setCodCessaoDeUso(Integer codCessaoDeUso) {
		this.codCessaoDeUso = codCessaoDeUso;
	}

	public LeiBemImovel getLeiBemImovel() {
		return leiBemImovel;
	}

	public void setLeiBemImovel(LeiBemImovel leiBemImovel) {
		this.leiBemImovel = leiBemImovel;
	}

	public String getNumeroProjetoDeLei() {
		return numeroProjetoDeLei;
	}

	public void setNumeroProjetoDeLei(String numeroProjetoDeLei) {
		this.numeroProjetoDeLei = numeroProjetoDeLei;
	}

	public CessaoDeUso getCessaoDeUsoOriginal() {
		return cessaoDeUsoOriginal;
	}

	public void setCessaoDeUsoOriginal(CessaoDeUso cessaoDeUsoOriginal) {
		this.cessaoDeUsoOriginal = cessaoDeUsoOriginal;
	}

	public String getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
	}

	public Date getDataInicioVigencia() {
		return dataInicioVigencia;
	}

	public void setDataInicioVigencia(Date dataInicioVigencia) {
		this.dataInicioVigencia = dataInicioVigencia;
	}

	public Date getDataFinalVigenciaPrevisao() {
		return dataFinalVigenciaPrevisao;
	}

	public void setDataFinalVigenciaPrevisao(Date dataFinalVigenciaPrevisao) {
		this.dataFinalVigenciaPrevisao = dataFinalVigenciaPrevisao;
	}

	public Date getDataFinalVigencia() {
		return dataFinalVigencia;
	}

	public void setDataFinalVigencia(Date dataFinalVigencia) {
		this.dataFinalVigencia = dataFinalVigencia;
	}

	public String getMotivoRevogacaoDevolucao() {
		return motivoRevogacaoDevolucao;
	}

	public void setMotivoRevogacaoDevolucao(String motivoRevogacaoDevolucao) {
		this.motivoRevogacaoDevolucao = motivoRevogacaoDevolucao;
	}

	public Date getTsInclusao() {
		return tsInclusao;
	}

	public void setTsInclusao(Date tsInclusao) {
		this.tsInclusao = tsInclusao;
	}

	public Date getTsAlteracao() {
		return tsAlteracao;
	}

	public void setTsAlteracao(Date tsAlteracao) {
		this.tsAlteracao = tsAlteracao;
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

	public BemImovel getBemImovel() {
		return bemImovel;
	}

	public void setBemImovel(BemImovel bemImovel) {
		this.bemImovel = bemImovel;
	}

	public StatusTermo getStatusTermo() {
		return statusTermo;
	}

	public void setStatusTermo(StatusTermo statusTermo) {
		this.statusTermo = statusTermo;
	}

	public Date getDataRegistro() {
		return dataRegistro;
	}

	public void setDataRegistro(Date dataRegistro) {
		this.dataRegistro = dataRegistro;
	}

	public Set<AssinaturaCessaoDeUso> getListaAssinaturaCessaoDeUso() {
		return listaAssinaturaCessaoDeUso;
	}

	public void setListaAssinaturaCessaoDeUso(
			Set<AssinaturaCessaoDeUso> listaAssinaturaCessaoDeUso) {
		this.listaAssinaturaCessaoDeUso = listaAssinaturaCessaoDeUso;
	}

	public Set<ItemCessaoDeUso> getListaItemCessaoDeUso() {
		return listaItemCessaoDeUso;
	}

	public void setListaItemCessaoDeUso(Set<ItemCessaoDeUso> listaItemCessaoDeUso) {
		this.listaItemCessaoDeUso = listaItemCessaoDeUso;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CessaoDeUso other = (CessaoDeUso) obj;
		if (codCessaoDeUso == null) {
			if (other.codCessaoDeUso != null)
				return false;
		} else if (!codCessaoDeUso.equals(other.codCessaoDeUso))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codCessaoDeUso == null) ? 0 : codCessaoDeUso.hashCode());
		return result;
	}
	
	
	@Transient
	public CessaoDeUso getInstanciaAtual() {
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
		if (this.getDataInicioVigencia() != null){
			cal.setTime(this.getDataInicioVigencia());	
		}else{
			cal.setTime(this.getTsInclusao());
		}
		Integer ano = cal.get(Calendar.YEAR);
		return codCessaoDeUso.toString().concat("/".concat(ano.toString()));
	}

	public Instituicao getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}

}
