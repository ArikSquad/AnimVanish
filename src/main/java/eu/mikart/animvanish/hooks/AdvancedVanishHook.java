package eu.mikart.animvanish.hooks;

import me.quantiom.advancedvanish.util.AdvancedVanishAPI;
import org.bukkit.entity.Player;

public class AdvancedVanishHook extends Hook {

	public AdvancedVanishHook() {
		super("AdvancedVanish");
	}

	@Override
	public void vanish(Player p) {
		AdvancedVanishAPI.INSTANCE.vanishPlayer(p, !isVanished(p));
	}

	@Override
	public boolean isVanished(Player p) {
		return AdvancedVanishAPI.INSTANCE.isPlayerVanished(p);
	}
}
