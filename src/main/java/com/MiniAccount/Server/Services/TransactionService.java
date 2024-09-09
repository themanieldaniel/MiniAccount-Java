package com.MiniAccount.Server.Services;

import com.MiniAccount.Server.Entities.Transaction;
import com.MiniAccount.Server.Repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

//    public List<Transaction> getAllTransactionsSortedByDateDesc() {
//        return transactionRepository.findAll(Sort.by(Sort.Direction.DESC, "date"));
//    }
    // Other service methods as needed
}