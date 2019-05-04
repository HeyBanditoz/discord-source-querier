package io.banditoz.discordsourcequerier.querier;

import com.github.koraktor.steamcondenser.exceptions.SteamCondenserException;
import com.github.koraktor.steamcondenser.steam.servers.SourceServer;
import io.banditoz.discordsourcequerier.Settings;
import io.banditoz.discordsourcequerier.SettingsManager;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeoutException;

// TODO: Split getPlayers() and getServerInfo() into their own classes.
public class SourceQuerier extends Querier {
    private SourceServer server;
    private final Color EMBED_COLOR;

    // TODO: Clean this shit up.
    public SourceQuerier(String address) throws SteamCondenserException {
        Settings settings = SettingsManager.getInstance().getSettings();
        HashMap<String, String> serverAliases = settings.getServerAliases();
        boolean aliasFound = false;
        EMBED_COLOR = new Color(109, 255, 59);

        // make sure we actually have server alias(es)
        if (!(serverAliases == null)) {
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
    public MessageEmbed getPlayers() throws TimeoutException, SteamCondenserException {
        server.updatePlayers();
        HashMap<String, Object> serverStatus = server.getServerInfo();
        List<String> player = new ArrayList<>(server.getPlayers().keySet());
        player.sort(String.CASE_INSENSITIVE_ORDER);
        StringBuilder players = new StringBuilder();
        for (String s : player) {
            players.append("- ");
            players.append(s);
            players.append("\n");
        }
        EmbedBuilder eb = new EmbedBuilder()
                .setAuthor((String) serverStatus.get("serverName"))
                .setColor(EMBED_COLOR)
                .addField("Players " + getFormattedPlayerCount(), players.toString(), true);

        return eb.build();
    }

    @Override
    public MessageEmbed getServerInfo() throws TimeoutException, SteamCondenserException {
        server.updateServerInfo();
        HashMap<String, Object> hm = server.getServerInfo();
        EmbedBuilder eb = new EmbedBuilder()
                .setAuthor((String) hm.get("serverName"))
                .setColor(EMBED_COLOR)
                .setTitle(String.valueOf(server.getIpAddresses()))
                .addField("Players", getFormattedPlayerCount(), true)
                .addField("Game", (String) hm.get("gameDir"), true)
                .addField("Gamemode", (String) hm.get("gameDescription"), true)
                .addField("Map", (String) hm.get("mapName"), true);

        return eb.build();
    }

    private String getFormattedPlayerCount() throws TimeoutException, SteamCondenserException {
        HashMap<String, Object> hm = server.getServerInfo();
        return hm.get("numberOfPlayers") + "/" + hm.get("maxPlayers");
    }
}
