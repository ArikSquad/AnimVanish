package eu.mikart.animvanish.util;

import com.google.common.base.Charsets;
import eu.mikart.animvanish.Main;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;

public class LocaleManager {

	@Getter
	private FileConfiguration config;

	@Getter
	private File configFile;

	@Getter
	private String localePath;

	@Getter
	private String localeString;

	public LocaleManager(String locale) {
		this.localeString = locale;
		loadLocale(locale);
	}

	private void loadLocale(String locale) {
		this.localePath = "lang/" + locale + ".yml";

		if (this.configFile == null) {
			this.configFile = new File(Main.getInstance().getDataFolder(), this.localePath);
		}
		if (!this.configFile.exists()) {
			Main.getInstance().saveResource(this.localePath, false);
		}

		this.config = YamlConfiguration.loadConfiguration(this.configFile);
		InputStream defStream = Main.getInstance().getResource(this.localePath);

		if (defStream != null) {
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defStream));
			this.config.setDefaults(defConfig);
		}
	}

	public Component getPrefix() {
		return miniMessage().deserialize(Objects.requireNonNull(config.getString("prefix")));
	}

	/**
	 * Get a message from config and parsing the colours.
	 * @param name The config value name.
	 * @return Component, with prefix before it.
	 */
	public Component getMessage(String name) {
		String unFormattedText = config.getString(name);
		if (unFormattedText == null) {
			return Component.text("Message source is missing: " + name);
		}
		return MiniMessage.miniMessage().deserialize(
				unFormattedText,
				Placeholder.component("prefix", getPrefix()),
				Placeholder.component("version", Component.text(Main.getInstance().getDescription().getVersion())),
				Placeholder.component("url", Component.text(Settings.PLUGIN_URL))
		);
	}

	/**
	 * Get a message from config and parsing the placeholder.
	 * @param name The config value name.
	 * @param placeholder The placeholder to replace
	 * @param value The value to replace the placeholder with
	 * @return Component, with the placeholder replaced.
	 */
	public Component getMessage(String name, String placeholder, String value) {
		String unFormattedText = config.getString(name);
		if (unFormattedText == null) {
			return Component.text("Message source is missing: " + name);
		}
		return miniMessage().deserialize(
				unFormattedText,
				Placeholder.component("prefix", getPrefix()),
				Placeholder.component("version", Component.text(Main.getInstance().getDescription().getVersion())),
				Placeholder.component("url", Component.text(Settings.PLUGIN_URL)),
				Placeholder.component(placeholder, Component.text(value))
		);
	}

	public void saveDefaultConfig() {
		if (this.getConfigFile() == null) {
			this.configFile = new File(Main.getInstance().getDataFolder(), Main.getInstance().getConfig().getString("locale") + ".yml");
		}
		if (!this.getConfigFile().exists()) {
			Main.getInstance().saveResource("lang" + File.separator + configFile.getName(), false);
		}
	}

	public void reloadConfig() {
		FileConfiguration newConfig = YamlConfiguration.loadConfiguration(configFile);
		final InputStream defConfigStream = Main.getInstance().getResource(this.localePath);
		if (defConfigStream == null) {
			return;
		}
		newConfig.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream, Charsets.UTF_8)));
		this.config = newConfig;
	}
}
