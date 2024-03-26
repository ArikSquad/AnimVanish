package eu.mikart.animvanish.effects;

import eu.mikart.animvanish.IAnimVanish;
import eu.mikart.animvanish.user.OnlineUser;
import net.kyori.adventure.audience.Audience;

public abstract class InternalEffect extends BareEffect {

	public IAnimVanish plugin;

	public InternalEffect(IAnimVanish plugin) {
		this.plugin = plugin;
	}

	public void runEffect(OnlineUser player) {
		if(this.canRun()) {
			if(plugin.getSettings().isDebug()) plugin.getLogger().info("Running effect " + this.getName() + " for player " + player.getName());
			this.start(player);
		}
	}

	protected Audience getConsole() {
		return plugin.getAudiences().console();
	}

}
