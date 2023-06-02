package com.sitesquad.ministore.exception;

import com.sitesquad.ministore.model.ResponseObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity handleAccessDeniedException(AccessDeniedException e){
        ResponseObject responseObject = new ResponseObject();
        responseObject.setStatus(HttpStatus.UNAUTHORIZED.value());
        return ResponseEntity.status(responseObject.getStatus()).body(responseObject);
    }
}
