package eu.mikart.animvanish.effects.impl;

import eu.mikart.animvanish.IAnimVanish;
import eu.mikart.animvanish.annotations.EffectAnnotation;
import eu.mikart.animvanish.effects.InternalEffect;
import eu.mikart.animvanish.user.BukkitUser;
import eu.mikart.animvanish.user.OnlineUser;
import net.kyori.adventure.audience.Audience;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

@EffectAnnotation(name = "particle", description = "Spawns particles", item = "AMETHYST_SHARD")
public class ParticleEffect extends InternalEffect {

	public ParticleEffect(IAnimVanish plugin) {
		super(plugin);
	}

	@Override
	public void start(OnlineUser p) {
		Player player = ((BukkitUser) p).getPlayer();
		Audience audience = plugin.getAudience(player.getUniqueId());
		try {
			player.getWorld().spawnParticle(Particle.valueOf(plugin.getSettings().getEffects().getParticle().getType()),
					player.getEyeLocation().add(0, 2, 0), plugin.getSettings().getEffects().getParticle().getAmount());
		} catch (Exception e) {
			plugin.getLocales().getLocale("particle_invalid_config")
					.ifPresent(audience::sendMessage);
			plugin.getLocales().getLocale("particle_invalid_config")
					.ifPresent(getConsole()::sendMessage);
			player.getWorld().spawnParticle(Particle.DRAGON_BREATH, player.getEyeLocation().add(0, 2, 0),
					50);
		}

		plugin.getCurrentHook().vanish(p);
	}
}
