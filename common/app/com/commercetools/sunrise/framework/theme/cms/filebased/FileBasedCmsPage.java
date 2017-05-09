package com.commercetools.sunrise.framework.theme.cms.filebased;

import com.commercetools.sunrise.cms.CmsPage;
import com.commercetools.sunrise.framework.theme.i18n.I18nIdentifier;
import com.commercetools.sunrise.framework.theme.i18n.I18nResolver;

import java.util.Optional;

/**
 * @see FileBasedCmsService
 */
public final class FileBasedCmsPage implements CmsPage {

    private final String pageKey;
    private final I18nResolver i18nResolver;

    FileBasedCmsPage(final String pageKey, final I18nResolver i18nResolver) {
        this.pageKey = pageKey;
        this.i18nResolver = i18nResolver;
    }

    @Override
    public Optional<String> field(final String fieldName) {
        final I18nIdentifier i18nIdentifier = I18nIdentifier.of(pageKey, fieldName);
        I18nResolver.of(locales, i18nResolverLoaders, i18nIdentifierFactory);
        return i18nResolver.resolve(pageKey + ":" + i18nIdentifier);
    }
}