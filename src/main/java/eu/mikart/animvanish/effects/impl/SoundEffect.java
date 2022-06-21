package eu.mikart.animvanish.effects.impl;

import eu.mikart.animvanish.Main;
import eu.mikart.animvanish.effects.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SoundEffect extends Effect {

	public SoundEffect() {
		super("sound", "Plays a sound to close players", new ItemStack(Material.AMETHYST_BLOCK));
	}

	@Override
	public void runEffect(Player player) {
		try {
			player.getWorld().playSound(player.getLocation(), Sound.valueOf(Main.instance.getConfig().getString("effects.sound.type")), 1, 1);
		} catch (Exception e) {
			player.getWorld().playSound(player.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_HIT, 1, 1);
			player.sendMessage(messages.getMessage("invis.sound.invalid_config"));
		}
	}

}
