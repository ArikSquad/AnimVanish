package eu.mikart.animvanish;

import co.aikar.commands.BukkitCommandExecutionContext;
import co.aikar.commands.PaperCommandManager;
import co.aikar.commands.contexts.ContextResolver;
import com.google.common.collect.Sets;
import eu.mikart.animvanish.api.AnimVanishBukkitAPI;
import eu.mikart.animvanish.commands.InvisibilityCommand;
import eu.mikart.animvanish.config.Locales;
import eu.mikart.animvanish.config.Settings;
import eu.mikart.animvanish.effects.BareEffect;
import eu.mikart.animvanish.effects.EffectManager;
import eu.mikart.animvanish.effects.impl.FireworkEffect;
import eu.mikart.animvanish.hooks.Hook;
import eu.mikart.animvanish.hooks.impl.AdvancedVanishHook;
import eu.mikart.animvanish.hooks.impl.PremiumVanishHook;
import eu.mikart.animvanish.hooks.impl.SuperVanishHook;
import eu.mikart.animvanish.util.UpdateChecker;
import eu.mikart.animvanish.util.Utilities;
import eu.mikart.animvanish.util.Version;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import eu.mikart.animvanish.commands.AnimVanishCommand;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.Set;
import java.util.logging.Logger;

@Getter
@NoArgsConstructor
public class AnimVanishBukkit extends JavaPlugin implements IAnimVanish {

	private AudienceProvider audiences;
	private PaperCommandManager commandManager;
	private static AnimVanishBukkit instance;
	private EffectManager effectManager;

	@Setter
	private Set<Hook> hooks = Sets.newHashSet();
	@Setter
	private Hook currentHook;
	@Setter
	private Settings settings;
	@Setter
	private Locales locales;

	@Override
	public void onEnable() {
		audiences = BukkitAudiences.create(this);
		effectManager = new EffectManager(this);
		commandManager = new PaperCommandManager(this);
		instance = this;


		new Metrics(this, 14993);
		this.loadConfig();

		getServer().getPluginManager().registerEvents(new FireworkEffect(this), this);
		commandSetup();
		loadExtra(); // Load extra effects from paper

		Utilities.BETA = getDescription().getVersion().contains("BETA");
		updateCheck();


		hooks.add(new PremiumVanishHook());
		hooks.add(new SuperVanishHook());
		hooks.add(new AdvancedVanishHook());

		for (Hook hook : hooks) {
			if (Bukkit.getPluginManager().isPluginEnabled(hook.getName())) {
				currentHook = hook;
				break;
			}
		}

		AnimVanishBukkitAPI.register(this);
	}

	public void commandSetup() {
		commandManager.getCommandContexts().registerContext(BareEffect.class, getEffectContextResolver());
		commandManager.getCommandCompletions().registerAsyncCompletion("effects", c -> getEffectManager().getEffects().stream().map(BareEffect::getName).toList());

		// Register commands
		commandManager.registerCommand(new AnimVanishCommand(this));
		commandManager.registerCommand(new InvisibilityCommand(this));
	}

	@Override
	public void onDisable() {
		instance = null;
		commandManager.unregisterCommands();
	}

	@Override
	public String getPluginVersion() {
		return getDescription().getVersion();
	}

	@Override
	public @NotNull AudienceProvider getAudiences() {
		return audiences;
	}

	public void updateCheck() {
		if (!getSettings().isUpdateChecker()) {
			return;
		}

		new UpdateChecker(this).getLatestVersion(version -> {
			Version currentVersion = getVersion();
			Version latestVersion = new Version(version);

			String channel = Utilities.BETA ? "beta" : "main";

			if (currentVersion.compareTo(latestVersion) < 0) {
				getLogger().warning("New " + channel + " channel release is available (" + version + ")! Download it from here: " + Utilities.PLUGIN_URL);
			} else {
				getLogger().info("Running on the latest AnimVanish version");
			}
		});
	}

	@Override
	@NotNull
	public Path getConfigDirectory() {
		return getDataFolder().toPath();
	}

	@Override
	@NotNull
	public AnimVanishBukkit getPlugin() {
		return this;
	}

	public static ContextResolver<BareEffect, BukkitCommandExecutionContext> getEffectContextResolver() {
		return (context) -> {
			String effectName = context.popFirstArg();
			if (effectName == null || effectName.isEmpty()) {
				return null; // No effect name provided
			}

			BareEffect effect = instance.getEffectManager().getEffect(effectName);
			if (effect == null) {
				instance.getLocales().getLocale("not_found").ifPresent(message -> {
					if (context.getSender() instanceof Player player) {
						instance.getAudience(player.getUniqueId()).sendMessage(message);
					} else {
						instance.getConsole().sendMessage(message);
					}
				});
			}

			return effect;
		};
	}

	@Override
	@NotNull
	public Logger getLogger() {
		return super.getLogger();
	}
}
