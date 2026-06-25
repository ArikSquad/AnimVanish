package eu.mikart.animvanish.impl;

import eu.mikart.animvanish.hooks.Hook;
import eu.mikart.animvanish.user.PaperUser;
import eu.mikart.animvanish.user.OnlineUser;
import me.quantiom.advancedvanish.util.AdvancedVanishAPI;

public class AdvancedVanishHook extends Hook {

	public AdvancedVanishHook() {
		super("AdvancedVanish");
	}

	@Override
	public void vanish(OnlineUser p) {
		AdvancedVanishAPI.INSTANCE.vanishPlayer(((PaperUser) p).getPlayer(), !isVanished(p));
	}

	@Override
	public boolean isVanished(OnlineUser p) {
		return AdvancedVanishAPI.INSTANCE.isPlayerVanished(((PaperUser) p).getPlayer());
	}
}
