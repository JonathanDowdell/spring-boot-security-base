package dev.springbootsecuritybase.server.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import dev.springbootsecuritybase.server.exception.model.GlobalException;
import dev.springbootsecuritybase.server.exception.model.GlobalExceptionDto;
import lombok.val;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(value = GlobalException.class)
    public ResponseEntity<Object> handleSocialSettingException(GlobalException exception) {
        val httpStatus = exception.getHttpStatus();
        val message = new GlobalExceptionDto(exception.getMessage());
        return ResponseEntity.status(httpStatus).body(message);
    }

}
