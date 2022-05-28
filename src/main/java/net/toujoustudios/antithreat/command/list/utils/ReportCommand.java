package net.toujoustudios.antithreat.command.list.utils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.toujoustudios.antithreat.command.CommandCategory;
import net.toujoustudios.antithreat.command.CommandContext;
import net.toujoustudios.antithreat.command.ICommand;
import net.toujoustudios.antithreat.config.Config;
import net.toujoustudios.antithreat.error.ErrorEmbed;
import net.toujoustudios.antithreat.error.ErrorType;
import net.toujoustudios.antithreat.util.ColorUtil;
import org.codehaus.plexus.classworlds.UrlUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * This file has been created by Ian Toujou.
 * Project: AntiThreat
 * Date: 28/05/2022
 * Time: 21:17
 */
public class ReportCommand implements ICommand {

    Config config = Config.getDefault();

    @Override
    public void handle(CommandContext context) {

        List<OptionMapping> args = context.getArgs();
        Member member = context.getMember();
        EmbedBuilder embedBuilder = new EmbedBuilder();

        String link = args.get(0).getAsString();
        if (!link.startsWith("http://") && !link.startsWith("https://")) {
            ErrorEmbed.sendError(context.getEvent(), ErrorType.COMMAND_INVALID_URL);
            return;
        }

        embedBuilder.setTitle("**:triangular_flag_on_post: Report**");
        embedBuilder.setDescription("The link has been successfully reported.");
        embedBuilder.setColor(ColorUtil.getFromRGBString(config.getString("format.color.default")));
        context.getEvent().replyEmbeds(embedBuilder.build()).queue();

    }

    @Override
    public String getName() {
        return "report";
    }

    @Override
    public String getDescription() {
        return "Report a suspicious link.";
    }

    @Override
    public String getEmoji() {
        return "🚩";
    }

    @Override
    public List<OptionData> getOptions() {
        List<OptionData> optionData = new ArrayList<>();
        optionData.add(new OptionData(OptionType.STRING, "link", "The link that you want to report.", false));
        return optionData;
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.UTILS;
    }

}
