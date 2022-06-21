package eu.mikart.animvanish.effects.impl;

import eu.mikart.animvanish.effects.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BlindnessEffect extends Effect {

	public BlindnessEffect() {
		super("blindness", "Makes players around you blind for a moment", new ItemStack(Material.SPLASH_POTION));
	}

	@Override
	public void runEffect(Player player) {
		player.sendMessage(messages.getMessage("invis.blindness.author"));
		for (Entity ps : player.getNearbyEntities(10, 10, 10)) {
			if (ps instanceof Player p) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 3, 1));
				p.sendMessage(messages.getMessage("invis.blindness.message"));
			}
		}
	}

}
