package io.banditoz.discordsourcequerier.commands.permissions;

import io.banditoz.discordsourcequerier.Settings;
import io.banditoz.discordsourcequerier.SettingsManager;
import net.dv8tion.jda.core.entities.User;

public class CommandPermissions {
    public static void isBotOwner(User attempter) throws InsufficientPermissionsException {
        Settings settings = SettingsManager.getInstance().getSettings();
        String[] botOwners = settings.getBotOwners();
        String botOwner;
        boolean found = false;
        for (String owner : botOwners) {
            botOwner = owner;
            if (botOwner.compareTo(attempter.getId()) == 0) {
                found = true;
                break;
            }
        }
        if (!found) {
            throw new InsufficientPermissionsException(attempter);
        }
    }
}
