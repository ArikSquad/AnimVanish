package eu.mikart.animvanish.effects.impl;

import eu.mikart.animvanish.Main;
import eu.mikart.animvanish.annonations.EffectAnnotation;
import eu.mikart.animvanish.effects.Effect;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

@EffectAnnotation(name = "particle", description = "Spawns particles", item = Material.AMETHYST_SHARD)
public class ParticleEffect extends Effect {

	@Override
	public void start(Player player) {
		try {
			player.getWorld().spawnParticle(Particle.valueOf(Main.getInstance().getConfig().getString("effects.particle.type")),
					player.getEyeLocation().add(0, 2, 0), 50);
		} catch (Exception e) {
			player.sendMessage(Main.getInstance().getLocaleConfig().getMessage("invis.particle.invalid_config"));
			Bukkit.getConsoleSender().sendMessage(Main.getInstance().getLocaleConfig().getMessage("invis.particle.invalid_config"));
			player.getWorld().spawnParticle(Particle.DRAGON_BREATH, player.getEyeLocation().add(0, 2, 0),
					50);
		}
		toggleVanish(player);
	}
}
