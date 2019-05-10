package io.banditoz.discordsourcequerier.commands.permissions;

import io.banditoz.discordsourcequerier.Settings;
import io.banditoz.discordsourcequerier.SettingsManager;
import net.dv8tion.jda.core.entities.User;

public class CommandPermissions {
    public static boolean isBotOwner(User attempter){
        Settings settings = SettingsManager.getInstance().getSettings();
        String[] botOwners = settings.getBotOwners();
        for (String owner : botOwners) {
            if (owner.compareTo(attempter.getId()) == 0) {
                return true;
            }
        }
        return false;
    }
}
