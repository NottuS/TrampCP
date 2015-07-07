/*
 * Este programa � licenciado de acordo com a
 * LPG-AP (LICEN�A P�BLICA GERAL PARA PROGRAMAS DE COMPUTADOR DA ADMINISTRA��O P�BLICA),
 * vers�o 1.1 ou qualquer vers�o posterior.
 * A LPG-AP deve acompanhar todas PUBLICA��ES, DISTRIBUI��ES e REPRODU��ES deste Programa.
 * Caso uma c�pia da LPG-AP n�o esteja dispon�vel junto com este Programa, voc�
 * pode contatar o LICENCIANTE ou ent�o acessar diretamente:
 * http://www.celepar.pr.gov.br/licenca/LPG-AP.pdf
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa
 * � preciso estar de acordo com os termos da LPG-AP.
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
 * Respons�vel por definir a inteface de acesso aos dados da tabela de aluno.
 */
public interface AlunoDAO extends GenericDAO<Aluno, Integer> {
	
	public Collection<Aluno> listar(Aluno aluno, Integer qtdPagina, Integer numPagina) throws ApplicationException;
	
	public Long buscarQtdLista(Aluno aluno) throws ApplicationException;	
	
	public void salvar(Aluno aluno) throws ApplicationException;
	
	public void alterar(Aluno aluno) throws ApplicationException;
	
	public String consultarMainFrame() throws ApplicationException;
}