package eu.mikart.animvanish.gui;

import de.myzelyam.api.vanish.VanishAPI;
import eu.mikart.animvanish.Main;
import eu.mikart.animvanish.effects.Effect;
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

	private static Inventory gui;

	/**
	 * Opens effect choosing GUI for a player.
	 */
	public static void openGUI(Player player) {
		gui = Bukkit.createInventory(null, 54, Main.getInstance().messages.getMessage("gui.title", "player", player.getName()));

		ItemStack border_item = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1);
		ItemMeta border_meta = border_item.getItemMeta();
		border_meta.displayName(Main.getInstance().messages.getMessage("gui.placeholder_name", "player", player.getName()));
		border_item.setItemMeta(border_meta);

		// Fill the inventory with border items, so it's cleaner.
		int[] placeholders = {0,1,2,3,4,5,6,7,8,9,18,27,36,45,17,26,35,44,45,46,47,48,49,50,51,52,53};
		for(int i : placeholders) {
			gui.setItem(i, border_item);
		}

		for(Effect effect : Main.getInstance().getEffectManager().getEffects()) {
			ItemStack item = effect.getItem();
			ItemMeta meta = item.getItemMeta();
			meta.displayName(Main.getInstance().messages.getMessage("gui.item_name", "item", effect.getName()));
			List<Component> lore = new ArrayList<>();
			lore.add(Main.getInstance().messages.getMessage("gui.item_lore", "lore", effect.getDescription()));
			meta.lore(lore);
			item.setItemMeta(meta);


			gui.addItem(item);
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
		Player p = (Player) e.getWhoClicked();

		if (Bukkit.getPluginManager().isPluginEnabled("SuperVanish") || Bukkit.getPluginManager().isPluginEnabled("PremiumVanish")) {
			// noinspection ConstantConditions
			boolean vanishing = !VanishAPI.isInvisible(p);

			ItemStack item = e.getCurrentItem();
			if (item == null || item.getType() == Material.AIR) {
				return;
			}

			for (Effect effect : Main.getInstance().getEffectManager().getEffects()) {
				if (item.isSimilar(effect.getItem())) {
					e.getInventory().close();
					if (p.hasPermission("animvanish.invis." + effect.getName())) {
						effect.runEffect(p);

						// noinspection ConstantConditions
						if (vanishing) {
							VanishAPI.hidePlayer(p);
						} else {
							VanishAPI.showPlayer(p);
						}

					} else {
						p.sendMessage(Main.getInstance().messages.getMessage("no_permissions", "permission", "animvanish.invis." + effect.getName()));
					}
					break;
				}
			}
		} else {
			p.sendMessage(Main.getInstance().messages.getMessage("dependency.no_vanish"));
		}
	}
}
