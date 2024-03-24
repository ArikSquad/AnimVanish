package eu.mikart.animvanish.api;

import eu.mikart.animvanish.effects.BareEffect;
import eu.mikart.animvanish.effects.InternalEffect;
import eu.mikart.animvanish.user.BukkitUser;
import eu.mikart.animvanish.user.OnlineUser;
import org.bukkit.entity.Player;

/**
 * Represents an effect that can be run on a player
 * This should be used in the API, and not internally
 *
 * <h3>This class should always be annotated with {@link eu.mikart.animvanish.annotations.EffectAnnotation}</h3>
 *
 * @see InternalEffect
 */
public abstract class Effect extends BareEffect {

	@Override
	public void runEffect(OnlineUser player) {
		if(this.canRun()) {
			this.start(player);
		}
	}

	@Override
	public void start(OnlineUser player) {
		this.start(((BukkitUser) player).getPlayer());
	}

	public abstract void start(Player player);

}
