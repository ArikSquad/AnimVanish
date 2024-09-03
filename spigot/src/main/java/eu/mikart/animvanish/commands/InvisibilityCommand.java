package eu.mikart.animvanish.commands;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.PatternPane;
import com.github.stefvanschie.inventoryframework.pane.util.Pattern;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.IStringTooltip;
import dev.jorel.commandapi.StringTooltip;
import dev.jorel.commandapi.arguments.Argument;
import dev.jorel.commandapi.arguments.ArgumentSuggestions;
import dev.jorel.commandapi.arguments.PlayerArgument;
import dev.jorel.commandapi.arguments.StringArgument;
import eu.mikart.animvanish.IAnimVanish;
import eu.mikart.animvanish.effects.BareEffect;
import eu.mikart.animvanish.user.BukkitUser;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InvisibilityCommand {
	private final IAnimVanish plugin;

	public InvisibilityCommand(IAnimVanish plugin) {
		this.plugin = plugin;

		List<Argument<?>> arguments = new ArrayList<>();
		arguments.add(new StringArgument("effect")
				.replaceSuggestions(ArgumentSuggestions.stringsWithTooltips(info -> plugin.getEffectManager().getEffects().stream()
						.filter(effect -> effect.canRun() && info.sender().hasPermission("animvanish.invis." + effect.getName()))
						.map(effect -> StringTooltip.ofString(effect.getName(), effect.getDescription()))
						.toArray(IStringTooltip[]::new))
				).setOptional(true));
		arguments.add(new PlayerArgument("target").setOptional(true));

		new CommandAPICommand("invis")
				.withPermission("animvanish.invis")
				.withArguments(arguments)
				.executesPlayer((player, args) -> {
					if (plugin.getCurrentHook() == null) {
						Audience audience = plugin.getAudience(player.getUniqueId());
						plugin.getLocales().getLocale("dependency_no_vanish")
								.ifPresent(audience::sendMessage);
						return;
					}

					Audience audience = plugin.getAudience(player.getUniqueId());
					BareEffect effect = plugin.getEffectManager().getEffect((String) args.get("effect"));
					Player target = (Player) args.get("target");

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
							effect.runEffect(BukkitUser.adapt(Objects.requireNonNull(target.getPlayer()), plugin));

						return;
					}

					if (!player.hasPermission("animvanish.invis." + effect.getName())) {
						plugin.getLocales().getLocale("no_permissions", "animvanish.invis." + effect.getName())
								.ifPresent(audience::sendMessage);
					} else
						effect.runEffect(BukkitUser.adapt(player, plugin));
				}).register();
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
