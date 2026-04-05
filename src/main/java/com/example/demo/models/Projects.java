package com.example.demo.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="projects")
public class Projects {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name="description")
    private String description;

    @Column(name="no_of_years")
    private Integer no_of_years;

    @Column(name="no_of_months")
    private Integer no_of_months;

    @Column(name = "teamsize")
    private Integer teamsize;

    @Column(name = "projectname")
    private String projectname;

    @Column(name = "roletitle")
    private String roletitle;

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Integer getNo_of_years() {
        return no_of_years;
    }

    public Integer getNo_of_months() {
        return no_of_months;
    }

    public Integer getTeamsize() {
        return teamsize;
    }

    public String getProjectname() {
        return projectname;
    }

    public String getRoletitle() {
        return roletitle;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNo_of_years(Integer no_of_years) {
        this.no_of_years = no_of_years;
    }

   public void setNo_of_months(Integer no_of_months) {
        this.no_of_months = no_of_months;
    }

    public void setTeamsize(Integer teamsize) {
        this.teamsize = teamsize;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public void setRoletitle(String roletitle) {
        this.roletitle = roletitle;
    }

    


}
