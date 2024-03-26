package eu.mikart.animvanish.util;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;

public class UpdateChecker {
	private final JavaPlugin plugin;

	public UpdateChecker(JavaPlugin plugin) {
		this.plugin = plugin;
	}

	private void getVersionAsync(String apiUrl, Consumer<String> consumer) {
		Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
			try (InputStream inputStream = new URL(apiUrl).openStream(); Scanner scanner = new Scanner(inputStream)) {
				if (scanner.hasNext()) {
					consumer.accept(scanner.next());
				}
			} catch (IOException exception) {
				plugin.getLogger().info("Unable to check for updates: " + exception.getMessage());
			}
		});
	}

	public void getVersion(final Consumer<String> consumer) {
		getVersionAsync("https://hangar.papermc.io/api/v1/projects/ArikSquad/AnimVanish/latestrelease", consumer);
	}

	public void getBetaVersion(final Consumer<String> consumer) {
		getVersionAsync("https://hangar.papermc.io/api/v1/projects/ArikSquad/AnimVanish/latest?channel=Beta", consumer);
	}

	/**
	 * Gets the latest version of the plugin
	 * @param consumer The consumer that accepts the latest version
	 */
	public void getLatestVersion(final Consumer<String> consumer) {
		getVersion(version -> {
			if (!Utilities.BETA) {
				consumer.accept(version);
				return;
			}
			getBetaVersion(betaVersion -> consumer.accept(version.compareTo(betaVersion) > 0 ? version : betaVersion));
		});
	}
}
