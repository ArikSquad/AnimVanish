package eu.mikart.animvanish.util;

import de.myzelyam.api.vanish.VanishAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Utilities {

	/**
	 * Check if a player is vanished
	 * @param p The player to check
	 * @since 1.0.8
	 */
	public static boolean isInvisible(Player p) {
		return VanishAPI.isInvisible(p);
	}

	/**
	 * @param p Player to be toggled
	 * @since 1.0.8
	 */
	public static void toggleVanish(Player p) {
		// noinspection ConstantConditions
		if (isInvisible(p)) {
			VanishAPI.showPlayer(p);
		} else {
			VanishAPI.hidePlayer(p);
		}
	}

	/**
	 * @since 1.0.8
	 * @return boolean value
	 */
	public static boolean isVanish() {
		return Bukkit.getPluginManager().isPluginEnabled("SuperVanish") || Bukkit.getPluginManager().isPluginEnabled("PremiumVanish");
	}

}
