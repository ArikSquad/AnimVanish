package eu.mikart.animvanish.util;

import eu.mikart.animvanish.Main;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;

public class MessageConfig {

	private final Main plugin;
	private FileConfiguration config = null;
	private File configFile = null;

	public MessageConfig(Main plugin) {
		this.plugin = plugin;
		saveDefaultConfig();
	}

	/**
	 * Reloads the config file
	 */
	public void reloadConfig() {
		if (this.configFile == null) {
			this.configFile = new File(this.plugin.getDataFolder(), "messages.yml");
		}
		this.config = YamlConfiguration.loadConfiguration(this.configFile);
		InputStream defStream = this.plugin.getResource("messages.yml");

		if (defStream != null) {
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defStream));
			this.config.setDefaults(defConfig);
		}
	}

	/**
	 * Gets message manager.
	 * @return Configuration file
	 */
	public FileConfiguration getConfig() {
		if (this.config == null) {
			reloadConfig();
		}
		return this.config;
	}

	public Component getPrefix() {
		return miniMessage().deserialize(Objects.requireNonNull(getConfig().getString("prefix")));
	}

	/**
	 * Get a message from config and parsing the colours.
	 * @param name The config value name.
	 * @return Component, with prefix before it.
	 */
	public Component getMessage(String name) {
		String unFormattedText = getConfig().getString(name);
		if (unFormattedText == null) {
			return Component.text("Message file is missing " + name);
		}
		return MiniMessage.miniMessage().deserialize(
				unFormattedText,
				Placeholder.component("prefix", getPrefix()),
				Placeholder.component("version", Component.text(Settings.getPluginVersion())),
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
		String unFormattedText = getConfig().getString(name);
		if (unFormattedText == null) {
			return Component.text("Message file is missing " + name);
		}
		return miniMessage().deserialize(
				unFormattedText,
				Placeholder.component("prefix", getPrefix()),
				Placeholder.component("version", Component.text(Settings.getPluginVersion())),
				Placeholder.component("url", Component.text(Settings.PLUGIN_URL)),
				Placeholder.component(placeholder, Component.text(value))
		);
	}

	/**
	 * Saves configuration file.
	 */
	public void saveConfig() {
		if (this.config == null || this.configFile == null) {
			return;
		}
		try {
			this.getConfig().save(this.configFile);
		} catch (IOException ex) {
			plugin.getLogger().severe("Could not save config to " + this.configFile);
		}
	}

	/**
	 * Saves default configuration.
	 */
	public void saveDefaultConfig() {
		if (this.configFile == null) {
			this.configFile = new File(this.plugin.getDataFolder(), "messages.yml");
		}
		if (!this.configFile.exists()) {
			this.plugin.saveResource("messages.yml", false);
		}
	}

}
