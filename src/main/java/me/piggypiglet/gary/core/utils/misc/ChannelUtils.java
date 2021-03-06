package me.piggypiglet.gary.core.utils.misc;

import com.google.inject.Inject;
import me.piggypiglet.gary.core.objects.Constants;
import me.piggypiglet.gary.core.utils.web.WebUtils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.TextChannel;

import java.time.ZonedDateTime;
import java.util.List;

// ------------------------------
// Copyright (c) PiggyPiglet 2018
// https://www.piggypiglet.me
// ------------------------------
public final class ChannelUtils {
    @Inject private WebUtils webUtils;

    public void purgeChannel(TextChannel channel, String messageId, int limit, boolean before) {
        JDA jda = channel.getJDA();
        List<Message> messages;
        if (before) {
            messages = channel.getHistoryBefore(messageId, limit).complete().getRetrievedHistory();
        } else {
            messages = channel.getHistoryAfter(messageId, limit).complete().getRetrievedHistory();
        }

        StringBuilder message = new StringBuilder();
        messages.forEach(msg -> message.append(msg.getContentRaw()).append("\n\n"));

        MessageEmbed.Field field = new MessageEmbed.Field("URL:", webUtils.hastebin(message.toString()), false);
        MessageEmbed msg = new EmbedBuilder()
                .setTitle("Purge")
                .setFooter("Purged #" + channel.getName(), Constants.AVATAR)
                .setTimestamp(ZonedDateTime.now())
                .addField(field)
                .build();

        jda.getTextChannelById(Constants.LOG).sendMessage(msg).queue();
        channel.deleteMessages(messages).queue();
    }
}
