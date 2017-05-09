package com.commercetools.sunrise.framework.theme.engine.handlebars;

import com.commercetools.sunrise.play.configuration.SunriseConfigurationException;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.FileTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import play.Configuration;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@Singleton
final class HandlebarsSettingsImpl implements HandlebarsSettings {

    private final List<TemplateLoader> templateLoaders;

    @Inject
    HandlebarsSettingsImpl(final Configuration configuration) {
        this(configuration, "sunrise.theme.engine.handlebars");
    }

    HandlebarsSettingsImpl(final Configuration globalConfig, final String configPath) {
        final Configuration config = globalConfig.getConfig(configPath);
        this.templateLoaders = config.getConfigList("templateLoaders", emptyList()).stream()
                .map(HandlebarsSettingsImpl::initializeTemplateLoader)
                .collect(toList());
    }

    @Override
    public List<TemplateLoader> templateLoaders() {
        return templateLoaders;
    }

    private static TemplateLoader initializeTemplateLoader(final Configuration configuration) {
        final String type = configuration.getString("type");
        final String path = configuration.getString("path");
        switch (type) {
            case "classpath":
                return new ClassPathTemplateLoader(path);
            case "file":
                return new FileTemplateLoader(path);
            default:
                throw new SunriseConfigurationException("Could not initialize Handlebars due to unrecognized template loader \"" + type + "\"");
        }
    }
}
