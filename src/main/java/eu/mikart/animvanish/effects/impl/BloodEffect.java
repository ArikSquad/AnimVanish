package eu.mikart.animvanish.effects.impl;

import eu.mikart.animvanish.annonations.EffectAnnotation;
import eu.mikart.animvanish.effects.Effect;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.concurrent.ThreadLocalRandom;

@EffectAnnotation(name = "blood", description = "Makes some red particles around the player", item = Material.REDSTONE)
public class BloodEffect extends Effect {

	@Override
	public void start(Player player) {
		Location location = player.getLocation();
		location.add(0, ThreadLocalRandom.current().nextFloat() * 1.75, 0);
		location.getWorld().playEffect(location, org.bukkit.Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
		player.playEffect(EntityEffect.HURT);
		toggleVanish(player);
	}

}
