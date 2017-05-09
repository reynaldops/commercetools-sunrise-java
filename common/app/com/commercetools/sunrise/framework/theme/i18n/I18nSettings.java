package com.commercetools.sunrise.framework.theme.i18n;

import com.google.inject.ImplementedBy;
import play.Configuration;

import java.util.List;

@ImplementedBy(I18nSettingsImpl.class)
public interface I18nSettings {

    /**
     * @return the list of bundles to be used to find the i18n messages
     */
    List<String> bundles();

    /**
     * @return the list of loaders to be used to generate messages from i18n expressions
     */
    List<I18nResolverLoader> resolverLoaders();

    static I18nSettings of(final Configuration globalConfig, final String configPath, final I18nResolverLoaderFactory i18nResolverLoaderFactory) {
        return new I18nSettingsImpl(globalConfig, configPath, i18nResolverLoaderFactory);
    }
}
