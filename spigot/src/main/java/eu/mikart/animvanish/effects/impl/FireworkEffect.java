package eu.mikart.animvanish.effects.impl;

import eu.mikart.animvanish.IAnimVanish;
import eu.mikart.animvanish.annotations.EffectAnnotation;
import eu.mikart.animvanish.effects.InternalEffect;
import eu.mikart.animvanish.user.BukkitUser;
import eu.mikart.animvanish.user.OnlineUser;
import org.bukkit.Color;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

@EffectAnnotation(name = "firework", description = "Spawns colorful fireworks", item = "FIREWORK_ROCKET")
public class FireworkEffect extends InternalEffect implements Listener {

	public FireworkEffect(IAnimVanish plugin) {
		super(plugin);
	}

	@Override
	public void start(OnlineUser p) {
		Player player = ((BukkitUser) p).getPlayer();

		EntityType fireworkType;
		try {
			fireworkType = EntityType.valueOf("FIREWORK_ROCKET"); // Good hack to support newer versions
		} catch (IllegalArgumentException e) {
			fireworkType = EntityType.FIREWORK;
		}

		Firework fw = (Firework) player.getWorld().spawnEntity(player.getLocation(), fireworkType);
		fw.setVelocity(new Vector(0, 2, 0));
		fw.getPersistentDataContainer().set(new NamespacedKey((Plugin) plugin, "nodamage"), PersistentDataType.INTEGER, 1);

		FireworkMeta fwm = fw.getFireworkMeta();
		fwm.setPower(1);
		for (java.awt.Color color : plugin.getSettings().getEffects().getFirework().getColors()) {
			fwm.addEffect(org.bukkit.FireworkEffect.builder().with(org.bukkit.FireworkEffect.Type.valueOf(plugin.getSettings().getEffects().getFirework().getType().toUpperCase())).withColor(Color.fromRGB(color.getRed(), color.getGreen(), color.getBlue())).build());
		}

		fw.setFireworkMeta(fwm);

		// This wasn't an intended feature, but it's so cool, the firework instantly explodes!
		// It's not a bug, it's a feature
		fw.detonate();
		plugin.getCurrentHook().vanish(p);
	}

	@EventHandler
	public void EntityDamageByEntityEvent(EntityDamageByEntityEvent e) {
		// In some 1.20.4 builds, Firework damage won't be cancelled
		// https://github.com/PaperMC/Paper/issues/10273
		// It has been fixed in Paper #10307 (paper build #453)
		// > will probably do damage in spigot

		if (e.getDamager() instanceof Firework fw) {
			if (fw.getPersistentDataContainer().get(new NamespacedKey((Plugin) plugin, "nodamage"), PersistentDataType.INTEGER) != null) {
				e.setCancelled(true);
			}
		}
	}

}
