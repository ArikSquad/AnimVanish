package eu.mikart.animvanish.commands;

import de.myzelyam.api.vanish.VanishAPI;
import eu.mikart.animvanish.Main;
import eu.mikart.animvanish.util.MessageConfig;
import eu.mikart.animvanish.effects.Effect;
import eu.mikart.animvanish.gui.InvisGUI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class InvisCommand implements TabExecutor {

	public MessageConfig messages = Main.getInstance().messages;

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if (!(sender instanceof Player player)) {
			sender.sendMessage(messages.getMessage("not_player"));
			return true;
		}

		if (args.length == 2) {
			if (player.hasPermission("animvanish.invis.other")) {
				Player p = Bukkit.getPlayer(args[1]);
				if (p != null) {
					player = p;
				} else {
					sender.sendMessage(messages.getMessage("player_not_found"));
					return true;
				}
			} else {
				sender.sendMessage(messages.getMessage("no_permission", "permission", "animvanish.invis.other"));
				return true;
			}
		}

		// SuperVanish and PremiumVanish supported effects
		if (Bukkit.getPluginManager().isPluginEnabled("SuperVanish") || Bukkit.getPluginManager().isPluginEnabled("PremiumVanish")) {

			// noinspection ConstantConditions
			boolean vanishing = !VanishAPI.isInvisible(player);

			if (args.length > 0) {
				boolean found = false;
				for (Effect effect : Main.getInstance().getEffectManager().getEffects()) {
					if (args[0].equalsIgnoreCase(effect.getName())) {
						found = true;
						if (sender.hasPermission("animvanish.invis." + effect.getName())) {
							effect.runEffect(player);
						} else {
							sender.sendMessage(messages.getMessage("no_permissions", "permission", "animvanish.invis." + effect.getName()));
							return true;
						}
						break;
					}
				}
				if (!found) {
					sender.sendMessage(messages.getMessage("invis.not_found"));
					return true;
				}

			} else {
				InvisGUI.openGUI(player);
				return true;
			}

			// noinspection ConstantConditions
			if (vanishing) {
				VanishAPI.hidePlayer(player);
			} else {
				VanishAPI.showPlayer(player);
			}

		} else {
			player.sendMessage(messages.getMessage("dependency.no_vanish"));
		}

		return true;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if (args.length == 1) {
			List<String> arguments = new ArrayList<>();
			for (Effect effect : Main.getInstance().getEffectManager().getEffects()) {
				if(sender.hasPermission("animvanish.invis." + effect.getName())) {
					arguments.add(effect.getName());
				}
			}

			return arguments;
		}

		return null;
	}
}
