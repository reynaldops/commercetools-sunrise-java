package com.commercetools.sunrise.framework.theme.i18n;

import com.commercetools.sunrise.framework.theme.i18n.contentloaders.YamlContentLoader;
import com.commercetools.sunrise.play.configuration.SunriseConfigurationException;
import play.Configuration;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@Singleton
final class I18nSettingsImpl implements I18nSettings {

    private final List<String> bundles;
    private final List<I18nResolverLoader> i18nResolverLoaders;

    @Inject
    I18nSettingsImpl(final Configuration configuration, final I18nResolverLoaderFactory i18nResolveLoaderFactory) {
        this(configuration, "sunrise.theme.i18n", i18nResolveLoaderFactory);
    }

    I18nSettingsImpl(final Configuration globalConfig, final String configPath, final I18nResolverLoaderFactory i18nResolverLoaderFactory) {
        final Configuration config = globalConfig.getConfig(configPath);
        this.bundles = config.getStringList("bundles", emptyList());
        this.i18nResolverLoaders = config.getConfigList("resolverLoaders", emptyList()).stream()
                .map(c -> initializeResolverLoader(c, bundles, i18nResolverLoaderFactory))
                .collect(toList());
    }

    @Override
    public List<String> bundles() {
        return bundles;
    }

    @Override
    public List<I18nResolverLoader> resolverLoaders() {
        return i18nResolverLoaders;
    }

    private static I18nResolverLoader initializeResolverLoader(final Configuration configuration, final List<String> bundles,
                                                               final I18nResolverLoaderFactory i18nResolverLoaderFactory) {
        final String type = configuration.getString("type");
        final String path = configuration.getString("path");
        switch (type) {
            case "yaml":
                return i18nResolverLoaderFactory.create(new YamlContentLoader(), path, bundles);
            default:
                throw new SunriseConfigurationException("Could not initialize i18n Resolver due to unrecognized resolver loader: " + type);
        }
    }
}
