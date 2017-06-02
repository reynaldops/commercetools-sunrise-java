package com.commercetools.sunrise.framework.theme.i18n;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

final class CompositeI18nContent implements I18nContent {

    private final List<I18nContent> i18nContentList;

    CompositeI18nContent(final List<I18nContent> i18nContentList) {
        this.i18nContentList = i18nContentList;
    }

    @Override
    public Optional<String> find(final List<Locale> locales, final I18nIdentifier i18nIdentifier, final Map<String, Object> hashArgs) {
        return i18nContentList.stream()
                .map(content -> content.find(locales, i18nIdentifier, hashArgs))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();
    }
}
