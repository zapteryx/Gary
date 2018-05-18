package me.piggypiglet.gary.core.handlers;

import com.google.inject.Inject;
import me.piggypiglet.gary.core.storage.mysql.tables.Users;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

// ------------------------------
// Copyright (c) PiggyPiglet 2018
// https://www.piggypiglet.me
// ------------------------------
public final class UserHandler extends ListenerAdapter {
    @Inject private Users users;

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent e) {
        users.addUser(e.getUser());
    }

    @Override
    public void onGuildMemberLeave(GuildMemberLeaveEvent e) {
        users.delUser(e.getUser().getIdLong());
    }
}
