package com.commercetools.sunrise.framework.theme.i18n;

import io.sphere.sdk.models.Base;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import static java.util.Collections.emptyMap;

/**
 * Resolves the i18n messages from the content of YAML files localed in the given path.
 * Parameters and pluralized forms are supported.
 *
 * For pluralized forms, specify the amount of items as a hash parameter {@code count}.
 * For the plural message, a suffix {@code _plural} must be added to the message key.
 * Notice that only pluralization forms similar to English are currently supported (1 is singular, the rest are plural).
 */
final class I18nContentImpl extends Base implements I18nContent {

    private final Map<String, Map> map;

    I18nContentImpl(final String path, final List<Locale> locales, final List<String> bundles) {
        this.map = new YamlContentLoader(path).load(locales, bundles);
    }

    @Override
    public Optional<String> find(final List<Locale> locales, final I18nIdentifier i18nIdentifier, final Map<String, Object> hashArgs) {
        final String message = findPluralizedTranslation(locales, i18nIdentifier, hashArgs)
                .orElseGet(() -> findFirstTranslation(locales, i18nIdentifier.getBundle(), i18nIdentifier.getMessageKey())
                        .orElse(null));
        return Optional.ofNullable(message).map(resolvedValue -> replaceParameters(resolvedValue, hashArgs));
    }

    static String buildKey(final String languageTag, final String bundle) {
        return languageTag + "/" + bundle;
    }

    private Optional<String> findPluralizedTranslation(final List<Locale> locales, final I18nIdentifier i18nIdentifier,
                                                       final Map<String, Object> hashArgs) {
        if (containsPlural(hashArgs)) {
            final String pluralizedKey = i18nIdentifier.getMessageKey() + "_plural";
            return findFirstTranslation(locales, i18nIdentifier.getBundle(), pluralizedKey);
        } else {
            return Optional.empty();
        }
    }

    private Optional<String> findFirstTranslation(final List<Locale> locales, final String bundle, final String key) {
        for (final Locale locale : locales) {
            final String message = resolveTranslation(locale, bundle, key);
            if (message != null) {
                return Optional.of(message);
            }
        }
        return Optional.empty();
    }

    private String replaceParameters(final String resolvedValue, final Map<String, Object> hashArgs) {
        String message = StringUtils.defaultString(resolvedValue);
        for (final Map.Entry<String, Object> entry : hashArgs.entrySet()) {
            if (entry.getValue() != null) {
                final String parameter = "__" + entry.getKey() + "__";
                message = message.replace(parameter, entry.getValue().toString());
            }
        }
        return message;
    }

    @Nullable
    private String resolveTranslation(final Locale locale, final String bundle, final String key) {
        final Map content = getContent(bundle, locale);
        final String[] pathSegments = StringUtils.split(key, '.');
        return resolveTranslation(content, pathSegments, 0);
    }

    private Map getContent(final String bundle, final Locale locale) {
        final String key = buildKey(locale.toLanguageTag(), bundle);
        return map.getOrDefault(key, emptyMap());
    }

    @Nullable
    private static String resolveTranslation(final Map content, final String[] pathSegments, final int index) {
        String message = null;
        if (index < pathSegments.length) {
            final Object entry = content.get(pathSegments[index]);
            if (entry instanceof String) {
                message = (String) entry;
            } else if (entry instanceof Map) {
                message = resolveTranslation((Map) entry, pathSegments, index + 1);
            }
        }
        return message;
    }

    private static boolean containsPlural(final Map<String, Object> hash) {
        return hash.entrySet().stream()
                .filter(entry -> entry.getKey().equals("count") && entry.getValue() instanceof Number)
                .anyMatch(entry -> ((Number) entry.getValue()).doubleValue() != 1);
    }
}
