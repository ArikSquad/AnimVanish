package eu.mikart.animvanish.commands.impl;

import eu.mikart.animvanish.Main;
import eu.mikart.animvanish.annonations.CommandAnnotation;
import eu.mikart.animvanish.commands.AnimCommand;
import eu.mikart.animvanish.util.Utilities;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;

@CommandAnnotation(name = "animvanish")
public class AnimVanishCommand extends AnimCommand {

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if (!(args.length > 0)) {
			Utilities.showAboutMenu(sender);
			return true;
		}
		if (args[0].equalsIgnoreCase("reload")) {
			if (!sender.hasPermission("animvanish.reload")) {
				sender.sendMessage(Main.getInstance().getLocaleConfig().getMessage("no_permissions", "permission", "animvanish.reload"));
				return true;
			}

			// Reload configs
			Main.getInstance().reloadConfig();
			Main.getInstance().getLocaleConfig().reloadConfig();
			Main.getInstance().setupLocaleConfig();

			sender.sendMessage(Main.getInstance().getLocaleConfig().getMessage("reload"));
			Bukkit.getConsoleSender().sendMessage(Main.getInstance().getLocaleConfig().getMessage("reload"));
			return true;

		}
		if (args[0].equalsIgnoreCase("help")) {
			if (!sender.hasPermission("animvanish.help")) {
				sender.sendMessage(Main.getInstance().getLocaleConfig().getMessage("animvanish.help.no_permission"));
				sender.sendMessage(Main.getInstance().getLocaleConfig().getMessage("no_permissions", "permission", "animvanish.help"));
				return true;
			}
			Component message = miniMessage().deserialize("""
					<color:#5f5f5>Available commands:</color> <color:#212121>| <hover:show_text:'Like this!'>Hover to see permissions</hover></color>\s
					<green><hover:show_text:'animvanish.reload'><click:suggest_command:'/animvanish reload'>/animvanish reload</click></hover></green> <gray>- Reloads the plugin config</gray>\s
					<green><hover:show_text:'animvanish.help'><click:suggest_command:'/animvanish help'>/animvanish help</click></hover></green> <gray>- Shows this help message</gray>\s
					<green><hover:show_text:'animvanish.invis.[effect]'><click:suggest_command:'/invis '>/invis [effect]</click></hover></green> <gray>- Vanishes using an effect</gray>\s
					<green><hover:show_text:'animvanish.invis.gui'><click:suggest_command:'/invis'>/invis</click></hover></green> <gray>- Opens the invis select gui menu</gray>""");
			sender.sendMessage(message);
			return true;
		}
		sender.sendMessage(Main.getInstance().getLocaleConfig().getMessage("invalid_args"));
		return true;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if (args.length == 1) {
			List<String> arguments = new ArrayList<>();

			if (sender.hasPermission("animvanish.reload")) { arguments.add("reload"); }
			if (sender.hasPermission("animvanish.help")) { arguments.add("help"); }

			return arguments;
		}

		return null;
	}
}
