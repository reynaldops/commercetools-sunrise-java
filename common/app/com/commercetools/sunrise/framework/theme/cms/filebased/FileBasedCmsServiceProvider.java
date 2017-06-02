package com.commercetools.sunrise.framework.theme.cms.filebased;

import com.commercetools.sunrise.cms.CmsService;
import com.commercetools.sunrise.framework.localization.ProjectContext;
import com.commercetools.sunrise.framework.theme.i18n.I18nSettings;
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

    @Inject
    FileBasedCmsServiceProvider(final Configuration configuration, final ProjectContext projectContext) {
        this.configuration = configuration;
        this.locales = projectContext.locales();
    }

    @Override
    public CmsService get() {
        final I18nSettings i18nSettings = I18nSettings.of(configuration, "sunrise.theme.cms.fileBased", locales);
        return FileBasedCmsService.of(i18nSettings.i18nContent());
    }
}
