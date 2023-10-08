package eu.mikart.animvanish.effects.impl;

import eu.mikart.animvanish.Main;
import eu.mikart.animvanish.annonations.EffectAnnotation;
import eu.mikart.animvanish.effects.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@EffectAnnotation(name = "blindness", description = "Makes players around you blind for a moment", item = Material.SPLASH_POTION)
public class BlindnessEffect extends Effect {

	@Override
	public void start(Player player) {
		player.sendMessage(Main.getInstance().getLocaleConfig().getMessage("invis.blindness.author"));
		for (Entity ps : player.getNearbyEntities(10, 10, 10)) {
			if (ps instanceof Player p) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 3, 1));
				p.sendMessage(Main.getInstance().getLocaleConfig().getMessage("invis.blindness.message"));
			}
		}
		toggleVanish(player);
	}

}
