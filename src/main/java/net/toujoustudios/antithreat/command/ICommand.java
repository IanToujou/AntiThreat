package net.toujoustudios.antithreat.command;

import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.List;

/**
 * This file has been created by Ian Toujou.
 * Project: AntiThreat
 * Date: 09/02/2022
 * Time: 19:15
 */
public interface ICommand {

    void handle(CommandContext context);

    String getName();

    String getDescription();

    String getEmoji();

    List<OptionData> getOptions();

    CommandCategory getCategory();

    default String getSyntax() {

        StringBuilder builder = new StringBuilder();
        builder.append(getName());

        for (OptionData data : getOptions()) {
            builder.append(" ");
            if (data.isRequired()) {
                builder.append("<");
                builder.append(data.getName());
                builder.append(">");
            } else {
                builder.append("[<");
                builder.append(data.getName());
                builder.append(">]");
            }
        }

        return builder.toString();

    }

}