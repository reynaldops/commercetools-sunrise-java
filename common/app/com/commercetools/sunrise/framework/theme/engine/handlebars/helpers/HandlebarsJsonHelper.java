package com.commercetools.sunrise.framework.theme.engine.handlebars.helpers;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import com.google.inject.ImplementedBy;

import java.io.IOException;

@ImplementedBy(HandlebarsJsonHelperImpl.class)
public interface HandlebarsJsonHelper extends Helper<Object> {

    @Override
    CharSequence apply(Object context, Options options) throws IOException;
}
