package net.toujoustudios.antithreat.error;

/**
 * This file has been created by Ian Toujou.
 * Project: AntiThreat
 * Date: 03/02/2022
 * Time: 23:25
 */
public enum ErrorType {

    GENERAL_UNKNOWN("0001", "An unknown error occurred."),
    GENERAL_DISABLED("0002", "This bot function has been temporarily disabled."),
    GENERAL_BANNED("0003", "You have been banned from using the bot."),
    GENERAL_UNFINISHED("0004", "This bot function is still under development."),
    GENERAL_DATABASE("0005", "An error occurred with the database."),
    GENERAL_PERMISSION("0006", "You do not have the permission to perform this action."),

    PERMISSION_MESSAGE_MANAGE("4002", "Failed to delete the message due to insufficient permissions.");

    private final String code;
    private final String description;

    ErrorType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

}