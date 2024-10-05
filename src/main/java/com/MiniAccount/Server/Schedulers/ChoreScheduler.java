package com.MiniAccount.Server.Schedulers;

import com.MiniAccount.Server.Services.ChoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ChoreScheduler {

    @Autowired
    private ChoreService choreService;

    @Scheduled(cron = "0 0 0 * * ?") // Runs at midnight every day
    public void updateChoreStatuses() {
        choreService.markIncompleteChores();
    }
}