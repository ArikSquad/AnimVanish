package eu.mikart.animvanish.commands;

import de.myzelyam.api.vanish.VanishAPI;
import eu.mikart.animvanish.Main;
import eu.mikart.animvanish.config.MessageManager;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class InvisCommand implements TabExecutor {

	MessageManager messages = Main.instance.messages;

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + messages.getMessage("not_player"));
			return true;
		}


		Player player = (Player) sender;
		long time_before = player.getWorld().getTime();


		if (player.hasPermission("animvanish.invis")) {

			// SuperVanish and PremiumVanish supported effects
			if (Bukkit.getPluginManager().isPluginEnabled("SuperVanish") || Bukkit.getPluginManager().isPluginEnabled("PremiumVanish")) {
				// Vanish
				boolean vanishing = true;
				if (VanishAPI.isInvisible(player)) {
					VanishAPI.showPlayer(player);
					vanishing = false;
				} else {
					VanishAPI.hidePlayer(player);
				}

				if (args.length > 0) {

					// Herobrine effect
					if (args[0].equalsIgnoreCase("herobrine")) {
						player.getWorld().strikeLightningEffect(player.getLocation());
						if (Main.instance.getConfig().getBoolean("herobrine.night")) {
							player.getWorld().setTime(14000);

							Bukkit.getScheduler().runTaskLater(Main.instance, () -> player.getWorld().setTime(time_before), 20 * 3);
						}
					}


					// Particle effect
					else if (args[0].equalsIgnoreCase("particle") && args.length == 1) {
						try {
							player.spawnParticle(Particle.valueOf(Main.instance.getConfig().getString("particle.type")), player.getEyeLocation().add(0, 2, 0), 50);
						} catch (Exception e) {
							player.sendMessage(Main.instance.getPrefix() + ChatColor.RED + messages.getMessage("invis.invalid_config"));
							Main.instance.getLogger().severe(ChatColor.RED + messages.getMessage("invis.invalid_config"));
							player.spawnParticle(Particle.DRAGON_BREATH, player.getEyeLocation().add(0, 2, 0), 50);
						}
					} else if (args[0].equalsIgnoreCase("particle") && args.length == 2) {
						try {
							player.spawnParticle(Particle.valueOf(args[1].toUpperCase()), player.getEyeLocation().add(0, 2, 0), 50);
						} catch (Exception e) {
							player.sendMessage(Main.instance.getPrefix() + ChatColor.RED + messages.getMessage("invis.invalid_particle"));
							player.spawnParticle(Particle.DRAGON_BREATH, player.getEyeLocation().add(0, 2, 0), 50);
						}
					}


					// Tnt effect
					else if (args[0].equalsIgnoreCase("tnt")) {
						if (vanishing) {
							player.getWorld().spawn(player.getLocation(), TNTPrimed.class, (tnt) -> tnt.setYield(0));
						}
					}


					// NPC effect
					else if (args[0].equalsIgnoreCase("npc")) {
						if (Bukkit.getPluginManager().isPluginEnabled("Citizens")) {
							if (vanishing) {
								NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, player.getDisplayName());
								npc.spawn(player.getLocation());

								Bukkit.getScheduler().runTaskLater(Main.instance, () -> npc.destroy(), 20 * Main.instance.getConfig().getLong("npc.despawn_after"));
							}
						} else {
							player.sendMessage(Main.instance.getPrefix() + ChatColor.RED + messages.getMessage("dependency.no_citizens"));
							return true;
						}
					}

					// Zombie effect
					else if (args[0].equalsIgnoreCase("zombie")) {
						if (vanishing) {
							Zombie zombie = (Zombie) player.getWorld().spawnEntity(player.getLocation(), EntityType.ZOMBIE);
							zombie.setAI(false);
							Bukkit.getScheduler().runTaskLater(Main.instance, () -> {
								player.spawnParticle(Particle.HEART, zombie.getLocation(), 3);
								zombie.remove();
							}, 20 * Main.instance.getConfig().getLong("zombie.despawn_after"));
						}
					}

					// Blindness effect
					else if (args[0].equalsIgnoreCase("blindness")) {
						for (Entity ps : player.getNearbyEntities(10, 10, 10)) {
							if (ps instanceof Player) {
								Player p = (Player) ps;
								p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * Main.instance.getConfig().getInt("blindness.effect_last"), 1));
								p.sendMessage(Main.instance.getPrefix() + ChatColor.RED + messages.getMessage("invis.blinded"));
							}
						}
					}


					else {
						player.sendMessage(Main.instance.getPrefix() + ChatColor.RED + messages.getMessage("invalid_args"));
					}

					// Default effect (Herobrine)
				} else {
					player.getWorld().strikeLightningEffect(player.getLocation());
					if (Main.instance.getConfig().getBoolean("herobrine.night")) {
						player.getWorld().setTime(14000);

						Bukkit.getScheduler().runTaskLater(Main.instance, () -> player.getWorld().setTime(time_before), 20 * 3);
					}
				}
			} else {
				player.sendMessage(Main.instance.getPrefix() + ChatColor.RED + messages.getMessage("dependency.no_vanish"));
			}
		} else {
			player.sendMessage(Main.instance.getPrefix() + ChatColor.RED + messages.getMessage("no_permission") + ChatColor.GREEN + " (animvanish.invis)");
		}

		return true;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if (args.length == 1) {
			List<String> arguments = new ArrayList<>();
			arguments.add("herobrine");
			arguments.add("particle");
			arguments.add("tnt");
			arguments.add("npc");
			arguments.add("zombie");
			arguments.add("blindness");

			return arguments;
		} else if (args.length == 2 && args[0].equalsIgnoreCase("particle")) {
			List<String> arguments = new ArrayList<>();
			for (Particle particle : EnumSet.allOf(Particle.class)) {
				arguments.add(particle.name());
			}

			return arguments;
		}

		return null;
	}
}
