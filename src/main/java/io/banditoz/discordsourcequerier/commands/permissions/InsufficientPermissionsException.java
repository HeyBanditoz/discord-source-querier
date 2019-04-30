package io.banditoz.discordsourcequerier.commands.permissions;

import net.dv8tion.jda.core.entities.User;

public class InsufficientPermissionsException extends Exception {
    public InsufficientPermissionsException(User u) {
        super(String.format("User %s (ID: %s) has insufficient permissions to run this command!", u.getAsTag(), u.getId()));
    }
}
