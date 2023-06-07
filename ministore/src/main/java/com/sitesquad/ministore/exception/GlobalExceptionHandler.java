package com.sitesquad.ministore.exception;

import com.sitesquad.ministore.model.ResponseObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@CrossOrigin

public class GlobalExceptionHandler {


    @ExceptionHandler
    public ResponseEntity handleAccessDeniedException(AccessDeniedException e){
        ResponseObject responseObject = new ResponseObject();
        responseObject.setStatus(HttpStatus.UNAUTHORIZED.value());
        responseObject.setMessage("Access Denied");
        e.printStackTrace();
        return ResponseEntity.status(responseObject.getStatus()).body(responseObject);
    }

    @ExceptionHandler
    public ResponseEntity handleBadRequestException (BadRequestException e) {
        ResponseObject responseObject = new ResponseObject();
        responseObject.setStatus(HttpStatus.BAD_REQUEST.value());
        responseObject.setMessage(e.getErrors().toString());
        e.printStackTrace();
        return ResponseEntity.status(400).body(responseObject);
    }

    @ExceptionHandler
    public ResponseEntity handleException(Exception e){
        e.printStackTrace();
        ResponseObject responseObject = new ResponseObject();
        responseObject.setMessage("Oops..Something went wrong!");
        responseObject.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(responseObject);
    }

}
