package eu.mikart.animvanish.effects;

import eu.mikart.animvanish.Main;
import eu.mikart.animvanish.util.MessageConfig;
import eu.mikart.animvanish.util.Utilities;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class Effect implements EffectInterface {

	public final String name, description;
	public final ItemStack item;
	public final MessageConfig messages = Main.getMessages();

	public Effect(String name, String description, ItemStack item) {
		this.name = name;
		this.description = description;
		this.item = item;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public ItemStack getItem() {
		return item;
	}

	public void toggleVanish(Player p) {
		Utilities.toggleVanish(p);
	}

	public boolean isVanished(Player p) {
		return Utilities.isInvisible(p);
	}

}
