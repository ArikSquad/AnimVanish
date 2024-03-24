package eu.mikart.animvanish;

import eu.mikart.animvanish.effects.impl.TntEffect;
import lombok.NoArgsConstructor;
import net.kyori.adventure.audience.Audience;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@NoArgsConstructor
public class AnimVanishPaper extends AnimVanishBukkit {

	@Override
	@NotNull
	public Audience getAudience(@NotNull UUID user) {
		final Player player = getServer().getPlayer(user);
		return player == null || !player.isOnline() ? Audience.empty() : player;
	}

	@Override
	public void loadExtra() {
		getEffectManager().registerEffect(new TntEffect(this));
	}

}
