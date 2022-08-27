package eu.mikart.animvanish.effects.impl;

import eu.mikart.animvanish.Main;
import eu.mikart.animvanish.effects.Effect;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;

public class ZombieEffect extends Effect {

	public ZombieEffect() {
		super("zombie", "Creates a zombie where you vanish", new ItemStack(Material.ZOMBIE_SPAWN_EGG));
	}

	@Override
	public void runEffect(Player player) {
		Zombie zombie = (Zombie) player.getWorld().spawnEntity(player.getLocation(), EntityType.ZOMBIE);
		zombie.setAI(false);
		zombie.setAdult();
		zombie.setInvulnerable(true);
		Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
			player.spawnParticle(Particle.HEART, zombie.getLocation(), 3);
			zombie.remove();
		}, 20 * Main.getInstance().getConfig().getLong("effects.zombie.despawn_after"));
	}

}
