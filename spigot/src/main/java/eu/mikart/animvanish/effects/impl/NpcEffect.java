package eu.mikart.animvanish.effects.impl;

import eu.mikart.animvanish.IAnimVanish;
import eu.mikart.animvanish.annotations.EffectAnnotation;
import eu.mikart.animvanish.effects.InternalEffect;
import eu.mikart.animvanish.user.BukkitUser;
import eu.mikart.animvanish.user.OnlineUser;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.kyori.adventure.audience.Audience;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

@EffectAnnotation(name = "npc", description = "Spawn a NPC", item = "SHEEP_SPAWN_EGG")
public class NpcEffect extends InternalEffect {

	public NpcEffect(IAnimVanish plugin) {
		super(plugin);
	}

	@Override
	public boolean canRun() {
		return Bukkit.getPluginManager().isPluginEnabled("Citizens");
	}

	@Override
	public void start(OnlineUser p) {
		Player player = ((BukkitUser) p).getPlayer();
		Audience audience = plugin.getAudience(player.getUniqueId());
		if (!canRun()) {
			plugin.getLocales().getLocale("dependency_no_citizens")
					.ifPresent(audience::sendMessage);
			return;
		}

		NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, player.getName());
		npc.spawn(player.getLocation());

		long duration = plugin.getSettings().getEffects().getNpc().getDuration();
		if (duration > 30) {
			plugin.getLogger().warning("The duration of the NPC effect is too long, setting it to 3 seconds.");
			duration = 3;
		}

		Bukkit.getScheduler().runTaskLater((Plugin) plugin, () -> npc.destroy(), 20 * duration);
		plugin.getCurrentHook().vanish(p);
	}
}
