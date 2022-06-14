package eu.mikart.animvanish.listeners;

import org.bukkit.entity.Firework;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class NoDamage implements Listener {

	@EventHandler
	public void EntityDamagebyEntityEvent(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Firework) {
			Firework fw = (Firework) e.getDamager();
			if (fw.hasMetadata("nodamage")) {
				e.setCancelled(true);
			}
		}
	}

}
