package eu.mikart.animvanish.gui;

import eu.mikart.animvanish.Main;
import eu.mikart.animvanish.effects.Effect;
import eu.mikart.animvanish.util.MessageConfig;
import eu.mikart.animvanish.util.Utilities;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class InvisGUI implements Listener {

	public static final MessageConfig messages = Main.getMessages();
	private static Inventory gui;

	/**
	 * Opens effect choosing GUI for a player.
	 */
	public static void openGUI(Player player) {
		if (!player.hasPermission("animvanish.invis.gui")) return;

		gui = Bukkit.createInventory(null, 54, messages.getMessage("gui.title", "player", player.getName()));

		ItemStack border_item = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1);
		ItemMeta border_meta = border_item.getItemMeta();
		border_meta.displayName(messages.getMessage("gui.placeholder_name", "player", player.getName()));
		border_item.setItemMeta(border_meta);

		// Fill the inventory with border items, so it's cleaner.
		int[] placeholders = {0,1,2,3,4,5,6,7,8,9,18,27,36,45,17,26,35,44,45,46,47,48,49,50,51,52,53};
		for(int i : placeholders) {
			gui.setItem(i, border_item);
		}

		for(Effect effect : Main.getInstance().getEffectManager().getEffects()) {
			if(effect.canRun()) {
				ItemStack item = new ItemStack(effect.getItem());
				ItemMeta meta = item.getItemMeta();
				meta.displayName(messages.getMessage("gui.item_name", "effect_name", effect.getName()));
				List<Component> lore = new ArrayList<>();
				lore.add(messages.getMessage("gui.item_lore", "effect_description", effect.getDescription()));
				meta.lore(lore);
				item.setItemMeta(meta);


				gui.addItem(item);
			}
		}

		player.openInventory(gui);
	}


	/**
	 * When a player clicks an item in the gui, this method is called.
	 */
	@EventHandler
	public void guiClickEvent(InventoryClickEvent e) {
		if(!e.getInventory().equals(gui)) {
			return;
		}

		e.setCancelled(true);
		Player player = (Player) e.getWhoClicked();

		if (!Utilities.isVanish()) {
			player.sendMessage(messages.getMessage("dependency.no_vanish"));
			return;

		}

		ItemStack item = e.getCurrentItem();
		if (item == null || item.getType() == Material.AIR) {
			return;
		}
		for (Effect effect : Main.getInstance().getEffectManager().getEffects()) {
			if (item.getType().equals(effect.getItem())) {
				e.getInventory().close();

				if (!player.hasPermission("animvanish.invis." + effect.getName())) {
					player.sendMessage(messages.getMessage("no_permissions", "permission", "animvanish.invis." + effect.getName()));
					return;
				}

				effect.runEffect(player);
				break;
			}
		}
	}
}
