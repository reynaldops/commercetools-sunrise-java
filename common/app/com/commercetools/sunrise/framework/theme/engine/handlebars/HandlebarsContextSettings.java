package com.commercetools.sunrise.framework.theme.engine.handlebars;

import com.github.jknack.handlebars.ValueResolver;
import com.google.inject.ImplementedBy;

import java.util.List;

@ImplementedBy(HandlebarsContextSettingsImpl.class)
public interface HandlebarsContextSettings {

    /**
     * @return the list of value resolvers used to resolve the expressions
     */
    List<ValueResolver> valueResolvers();
}
