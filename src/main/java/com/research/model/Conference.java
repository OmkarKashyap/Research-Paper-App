package com.research.model;

import jakarta.persistence.*;

@Entity
@Table(name = "conferences")
public class Conference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eraId; // Maps to the ERAID column

    private String title; // Maps to the Title column

    private String acronym; // Maps to the Acronym column

    private String rank; // Maps to the Rank column

    private String for1; // Maps to the FOR1 column

    @Column(name = "for1_name")
    private String for1Name; // Maps to the FOR1 Name column

    private String for2; // Maps to the FOR2 column

    @Column(name = "for2_name")
    private String for2Name; // Maps to the FOR2 Name column

    private String for3; // Maps to the FOR3 column

    @Column(name = "for3_name")
    private String for3Name; // Maps to the FOR3 Name column

    // Getters and Setters
    public Long getEraId() {
        return eraId;
    }

    public void setEraId(Long eraId) {
        this.eraId = eraId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getFor1() {
        return for1;
    }

    public void setFor1(String for1) {
        this.for1 = for1;
    }

    public String getFor1Name() {
        return for1Name;
    }

    public void setFor1Name(String for1Name) {
        this.for1Name = for1Name;
    }

    public String getFor2() {
        return for2;
    }

    public void setFor2(String for2) {
        this.for2 = for2;
    }

    public String getFor2Name() {
        return for2Name;
    }

    public void setFor2Name(String for2Name) {
        this.for2Name = for2Name;
    }

    public String getFor3() {
        return for3;
    }

    public void setFor3(String for3) {
        this.for3 = for3;
    }

    public String getFor3Name() {
        return for3Name;
    }

    public void setFor3Name(String for3Name) {
        this.for3Name = for3Name;
    }
}