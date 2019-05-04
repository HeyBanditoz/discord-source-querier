package io.banditoz.discordsourcequerier.querier;

import com.github.koraktor.steamcondenser.exceptions.SteamCondenserException;
import net.dv8tion.jda.core.entities.MessageEmbed;

import java.util.concurrent.TimeoutException;

public abstract class Querier {
    protected String ip;
    protected int port;
    protected String[] ipAndPort;

    public abstract MessageEmbed getPlayers() throws TimeoutException, SteamCondenserException;
    public abstract MessageEmbed getServerInfo() throws TimeoutException, SteamCondenserException;
}
