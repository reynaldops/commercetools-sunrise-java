package com.commercetools.sunrise.framework.theme.engine.handlebars.helpers;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import com.google.inject.ImplementedBy;

import java.io.IOException;

@ImplementedBy(HandlebarsI18nHelperImpl.class)
public interface HandlebarsI18nHelper extends Helper<String> {

    @Override
    CharSequence apply(String context, Options options) throws IOException;
}
