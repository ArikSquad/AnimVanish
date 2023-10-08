package eu.mikart.animvanish.hooks;

import lombok.Getter;

public abstract class Hook implements HookInterface {

	@Getter
	String name;

	public Hook(String name) {
		this.name = name;
	}

}
