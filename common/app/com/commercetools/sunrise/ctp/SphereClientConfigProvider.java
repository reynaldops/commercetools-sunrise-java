package com.commercetools.sunrise.ctp;

import com.google.inject.Provider;
import io.sphere.sdk.client.SphereClientConfig;
import play.Configuration;

import javax.inject.Inject;
import java.util.Optional;

public final class SphereClientConfigProvider implements Provider<SphereClientConfig> {

    private static final String CONFIG_PROJECT_KEY = "ctp.projectKey";
    private static final String CONFIG_CLIENT_ID = "ctp.clientId";
    private static final String CONFIG_CLIENT_SECRET = "ctp.clientSecret";
    private static final String CONFIG_API_URL = "ctp.apiUrl";
    private static final String CONFIG_AUTH_URL = "ctp.authUrl";
    private static final String DEFAULT_API_URL = "https://api.sphere.io";
    private static final String DEFAULT_AUTH_URL = "https://auth.sphere.io";

    private final String project;
    private final String clientId;
    private final String clientSecret;
    private final String authUrl;
    private final String apiUrl;

    @Inject
    public SphereClientConfigProvider(final Configuration configuration) {
        this.project = getValue(configuration, CONFIG_PROJECT_KEY);
        this.clientId = getValue(configuration, CONFIG_CLIENT_ID);
        this.clientSecret = getValue(configuration, CONFIG_CLIENT_SECRET);
        this.authUrl = configuration.getString(CONFIG_AUTH_URL, DEFAULT_AUTH_URL);
        this.apiUrl = configuration.getString(CONFIG_API_URL, DEFAULT_API_URL);
    }

    @Override
    public SphereClientConfig get() {
        return SphereClientConfig.of(project, clientId, clientSecret, authUrl, apiUrl);
    }

    private String getValue(final Configuration configuration, final String key) {
        return Optional.ofNullable(configuration.getString(key))
                .orElseThrow(() -> new SphereClientCredentialsException("Missing value for configuration key " + key));
    }
}
