package eu.mikart.animvanish;


import com.jeff_media.updatechecker.UpdateCheckSource;
import com.jeff_media.updatechecker.UpdateChecker;
import com.tchristofferson.configupdater.ConfigUpdater;
import eu.mikart.animvanish.commands.AnimVanishCommand;
import eu.mikart.animvanish.commands.InvisCommand;
import eu.mikart.animvanish.config.MessageManager;
import eu.mikart.animvanish.utils.Settings;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class Main extends JavaPlugin {

	public static Main instance;
	public MessageManager messages;
	private BukkitAudiences adventure;


	@Override
	public void onEnable() {
		instance = this;
		this.messages = new MessageManager(this);
		this.adventure = BukkitAudiences.create(this);

		// bStats
		int pluginId = 14993;
		Metrics metrics = new Metrics(this, pluginId);

		// Load configs
		getConfig().options().copyDefaults();
		saveDefaultConfig();

		// Update configs
		File configFile = new File(getDataFolder(), "config.yml");
		File messagesFile = new File(getDataFolder(), "messages.yml");

		try {
			ConfigUpdater.update(this, "config.yml", configFile);
			ConfigUpdater.update(this, "messages.yml", messagesFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

		reloadConfig();
		messages.reloadConfig();

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

	public BukkitAudiences adventure() {
		if(this.adventure == null) {
			throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
		}
		return this.adventure;
	}

	@Override
	public void onDisable() {
		instance = null;
		if(this.adventure != null) {
			this.adventure.close();
			this.adventure = null;
		}
	}

}
