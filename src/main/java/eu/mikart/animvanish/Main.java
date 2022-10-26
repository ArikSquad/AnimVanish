package eu.mikart.animvanish;


import com.jeff_media.updatechecker.UpdateCheckSource;
import com.jeff_media.updatechecker.UpdateChecker;
import com.tchristofferson.configupdater.ConfigUpdater;
import eu.mikart.animvanish.commands.AnimVanishCommand;
import eu.mikart.animvanish.commands.InvisCommand;
import eu.mikart.animvanish.effects.EffectManager;
import eu.mikart.animvanish.effects.impl.FireworkEffect;
import eu.mikart.animvanish.gui.InvisGUI;
import eu.mikart.animvanish.util.MessageConfig;
import eu.mikart.animvanish.util.Settings;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

import java.io.IOException;
import java.util.Objects;

public final class Main extends JavaPlugin {

	private static Main instance;
	public static MessageConfig messages;
	private EffectManager effectManager;


	@Override
	public void onEnable() {
		instance = this;
		messages = new MessageConfig(this);
		effectManager = new EffectManager();

		// bStats
		new Metrics(this, Settings.bStats);

		// Load configs
		getConfig().options().copyDefaults();
		saveDefaultConfig();

		messages.getConfig().options().copyDefaults();
		messages.saveDefaultConfig();

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
		Objects.requireNonNull(getCommand("animvanish")).setExecutor(new AnimVanishCommand());
		Objects.requireNonNull(getCommand("invis")).setExecutor(new InvisCommand());


		// Register listeners
		getServer().getPluginManager().registerEvents(new FireworkEffect(), this);
		getServer().getPluginManager().registerEvents(new InvisGUI(), this);

		// Check for updates
		new UpdateChecker(this, UpdateCheckSource.SPIGET, Settings.PLUGIN_STR)
				.checkEveryXHours(24)
				.setNotifyOpsOnJoin(getConfig().getBoolean("notifyOps"))
				.setFreeDownloadLink(Settings.PLUGIN_URL)
				.suppressUpToDateMessage(getConfig().getBoolean("suppressToDate"))
				.setColoredConsoleOutput(true)
				.checkNow();
	}

	@Override
	public void onDisable() {
		instance = null;
	}

	public EffectManager getEffectManager() {
		return effectManager;
	}

	public static Main getInstance() {
		return instance;
	}

	public static MessageConfig getMessages() {
		return messages;
	}

}
