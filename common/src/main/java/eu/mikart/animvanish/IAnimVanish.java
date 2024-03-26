package eu.mikart.animvanish;

import eu.mikart.animvanish.config.ConfigProvider;
import eu.mikart.animvanish.effects.IEffectManager;
import eu.mikart.animvanish.hooks.Hook;
import eu.mikart.animvanish.user.ConsoleUser;
import eu.mikart.animvanish.util.Version;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.AudienceProvider;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.logging.Logger;

public interface IAnimVanish extends ConfigProvider {

	String getPluginVersion();

	IEffectManager getEffectManager();

	@NotNull
	AudienceProvider getAudiences();

	@NotNull
	default Version getVersion() {
		return new Version(getPluginVersion());
	}

	Hook getCurrentHook();

	Logger getLogger();

	@NotNull
	default Audience getAudience(@NotNull UUID user) {
		return getAudiences().player(user);
	}

	@NotNull
	default ConsoleUser getConsole() {
		return ConsoleUser.wrap(getAudiences().console());
	}

	default void reload() {
		loadConfig();
	}

	default void loadExtra() {}
}