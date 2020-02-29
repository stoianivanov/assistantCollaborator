package com.fmi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Direction.
 */
@Entity
@Table(name = "direction")
public class Direction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "appropriate")
    private String appropriate;

    @ManyToOne
    @JsonIgnoreProperties("approproateFors")
    private Discipline discipline;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Direction name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public Direction code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAppropriate() {
        return appropriate;
    }

    public Direction appropriate(String appropriate) {
        this.appropriate = appropriate;
        return this;
    }

    public void setAppropriate(String appropriate) {
        this.appropriate = appropriate;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public Direction discipline(Discipline discipline) {
        this.discipline = discipline;
        return this;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Direction)) {
            return false;
        }
        return id != null && id.equals(((Direction) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Direction{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", appropriate='" + getAppropriate() + "'" +
            "}";
    }
}
