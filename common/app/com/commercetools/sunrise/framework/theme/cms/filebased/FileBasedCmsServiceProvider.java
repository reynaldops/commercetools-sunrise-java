package com.commercetools.sunrise.framework.theme.cms.filebased;

import com.commercetools.sunrise.cms.CmsService;
import com.commercetools.sunrise.framework.localization.ProjectContext;
import com.commercetools.sunrise.framework.theme.cms.filebased.FileBasedCmsService;
import com.commercetools.sunrise.framework.theme.i18n.I18nResolverLoaderFactory;
import com.commercetools.sunrise.framework.theme.i18n.I18nSettings;
import com.commercetools.sunrise.framework.theme.i18n.composite.CompositeI18nResolver;
import com.commercetools.sunrise.framework.theme.i18n.composite.CompositeI18nResolverFactory;
import com.google.inject.Provider;
import play.Configuration;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.List;
import java.util.Locale;

public final class FileBasedCmsServiceProvider implements Provider<CmsService> {

    @Nullable
    private final Configuration configuration;
    private final List<Locale> locales;
    private final I18nResolverLoaderFactory i18nResolverLoaderFactory;

    @Inject
    FileBasedCmsServiceProvider(final Configuration configuration, final ProjectContext projectContext,
                                final I18nResolverLoaderFactory i18nResolverLoaderFactory) {
        this.configuration = configuration;
        this.locales = projectContext.locales();
        this.i18nResolverLoaderFactory = i18nResolverLoaderFactory;
    }

    @Override
    public CmsService get() {
        final I18nSettings i18nSettings = I18nSettings.of(configuration, "sunrise.theme.cms.fileBased", i18nResolverLoaderFactory);
        return FileBasedCmsService.of(i18nResolver);
    }
}
