package eu.mikart.animvanish.effects;

import eu.mikart.animvanish.effects.impl.*;

import java.util.ArrayList;

public class EffectManager {

	public ArrayList<Effect> effects = new ArrayList<>();

	public EffectManager () {
		// Herobrine
		effects.add(new HerobrineEffect());

		// Particle
		effects.add(new ParticleEffect());

		// Tnt
		effects.add(new TntEffect());

		// Npc
		effects.add(new NpcEffect());

		// Zombie
		effects.add(new ZombieEffect());

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
	}


	/**
	 * Returns the effect with the given name
	 * @param name The name of the effect
	 * @return Effect
	 */
	public Effect getEffect(String name) {
		return effects.stream().filter(effect -> effect.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
	}

	/**
	 * Returns all the effects
	 * @return ArrayList<Effect>
	 */
	public ArrayList<Effect> getEffects() {
		return effects;
	}

}
