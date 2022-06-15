package eu.mikart.animvanish.config;

import eu.mikart.animvanish.Main;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;

public class MessageManager {

	private final Main plugin;
	private FileConfiguration config = null;
	private File configFile = null;

	public MessageManager(Main plugin) {
		this.plugin = plugin;
		saveDefaultConfig();
	}

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

	public FileConfiguration getConfig() {
		if (this.config == null) {
			reloadConfig();
		}
		return this.config;
	}

	public Component getMessage(String name) {
		String prefix = getConfig().getString("prefix");
		String message = getConfig().getString(name);
		return miniMessage().deserialize(prefix + message);
	}

	public Component getMessage(String name, String placeholder, String value) {
		String message = getConfig().getString(name);
		return miniMessage().deserialize(message, Placeholder.component(placeholder, Component.text(value)));
	}

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

	public void saveDefaultConfig() {
		if (this.configFile == null) {
			this.configFile = new File(this.plugin.getDataFolder(), "messages.yml");
		}
		if (!this.configFile.exists()) {
			this.plugin.saveResource("messages.yml", false);
		}
	}

}
