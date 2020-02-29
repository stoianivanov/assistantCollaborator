package com.fmi.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fmi.domain.enumeration.Semester;

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

    @Column(name = "code")
    private String code;

    @Column(name = "department")
    private String department;

    @Column(name = "name")
    private String name;

    @Column(name = "hours_for_lecture")
    private Integer hoursForLecture;

    @Column(name = "hours_for_workshop")
    private Integer hoursForWorkshop;

    @Column(name = "hours_for_exercise")
    private Integer hoursForExercise;

    @Enumerated(EnumType.STRING)
    @Column(name = "semester")
    private Semester semester;

    @Column(name = "number_of_students")
    private Integer numberOfStudents;

    @Column(name = "incoming_test")
    private Boolean incomingTest;

    @OneToOne
    @JoinColumn(unique = true)
    private DisciplineType type;

    @OneToMany(mappedBy = "discipline")
    private Set<Direction> approproateFors = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "discipline_lectos",
               joinColumns = @JoinColumn(name = "discipline_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "lectos_id", referencedColumnName = "id"))
    private Set<Identity> lectos = new HashSet<>();

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

    public Discipline code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDepartment() {
        return department;
    }

    public Discipline department(String department) {
        this.department = department;
        return this;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public Discipline name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getHoursForLecture() {
        return hoursForLecture;
    }

    public Discipline hoursForLecture(Integer hoursForLecture) {
        this.hoursForLecture = hoursForLecture;
        return this;
    }

    public void setHoursForLecture(Integer hoursForLecture) {
        this.hoursForLecture = hoursForLecture;
    }

    public Integer getHoursForWorkshop() {
        return hoursForWorkshop;
    }

    public Discipline hoursForWorkshop(Integer hoursForWorkshop) {
        this.hoursForWorkshop = hoursForWorkshop;
        return this;
    }

    public void setHoursForWorkshop(Integer hoursForWorkshop) {
        this.hoursForWorkshop = hoursForWorkshop;
    }

    public Integer getHoursForExercise() {
        return hoursForExercise;
    }

    public Discipline hoursForExercise(Integer hoursForExercise) {
        this.hoursForExercise = hoursForExercise;
        return this;
    }

    public void setHoursForExercise(Integer hoursForExercise) {
        this.hoursForExercise = hoursForExercise;
    }

    public Semester getSemester() {
        return semester;
    }

    public Discipline semester(Semester semester) {
        this.semester = semester;
        return this;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public Integer getNumberOfStudents() {
        return numberOfStudents;
    }

    public Discipline numberOfStudents(Integer numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
        return this;
    }

    public void setNumberOfStudents(Integer numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }

    public Boolean isIncomingTest() {
        return incomingTest;
    }

    public Discipline incomingTest(Boolean incomingTest) {
        this.incomingTest = incomingTest;
        return this;
    }

    public void setIncomingTest(Boolean incomingTest) {
        this.incomingTest = incomingTest;
    }

    public DisciplineType getType() {
        return type;
    }

    public Discipline type(DisciplineType disciplineType) {
        this.type = disciplineType;
        return this;
    }

    public void setType(DisciplineType disciplineType) {
        this.type = disciplineType;
    }

    public Set<Direction> getApproproateFors() {
        return approproateFors;
    }

    public Discipline approproateFors(Set<Direction> directions) {
        this.approproateFors = directions;
        return this;
    }

    public Discipline addApproproateFor(Direction direction) {
        this.approproateFors.add(direction);
        direction.setDiscipline(this);
        return this;
    }

    public Discipline removeApproproateFor(Direction direction) {
        this.approproateFors.remove(direction);
        direction.setDiscipline(null);
        return this;
    }

    public void setApproproateFors(Set<Direction> directions) {
        this.approproateFors = directions;
    }

    public Set<Identity> getLectos() {
        return lectos;
    }

    public Discipline lectos(Set<Identity> identities) {
        this.lectos = identities;
        return this;
    }

    public Discipline addLectos(Identity identity) {
        this.lectos.add(identity);
        identity.getDisciplines().add(this);
        return this;
    }

    public Discipline removeLectos(Identity identity) {
        this.lectos.remove(identity);
        identity.getDisciplines().remove(this);
        return this;
    }

    public void setLectos(Set<Identity> identities) {
        this.lectos = identities;
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
            ", code='" + getCode() + "'" +
            ", department='" + getDepartment() + "'" +
            ", name='" + getName() + "'" +
            ", hoursForLecture=" + getHoursForLecture() +
            ", hoursForWorkshop=" + getHoursForWorkshop() +
            ", hoursForExercise=" + getHoursForExercise() +
            ", semester='" + getSemester() + "'" +
            ", numberOfStudents=" + getNumberOfStudents() +
            ", incomingTest='" + isIncomingTest() + "'" +
            "}";
    }
}
