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
    public ResponseEntity<MessageErreur> GereExistEmailException(GereExistEmailException exception){
                     MessageErreur message=new MessageErreur(HttpStatus.CONFLICT
                             ,exception.getMessage());

                     return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
    }

    @ExceptionHandler(HandleIncorrectAuthentification.class)
    public ResponseEntity<MessageErreur> HandleIncorrectAuthentification(HandleIncorrectAuthentification exception){

        MessageErreur message=new MessageErreur(HttpStatus.UNAUTHORIZED
                ,exception.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
    }
    @ExceptionHandler(GereMedecinNotFound.class)
    public ResponseEntity<MessageErreur> GereMedecinNotFound( GereMedecinNotFound exception){

        MessageErreur message=new MessageErreur(HttpStatus.NOT_FOUND
                ,exception.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<MessageErreur> UserNotFoundException( UserNotFoundException exception){

        MessageErreur message=new MessageErreur(HttpStatus.NOT_FOUND
                ,exception.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    @ExceptionHandler(RendezVousNotFound.class)
    public ResponseEntity<MessageErreur> RendezVousNotFound( RendezVousNotFound exception){

        MessageErreur message=new MessageErreur(HttpStatus.NOT_FOUND
                ,exception.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }
    @ExceptionHandler(RendezVousExisteDeja.class)
    public ResponseEntity<MessageErreur>  RendezVousExisteDeja(RendezVousExisteDeja exception){

        MessageErreur message=new MessageErreur(HttpStatus.CONFLICT
                ,exception.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
    }

}
