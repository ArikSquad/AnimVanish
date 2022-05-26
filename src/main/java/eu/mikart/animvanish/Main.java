package eu.mikart.animvanish;


import com.jeff_media.updatechecker.UpdateCheckSource;
import com.jeff_media.updatechecker.UpdateChecker;
import com.tchristofferson.configupdater.ConfigUpdater;
import eu.mikart.animvanish.commands.AnimVanishCommand;
import eu.mikart.animvanish.commands.InvisCommand;
import eu.mikart.animvanish.config.MessageManager;
import eu.mikart.animvanish.utils.Settings;
import org.bstats.bukkit.Metrics;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

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

		// Update configs
		File configFile = new File(getDataFolder(), "config.yml");

		try {
			ConfigUpdater.update(this, "config.yml", configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

		reloadConfig();

		// Register commands
		getCommand("animvanish").setExecutor(new AnimVanishCommand());
		getCommand("invis").setExecutor(new InvisCommand());

		// Check for updates
		new UpdateChecker(this, UpdateCheckSource.SPIGET, Settings.PLUGIN_STR)
				.checkEveryXHours(24)
				.setNotifyOpsOnJoin(false)
				.setFreeDownloadLink(Settings.PLUGIN_URL)
				.checkNow();
	}

	@Override
	public void onDisable() {
		instance = null;
	}

	public String getPrefix() {
		return messages.getConfig().getString("prefix");
	}


}
