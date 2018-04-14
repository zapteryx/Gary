package me.piggypiglet.gary.commands.server.help;

import com.google.inject.Inject;
import me.piggypiglet.gary.core.framework.Command;
import me.piggypiglet.gary.core.handlers.CommandHandler;
import me.piggypiglet.gary.core.objects.Constants;
import me.piggypiglet.gary.core.utils.admin.RoleUtils;
import me.piggypiglet.gary.core.utils.message.MessageUtils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.concurrent.TimeUnit;

public class Commands extends Command {
    @Inject private CommandHandler commandHandler;
    @Inject private MessageUtils messageUtils;
    @Inject private RoleUtils roleUtils;

    public Commands() {
        super("?commands", "Command list.", true);
    }

    @Override
    protected void execute(MessageReceivedEvent e, String[] args) {
        StringBuilder description = new StringBuilder();

        commandHandler.getCommands().forEach(cmd -> {
            if (roleUtils.isTrustedPlus(e.getMember()) || cmd.getHelp()) {
                description.append("**").append(messageUtils.getFirst(cmd.getName())).append("** - ").append(cmd.getDescription()).append("\n");
            }
        });

        MessageEmbed message = new EmbedBuilder()
                .setTitle("Commands")
                .setFooter("Gary v" + Constants.VERSION, Constants.AVATAR)
                .setThumbnail(Constants.AVATAR)
                .appendDescription(description)
                .build();

        e.getChannel().sendMessage(message).complete().delete().queueAfter(3, TimeUnit.MINUTES);
    }
}
