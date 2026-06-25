package eu.mikart.animvanish.commands;

import eu.mikart.animvanish.IAnimVanish;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.william278.desertwell.about.AboutMenu;
import net.william278.desertwell.util.Version;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Dependency;
import revxrsal.commands.bukkit.annotation.CommandPermission;

public class AnimVanishCommand {

	@Dependency
	private IAnimVanish plugin;

	@Command({"animvanish reload", "av reload"})
	@CommandPermission("animvanish.reload")
	public void reload(CommandSender sender) {
		plugin.reload();

		plugin.getLocales().getLocale("reload").ifPresent(message -> {
			if (sender instanceof Player player) {
				plugin.getAudience(player.getUniqueId()).sendMessage(message);
			} else {
				plugin.getConsole().sendMessage(message);
			}
		});
	}

	@Command({"animvanish", "av"})
	public void help(CommandSender sender) {
		Audience audience;
		if (sender instanceof Player player) {
			audience = plugin.getAudience(player.getUniqueId());
		} else {
			audience = plugin.getConsoleAudience();
		}

		final AboutMenu menu = AboutMenu.builder()
			.title(Component.text("AnimVanish"))
			.description(Component.text("Beautiful library of pre-made effects to make vanishing look as cool as it can!"))
			.version(Version.fromString(plugin.getPluginVersion()))
			.credits("Author", AboutMenu.Credit.of("ArikSquad").description("Click to visit website").url("https://github.com/ArikSquad"))
			.buttons(AboutMenu.Link.of("https://www.mikart.eu/").text("Website").icon("⛏"),
				AboutMenu.Link.of("https://discord.gg/xh9WAvGdVF").text("Discord").icon("⭐").color(TextColor.color(0x6773f5)))
			.build();

		audience.sendMessage(menu.toComponent());
	}

}
