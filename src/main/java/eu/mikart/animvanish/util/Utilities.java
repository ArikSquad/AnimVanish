package eu.mikart.animvanish.util;

import de.myzelyam.api.vanish.VanishAPI;
import eu.mikart.animvanish.Main;
import net.william278.desertwell.AboutMenu;
import net.william278.desertwell.Version;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
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
	 * Check if SuperVanish or PremiumVanish is installed
	 * @since 1.0.8
	 * @return boolean value if SuperVanish or PremiumVanish is installed
	 */
	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	public static boolean isVanish() {
		return Bukkit.getPluginManager().isPluginEnabled("SuperVanish") || Bukkit.getPluginManager().isPluginEnabled("PremiumVanish");
	}

	/**
	 * Check if Citizens is installed
	 * @since 1.0.8
	 * @return boolean value
	 */
	public static boolean isCitizens() {
		return Bukkit.getPluginManager().isPluginEnabled("Citizens");
	}

	/**
	 * @since 1.0.8
	 */
	public static void showAboutMenu(CommandSender player) {
		final AboutMenu menu = AboutMenu.create("AnimVanish")
				.withDescription("Beautiful library of pre-made effects to make vanishing look as cool as it can!")
				.withVersion(Version.fromString(Main.getInstance().getDescription().getVersion()))
				.addAttribution("Author", AboutMenu.Credit.of("ArikSquad").withDescription("Click to visit website").withUrl("https://www.mikart.eu/"))
				.addButtons(AboutMenu.Link.of("https://www.mikart.eu/").withText("Website").withIcon("⛏"), AboutMenu.Link.of("https://discord.gg/xh9WAvGdVF").withText("Discord").withIcon("⭐").withColor("#6773f5"));

		player.sendMessage(menu.toMineDown().toComponent());
	}


}
