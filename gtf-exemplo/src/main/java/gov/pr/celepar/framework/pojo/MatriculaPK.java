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

package gov.pr.celepar.framework.pojo;

import java.io.Serializable;
import javax.persistence.*;
import org.apache.commons.lang.builder.*;


@Embeddable
public class MatriculaPK implements Serializable {

    private Integer anoMatricula;
    private Integer aluno;

    /** full constructor */
    public MatriculaPK(Integer anoMatricula, Integer aluno) {
        this.anoMatricula = anoMatricula;
        this.aluno = aluno;
    }

    /** default constructor */
    public MatriculaPK() {
    }

    @Column(name = "ano_matricula", nullable = false, precision = 4, scale = 0)
    public Integer getAnoMatricula() {
        return this.anoMatricula;
    }

    public void setAnoMatricula(Integer anoMatricula) {
        this.anoMatricula = anoMatricula;
    }

    @Column(name = "aluno", nullable = false)
    public Integer getAluno() {
        return this.aluno;
    }

    public void setAluno(Integer aluno) {
        this.aluno = aluno;
    }

    public boolean equals(Object other) {
        if ( !(other instanceof MatriculaPK) ) return false;
        MatriculaPK castOther = (MatriculaPK) other;
        return new EqualsBuilder()
            .append(this.getAnoMatricula(), castOther.getAnoMatricula())
            .append(this.getAluno(), castOther.getAluno())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getAnoMatricula())
            .append(getAluno())
            .toHashCode();
    }

}
