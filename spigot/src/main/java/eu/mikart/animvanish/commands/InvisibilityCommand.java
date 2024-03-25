package eu.mikart.animvanish.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.PatternPane;
import com.github.stefvanschie.inventoryframework.pane.util.Pattern;
import eu.mikart.animvanish.IAnimVanish;
import eu.mikart.animvanish.effects.BareEffect;
import eu.mikart.animvanish.effects.InternalEffect;
import eu.mikart.animvanish.user.BukkitUser;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@CommandAlias("invis")
public class InvisibilityCommand extends BaseCommand {
	private final IAnimVanish plugin;

	public InvisibilityCommand(IAnimVanish plugin) {
		this.plugin = plugin;
	}

	@PreCommand
	public boolean preCommand(CommandSender sender, String[] args) {
		if (!(sender instanceof Player player)) {
			return true;
		}

		if (plugin.getCurrentHook() == null) {
			Audience audience = plugin.getAudience(player.getUniqueId());
			plugin.getLocales().getLocale("dependency_no_vanish")
					.ifPresent(audience::sendMessage);
			return true;
		}

		return false;
	}

	// TODO: add back support for autocompletion to not show effects that you don't have permission to use
	@Default
	@CommandPermission("animvanish.invis")
	@CommandCompletion("@effects @players")
	public void onInvis(Player player, @Optional InternalEffect effect, @Optional OnlinePlayer target) {
		Audience audience = plugin.getAudience(player.getUniqueId());
		if (effect == null) {
			if (player.hasPermission("animvanish.invis.gui"))
				openGui(player);
			else
				plugin.getLocales().getLocale("no_permissions", "animvanish.invis.gui")
						.ifPresent(audience::sendMessage);

			return;
		}

		if (target != null) {
			if (!player.hasPermission("animvanish.invis.other"))
				plugin.getLocales().getLocale("no_permissions", "animvanish.invis.other")
						.ifPresent(audience::sendMessage);
			else
				effect.runEffect(BukkitUser.adapt(target.getPlayer(), plugin));

			return;
		}

		if (!player.hasPermission("animvanish.invis." + effect.getName()))
			plugin.getLocales().getLocale("no_permissions", "animvanish.invis." + effect.getName())
					.ifPresent(audience::sendMessage);
		else
			effect.runEffect(BukkitUser.adapt(player, plugin));
	}

	private void openGui(Player player) {
		ChestGui gui = new ChestGui(6, LegacyComponentSerializer.legacySection().serialize(plugin.getLocales().getLocale("gui_title").orElse(MiniMessage.miniMessage().deserialize("<black>Select an effect</black>"))));
		gui.setOnGlobalClick(event -> event.setCancelled(true));

		ItemStack border_item = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1);
		ItemMeta border_meta = border_item.getItemMeta();
		Component border_name = plugin.getLocales().getLocale("gui_placeholder_name").orElse(MiniMessage.miniMessage().deserialize("<gray>Select an effect</gray>"));
		border_meta.setDisplayName(LegacyComponentSerializer.legacySection().serialize(border_name));
		border_item.setItemMeta(border_meta);

		Pattern pattern = new Pattern(
				"111111111",
				"100000001",
				"100000001",
				"100000001",
				"100000001",
				"111111111"
		);
		PatternPane background = new PatternPane(0, 0, 9, 6, pattern);
		background.bindItem('1', new GuiItem(border_item));
		gui.addPane(background);

		OutlinePane effectPane = new OutlinePane(1, 1, 8, 5);
		for (BareEffect effect : plugin.getEffectManager().getEffects()) {
			if (effect.canRun() && player.hasPermission("animvanish.invis." + effect.getName())) {
				effectPane.addItem(effectIcon(player, effect));
			}
		}
		gui.addPane(effectPane);
		gui.show(player);
	}

	private GuiItem effectIcon(Player player, BareEffect effect) {
		ItemStack item = new ItemStack(Material.valueOf(effect.getItem()));
		ItemMeta meta = item.getItemMeta();
		meta.getPersistentDataContainer().set(new NamespacedKey((Plugin) plugin, "identifier"), PersistentDataType.STRING, effect.getName());
		Component itemName = plugin.getLocales().getLocale("gui_item_name", effect.getName()).orElse(MiniMessage.miniMessage().deserialize("<green>" + effect.getName() + "</green>"));
		meta.setDisplayName(LegacyComponentSerializer.legacySection().serialize(itemName));
		List<String> lore = new ArrayList<>();
		Component loreName = plugin.getLocales().getLocale("gui_item_lore", effect.getDescription()).orElse(MiniMessage.miniMessage().deserialize("<blue>" + effect.getDescription() + "</blue>"));
		lore.add(LegacyComponentSerializer.legacySection().serialize(loreName));
		meta.setLore(lore);
		item.setItemMeta(meta);

		return new GuiItem(item, event -> {
			Audience audience = plugin.getAudience(event.getWhoClicked().getUniqueId());
			if (plugin.getCurrentHook() == null) {
				audience.sendMessage(
						plugin.getLocales()
								.getLocale("dependency_no_vanish")
								.orElse(MiniMessage.miniMessage().deserialize("<red>You must have a supported vanish plugin installed to use this command.</red>"))
				);
				event.getWhoClicked().closeInventory();
				return;
			}

			ItemStack i = event.getCurrentItem();
			if (i == null || i.getType() == Material.AIR) {
				return;
			}

			@Nullable String effectName = i.getItemMeta().getPersistentDataContainer().get(new NamespacedKey((Plugin) plugin, "identifier"), PersistentDataType.STRING);
			BareEffect selectedEffect = plugin.getEffectManager().getEffect(effectName);

			// Close the gui
			event.getWhoClicked().closeInventory();
			if (!player.hasPermission("animvanish.invis." + selectedEffect.getName())) {
				plugin.getLocales().getLocale("no_permissions", "animvanish.invis." + selectedEffect.getName())
						.ifPresent(audience::sendMessage);
				return;
			}
			selectedEffect.runEffect(BukkitUser.adapt(player, plugin));
		});
	}

}
