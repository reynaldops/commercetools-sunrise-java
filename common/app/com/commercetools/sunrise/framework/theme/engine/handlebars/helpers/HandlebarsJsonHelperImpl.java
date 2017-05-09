package com.commercetools.sunrise.framework.theme.engine.handlebars.helpers;

import com.commercetools.sunrise.framework.SunriseModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jknack.handlebars.Options;

import java.io.IOException;

final class HandlebarsJsonHelperImpl extends SunriseModel implements HandlebarsJsonHelper {

    @Override
    public CharSequence apply(final Object context, final Options options) throws IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(context);
    }
}
