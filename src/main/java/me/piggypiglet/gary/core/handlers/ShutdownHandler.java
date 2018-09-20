package me.piggypiglet.gary.core.handlers;

import com.google.inject.Inject;
import me.piggypiglet.gary.GaryBot;
import me.piggypiglet.gary.core.objects.tasks.Task;

// ------------------------------
// Copyright (c) PiggyPiglet 2018
// https://www.piggypiglet.me
// ------------------------------
public final class ShutdownHandler extends Thread {
    @Inject private GaryBot garyBot;

    @Override
    public void run() {
        garyBot.getJda().shutdownNow();
        Task.shutdown();
    }
}
