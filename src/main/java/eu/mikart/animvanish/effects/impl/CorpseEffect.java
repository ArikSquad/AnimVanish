package eu.mikart.animvanish.effects.impl;

import dev.geco.gsit.api.GSitAPI;
import dev.geco.gsit.objects.GetUpReason;
import eu.mikart.animvanish.Main;
import eu.mikart.animvanish.effects.Effect;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Pose;
import org.bukkit.inventory.ItemStack;

public class CorpseEffect extends Effect {

	public CorpseEffect() {
		super("corpse", "Lays down and disappears", new ItemStack(Material.SKELETON_SKULL));
	}

	@Override
	public void runEffect(Player player) {
		if (isVanished(player)) {
			player.sendMessage(messages.getMessage("invis.only_to_vanish"));
			return;
		}
		if (!Bukkit.getPluginManager().isPluginEnabled("GSit")) {
			player.sendMessage(messages.getMessage("invis.dependency.no_gsit"));
			return;
		}
		if(!GSitAPI.canPlayerSit(player)) {
			player.sendMessage(messages.getMessage("invis.dead.cant_sit"));
			return;
		}

		GSitAPI.createPose(player.getLocation().getBlock().getRelative(0, -1, 0), player, Pose.SLEEPING);
		Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
			toggleVanish(player);
			GSitAPI.removePose(player, GetUpReason.PLUGIN);
		}, 20 * 2);
	}
}
