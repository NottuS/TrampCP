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
