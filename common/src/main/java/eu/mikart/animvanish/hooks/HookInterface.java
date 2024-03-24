package eu.mikart.animvanish.hooks;

import eu.mikart.animvanish.user.OnlineUser;

public interface HookInterface {

	void vanish(OnlineUser p);

	boolean isVanished(OnlineUser p);

}
