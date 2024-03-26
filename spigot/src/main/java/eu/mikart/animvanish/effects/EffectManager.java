package eu.mikart.animvanish.effects;

import eu.mikart.animvanish.IAnimVanish;
import eu.mikart.animvanish.effects.impl.*;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

@Getter
public class EffectManager implements IEffectManager {

	@Getter
	public final ArrayList<BareEffect> effects = new ArrayList<>();
	private final IAnimVanish plugin;

	public EffectManager(@NotNull IAnimVanish plugin) {
		this.plugin = plugin;

		// Lightning
		effects.add(new LightningEffect(plugin));

		// Particle
		effects.add(new ParticleEffect(plugin));

		// Npc
		effects.add(new NpcEffect(plugin));

		// Blood
		effects.add(new BloodEffect(plugin));

		// Blindness
		effects.add(new BlindnessEffect(plugin));

		// Turn
		effects.add(new TurnEffect(plugin));

		// Firework
		effects.add(new FireworkEffect(plugin));

		// Sound
		effects.add(new SoundEffect(plugin));

		// Launch
		effects.add(new LaunchEffect(plugin));
	}

	public BareEffect getEffect(String name) {
		for (BareEffect effect : effects) {
			if (effect.getName().equalsIgnoreCase(name)) {
				return effect;
			}
		}
		return null;
	}

	/**
	 * Internal use (shouldn't even work outside)
	 * @param effect InternalEffect
	 */
	public void registerEffect(InternalEffect effect) {
		effects.add(effect);
	}

	/**
	 * Internal use (shouldn't even work outside)
	 * @param effect InternalEffect
	 */
	public void unregisterEffect(InternalEffect effect) {
		effects.remove(effect);
	}

	public void registerEffect(BareEffect effect) {
		effects.add(effect);
	}

	public void unregisterEffect(BareEffect effect) {
		effects.remove(effect);
	}


}
