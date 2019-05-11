package io.banditoz.discordsourcequerier.querier;

import com.github.koraktor.steamcondenser.exceptions.SteamCondenserException;
import com.github.koraktor.steamcondenser.steam.servers.SourceServer;
import io.banditoz.discordsourcequerier.DiscordSourceQuerier;
import io.banditoz.discordsourcequerier.Settings;
import io.banditoz.discordsourcequerier.SettingsManager;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import com.jagrosh.jdautilities.menu.Paginator;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

// TODO: Split getPlayers() and getServerInfo() into their own classes.
public class SourceQuerier extends Querier {
    private SourceServer server;
    private final Color EMBED_COLOR;

    public SourceQuerier(String givenAddress) throws SteamCondenserException {
        Settings settings = SettingsManager.getInstance().getSettings();
        HashMap<String, String> serverAliases = settings.getServerAliases();
        EMBED_COLOR = new Color(109, 255, 59);

        // make sure we actually have server alias(es)
        if (!(serverAliases == null)) {
            // check all of the keys in the server aliases against the given string
            for (String key : serverAliases.keySet()) {
                if (key.compareToIgnoreCase(givenAddress) == 0) {
                    ip = serverAliases.get(key);
                    break;
                }
                ip = givenAddress;
            }
        }
        server = new SourceServer(ip);
    }

    @Override
    public Paginator.Builder getPlayers() throws TimeoutException, SteamCondenserException {
        server.updatePlayers();
        HashMap<String, Object> serverStatus = server.getServerInfo();
        List<String> players = new ArrayList<>(server.getPlayers().keySet());
        Paginator.Builder pb;
        players.sort(String.CASE_INSENSITIVE_ORDER);
        String text = (String) serverStatus.get("serverName");
        pb = new Paginator.Builder()
                .setText(text + "\nPlayers: " + getFormattedPlayerCount())
                .setColumns(1)
                .setItemsPerPage(20)
                .useNumberedItems(false)
                .setColor(EMBED_COLOR)
                .setItems(players.toArray(new String[0]))
                .setEventWaiter(DiscordSourceQuerier.getWaiter())
                .setTimeout(1, TimeUnit.MINUTES);
        return pb;
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
