package eu.mikart.animvanish;

import eu.mikart.animvanish.config.ConfigProvider;
import eu.mikart.animvanish.effects.IEffectManager;
import eu.mikart.animvanish.hooks.Hook;
import eu.mikart.animvanish.user.ConsoleUser;
import eu.mikart.animvanish.util.Version;
import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.logging.Logger;

public interface IAnimVanish extends ConfigProvider {

	String getPluginVersion();

	IEffectManager getEffectManager();

	@NotNull
	default Version getVersion() {
		return Version.fromString(getPluginVersion());
	}

	Hook getCurrentHook();

	Logger getLogger();

	@NotNull
	Audience getAudience(@NotNull UUID user);

	@NotNull
	Audience getConsoleAudience();

	@NotNull
	default ConsoleUser getConsole() {
		return ConsoleUser.wrap(getConsoleAudience());
	}

	default void reload() {
		loadConfig();
	}
}
