package eu.mikart.animvanish.effects.impl;

import eu.mikart.animvanish.Main;
import eu.mikart.animvanish.effects.Effect;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.ThreadLocalRandom;

public class BloodEffect extends Effect {

	public BloodEffect() {
		super("blood", "Makes some red particles", new ItemStack(Material.REDSTONE));
	}

	@Override
	public void runEffect(Player player) {

		new BukkitRunnable() {
			int i = 0;
			public void run() {

				i++;
				Location location = player.getLocation();
				location.add(0, ThreadLocalRandom.current().nextFloat() * 1.75, 0);
				location.getWorld().playEffect(location, org.bukkit.Effect.STEP_SOUND, Material.REDSTONE_BLOCK);

				player.playEffect(EntityEffect.HURT);
				if(i == 5) {
					this.cancel();
				}
			}
		}.runTaskTimerAsynchronously(Main.instance, 0, 2);
	}

}
