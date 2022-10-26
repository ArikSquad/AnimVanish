package eu.mikart.animvanish.commands;

import eu.mikart.animvanish.Main;
import eu.mikart.animvanish.effects.Effect;
import eu.mikart.animvanish.gui.InvisGUI;
import eu.mikart.animvanish.util.MessageConfig;
import eu.mikart.animvanish.util.Utilities;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class InvisCommand implements TabExecutor {

	public final MessageConfig messages = Main.getMessages();

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if (!(sender instanceof Player player)) {
			sender.sendMessage(messages.getMessage("not_player"));
			return true;
		}
		if (!Utilities.isVanish()) {
			player.sendMessage(messages.getMessage("dependency.no_vanish"));
			return true;
		}
		if (!(args.length > 0)) {
			InvisGUI.openGUI(player);
			return true;

		}
		boolean found = false;
		for (Effect effect : Main.getInstance().getEffectManager().getEffects()) {
			if (args[0].equalsIgnoreCase(effect.getName())) {
				found = true;
				if (!player.hasPermission("animvanish.invis." + effect.getName())) {
					player.sendMessage(messages.getMessage("no_permissions", "permission", "animvanish.invis." + effect.getName()));
					return true;
				}
				effect.runEffect(player);
				break;
			}
		}
		if (!found) {
			player.sendMessage(messages.getMessage("invis.not_found"));
			return true;
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
