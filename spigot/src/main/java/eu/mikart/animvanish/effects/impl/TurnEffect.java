package eu.mikart.animvanish.effects.impl;

import eu.mikart.animvanish.IAnimVanish;
import eu.mikart.animvanish.annotations.EffectAnnotation;
import eu.mikart.animvanish.effects.InternalEffect;
import eu.mikart.animvanish.user.BukkitUser;
import eu.mikart.animvanish.user.OnlineUser;
import net.kyori.adventure.audience.Audience;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

@EffectAnnotation(name = "turn", description = "Turns the close players to look the other way", item = "BARREL")
public class TurnEffect extends InternalEffect {

	public TurnEffect(IAnimVanish plugin) {
		super(plugin);
	}

	@Override
	public void start(OnlineUser p) {
		Player player = ((BukkitUser) p).getPlayer();
		Audience audience = plugin.getAudience(player.getUniqueId());
		int playerAmount = 0;
		for (Entity ps : player.getNearbyEntities(10, 10, 10)) {
			if (ps instanceof Player nearbyPlayer) {
				nearbyPlayer.teleport(new Location(nearbyPlayer.getWorld(), nearbyPlayer.getLocation().getX(), nearbyPlayer.getLocation().getY(), nearbyPlayer.getLocation().getZ(),
						nearbyPlayer.getLocation().getYaw() + 180, nearbyPlayer.getLocation().getPitch()));
				playerAmount++;
			}
		}
		if (playerAmount == 0) {
			plugin.getLocales().getLocale("turn_none")
					.ifPresent(audience::sendMessage);
		}
		plugin.getCurrentHook().vanish(p);
	}

}
