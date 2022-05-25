package eu.mikart.animvanish;


import eu.mikart.animvanish.commands.AnimVanishCommand;
import eu.mikart.animvanish.commands.InvisCommand;
import eu.mikart.animvanish.config.MessageManager;
import eu.mikart.animvanish.utils.Settings;
import eu.mikart.animvanish.utils.UpdateChecker;
import org.bstats.bukkit.Metrics;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

	public MessageManager messages;
	public static Main instance;

	@Override
	public void onEnable() {
		instance = this;
		this.messages = new MessageManager(this);

		// bStats
		int pluginId = 14993;
		Metrics metrics = new Metrics(this, pluginId);

		// Load configs
		getConfig().options().copyDefaults();
		saveDefaultConfig();

		// Register commands
		getCommand("animvanish").setExecutor(new AnimVanishCommand());
		getCommand("invis").setExecutor(new InvisCommand());

		// Check for updates
		new UpdateChecker(this, Settings.PLUGIN_ID).getLatestVersion(version -> {
			if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
				getLogger().info("You are using the latest version of AnimVanish!");
			} else {
				getLogger().warning("There is a new version of AnimVanish available! You are running " + this.getDescription().getVersion() + " and the latest version is " + version);
				getLogger().warning("Download it at " + Settings.PLUGIN_URL);
			}
		});
	}

	@Override
	public void onDisable() {
		instance = null;
	}

	public String getPrefix() {
		return "["+ ChatColor.AQUA + getPlainPrefix() + ChatColor.WHITE + "] ";
	}

	public String getPlainPrefix() {
		return getConfig().getString("prefix");
	}


}
