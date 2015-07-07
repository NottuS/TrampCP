package gov.pr.celepar.ucs_manterinstituicao.form;

import gov.pr.celepar.framework.exception.ApplicationException;
import gov.pr.celepar.ucs_manterinstituicao.pojo.AreaInteresse;
import gov.pr.celepar.ucs_manterinstituicao.pojo.Instituicao;
import gov.pr.celepar.ucs_manterinstituicao.pojo.Telefone;

import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.struts.validator.ValidatorForm;

public class ManterInstituicaoForm extends ValidatorForm {
	private static Logger log = Logger.getLogger(ManterInstituicaoForm.class);
	private static final long serialVersionUID = 9159101547035595149L;
	private String codInstituicao;
	private String cnpj;
	private String razaoSocial;
	private String naturezaJuridica;
	private String porte;
	private String dataCadastroInicio;
	private String dataCadastroFim;
	private String actionType;
	private String[] telefones;
	private String[] areasInteresseSelecionadas;

	public String[] getTelefones() {
		return telefones;
	}

	public void setTelefones(String[] telefones) {
		this.telefones = telefones;
	}

	public String[] getAreasInteresseSelecionadas() {
		return areasInteresseSelecionadas;
	}

	public void setAreasInteresseSelecionadas(String[] areasInteresse) {
		this.areasInteresseSelecionadas = areasInteresse;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getNaturezaJuridica() {
		return naturezaJuridica;
	}

	public void setNaturezaJuridica(String naturezaJuridica) {
		this.naturezaJuridica = naturezaJuridica;
	}

	public String getPorte() {
		return porte;
	}

	public void setPorte(String porte) {
		this.porte = porte;
	}

	public String getDataCadastroInicio() {
		return dataCadastroInicio;
	}

	public void setDataCadastroInicio(String dataCadastroInicio) {
		this.dataCadastroInicio = dataCadastroInicio;
	}

	public String getDataCadastroFim() {
		return dataCadastroFim;
	}

	public void setDataCadastroFim(String dataCadastroFim) {
		this.dataCadastroFim = dataCadastroFim;
	}

	public String getCodInstituicao() {
		return codInstituicao;
	}

	public void setCodInstituicao(String codInstituicao) {
		this.codInstituicao = codInstituicao;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String action) {
		this.actionType = action;
	}

	public void preencheInstituicao(Instituicao instituicao)
			throws ApplicationException {
		ManterInstituicaoForm mif = this;
		try {
			mif.setCnpj(instituicao.getCnpj());
			mif.setPorte(instituicao.getPorte().toString());
			mif.setNaturezaJuridica(instituicao.getNaturezaJuridica()
					.getCodNaturezaJuridica().toString());
			mif.setRazaoSocial(instituicao.getRazaoSocial());
			mif.setTelefones(new String[instituicao.getInstituicaoTelefones().size()]);
			mif.setAreasInteresseSelecionadas(new String[instituicao
					.getAreaInteresses().size()]);
			int i = 0;
			for (Telefone telefone : instituicao.getInstituicaoTelefones()) {
				mif.getTelefones()[i] = telefone.getTelefone();
				i++;
			}
			i = 0;
			for (AreaInteresse areaInteresse : instituicao.getAreaInteresses()) {
				mif.getAreasInteresseSelecionadas()[i] = areaInteresse
						.getDescricao();
				i++;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e.getCause());
			throw new ApplicationException("Erro ao preencher form", e);
		}
	}

	public Instituicao convertToInstituicao() throws ApplicationException {
		Instituicao instituicao = new Instituicao();

		try {
			
			if(this.getCodInstituicao() == null || this.getCodInstituicao().isEmpty()){
				instituicao.setCodInstituicao(null);
			} else {
				instituicao.setCodInstituicao(Integer.parseInt(this.getCodInstituicao()));
			}
			if(this.getCnpj() == null || this.getCnpj().isEmpty()){
				instituicao.setCnpj(null);
			} else {
				instituicao.setCnpj(this.cnpj);
			}
			instituicao.setRazaoSocial(this.getRazaoSocial());
			if (this.getPorte() == null || this.getPorte().isEmpty()) {
				instituicao.setPorte(null);
			} else {
				instituicao.setPorte(Integer.parseInt(this.getPorte()));
			}
			instituicao.setDataCriacao(new Date());
		} catch (Exception e) {
			log.error(e.getMessage(), e.getCause());
			throw new ApplicationException("Erro ao converter form", e);
		}

		return instituicao;
	}
}
