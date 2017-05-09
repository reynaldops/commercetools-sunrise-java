package com.commercetools.sunrise.framework.theme.engine.handlebars;

import com.commercetools.sunrise.cms.CmsPage;
import com.commercetools.sunrise.framework.theme.engine.TemplateEngine;
import com.commercetools.sunrise.framework.theme.engine.TemplateNotFoundException;
import com.commercetools.sunrise.framework.theme.engine.TemplateRenderException;
import com.commercetools.sunrise.framework.viewmodels.PageData;
import com.github.jknack.handlebars.Context;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.io.IOException;

public final class HandlebarsTemplateEngine implements TemplateEngine {

    private static final Logger LOGGER = LoggerFactory.getLogger(TemplateEngine.class);
    private final Handlebars handlebars;
    private final HandlebarsContextFactory contextFactory;

    @Inject
    HandlebarsTemplateEngine(final Handlebars handlebars, final HandlebarsContextFactory contextFactory) {
        this.handlebars = handlebars;
        this.contextFactory = contextFactory;
    }

    @Override
    public String render(final String templateName, final PageData pageData, @Nullable final CmsPage cmsPage) {
        final Template template = compileTemplate(templateName);
        final Context context = contextFactory.create(templateName, pageData, cmsPage);
        try {
            LOGGER.debug("Rendering template " + templateName);
            return template.apply(context);
        } catch (IOException e) {
            throw new TemplateRenderException("Context could not be applied to template " + templateName, e);
        }
    }

    private Template compileTemplate(final String templateName) {
        try {
            return handlebars.compile(templateName);
        } catch (IOException e) {
            throw new TemplateNotFoundException("Could not find the default template", e);
        }
    }
}
