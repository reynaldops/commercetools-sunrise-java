package com.commercetools.sunrise.framework.theme.engine.handlebars;

import com.github.jknack.handlebars.io.TemplateLoader;
import com.google.inject.ImplementedBy;
import play.Configuration;

import java.util.List;

@ImplementedBy(HandlebarsSettingsImpl.class)
public interface HandlebarsSettings {

    /**
     * @return the list of loaders used to find the template files
     */
    List<TemplateLoader> templateLoaders();

    static HandlebarsSettings of(final Configuration globalConfig, final String configPath) {
        return new HandlebarsSettingsImpl(globalConfig, configPath);
    }
}
