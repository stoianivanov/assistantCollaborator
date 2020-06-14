package com.fmi.domain.dto;

public class LectorDto {
    private Integer number;
    private String name;
    private String scienceDegree;
    private String education;
    private String job;
    private String phoneNumber;
    private String email;
    private String discipline;
    private String disciplineType;
    private String disciplineKind;
    private String spec;
    private Integer course;
    private String form;
    private Integer flow;
    private String group;
    private Integer hoursForLecture;
    private Integer hoursForWorkshop;
    private Integer hoursForExercise;
    private Integer numberOfStudents;

    public LectorDto() {
    }

    public LectorDto(Integer number, String name, String scienceDegree, String education, String job, String phoneNumber, String email, String discipline, String disciplineType, String disciplineKind, String spec, Integer course, String form, Integer flow, String group, Integer hoursForLecture, Integer hoursForWorkshop, Integer hoursForExercise, Integer numberOfStudents) {
        this.number = number;
        this.name = name;
        this.scienceDegree = scienceDegree;
        this.education = education;
        this.job = job;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.discipline = discipline;
        this.disciplineType = disciplineType;
        this.disciplineKind = disciplineKind;
        this.spec = spec;
        this.course = course;
        this.form = form;
        this.flow = flow;
        this.group = group;
        this.hoursForLecture = hoursForLecture;
        this.hoursForWorkshop = hoursForWorkshop;
        this.hoursForExercise = hoursForExercise;
        this.numberOfStudents = numberOfStudents;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScienceDegree() {
        return scienceDegree;
    }

    public void setScienceDegree(String scienceDegree) {
        this.scienceDegree = scienceDegree;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public String getDisciplineType() {
        return disciplineType;
    }

    public void setDisciplineType(String disciplineType) {
        this.disciplineType = disciplineType;
    }

    public String getDisciplineKind() {
        return disciplineKind;
    }

    public void setDisciplineKind(String disciplineKind) {
        this.disciplineKind = disciplineKind;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public Integer getCourse() {
        return course;
    }

    public void setCourse(Integer course) {
        this.course = course;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public Integer getFlow() {
        return flow;
    }

    public void setFlow(Integer flow) {
        this.flow = flow;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Integer getHoursForLecture() {
        return hoursForLecture;
    }

    public void setHoursForLecture(Integer hoursForLecture) {
        this.hoursForLecture = hoursForLecture;
    }

    public Integer getHoursForWorkshop() {
        return hoursForWorkshop;
    }

    public void setHoursForWorkshop(Integer hoursForWorkshop) {
        this.hoursForWorkshop = hoursForWorkshop;
    }

    public Integer getHoursForExercise() {
        return hoursForExercise;
    }

    public void setHoursForExercise(Integer hoursForExercise) {
        this.hoursForExercise = hoursForExercise;
    }

    public Integer getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(Integer numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }
}
