package eu.mikart.animvanish.effects.impl;

import eu.mikart.animvanish.IAnimVanish;
import eu.mikart.animvanish.annotations.EffectAnnotation;
import eu.mikart.animvanish.effects.InternalEffect;
import eu.mikart.animvanish.user.BukkitUser;
import eu.mikart.animvanish.user.OnlineUser;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

@EffectAnnotation(name = "lightning", description = "Strike the player with a lightning", item = "LIGHTNING_ROD")
public class LightningEffect extends InternalEffect {

	public LightningEffect(IAnimVanish plugin) {
		super(plugin);
	}

	@Override
	public void start(OnlineUser p) {
		Player player = ((BukkitUser) p).getPlayer();
		long timeBefore = player.getWorld().getTime();

		player.getWorld().strikeLightningEffect(player.getLocation());
		if (plugin.getSettings().getEffects().getLightning().isNight()) {
			player.getWorld().setTime(14000);
			Bukkit.getScheduler().runTaskLater((Plugin) plugin, () -> player.getWorld().setTime(timeBefore), 20 * 5);
		}

		plugin.getCurrentHook().vanish(p);
	}
}
