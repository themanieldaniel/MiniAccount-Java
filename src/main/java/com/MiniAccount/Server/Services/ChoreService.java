package com.MiniAccount.Server.Services;

import com.MiniAccount.Server.Entities.Chore;
import com.MiniAccount.Server.Entities.Transaction;
import com.MiniAccount.Server.Repositories.ChoreRepository;
import com.MiniAccount.Server.Repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ChoreService {

    @Autowired
    private ChoreRepository choreRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public void toggleChoreCompletion(Long choreId) {
        Chore chore = choreRepository.findById(choreId)
                .orElseThrow(() -> new RuntimeException("Chore not found"));

        // Toggle the completion status
        if (chore.getCompleted()) {
            chore.setCompleted(false);
            chore.setDateCompleted(null);  // Reset the date if marking incomplete

            // Delete the corresponding transaction when a chore is marked as incomplete
            Transaction transaction = transactionRepository.findCurrentTransactionByChoreId(chore.getId(), LocalDate.now());
            transactionRepository.delete(transaction);
        } else {
            chore.setCompleted(true);
            chore.setDateCompleted(LocalDate.now());  // Set current date when marking complete

            // Create a transaction when a chore is marked as complete
            Transaction transaction = new Transaction();
            transaction.setChoreId(chore.getId());
            transaction.setDescription(chore.getDescription());
            transaction.setAmount(chore.getAmount());
            transaction.setDate(LocalDate.now());
            transactionRepository.save(transaction);
        }

        choreRepository.save(chore);  // Save the updated chore
    }

    public void markIncompleteChores() {
        List<Chore> choresWithoutTransaction = choreRepository.findChoresWithoutTransactionToday();
        for (Chore chore : choresWithoutTransaction) {
            chore.setCompleted(false);
            choreRepository.save(chore);
        }
    }

}
