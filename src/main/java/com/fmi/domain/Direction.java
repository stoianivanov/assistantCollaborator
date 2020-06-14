package com.fmi.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "directions")
    @JsonIgnore
    private Set<DisciplineRecord> disciplineRecords = new HashSet<>();

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

    public String getDescription() {
        return description;
    }

    public Direction description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<DisciplineRecord> getDisciplineRecords() {
        return disciplineRecords;
    }

    public Direction disciplineRecords(Set<DisciplineRecord> disciplineRecords) {
        this.disciplineRecords = disciplineRecords;
        return this;
    }

    public Direction addDisciplineRecord(DisciplineRecord disciplineRecord) {
        this.disciplineRecords.add(disciplineRecord);
        disciplineRecord.getDirections().add(this);
        return this;
    }

    public Direction removeDisciplineRecord(DisciplineRecord disciplineRecord) {
        this.disciplineRecords.remove(disciplineRecord);
        disciplineRecord.getDirections().remove(this);
        return this;
    }

    public void setDisciplineRecords(Set<DisciplineRecord> disciplineRecords) {
        this.disciplineRecords = disciplineRecords;
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
            ", description='" + getDescription() + "'" +
            "}";
    }
}
