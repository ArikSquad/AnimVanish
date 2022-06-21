package eu.mikart.animvanish.effects.impl;

import eu.mikart.animvanish.effects.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.inventory.ItemStack;

public class TntEffect extends Effect {


	public TntEffect() {
		super("tnt", "Spawns a tnt", new ItemStack(Material.TNT));
	}

	@Override
	public void runEffect(Player player) {
		player.getWorld().spawn(player.getLocation(), TNTPrimed.class, (tnt) -> tnt.setYield(0));
	}
}
