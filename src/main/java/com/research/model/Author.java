package com.research.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "authors")
public class Author extends User {
    private String affiliation;
    private String researchInterests;

    @OneToMany(mappedBy = "author")
    private List<Paper> papers;
}