package io.banditoz.discordsourcequerier.commands.permissions;

import io.banditoz.discordsourcequerier.Settings;
import io.banditoz.discordsourcequerier.SettingsManager;
import net.dv8tion.jda.core.entities.User;

public class CommandPermissions {
    public static void isBotOwner(User u) throws InsufficientPermissionsException {
        Settings settings = SettingsManager.getInstance().getSettings();
        String[] botOwners = settings.getBotOwners();
        String user;
        for (int i = 0; i < botOwners.length; i++) {
            user = botOwners[i];
            if (user.compareTo(u.getId()) == 0) {
                break;
            }
            else {
                throw new InsufficientPermissionsException(u);
            }
        }
    }

}
