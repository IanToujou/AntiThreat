package net.toujoustudios.antithreat.main;

import net.toujoustudios.antithreat.loader.Loader;

/**
 * This file has been created by Ian Toujou.
 * Project: AntiThreat
 * Date: 01/02/2022
 * Time: 19:29
 */
public class Main {

    private static AntiThreat bot;

    public static void main(String[] args) {
        bot = new AntiThreat();
        Loader.startLoading();
    }

    public static AntiThreat getBot() {
        return bot;
    }

}