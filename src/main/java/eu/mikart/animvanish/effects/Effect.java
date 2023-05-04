package eu.mikart.animvanish.effects;

import eu.mikart.animvanish.annonations.EffectAnnotation;
import eu.mikart.animvanish.util.Utilities;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public abstract class Effect implements EffectInterface {

	private final EffectAnnotation effectAnnotation = getClass().getAnnotation(EffectAnnotation.class);
	@Getter
	private final String name = effectAnnotation.name(), description = effectAnnotation.description();
	@Getter
	private final Material item = effectAnnotation.item();

	public boolean canRun() {
		return true;
	}

	public void toggleVanish(Player p) {
		Utilities.toggleVanish(p);
	}

	/**
	 * Check if a player is vanished
	 * @param p Player
	 * @return Player's vanish status
	 */
	public boolean isVanished(Player p) {
		return Utilities.isInvisible(p);
	}

}
