package me.piggypiglet.gary.commands.admin.database;

import com.google.inject.Inject;
import me.piggypiglet.gary.core.framework.Command;
import me.piggypiglet.gary.core.utils.admin.RoleUtils;
import me.piggypiglet.gary.core.utils.mysql.UserUtils;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

// ------------------------------
// Copyright (c) PiggyPiglet 2018
// https://www.piggypiglet.me
// ------------------------------
public class SyncUsers extends Command {
    @Inject private RoleUtils roleUtils;
    @Inject private UserUtils userUtils;

    public SyncUsers() {
        super("?syncusers", "", false);
    }

    @Override
    protected void execute(MessageReceivedEvent e, String[] args) {
        if (roleUtils.isTrustedPlus(e.getMember())) {
            e.getChannel().sendMessage(userUtils.syncUsers(e.getGuild(), e.getJDA())).queue();
        }
    }
}