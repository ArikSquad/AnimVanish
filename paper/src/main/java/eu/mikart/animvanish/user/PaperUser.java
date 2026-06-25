package eu.mikart.animvanish.user;

import eu.mikart.animvanish.IAnimVanish;
import net.kyori.adventure.key.Key;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public final class PaperUser extends OnlineUser {

	private final Player player;

	private PaperUser(@NotNull Player player, @NotNull IAnimVanish plugin) {
		super(player.getUniqueId(), player.getName(), plugin);
		this.player = player;
	}

	@NotNull
	public static PaperUser adapt(@NotNull Player player, @NotNull IAnimVanish plugin) {
		return new PaperUser(player, plugin);
	}


	@Override
	public void addPotionEffect(@NotNull Key potionEffect, int duration, int amplifier) {
		final PotionEffectType potionEffectType = PotionEffectType.getByName(potionEffect.asString());
		if (potionEffectType == null) {
			throw new IllegalArgumentException("Invalid potion effect type: " + potionEffect.asString());
		}

		final PotionEffect effect = new PotionEffect(potionEffectType, duration, amplifier);
	}

	@Override
	public boolean isSneaking() {
		return player.isSneaking();
	}

	@Override
	public void giveExperiencePoints(int quantity) {
		player.giveExp(quantity);
	}

	@Override
	public void giveExperienceLevels(int quantity) {
		player.giveExpLevels(quantity);
	}

	@Override
	public void giveItem(@NotNull Key material, int quantity) {
		final Material materialType = Material.matchMaterial(material.asString());
		if (materialType == null) {
			throw new IllegalArgumentException("Invalid material type: " + material.asString());
		}

		// Give the player the item(s); drop excess on the ground
		final ItemStack stack = new ItemStack(materialType, quantity);
		if (!player.getInventory().addItem(stack).isEmpty()) {
			player.getWorld().dropItem(player.getLocation(), stack);
		}
	}

	@Override
	public boolean hasPermission(@NotNull String permission) {
		return player.hasPermission(permission);
	}

	@NotNull
	public Player getPlayer() {
		return player;
	}

}