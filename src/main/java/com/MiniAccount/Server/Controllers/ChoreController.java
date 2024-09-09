package com.MiniAccount.Server.Controllers;

import com.MiniAccount.Server.Entities.Chore;
import com.MiniAccount.Server.Entities.Transaction;
import com.MiniAccount.Server.Repositories.ChoreRepository;
import com.MiniAccount.Server.Repositories.TransactionRepository;
import com.MiniAccount.Server.Services.ChoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Controller
public class ChoreController {

    @Autowired
    private ChoreRepository choreRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ChoreService choreService;

    @PostMapping("/toggleChore")
    @ResponseBody
    public RedirectView toggleChore(@RequestParam Long choreId) {
        choreService.toggleChoreCompletion(choreId);
        return new RedirectView("/");  // TODO check if this is the correct redirect
    }

    @GetMapping("/")
    public String viewChores(Model model) {
        List<Chore> chores = choreRepository.findAllChoresSortedByAmountDesc();
        BigDecimal balance = transactionRepository.getTotalBalance();

        model.addAttribute("chores", chores);
        model.addAttribute("balance", balance);

        return "home";
    }

    @PostMapping("/updateChore")
    public RedirectView updateChore(@RequestParam Long id, @RequestParam String description, @RequestParam BigDecimal amount) {
        Chore chore = choreRepository.findById(id).orElseThrow(() -> new RuntimeException("Chore not found"));
        chore.setDescription(description);
        chore.setAmount(amount);
        choreRepository.save(chore);
        return new RedirectView("/");
    }

//    @PostMapping("/chores/complete/{id}")
//    public String markChoreComplete(@PathVariable Long id) {
//        Chore chore = choreRepository.findById(id).orElseThrow();
//        chore.setCompleted(true);
//        choreRepository.save(chore);
//
//        // Create a transaction when a chore is marked as complete
//        Transaction transaction = new Transaction();
//        transaction.setDescription(chore.getDescription());
//        transaction.setAmount(chore.getAmount());
//        transaction.setDate(LocalDate.now());
//        transactionRepository.save(transaction);
//
//        return "redirect:/";
//    }

//    @PostMapping("/chores/undo/{id}")
//    public String undoChoreComplete(@PathVariable Long id) {
//        Chore chore = choreRepository.findById(id).orElseThrow();
//        chore.setCompleted(false);
//        choreRepository.save(chore);
//
//        // Delete the corresponding transaction when a chore is marked as incomplete
//        Transaction transaction = transactionRepository.findCurrentTransactionByDescription(chore.getDescription());
//        transactionRepository.delete(transaction);
//
//        return "redirect:/";
//    }
}