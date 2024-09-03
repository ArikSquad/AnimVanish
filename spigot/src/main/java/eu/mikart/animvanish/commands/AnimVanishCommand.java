package eu.mikart.animvanish.commands;

import dev.jorel.commandapi.CommandAPICommand;
import eu.mikart.animvanish.IAnimVanish;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.william278.desertwell.about.AboutMenu;
import net.william278.desertwell.util.Version;
import org.bukkit.entity.Player;

public class AnimVanishCommand {

	public AnimVanishCommand(IAnimVanish plugin) {
		CommandAPICommand reload = new CommandAPICommand("reload")
				.withPermission("animvanish.reload")
				.withFullDescription("Reload the plugin configuration")
				.executes((sender, args) -> {
					plugin.reload();

					plugin.getLocales().getLocale("reload").ifPresent(message -> {
						if (sender instanceof Player player) {
							plugin.getAudience(player.getUniqueId()).sendMessage(message);
						} else {
							plugin.getConsole().sendMessage(message);
						}
					});
				});

		new CommandAPICommand("animvanish")
				.withAliases("av")
				.withPermission("animvanish.help")
				.executesPlayer((player, args) -> {
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

				})
				.executesConsole((sender, args) -> {
					plugin.getConsole().sendMessage(Component.text("AnimVanish v" + plugin.getPluginVersion()));
				})
				.withSubcommand(reload)
				.register();
	}

}
