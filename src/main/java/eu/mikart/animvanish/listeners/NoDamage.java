package eu.mikart.animvanish.listeners;

import org.bukkit.block.Sign;
import org.bukkit.entity.Firework;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class NoDamage implements Listener {

	@EventHandler
	public void EntityDamagebyEntityEvent(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Firework fw) {
			if (fw.hasMetadata("nodamage")) {
				e.setCancelled(true);
			}
		}
	}

}
