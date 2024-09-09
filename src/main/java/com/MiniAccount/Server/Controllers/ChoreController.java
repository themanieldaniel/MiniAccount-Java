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
        return new RedirectView("/");
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

    @PostMapping("/addChore")
    public RedirectView addChore(@RequestParam String description, @RequestParam BigDecimal amount) {
        Chore newChore = new Chore();
        newChore.setDescription(description);
        newChore.setAmount(amount);
        newChore.setCompleted(false); // Newly added chores should not be completed
        choreRepository.save(newChore);
        return new RedirectView("/");
    }

    @PostMapping("/deleteChore")
    public RedirectView deleteChore(@RequestParam Long id) {
        choreRepository.deleteById(id);
        return new RedirectView("/");
    }
}