package eu.mikart.animvanish.commands;

import de.myzelyam.api.vanish.VanishAPI;
import eu.mikart.animvanish.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

import static java.util.Calendar.SECOND;

public class InvisCommand implements CommandExecutor {

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "You must be a player to use this command.");
			return true;
		}

		Player player = (Player) sender;
		long old_time = player.getWorld().getTime();

		if (player.hasPermission("animvanish.invis")) {
			if (Bukkit.getPluginManager().isPluginEnabled("SuperVanish") || Bukkit.getPluginManager().isPluginEnabled("PremiumVanish")) {
				if (VanishAPI.isInvisible(player)) {
					VanishAPI.showPlayer(player);
				} else {
					VanishAPI.hidePlayer(player);
				}

				player.getWorld().strikeLightningEffect(player.getLocation());
				if (Main.instance.getConfig().getBoolean("night")) {
					player.getWorld().setTime(14000);

					Calendar c = Calendar.getInstance();
					int s = c.get(SECOND) % 5 + 5;
					while (s > c.get(SECOND) % 5) {}
					player.getWorld().setTime(old_time);
				}
			} else {
				player.sendMessage(Main.instance.getPrefix() + ChatColor.RED + "You must have SuperVanish or PremiumVanish installed to use this command.");
			}
		} else {
			player.sendMessage(Main.instance.getPrefix() + ChatColor.RED + "You do not have permission to use this command. " + ChatColor.GREEN + "(animvanish.invis)");
		}

		return true;
	}
}
