package com.example.projetj2E.erreur;

import com.example.projetj2E.models.MessageErreur;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
@ControllerAdvice
@ResponseStatus
public class RestReponseEntityHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(GereExistEmailException.class)
    public ResponseEntity<MessageErreur> GereExistEmailException(GereExistEmailException exception, WebRequest webRequest){
                     MessageErreur message=new MessageErreur(HttpStatus.CONFLICT
                             ,exception.getMessage());

                     return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
    }
}
