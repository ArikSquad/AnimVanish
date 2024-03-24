package eu.mikart.animvanish.config;

import de.exlll.configlib.Comment;
import de.exlll.configlib.Configuration;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The configuration has been inspired by <a href="https://github.com/William278/HuskTowns">HuskTowns</a>
 * which is licensed under the Apache License 2.0.
 */
@Getter
@Configuration
@SuppressWarnings("FieldMayBeFinal")
public class Settings {

	protected static final String CONFIG_HEADER = """
            ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
            ┃       AnimVanish Config      ┃
            ┃    Developed by ArikSquad    ┃
            ┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛
            ┣╸ Information: https://github.com/ArikSquad/AnimVanish
            ┣╸ Config Help: https://animvanish.mikart.eu/configuration/config.yml
            ┗╸ Documentation: https://animvanish.mikart.eu/""";

	@Comment("Locale of the default language file to use. Docs: https://animvanish.mikart.eu/configuration/language-files")
	private String language = Locales.DEFAULT_LOCALE;

	@Comment("Whether to enable debug mode. This will print additional information to the console.")
	private boolean debug = false;

	@Comment("Whether to enable the plugin's update checker.")
	private boolean updateChecker = true;

	@Comment("All settings related to the effects.")
	private EffectsSettings effects = new EffectsSettings();

	@Getter
	@Configuration
	public static class EffectsSettings {

		private LightningSetting lightning = new LightningSetting();
		private ParticleSettings particle = new ParticleSettings();
		private SoundSettings sound = new SoundSettings();
		private BlindnessSettings blindness = new BlindnessSettings();
		private FireworkSettings firework = new FireworkSettings();
		private NPCSettings npc = new NPCSettings();
		private LaunchSettings launch = new LaunchSettings();

		@Getter
		@Configuration
		@NoArgsConstructor(access = AccessLevel.PRIVATE)
		public static class LightningSetting {

			@Comment("Will the time set to night with the effect? Default: true")
			private boolean night = true;

		}

		@Getter
		@Configuration
		@NoArgsConstructor(access = AccessLevel.PRIVATE)
		public static class ParticleSettings {

			@Comment("What particle will be used when using particle as the effect. Docs: https://animvanish.mikart.eu/configuration/config.yml#particle")
			private String type = "DRAGON_BREATH";

			@Comment("How many particles will be spawned. Default: 50")
			private int amount = 50;
		}

		@Getter
		@Configuration
		@NoArgsConstructor(access = AccessLevel.PRIVATE)
		public static class SoundSettings {

			@Comment("What sound the player will hear. Default: BLOCK_AMETHYST_BLOCK_HIT Docs: https://animvanish.mikart.eu/configuration/config.yml#sound")
			private String type = "BLOCK_AMETHYST_BLOCK_HIT";

		}

		@Getter
		@Configuration
		@NoArgsConstructor(access = AccessLevel.PRIVATE)
		public static class BlindnessSettings {

			@Comment("How many seconds the blindness effect will last. Default: 3")
			private int duration = 3;

			@Comment("Radius of how many blocks the blindness effect will reach. Default: 10")
			private int radius = 10;

		}

		@Getter
		@Configuration
		@NoArgsConstructor(access = AccessLevel.PRIVATE)
		public static class FireworkSettings {

			@Comment("What type of firework you want to have. Default: BURST Docs: https://animvanish.mikart.eu/configuration/config.yml#firework")
			private String type = "BURST";

			@Getter(AccessLevel.NONE)
			@Comment("Colors of the firework. Default: ['#FF0000', '#00FF00', '#0000FF', '#FFFF00', '#00FFFF']")
			private String[] colors = new String[] {
					"#FF0000",
					"#00FF00",
					"#0000FF",
					"#FFFF00",
					"#00FFFF"
			};

			@NotNull
			public List<Color> getColors() {
				return Arrays.stream(colors)
						.map(Color::decode)
						.collect(Collectors.toList());
			}
		}

		@Getter
		@Configuration
		@NoArgsConstructor(access = AccessLevel.PRIVATE)
		public static class NPCSettings {

			@Comment("How many seconds the NPC will be visible. Default: 3")
			private int duration = 3;

		}

		@Getter
		@Configuration
		@NoArgsConstructor(access = AccessLevel.PRIVATE)
		public static class LaunchSettings {

			@Comment("Enable if launch effect Armor Stands should use the players' own armor. Default: true")
			private boolean usePlayerArmor = true;

		}

	}
}
