package eu.mikart.animvanish.effects.impl;

import eu.mikart.animvanish.IAnimVanish;
import eu.mikart.animvanish.annotations.EffectAnnotation;
import eu.mikart.animvanish.effects.InternalEffect;
import eu.mikart.animvanish.user.BukkitUser;
import eu.mikart.animvanish.user.OnlineUser;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
@EffectAnnotation(name = "tnt", description = "Spawn a tnt", item = "TNT")
public class TntEffect extends InternalEffect {

	public TntEffect(IAnimVanish plugin) {
		super(plugin);
	}

	@Override
	public void start(OnlineUser p) {
		Player player = ((BukkitUser) p).getPlayer();
		player.getWorld().spawn(player.getLocation(), TNTPrimed.class, tnt -> tnt.setYield(0));
		plugin.getCurrentHook().vanish(p);
	}

}
