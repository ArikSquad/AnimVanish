package eu.mikart.animvanish.user;

import eu.mikart.animvanish.IAnimVanish;
import eu.mikart.animvanish.config.Locales;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.intellij.lang.annotations.Subst;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * The user system has been inspired by <a href="https://github.com/William278/HuskTowns">HuskTowns</a>
 * which is licensed under the Apache License 2.0.
 */
public abstract class OnlineUser extends User implements CommandUser {

	@NotNull
	protected final IAnimVanish plugin;

	protected OnlineUser(@NotNull UUID uuid, @NotNull String username, @NotNull IAnimVanish plugin) {
		super(uuid, username);
		this.plugin = plugin;
	}

	public final void sendActionBar(@NotNull Component component) {
		getAudience().sendActionBar(component);
	}

	public final void sendTitle(@NotNull Component title, @NotNull Component subtitle) {
		getAudience().showTitle(Title.title(title, subtitle));
	}

	public final void sendMessage(@NotNull Locales.Slot slot, @NotNull Component message) {
		switch (slot) {
			case CHAT -> this.sendMessage(message);
			case ACTION_BAR -> this.sendActionBar(message);
			case TITLE -> this.sendTitle(message, Component.empty());
			case SUBTITLE -> this.sendTitle(Component.empty(), message);
		}
	}

	public final void playSound(@Subst("minecraft:block.note_block.banjo") @NotNull String sound) {
		getAudience().playSound(Sound.sound(Key.key(sound), Sound.Source.PLAYER, 1.0f, 1.0f));
	}

	public abstract void addPotionEffect(@NotNull Key potionEffect, int duration, int amplifier);

	@NotNull
	public Audience getAudience() {
		return plugin.getAudience(getUuid());
	}

	public abstract boolean isSneaking();

	/*not needed: public abstract void teleportTo(@NotNull Position position);*/

	public abstract void giveExperiencePoints(int quantity);

	public abstract void giveExperienceLevels(int quantity);

	public abstract void giveItem(@NotNull Key material, int quantity);

}