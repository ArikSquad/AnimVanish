package eu.mikart.animvanish.hooks;

import eu.mikart.animvanish.user.OnlineUser;
import lombok.Getter;

@Getter
public abstract class Hook implements HookInterface {

	String name;

	public Hook(String name) {
		this.name = name;
	}

	public abstract void vanish(OnlineUser p);
}
