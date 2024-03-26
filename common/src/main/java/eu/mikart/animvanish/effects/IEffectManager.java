package eu.mikart.animvanish.effects;

import java.util.ArrayList;

public interface IEffectManager {
	BareEffect getEffect(String effectName);

	ArrayList<BareEffect> getEffects();

	void registerEffect(BareEffect effect);

	void unregisterEffect(BareEffect effect);
}
