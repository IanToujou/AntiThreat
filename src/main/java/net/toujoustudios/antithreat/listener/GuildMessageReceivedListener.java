package net.toujoustudios.antithreat.listener;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.internal.utils.PermissionUtil;
import net.toujoustudios.antithreat.config.Config;
import net.toujoustudios.antithreat.error.ErrorEmbed;
import net.toujoustudios.antithreat.error.ErrorType;
import net.toujoustudios.antithreat.log.LogLevel;
import net.toujoustudios.antithreat.log.Logger;
import net.toujoustudios.antithreat.main.Main;
import net.toujoustudios.antithreat.util.ColorUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

/**
 * This file has been created by Ian Toujou.
 * Project: AntiThreat
 * Date: 01/02/2022
 * Time: 22:43
 */
public class GuildMessageReceivedListener extends ListenerAdapter {

    private final Config config = Config.getDefault();
    private final List<String> blacklistedWords = Config.getDefault().getStringList("phishing.blacklist.words");
    private final List<String> blacklistedSites = Config.getDefault().getStringList("phishing.blacklist.sites");
    private final List<String> whitelistedSites = Config.getDefault().getStringList("phishing.whitelist.sites");

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {

        String rawMessage = event.getMessage().getContentRaw();
        String baseMessage = rawMessage.toLowerCase();

        if (baseMessage.contains("http://") || baseMessage.contains("https://")) {

            String[] splits = baseMessage.split(" ");
            List<String> linksFound = Arrays.stream(splits).filter(s -> s.startsWith("http://") || s.startsWith("https://")).toList();

            for (String link : linksFound) {

                Logger.log(LogLevel.DEBUG, "New link found to scan. (" + link + ")");

                //Bypass security checks for the whitelisted sites.
                for (String site : whitelistedSites)
                    if (link.startsWith("http://" + site) || link.startsWith("https://" + site)) return;

                for (String site : blacklistedWords) {
                    if (link.contains(site)) {

                        EmbedBuilder embedBuilder = new EmbedBuilder();
                        embedBuilder.setColor(ColorUtil.getFromRGBString(config.getString("format.color.default")));
                        Logger.log(LogLevel.WARNING, "A scam message has been detected. Attempting deletion...");

                        if (PermissionUtil.checkPermission(event.getChannel(), event.getGuild().getSelfMember(), Permission.MESSAGE_MANAGE)) {
                            embedBuilder.setTitle(":warning: **Message Deleted**");
                            embedBuilder.setDescription("Your message has been deleted for the following reason:\n`Potentially malicious or dangerous links.`\n\n*This message has been logged and reported to administrators.*");
                            event.getMessage().delete().queue();
                            event.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();
                            Logger.log(LogLevel.INFORMATION, "Successfully removed the scam message.");
                        } else {
                            Logger.log(LogLevel.ERROR, "Could not delete the scam message due to insufficient permissions.");
                            ErrorEmbed.sendError(event.getChannel(), ErrorType.PERMISSION_MANAGE_MESSAGES);
                        }

                        embedBuilder.setTitle(":warning: **Scam Link Detection**");
                        embedBuilder.setDescription("A message was flagged as scam and removed from a server.```" + rawMessage + "```");
                        User user = Main.getBot().getJDA().getUserById(config.getString("user.admin"));
                        if (user != null)
                            user.openPrivateChannel().flatMap(channel -> channel.sendMessageEmbeds(embedBuilder.build())).queue();
                        return;

                    }
                }

                for (String site : blacklistedSites) {
                    if (link.startsWith("http://" + site) || link.startsWith("https://" + site)) {

                        EmbedBuilder embedBuilder = new EmbedBuilder();
                        embedBuilder.setColor(ColorUtil.getFromRGBString(config.getString("format.color.default")));
                        Logger.log(LogLevel.WARNING, "A scam message has been detected. Attempting deletion...");

                        if (PermissionUtil.checkPermission(event.getChannel(), event.getGuild().getSelfMember(), Permission.MESSAGE_MANAGE)) {
                            embedBuilder.setTitle(":warning: **Message Deleted**");
                            embedBuilder.setDescription("Your message has been deleted for the following reason:\n`Potentially malicious or dangerous links.`\n\n*This message has been logged and reported to administrators.*");
                            event.getMessage().delete().queue();
                            event.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();
                            Logger.log(LogLevel.INFORMATION, "Successfully removed the scam message.");
                        } else {
                            Logger.log(LogLevel.ERROR, "Could not delete the scam message due to insufficient permissions.");
                            ErrorEmbed.sendError(event.getChannel(), ErrorType.PERMISSION_MANAGE_MESSAGES);
                        }

                        embedBuilder.setTitle(":warning: **Scam Link Detection**");
                        embedBuilder.setDescription("A message was flagged as scam and removed from a server.```" + rawMessage + "```");
                        User user = Main.getBot().getJDA().getUserById(config.getString("user.admin"));
                        if (user != null)
                            user.openPrivateChannel().flatMap(channel -> channel.sendMessageEmbeds(embedBuilder.build())).queue();
                        return;

                    }
                }

            }

        }

    }

}
