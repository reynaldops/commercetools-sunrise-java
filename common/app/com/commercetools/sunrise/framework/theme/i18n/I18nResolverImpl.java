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
    private final I18nContent i18nContent;
    private final I18nIdentifierFactory i18nIdentifierFactory;

    @Inject
    I18nResolverImpl(final UserLanguage userLanguage, final I18nSettings i18nSettings,
                     final I18nIdentifierFactory i18nIdentifierFactory) {
        this(userLanguage.locales(), i18nSettings, i18nIdentifierFactory);
    }

    I18nResolverImpl(final List<Locale> locales, final I18nSettings i18nSettings,
                     final I18nIdentifierFactory i18nIdentifierFactory) {
        this.locales = locales;
        this.i18nContent = i18nSettings.i18nContent();
        this.i18nIdentifierFactory = i18nIdentifierFactory;
    }

    @Override
    public Optional<String> find(final String i18nIdentifierAsString) {
        final I18nIdentifier i18nIdentifier = i18nIdentifierFactory.create(i18nIdentifierAsString);
        return i18nContent.find(locales, i18nIdentifier);
    }
}
