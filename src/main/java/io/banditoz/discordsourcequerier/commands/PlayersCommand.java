package io.banditoz.discordsourcequerier.commands;

import io.banditoz.discordsourcequerier.querier.SourceQuerier;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.Arrays;
import java.util.List;

public class PlayersCommand extends Command {
    @Override
    public List<String> getAliases() {
        return Arrays.asList("!players");
    }

    @Override
    public void onCommand(MessageReceivedEvent e, String[] commandArgs) {
        try {
            SourceQuerier server = new SourceQuerier(commandArgs[1]);
            sendReply(e, server.getPlayers());
        }
        catch (Exception ex) {
            sendExceptionMessage(e, ex);
        }
    }
}
