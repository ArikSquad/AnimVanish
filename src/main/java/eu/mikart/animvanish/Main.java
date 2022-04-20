package eu.mikart.animvanish;


import eu.mikart.animvanish.commands.AnimVanishCommand;
import eu.mikart.animvanish.commands.InvisCommand;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

	public static Main instance;

	@Override
	public void onEnable() {
		instance = this;

		getCommand("animvanish").setExecutor(new AnimVanishCommand());
		getCommand("invis").setExecutor(new InvisCommand());

		getConfig().options().copyDefaults();
		saveDefaultConfig();
	}

	@Override
	public void onDisable() {
		instance = null;
	}

	public void logInfo(String msg, ChatColor color) {
		getServer().getConsoleSender().sendMessage(getPrefix() + color + msg);
	}

	public String getPrefix() {
		return  "["+ ChatColor.AQUA + getPlainPrefix() + ChatColor.WHITE + "] ";
	}

	public String getPlainPrefix() {
		return getConfig().getString("prefix");
	}


}
