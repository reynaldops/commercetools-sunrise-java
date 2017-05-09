package com.commercetools.sunrise.framework.theme.i18n.contentloaders;

import com.commercetools.sunrise.framework.theme.i18n.contentloaders.ContentLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.InputStream;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public final class YamlContentLoader implements ContentLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContentLoader.class);

    @Override
    public Optional<Map> load(final String filepath, final Locale locale, final String bundle) {
        final String path = String.format("%s/%s/%s.yaml", filepath, locale.toLanguageTag(), bundle);
        final InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
        if (resourceAsStream != null) {
            try {
                return Optional.of((Map) new Yaml().load(resourceAsStream));
            } catch (final YAMLException e) {
                LOGGER.error("Failed to load bundle '{}' for locale '{}' in filepath '{}'", bundle, locale, filepath, e);
            }
        } else {
            LOGGER.warn("Could not find bundle '{}' for locale '{}' in filepath '{}'", bundle, locale, filepath);
        }
        return Optional.empty();
    }
}
