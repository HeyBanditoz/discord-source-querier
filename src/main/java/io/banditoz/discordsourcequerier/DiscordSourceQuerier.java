package io.banditoz.discordsourcequerier;

import io.banditoz.discordsourcequerier.commands.HelpCommand;
import io.banditoz.discordsourcequerier.commands.PlayersCommand;
import io.banditoz.discordsourcequerier.commands.StatusCommand;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

public class DiscordSourceQuerier extends ListenerAdapter {
    public static void main(String[] args) throws LoginException, InterruptedException {
        setupBot();
    }

    private static void setupBot() throws LoginException, InterruptedException {
        Settings settings = SettingsManager.getInstance().getSettings();
        JDA jda = new JDABuilder(settings.getBotToken()).build();
        jda.awaitReady();
        jda.addEventListener(new DiscordSourceQuerier());
        jda.addEventListener(new StatusCommand());
        jda.addEventListener(new PlayersCommand());
        jda.addEventListener(new HelpCommand());
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.isFromType(ChannelType.PRIVATE)) {
            System.out.printf("[PM] %s: %s\n", event.getAuthor().getName(),
                    event.getMessage().getContentDisplay());

        }
    }
}
