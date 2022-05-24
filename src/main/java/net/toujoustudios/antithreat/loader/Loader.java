package net.toujoustudios.antithreat.loader;

import net.toujoustudios.antithreat.config.Config;
import net.toujoustudios.antithreat.main.Main;

/**
 * This file has been created by Ian Toujou.
 * Project: AntiThreat
 * Date: 01/02/2022
 * Time: 19:30
 */
public class Loader {

    private static LoaderState state;

    public static void startLoading() {
        preInitialize();
        initialize();
        postInitialize();
    }

    private static void preInitialize() {
        new Config("config.yml");
        new Config("keys.yml");
        new Config("database.yml");
        Main.getBot().initializeDatabase();
    }

    private static void initialize() {
        Main.getBot().build();
    }

    private static void postInitialize() {
        Main.getBot().start();
    }

    public static LoaderState getState() {
        return state;
    }

    public static void setState(LoaderState state) {
        Loader.state = state;
    }

    public static void cancel() {
        Loader.state = LoaderState.CANCELLED;
    }

}