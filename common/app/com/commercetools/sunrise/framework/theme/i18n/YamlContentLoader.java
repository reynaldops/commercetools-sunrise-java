package com.commercetools.sunrise.framework.theme.i18n;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.InputStream;
import java.util.*;

import static com.commercetools.sunrise.framework.theme.i18n.I18nContentImpl.buildKey;

final class YamlContentLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(YamlContentLoader.class);

    private final String path;

    YamlContentLoader(final String path) {
        this.path = path;
    }

    Map<String, Map> load(final List<Locale> locales, final List<String> bundles) {
        final Map<String, Map> map = new HashMap<>();
        locales.forEach(locale -> bundles
                .forEach(bundle -> load(locale, bundle)
                        .ifPresent(content -> {
                            final String key = buildKey(locale.toLanguageTag(), bundle);
                            map.put(key, content);
                        })));
        return map;
    }

    private Optional<Map> load(final Locale locale, final String bundle) {
        final String path = String.format("%s/%s/%s.yaml", this.path, locale.toLanguageTag(), bundle);
        final InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
        if (resourceAsStream != null) {
            try {
                return Optional.of(new Yaml().loadAs(resourceAsStream, Map.class));
            } catch (final YAMLException e) {
                LOGGER.error("Failed to load bundle '{}' for locale '{}' in path '{}'", bundle, locale, this.path, e);
            }
        } else {
            LOGGER.warn("Could not find bundle '{}' for locale '{}' in path '{}'", bundle, locale, this.path);
        }
        return Optional.empty();
    }
}
