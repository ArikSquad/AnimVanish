package eu.mikart.animvanish;

import eu.mikart.animvanish.api.AnimVanishPaperAPI;
import eu.mikart.animvanish.commands.AnimVanishCommand;
import eu.mikart.animvanish.commands.InvisibilityCommand;
import eu.mikart.animvanish.config.Locales;
import eu.mikart.animvanish.config.Settings;
import eu.mikart.animvanish.effects.EffectManager;
import eu.mikart.animvanish.effects.impl.FireworkEffect;
import eu.mikart.animvanish.effects.impl.TntEffect;
import eu.mikart.animvanish.hooks.Hook;
import eu.mikart.animvanish.impl.AdvancedVanishHook;
import eu.mikart.animvanish.impl.PremiumVanishHook;
import eu.mikart.animvanish.impl.SuperVanishHook;
import eu.mikart.animvanish.util.UpdateChecker;
import eu.mikart.animvanish.util.Utilities;
import eu.mikart.animvanish.util.Version;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.kyori.adventure.audience.Audience;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.Lamp;
import revxrsal.commands.bukkit.BukkitLamp;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class AnimVanishPaper extends JavaPlugin implements IAnimVanish {

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
        effectManager = new EffectManager(this);

        new Metrics(this, 14993);
        this.loadConfig();

        getServer().getPluginManager().registerEvents(new FireworkEffect(this), this);

        updateCheck();

        Lamp<BukkitCommandActor> lamp = BukkitLamp.builder(this).dependency(IAnimVanish.class, this).build();
        lamp.register(new AnimVanishCommand());
        lamp.register(new InvisibilityCommand());

        hooks.add(new PremiumVanishHook());
        hooks.add(new SuperVanishHook());
        hooks.add(new AdvancedVanishHook());

        for (Hook hook : hooks) {
            if (Bukkit.getPluginManager().isPluginEnabled(hook.getName())) {
                currentHook = hook;
                currentHook.init();
                break;
            }
        }

        AnimVanishPaperAPI.register(this);
    }

    @Override
    public void onDisable() {
    }

    @Override
    public String getPluginVersion() {
        return getDescription().getVersion();
    }

    public void updateCheck() {
        if (!getSettings().isUpdateChecker()) {
            return;
        }

        getServer().getAsyncScheduler().runNow(this, _ -> {
            UpdateChecker.builder().currentVersion(getVersion())
                .endpoint(UpdateChecker.Endpoint.HANGAR)
                .resource("ArikSquad/AnimVanish")
                .build().check().thenAcceptAsync(completed -> {
                    if (completed.isUpToDate()) {
                        return;
                    }
                    getServer().getLogger().info("New release is available (" + completed.getLatestVersion() + ")! Download it from here: " + Utilities.PLUGIN_URL);
                });
        });
    }

    @Override
    @NotNull
    public Path getConfigDirectory() {
        return getDataFolder().toPath();
    }

    @Override
    @NotNull
    public AnimVanishPaper getPlugin() {
        return this;
    }

    @Override
    @NotNull
    public Audience getAudience(@NotNull UUID user) {
        final Player player = getServer().getPlayer(user);
        return player == null || !player.isOnline() ? Audience.empty() : player;
    }

    @Override
    public @NotNull Audience getConsoleAudience() {
        return getServer().getConsoleSender();
    }

}
