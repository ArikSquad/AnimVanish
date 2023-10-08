package eu.mikart.animvanish.util;

import eu.mikart.animvanish.Main;
import net.william278.desertwell.AboutMenu;
import net.william278.desertwell.Version;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class Utilities {

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
