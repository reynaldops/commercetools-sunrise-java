package com.commercetools.sunrise.framework.theme.engine.handlebars.helpers;

import com.commercetools.sunrise.cms.CmsPage;
import com.github.jknack.handlebars.Options;

import java.io.IOException;
import java.util.Optional;

import static com.commercetools.sunrise.framework.theme.engine.handlebars.HandlebarsContextKeys.CMS_PAGE_KEY;

final class HandlebarsCmsHelperImpl implements HandlebarsCmsHelper {

    @Override
    public CharSequence apply(final String context, final Options options) throws IOException {
        final Optional<CmsPage> cmsPage = findCmsPage(options);
        return cmsPage.map(page -> page.fieldOrEmpty(context)).orElse("");
    }

    private static Optional<CmsPage> findCmsPage(final Options options) {
        final Object cmsPageAsObject = options.get(CMS_PAGE_KEY);
        if (cmsPageAsObject instanceof CmsPage) {
            final CmsPage cmsPage = (CmsPage) cmsPageAsObject;
            return Optional.of(cmsPage);
        } else {
            return Optional.empty();
        }
    }
}
