package com.fetch.exceptions.handlers;

import com.fetch.exceptions.FetchServiceBadRequestException;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import io.micronaut.http.server.exceptions.response.ErrorContext;
import io.micronaut.http.server.exceptions.response.ErrorResponseProcessor;
import jakarta.inject.Singleton;

@Produces
@Singleton
@Requires(classes = {FetchServiceBadRequestException.class, ExceptionHandler.class})
public class FetchServiceBadRequestHandlers
        implements ExceptionHandler<FetchServiceBadRequestException, HttpResponse<?>> {
    private final ErrorResponseProcessor<?> errorResponseProcessor;

    public FetchServiceBadRequestHandlers(ErrorResponseProcessor<?> errorResponseProcessor) {
        this.errorResponseProcessor = errorResponseProcessor;
    }

    @Override
    public HttpResponse<?> handle(HttpRequest request, FetchServiceBadRequestException exception) {
        return errorResponseProcessor.processResponse(
                ErrorContext.builder(request).cause(exception).errorMessage(exception.getMessage()).build(),
                HttpResponse.status(HttpStatus.BAD_REQUEST));
    }
}