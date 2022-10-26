package eu.mikart.animvanish.util;

import eu.mikart.animvanish.Main;
import org.jetbrains.annotations.NotNull;

public class Settings {
	public static final String PLUGIN_URL = "https://www.spigotmc.org/resources/animvanish-1-19-animated-vanishing.102183/";
	public static final String PLUGIN_STR = "102183";
	public static final int bStats = 14993;

	/**
	 * Check the plugin version
	 * @since 1.0.8
	 */
	public static @NotNull String getPluginVersion() {
		return Main.getInstance().getDescription().getVersion();
	}
}
