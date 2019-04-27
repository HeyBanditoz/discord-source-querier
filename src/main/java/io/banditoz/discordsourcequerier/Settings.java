package io.banditoz.discordsourcequerier;

import java.util.HashMap;

public class Settings {
    private String botToken;
    private HashMap<String, String> serverAliases;

    public String getBotToken() {
        return botToken;
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }

    public HashMap<String, String> getServerAliases() {
        return serverAliases;
    }

    public void setServerAliases(HashMap<String, String> serverAliases) {
        this.serverAliases = serverAliases;
    }
}
