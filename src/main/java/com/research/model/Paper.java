package com.research.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "papers")
public class Paper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer paperId;

    private String title;
    private String abstract_;
    private String content;
    private Date submissionDate;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @OneToMany(mappedBy = "paper", cascade = CascadeType.ALL)
    private List<Comment> comments;
}