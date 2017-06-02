package com.commercetools.sunrise.framework.theme.i18n;

import com.google.inject.ImplementedBy;
import play.Configuration;

import java.util.List;
import java.util.Locale;

@ImplementedBy(I18nSettingsImpl.class)
public interface I18nSettings {

    /**
     * @return the list of bundles to be used to find the i18n messages
     */
    List<String> bundles();

    /**
     * @return the i18n content to be used to generate messages from i18n expressions
     */
    I18nContent i18nContent();

    static I18nSettings of(final Configuration globalConfig, final String configPath, final List<Locale> locales) {
        return new I18nSettingsImpl(globalConfig, configPath, locales);
    }
}
