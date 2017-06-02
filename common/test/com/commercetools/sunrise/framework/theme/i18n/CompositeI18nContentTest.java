package com.commercetools.sunrise.framework.theme.i18n;

import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.Locale.ENGLISH;
import static org.assertj.core.api.Assertions.assertThat;

public class CompositeI18nContentTest {

    public static final I18nContent I18N_RETURNS_ALWAYS_EMPTY = (locale, i18nIdentifier, hashArgs) -> Optional.empty();

    @Test
    public void resolvesWithFirstResolver() throws Exception {
        final List<I18nContent> i18nContentList = asList(i18nReturnsAlwaysValue("foo"), i18nReturnsAlwaysValue("bar"));
        testCompositeResolver(i18nContentList, message -> assertThat(message).contains("foo"));
    }

    @Test
    public void fallbacksToSecondResolver() throws Exception {
        final List<I18nContent> i18nContentList = asList(I18N_RETURNS_ALWAYS_EMPTY, i18nReturnsAlwaysValue("bar"));
        testCompositeResolver(i18nContentList, message -> assertThat(message).contains("bar"));
    }

    @Test
    public void emptyWhenNotFoundInAnyResolver() throws Exception {
        final List<I18nContent> i18nContentList = asList(I18N_RETURNS_ALWAYS_EMPTY, I18N_RETURNS_ALWAYS_EMPTY);
        testCompositeResolver(i18nContentList, message -> assertThat(message).isEmpty());
    }

    public void testCompositeResolver(final List<I18nContent> i18nContentList, final Consumer<Optional<String>> test) {
        final I18nContent i18nContent = new CompositeI18nContent(i18nContentList);
        final I18nIdentifier i18nIdentifier = I18nIdentifier.of("bundle", "key");
        final Optional<String> message = i18nContent.find(singletonList(ENGLISH), i18nIdentifier);
        test.accept(message);
    }

    private I18nContent i18nReturnsAlwaysValue(final String bar) {
        return (locale, i18nIdentifier, hashArgs) -> Optional.of(bar);
    }

}
