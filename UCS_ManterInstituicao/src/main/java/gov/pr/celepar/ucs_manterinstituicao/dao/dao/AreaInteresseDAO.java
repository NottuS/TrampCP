package gov.pr.celepar.ucs_manterinstituicao.dao.dao;

import gov.pr.celepar.framework.database.GenericDAO;
import gov.pr.celepar.framework.exception.ApplicationException;
import gov.pr.celepar.ucs_manterinstituicao.pojo.AreaInteresse;

import java.util.Collection;

public interface AreaInteresseDAO extends GenericDAO<AreaInteresse, Long>{
	public Collection<AreaInteresse> listar(AreaInteresse areaInteresse) throws ApplicationException;
	public Collection<AreaInteresse> listar() throws ApplicationException;
	public Collection<AreaInteresse> listarSeleciondas(
			String[] areasInteresseSelecionadas) throws ApplicationException;
}
