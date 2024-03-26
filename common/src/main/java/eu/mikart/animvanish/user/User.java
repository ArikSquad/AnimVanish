package eu.mikart.animvanish.user;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * The user system has been inspired by <a href="https://github.com/William278/HuskTowns">HuskTowns</a>
 * which is licensed under the Apache License 2.0.
 */
public class User implements Comparable<User> {
	private UUID uuid;
	private String username;

	protected User(@NotNull UUID uuid, @NotNull String username) {
		this.uuid = uuid;
		this.username = username;
	}

	@NotNull
	public static User of(@NotNull UUID uuid, @NotNull String username) {
		return new User(uuid, username);
	}

	@SuppressWarnings("unused")
	private User() {
	}

	@NotNull
	public UUID getUuid() {
		return uuid;
	}

	@NotNull
	public String getName() {
		return username;
	}

	@NotNull
	public String getUsername() {
		return getName();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof User user)) {
			return false;
		}
		return user.getUuid().equals(uuid);
	}

	@Override
	public int compareTo(@NotNull User o) {
		return username.compareTo(o.getUsername());
	}
}
