package gov.pr.celepar.ucs_manterinstituicao.form;

import gov.pr.celepar.ucs_manterinstituicao.pojo.Colaborador;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.struts.validator.ValidatorForm;

public class ManterColaboradorForm extends ValidatorForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7657375669443324L;
	private String idColaborador;
	private String cpf;
	private String rg;
	private String nome;
	private String sexo;
	private String dataNascimento;
	private String telefone;
	private String celular;
	private String email;
	private String instituicao;
	private String dataAdmissao;
	private String dataDemissao;
	private String dataInicial;
	private String dataFinal;
	private String situacao;
	private String actionType;
	
	public ManterColaboradorForm() {
	}
	
	public String getIdColaborador() {
		return idColaborador;
	}
	public void setIdColaborador(String idColaborador) {
		this.idColaborador = idColaborador;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getRg() {
		return rg;
	}
	public void setRg(String rg) {
		this.rg = rg;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public String getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getInstituicao() {
		return instituicao;
	}
	public void setInstituicao(String instituicao) {
		this.instituicao = instituicao;
	}
	public String getDataAdmissao() {
		return dataAdmissao;
	}
	public void setDataAdmissao(String dataAdmissao) {
		this.dataAdmissao = dataAdmissao;
	}
	public String getDataDemissao() {
		return dataDemissao;
	}
	public void setDataDemissao(String dataDemissao) {
		this.dataDemissao = dataDemissao;
	}
	public String getDataInicial() {
		return dataInicial;
	}
	public void setDataInicial(String dataInicial) {
		this.dataInicial = dataInicial;
	}
	public String getDataFinal() {
		return dataFinal;
	}
	public void setDataFinal(String dataFinal) {
		this.dataFinal = dataFinal;
	}
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	
	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public void preencheColaborador(Colaborador colaborador){
		this.setIdColaborador(colaborador.getIdColaborador() == null ? 
				null : colaborador.getIdColaborador().toString());
		this.setCpf(colaborador.getCpf());
		this.setRg(colaborador.getRg());
		this.setNome(colaborador.getNome());
		this.setSexo(colaborador.getIndSexo() == null ? 
				null : colaborador.getIndSexo().toString());
		SimpleDateFormat smf = new SimpleDateFormat("dd/MM/yy");
		this.setDataNascimento(colaborador.getDataNascimento() == null ? 
				null : smf.format(colaborador.getDataNascimento()));
		this.setDataAdmissao(colaborador.getDataAdmissao() == null ? 
				null : smf.format(colaborador.getDataAdmissao()));
		this.setDataDemissao(colaborador.getDataDEmissao() == null ? 
				null : smf.format(colaborador.getDataDEmissao()));
		this.setCelular(colaborador.getCelular());
		this.setTelefone(colaborador.getTelefone());
		
		this.setEmail(colaborador.getEmail());
		this.setInstituicao(colaborador.getInstituicao().getCodInstituicao() == null ? 
				null : colaborador.getInstituicao().getCodInstituicao().toString());
	}
	
	public Colaborador convertToColaborador(Colaborador colaborador) throws ParseException{
		if(colaborador == null){
			colaborador = new Colaborador();
		}
		
		if(this.getIdColaborador() == null || this.getIdColaborador().isEmpty()){
			colaborador.setIdColaborador(null);
		} else {
			colaborador.setIdColaborador(Integer.parseInt(this.getIdColaborador()));
		}
		
		colaborador.setCpf(this.getCpf());
		colaborador.setNome(this.getNome());
		colaborador.setRg(this.getRg());
		if(this.getSexo() == null || this.getSexo().isEmpty()){
			colaborador.setIndSexo(null);
		}else{
			colaborador.setIndSexo(Boolean.parseBoolean(this.getSexo()));
		}
		
		SimpleDateFormat smf = new SimpleDateFormat("dd/MM/yy");
		if(this.getDataNascimento() == null || this.getDataNascimento().isEmpty()){
			colaborador.setDataNascimento(null);
		} else {
			colaborador.setDataNascimento(smf.parse(this.getDataNascimento()));
		}
		
		if(this.getDataAdmissao() == null || this.getDataAdmissao().isEmpty()){
			colaborador.setDataAdmissao(null);
		} else {
			colaborador.setDataAdmissao(smf.parse(this.getDataAdmissao()));
		}
		
		if(this.getDataDemissao() == null || this.getDataDemissao().isEmpty()){
			colaborador.setDataDEmissao(null);
		} else {
			colaborador.setDataDEmissao(smf.parse(this.getDataDemissao()));
		}
		
		colaborador.setTelefone(this.getTelefone());

		colaborador.setCelular(this.getCelular());
		
		colaborador.setEmail(this.getEmail());
		
		return colaborador;
	}
	
}
