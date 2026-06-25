package eu.mikart.animvanish.impl;

import de.myzelyam.api.vanish.VanishAPI;
import eu.mikart.animvanish.hooks.Hook;
import eu.mikart.animvanish.user.PaperUser;
import eu.mikart.animvanish.user.OnlineUser;
import org.bukkit.entity.Player;

public class SuperVanishHook extends Hook {

	public SuperVanishHook() {
		super("SuperVanish");
	}

	@Override
	public void vanish(OnlineUser p) {
		Player player = ((PaperUser) p).getPlayer();
		if (isVanished(p)) {
			VanishAPI.showPlayer(player);
		} else {
			VanishAPI.hidePlayer(player);
		}
	}

	@Override
	public boolean isVanished(OnlineUser p) {
		return VanishAPI.isInvisible(((PaperUser) p).getPlayer());
	}
}
