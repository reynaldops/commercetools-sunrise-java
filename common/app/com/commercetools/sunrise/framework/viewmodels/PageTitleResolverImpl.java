package com.commercetools.sunrise.framework.viewmodels;

import com.commercetools.sunrise.framework.injection.RequestScoped;
import com.commercetools.sunrise.framework.theme.i18n.I18nResolver;

import javax.inject.Inject;
import java.util.Optional;

@RequestScoped
final class PageTitleResolverImpl implements PageTitleResolver {

    private final I18nResolver i18nResolver;

    @Inject
    PageTitleResolverImpl(final I18nResolver i18nResolver) {
        this.i18nResolver = i18nResolver;
    }

    @Override
    public Optional<String> find(final String key) {
        return i18nResolver.resolve(key);
    }
}
