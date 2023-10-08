package eu.mikart.animvanish.hooks;

import de.myzelyam.api.vanish.VanishAPI;
import org.bukkit.entity.Player;

public class PremiumVanishHook extends Hook {

	public PremiumVanishHook() {
		super("PremiumVanish");
	}

	@Override
	public void vanish(Player p) {
		if (isVanished(p)) {
			VanishAPI.showPlayer(p);
		} else {
			VanishAPI.hidePlayer(p);
		}
	}

	@Override
	public boolean isVanished(Player p) {
		return VanishAPI.isInvisible(p);
	}
}
