package net.toujoustudios.antithreat.command.list.general;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.components.Button;
import net.toujoustudios.antithreat.command.CommandCategory;
import net.toujoustudios.antithreat.command.CommandContext;
import net.toujoustudios.antithreat.command.ICommand;
import net.toujoustudios.antithreat.config.Config;
import net.toujoustudios.antithreat.util.ColorUtil;

import java.util.Collections;
import java.util.List;

/**
 * This file has been created by Ian Toujou.
 * Project: AntiThreat
 * Date: 28/02/2022
 * Time: 12:13
 */
public class InfoCommand implements ICommand {

    private final Config config;

    public InfoCommand() {
        config = Config.getDefault();
    }

    @Override
    public void handle(CommandContext context) {

        List<OptionMapping> args = context.getArgs();
        EmbedBuilder embedBuilder = new EmbedBuilder();

        embedBuilder.setColor(ColorUtil.getFromRGBString(config.getString("format.color.default")));
        embedBuilder.setTitle(":green_heart: **AntiThreat Information**");
        embedBuilder.setDescription("Here is all the information you need to know about the **AntiThreat Bot**.");
        embedBuilder.addField(":information_source: Description:", "AntiThreat enhanced the security on your Discord server by blocking nitro phishing links!", false);
        embedBuilder.addField(":test_tube: Development:", "**GitHub:** https://github.com/IanToujou/AntiThreat\n**Credits:** Made by `Toujou Studios`", false);
        embedBuilder.setThumbnail(config.getString("assets.img.icon_information"));
        context.getEvent().replyEmbeds(embedBuilder.build()).addActionRow(Button.link(config.getString("link.invite"), "Invite")).queue();

    }

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getDescription() {
        return "💚 Get general information about the bot.";
    }

    @Override
    public List<OptionData> getOptions() {
        return Collections.emptyList();
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.GENERAL;
    }

}