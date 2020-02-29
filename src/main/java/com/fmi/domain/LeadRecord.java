package com.fmi.domain;


import javax.persistence.*;

import java.io.Serializable;

import com.fmi.domain.enumeration.CourseType;

/**
 * A LeadRecord.
 */
@Entity
@Table(name = "lead_record")
public class LeadRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "max_hours_for_lecture")
    private Integer maxHoursForLecture;

    @Column(name = "max_hours_for_exercise")
    private Integer maxHoursForExercise;

    @Column(name = "max_hours_for_workshop")
    private Integer maxHoursForWorkshop;

    @Column(name = "course")
    private Integer course;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private CourseType type;

    @OneToOne
    @JoinColumn(unique = true)
    private Discipline assitent;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMaxHoursForLecture() {
        return maxHoursForLecture;
    }

    public LeadRecord maxHoursForLecture(Integer maxHoursForLecture) {
        this.maxHoursForLecture = maxHoursForLecture;
        return this;
    }

    public void setMaxHoursForLecture(Integer maxHoursForLecture) {
        this.maxHoursForLecture = maxHoursForLecture;
    }

    public Integer getMaxHoursForExercise() {
        return maxHoursForExercise;
    }

    public LeadRecord maxHoursForExercise(Integer maxHoursForExercise) {
        this.maxHoursForExercise = maxHoursForExercise;
        return this;
    }

    public void setMaxHoursForExercise(Integer maxHoursForExercise) {
        this.maxHoursForExercise = maxHoursForExercise;
    }

    public Integer getMaxHoursForWorkshop() {
        return maxHoursForWorkshop;
    }

    public LeadRecord maxHoursForWorkshop(Integer maxHoursForWorkshop) {
        this.maxHoursForWorkshop = maxHoursForWorkshop;
        return this;
    }

    public void setMaxHoursForWorkshop(Integer maxHoursForWorkshop) {
        this.maxHoursForWorkshop = maxHoursForWorkshop;
    }

    public Integer getCourse() {
        return course;
    }

    public LeadRecord course(Integer course) {
        this.course = course;
        return this;
    }

    public void setCourse(Integer course) {
        this.course = course;
    }

    public CourseType getType() {
        return type;
    }

    public LeadRecord type(CourseType type) {
        this.type = type;
        return this;
    }

    public void setType(CourseType type) {
        this.type = type;
    }

    public Discipline getAssitent() {
        return assitent;
    }

    public LeadRecord assitent(Discipline discipline) {
        this.assitent = discipline;
        return this;
    }

    public void setAssitent(Discipline discipline) {
        this.assitent = discipline;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LeadRecord)) {
            return false;
        }
        return id != null && id.equals(((LeadRecord) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "LeadRecord{" +
            "id=" + getId() +
            ", maxHoursForLecture=" + getMaxHoursForLecture() +
            ", maxHoursForExercise=" + getMaxHoursForExercise() +
            ", maxHoursForWorkshop=" + getMaxHoursForWorkshop() +
            ", course=" + getCourse() +
            ", type='" + getType() + "'" +
            "}";
    }
}
