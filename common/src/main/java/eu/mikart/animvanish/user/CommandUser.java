package eu.mikart.animvanish.user;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

/**
 * The user system has been inspired by <a href="https://github.com/William278/HuskTowns">HuskTowns</a>
 * which is licensed under the Apache License 2.0.
 */
public interface CommandUser {

	@NotNull
	Audience getAudience();

	boolean hasPermission(@NotNull String permission);

	default void sendMessage(@NotNull Component component) {
		getAudience().sendMessage(component);
	}

}
