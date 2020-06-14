package com.fmi.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Identity.
 */
@Entity
@Table(name = "identity")
public class Identity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "science_degree")
    private String scienceDegree;

    @Column(name = "education")
    private String education;

    @Column(name = "job")
    private String job;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "e_mail")
    private String eMail;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public Identity fullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getScienceDegree() {
        return scienceDegree;
    }

    public Identity scienceDegree(String scienceDegree) {
        this.scienceDegree = scienceDegree;
        return this;
    }

    public void setScienceDegree(String scienceDegree) {
        this.scienceDegree = scienceDegree;
    }

    public String getEducation() {
        return education;
    }

    public Identity education(String education) {
        this.education = education;
        return this;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getJob() {
        return job;
    }

    public Identity job(String job) {
        this.job = job;
        return this;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Identity phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String geteMail() {
        return eMail;
    }

    public Identity eMail(String eMail) {
        this.eMail = eMail;
        return this;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Identity)) return false;
        Identity identity = (Identity) o;
        return Objects.equals(id, identity.id) &&
                Objects.equals(fullName, identity.fullName) &&
                Objects.equals(eMail, identity.eMail);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Identity{" +
            "id=" + getId() +
            ", fullName='" + getFullName() + "'" +
            ", scienceDegree='" + getScienceDegree() + "'" +
            ", education='" + getEducation() + "'" +
            ", job='" + getJob() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", eMail='" + geteMail() + "'" +
            "}";
    }
}
