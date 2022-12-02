package eu.mikart.animvanish.effects;

import eu.mikart.animvanish.effects.impl.*;

import java.util.ArrayList;

public class EffectManager {

	public final ArrayList<Effect> effects = new ArrayList<>();

	public EffectManager () {
		// Lightning
		effects.add(new LightningEffect());

		// Particle
		effects.add(new ParticleEffect());

		// Tnt
		effects.add(new TntEffect());

		// Npc
		effects.add(new NpcEffect());

		// Blood
		effects.add(new BloodEffect());

		// Blindness
		effects.add(new BlindnessEffect());

		// Turn
		effects.add(new TurnEffect());

		// Firework
		effects.add(new FireworkEffect());

		// Sound
		effects.add(new SoundEffect());

		// Launch
		effects.add(new LaunchEffect());
	}


	/**
	 * Returns all the effects
	 * @return ArrayList<Effect>
	 */
	public ArrayList<Effect> getEffects() {
		return effects;
	}

}
