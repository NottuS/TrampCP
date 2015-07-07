package gov.pr.celepar.abi.dao;

import gov.pr.celepar.abi.pojo.Vistoriador;
import gov.pr.celepar.framework.database.GenericDAO;
import gov.pr.celepar.framework.exception.ApplicationException;

import java.util.Collection;

public interface VistoriadorDAO extends GenericDAO<Vistoriador, Integer>{
public Collection<Vistoriador> listar(Integer qtdPagina, Integer numPagina, String cpf, String nome) throws ApplicationException;
public boolean existeVistoriador(String cpf, Integer codInstituicao) throws ApplicationException;
public Collection<Vistoriador> listar(Integer qtdPagina, Integer numPagina, String cpf, String nome, Integer codInstituicao) throws ApplicationException;
public Integer obterQuantidadeLista(Integer qtdPagina, Integer numPagina, String cpf, String nome, Integer codInstituicao) throws ApplicationException;
}
