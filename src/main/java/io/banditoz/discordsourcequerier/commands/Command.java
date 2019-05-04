package io.banditoz.discordsourcequerier.commands;

import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.TimeoutException;

public abstract class Command extends ListenerAdapter {
    protected abstract void onCommand(MessageReceivedEvent e, String[] commandArgs) throws TimeoutException;
    public abstract List<String> getAliases();
    protected final boolean SEND_FULL_STACK_TRACE = false;

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        if (containsCommand(e.getMessage())) {
            try {
                onCommand(e, commandArgs(e.getMessage()));
            } catch (TimeoutException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Sends a reply containing the exception message.
     * @param e The MessageReceivedEvent to reply to.
     * @param ex The exception.
     */
    public void sendExceptionMessage(MessageReceivedEvent e, Exception ex) {
        StringBuilder reply = new StringBuilder("**Exception thrown: **" + ex.toString()); // bold for Discord, and code blocks
        if (SEND_FULL_STACK_TRACE) {
            reply.append("\n```");
            for (int i = 0; i < ex.getStackTrace().length; i++) {
                reply.append(ex.getStackTrace()[i]);
                reply.append("\n");
            }
            reply.append("```");
        }
        else {
            ex.printStackTrace();
        }
        sendReply(e, reply.toString());
    }

    /**
     * Sends a reply.
     * @param e The MessageReceivedEvent to reply to.
     * @param msg The reply.
     */
    public void sendReply(MessageReceivedEvent e, String msg) {
        Queue<Message> toSend = new MessageBuilder()
                .append(msg)
                .buildAll(MessageBuilder.SplitPolicy.ANYWHERE);
        toSend.forEach(message -> e.getChannel().sendMessage(msg).queue());
    }

    /**
     * Sends an EmbedReply.
     * @param e The MessageReceivedEvent to reply to.
     * @param me The reply.
     */
    public void sendEmbedReply(MessageReceivedEvent e, MessageEmbed me) {
        e.getChannel().sendMessage(me).queue();
    }

    protected String[] commandArgs(String string) {
        return string.split(" ");
    }

    protected String[] commandArgs(Message message) {
        return commandArgs(message.getContentDisplay());
    }

    protected boolean containsCommand(Message message) {
        return getAliases().contains(commandArgs(message)[0]);
    }
}
