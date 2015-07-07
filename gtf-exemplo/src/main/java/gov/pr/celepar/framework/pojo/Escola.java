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
import java.util.Set;
import javax.persistence.*;

import org.apache.commons.lang.builder.*;


@Entity
@Table(name = "tb_escola", schema = "exemplo")
public class Escola implements Serializable {
	
    private Integer idEscola;
    private String nomeEscola;
    private Endereco endereco;
    private Set<Matricula> matriculas;

    /** full constructor */
    public Escola(Integer idEscola, String nomeEscola, gov.pr.celepar.framework.pojo.Endereco endereco, Set<Matricula> matriculas) {
        this.idEscola = idEscola;
        this.nomeEscola = nomeEscola;
        this.endereco = endereco;
        this.matriculas = matriculas;
    }

    /** default constructor */
    public Escola() {
    }

    /** minimal constructor */
    public Escola(Integer idEscola, gov.pr.celepar.framework.pojo.Endereco endereco, Set<Matricula> matriculas) {
        this.idEscola = idEscola;
        this.endereco = endereco;
        this.matriculas = matriculas;
    }

    @Id    
    @SequenceGenerator(name="ESCOLA_ID_SEQ", sequenceName="tb_escola_id_escola_seq")
    @GeneratedValue(strategy=GenerationType.AUTO, generator="ESCOLA_ID_SEQ")
    @Column(name = "id_escola", nullable = false)
    public Integer getIdEscola() {
        return this.idEscola;
    }

    public void setIdEscola(Integer idEscola) {
        this.idEscola = idEscola;
    }

    @Column(name = "nome_escola", length = 64)
    public String getNomeEscola() {
        return this.nomeEscola;
    }

    public void setNomeEscola(String nomeEscola) {
        this.nomeEscola = nomeEscola;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "endereco", nullable = false)
    public Endereco getEndereco() {
        return this.endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "escola")
    public Set<Matricula> getMatriculas() {
        return this.matriculas;
    }

    public void setMatriculas(Set<Matricula> matriculas) {
        this.matriculas = matriculas;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("idEscola", getIdEscola())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Escola) ) return false;
        Escola castOther = (Escola) other;
        return new EqualsBuilder()
            .append(this.getIdEscola(), castOther.getIdEscola())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getIdEscola())
            .toHashCode();
    }

}
