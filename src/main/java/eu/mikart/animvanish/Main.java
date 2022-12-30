package eu.mikart.animvanish;

import com.tchristofferson.configupdater.ConfigUpdater;
import eu.mikart.animvanish.commands.AnimCommand;
import eu.mikart.animvanish.commands.AnimCommandManager;
import eu.mikart.animvanish.effects.EffectManager;
import eu.mikart.animvanish.effects.impl.FireworkEffect;
import eu.mikart.animvanish.gui.InvisGUI;
import eu.mikart.animvanish.util.MessageConfig;
import eu.mikart.animvanish.util.Settings;
import net.william278.desertwell.UpdateChecker;
import net.william278.desertwell.Version;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

import java.io.IOException;
import java.util.Objects;

public final class Main extends JavaPlugin {

	private static Main instance;
	public static MessageConfig messages;
	private EffectManager effectManager;
	private static AnimCommandManager animCommandManager;

	@Override
	public void onEnable() {
		instance = this;
		messages = new MessageConfig(this);
		effectManager = new EffectManager();
		animCommandManager = new AnimCommandManager();
		new Metrics(this, Settings.bStats);

		setupConfig();

		// Register listeners
		getServer().getPluginManager().registerEvents(new FireworkEffect(), this);
		getServer().getPluginManager().registerEvents(new InvisGUI(), this);

		updateCheck();
		Settings.DEBUG = getConfig().getBoolean("debug");

		// Register commands
		for (AnimCommand command : getAnimCommandManager().getCommands()) {
			if (Settings.DEBUG) getLogger().info("Registering command: " + command.getName());
			Objects.requireNonNull(getCommand(command.getName())).setExecutor(command);
		}
	}

	public void setupConfig() {
		getConfig().options().copyDefaults();
		saveDefaultConfig();

		messages.getConfig().options().copyDefaults();
		messages.saveDefaultConfig();

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
	}
	public void updateCheck() {
		final UpdateChecker updateChecker = UpdateChecker.create(Version.fromString(Settings.getPluginVersion()), Settings.PLUGIN_INT);
		updateChecker.isUpToDate().thenAccept(upToDate -> {
			if (upToDate) {
				if(!getConfig().getBoolean("suppressToDate")) {
					getLogger().info("Running the latest version (" + updateChecker.getCurrentVersion() + ")");
				}

			} else {
				getLogger().info("An update is available! Download from: https://www.spigotmc.org/resources/" + Settings.PLUGIN_INT);
			}
		});
	}

	@Override
	public void onDisable() {
		instance = null;
	}

	public EffectManager getEffectManager() {
		return effectManager;
	}

	public AnimCommandManager getAnimCommandManager() {
		return animCommandManager;
	}

	public static Main getInstance() {
		return instance;
	}

	public static MessageConfig getMessages() {
		return messages;
	}

}
