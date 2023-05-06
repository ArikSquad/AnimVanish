package eu.mikart.animvanish;

import com.tchristofferson.configupdater.ConfigUpdater;
import eu.mikart.animvanish.commands.AnimCommandManager;
import eu.mikart.animvanish.effects.EffectManager;
import eu.mikart.animvanish.effects.impl.FireworkEffect;
import eu.mikart.animvanish.gui.InvisGUI;
import eu.mikart.animvanish.util.LocaleManager;
import eu.mikart.animvanish.util.Settings;
import eu.mikart.animvanish.util.UpdateChecker;
import eu.mikart.animvanish.util.Version;
import lombok.Getter;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public final class Main extends JavaPlugin {

	@Getter
	private static Main instance;

	@Getter
	private EffectManager effectManager;

	@Getter
	private static AnimCommandManager commandManager;

	@Getter
	private LocaleManager localeConfig;

	@Override
	public void onEnable() {
		instance = this;
		effectManager = new EffectManager();
		commandManager = new AnimCommandManager();
		setupLocaleConfig();

		new Metrics(this, Settings.bStats);
		setupConfig();

		// Register listeners
		getServer().getPluginManager().registerEvents(new FireworkEffect(), this);
		getServer().getPluginManager().registerEvents(new InvisGUI(), this);

		updateCheck();
		commandManager.registerAll();
	}

	public void setupLocaleConfig() {
		localeConfig = new LocaleManager(getConfig().getString("locale"));
	}

	private void setupConfig() {
		getConfig().options().copyDefaults();
		saveDefaultConfig();
		localeConfig.getConfig().options().copyDefaults();
		localeConfig.saveDefaultConfig();

		File configFile = new File(getDataFolder(), "config.yml");

		try {
			ConfigUpdater.update(this, "config.yml", configFile);
			ConfigUpdater.update(this, "lang/" + localeConfig.getLocaleString() + ".yml", localeConfig.getConfigFile());
		} catch (IOException e) {
			e.printStackTrace();
		}

		reloadConfig();
		localeConfig.reloadConfig();
	}
	private void updateCheck() {
		new UpdateChecker(this).getVersion(version -> {
			Version currentVersion = new Version(getDescription().getVersion().replace("-BETA", ""));
			Version latestVersion = new Version(version.replace("-BETA", ""));
			if (currentVersion.compareTo(latestVersion) < 0) {
				getLogger().info("An update is available " + latestVersion + "! Download it here: " + Settings.PLUGIN_URL);
			} else {
				if (Objects.equals(getConfig().getString("suppress-up-to-date"), "false")) {
					getLogger().info("There is not a new update available.");
				}
			}
		});
	}

	@Override
	public void onDisable() {
		instance = null;
	}

}
