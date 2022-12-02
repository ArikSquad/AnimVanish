package eu.mikart.animvanish.effects.impl;

import eu.mikart.animvanish.annonations.EffectAnnotation;
import eu.mikart.animvanish.effects.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;

@EffectAnnotation(name = "tnt", description = "Spawn a tnt", item = Material.TNT)
public class TntEffect extends Effect {

	@Override
	public void runEffect(Player player) {
		player.getWorld().spawn(player.getLocation(), TNTPrimed.class, (tnt) -> tnt.setYield(0));
		toggleVanish(player);
	}
}
