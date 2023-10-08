package eu.mikart.animvanish.effects.impl;

import eu.mikart.animvanish.Main;
import eu.mikart.animvanish.annonations.EffectAnnotation;
import eu.mikart.animvanish.effects.Effect;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

@EffectAnnotation(name = "lightning", description = "Strike the player with a lightning", item = Material.LIGHTNING_ROD)
public class LightningEffect extends Effect {
	@Override
	public void start(Player player) {
		long timeBefore = player.getWorld().getTime();

		player.getWorld().strikeLightningEffect(player.getLocation());
		if (Main.getInstance().getConfig().getBoolean("effects.lightning.night")) {
			player.getWorld().setTime(14000);

			Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> player.getWorld().setTime(timeBefore), 20 * 5);
		}
		toggleVanish(player);
	}
}
