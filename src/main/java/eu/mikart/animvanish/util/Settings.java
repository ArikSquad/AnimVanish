package eu.mikart.animvanish.util;

import eu.mikart.animvanish.Main;

public class Settings {
	public static final String PLUGIN_URL = "https://hangar.papermc.io/ArikSquad/AnimVanish";
	public static final boolean DEBUG = Main.getInstance().getConfig().getBoolean("debug");
	public static boolean BETA = false;
}
