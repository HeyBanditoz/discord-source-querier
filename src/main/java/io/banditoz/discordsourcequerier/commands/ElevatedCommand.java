package io.banditoz.discordsourcequerier.commands;

import io.banditoz.discordsourcequerier.commands.permissions.CommandPermissions;
import io.banditoz.discordsourcequerier.commands.permissions.InsufficientPermissionsException;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.concurrent.TimeoutException;

public abstract class ElevatedCommand extends Command {
    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        if (containsCommand(e.getMessage())) {
            try {
                CommandPermissions.isBotOwner(e.getAuthor());
                onCommand(e, commandArgs(e.getMessage()));
            } catch (TimeoutException | InsufficientPermissionsException ex) {
                sendExceptionMessage(e, ex);
            }
        }
    }
}
