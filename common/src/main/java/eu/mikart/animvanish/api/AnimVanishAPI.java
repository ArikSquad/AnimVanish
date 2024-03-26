package eu.mikart.animvanish.api;

import eu.mikart.animvanish.IAnimVanish;
import eu.mikart.animvanish.effects.BareEffect;
import eu.mikart.animvanish.hooks.Hook;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class AnimVanishAPI {
	protected static AnimVanishAPI instance;
	protected final IAnimVanish plugin;


	/**
	 * Register an effect to the plugin
	 * @param effect the effect to register
	 * @since 1.1.0
	 */
	public void registerEffect(BareEffect effect) {
		plugin.getEffectManager().registerEffect(effect);
	}

	/**
	 * Unregister an effect from the plugin
	 *
	 * @param effect the effect to unregister
	 * @since 1.1.0
	 */
	public void unregisterEffect(BareEffect effect) {
		plugin.getEffectManager().unregisterEffect(effect);
	}

	/**
	 * Get the currentHook of the plugin. I have no idea why you would need this. But here it is.
	 * @return the currentHook of the plugin
	 * @since 1.1.0
	 */
	public Hook getCurrentHook() {
		return plugin.getCurrentHook();
	}

	/**
	 * Get a raw locale from the plugin locale file
	 *
	 * @param localeId     the locale ID to get
	 * @param replacements the replacements to make in the locale
	 * @return the locale, with replacements made
	 * @since 1.1.0
	 */
	public Optional<String> getRawLocale(@NotNull String localeId, @NotNull String... replacements) {
		return plugin.getLocales().getRawLocale(localeId, replacements);
	}

	/**
	 * Get a locale from the plugin locale file
	 *
	 * @param localeId     the locale ID to get
	 * @param replacements the replacements to make in the locale
	 * @return the locale as a formatted adventure {@link Component}, with replacements made
	 * @since 1.1.0
	 */
	public Optional<Component> getLocale(@NotNull String localeId, @NotNull String... replacements) {
		return plugin.getLocales().getLocale(localeId, replacements);
	}

	/**
	 * Get an instance of the AnimVanish API.
	 *
	 * @return instance of the AnimVanish API
	 * @throws NotRegisteredException if the API has not yet been registered.
	 * @since 1.1.0
	 */
	@NotNull
	public static AnimVanishAPI getInstance() throws NotRegisteredException {
		if (instance == null) {
			throw new NotRegisteredException();
		}
		return instance;
	}

	/**
	 * <b>(Internal use only)</b> - Unregister the API instance.
	 *
	 * @since 1.1.0
	 */
	@ApiStatus.Internal
	public static void unregister() {
		instance = null;
	}

	/**
	 * An exception indicating the plugin has been accessed before it has been registered.
	 *
	 * @since 1.1.0
	 */
	public static final class NotRegisteredException extends IllegalStateException {

		private static final String MESSAGE = """
                Could not access the AnimVanish API as it has not yet been registered. This could be because:
                1) AnimVanish has failed to enable successfully
                2) Your plugin isn't set to load after AnimVanish has
                   (Check if it set as a (soft)depend in plugin.yml or to load: BEFORE in paper-plugin.yml?)
                3) You are attempting to access AnimVanish on plugin construction/before your plugin has enabled.""";

		NotRegisteredException() {
			super(MESSAGE);
		}

	}
}
