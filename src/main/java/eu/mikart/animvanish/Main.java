package eu.mikart.animvanish;


import com.jeff_media.updatechecker.UpdateCheckSource;
import com.jeff_media.updatechecker.UpdateChecker;
import com.tchristofferson.configupdater.ConfigUpdater;
import eu.mikart.animvanish.commands.AnimVanishCommand;
import eu.mikart.animvanish.commands.InvisCommand;
import eu.mikart.animvanish.config.MessageManager;
import eu.mikart.animvanish.effects.EffectManager;
import eu.mikart.animvanish.gui.InvisGUI;
import eu.mikart.animvanish.listeners.NoDamage;
import eu.mikart.animvanish.config.Settings;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class Main extends JavaPlugin {

	public static Main instance;
	public MessageManager messages;
	private EffectManager effectManager;


	@Override
	public void onEnable() {
		instance = this;
		this.messages = new MessageManager(this);
		this.effectManager = new EffectManager();

		// bStats
		new Metrics(this, Settings.bStats);

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

		// Register listeners
		getServer().getPluginManager().registerEvents(new NoDamage(), this);
		getServer().getPluginManager().registerEvents(new InvisGUI(), this);

		// Check for updates
		new UpdateChecker(this, UpdateCheckSource.SPIGET, Settings.PLUGIN_STR)
				.checkEveryXHours(24)
				.setNotifyOpsOnJoin(false)
				.setFreeDownloadLink(Settings.PLUGIN_URL)
				.suppressUpToDateMessage(true)
				.checkNow();
	}

	@Override
	public void onDisable() {
		instance = null;
	}

	public eu.mikart.animvanish.effects.EffectManager getEffectManager() {
		return effectManager;
	}

}
