package com.commercetools.sunrise.framework.theme.i18n.composite;

import com.commercetools.sunrise.framework.theme.i18n.I18nIdentifier;
import com.commercetools.sunrise.framework.theme.i18n.I18nResolverLoader;
import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.Locale.ENGLISH;
import static org.assertj.core.api.Assertions.assertThat;

public class CompositeI18NResolverLoaderTest {

    public static final I18nResolverLoader I18N_RETURNS_ALWAYS_EMPTY = (locale, i18nIdentifier, hashArgs) -> Optional.empty();

    @Test
    public void resolvesWithFirstResolver() throws Exception {
        final List<I18nResolverLoader> i18nResolverLoaderList = asList(i18nReturnsAlwaysValue("foo"), i18nReturnsAlwaysValue("bar"));
        testCompositeResolver(i18nResolverLoaderList, message -> assertThat(message).contains("foo"));
    }

    @Test
    public void fallbacksToSecondResolver() throws Exception {
        final List<I18nResolverLoader> i18nResolverLoaderList = asList(I18N_RETURNS_ALWAYS_EMPTY, i18nReturnsAlwaysValue("bar"));
        testCompositeResolver(i18nResolverLoaderList, message -> assertThat(message).contains("bar"));
    }

    @Test
    public void emptyWhenNotFoundInAnyResolver() throws Exception {
        final List<I18nResolverLoader> i18nResolverLoaderList = asList(I18N_RETURNS_ALWAYS_EMPTY, I18N_RETURNS_ALWAYS_EMPTY);
        testCompositeResolver(i18nResolverLoaderList, message -> assertThat(message).isEmpty());
    }

    public void testCompositeResolver(final List<I18nResolverLoader> i18nResolverLoaderList, final Consumer<Optional<String>> test) {
        final CompositeI18nResolver i18nResolver = CompositeI18nResolver.of(i18nResolverLoaderList);
        final I18nIdentifier i18nIdentifier = I18nIdentifier.of("bundle", "key");
        final Optional<String> message = i18nResolver.get(singletonList(ENGLISH), i18nIdentifier);
        test.accept(message);
    }

    private I18nResolverLoader i18nReturnsAlwaysValue(final String bar) {
        return (locale, i18nIdentifier, hashArgs) -> Optional.of(bar);
    }

}
