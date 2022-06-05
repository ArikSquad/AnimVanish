package eu.mikart.animvanish.listeners;

import eu.mikart.animvanish.Main;
import org.bukkit.entity.Firework;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class NoDamage implements Listener {

	@EventHandler
	public void EntityDamagebyEntityEvent(EntityDamageByEntityEvent e) {
		Main.instance.getLogger().info("EntityDamageByEntityEvent");
		if (e.getDamager() instanceof Firework) {
			Firework fw = (Firework) e.getDamager();
			if (fw.hasMetadata("nodamage")) {
				e.setCancelled(true);
			}
		}
	}

}
