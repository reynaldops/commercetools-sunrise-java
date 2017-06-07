//package com.commercetools.sunrise.framework.theme.engine.handlebars.helpers;
//
//import com.commercetools.sunrise.cms.CmsPage;
//import com.commercetools.sunrise.framework.localization.UserLanguage;
//import com.commercetools.sunrise.framework.theme.engine.TemplateEngine;
//import com.commercetools.sunrise.framework.theme.engine.handlebars.HandlebarsContextFactory;
//import com.commercetools.sunrise.framework.theme.engine.handlebars.HandlebarsContextSettings;
//import com.commercetools.sunrise.framework.theme.engine.handlebars.HandlebarsTemplateEngine;
//import com.commercetools.sunrise.framework.viewmodels.PageData;
//import com.github.jknack.handlebars.Handlebars;
//import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
//import org.junit.Test;
//
//import java.util.Optional;
//import java.util.function.Consumer;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.mock;
//
//public class HandlebarsCmsHelperTest {
//
//    @Test
//    public void resolvesMessage() throws Exception {
//        testTemplate("simple", html -> assertThat(html).contains("something"));
//    }
//
//    @Test
//    public void fieldNotFound() throws Exception {
//        testTemplate("missingField", html -> assertThat(html).isEmpty());
//    }
//
//    private static void testTemplate(final String templateName, final Consumer<String> test) throws Exception {
//        final String html = renderTemplate(templateName, handlebarsTemplateEngine());
//        test.accept(html);
//    }
//
//    private static String renderTemplate(final String templateName, final TemplateEngine templateEngine) throws Exception {
//        return templateEngine.render(templateName, new PageData(), cmsPage());
//    }
//
//    private static CmsPage cmsPage() {
//        return fieldName -> {
//            if (fieldName.equals("someid.somefield")) return Optional.of("something");
//            else return Optional.empty();
//        };
//    }
//
//    private static TemplateEngine handlebarsTemplateEngine() {
//        final Handlebars handlebars = new Handlebars()
//                .with(new ClassPathTemplateLoader("/templates/cmsHelper"));
//        final HandlebarsContextSettings handlebarsSettings = mock(HandlebarsContextSettings.class);
//        final UserLanguage userLanguage = mock(UserLanguage.class);
//        final HandlebarsContextFactory contextFactory = new HandlebarsContextFactory(handlebarsSettings, userLanguage);
//        return new HandlebarsTemplateEngine(handlebars, contextFactory);
//    }
//}
