package com.commercetools.sunrise.framework.theme.engine.handlebars.helpers;

import com.commercetools.sunrise.framework.SunriseModel;
import com.commercetools.sunrise.framework.theme.i18n.I18nIdentifier;
import com.commercetools.sunrise.framework.theme.i18n.I18nIdentifierFactory;
import com.commercetools.sunrise.framework.theme.i18n.I18nResolverLoader;
import com.github.jknack.handlebars.Context;
import com.github.jknack.handlebars.Options;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static com.commercetools.sunrise.framework.theme.engine.handlebars.HandlebarsContextKeys.LANGUAGE_TAGS_KEY;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

final class HandlebarsI18nHelperImpl extends SunriseModel implements HandlebarsI18nHelper {

    private final I18nResolverLoader i18nResolverLoader;
    private final I18nIdentifierFactory i18nIdentifierFactory;

    @Inject
    HandlebarsI18nHelperImpl(final I18nResolverLoader i18nResolverLoader, final I18nIdentifierFactory i18nIdentifierFactory) {
        this.i18nResolverLoader = i18nResolverLoader;
        this.i18nIdentifierFactory = i18nIdentifierFactory;
    }

    @Override
    public CharSequence apply(final String context, final Options options) throws IOException {
        final List<Locale> locales = getLocalesFromContext(options.context);
        final I18nIdentifier i18nIdentifier = i18nIdentifierFactory.create(context);
        return i18nResolverLoader.getOrEmpty(locales, i18nIdentifier, options.hash);
    }

    @SuppressWarnings("unchecked")
    private static List<Locale> getLocalesFromContext(final Context context) {
        final Object languageTagsAsObject = context.get(LANGUAGE_TAGS_KEY);
        if (languageTagsAsObject instanceof List) {
            final List<String> locales = (List<String>) languageTagsAsObject;
            return locales.stream()
                    .map(Locale::forLanguageTag)
                    .collect(toList());
        } else {
            return emptyList();
        }
    }
}
