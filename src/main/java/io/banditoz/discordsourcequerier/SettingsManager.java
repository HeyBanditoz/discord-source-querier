package io.banditoz.discordsourcequerier;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

// Thanks https://github.com/DV8FromTheWorld/Yui/blob/e8da929a8f637591e4da53599c39c8161be38746/src/net/dv8tion/discord/SettingsManager.java
public class SettingsManager {
    private static SettingsManager instance;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private Settings settings;
    private final Path configFile = new File(".").toPath().resolve("Config.json");

    public static SettingsManager getInstance() {
        if (instance == null) {
            instance = new SettingsManager();
        }
        return instance;
    }

    public SettingsManager() {
        if (!configFile.toFile().exists()) {
            System.out.println("SettingsManager: Creating default settings");
            System.out.println("SettingsManager: You will need to edit the Config.json with your login information.");
            this.settings = getDefaultSettings();
            saveSettings();
            System.exit(1);
        }
        loadSettings();
    }

    public void loadSettings() {
        try {
            BufferedReader reader = Files.newBufferedReader(configFile, StandardCharsets.UTF_8);
            this.settings = gson.fromJson(reader, Settings.class);

            reader.close();
            System.out.println("SettingsManager: Settings loaded");
        } catch (IOException e) {
            System.out.println("SettingsManager: Error Loading Settings");
            e.printStackTrace();
        }
    }

    public Settings getSettings() {
        return settings;
    }

    public void saveSettings() {
        String jsonOut = gson.toJson(this.settings);
        try {
            BufferedWriter writer = Files.newBufferedWriter(configFile, StandardCharsets.UTF_8);
            writer.append(jsonOut);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Settings getDefaultSettings() {
        Settings newSettings = new Settings();
        newSettings.setBotToken("");
        return newSettings;
    }
}
