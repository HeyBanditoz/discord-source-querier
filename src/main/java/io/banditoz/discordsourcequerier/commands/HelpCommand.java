package io.banditoz.discordsourcequerier.commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.Arrays;
import java.util.List;

public class HelpCommand extends Command {
    @Override
    public List<String> getAliases() {
        return Arrays.asList("!help");
    }

    @Override
    public void onCommand(MessageReceivedEvent e, String[] commandArgs) {
        try {
            sendExceptionMessage(e, new UnsupportedOperationException("Not implemented yet."));
        }
        catch (Exception ex) {
            sendExceptionMessage(e, ex);
        }
    }
}
