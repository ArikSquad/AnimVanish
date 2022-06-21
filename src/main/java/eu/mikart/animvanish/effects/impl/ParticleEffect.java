package eu.mikart.animvanish.effects.impl;

import eu.mikart.animvanish.Main;
import eu.mikart.animvanish.effects.Effect;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ParticleEffect extends Effect {
	public ParticleEffect() {
		super("particle", "Spawns particles", new ItemStack(Material.AMETHYST_SHARD));
	}

	@Override
	public void runEffect(Player player) {
		try {
			player.getWorld().spawnParticle(Particle.valueOf(Main.instance.getConfig().getString("effects.particle.type")),
					player.getEyeLocation().add(0, 2, 0), 50);
		} catch (Exception e) {
			player.sendMessage(messages.getMessage("invis.particle.invalid_config"));
			Bukkit.getConsoleSender().sendMessage(messages.getMessage("invis.particle.invalid_config"));
			player.getWorld().spawnParticle(Particle.DRAGON_BREATH, player.getEyeLocation().add(0, 2, 0),
					50);
		}
	}
}
