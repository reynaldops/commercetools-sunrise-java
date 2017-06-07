package com.commercetools.sunrise.framework.theme.engine.handlebars.helpers;

import com.commercetools.sunrise.framework.theme.i18n.I18nIdentifierFactory;
import com.commercetools.sunrise.framework.theme.i18n.I18nResolver;
import com.commercetools.sunrise.framework.theme.i18n.I18nSettings;
import com.github.jknack.handlebars.Options;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static com.commercetools.sunrise.framework.theme.engine.handlebars.HandlebarsContextKeys.LANGUAGE_TAGS_KEY;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

final class HandlebarsI18nHelperImpl implements HandlebarsI18nHelper {

    private final I18nSettings i18nSettings;
    private final I18nIdentifierFactory i18nIdentifierFactory;

    @Inject
    HandlebarsI18nHelperImpl(final I18nSettings i18nSettings, final I18nIdentifierFactory i18nIdentifierFactory) {
        this.i18nSettings = i18nSettings;
        this.i18nIdentifierFactory = i18nIdentifierFactory;
    }

    @Override
    public CharSequence apply(final String context, final Options options) throws IOException {
        final List<Locale> locales = findLocales(options);
        final I18nResolver i18nResolver = I18nResolver.of(locales, i18nSettings, i18nIdentifierFactory);
        return i18nResolver.getOrEmpty(context);
    }

    @SuppressWarnings("unchecked")
    private static List<Locale> findLocales(final Options options) {
        final Object languageTagsAsObject = options.data(LANGUAGE_TAGS_KEY);
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
