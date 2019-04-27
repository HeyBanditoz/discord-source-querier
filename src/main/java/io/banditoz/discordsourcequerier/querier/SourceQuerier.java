package io.banditoz.discordsourcequerier.querier;

import com.github.koraktor.steamcondenser.exceptions.SteamCondenserException;
import com.github.koraktor.steamcondenser.steam.SteamPlayer;
import com.github.koraktor.steamcondenser.steam.servers.SourceServer;
import io.banditoz.discordsourcequerier.Settings;
import io.banditoz.discordsourcequerier.SettingsManager;

import java.util.HashMap;
import java.util.concurrent.TimeoutException;

// TODO: Split getPlayers() and getServerInfo() into their own classes.
public class SourceQuerier extends Querier {
    private SourceServer server;

    // TODO: Clean this shit up.
    public SourceQuerier(String address) throws SteamCondenserException {
        Settings settings = SettingsManager.getInstance().getSettings();
        HashMap<String, String> serverAliases = settings.getServerAliases();
        boolean aliasFound = false;

        // check all of the keys in the server aliases against the given string
        for (String key : serverAliases.keySet()) {
            if (key.compareToIgnoreCase(address) == 0) {
                String[] ipAndPort = {serverAliases.get(key)};
                if (1 >= ipAndPort.length) {
                    ip = ipAndPort[0];
                    port = 27015; // default
                } else {
                    ip = ipAndPort[0];
                    port = Integer.parseInt(ipAndPort[1]);
                }
                aliasFound = true;
                break;
            }
        }

        // we didn't find an alias, interpret it as an address
        if (!aliasFound) {
            ipAndPort = address.split(":");
            if (1 >= ipAndPort.length) {
                ip = ipAndPort[0];
                port = 27015; // default
            } else {
                ip = ipAndPort[0];
                port = Integer.parseInt(ipAndPort[1]);
            }
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
