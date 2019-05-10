package io.banditoz.discordsourcequerier.commands;

import io.banditoz.discordsourcequerier.querier.SourceQuerier;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.PermissionException;

import java.nio.BufferUnderflowException;
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
            server.getPlayers().setFinalAction(m -> {
                try {
                    m.clearReactions().queue();
                } catch (PermissionException ex) {
                    sendExceptionMessage(e, ex);
                }
            })
                    .build().paginate(e.getChannel(), 1);
        } catch (BufferUnderflowException ex) {
            sendReply(e, "Unfortunately, due to the way Garry's Mod sends packets containing player information, this bot cannot retrieve the player list if the server has over 100 players.");
        } catch (Exception ex) {
            sendExceptionMessage(e, ex);
        }
    }
}
