package eu.mikart.animvanish.effects.impl;

import eu.mikart.animvanish.IAnimVanish;
import eu.mikart.animvanish.annotations.EffectAnnotation;
import eu.mikart.animvanish.effects.InternalEffect;
import eu.mikart.animvanish.user.BukkitUser;
import eu.mikart.animvanish.user.OnlineUser;
import net.kyori.adventure.audience.Audience;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@EffectAnnotation(name = "blindness", description = "Makes players around you blind for a moment", item = "SPLASH_POTION")
public class BlindnessEffect extends InternalEffect {

	public BlindnessEffect(IAnimVanish plugin) {
		super(plugin);
	}

	/**
	 * We could use BukkitUser to add the potion effect to the player
	 * if we ever are going to move these to common
	 * @see BukkitUser#addPotionEffect
	 */
	@Override
	public void start(OnlineUser p) {
		Player player = ((BukkitUser) p).getPlayer();
		Audience sender = plugin.getAudience(player.getUniqueId());
		plugin.getLocales().getLocale("blindness_author")
				.ifPresent(sender::sendMessage);
		int duration = plugin.getSettings().getEffects().getBlindness().getDuration();
		int radius = plugin.getSettings().getEffects().getBlindness().getRadius();
		for (Entity ps : player.getNearbyEntities(radius, radius, radius)) {
			if (ps instanceof Player rPlayer) {
				Audience rAudience = plugin.getAudience(rPlayer.getUniqueId());
				rPlayer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * duration, 1));
				plugin.getLocales().getLocale("blindness_message")
						.ifPresent(rAudience::sendMessage);
			}
		}
		plugin.getCurrentHook().vanish(p);
	}

}
