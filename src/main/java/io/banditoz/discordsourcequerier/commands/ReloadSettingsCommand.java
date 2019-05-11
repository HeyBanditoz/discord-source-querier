package io.banditoz.discordsourcequerier.commands;

import io.banditoz.discordsourcequerier.SettingsManager;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.Arrays;
import java.util.List;

public class ReloadSettingsCommand extends ElevatedCommand {
    @Override
    public List<String> getAliases() {
        return Arrays.asList("!reload");
    }

    @Override
    protected void onCommand(MessageReceivedEvent e, String[] commandArgs) {
        try {
            SettingsManager.getInstance().loadSettings();
            sendReply(e, "Settings successfully reloaded.");
        } catch (Exception ex) {
            sendExceptionMessage(e, ex);
        }
    }

}
