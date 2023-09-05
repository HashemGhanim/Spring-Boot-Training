package com.hashem.restdemo.validation;


import com.hashem.restdemo.exception.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String , String > handleMethodArgumentException(MethodArgumentNotValidException exception){
        Map<String , String > errorMap = new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(error ->{
            errorMap.put(error.getField() , error.getDefaultMessage());
        });

        return errorMap;
    }


    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String , String > emailIsExistExceptionHandler(BadRequestException exception){
        Map<String , String > errorMap = new HashMap<>();
        errorMap.put("Email Error" , exception.getMessage());
        return errorMap;
    }


    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String , String > userNotFound(UsernameNotFoundException exception){
        Map<String , String > errorMap = new HashMap<>();
        errorMap.put("UserNotFoundError" , exception.getMessage());

        return errorMap;
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String ,String> notAuthorize(AuthenticationException exception){
        Map<String , String > errorMap = new HashMap<>();
        errorMap.put("Error : " , exception.getMessage());
        return errorMap;
    }
}
