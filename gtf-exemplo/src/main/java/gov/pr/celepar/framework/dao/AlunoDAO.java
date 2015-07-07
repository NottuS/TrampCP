/*
 * Este programa é licenciado de acordo com a
 * LPG-AP (LICENÇA PÚBLICA GERAL PARA PROGRAMAS DE COMPUTADOR DA ADMINISTRAÇÃO PÚBLICA),
 * versão 1.1 ou qualquer versão posterior.
 * A LPG-AP deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e REPRODUÇÕES deste Programa.
 * Caso uma cópia da LPG-AP não esteja disponível junto com este Programa, você
 * pode contatar o LICENCIANTE ou então acessar diretamente:
 * http://www.celepar.pr.gov.br/licenca/LPG-AP.pdf
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa
 * é preciso estar de acordo com os termos da LPG-AP.
 */

package gov.pr.celepar.framework.dao;

import java.util.Collection;
import gov.pr.celepar.framework.pojo.Aluno;
//import gov.pr.celepar.framework.database.GenericDAO;
import gov.pr.celepar.framework.database.GenericDAO;
import gov.pr.celepar.framework.exception.ApplicationException;

/**
 * @author Grupo Framework - Componentes
 * @version 1.0
 * @since 18/01/2005
 * 
 * Classe Exemplo:
 * Responsável por definir a inteface de acesso aos dados da tabela de aluno.
 */
public interface AlunoDAO extends GenericDAO<Aluno, Integer> {
	
	public Collection<Aluno> listar(Aluno aluno, Integer qtdPagina, Integer numPagina) throws ApplicationException;
	
	public Long buscarQtdLista(Aluno aluno) throws ApplicationException;	
	
	public void salvar(Aluno aluno) throws ApplicationException;
	
	public void alterar(Aluno aluno) throws ApplicationException;
	
	public String consultarMainFrame() throws ApplicationException;
}