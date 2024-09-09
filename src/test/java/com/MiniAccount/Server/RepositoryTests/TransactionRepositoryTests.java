package com.MiniAccount.Server.RepositoryTests;

import com.MiniAccount.Server.Entities.Transaction;
import com.MiniAccount.Server.Repositories.TransactionRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TransactionRepositoryTests {
    // Query returns a transaction when choreId and currentDate match
    @Test
    public void test_query_returns_transaction_when_choreId_and_currentDate_match() {
        // Arrange
        Long choreId = 1L;
        LocalDate currentDate = LocalDate.now();
        Transaction expectedTransaction = new Transaction();
        expectedTransaction.setChoreId(choreId);
        expectedTransaction.setDate(currentDate);

        TransactionRepository transactionRepository = mock(TransactionRepository.class);
        when(transactionRepository.findCurrentTransactionByChoreId(choreId, currentDate)).thenReturn(expectedTransaction);

        // Act
        Transaction actualTransaction = transactionRepository.findCurrentTransactionByChoreId(choreId, currentDate);

        // Assert
        assertNotNull(actualTransaction);
        assertEquals(expectedTransaction, actualTransaction);
    }
}
