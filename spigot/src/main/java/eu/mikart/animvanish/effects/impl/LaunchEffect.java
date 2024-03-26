package eu.mikart.animvanish.effects.impl;

import eu.mikart.animvanish.IAnimVanish;
import eu.mikart.animvanish.annotations.EffectAnnotation;
import eu.mikart.animvanish.effects.InternalEffect;
import eu.mikart.animvanish.user.BukkitUser;
import eu.mikart.animvanish.user.OnlineUser;
import net.kyori.adventure.audience.Audience;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

@EffectAnnotation(name = "launch", description = "Launches you in the air!", item = "PISTON")
public class LaunchEffect extends InternalEffect {

	public LaunchEffect(IAnimVanish plugin) {
		super(plugin);
	}

	@Override
	public void start(OnlineUser p) {
		Player player = ((BukkitUser) p).getPlayer();
		Audience audience = plugin.getAudience(player.getUniqueId());
		// Check if there is only air above the player
		for (int i = 0; i < 100; i++) {
			Location location = player.getLocation().add(0, i, 0);
			if (!location.getBlock().isPassable()) {
				plugin.getLocales().getLocale("launch_no_spacer")
						.ifPresent(audience::sendMessage);
				return;
			}
		}

		// Vanish early if not vanished already
		boolean vanished = plugin.getCurrentHook().isVanished(BukkitUser.adapt(player, plugin));
		if (!vanished) {
			plugin.getCurrentHook().vanish(p);
		}

		// Create and configure ArmorStand
		ArmorStand armorStand = player.getWorld().spawn(player.getLocation().add(0, 1, 0).setDirection(player.getLocation().getDirection()), ArmorStand.class);
		armorStand.setInvulnerable(true);
		armorStand.setArms(true);
		armorStand.setCustomNameVisible(true);
		armorStand.setCustomNameVisible(true);
		armorStand.setCustomName(player.getName());

		// Copy player's armor to the ArmorStand if configured
		if (plugin.getSettings().getEffects().getLaunch().isUsePlayerArmor()) {
			EntityEquipment equipment = armorStand.getEquipment();
			equipment.setHelmet(player.getInventory().getHelmet());
			equipment.setChestplate(player.getInventory().getChestplate());
			equipment.setLeggings(player.getInventory().getLeggings());
			equipment.setBoots(player.getInventory().getBoots());
			equipment.setItemInMainHand(player.getInventory().getItemInMainHand());
			equipment.setItemInOffHand(player.getInventory().getItemInOffHand());
		}

		// Add the player as a passenger
		armorStand.addPassenger(player);

		Vector launchDirection = new Vector(0, 1.5, 0);
		armorStand.setVelocity(launchDirection);

		Bukkit.getScheduler().runTaskLater((Plugin) plugin, () -> {
			if (vanished) {
				plugin.getCurrentHook().vanish(p);
			}
			armorStand.remove();
		}, 20 * 2);
	}
}
