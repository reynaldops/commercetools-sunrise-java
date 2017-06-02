package com.commercetools.sunrise.framework.theme.i18n;

import com.google.inject.ImplementedBy;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@ImplementedBy(I18nResolverImpl.class)
@FunctionalInterface
public interface I18nResolver {

    Optional<String> find(final String i18nIdentifier);

    default String getOrEmpty(final String i18nIdentifier) {
        return find(i18nIdentifier).orElse("");
    }

    default String getOrKey(final String i18nIdentifier) {
        return find(i18nIdentifier).orElse(i18nIdentifier);
    }

    static I18nResolver of(final List<Locale> locales, final I18nSettings i18nSettings,
                           final I18nIdentifierFactory i18nIdentifierFactory) {
        return new I18nResolverImpl(locales, i18nSettings, i18nIdentifierFactory);
    }
}
