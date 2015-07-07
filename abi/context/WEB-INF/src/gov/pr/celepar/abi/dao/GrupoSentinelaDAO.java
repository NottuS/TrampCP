package gov.pr.celepar.abi.dao;

import gov.pr.celepar.abi.pojo.GrupoSentinela;
import gov.pr.celepar.framework.database.GenericDAO;
import gov.pr.celepar.framework.exception.ApplicationException;

import java.util.Collection;

public interface GrupoSentinelaDAO extends GenericDAO<GrupoSentinela, Integer> {

	public GrupoSentinela obterByDescSentinela(String grupo) throws ApplicationException;

	public Collection<GrupoSentinela> listarByUsuario(Integer codUsuario) throws ApplicationException;

}
