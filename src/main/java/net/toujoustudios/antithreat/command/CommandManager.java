package net.toujoustudios.antithreat.command;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import net.toujoustudios.antithreat.command.list.general.HelpCommand;
import net.toujoustudios.antithreat.log.LogLevel;
import net.toujoustudios.antithreat.log.Logger;
import net.toujoustudios.antithreat.main.Main;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * This file has been created by Ian Toujou.
 * Project: AntiThreat
 * Date: 09/02/2022
 * Time: 19:14
 */
public class CommandManager {

    private static CommandManager instance;
    private final List<ICommand> commands = new ArrayList<>();

    public CommandManager() {

        instance = this;

        //General commands
        this.addCommand(new HelpCommand(this));

    }

    private void addCommand(ICommand command) {
        boolean nameFound = this.commands.stream().anyMatch((it) -> it.getName().equalsIgnoreCase(command.getName()));
        if (nameFound) throw new IllegalArgumentException("A command with this name is already present.");
        commands.add(command);
    }

    @SuppressWarnings("unused")
    public void registerCommands() {

        Logger.log(LogLevel.INFORMATION, "Registering commands. This may take a while...");
        CommandListUpdateAction updateAction = Main.getBot().getJDA().updateCommands();
        List<CommandData> commands = new ArrayList<>();

        for (ICommand command : this.commands) {
            Logger.log(LogLevel.DEBUG, "Started registration of the following commands: /" + command.getName());
            CommandData data = new CommandData(command.getName(), command.getDescription()).addOptions(command.getOptions());
            commands.add(data);
        }

        updateAction.addCommands(commands).queue();
        Logger.log(LogLevel.INFORMATION, "Successfully registered " + commands.size() + " commands.");

    }

    public List<ICommand> getCommands() {
        return commands;
    }

    @Nullable
    public ICommand getCommand(String search) {

        String searchLower = search.toLowerCase();

        for (ICommand command : this.commands) {
            if (command.getName().equals(searchLower) || command.getAliases().contains(searchLower)) return command;
        }

        return null;

    }

    public void handle(SlashCommandEvent event) {

        String invoke = event.getName();
        ICommand command = this.getCommand(invoke);

        if (command != null) {

            event.getChannel().sendTyping().queue();

            List<OptionMapping> args = event.getOptions();
            CommandContext context = new CommandContext(event, args);
            command.handle(context);

        }

    }

}