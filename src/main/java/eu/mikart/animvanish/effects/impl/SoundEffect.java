package eu.mikart.animvanish.effects.impl;

import eu.mikart.animvanish.Main;
import eu.mikart.animvanish.annonations.EffectAnnotation;
import eu.mikart.animvanish.effects.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

@EffectAnnotation(name = "sound", description = "Plays a sound to close players", item = Material.AMETHYST_BLOCK)
public class SoundEffect extends Effect {

	@Override
	public void start(Player player) {
		try {
			player.getWorld().playSound(player.getLocation(), Sound.valueOf(Main.getInstance().getConfig().getString("effects.sound.type")), 1, 1);
		} catch (Exception e) {
			player.getWorld().playSound(player.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_HIT, 1, 1);
			player.sendMessage(Main.getInstance().getLocaleConfig().getMessage("invis.sound.invalid_config"));
		}
		toggleVanish(player);
	}

}
