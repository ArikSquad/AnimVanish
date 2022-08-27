package eu.mikart.animvanish.effects.impl;

import eu.mikart.animvanish.Main;
import eu.mikart.animvanish.effects.Effect;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

public class FireworkEffect extends Effect {

	public FireworkEffect() {
		super("firework", "Spawns colorful fireworks", new ItemStack(Material.FIREWORK_ROCKET));
	}

	@Override
	public void runEffect(Player player) {
		Firework fw = (Firework) player.getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK);
		FireworkMeta fwm = fw.getFireworkMeta();
		fwm.setPower(1);
		fwm.addEffect(org.bukkit.FireworkEffect.builder().with(org.bukkit.FireworkEffect.Type.BURST).withColor(Color.RED).build());
		fwm.addEffect(org.bukkit.FireworkEffect.builder().with(org.bukkit.FireworkEffect.Type.BURST).withColor(Color.GREEN).build());
		fwm.addEffect(org.bukkit.FireworkEffect.builder().with(org.bukkit.FireworkEffect.Type.BURST).withColor(Color.BLUE).build());
		fwm.addEffect(org.bukkit.FireworkEffect.builder().with(org.bukkit.FireworkEffect.Type.BURST).withColor(Color.YELLOW).build());
		fwm.addEffect(org.bukkit.FireworkEffect.builder().with(org.bukkit.FireworkEffect.Type.BURST).withColor(Color.AQUA).build());
		fw.setVelocity(new Vector(0, 2, 0));
		fw.setMetadata("nodamage", new FixedMetadataValue(Main.getInstance(), true));
		fw.setFireworkMeta(fwm);

		// This was a not intended feature, but it's so cool.
		// It isn't a bug, it's a feature
		fw.detonate();
	}


}
