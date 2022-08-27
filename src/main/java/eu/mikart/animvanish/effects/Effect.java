package eu.mikart.animvanish.effects;

import eu.mikart.animvanish.Main;
import eu.mikart.animvanish.util.MessageConfig;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Effect implements EffectInterface {

	public String name, description;
	public ItemStack item;
	public MessageConfig messages = Main.getInstance().messages;

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

	@Override
	public void runEffect(Player p) {

	}

}
