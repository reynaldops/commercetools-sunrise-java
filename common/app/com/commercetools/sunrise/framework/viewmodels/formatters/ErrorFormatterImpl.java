package com.commercetools.sunrise.framework.viewmodels.formatters;

import com.commercetools.sunrise.framework.injection.RequestScoped;
import com.commercetools.sunrise.framework.theme.i18n.I18nResolver;

import javax.inject.Inject;

@RequestScoped
final class ErrorFormatterImpl implements ErrorFormatter {

    private final I18nResolver i18nResolver;

    @Inject
    ErrorFormatterImpl(final I18nResolver i18nResolver) {
        this.i18nResolver = i18nResolver;
    }

    @Override
    public String format(final String messageKey) {
        return i18nResolver.resolveOrKey(messageKey);
    }
}
