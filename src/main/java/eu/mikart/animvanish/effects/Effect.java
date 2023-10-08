package eu.mikart.animvanish.effects;

import eu.mikart.animvanish.Main;
import eu.mikart.animvanish.annonations.EffectAnnotation;
import eu.mikart.animvanish.util.Settings;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public abstract class Effect implements EffectInterface {

	private final EffectAnnotation effectAnnotation = getClass().getAnnotation(EffectAnnotation.class);
	@Getter
	private final String name = effectAnnotation.name(), description = effectAnnotation.description();
	@Getter
	private final Material item = effectAnnotation.item();

	public void runEffect(Player p) {
		if(this.canRun()) {
			if(Settings.DEBUG) Main.getInstance().getLogger().info("Running effect " + this.getName() + " for player " + p.getName());
			this.start(p);
		}
	}

	public boolean canRun() {
		return true;
	}

	public void toggleVanish(Player p) {
		Main.getInstance().getHookManager().getCurrentHook().vanish(p);
	}

}
