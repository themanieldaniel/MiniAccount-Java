package com.MiniAccount.Server.Entities;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "chores") // Ensure the table name matches your existing database table
public class Chore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Assumes auto-generation of ID in PostgreSQL
    private Long id;

    private String description; // Maps to the "description" column

    private BigDecimal amount; // Maps to the "amount" column

    private Boolean completed; // Maps to the "completed" column (assuming it's a boolean type)

    private LocalDate dateCompleted; // Maps to the "date_completed" column
}