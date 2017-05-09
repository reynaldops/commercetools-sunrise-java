package com.commercetools.sunrise.framework.theme.i18n.contentloaders;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public interface ContentLoader {

    Optional<Map> load(final String filepath, final Locale locale, final String bundle);
}
