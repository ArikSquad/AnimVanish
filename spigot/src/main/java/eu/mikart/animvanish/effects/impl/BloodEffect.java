package eu.mikart.animvanish.effects.impl;

import eu.mikart.animvanish.IAnimVanish;
import eu.mikart.animvanish.annotations.EffectAnnotation;
import eu.mikart.animvanish.effects.InternalEffect;
import eu.mikart.animvanish.user.BukkitUser;
import eu.mikart.animvanish.user.OnlineUser;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.concurrent.ThreadLocalRandom;

@EffectAnnotation(name = "blood", description = "Makes some red particles around the player", item = "REDSTONE_BLOCK")
public class BloodEffect extends InternalEffect {

	public BloodEffect(IAnimVanish plugin) {
		super(plugin);
	}

	@Override
	public void start(OnlineUser p) {
		Player player = ((BukkitUser) p).getPlayer();
		Location location = player.getLocation();
		location.add(0, ThreadLocalRandom.current().nextFloat() * 1.75, 0);
		player.getWorld().playEffect(location, org.bukkit.Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
		player.playEffect(EntityEffect.HURT);
		plugin.getCurrentHook().vanish(p);
	}

}
