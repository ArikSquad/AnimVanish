package eu.mikart.animvanish.effects;

import eu.mikart.animvanish.Main;
import eu.mikart.animvanish.config.MessageManager;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Effect {

	public String name, description;
	public ItemStack item;
	public MessageManager messages = Main.instance.messages;

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

	public void runEffect(Player p) {

	}

}
