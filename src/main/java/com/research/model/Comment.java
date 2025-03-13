package com.research.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Data
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentId;

    @ManyToOne
    @JoinColumn(name = "paper_id")
    private Paper paper;

    @ManyToOne
    @JoinColumn(name = "researcher_id")
    private Researcher researcher;

    private String content;
    private Date timestamp;
}