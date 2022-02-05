package net.toujoustudios.antithreat.listener;

import com.mysql.cj.x.protobuf.Mysqlx;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.internal.utils.PermissionUtil;
import net.toujoustudios.antithreat.config.Config;
import net.toujoustudios.antithreat.error.ErrorEmbed;
import net.toujoustudios.antithreat.error.ErrorType;
import net.toujoustudios.antithreat.main.Main;
import net.toujoustudios.antithreat.util.ColorUtil;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
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
    private final List<String> blacklistedWords = List.of("nitro", "nirto", "nitr0", "n1tro", "discrd", "d1scord", "disc0rd");
    private final List<String> whitelistedSites = List.of("discord.com", "discord.gift", "discord.gg", "tenor.com", "discordapp.net", "discordapp.com");

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {

        String rawMessage = event.getMessage().getContentRaw();
        String baseMessage = rawMessage.toLowerCase();

        if (baseMessage.contains("http://") || baseMessage.contains("https://")) {

            String[] splits = baseMessage.split(" ");
            List<String> linksFound = Arrays.stream(splits).filter(s -> s.startsWith("http://") || s.startsWith("https://")).toList();

            for (String link : linksFound) {

                for (String site : whitelistedSites) if (link.contains(site)) return;

                for (String site : blacklistedWords) {
                    if (link.contains(site)) {

                        EmbedBuilder embedBuilder = new EmbedBuilder();
                        embedBuilder.setColor(ColorUtil.getFromRGBString(config.getString("format.color.default")));

                        if(PermissionUtil.checkPermission(event.getChannel(), event.getGuild().getSelfMember(), Permission.MESSAGE_MANAGE)) {
                            embedBuilder.setTitle(":warning: **Message Deleted**");
                            embedBuilder.setDescription("Your message has been deleted for the following reason:\n`Potentially malicious or dangerous links.`\n\n*This message has been logged and reported to administrators.*");
                            event.getMessage().delete().queue();
                            event.getChannel().sendMessage(embedBuilder.build()).queue();
                        } else {
                            ErrorEmbed.sendError(event.getChannel(), ErrorType.PERMISSION_MESSAGE_MANAGE);
                        }

                        embedBuilder.setTitle(":warning: **Scam Link Detection:**");
                        embedBuilder.setDescription("A message was flagged as scam and removed from a server.```" + rawMessage + "```");
                        User user = Main.getBot().getJDA().getUserById(config.getString("user.admin"));
                        if (user != null)
                            user.openPrivateChannel().flatMap(channel -> channel.sendMessage(embedBuilder.build())).queue();
                        return;

                    }
                }
            }

        }

    }

}
