package eu.mikart.animvanish;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import eu.mikart.animvanish.effects.impl.TntEffect;
import lombok.NoArgsConstructor;
import net.kyori.adventure.audience.Audience;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@NoArgsConstructor
public class AnimVanishPaper extends AnimVanishBukkit {

	@Override
	public void onLoad() {
		CommandAPI.onLoad(new CommandAPIBukkitConfig(this).shouldHookPaperReload(true).silentLogs(true));
	}

	@Override
	@NotNull
	public Audience getAudience(@NotNull UUID user) {
		final Player player = getServer().getPlayer(user);
		return player == null || !player.isOnline() ? Audience.empty() : player;
	}

	@Override
	public void loadPlatform() {
		CommandAPI.onEnable();
		areCommandsInitialized = true;
		getEffectManager().registerEffect(new TntEffect(this));
	}

	@Override
	public void unloadPlatform() {
		CommandAPI.onDisable();
	}

}
