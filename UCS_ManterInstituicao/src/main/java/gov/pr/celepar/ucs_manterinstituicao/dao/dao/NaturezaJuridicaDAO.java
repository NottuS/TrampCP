package gov.pr.celepar.ucs_manterinstituicao.dao.dao;

import java.util.Collection;

import gov.pr.celepar.framework.database.GenericDAO;
import gov.pr.celepar.framework.exception.ApplicationException;
import gov.pr.celepar.ucs_manterinstituicao.pojo.NaturezaJuridica;

public interface NaturezaJuridicaDAO  extends GenericDAO<NaturezaJuridica, Long>{
	public Collection<NaturezaJuridica> listar(NaturezaJuridica nj, Integer qtdPagina, Integer numPagina) throws ApplicationException;
	public Collection<NaturezaJuridica> listar(NaturezaJuridica nj) throws ApplicationException;
	public Collection<NaturezaJuridica> listar() throws ApplicationException;
	public NaturezaJuridica obter(Integer codNaturezaJuridica) throws ApplicationException;
}
