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
