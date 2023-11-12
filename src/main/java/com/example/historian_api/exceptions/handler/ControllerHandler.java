package com.example.historian_api.exceptions.handler;

import com.example.historian_api.exceptions.*;
import com.example.historian_api.exceptions.auth.AlreadyLoginPhoneWithAnotherDeviceException;
import com.example.historian_api.exceptions.auth.NotFoundPhoneNumberLoginException;
import com.example.historian_api.exceptions.auth.UsedPhoneRegisterException;
import com.example.historian_api.factories.impl.*;
import com.example.historian_api.models.ApiCustomResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@ResponseBody
@RequiredArgsConstructor
public class ControllerHandler {

    private final ResponseFactory400 badRequestFactory;
    private final ResponseFactory404 notFoundFactory;
    private final ResponseFactory401 unAuthorizedFactory;
    private final ResponseFactory403 forbiddenFactory;

    @ExceptionHandler(NotValidSubscriptionCardException.class)
    public ResponseEntity<?> handleNotValidSubscriptionCardException(NotValidSubscriptionCardException e){
        var response = badRequestFactory.createResponse(e.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(NotFoundStudentException.class)
    public ResponseEntity<?> handleNotFoundStudentException(NotFoundStudentException e){
        var response = badRequestFactory.createResponse(e.getMessage());
        return ResponseEntity.status(405).body(response);
    }

    @ExceptionHandler(AlreadyEnrolledCourseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleAlreadyEnrolledCourseException(AlreadyEnrolledCourseException e){
        var response = badRequestFactory.createResponse(e.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleBadRequestException(BadRequestException exception) {
        return ResponseEntity
                .badRequest()
                .body(badRequestFactory.createResponse(exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> notValid(MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();

        ex.getAllErrors().forEach(err -> errors.add(err.getDefaultMessage()));

        var responseBody = ApiCustomResponse
                .builder()
                .message("Not Valid request inputs !!")
                .statusCode(400)
                .isSuccess(false)
                .data(errors)
                .build();

        return ResponseEntity.badRequest().body(responseBody);
    }

    @ExceptionHandler(AlreadySolvedQuizException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiCustomResponse<?>> handleAlreadySolvedQuizException(
            AlreadySolvedQuizException e
    ) {
        return ResponseEntity.badRequest()
                .body(badRequestFactory.createResponse(e.getMessage()));
    }

    @ExceptionHandler(MismatchPasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiCustomResponse<?>> handleMismatchPasswordException(
            MismatchPasswordException e
    ) {
        var response = badRequestFactory.createResponse(e.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiCustomResponse<?>> handleUsernameNotFoundException(
            UsernameNotFoundException e
    ) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value())
                .body(notFoundFactory.createResponse(e.getMessage()));
    }

    @ExceptionHandler(UsedPhoneRegisterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiCustomResponse<?>> handleUsedPhoneRegisterException(
            UsedPhoneRegisterException e
    ) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
                .body(badRequestFactory.createResponse(e.getMessage()));
    }

    @ExceptionHandler(NotFoundNotificationException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiCustomResponse<?>> handleNotificationNotFoundException(NotFoundNotificationException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value())
                .body(badRequestFactory.createResponse(e.getMessage()));
    }


    // redirect to register
    @ExceptionHandler(NotFoundPhoneNumberLoginException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiCustomResponse<?>> handleNotFoundPhoneNumberLoginException(
            NotFoundPhoneNumberLoginException e
    ) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value())
                .body(notFoundFactory.createResponse(e.getMessage()));
    }


    @ExceptionHandler(AlreadyLoginPhoneWithAnotherDeviceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiCustomResponse<?>> handleAlreadyLoginPhoneWithAnotherDeviceException(
            AlreadyLoginPhoneWithAnotherDeviceException e
    ) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
                .body(badRequestFactory.createResponse(e.getMessage()));
    }

    @ExceptionHandler(NotFoundAuthenticatedUserException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ApiCustomResponse<?>> handleNotFoundAuthenticatedUserException(
            NotFoundAuthenticatedUserException e
    ) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value())
                .body(unAuthorizedFactory.createResponse(e.getMessage()));
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ApiCustomResponse<?>> handleGlobalUnAuthenticatedException(
            AuthenticationException e
    ) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value())
                .body(unAuthorizedFactory.createResponse("You are UN_AUTHORIZED of accessing this resource!!"));
    }


    @ExceptionHandler(NotFoundResourceException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiCustomResponse<?>> handleNotFoundResourceException(
            NotFoundResourceException e
    ) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value())
                .body(notFoundFactory.createResponse(e.getMessage()));
    }
}
