package eu.mikart.animvanish.hooks;

import lombok.Getter;
import org.bukkit.Bukkit;

import java.util.ArrayList;

public class HookManager {

	@Getter
	private Hook currentHook;

	public HookManager() {
		final ArrayList<Hook> hooks = new ArrayList<>();
		hooks.add(new AdvancedVanishHook());
		hooks.add(new SuperVanishHook());
		hooks.add(new PremiumVanishHook());

		for(Hook hook : hooks) {
			if(Bukkit.getPluginManager().isPluginEnabled(hook.getName())) {
				currentHook = hook;
				break;
			}
		}
	}

}
