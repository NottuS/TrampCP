package gov.pr.celepar.abi.pojo;


public class ParametroAgendaEmail implements java.io.Serializable {

	private static final long serialVersionUID = 933963474672049417L;
	private Integer codParametroAgendaEmail;
    private String email;
    private ParametroAgenda parametroAgenda;
    
    public ParametroAgendaEmail() {
    }


	public ParametroAgendaEmail getInstanciaAtual() {
		return this;
	}

	
	public Integer getCodParametroAgendaEmail() {
		return codParametroAgendaEmail;
	}


	public void setCodParametroAgendaEmail(Integer codParametroAgendaEmail) {
		this.codParametroAgendaEmail = codParametroAgendaEmail;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public ParametroAgenda getParametroAgenda() {
		return parametroAgenda;
	}


	public void setParametroAgenda(ParametroAgenda parametroAgenda) {
		this.parametroAgenda = parametroAgenda;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ParametroAgendaEmail other = (ParametroAgendaEmail) obj;
		if (codParametroAgendaEmail == null) {
			if (other.codParametroAgendaEmail != null)
				return false;
		} else if (!codParametroAgendaEmail.equals(other.codParametroAgendaEmail))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codParametroAgendaEmail == null) ? 0 : codParametroAgendaEmail.hashCode());
		return result;
	}
}
