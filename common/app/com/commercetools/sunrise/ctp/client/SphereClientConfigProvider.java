package com.commercetools.sunrise.ctp.client;

import com.commercetools.sunrise.play.configuration.SunriseConfigurationException;
import com.google.inject.Provider;
import io.sphere.sdk.client.SphereClientConfig;
import play.Configuration;

import javax.inject.Inject;

public final class SphereClientConfigProvider implements Provider<SphereClientConfig> {

    private final Configuration clientConfiguration;

    @Inject
    SphereClientConfigProvider(final Configuration configuration) {
        this.clientConfiguration = configuration.getConfig("ctp.client");
    }

    @Override
    public SphereClientConfig get() {
        try {
            final String projectKey = clientConfiguration.getString("projectKey");
            final String clientId = clientConfiguration.getString("clientId");
            final String clientSecret = clientConfiguration.getString("clientSecret");
            return SphereClientConfig.of(projectKey, clientId, clientSecret)
                    .withApiUrl(clientConfiguration.getString("apiUrl"))
                    .withAuthUrl(clientConfiguration.getString("authUrl"));
        } catch (IllegalArgumentException e) {
            throw new SunriseConfigurationException("Could not initialize SphereClientConfig", "ctp.client", e);
        }
    }
}
