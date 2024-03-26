package eu.mikart.animvanish.effects.impl;

import eu.mikart.animvanish.IAnimVanish;
import eu.mikart.animvanish.annotations.EffectAnnotation;
import eu.mikart.animvanish.effects.InternalEffect;
import eu.mikart.animvanish.user.BukkitUser;
import eu.mikart.animvanish.user.OnlineUser;
import net.kyori.adventure.audience.Audience;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

@EffectAnnotation(name = "sound", description = "Plays a sound to close players", item = "AMETHYST_BLOCK")
public class SoundEffect extends InternalEffect {

	public SoundEffect(IAnimVanish plugin) {
		super(plugin);
	}

	@Override
	public void start(OnlineUser p) {
		Player player = ((BukkitUser) p).getPlayer();
		Audience audience = plugin.getAudience(player.getUniqueId());
		try {
			player.getWorld().playSound(player.getLocation(), Sound.valueOf(plugin.getSettings().getEffects().getSound().getType()), 1, 1);
		} catch (Exception e) {
			player.getWorld().playSound(player.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_HIT, 1, 1);
			plugin.getLocales().getLocale("sound_invalid_config")
					.ifPresent(audience::sendMessage);
		}

		plugin.getCurrentHook().vanish(p);
	}

}
