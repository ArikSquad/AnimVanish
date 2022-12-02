package eu.mikart.animvanish.effects.impl;

import eu.mikart.animvanish.Main;
import eu.mikart.animvanish.annonations.EffectAnnotation;
import eu.mikart.animvanish.effects.Effect;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;

@EffectAnnotation(name = "launch", description = "Launches you in the air!", item = Material.PISTON)
public class LaunchEffect extends Effect {

	@Override
	public void runEffect(Player player) {
		// check if there is only air above player
		// there is probably an easier way to do this, but for now this works
		for (int i = 0; i < 100; i++) {
			if (!player.getWorld().getBlockAt(player.getLocation().add(0, i, 0)).isPassable()) {
				player.sendMessage(messages.getMessage("invis.launch.no_space"));
				return;
			}
		}
		// Vanish early if not vanished already
		boolean vanished = isVanished(player);
		if(!vanished) toggleVanish(player);

		ArmorStand armorStand = player.getWorld().spawn(player.getLocation().add(0, 1, 0), ArmorStand.class);
		armorStand.setInvulnerable(true);
		armorStand.setArms(true);
		armorStand.customName(miniMessage().deserialize(player.getName()));
		armorStand.setCustomNameVisible(true);

		if(Main.getInstance().getConfig().getBoolean("effects.launch.use_player_armor")) {
			armorStand.getEquipment().setHelmet(player.getInventory().getHelmet());
			armorStand.getEquipment().setChestplate(player.getInventory().getChestplate());
			armorStand.getEquipment().setLeggings(player.getInventory().getLeggings());
			armorStand.getEquipment().setBoots(player.getInventory().getBoots());
		}

		// add player to be the passenger
		armorStand.addPassenger(player);

		// launch up to air
		Location location = armorStand.getLocation();
		Vector launchDirection = location.toVector().add(location.toVector().multiply(-1));
		launchDirection.setY(1.5);
		armorStand.setVelocity(launchDirection);

		Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
			if(vanished) {
				toggleVanish(player);
			}
			armorStand.remove();
		}, 20 * 2);
	}


}
