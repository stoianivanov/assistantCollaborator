package com.fmi.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DisciplineRecord.
 */
@Entity
@Table(name = "discipline_record")
public class DisciplineRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "department")
    private String department;

    @Column(name = "name")
    private String name;

    @Column(name = "form")
    private String form;

    @Column(name = "course")
    private Integer course;

    @Column(name = "stream")
    private Integer stream;

    @Column(name = "jhi_group")
    private Integer group;

    @Column(name = "hours_for_lecture")
    private Integer hoursForLecture;

    @Column(name = "hours_for_workshop")
    private Integer hoursForWorkshop;

    @Column(name = "hours_for_exercise")
    private Integer hoursForExercise;

    @Column(name = "number_of_students")
    private Integer numberOfStudents;

    @OneToOne
    @JoinColumn(unique = true)
    private ClassType classType;

    @OneToMany(mappedBy = "disciplineRecord")
    private Set<Discipline> disciplines = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "discipline_record_lectos",
               joinColumns = @JoinColumn(name = "discipline_record_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "lectos_id", referencedColumnName = "id"))
    private Set<Identity> lectos = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "discipline_record_direction",
               joinColumns = @JoinColumn(name = "discipline_record_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "direction_id", referencedColumnName = "id"))
    private Set<Direction> directions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public DisciplineRecord code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDepartment() {
        return department;
    }

    public DisciplineRecord department(String department) {
        this.department = department;
        return this;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public DisciplineRecord name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getForm() {
        return form;
    }

    public DisciplineRecord form(String form) {
        this.form = form;
        return this;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public Integer getCourse() {
        return course;
    }

    public DisciplineRecord course(Integer course) {
        this.course = course;
        return this;
    }

    public void setCourse(Integer course) {
        this.course = course;
    }

    public Integer getStream() {
        return stream;
    }

    public DisciplineRecord stream(Integer stream) {
        this.stream = stream;
        return this;
    }

    public void setStream(Integer stream) {
        this.stream = stream;
    }

    public Integer getGroup() {
        return group;
    }

    public DisciplineRecord group(Integer group) {
        this.group = group;
        return this;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }

    public Integer getHoursForLecture() {
        return hoursForLecture;
    }

    public DisciplineRecord hoursForLecture(Integer hoursForLecture) {
        this.hoursForLecture = hoursForLecture;
        return this;
    }

    public void setHoursForLecture(Integer hoursForLecture) {
        this.hoursForLecture = hoursForLecture;
    }

    public Integer getHoursForWorkshop() {
        return hoursForWorkshop;
    }

    public DisciplineRecord hoursForWorkshop(Integer hoursForWorkshop) {
        this.hoursForWorkshop = hoursForWorkshop;
        return this;
    }

    public void setHoursForWorkshop(Integer hoursForWorkshop) {
        this.hoursForWorkshop = hoursForWorkshop;
    }

    public Integer getHoursForExercise() {
        return hoursForExercise;
    }

    public DisciplineRecord hoursForExercise(Integer hoursForExercise) {
        this.hoursForExercise = hoursForExercise;
        return this;
    }

    public void setHoursForExercise(Integer hoursForExercise) {
        this.hoursForExercise = hoursForExercise;
    }

    public Integer getNumberOfStudents() {
        return numberOfStudents;
    }

    public DisciplineRecord numberOfStudents(Integer numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
        return this;
    }

    public void setNumberOfStudents(Integer numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }

    public ClassType getClassType() {
        return classType;
    }

    public DisciplineRecord classType(ClassType classType) {
        this.classType = classType;
        return this;
    }

    public void setClassType(ClassType classType) {
        this.classType = classType;
    }

    public Set<Discipline> getDisciplines() {
        return disciplines;
    }

    public DisciplineRecord disciplines(Set<Discipline> disciplines) {
        this.disciplines = disciplines;
        return this;
    }

    public DisciplineRecord addDiscipline(Discipline discipline) {
        this.disciplines.add(discipline);
        discipline.setDisciplineRecord(this);
        return this;
    }

    public DisciplineRecord removeDiscipline(Discipline discipline) {
        this.disciplines.remove(discipline);
        discipline.setDisciplineRecord(null);
        return this;
    }

    public void setDisciplines(Set<Discipline> disciplines) {
        this.disciplines = disciplines;
    }

    public Set<Identity> getLectos() {
        return lectos;
    }

    public DisciplineRecord lectos(Set<Identity> identities) {
        this.lectos = identities;
        return this;
    }

    public DisciplineRecord addLectos(Identity identity) {
        this.lectos.add(identity);
        identity.getDisciplines().add(this);
        return this;
    }

    public DisciplineRecord removeLectos(Identity identity) {
        this.lectos.remove(identity);
        identity.getDisciplines().remove(this);
        return this;
    }

    public void setLectos(Set<Identity> identities) {
        this.lectos = identities;
    }

    public Set<Direction> getDirections() {
        return directions;
    }

    public DisciplineRecord directions(Set<Direction> directions) {
        this.directions = directions;
        return this;
    }

    public DisciplineRecord addDirection(Direction direction) {
        this.directions.add(direction);
        direction.getDisciplineRecords().add(this);
        return this;
    }

    public DisciplineRecord removeDirection(Direction direction) {
        this.directions.remove(direction);
        direction.getDisciplineRecords().remove(this);
        return this;
    }

    public void setDirections(Set<Direction> directions) {
        this.directions = directions;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DisciplineRecord)) {
            return false;
        }
        return id != null && id.equals(((DisciplineRecord) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DisciplineRecord{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", department='" + getDepartment() + "'" +
            ", name='" + getName() + "'" +
            ", form='" + getForm() + "'" +
            ", course=" + getCourse() +
            ", stream=" + getStream() +
            ", group=" + getGroup() +
            ", hoursForLecture=" + getHoursForLecture() +
            ", hoursForWorkshop=" + getHoursForWorkshop() +
            ", hoursForExercise=" + getHoursForExercise() +
            ", numberOfStudents=" + getNumberOfStudents() +
            "}";
    }
}
