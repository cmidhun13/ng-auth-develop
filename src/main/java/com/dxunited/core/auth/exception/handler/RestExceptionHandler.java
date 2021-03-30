package com.dxunited.core.auth.exception.handler;


//import com.szells.common.model.Error;

import com.dxunited.core.auth.constant.AuthConstants;
import com.dxunited.core.auth.exception.*;
import com.dxunited.core.auth.model.ErrorResponse;
import com.dxunited.core.auth.util.ErrorLogDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;
import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;
import static org.springframework.http.HttpStatus.*;

//import com.szells.gce.core.utilities.loggingService.CoreLogger;

@RestControllerAdvice
@RequiredArgsConstructor
@Order(HIGHEST_PRECEDENCE)
@Log4j2
public class RestExceptionHandler {

//    private final CoreLogger coreLogger;

    private final ErrorLogDetails errorLogDetails;

    private final ObjectMapper objectMapper;

    private final Properties errorProperties;

    @ExceptionHandler(value = HttpHeaderException.class)
    @ResponseStatus(PRECONDITION_FAILED)
    public ErrorResponse handleHttpHeaderException(HttpHeaderException ex, WebRequest request) {
        Error error = getError(ex);
        logExceptionDetails(request, ex, error.getMessage());
        return ErrorResponse.builder()
                .context("Invalid Request Headers")
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(UNAUTHORIZED)
    public ErrorResponse handleUserAuthenticationException(UserAuthenticationException ex, WebRequest request) {
        Error error = convert(errorProperties.getProperty(ex.getCode()));
        logExceptionDetails(request, ex, error.getMessage());
        return ErrorResponse.builder()
                .failCount(ex.getFailCount())
                .context("Login Failed")
                .build();
    }

    @ExceptionHandler(value = AuthServiceException.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorResponse handleAuthServiceException(AuthServiceException ex, WebRequest request) {
        Error error = getError(ex);
        logExceptionDetails(request, ex, error.getMessage());
        return ErrorResponse.builder()
                .context("Server Error")
                .message(ex.getMessage())
                .build();
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handleInvalidRequestBody(MethodArgumentNotValidException ex, WebRequest request) {
        List<Error> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> String.format(
                        requireNonNull(fieldError.getDefaultMessage()),
                        fieldError.getField())
                )
                .map(this::convert)
                .collect(toList());
        logExceptionDetails(request, ex,
                errors.stream().map(Error::getMessage).collect(Collectors.joining(",")));
        return ErrorResponse.builder()
                .context("Invalid Request Body")
                .build();
    }

    private Error getError(AuthServiceException ex) {
        String errorMessage = String.format(errorProperties.getProperty(ex.getCode()), ex.getAdditionalParams());
        return convert(errorMessage);
    }

    private Error convert(String error) {
        try {
            return objectMapper.readValue(error, Error.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void logExceptionDetails(WebRequest request, Exception e, String message) {
        String correlationId = request.getHeader(AuthConstants.X_CORRELATION_ID);
        String tenantId = request.getHeader(AuthConstants.TENANT_ID);
//        coreLogger.logException(message + " " + tenantId, e, errorLogDetails.getExceptionDetails(correlationId));
    }

    @ExceptionHandler(HttpClientErrorException.class)
    protected ResponseEntity<ErrorResponse> handleHttpClientErrorException(HttpClientErrorException ex, WebRequest request) {
        logExceptionDetails(request, ex, ex.getMessage());
//        Error error = new Error("3010", "Error occurred while processing request. " + ex.getMessage());
        return new ResponseEntity<>(ErrorResponse.builder()
                .context("Request Failed")
                .build(), ex.getStatusCode());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorResponse handleRuntimeException(RuntimeException ex, WebRequest request) {
        logExceptionDetails(request, ex, ex.getMessage());
        return ErrorResponse.builder()
                .context("Something went wrong")
                .build();
    }

    @ExceptionHandler(LogoutException.class)
    @ResponseStatus(BAD_REQUEST)
    protected ErrorResponse handleLogoutException(LogoutException ex, WebRequest request) {
        logExceptionDetails(request, ex, "Error while invalidating user session/access token");
        return ErrorResponse.builder()
                .context("Logout Failed")
                .build();
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
        Error error = getError(ex);
        logExceptionDetails(request, ex, error.getMessage());
        return ErrorResponse.builder()
                .context("Request Failed")
                .build();
    }
}
