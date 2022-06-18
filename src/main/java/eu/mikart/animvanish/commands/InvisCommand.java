package eu.mikart.animvanish.commands;

import de.myzelyam.api.vanish.VanishAPI;
import eu.mikart.animvanish.Main;
import eu.mikart.animvanish.config.MessageManager;
import eu.mikart.animvanish.utils.Effects;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class InvisCommand implements TabExecutor {

	MessageManager messages = Main.instance.messages;

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if (!(sender instanceof Player player)) {
			sender.sendMessage(messages.getMessage("not_player"));
			return true;
		}

		if (args.length == 2) {
			if (player.hasPermission("animvanish.invis.other")) {
				Player p = Bukkit.getPlayer(args[1]);
				if (p != null) {
					player = p;
				} else {
					sender.sendMessage(messages.getMessage("player_not_found"));
					return true;
				}
			}
		}

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
						player.sendMessage(messages.getMessage("invis.herobrine.no_permission"));
						return true;
					}
				}


				// Particle effect using config values
				else if (args[0].equalsIgnoreCase("particle")) {
					if (player.hasPermission("animvanish.invis.particle")) {
						Effects.particle(player);
					} else {
						player.sendMessage(messages.getMessage("invis.particle.no_permission"));
						return true;
					}
				}


				// TNT effect, spawns a tnt that does no damage
				else if (args[0].equalsIgnoreCase("tnt")) {
					if (player.hasPermission("animvanish.invis.tnt")) {
						// noinspection ConstantConditions
						if (vanishing) {
							Effects.tnt(player);
						} else {
							player.sendMessage(messages.getMessage("invis.only_to_vanish"));
						}
					} else {
						player.sendMessage(messages.getMessage("invis.tnt.no_permission"));
						return true;
					}
				}


				// NPC effect, spawns a NPC using Citizens
				else if (args[0].equalsIgnoreCase("npc")) {
					if (player.hasPermission("animvanish.invis.npc")) {
						if (Bukkit.getPluginManager().isPluginEnabled("Citizens")) {
							// noinspection ConstantConditions
							if (vanishing) {
								Effects.npc(player);
							} else {
								player.sendMessage(messages.getMessage("invis.only_to_vanish"));
							}
						} else {
							player.sendMessage(messages.getMessage("dependency.no_citizens"));
							return true;
						}
					} else {
						player.sendMessage(messages.getMessage("invis.npc.no_permission"));
						return true;
					}
				}

				// Zombie effect spawns a NO-AI zombie
				else if (args[0].equalsIgnoreCase("zombie")) {
					if (player.hasPermission("animvanish.invis.zombie")) {
						// noinspection ConstantConditions
						if (vanishing) {
							Effects.zombie(player);
						} else {
							player.sendMessage(messages.getMessage("invis.only_to_vanish"));
						}
					} else {
						player.sendMessage(messages.getMessage("invis.zombie.no_permission"));
						return true;
					}
				}

				// Blindness effect for the surrounding players
				else if (args[0].equalsIgnoreCase("blindness")) {
					if (player.hasPermission("animvanish.invis.blindness")) {
						Effects.blindness(player);
					} else {
						player.sendMessage(messages.getMessage("invis.blindness.no_permission"));
						return true;
					}
				}

				// Sound effect, plays a sound from config
				else if (args[0].equalsIgnoreCase("sound")) {
					if (player.hasPermission("animvanish.invis.sound")) {
						Effects.sound(player);
					} else {
						player.sendMessage(messages.getMessage("invis.sound.no_permission"));
						return true;
					}
				}

				// Turn effect, turns player
				else if (args[0].equalsIgnoreCase("turn")) {
					if (player.hasPermission("animvanish.invis.turn")) {
						Effects.turn(player);
					} else {
						player.sendMessage(messages.getMessage("invis.turn.no_permission"));
						return true;
					}
				}
				// Firework effect, spawns a firework
				else if (args[0].equalsIgnoreCase("firework")) {
					if (player.hasPermission("animvanish.invis.firework")) {
						Effects.firework(player);
					} else {
						player.sendMessage(messages.getMessage("invis.firework.no_permission"));
						return true;
					}
				}

				// Blood effect, spawns red particles
				else if (args[0].equalsIgnoreCase("blood")) {
					if (player.hasPermission("animvanish.invis.blood")) {
						Effects.blood(player);
					} else {
						player.sendMessage(messages.getMessage("invis.blood.no_permission"));
						return true;
					}

				} else {
					player.sendMessage(messages.getMessage("invalid_args"));
					return true;
				}


			// Default effect (Herobrine)
			} else {
				if (player.hasPermission("animvanish.invis.herobrine")) {
					Effects.herobrine(player, player.getWorld().getTime());
				} else {
					player.sendMessage(messages.getMessage("invis.herobrine.no_permission"));
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
			player.sendMessage(messages.getMessage("dependency.no_vanish"));
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
			if (sender.hasPermission("animvanish.invis.blood")) {
				arguments.add("blood");
			}

			return arguments;
		}

		return null;
	}
}
