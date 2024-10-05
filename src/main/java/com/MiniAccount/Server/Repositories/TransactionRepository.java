package com.MiniAccount.Server.Repositories;

import com.MiniAccount.Server.Entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    // Add custom query methods if needed, e.g., find by date, description, etc.
    @Query("SELECT SUM(t.amount) FROM Transaction t")
    BigDecimal getTotalBalance();

    @Query("SELECT t FROM Transaction t ORDER BY t.date DESC")
    List<Transaction> findAllTransactionsSortedByDateDesc();

    @Query("SELECT t FROM Transaction t WHERE t.choreId = :choreId AND t.date = :currentDate")
    Transaction findCurrentTransactionByChoreId(@Param("choreId") Long choreId, @Param("currentDate") LocalDate currentDate);
}
