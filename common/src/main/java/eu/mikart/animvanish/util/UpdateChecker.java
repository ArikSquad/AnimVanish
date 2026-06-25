package eu.mikart.animvanish.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * Utility for comparing a {@link Version} against the latest version on various {@link Endpoint}s
 */
@SuppressWarnings("unused")
public class UpdateChecker {
    private final Endpoint endpoint;
    private final Version currentVersion;
    private final String versionMetaDelimiter;
    private final String resource;

    private UpdateChecker(@NotNull Endpoint endpoint, @NotNull Version currentVersion,
                          @NotNull String versionMetaDelimiter, @NotNull String resource) {
        this.endpoint = endpoint;
        this.currentVersion = currentVersion;
        this.versionMetaDelimiter = versionMetaDelimiter;
        this.resource = resource;
    }

    @NotNull
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Query endpoint for the latest {@link Version} of the plugin
     *
     * @return A {@link CompletableFuture} containing the latest {@link Version} of the plugin
     */
    public CompletableFuture<Completed> check() {
        return CompletableFuture
                .supplyAsync(() -> new Completed(this, Version.fromString(endpoint.query(resource), versionMetaDelimiter)))
                .exceptionally(throwable -> new Completed(this, currentVersion));
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Builder {
        private Endpoint endpoint = Endpoint.SPIGOT;
        private Version currentVersion;
        private String versionMetaDelimiter = Version.META_DELIMITER;
        private String resource;

        @NotNull
        public Builder endpoint(@NotNull Endpoint endpoint) {
            this.endpoint = endpoint;
            return this;
        }

        @NotNull
        public Builder currentVersion(@NotNull Version currentVersion) {
            this.currentVersion = currentVersion;
            return this;
        }

        @NotNull
        public Builder versionMetaDelimiter(@NotNull String versionMetaDelimiter) {
            this.versionMetaDelimiter = versionMetaDelimiter;
            return this;
        }

        @NotNull
        public Builder resource(@NotNull String resource) {
            this.resource = resource;
            return this;
        }

        @NotNull
        public UpdateChecker build() {
            if (currentVersion == null) {
                throw new IllegalStateException("Current version is not set");
            }
            if (resource == null) {
                throw new IllegalStateException("Resource is not set");
            }
            return new UpdateChecker(endpoint, currentVersion, versionMetaDelimiter, resource);
        }

    }

    /**
     * Represents endpoints from which the latest version can be queried
     * @since 1.0.0
     */
    public enum Endpoint {
        SPIGOT((resource -> {
            final String url = formatId("https://api.spigotmc.org/legacy/update.php?resource={id}", resource);
            try (final InputStreamReader reader = new InputStreamReader(URI.create(url).toURL().openConnection().getInputStream())) {
                return new BufferedReader(reader).readLine();
            } catch (IOException e) {
                throw new IllegalStateException("Unable to fetch latest version", e);
            }
        })),
        VOXEL((resource -> {
            final String url = formatId("https://api.voxel.shop/v1/getResourceInfoSimple/?resource_id={id}&key=version", resource);
            try (final InputStreamReader reader = new InputStreamReader(URI.create(url).toURL().openConnection().getInputStream())) {
                return new BufferedReader(reader).readLine();
            } catch (IOException e) {
                throw new IllegalStateException("Unable to fetch latest version", e);
            }
        })),
        HANGAR(resource -> {
            final String url = formatId("https://hangar.papermc.io/api/v1/projects/{id}/latestrelease", resource);
            try (final InputStreamReader reader = new InputStreamReader(URI.create(url).toURL().openConnection().getInputStream())) {
                return new BufferedReader(reader).readLine();
            } catch (IOException e) {
                throw new IllegalStateException("Unable to fetch latest version", e);
            }
        }),
        MODRINTH((resource -> {
            final String url = formatId("https://api.modrinth.com/v2/project/{id}/version", resource);
            try (final InputStreamReader reader = new InputStreamReader(URI.create(url).toURL().openConnection().getInputStream())) {
                final JSONArray array = new JSONArray(new BufferedReader(reader).readLine());
                for (int i = 0; i < array.length(); i++) {
                    final JSONObject object = array.getJSONObject(i);
                    if (object.getString("version_type").equals("release")) {
                        return object.getString("version_number");
                    }
                }
                throw new IllegalStateException("No versions found");
            } catch (IOException e) {
                throw new IllegalStateException("Unable to fetch latest version", e);
            }
        })),
        GITHUB((resource -> {
            final String url = formatId("https://api.github.com/repos/{id}/releases/latest", resource);
            try (final InputStreamReader reader = new InputStreamReader(URI.create(url).toURL().openConnection().getInputStream())) {
                return new JSONObject(new BufferedReader(reader).readLine()).getString("tag_name");
            } catch (IOException e) {
                throw new IllegalStateException("Unable to fetch latest version", e);
            }
        }));

        private final Function<String, String> queryFunction;

        Endpoint(@NotNull Function<String, String> queryFunction) {
            this.queryFunction = queryFunction;
        }

        @NotNull
        public String query(@NotNull String resource) {
            return queryFunction.apply(resource);
        }

        @NotNull
        private static String formatId(@NotNull String endpoint, @NotNull String resource) {
            return endpoint.replaceAll(Pattern.quote("{id}"), resource);
        }
    }

    public static class Completed {
        private final UpdateChecker checker;
        private final Version latestVersion;

        private Completed(@NotNull UpdateChecker checker, @NotNull Version latestVersion) {
            this.checker = checker;
            this.latestVersion = latestVersion;
        }

        @NotNull
        public Version getLatestVersion() {
            return latestVersion;
        }

        @NotNull
        public Version getCurrentVersion() {
            return checker.currentVersion;
        }

        public boolean isUpToDate() {
            return checker.currentVersion.compareTo(latestVersion) >= 0;
        }

    }

}