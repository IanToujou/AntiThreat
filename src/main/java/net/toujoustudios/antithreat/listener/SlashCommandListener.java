package net.toujoustudios.antithreat.listener;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.toujoustudios.antithreat.main.Main;
import org.jetbrains.annotations.NotNull;

/**
 * This file has been created by Ian Toujou.
 * Project: AntiThreat
 * Date: 15/02/2022
 * Time: 16:03
 */
public class SlashCommandListener extends ListenerAdapter {

    @Override
    public void onSlashCommand(@NotNull SlashCommandEvent event) {

        if (event.getGuild() == null) return;
        if (event.getUser().isBot()) return;

        Main.getBot().getCommandManager().handle(event);

    }

}

