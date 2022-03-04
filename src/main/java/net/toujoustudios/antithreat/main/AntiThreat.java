package net.toujoustudios.antithreat.main;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.toujoustudios.antithreat.command.CommandManager;
import net.toujoustudios.antithreat.config.Config;
import net.toujoustudios.antithreat.listener.GuildMessageReceivedListener;
import net.toujoustudios.antithreat.listener.SlashCommandListener;
import net.toujoustudios.antithreat.log.LogLevel;
import net.toujoustudios.antithreat.log.Logger;

import javax.security.auth.login.LoginException;
import java.util.Scanner;

/**
 * This file has been created by Ian Toujou.
 * Project: AntiThreat
 * Date: 01/02/2022
 * Time: 19:29
 */
public class AntiThreat {

    private JDABuilder builder;
    private JDA jda;
    private CommandManager commandManager;

    public void build() {

        Config config = Config.getDefault();
        Config keysConfig = Config.getFile("keys.yml");
        commandManager = new CommandManager();

        builder = JDABuilder.createDefault(keysConfig.getString("keys.token"));

        builder.setBulkDeleteSplittingEnabled(false);
        builder.setActivity(Activity.streaming("Running " + config.getString("general.name") + " " + config.getString("general.version"), "https://twitch.tv/iantoujou"));

        builder.enableIntents(GatewayIntent.GUILD_MEMBERS);
        builder.enableIntents(GatewayIntent.GUILD_PRESENCES);
        builder.addEventListeners(new GuildMessageReceivedListener());
        builder.addEventListeners(new SlashCommandListener());

    }

    public void start() {
        try {
            jda = builder.build();
            commandManager.registerCommands();
            startConsole();
        } catch (LoginException exception) {
            exception.printStackTrace();
        }
    }

    public void startConsole() {

        Scanner scanner = new Scanner(System.in);
        while (true) {

            String input = scanner.nextLine();

            if (input.startsWith("shutdown")) {
                Logger.log(LogLevel.INFORMATION, "Successfully saved all data in the cache.");
                System.exit(0);
            } else {
                Logger.log(LogLevel.ERROR, "The specified command does not exist.");
            }

        }

    }

    public JDABuilder getBuilder() {
        return builder;
    }

    public JDA getJDA() {
        return jda;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

}
