package eu.mikart.animvanish.commands;

import eu.mikart.animvanish.IAnimVanish;
import eu.mikart.animvanish.effects.BareEffect;
import eu.mikart.animvanish.user.PaperUser;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Nullable;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Dependency;
import revxrsal.commands.annotation.Optional;
import revxrsal.commands.annotation.SuggestWith;
import revxrsal.commands.bukkit.annotation.CommandPermission;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.window.Window;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InvisibilityCommand {
	@Dependency
	private IAnimVanish plugin;

	@Command("invis")
	@CommandPermission("animvanish.invis")
	public void invis(Player player, @Optional @SuggestWith(EffectSuggestions.class) String effectName, @Optional Player target) {
		if (plugin.getCurrentHook() == null) {
			Audience audience = plugin.getAudience(player.getUniqueId());
			plugin.getLocales().getLocale("dependency_no_vanish")
					.ifPresent(audience::sendMessage);
			return;
		}

		Audience audience = plugin.getAudience(player.getUniqueId());
		BareEffect effect = plugin.getEffectManager().getEffect(effectName);

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
				effect.runEffect(PaperUser.adapt(Objects.requireNonNull(target.getPlayer()), plugin));

			return;
		}

		if (!player.hasPermission("animvanish.invis." + effect.getName())) {
			plugin.getLocales().getLocale("no_permissions", "animvanish.invis." + effect.getName())
					.ifPresent(audience::sendMessage);
		} else
			effect.runEffect(PaperUser.adapt(player, plugin));
	}

	private void openGui(Player player) {
		ItemStack border_item = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1);
		ItemMeta border_meta = border_item.getItemMeta();
		Component border_name = plugin.getLocales().getLocale("gui_placeholder_name").orElse(MiniMessage.miniMessage().deserialize("<gray>Select an effect</gray>"));
		border_meta.setDisplayName(LegacyComponentSerializer.legacySection().serialize(border_name));
		border_item.setItemMeta(border_meta);

		Gui gui = Gui.builder()
				.setStructure(
						"1 1 1 1 1 1 1 1 1",
						"1 . . . . . . . 1",
						"1 . . . . . . . 1",
						"1 . . . . . . . 1",
						"1 . . . . . . . 1",
						"1 1 1 1 1 1 1 1 1"
				)
				.addIngredient('1', border_item)
				.build();

		int slotIndex = 0;
		for (BareEffect effect : plugin.getEffectManager().getEffects()) {
			if (effect.canRun() && player.hasPermission("animvanish.invis." + effect.getName())) {
				int row = 1 + slotIndex / 8;
				int column = 1 + slotIndex % 8;
				gui.setItem(column, row, effectIcon(player, effect));
				slotIndex++;

				if (slotIndex >= 40) {
					break;
				}
			}
		}

		Window.builder()
				.setViewer(player)
				.setTitle(LegacyComponentSerializer.legacySection().serialize(plugin.getLocales().getLocale("gui_title").orElse(MiniMessage.miniMessage().deserialize("<black>Select an effect</black>"))))
				.setUpperGui(gui)
				.build()
				.open();
	}

	private Item effectIcon(Player player, BareEffect effect) {
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

		return Item.builder().setItemProvider(item).addClickHandler(click -> {
			Audience audience = plugin.getAudience(click.player().getUniqueId());
			if (plugin.getCurrentHook() == null) {
				audience.sendMessage(
						plugin.getLocales()
								.getLocale("dependency_no_vanish")
								.orElse(MiniMessage.miniMessage().deserialize("<red>You must have a supported vanish plugin installed to use this command.</red>"))
				);
				click.player().closeInventory();
				return;
			}

			@Nullable String effectName = item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey((Plugin) plugin, "identifier"), PersistentDataType.STRING);
			BareEffect selectedEffect = plugin.getEffectManager().getEffect(effectName);
			if (selectedEffect == null) {
				click.player().closeInventory();
				return;
			}

			// Close the gui
			click.player().closeInventory();
			if (!player.hasPermission("animvanish.invis." + selectedEffect.getName())) {
				plugin.getLocales().getLocale("no_permissions", "animvanish.invis." + selectedEffect.getName())
						.ifPresent(audience::sendMessage);
				return;
			}
			selectedEffect.runEffect(PaperUser.adapt(player, plugin));
		}).build();
	}

}
