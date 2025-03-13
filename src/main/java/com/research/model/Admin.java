package com.research.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "admins")
public class Admin extends User {
    // Admin-specific fields can be added here if needed
}