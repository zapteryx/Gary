package me.piggypiglet.gary.core.logging.types;

import me.piggypiglet.gary.core.logging.Logger;
import me.piggypiglet.gary.core.objects.Constants;
import me.piggypiglet.gary.core.objects.enums.EventsEnum;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;

import java.time.ZonedDateTime;

// ------------------------------
// Copyright (c) PiggyPiglet 2018
// https://www.piggypiglet.me
// ------------------------------
public final class VoiceJoin extends Logger {
    public VoiceJoin() {
        super(EventsEnum.VOICE_JOIN);
    }

    @Override
    protected MessageEmbed send() {
        if (getOther()[0] instanceof User && getOther()[1] instanceof Channel) {
            User user = (User) getOther()[0];
            Channel channel = (Channel) getOther()[1];

            return new EmbedBuilder()
                    .setAuthor(user.getName() + "#" + user.getDiscriminator(), null, user.getEffectiveAvatarUrl())
                    .setColor(Constants.GREEN)
                    .setDescription(user.getAsMention() + " **joined voice channel <#" + channel.getId() + ">**")
                    .setFooter("ID: " + user.getId(), null)
                    .setTimestamp(ZonedDateTime.now())
                    .build();
        }

        return null;
    }
}
