package com.MiniAccount.Server.Repositories;

import com.MiniAccount.Server.Entities.Chore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChoreRepository extends JpaRepository<Chore, Long> {
    // Add custom query methods if needed, e.g., find by completed status, etc.
    List<Chore> findByCompleted(boolean completed);

    @Query("SELECT c FROM Chore c ORDER BY c.amount DESC")
    List<Chore> findAllChoresSortedByAmountDesc();

    @Query("SELECT c FROM Chore c WHERE c.id NOT IN " +
            "(SELECT t.choreId FROM Transaction t WHERE t.date = CURRENT_DATE)")
    List<Chore> findChoresWithoutTransactionToday();

}