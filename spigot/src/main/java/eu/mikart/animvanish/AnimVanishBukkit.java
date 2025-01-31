package eu.mikart.animvanish;

import eu.mikart.animvanish.api.AnimVanishBukkitAPI;
import eu.mikart.animvanish.commands.InvisibilityCommand;
import eu.mikart.animvanish.config.Locales;
import eu.mikart.animvanish.config.Settings;
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
import org.bukkit.plugin.java.JavaPlugin;
import eu.mikart.animvanish.commands.AnimVanishCommand;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

@Getter
@NoArgsConstructor
public class AnimVanishBukkit extends JavaPlugin implements IAnimVanish {

    private AudienceProvider audiences;
    private EffectManager effectManager;
    public boolean areCommandsInitialized = false;

    @Setter
    private Set<Hook> hooks = new HashSet<>();
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

        new Metrics(this, 14993);
        this.loadConfig();

        getServer().getPluginManager().registerEvents(new FireworkEffect(this), this);
        loadPlatform(); // Load extra effects from paper

        Utilities.BETA = getDescription().getVersion().contains("BETA");
        updateCheck();


        hooks.add(new PremiumVanishHook());
        hooks.add(new SuperVanishHook());
        hooks.add(new AdvancedVanishHook());
        new AnimVanishCommand(this);
        new InvisibilityCommand(this);

        for (Hook hook : hooks) {
            if (Bukkit.getPluginManager().isPluginEnabled(hook.getName())) {
                currentHook = hook;
                currentHook.init();
                break;
            }
        }

        AnimVanishBukkitAPI.register(this);
    }

    @Override
    public void onDisable() {
        unloadPlatform();
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

    @Override
    @NotNull
    public Logger getLogger() {
        return super.getLogger();
    }
}
