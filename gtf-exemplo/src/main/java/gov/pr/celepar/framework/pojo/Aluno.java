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
import java.util.*;
import javax.persistence.*;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cascade;


@Entity
@Table(name = "tb_aluno", schema = "exemplo")
@NamedQuery(name="buscaAlunos",query="from Aluno a where a.nomeAluno like :arg")
public class Aluno implements Serializable {
	private static final long serialVersionUID = 1L;

    private Integer idAluno;
    private String nomeAluno;
    private Date nascimentoAluno;
    private String paiAluno;
    private String maeAluno;
    private String cpfAluno;
    private Endereco endereco;
    private Set<Matricula> matriculas;

    /** full constructor */
    public Aluno(Integer idAluno, String nomeAluno, Date nascimentoAluno, String paiAluno, String maeAluno, String cpfAluno, gov.pr.celepar.framework.pojo.Endereco endereco, Set<Matricula> matriculas) {
        this.idAluno = idAluno;
        this.nomeAluno = nomeAluno;
        this.nascimentoAluno = nascimentoAluno;
        this.paiAluno = paiAluno;
        this.maeAluno = maeAluno;
        this.cpfAluno = cpfAluno;
        this.endereco = endereco;
        this.matriculas = matriculas;
        
    }

    /** default constructor */
    public Aluno() {
    }

    /** minimal constructor */
    public Aluno(Integer idAluno, String nomeAluno, Date nascimentoAluno, String maeAluno, String cpfAluno, gov.pr.celepar.framework.pojo.Endereco endereco, Set<Matricula> matriculas) {
        this.idAluno = idAluno;
        this.nomeAluno = nomeAluno;
        this.nascimentoAluno = nascimentoAluno;
        this.maeAluno = maeAluno;
        this.cpfAluno = cpfAluno;
        this.endereco = endereco;
        this.matriculas = matriculas;
    }

    
    @Id 
    @SequenceGenerator(name="ALUNO_ID_SEQ", sequenceName="tb_aluno_id_aluno_seq")
    @GeneratedValue(strategy=GenerationType.AUTO, generator="ALUNO_ID_SEQ")
    @Column(name = "id_aluno", nullable = false)
    public Integer getIdAluno() {
        return this.idAluno;
    }

    public void setIdAluno(Integer idAluno) {
        this.idAluno = idAluno;
    }

    @Column(name = "nome_aluno", nullable = false, length = 128)
    public String getNomeAluno() {
        return this.nomeAluno;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "nascimento_aluno", nullable = false, length = 4)
    public Date getNascimentoAluno() {
        return this.nascimentoAluno;
    }

    public void setNascimentoAluno(Date nascimentoAluno) {
        this.nascimentoAluno = nascimentoAluno;
    }

    @Column(name = "pai_aluno", length = 128)
    public String getPaiAluno() {
        return this.paiAluno;
    }

    public void setPaiAluno(String paiAluno) {
        this.paiAluno = paiAluno;
    }

    @Column(name = "mae_aluno", nullable = false, length = 128)
    public String getMaeAluno() {
        return this.maeAluno;
    }

    public void setMaeAluno(String maeAluno) {
        this.maeAluno = maeAluno;
    }

    @Column(name = "cpf_aluno", nullable = false, length = 15)
    public String getCpfAluno() {
        return this.cpfAluno;
    }

    public void setCpfAluno(String cpfAluno) {
        this.cpfAluno = cpfAluno;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="endereco")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    public Endereco getEndereco() {
        return this.endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "aluno")
    public Set<Matricula> getMatriculas() {
        return this.matriculas;
    }

    public void setMatriculas(Set<Matricula> matriculas) {
        this.matriculas = matriculas;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("idAluno", getIdAluno())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Aluno) ) return false;
        Aluno castOther = (Aluno) other;
        return new EqualsBuilder()
            .append(this.getIdAluno(), castOther.getIdAluno())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getIdAluno())
            .toHashCode();
    }

}
