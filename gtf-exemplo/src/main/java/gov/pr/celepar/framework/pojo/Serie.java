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
@Table(name = "tb_serie", schema = "exemplo")
public class Serie implements Serializable {
    
	private Integer idSerie;
    private String nomeSerie;
    private String tipoSerie;
    private Set<Matricula> matriculas;

    /** full constructor */
    public Serie(Integer idSerie, String nomeSerie, String tipoSerie, Set<Matricula> matriculas) {
        this.idSerie = idSerie;
        this.nomeSerie = nomeSerie;
        this.tipoSerie = tipoSerie;
        this.matriculas = matriculas;
    }

    /** default constructor */
    public Serie() {
    }

    
    @Id    
    @SequenceGenerator(name="SERIE_ID_SEQ", sequenceName="tb_serie_id_serie_seq")
    @GeneratedValue(strategy=GenerationType.AUTO, generator="SERIE_ID_SEQ")
    @Column(name = "id_serie", nullable = false)
    public Integer getIdSerie() {
        return this.idSerie;
    }

    public void setIdSerie(Integer idSerie) {
        this.idSerie = idSerie;
    }

    @Column(name = "nome_serie", nullable = false, length = 128)
    public String getNomeSerie() {
        return this.nomeSerie;
    }

    public void setNomeSerie(String nomeSerie) {
        this.nomeSerie = nomeSerie;
    }

    @Column(name = "tipo_serie", nullable = false, length = 1)
    public String getTipoSerie() {
        return this.tipoSerie;
    }

    public void setTipoSerie(String tipoSerie) {
        this.tipoSerie = tipoSerie;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "serie")
    public Set<Matricula> getMatriculas() {
        return this.matriculas;
    }

    public void setMatriculas(Set<Matricula> matriculas) {
        this.matriculas = matriculas;
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Serie) ) return false;
        Serie castOther = (Serie) other;
        return new EqualsBuilder()
            .append(this.getIdSerie(), castOther.getIdSerie())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getIdSerie())
            .toHashCode();
    }

}
