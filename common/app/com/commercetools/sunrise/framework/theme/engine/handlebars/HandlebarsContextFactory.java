package com.commercetools.sunrise.framework.theme.engine.handlebars;

import com.commercetools.sunrise.cms.CmsPage;
import com.commercetools.sunrise.framework.SunriseModel;
import com.commercetools.sunrise.framework.localization.UserLanguage;
import com.commercetools.sunrise.framework.viewmodels.PageData;
import com.github.jknack.handlebars.Context;
import com.github.jknack.handlebars.ValueResolver;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.UnaryOperator;

import static com.commercetools.sunrise.framework.theme.engine.handlebars.HandlebarsContextKeys.CMS_PAGE_KEY;
import static com.commercetools.sunrise.framework.theme.engine.handlebars.HandlebarsContextKeys.LANGUAGE_TAGS_KEY;
import static java.util.stream.Collectors.toList;

public class HandlebarsContextFactory extends SunriseModel {

    private final HandlebarsContextSettings settings;
    private final List<Locale> locales;

    @Inject
    HandlebarsContextFactory(final HandlebarsContextSettings settings, final UserLanguage userLanguage) {
        this.settings = settings;
        this.locales = userLanguage.locales();
    }

    public Context create(final String templateName, final PageData pageData, @Nullable final CmsPage cmsPage) {
        final Context context = createContextBuilder(pageData).build();
        return contextWithLocale()
                .andThen(contextWithCmsPage(cmsPage))
                .apply(context);
    }

    protected Context.Builder createContextBuilder(final PageData pageData) {
        return Context.newBuilder(pageData)
                .resolver(settings.valueResolvers().toArray(new ValueResolver[settings.valueResolvers().size()]));
    }

    private UnaryOperator<Context> contextWithCmsPage(@Nullable final CmsPage nullableCmsPage) {
        return context -> Optional.ofNullable(nullableCmsPage)
                .map(cmsPage -> context.data(CMS_PAGE_KEY, cmsPage))
                .orElse(context);
    }

    private UnaryOperator<Context> contextWithLocale() {
        return context -> context.data(LANGUAGE_TAGS_KEY, locales.stream()
                .map(Locale::toLanguageTag)
                .collect(toList()));
    }
}
