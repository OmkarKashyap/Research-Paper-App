package com.research.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "researchers")
public class Researcher extends User {
    @OneToMany(mappedBy = "researcher")
    private List<Comment> comments;
}