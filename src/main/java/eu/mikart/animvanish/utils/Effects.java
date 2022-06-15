package eu.mikart.animvanish.utils;

import com.google.common.collect.ImmutableSet;
import de.slikey.effectlib.effect.BleedEffect;
import de.slikey.effectlib.effect.DnaEffect;
import eu.mikart.animvanish.Main;
import eu.mikart.animvanish.config.MessageManager;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.Set;

public class Effects {

	static MessageManager messages = Main.instance.messages;
	private static final Set<Material> transparent = ImmutableSet.of(Material.AIR, Material.CAVE_AIR, Material.TALL_GRASS);

	/**
	 * Herobrine effect, which will strike a lightning on the player, and set the time to night if config says so.
	 */
	public static void herobrine(Player player, Long time) {
		player.getWorld().strikeLightningEffect(player.getLocation());
		if (Main.instance.getConfig().getBoolean("effects.herobrine.night")) {
			player.getWorld().setTime(14000);

			Bukkit.getScheduler().runTaskLater(Main.instance, () -> player.getWorld().setTime(time), 20 * Main.instance.getConfig().getLong("effects.herobrine.time"));
		}
	}

	/**
	 * Particle effect from config, which will send particles.
	 */
	public static void particleFromConfig(Player player) {
		try {
			player.getWorld().spawnParticle(Particle.valueOf(Main.instance.getConfig().getString("effects.particle.type")),
					player.getEyeLocation().add(0, 2, 0), Main.instance.getConfig().getInt("effects.particle.amount"));
		} catch (Exception e) {
			player.sendMessage(messages.getMessage("invis.particle.invalid_config"));
			Bukkit.getConsoleSender().sendMessage(messages.getMessage("invis.particle.invalid_config"));
			player.getWorld().spawnParticle(Particle.DRAGON_BREATH, player.getEyeLocation().add(0, 2, 0),
					Main.instance.getConfig().getInt("effects.particle.amount"));
		}
	}

	public static void particle(Player player, String effect) {
		try {
			player.getWorld().spawnParticle(Particle.valueOf(effect), player.getEyeLocation().add(0, 2, 0), 50);
		} catch (Exception e) {
			player.sendMessage(messages.getMessage("invis.invalid_particle"));
			player.getWorld().spawnParticle(Particle.DRAGON_BREATH, player.getEyeLocation().add(0, 2, 0), 50);
		}
	}

	public static void tnt(Player player) {
		player.getWorld().spawn(player.getLocation(), TNTPrimed.class, (tnt) -> tnt.setYield(0));
	}

	public static void npc(Player player) {
		NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, player.getName());
		npc.spawn(player.getLocation());

		Bukkit.getScheduler().runTaskLater(Main.instance, () -> npc.destroy(), 20 * Main.instance.getConfig().getLong("effects.npc.despawn_after"));
	}

	public static void zombie(Player player) {
		Zombie zombie = (Zombie) player.getWorld().spawnEntity(player.getLocation(), EntityType.ZOMBIE);
		zombie.setAI(false);
		zombie.setAdult();
		zombie.setInvulnerable(true);
		Bukkit.getScheduler().runTaskLater(Main.instance, () -> {
			player.spawnParticle(Particle.HEART, zombie.getLocation(), 3);
			zombie.remove();
		}, 20 * Main.instance.getConfig().getLong("effects.zombie.despawn_after"));
	}

	public static void blindness(Player player) {
		for (Entity ps : player.getNearbyEntities(10, 10, 10)) {
			if (ps instanceof Player p) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * Main.instance.getConfig().getInt("effects.blindness.effect_last"), 1));
				p.sendMessage(messages.getMessage("invis.blindness.message"));
			}
		}
	}

	public static void soundFromConfig(Player player) {
		try {
			player.getWorld().playSound(player.getLocation(), Sound.valueOf(Main.instance.getConfig().getString("effects.sound.type")), 1, 1);
		} catch (Exception e) {
			player.getWorld().playSound(player.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_HIT, 1, 1);
			player.sendMessage(messages.getMessage("invis.sound.invalid_config"));
		}
	}

	public static void sound(Player player, String sound) {
		try {
			player.getWorld().playSound(player.getLocation(), Sound.valueOf(sound), 1, 1);
		} catch (Exception e) {
			player.getWorld().playSound(player.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_HIT, 1, 1);
			player.sendMessage(messages.getMessage("invis.sound.invalid_sound"));
		}
	}

	public static void turn(Player player) {
		for (Entity ps : player.getNearbyEntities(10, 10, 10)) {
			if (ps instanceof Player p) {
				p.teleport(new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(),
						p.getLocation().getYaw() + 180, p.getLocation().getPitch()));
			}
		}
	}

	public static void firework(Player player) {
		Firework fw = (Firework) player.getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK);
		FireworkMeta fwm = fw.getFireworkMeta();
		fwm.setPower(1);
		fwm.addEffect(FireworkEffect.builder().with(FireworkEffect.Type.BURST).withColor(Color.RED).build());
		fwm.addEffect(FireworkEffect.builder().with(FireworkEffect.Type.BURST).withColor(Color.GREEN).build());
		fwm.addEffect(FireworkEffect.builder().with(FireworkEffect.Type.BURST).withColor(Color.BLUE).build());
		fw.setVelocity(new Vector(0, 2, 0));
		fw.setMetadata("nodamage", new FixedMetadataValue(Main.instance, true));
		fw.setFireworkMeta(fwm);

		Bukkit.getScheduler().runTaskLater(Main.instance, fw::detonate, 20 * Main.instance.getConfig().getLong("effects.firework.despawn_after"));
	}

	public static void blood(Player player) {
		BleedEffect effect = new BleedEffect(Main.instance.effectManager);
		effect.setLocation(player.getLocation());
		effect.iterations = 10;
		effect.start();
	}

	//
}
