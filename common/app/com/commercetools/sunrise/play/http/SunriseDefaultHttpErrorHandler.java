package com.commercetools.sunrise.play.http;

import play.Configuration;
import play.Environment;
import play.api.OptionalSourceMapper;
import play.api.UsefulException;
import play.api.routing.Router;
import play.http.DefaultHttpErrorHandler;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.concurrent.CompletionStage;

@Singleton
public class SunriseDefaultHttpErrorHandler extends DefaultHttpErrorHandler {

    @Inject
    public SunriseDefaultHttpErrorHandler(final Configuration configuration, final Environment environment, final OptionalSourceMapper sourceMapper, final Provider<Router> routes) {
        super(configuration, environment, sourceMapper, routes);
    }

    @Override
    protected CompletionStage<Result> onDevServerError(final Http.RequestHeader request, final UsefulException exception) {
        return super.onDevServerError(request, exception);
    }
}
