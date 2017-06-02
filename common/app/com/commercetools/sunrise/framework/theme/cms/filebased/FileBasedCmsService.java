package com.commercetools.sunrise.framework.theme.cms.filebased;

import com.commercetools.sunrise.cms.CmsPage;
import com.commercetools.sunrise.cms.CmsService;
import com.commercetools.sunrise.framework.theme.i18n.I18nContent;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.completedFuture;

/**
 * Service that provides content data from i18n files that can be found in a local file.
 * Internally it uses a I18nResolverLoader to resolve the requested messages.
 *
 * The mapping of the {@link CmsService} parameters to {@link I18nContent} parameters goes as follows:
 *
 * - {@code bundle} = {@code entryType} (e.g. banner)
 * - {@code messageKey} = {@code entryKey.fieldName} (e.g. homeTopLeft.subtitle.text)
 */
public final class FileBasedCmsService implements CmsService {

    private final I18nContent i18nContent;

    private FileBasedCmsService(final I18nContent i18nContent) {
        this.i18nContent = i18nContent;
    }

    @Override
    public CompletionStage<Optional<CmsPage>> page(final String pageKey, final List<Locale> locales) {
        final CmsPage cmsPage = new FileBasedCmsPage(pageKey, i18nContent, locales);
        return completedFuture(Optional.of(cmsPage));
    }

    public static FileBasedCmsService of(final I18nContent i18nContent) {
        return new FileBasedCmsService(i18nContent);
    }
}