package eu.mikart.animvanish.effects.impl;

import eu.mikart.animvanish.Main;
import eu.mikart.animvanish.effects.Effect;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class HerobrineEffect extends Effect {
	public HerobrineEffect() {
		super("herobrine", "Strike the player with an lightning", new ItemStack(Material.ZOMBIE_HEAD));
	}

	@Override
	public void runEffect(Player player) {
		long timeBefore = player.getWorld().getTime();

		player.getWorld().strikeLightningEffect(player.getLocation());
		if (Main.getInstance().getConfig().getBoolean("effects.herobrine.night")) {
			player.getWorld().setTime(14000);

			Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> player.getWorld().setTime(timeBefore), 20 * 5);
		}
		toggleVanish(player);
	}
}
