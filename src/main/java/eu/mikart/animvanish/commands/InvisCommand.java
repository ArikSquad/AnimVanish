package eu.mikart.animvanish.commands;

import de.myzelyam.api.vanish.VanishAPI;
import eu.mikart.animvanish.Main;
import eu.mikart.animvanish.config.MessageManager;
import eu.mikart.animvanish.utils.Effects;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.*;
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
			Main.instance.adventure().sender(sender).sendMessage(messages.getMessage("not_player"));
			return true;
		}


		Player player = (Player) sender;

		// SuperVanish and PremiumVanish supported effects
		if (Bukkit.getPluginManager().isPluginEnabled("SuperVanish") || Bukkit.getPluginManager().isPluginEnabled("PremiumVanish")) {

			// noinspection ConstantConditions
			boolean vanishing = !VanishAPI.isInvisible(player);

			if (args.length > 0) {
				// Herobrine effect, that strikes a lightning effect on the player
				if (args[0].equalsIgnoreCase("herobrine")) {
					if (player.hasPermission("animvanish.invis.herobrine")) {

						Effects.herobrine(player, player.getWorld().getTime());
					} else {
						Main.instance.adventure().player(player).sendMessage(messages.getMessage("invis.herobrine.no_permission"));
						return true;
					}
				}


				// Particle effect using config values or args
				else if (args[0].equalsIgnoreCase("particle")) {
					if (player.hasPermission("animvanish.invis.particle")) {
						if (args.length == 1) {
							Effects.particleFromConfig(player);
						} else if (args.length == 2) {
							Effects.particle(player, args[1].toUpperCase());
						}
					} else {
						Main.instance.adventure().player(player).sendMessage(messages.getMessage("invis.particle.no_permission"));
						return true;
					}
				}


				// TNT effect spawns a tnt, that does no damage
				else if (args[0].equalsIgnoreCase("tnt")) {
					if (player.hasPermission("animvanish.invis.tnt")) {
						// noinspection ConstantConditions
						if (vanishing) {
							Effects.tnt(player);
						} else {
							Main.instance.adventure().player(player).sendMessage(messages.getMessage("invis.only_to_vanish"));
						}
					} else {
						Main.instance.adventure().player(player).sendMessage(messages.getMessage("invis.tnt.no_permission"));
						return true;
					}
				}


				// NPC effect (Citizens)
				else if (args[0].equalsIgnoreCase("npc")) {
					if (player.hasPermission("animvanish.invis.npc")) {
						if (Bukkit.getPluginManager().isPluginEnabled("Citizens")) {
							// noinspection ConstantConditions
							if (vanishing) {
								Effects.npc(player);
							} else {
								Main.instance.adventure().player(player).sendMessage(messages.getMessage("invis.only_to_vanish"));
							}
						} else {
							Main.instance.adventure().player(player).sendMessage(messages.getMessage("dependency.no_citizens"));
							return true;
						}
					} else {
						Main.instance.adventure().player(player).sendMessage(messages.getMessage("invis.npc.no_permission"));
						return true;
					}
				}

				// Zombie effect spawns a NO AI zombie
				else if (args[0].equalsIgnoreCase("zombie")) {
					if (player.hasPermission("animvanish.invis.zombie")) {
						// noinspection ConstantConditions
						if (vanishing) {
							Effects.zombie(player);
						} else {
							Main.instance.adventure().player(player).sendMessage(messages.getMessage("invis.only_to_vanish"));
						}
					} else {
						Main.instance.adventure().player(player).sendMessage(messages.getMessage("invis.zombie.no_permission"));
						return true;
					}
				}

				// Blindness effect for the surrounding players
				else if (args[0].equalsIgnoreCase("blindness")) {
					if (player.hasPermission("animvanish.invis.blindness")) {
						Main.instance.adventure().player(player).sendMessage(messages.getMessage("invis.blindness.author"));
						Effects.blindness(player);
					} else {
						Main.instance.adventure().player(player).sendMessage(messages.getMessage("invis.blindness.no_permission"));
						return true;
					}
				}

				// Sound effect, plays a sound from config or from args
				else if (args[0].equalsIgnoreCase("sound")) {
					if (player.hasPermission("animvanish.invis.sound")) {
						if (args.length == 1) {
							Effects.soundFromConfig(player);
						} else if (args.length == 2) {
							Effects.sound(player, args[1].toUpperCase());
						}
					} else {
						Main.instance.adventure().player(player).sendMessage(messages.getMessage("invis.sound.no_permission"));
						return true;
					}
				}

				// Turn effect, turns player
				else if (args[0].equalsIgnoreCase("turn")) {
					if (player.hasPermission("animvanish.invis.turn")) {
						Effects.turn(player);
					} else {
						Main.instance.adventure().player(player).sendMessage(messages.getMessage("invis.turn.no_permission"));
						return true;
					}
				}
				// Firework effect, spawns a firework
				else if (args[0].equalsIgnoreCase("firework")) {
					if (player.hasPermission("animvanish.invis.firework")) {
						Effects.firework(player);
					} else {
						Main.instance.adventure().player(player).sendMessage(messages.getMessage("invis.firework.no_permission"));
						return true;
					}
				} else {
					Main.instance.adventure().player(player).sendMessage(messages.getMessage("invalid_args"));
					return true;
				}


			// Default effect (Herobrine)
			} else {
				if (player.hasPermission("animvanish.invis.herobrine")) {
					Effects.herobrine(player, player.getWorld().getTime());
				} else {
					Main.instance.adventure().player(player).sendMessage(messages.getMessage("invis.herobrine.no_permission"));
					return true;
				}
			}

			// noinspection ConstantConditions
			if (vanishing) {
				VanishAPI.hidePlayer(player);
			} else {
				VanishAPI.showPlayer(player);
			}

		} else {
			Main.instance.adventure().player(player).sendMessage(messages.getMessage("dependency.no_vanish"));
		}

		return true;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if (args.length == 1) {
			List<String> arguments = new ArrayList<>();
			if (sender.hasPermission("animvanish.invis.herobrine")) {
				arguments.add("herobrine");
			}
			if (sender.hasPermission("animvanish.invis.particle")) {
				arguments.add("particle");
			}
			if (sender.hasPermission("animvanish.invis.tnt")) {
				arguments.add("tnt");
			}
			if (sender.hasPermission("animvanish.invis.npc")) {
				arguments.add("npc");
			}
			if (sender.hasPermission("animvanish.invis.zombie")) {
				arguments.add("zombie");
			}
			if (sender.hasPermission("animvanish.invis.blindness")) {
				arguments.add("blindness");
			}
			if (sender.hasPermission("animvanish.invis.sound")) {
				arguments.add("sound");
			}
			if (sender.hasPermission("animvanish.invis.turn")) {
				arguments.add("turn");
			}
			if (sender.hasPermission("animvanish.invis.firework")) {
				arguments.add("firework");
			}

			return arguments;
		} else if (args.length == 2) {
			// Particle args
			if (args[0].equalsIgnoreCase("particle")) {
				List<String> arguments = new ArrayList<>();
				for (Particle particle : EnumSet.allOf(Particle.class)) {
					arguments.add(particle.name());
				}

				return arguments;

			// Sound args
			} else if (args[0].equalsIgnoreCase("sound")) {
				List<String> arguments = new ArrayList<>();
				for (Sound s : EnumSet.allOf(Sound.class)) {
					arguments.add(s.name());
				}

				return arguments;
			}
		}

		return null;
	}
}
