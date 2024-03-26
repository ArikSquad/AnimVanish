package eu.mikart.animvanish.api;

import eu.mikart.animvanish.AnimVanishBukkit;
import eu.mikart.animvanish.IAnimVanish;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * The Bukkit implementation of the AnimVanish API. Get the instance with {@link #getInstance()}.
 *
 * @since 1.1.0
 */
public class AnimVanishBukkitAPI extends AnimVanishAPI {

	/**
	 * <b>(Internal use only)</b> - Constructor, instantiating the API.
	 *
	 * @param plugin The AnimVanish plugin instance
	 * @since 1.1.0
	 */
	protected AnimVanishBukkitAPI(@NotNull IAnimVanish plugin) {
		super(plugin);
	}

	/**
	 * Get an instance of the AnimVanish API.
	 *
	 * @return instance of the AnimVanish API
	 * @throws NotRegisteredException if the API has not yet been registered.
	 * @since 1.1.0
	 */
	@NotNull
	public static AnimVanishBukkitAPI getInstance() throws NotRegisteredException {
		return (AnimVanishBukkitAPI) AnimVanishAPI.getInstance();
	}

	/**
	 * <b>(Internal use only)</b> - Unregister the API instance.
	 *
	 * @since 1.1.0
	 */
	@ApiStatus.Internal
	public static void register(@NotNull AnimVanishBukkit plugin) {
		instance = new AnimVanishBukkitAPI(plugin);
	}

}
