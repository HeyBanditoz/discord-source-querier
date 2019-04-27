package io.banditoz.discordsourcequerier.querier;

import com.github.koraktor.steamcondenser.exceptions.SteamCondenserException;

import java.util.concurrent.TimeoutException;

public abstract class Querier {
    protected String ip;
    protected int port;
    protected String[] ipAndPort;

    public abstract String getPlayers() throws TimeoutException, SteamCondenserException;
    public abstract String getServerInfo() throws TimeoutException, SteamCondenserException;
}
