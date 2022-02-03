package net.toujoustudios.antithreat.error;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.components.Button;
import net.toujoustudios.antithreat.config.Config;
import net.toujoustudios.antithreat.util.ColorUtil;

/**
 * This file has been created by Ian Toujou.
 * Project: AntiThreat
 * Date: 03/02/2022
 * Time: 23:24
 */
public class ErrorEmbed {

    static Config config = Config.getDefault();

    public static MessageEmbed buildError(ErrorType type) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle(":x: **Something went wrong**");
        embedBuilder.setColor(ColorUtil.getFromRGBString(config.getString("format.color.error")));
        embedBuilder.setThumbnail(config.getString("assets.img.icon_error"));
        embedBuilder.setDescription("Oops! An error occurred while attempting to perform this action. Please review the details below.\n\n**Error Code:** `" + type.getCode() + "`\n**Description:** " + type.getDescription());
        return embedBuilder.build();
    }

    public static void sendError(SlashCommandEvent event, ErrorType type) {
        event.replyEmbeds(buildError(type)).addActionRow(Button.link(config.getString("link.help"), "Help")).setEphemeral(true).queue();
    }

    public static void sendError(TextChannel channel, ErrorType type) {
        channel.sendMessage(buildError(type)).queue();
    }

}