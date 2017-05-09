package com.commercetools.sunrise.framework.theme.engine.handlebars;

import com.commercetools.sunrise.framework.localization.UserLanguage;
import com.github.jknack.handlebars.ValueResolver;
import com.github.jknack.handlebars.context.JavaBeanValueResolver;
import com.github.jknack.handlebars.context.MapValueResolver;

import javax.inject.Inject;
import java.util.List;
import java.util.Locale;

import static java.util.Arrays.asList;

public class HandlebarsContextSettingsImpl implements HandlebarsContextSettings {

    private final List<Locale> locales;
    private final PlayJavaFormResolver playJavaFormResolver;

    @Inject
    HandlebarsContextSettingsImpl(final UserLanguage userLanguage, final PlayJavaFormResolver playJavaFormResolver) {
        this.locales = userLanguage.locales();
        this.playJavaFormResolver = playJavaFormResolver;
    }

    @Override
    public List<ValueResolver> valueResolvers() {
        return asList(MapValueResolver.INSTANCE, javaBeanValueResolver(), playJavaFormResolver);
    }

    private SunriseJavaBeanValueResolver javaBeanValueResolver() {
        return new SunriseJavaBeanValueResolver(JavaBeanValueResolver.INSTANCE, locales);
    }
}
