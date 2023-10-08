package eu.mikart.animvanish.hooks;

import org.bukkit.entity.Player;

public interface HookInterface {

	void vanish(Player p);

	boolean isVanished(Player p);

}
