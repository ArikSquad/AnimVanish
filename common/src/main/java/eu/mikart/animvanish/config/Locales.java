package eu.mikart.animvanish.config;

import com.google.common.collect.Maps;
import de.exlll.configlib.Configuration;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.apache.commons.lang.StringEscapeUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

/**
 * The configuration has been inspired by <a href="https://github.com/William278/HuskTowns">HuskTowns</a>
 * which is licensed under the Apache License 2.0.
 */
@Configuration
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Locales {

    static final String CONFIG_HEADER = """
            ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
            ┃     AnimVanish - Locales     ┃
            ┃    Developed by ArikSquad    ┃
            ┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛
            ┣╸ Thanks for using AnimVanish.
            ┗╸ Translate AnimVanish: https://github.com/ArikSquad/AnimVanish""";

    protected static final String DEFAULT_LOCALE = "en-us";

    // The raw set of locales loaded from yaml
    Map<String, String> locales = Maps.newTreeMap();

    /**
     * Returns a raw, unformatted locale loaded from the locales file
     *
     * @param localeId String identifier of the locale, corresponding to a key in the file
     * @return An {@link Optional} containing the locale corresponding to the id, if it exists
     */
    public Optional<String> getRawLocale(@NotNull String localeId) {
        return Optional.ofNullable(locales.get(localeId)).map(StringEscapeUtils::unescapeJava);
    }

    /**
     * Returns a raw, un-formatted locale loaded from the locales file, with replacements applied
     * <p>
     * Note that replacements will not be MiniMessage-escaped; use {@link #escapeText(String)} to escape replacements
     *
     * @param localeId     String identifier of the locale, corresponding to a key in the file
     * @param replacements Ordered array of replacement strings to fill in placeholders with
     * @return An {@link Optional} containing the replacement-applied locale corresponding to the id, if it exists
     */
    public Optional<String> getRawLocale(@NotNull String localeId, @NotNull String... replacements) {
        return getRawLocale(localeId).map(locale -> applyReplacements(locale, replacements));
    }

    /**
     * Returns a MiniMessage-formatted locale from the locales file
     *
     * @param localeId String identifier of the locale, corresponding to a key in the file
     * @return An {@link Optional} containing the formatted locale corresponding to the id, if it exists
     */
    public Optional<Component> getLocale(@NotNull String localeId) {
        return getRawLocale(localeId).map(this::format);
    }

    /**
     * Returns a MiniMessage-formatted locale from the locales file, with replacements applied
     *
     * @param localeId     String identifier of the locale, corresponding to a key in the file
     * @param replacements Ordered array of replacement strings to fill in placeholders with
     * @return An {@link Optional} containing the replacement-applied, formatted locale corresponding to the id, if it exists
     */
    public Optional<Component> getLocale(@NotNull String localeId, @NotNull String... replacements) {

        return getRawLocale(localeId, Arrays.stream(replacements).map(Locales::escapeText)
                .toArray(String[]::new)).map(this::format);
    }

    /**
     * Returns a MiniMessage-formatted string
     *
     * @param text The text to format
     * @return A {@link Component} object containing the formatted text
     */
    @NotNull
    public Component format(@NotNull String text) {
        return MiniMessage.miniMessage().deserialize(text, Placeholder.component("prefix", MiniMessage.miniMessage().deserialize(getRawLocale("prefix").orElse("[<gradient:#77DD77:#AFEEEE>AnimVanish</gradient>]"))));
    }

    /**
     * Apply placeholder replacements to a raw locale
     *
     * @param rawLocale    The raw, unparsed locale
     * @param replacements Ordered array of replacement strings to fill in placeholders with
     * @return the raw locale, with inserted placeholders
     */
    @NotNull
    private String applyReplacements(@NotNull String rawLocale, @NotNull String... replacements) {
        int replacementIndexer = 1;
        for (String replacement : replacements) {
            String replacementString = "%" + replacementIndexer + "%";
            rawLocale = rawLocale.replace(replacementString, replacement);
            replacementIndexer += 1;
        }
        return rawLocale;
    }

    /**
     * Escape a string from {@link Component} formatting
     * I don't know if I need this.
     *
     * @param string The string to escape
     * @return The escaped string
     */
    @NotNull
    public static String escapeText(@NotNull String string) {
        final StringBuilder value = new StringBuilder();
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            boolean isEscape = c == '\\';
            boolean isColorCode = i + 1 < string.length() && (c == 167 || c == '&');
            boolean isEvent = c == '[' || c == ']' || c == '(' || c == ')';
            if (isEscape || isColorCode || isEvent) {
                value.append('\\');
            }

            value.append(c);
        }
        return value.toString().replace("__", "_\\_");
    }

    @NotNull
    public String truncateText(@NotNull String string, int truncateAfter) {
        if (string.isBlank()) {
            return string;
        }
        return string.length() > truncateAfter ? string.substring(0, truncateAfter) + "…" : string;
    }

    public enum Slot {
        CHAT,
        ACTION_BAR,
        TITLE,
        SUBTITLE,
        NONE
    }

}
