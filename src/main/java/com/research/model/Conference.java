package com.research.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Data
@Entity
@Table(name = "conferences")
public class Conference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer conferenceId;

    private String name;
    private String location;
    private Date date;
    private Date submissionDeadline;
}