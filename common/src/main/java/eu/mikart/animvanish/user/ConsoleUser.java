package eu.mikart.animvanish.user;

import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;

/**
 * The user system has been inspired by <a href="https://github.com/William278/HuskTowns">HuskTowns</a>
 * which is licensed under the Apache License 2.0.
 */
public final class ConsoleUser implements CommandUser {

	@NotNull
	private final Audience audience;

	private ConsoleUser(@NotNull Audience console) {
		this.audience = console;
	}

	@NotNull
	public static ConsoleUser wrap(@NotNull Audience console) {
		return new ConsoleUser(console);
	}

	@Override
	@NotNull
	public Audience getAudience() {
		return audience;
	}

	@Override
	public boolean hasPermission(@NotNull String permission) {
		return true;
	}

}