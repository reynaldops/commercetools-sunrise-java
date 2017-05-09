package com.commercetools.sunrise.framework.theme.engine.handlebars;

import com.commercetools.sunrise.framework.theme.engine.handlebars.helpers.HandlebarsCmsHelper;
import com.commercetools.sunrise.framework.theme.engine.handlebars.helpers.HandlebarsI18nHelper;
import com.commercetools.sunrise.framework.theme.engine.handlebars.helpers.HandlebarsJsonHelper;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.cache.HighConcurrencyTemplateCache;
import com.github.jknack.handlebars.io.TemplateLoader;

import javax.inject.Inject;
import javax.inject.Provider;

public final class HandlebarsProvider implements Provider<Handlebars> {

    private final HandlebarsSettings settings;
    private final HandlebarsI18nHelper i18nHelper;
    private final HandlebarsCmsHelper cmsHelper;
    private final HandlebarsJsonHelper jsonHelper;

    @Inject
    HandlebarsProvider(final HandlebarsSettings settings, final HandlebarsI18nHelper i18nHelper,
                       final HandlebarsCmsHelper cmsHelper, final HandlebarsJsonHelper jsonHelper) {
        this.settings = settings;
        this.i18nHelper = i18nHelper;
        this.cmsHelper = cmsHelper;
        this.jsonHelper = jsonHelper;
    }

    @Override
    public Handlebars get() {
        final Handlebars handlebars = new Handlebars()
                .with(settings.templateLoaders().toArray(new TemplateLoader[settings.templateLoaders().size()]))
                .with(new HighConcurrencyTemplateCache())
                .infiniteLoops(true);
        handlebars.registerHelper("i18n", i18nHelper);
        handlebars.registerHelper("cms", cmsHelper);
        handlebars.registerHelper("json", jsonHelper);
        return handlebars;
    }
}
