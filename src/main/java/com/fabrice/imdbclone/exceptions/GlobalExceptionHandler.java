package com.fabrice.imdbclone.exceptions;

import com.fabrice.imdbclone.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    //handle not found exception
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) //response status code
    public ErrorResponse handleNotFoundException(NotFoundException exception){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorResponse.setMessage(exception.getMessage());
        return  errorResponse;
    }

    //handling bad request exception
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequestException(BadRequestException exception){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(exception.getMessage());
        return errorResponse;
    }

    //handling unauthorized exception
    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public  ErrorResponse handleUnauthorizedException(UnauthorizedException exception){
        ErrorResponse errorResponse =  new ErrorResponse();
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        return errorResponse;
    }

    // global exceptions => reformatting spring boot exception response
    // for all remaining kind of errors which may occur within our app
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalExceptions(Exception exception){
        String message  =  exception.getMessage();
        Integer statusCode = HttpStatus.BAD_REQUEST.value();
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(message);
        errorResponse.setStatusCode(statusCode);
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }
}
