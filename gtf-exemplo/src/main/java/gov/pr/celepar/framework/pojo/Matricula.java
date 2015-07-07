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


@Entity
@Table(name = "tb_matricula", schema = "exemplo")
public class Matricula implements Serializable {
	
    private MatriculaPK comp_id;
    private Aluno aluno;
    private Escola escola;
    private Serie serie;

    /** full constructor */
    public Matricula(gov.pr.celepar.framework.pojo.MatriculaPK comp_id, gov.pr.celepar.framework.pojo.Aluno aluno, gov.pr.celepar.framework.pojo.Escola escola, gov.pr.celepar.framework.pojo.Serie serie) {
        this.comp_id = comp_id;
        this.aluno = aluno;
        this.escola = escola;
        this.serie = serie;
    }

    /** default constructor */
    public Matricula() {
    }

    /** minimal constructor */
    public Matricula(gov.pr.celepar.framework.pojo.MatriculaPK comp_id, gov.pr.celepar.framework.pojo.Escola escola, gov.pr.celepar.framework.pojo.Serie serie) {
        this.comp_id = comp_id;
        this.escola = escola;
        this.serie = serie;
    }

    @EmbeddedId
    @AttributeOverrides({@AttributeOverride(name = "anoMatricula",column = @Column(name = "ano_matricula",nullable = false,precision = 4,scale = 0)
        )
        , @AttributeOverride(name = "aluno",column = @Column(name = "aluno",nullable = false)
        )
    })
    public MatriculaPK getComp_id() {
        return this.comp_id;
    }

    public void setComp_id(MatriculaPK comp_id) {
        this.comp_id = comp_id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aluno", nullable = false, insertable = false, updatable = false)
    public Aluno getAluno() {
        return this.aluno;
    }

    public void setAluno(gov.pr.celepar.framework.pojo.Aluno aluno) {
        this.aluno = aluno;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "escola", nullable = false)
    public Escola getEscola() {
        return this.escola;
    }

    public void setEscola(gov.pr.celepar.framework.pojo.Escola escola) {
        this.escola = escola;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "serie", nullable = false)
    public Serie getSerie() {
        return this.serie;
    }

    public void setSerie(gov.pr.celepar.framework.pojo.Serie serie) {
        this.serie = serie;
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Matricula) ) return false;
        Matricula castOther = (Matricula) other;
        return new EqualsBuilder()
            .append(this.getComp_id(), castOther.getComp_id())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getComp_id())
            .toHashCode();
    }

}
