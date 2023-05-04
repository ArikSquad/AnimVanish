package eu.mikart.animvanish.effects.impl;

import eu.mikart.animvanish.Main;
import eu.mikart.animvanish.annonations.EffectAnnotation;
import eu.mikart.animvanish.effects.Effect;
import eu.mikart.animvanish.util.Utilities;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

@EffectAnnotation(name = "npc", description = "Spawn a NPC", item = Material.SHEEP_SPAWN_EGG)
public class NpcEffect extends Effect {

	@Override
	public boolean canRun() {
		return Utilities.isCitizens();
	}

	@Override
	public void runEffect(Player player) {
		if (!Utilities.isCitizens()) {
			player.sendMessage(Main.getInstance().getLocaleConfig().getMessage("dependency.no_citizens"));
			return;
		}

		NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, player.getName());
		npc.spawn(player.getLocation());

		Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> npc.destroy(), 20 * 3);

		toggleVanish(player);
	}
}
