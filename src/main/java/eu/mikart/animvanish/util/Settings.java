package eu.mikart.animvanish.util;

import eu.mikart.animvanish.Main;
import org.jetbrains.annotations.NotNull;

public class Settings {
	public static final String PLUGIN_URL = "https://www.spigotmc.org/resources/animvanish-1-19-animated-vanishing.102183/";
	public static final int PLUGIN_ID = 102183;
	public static final int bStats = 14993;
	public static final boolean DEBUG = Main.getInstance().getConfig().getBoolean("debug");
}
