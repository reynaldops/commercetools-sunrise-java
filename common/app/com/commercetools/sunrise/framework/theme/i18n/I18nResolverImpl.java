package com.commercetools.sunrise.framework.theme.i18n;

import com.commercetools.sunrise.framework.injection.RequestScoped;
import com.commercetools.sunrise.framework.localization.UserLanguage;

import javax.inject.Inject;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RequestScoped
final class I18nResolverImpl implements I18nResolver {

    private final List<Locale> locales;
    private final List<I18nResolverLoader> resolverLoaders;
    private final I18nIdentifierFactory i18nIdentifierFactory;

    @Inject
    I18nResolverImpl(final UserLanguage userLanguage, final I18nSettings i18nSettings,
                     final I18nIdentifierFactory i18nIdentifierFactory) {
        this(userLanguage.locales(), i18nSettings.resolverLoaders(), i18nIdentifierFactory);
    }

    I18nResolverImpl(final List<Locale> locales, final List<I18nResolverLoader> resolverLoaders,
                     final I18nIdentifierFactory i18nIdentifierFactory) {
        this.locales = locales;
        this.resolverLoaders = resolverLoaders;
        this.i18nIdentifierFactory = i18nIdentifierFactory;
    }

    @Override
    public Optional<String> resolve(final String i18nIdentifierAsString) {
        final I18nIdentifier i18nIdentifier = i18nIdentifierFactory.create(i18nIdentifierAsString);
        for (I18nResolverLoader i18nResolverLoader : resolverLoaders) {
            final Optional<String> message = i18nResolverLoader.get(locales, i18nIdentifier);
            if (message.isPresent()) {
                return message;
            }
        }
        return Optional.empty();
    }
}
