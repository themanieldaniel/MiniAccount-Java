package com.MiniAccount.Server.Controllers;

import com.MiniAccount.Server.Entities.Transaction;
import com.MiniAccount.Server.Repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Controller
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping("/transactions")
    public String viewTransactions(Model model) {
        List<Transaction> transactions = transactionRepository.findAllTransactionsSortedByDateDesc();
        BigDecimal balance = transactionRepository.getTotalBalance();

        model.addAttribute("transactions", transactions);
        model.addAttribute("balance", balance);

        return "transactions";
    }

    @PostMapping("/updateTransaction")
    public String updateTransaction(@RequestParam Long id, @RequestParam String description, @RequestParam BigDecimal amount, @RequestParam LocalDate date) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new RuntimeException("Transaction not found"));
        transaction.setDescription(description);
        transaction.setAmount(amount);
        transaction.setDate(date);
        transactionRepository.save(transaction);
        return "redirect:/transactions";
    }
}