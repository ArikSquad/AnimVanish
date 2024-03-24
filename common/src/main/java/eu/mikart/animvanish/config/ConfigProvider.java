package eu.mikart.animvanish.config;

import de.exlll.configlib.NameFormatters;
import de.exlll.configlib.YamlConfigurationProperties;
import de.exlll.configlib.YamlConfigurationStore;
import de.exlll.configlib.YamlConfigurations;
import eu.mikart.animvanish.IAnimVanish;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;

/**
 * The configuration has been inspired by <a href="https://github.com/William278/HuskTowns">HuskTowns</a>
 * which is licensed under the Apache License 2.0.
 */
public interface ConfigProvider {

    @NotNull
    YamlConfigurationProperties.Builder<?> YAML_CONFIGURATION_PROPERTIES = YamlConfigurationProperties.newBuilder()
            .charset(StandardCharsets.UTF_8)
            .setNameFormatter(NameFormatters.LOWER_UNDERSCORE);


    default void loadConfig() {
        loadSettings();
        loadLocales();
    }

    @NotNull
    Settings getSettings();

    void setSettings(@NotNull Settings settings);

    default void loadSettings() {
        setSettings(YamlConfigurations.update(
                getConfigDirectory().resolve("config.yml"),
                Settings.class,
                YAML_CONFIGURATION_PROPERTIES.header(Settings.CONFIG_HEADER).build()
        ));
    }

    @NotNull
    Locales getLocales();

    void setLocales(@NotNull Locales locales);

    /**
     * Load the locales from the config file
     *
     * @since 1.1.0
     */
    default void loadLocales() {
        final YamlConfigurationStore<Locales> store = new YamlConfigurationStore<>(
                Locales.class, YAML_CONFIGURATION_PROPERTIES.header(Locales.CONFIG_HEADER).build()
        );
        // Read existing locales if present
        final Path path = getConfigDirectory().resolve(String.format("messages-%s.yml", getSettings().getLanguage()));
        if (Files.exists(path)) {
            setLocales(store.load(path));
            return;
        }

        // Otherwise, save and read the default locales
        try (InputStream input = getResource(String.format("locales/%s.yml", getSettings().getLanguage()))) {
            final Locales locales = store.read(input);
            store.save(locales, path);
            setLocales(locales);
        } catch (Throwable e) {
            getPlugin().getLogger().log(Level.SEVERE, "An error occurred loading the locales (invalid lang code?)", e);
        }
    }


    /**
     * Get a plugin resource
     *
     * @param name The name of the resource
     * @return the resource, if found
     * @since 1.1.0
     */
    InputStream getResource(@NotNull String name);

    /**
     * Get the plugin config directory
     *
     * @return the plugin config directory
     * @since 1.1.0
     */
    @NotNull
    Path getConfigDirectory();

    @NotNull
    IAnimVanish getPlugin();

}
