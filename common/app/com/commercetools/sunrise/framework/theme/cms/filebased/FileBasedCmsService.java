package com.commercetools.sunrise.framework.theme.cms.filebased;

import com.commercetools.sunrise.cms.CmsPage;
import com.commercetools.sunrise.cms.CmsService;
import com.commercetools.sunrise.framework.theme.i18n.I18nIdentifierFactory;
import com.commercetools.sunrise.framework.theme.i18n.I18nResolver;
import com.commercetools.sunrise.framework.theme.i18n.I18nResolverLoader;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.completedFuture;

/**
 * Service that provides content data from i18n files that can be found in a local file.
 * Internally it uses a I18nResolverLoader to resolve the requested messages.
 *
 * The mapping of the {@link CmsService} parameters to {@link I18nResolverLoader} parameters goes as follows:
 *
 * - {@code bundle} = {@code entryType} (e.g. banner)
 * - {@code messageKey} = {@code entryKey.fieldName} (e.g. homeTopLeft.subtitle.text)
 */
public final class FileBasedCmsService implements CmsService {

    private List<I18nResolverLoader> i18nResolverLoaders;
    private final I18nIdentifierFactory i18nIdentifierFactory;

    private FileBasedCmsService(final List<I18nResolverLoader> i18nResolverLoaders,
                                final I18nIdentifierFactory i18nIdentifierFactory) {
        this.i18nResolverLoaders = i18nResolverLoaders;
        this.i18nIdentifierFactory = i18nIdentifierFactory;
    }

    @Override
    public CompletionStage<Optional<CmsPage>> page(final String pageKey, final List<Locale> locales) {
        final I18nResolver i18nResolver = I18nResolver.of(locales, i18nResolverLoaders, i18nIdentifierFactory);
        final CmsPage cmsPage = new FileBasedCmsPage(i18nResolverLoaders, locales, pageKey);
        return completedFuture(Optional.of(cmsPage));
    }

    public static FileBasedCmsService of(final List<I18nResolverLoader> i18nResolverLoaders) {
        return new FileBasedCmsService(i18nResolverLoaders, i18nIdentifierFactory);
    }
}