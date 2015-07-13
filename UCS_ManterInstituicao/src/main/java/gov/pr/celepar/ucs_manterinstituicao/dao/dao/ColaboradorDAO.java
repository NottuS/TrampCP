package gov.pr.celepar.ucs_manterinstituicao.dao.dao;

import java.util.Collection;
import java.util.Date;

import gov.pr.celepar.framework.database.GenericDAO;
import gov.pr.celepar.framework.exception.ApplicationException;
import gov.pr.celepar.ucs_manterinstituicao.pojo.Colaborador;

public interface ColaboradorDAO extends GenericDAO<Colaborador, Integer>{

	Long buscarQtdLista(Colaborador colaborador, Date dataInicial, Date dataFinal, String situacao)
			throws ApplicationException ;
	public Collection<Colaborador> listar(Colaborador colaborador, Date dataInicial, Date dataFinal, String situacao,
			Integer quantidade, Integer paginaAtual) throws ApplicationException;
}
