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

	public void getVersion(final Consumer<String> consumer) {
		Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
			try (InputStream inputStream = new URL("https://hangar.papermc.io/api/v1/projects/ArikSquad/AnimVanish/latestrelease").openStream(); Scanner scanner = new Scanner(inputStream)) {
				if (scanner.hasNext()) {
					consumer.accept(scanner.next());
				}
			} catch (IOException exception) {
				plugin.getLogger().info("Unable to check for updates: " + exception.getMessage());
			}
		});
	}
}
