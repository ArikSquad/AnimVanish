package eu.mikart.animvanish.effects.impl;

import eu.mikart.animvanish.Main;
import eu.mikart.animvanish.annonations.EffectAnnotation;
import eu.mikart.animvanish.effects.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

@EffectAnnotation(name = "turn", description = "Turns the close players to look the other way", item = Material.BARREL)
public class TurnEffect extends Effect {

	@Override
	public void start(Player player) {
		int player_amount = 0;
		for (Entity ps : player.getNearbyEntities(10, 10, 10)) {
			if (ps instanceof Player p) {
				p.teleport(new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(),
						p.getLocation().getYaw() + 180, p.getLocation().getPitch()));
				player_amount++;
			}
		}
		if (player_amount == 0) {
			player.sendMessage(Main.getInstance().getLocaleConfig().getMessage("invis.turn.none"));
		}
		toggleVanish(player);
	}

}
