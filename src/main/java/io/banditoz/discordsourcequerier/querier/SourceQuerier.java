package io.banditoz.discordsourcequerier.querier;

import com.github.koraktor.steamcondenser.exceptions.SteamCondenserException;
import com.github.koraktor.steamcondenser.steam.SteamPlayer;
import com.github.koraktor.steamcondenser.steam.servers.SourceServer;

import java.util.HashMap;
import java.util.concurrent.TimeoutException;

// TODO: Split getPlayers() and getServerInfo() into their own classes.
public class SourceQuerier extends Querier {
    private SourceServer server;

    public SourceQuerier(String address) throws SteamCondenserException {
        ipAndPort = address.split(":");
        if (1 >= ipAndPort.length) {
            ip = ipAndPort[0];
            port = 27015; // default
        }
        else {
            ip = ipAndPort[0];
            port = Integer.parseInt(ipAndPort[1]);
        }
        server = new SourceServer(ip, port);
    }

    @Override
    public String getPlayers() throws TimeoutException, SteamCondenserException {
        server.updatePlayers();
        HashMap<String, SteamPlayer> hm = server.getPlayers();
        StringBuilder players = new StringBuilder("`Players: ");
        for (String key : hm.keySet()) {
            players.append(key);
            players.append(", ");
        }
        players.append("`");
        return players.toString();
    }

    @Override
    public String getServerInfo() throws TimeoutException, SteamCondenserException {
        server.updateServerInfo();
        HashMap<String, Object> hm = server.getServerInfo();
        // this gets a little ugly!
        String response = "```";
        response += hm.get("serverName") +
                " " +
                server.getIpAddresses() +
                "\nPlayers: " +
                hm.get("numberOfPlayers") + "/" +
                hm.get("maxPlayers") +
                "\nGame: " +
                hm.get("gameDir") +
                "\nGamemode: " +
                hm.get("gameDescription") +
                "\nMap: " +
                hm.get("mapName") +
                "```";
        return response;
    }
}
