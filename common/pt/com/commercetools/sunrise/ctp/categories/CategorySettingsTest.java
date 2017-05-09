package com.commercetools.sunrise.ctp.categories;

import org.junit.Test;
import play.Application;
import play.Configuration;
import play.inject.guice.GuiceApplicationBuilder;
import play.test.WithApplication;

import static org.assertj.core.api.Assertions.assertThat;

public class CategorySettingsTest extends WithApplication {

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder()
                .configure(Configuration.empty())
                .build();
    }

    @Test
    public void fallbacksToDefaultValues() throws Exception {
        final CategorySettings configuration = app.injector().instanceOf(CategorySettings.class);
        assertThat(configuration.cacheExpiration())
                .as("Cache expiration")
                .isEmpty();
        assertThat(configuration.cacheKey())
                .as("Cache key")
                .isNotNull();
        assertThat(configuration.discardEmpty())
                .as("Discard empty")
                .isFalse();
        assertThat(configuration.sortExpressions())
                .as("Sort expressions")
                .isEmpty();
        assertThat(configuration.navigationExternalId())
                .as("Navigation external ID")
                .isEmpty();
        assertThat(configuration.newExtId())
                .as("New external ID")
                .isEmpty();
        assertThat(configuration.specialCategories())
                .as("Special categories")
                .isEmpty();
    }
}