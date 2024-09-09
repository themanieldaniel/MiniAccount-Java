package com.MiniAccount.Server.Entities;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "transactions") // Ensure the table name matches your existing database table
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Assumes auto-generation of ID in PostgreSQL
    private Long id;

    private LocalDate date; // Maps to the "date" column

    private String description; // Maps to the "description" column

    private BigDecimal amount; // Maps to the "amount" column

    private Long choreId;
}