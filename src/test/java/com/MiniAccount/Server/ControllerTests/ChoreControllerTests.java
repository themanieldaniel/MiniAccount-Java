package com.MiniAccount.Server.ControllerTests;

import com.MiniAccount.Server.Entities.Chore;
import com.MiniAccount.Server.Entities.Transaction;
import com.MiniAccount.Server.Repositories.ChoreRepository;
import com.MiniAccount.Server.Repositories.TransactionRepository;
import com.MiniAccount.Server.Services.ChoreService;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class ChoreControllerTests {

    // Chore completion toggles successfully
    @Test
    public void test_toggle_chore_completion_success() {
        // Arrange
        Long choreId = 1L;
        Chore chore = new Chore();
        chore.setId(choreId);
        chore.setCompleted(false);
        chore.setDescription("Test Chore");
        chore.setAmount(new BigDecimal("10.00"));

        Transaction transaction = new Transaction();
        transaction.setChoreId(choreId);
        transaction.setDescription("Test Chore");
        transaction.setAmount(new BigDecimal("10.00"));
        transaction.setDate(LocalDate.now());

        ChoreRepository choreRepository = mock(ChoreRepository.class);
        TransactionRepository transactionRepository = mock(TransactionRepository.class);
        ChoreService choreService = new ChoreService();
        ReflectionTestUtils.setField(choreService, "choreRepository", choreRepository);
        ReflectionTestUtils.setField(choreService, "transactionRepository", transactionRepository);

        when(choreRepository.findById(choreId)).thenReturn(Optional.of(chore));
        when(transactionRepository.findCurrentTransactionByChoreId(choreId, LocalDate.now())).thenReturn(transaction);

        // Act
        choreService.toggleChoreCompletion(choreId);

        // Assert
        assertTrue(chore.getCompleted());
        assertNotNull(chore.getDateCompleted());
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }
}
