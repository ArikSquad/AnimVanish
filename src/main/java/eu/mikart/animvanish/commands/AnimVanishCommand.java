package eu.mikart.animvanish.commands;

import eu.mikart.animvanish.Main;
import eu.mikart.animvanish.util.MessageConfig;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;

public class AnimVanishCommand implements TabExecutor {

	final MessageConfig messages = Main.getMessages();

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if (!(args.length > 0)) {
			Component message = messages.getMessage("animvanish.version.message");
			sender.sendMessage(message);
		}
		if (args[0].equalsIgnoreCase("reload")) {
			if (!sender.hasPermission("animvanish.reload")) {
				sender.sendMessage(messages.getMessage("animvanish.reload.no_permission"));
				return true;
			}

			// Reload configs
			Main.getInstance().reloadConfig();
			messages.reloadConfig();

			sender.sendMessage(messages.getMessage("reload"));
			Bukkit.getConsoleSender().sendMessage(messages.getMessage("reload"));
			return true;

		} else if (args[0].equalsIgnoreCase("author")) {

			sender.sendMessage(miniMessage().deserialize(messages.getConfig().getString("prefix") + "The plugin author is <gold>" + Main.getInstance().getDescription().getAuthors() + "</gold>"));

		} else if (args[0].equalsIgnoreCase("help")) {
			if (!sender.hasPermission("animvanish.help")) {
				sender.sendMessage(messages.getMessage("animvanish.help.no_permission"));
				return true;
			}

			Component message = miniMessage().deserialize("""
					<color:#5f5f5>Available commands:</color> <color:#212121>| <hover:show_text:'Like this!'>Hover to see permissions</hover></color>\s
					<green><hover:show_text:'animvanish.reload'><click:suggest_command:'/animvanish reload'>/animvanish reload</click></hover></green> <gray>- Reloads the plugin config</gray>\s
					<green><click:suggest_command:'/animvanish author'>/animvanish author</click></green> <gray>- Shows the plugin author</gray> <green><hover:show_text:'animvanish.help'><click:suggest_command:'/animvanish help'>/animvanish help</click></hover></green> <gray>- Shows this help message</gray>\s
					<green><hover:show_text:'animvanish.invis.[effect]'><click:suggest_command:'/invis '>/invis [effect]</click></hover></green> <gray>- Vanishes using an effect</gray>\s
					<green><hover:show_text:'animvanish.invis.gui'><click:suggest_command:'/invis'>/invis</click></hover></green> <gray>- Opens the invis select gui menu</gray>""");
			sender.sendMessage(message);
		} else {
			sender.sendMessage(messages.getMessage("invalid_args"));
		}
		return true;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if (args.length == 1) {
			List<String> arguments = new ArrayList<>();
			if (sender.hasPermission("animvanish.reload")) { arguments.add("reload"); }
			if (sender.hasPermission("animvanish.help")) { arguments.add("help"); }

			arguments.add("author");
			return arguments;
		}

		return null;
	}
}
