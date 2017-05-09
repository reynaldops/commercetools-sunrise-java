package com.commercetools.sunrise.framework.theme.i18n;

import com.commercetools.sunrise.framework.SunriseModel;
import com.commercetools.sunrise.framework.localization.ProjectContext;
import com.commercetools.sunrise.framework.theme.i18n.contentloaders.ContentLoader;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.commercetools.sunrise.framework.theme.i18n.I18nResolverLoader.buildKey;

public class I18nResolverLoaderFactory extends SunriseModel {

    private final List<Locale> locales;

    @Inject
    protected I18nResolverLoaderFactory(final ProjectContext projectContext) {
        this.locales = projectContext.locales();
    }

    public I18nResolverLoader create(final ContentLoader contentLoader, final String filepath, final List<String> bundles) {
        return I18nResolverLoader.of(buildMap(contentLoader, filepath, bundles));
    }

    protected Map<String, Map> buildMap(final ContentLoader contentLoader, final String filepath, final List<String> bundles) {
        final Map<String, Map> map = new HashMap<>();
        locales.forEach(locale -> bundles
                .forEach(bundle -> contentLoader.load(filepath, locale, bundle)
                        .ifPresent(content -> {
                            final String key = buildKey(locale.toLanguageTag(), bundle);
                            map.put(key, content);
                        })));
        return map;
    }
}
