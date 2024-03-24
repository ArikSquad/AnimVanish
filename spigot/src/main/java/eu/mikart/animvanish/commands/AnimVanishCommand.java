package eu.mikart.animvanish.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import eu.mikart.animvanish.IAnimVanish;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.william278.desertwell.about.AboutMenu;
import net.william278.desertwell.util.Version;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("animvanish")
public class AnimVanishCommand extends BaseCommand {
	private final IAnimVanish plugin;

	public AnimVanishCommand(IAnimVanish plugin) {
		this.plugin = plugin;
	}

	@Default
	@Description("Command for information about the AnimVanish")
	public void onDefault(CommandSender sender) {
		if (sender instanceof Player player) {
			Audience audience = plugin.getAudience(player.getUniqueId());
			final AboutMenu menu = AboutMenu.builder()
					.title(Component.text("AnimVanish"))
					.description(Component.text("Beautiful library of pre-made effects to make vanishing look as cool as it can!"))
					.version(Version.fromString(plugin.getPluginVersion()))
					.credits("Author", AboutMenu.Credit.of("ArikSquad").description("Click to visit website").url("https://github.com/ArikSquad"))
					.buttons(AboutMenu.Link.of("https://www.mikart.eu/").text("Website").icon("⛏"),
							AboutMenu.Link.of("https://discord.gg/xh9WAvGdVF").text("Discord").icon("⭐").color(TextColor.color(0x6773f5)))
					.build();

			audience.sendMessage(menu.toComponent());
		} else {
			plugin.getConsole().sendMessage(Component.text("AnimVanish v" + plugin.getPluginVersion()));
		}
	}
	@Subcommand("reload")
	@CommandPermission("animvanish.reload")
	@Description("Reloads the plugin config")
	public void onReload(CommandSender sender) {
		plugin.reload();

		plugin.getLocales().getLocale("reload").ifPresent(message -> {
			if (sender instanceof Player player) {
				plugin.getAudience(player.getUniqueId()).sendMessage(message);
			} else {
				plugin.getConsole().sendMessage(message);
			}
		});
	}

}
