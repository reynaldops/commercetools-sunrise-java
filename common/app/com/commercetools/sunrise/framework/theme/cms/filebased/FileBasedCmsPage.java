package com.commercetools.sunrise.framework.theme.cms.filebased;

import com.commercetools.sunrise.cms.CmsPage;
import com.commercetools.sunrise.framework.theme.i18n.I18nContent;
import com.commercetools.sunrise.framework.theme.i18n.I18nIdentifier;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * @see FileBasedCmsService
 */
public final class FileBasedCmsPage implements CmsPage {

    private final String pageKey;
    private final I18nContent i18nContent;
    private final List<Locale> locales;

    FileBasedCmsPage(final String pageKey, final I18nContent i18nContent, final List<Locale> locales) {
        this.pageKey = pageKey;
        this.i18nContent = i18nContent;
        this.locales = locales;
    }

    @Override
    public Optional<String> field(final String fieldName) {
        final I18nIdentifier i18nIdentifier = I18nIdentifier.of(pageKey, fieldName);
        return i18nContent.find(locales, i18nIdentifier);
    }
}