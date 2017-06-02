package com.commercetools.sunrise.framework.theme.i18n;

import com.commercetools.sunrise.framework.localization.ProjectContext;
import com.commercetools.sunrise.play.configuration.SunriseConfigurationException;
import play.Configuration;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.Locale;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@Singleton
final class I18nSettingsImpl implements I18nSettings {

    private final List<String> bundles;
    private final I18nContent i18nContent;

    @Inject
    I18nSettingsImpl(final Configuration configuration, final ProjectContext projectContext) {
        this(configuration, "sunrise.theme.i18n", projectContext.locales());
    }

    I18nSettingsImpl(final Configuration globalConfig, final String configPath, final List<Locale> locales) {
        final Configuration config = globalConfig.getConfig(configPath);
        this.bundles = config.getStringList("bundles", emptyList());
        final List<I18nContent> i18nContentList = config.getConfigList("contentLoaders", emptyList()).stream()
                .map(content -> initializeContentLoader(content, locales, bundles))
                .collect(toList());
        this.i18nContent = I18nContent.ofList(i18nContentList);
    }

    @Override
    public List<String> bundles() {
        return bundles;
    }

    @Override
    public I18nContent i18nContent() {
        return i18nContent;
    }

    private static I18nContent initializeContentLoader(final Configuration configuration,
                                                       final List<Locale> locales, final List<String> bundles) {
        final String type = configuration.getString("type");
        final String path = configuration.getString("path");
        switch (type) {
            case "classpath":
                return I18nContent.of(path, locales, bundles);
            default:
                throw new SunriseConfigurationException("Could not initialize i18n Resolver due to unrecognized loader type: " + type);
        }
    }
}
