package eu.mikart.animvanish.effects;

import eu.mikart.animvanish.user.OnlineUser;

public interface IEffect {

	void start(OnlineUser player);

	default boolean canRun() {
		return true;
	}

}
