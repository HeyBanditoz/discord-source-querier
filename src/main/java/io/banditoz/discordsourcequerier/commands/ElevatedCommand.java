package io.banditoz.discordsourcequerier.commands;

import io.banditoz.discordsourcequerier.commands.permissions.CommandPermissions;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.concurrent.TimeoutException;

public abstract class ElevatedCommand extends Command {
    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        if (containsCommand(e.getMessage())) {
            try {
                if (CommandPermissions.isBotOwner(e.getAuthor())) {
                    onCommand(e, commandArgs(e.getMessage()));
                }
                else {
                    sendReply(e, String.format("User %s (ID: %s) does not have permission to run this command!", e.getAuthor().getAsTag(), e.getAuthor().getId()));
                }
            } catch (TimeoutException ex) {
                sendExceptionMessage(e, ex);
            }
        }
    }
}
