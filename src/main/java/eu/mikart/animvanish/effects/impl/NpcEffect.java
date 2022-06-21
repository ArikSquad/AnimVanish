package eu.mikart.animvanish.effects.impl;

import eu.mikart.animvanish.Main;
import eu.mikart.animvanish.effects.Effect;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class NpcEffect extends Effect {

	public NpcEffect() {
		super("npc", "Spawns a npc", new ItemStack(Material.SHEEP_SPAWN_EGG));
	}

	@Override
	public void runEffect(Player player) {
		if (Bukkit.getPluginManager().isPluginEnabled("Citizens")) {
			NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, player.getName());
			npc.spawn(player.getLocation());

			Bukkit.getScheduler().runTaskLater(Main.instance, () -> npc.destroy(), 20 * 3);
		} else {
			player.sendMessage(messages.getMessage("invis.dependency.no_citizens"));
		}
	}
}
