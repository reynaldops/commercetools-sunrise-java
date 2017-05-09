package com.commercetools.sunrise.framework.theme.i18n;

import com.google.inject.ImplementedBy;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@ImplementedBy(I18nResolverImpl.class)
@FunctionalInterface
public interface I18nResolver {

    Optional<String> resolve(final String i18nIdentifier);

    default String resolveOrEmpty(final String i18nIdentifier) {
        return resolve(i18nIdentifier).orElse("");
    }

    default String resolveOrKey(final String i18nIdentifier) {
        return resolve(i18nIdentifier).orElse(i18nIdentifier);
    }

    static I18nResolver of(final List<Locale> locales, final List<I18nResolverLoader> resolverLoaders,
                           final I18nIdentifierFactory i18nIdentifierFactory) {
        return new I18nResolverImpl(locales, resolverLoaders, i18nIdentifierFactory);
    }
}
