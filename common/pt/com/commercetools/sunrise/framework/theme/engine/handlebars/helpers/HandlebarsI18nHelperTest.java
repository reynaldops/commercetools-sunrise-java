package com.commercetools.sunrise.framework.theme.engine.handlebars.helpers;

import com.commercetools.sunrise.framework.theme.engine.TemplateEngine;
import com.commercetools.sunrise.framework.theme.engine.handlebars.HandlebarsSettings;
import com.commercetools.sunrise.framework.theme.engine.handlebars.HandlebarsTemplateEngine;
import com.commercetools.sunrise.framework.theme.i18n.I18nContent;
import com.commercetools.sunrise.framework.theme.i18n.I18nSettings;
import com.commercetools.sunrise.framework.theme.i18n.TestableI18nContent;
import com.commercetools.sunrise.framework.viewmodels.PageData;
import com.commercetools.sunrise.test.TestableSphereClient;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.google.inject.AbstractModule;
import io.sphere.sdk.client.SphereClient;
import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.test.WithApplication;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Consumer;

import static java.util.Collections.singletonList;
import static java.util.Locale.ENGLISH;
import static java.util.Locale.GERMAN;
import static org.assertj.core.api.Assertions.assertThat;

public class HandlebarsI18nHelperTest extends WithApplication {

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder()
                .overrides(new AbstractModule() {
                    @Override
                    protected void configure() {
                        bind(SphereClient.class).toInstance(TestableSphereClient.ofEmptyResponse());
                        bind(HandlebarsSettings.class).toInstance(handlebarsSettings());
                        bind(I18nSettings.class).toInstance(i18nSettings());
                        bind(TemplateEngine.class).to(HandlebarsTemplateEngine.class);
                    }
                }).build();
    }

    @Test
    public void resolvesMessage() throws Exception {
        testTemplate("simple", ENGLISH, html -> assertThat(html).contains("bar"));
    }

    @Test
    public void resolvesMessageWithBundle() throws Exception {
        testTemplate("bundle", ENGLISH, html -> assertThat(html).contains("something"));
    }

    @Test
    public void resolvesMessageWithParameters() throws Exception {
        testTemplate("parameter", ENGLISH, html -> assertThat(html).contains("something firstName=John,lastName=Doe"));
    }

    @Test
    public void languageNotFound() throws Exception {
        testTemplate("bundle", GERMAN, html -> assertThat(html).isEmpty());
    }

    @Test
    public void keyNotFound() throws Exception {
        testTemplate("missingKey", ENGLISH, html -> assertThat(html).isEmpty());
    }

    @Test
    public void bundleNotFound() throws Exception {
        testTemplate("missingBundle", ENGLISH, html -> assertThat(html).isEmpty());
    }

    private void testTemplate(final String templateName, final Locale locale, final Consumer<String> test) {
        final TemplateEngine templateEngine = app.injector().instanceOf(TemplateEngine.class);
//        final I18nSettings i18nSettings = mock(I18nSettings.class);
//        when(i18nSettings.i18nContent()).thenReturn(i18nResolver(i18nMap));
//        final HandlebarsI18nHelper i18nHelper = new HandlebarsI18nHelperImpl(i18nSettings, new I18nIdentifierFactory());
//        final Handlebars handlebars = new Handlebars()
//                .with(DEFAULT_LOADER)
//                .registerHelper("i18n", i18nHelper);
//
//        final UserLanguage userLanguage = mock(UserLanguage.class);
//        final HandlebarsContextSettings contextSettings = mock(HandlebarsContextSettings.class);
//        when(contextSettings.valueResolvers()).thenReturn(singletonList(new PlayJavaFormResolver(msg -> msg)));
//        final HandlebarsContextFactory contextFactory = new HandlebarsContextFactory(contextSettings, userLanguage);
//        final TemplateEngine templateEngine = new HandlebarsTemplateEngine(handlebars, contextFactory);
        final String html = renderTemplate(templateName, templateEngine, locale);
        test.accept(html);
    }

    private static String renderTemplate(final String templateName, final TemplateEngine templateEngine, final Locale locale) {
        return templateEngine.render(templateName, new PageData(), null);
    }

    private static HandlebarsSettings handlebarsSettings() {
        return () -> singletonList(new ClassPathTemplateLoader("/templates/i18nHelper"));
    }

    private static I18nSettings i18nSettings() {
        final Map<String, String> i18nMap = new HashMap<>();
        i18nMap.put("en/somebundle:somekey", "something");
        i18nMap.put("es/somebundle:somekey", "algo");
        i18nMap.put("en/main:foo", "bar");

        return new I18nSettings() {
            @Override
            public List<String> bundles() {
                return null;
            }

            @Override
            public I18nContent i18nContent() {
                return new TestableI18nContent(i18nMap);
            }
        };
    }
}
