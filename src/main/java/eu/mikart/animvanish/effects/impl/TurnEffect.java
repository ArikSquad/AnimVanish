package eu.mikart.animvanish.effects.impl;

import eu.mikart.animvanish.effects.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TurnEffect extends Effect {

	public TurnEffect() {
		super("turn", "Turns the close players to look the other way", new ItemStack(Material.BARREL));
	}

	@Override
	public void runEffect(Player player) {
		int player_amount = 0;
		for (Entity ps : player.getNearbyEntities(10, 10, 10)) {
			if (ps instanceof Player p) {
				p.teleport(new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(),
						p.getLocation().getYaw() + 180, p.getLocation().getPitch()));
				player_amount++;
			}
		}
		if (player_amount == 0) {
			player.sendMessage(messages.getMessage("invis.turn.none"));
		}
		toggleVanish(player);
	}

}
