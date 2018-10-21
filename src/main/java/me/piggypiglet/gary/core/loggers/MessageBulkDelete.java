package me.piggypiglet.gary.core.loggers;

import me.piggypiglet.gary.core.framework.logging.Logger;
import me.piggypiglet.gary.core.objects.Constants;
import me.piggypiglet.gary.core.objects.enums.EventsEnum;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

import java.time.ZonedDateTime;

// ------------------------------
// Copyright (c) PiggyPiglet 2018
// https://www.piggypiglet.me
// ------------------------------
public final class MessageBulkDelete extends Logger {
    public MessageBulkDelete() {
        super(EventsEnum.MESSAGE_BULK_DELETE);
    }

    @Override
    protected MessageEmbed send() {
        return new EmbedBuilder()
                .setTitle(guild.getName())
                .setColor(Constants.BLUE)
                .setDescription("**Bulk Delete in " + channel.getAsMention() + ", " + list.size() + " messages deleted.**")
                .setTimestamp(ZonedDateTime.now())
                .build();
    }
}
