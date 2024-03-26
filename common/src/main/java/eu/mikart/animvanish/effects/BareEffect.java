package eu.mikart.animvanish.effects;

import eu.mikart.animvanish.annotations.EffectAnnotation;
import eu.mikart.animvanish.user.OnlineUser;
import lombok.Getter;

public abstract class BareEffect implements IEffect {

	private final EffectAnnotation effectAnnotation = getClass().getAnnotation(EffectAnnotation.class);
	@Getter
	private final String name = effectAnnotation.name(), description = effectAnnotation.description();
	@Getter
	private final String item = effectAnnotation.item();

	public void runEffect(OnlineUser player) {
		if(this.canRun()) {
			this.start(player);
		}
	}

}
