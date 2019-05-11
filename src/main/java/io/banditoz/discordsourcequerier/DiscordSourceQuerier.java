package io.banditoz.discordsourcequerier;

import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import io.banditoz.discordsourcequerier.commands.*;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

public class DiscordSourceQuerier extends ListenerAdapter {
    private static EventWaiter waiter;
    public static void main(String[] args) throws LoginException, InterruptedException {
        setupBot();
    }

    private static void setupBot() throws LoginException, InterruptedException {
        waiter = new EventWaiter();
        Settings settings = SettingsManager.getInstance().getSettings();
        JDA jda = new JDABuilder(settings.getBotToken()).build();
        jda.awaitReady();
        jda.addEventListener(new DiscordSourceQuerier());
        jda.addEventListener(new StatusCommand());
        jda.addEventListener(new PlayersCommand());
        jda.addEventListener(new HelpCommand());
        jda.addEventListener(new BashCommand());
        jda.addEventListener(new ReloadSettingsCommand());
        jda.addEventListener(waiter = new EventWaiter());
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.isFromType(ChannelType.PRIVATE)) {
            System.out.printf("[PM] %s: %s\n", event.getAuthor().getName(),
                    event.getMessage().getContentDisplay());

        }
    }

    public static EventWaiter getWaiter() {
        return waiter;
    }
}
