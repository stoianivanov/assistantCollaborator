package com.fmi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Discipline.
 */
@Entity
@Table(name = "discipline")
public class Discipline implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "discipline_type")
    private String disciplineType;

    @ManyToOne
    @JsonIgnoreProperties("disciplines")
    private DisciplineRecord disciplineRecord;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public Discipline description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDisciplineType() {
        return disciplineType;
    }

    public Discipline disciplineType(String disciplineType) {
        this.disciplineType = disciplineType;
        return this;
    }

    public void setDisciplineType(String disciplineType) {
        this.disciplineType = disciplineType;
    }

    public DisciplineRecord getDisciplineRecord() {
        return disciplineRecord;
    }

    public Discipline disciplineRecord(DisciplineRecord disciplineRecord) {
        this.disciplineRecord = disciplineRecord;
        return this;
    }

    public void setDisciplineRecord(DisciplineRecord disciplineRecord) {
        this.disciplineRecord = disciplineRecord;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Discipline)) {
            return false;
        }
        return id != null && id.equals(((Discipline) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Discipline{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", disciplineType='" + getDisciplineType() + "'" +
            "}";
    }
}
