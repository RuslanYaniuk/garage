package com.garage.controllers;

import com.garage.dto.ResponseDTO;
import com.garage.exceptions.GarageConfigurationException;
import com.garage.exceptions.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author Ruslan Yaniuk
 * @date October 2015
 */
@ControllerAdvice
public class ExceptionController extends AbstractController {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity handleValidationException(ValidationException e) {
        return badRequest(new ResponseDTO<>(e.getClass().getSimpleName(), null));
    }

    @ExceptionHandler(GarageConfigurationException.class)
    public ResponseEntity handleValidationException(GarageConfigurationException e) {
        return badRequest(new ResponseDTO<>(e.getClass().getSimpleName(), null));
    }
/*
    @ExceptionHandler({
            EmailAlreadyTakenException.class,
            LoginAlreadyTakenException.class,})
    public ResponseEntity handleBadRequest(Exception e, Locale locale) {
        String message = customMessageSource.getMessage(e.getMessage(), null, locale);
        MessageDTO messageDTO = new MessageDTO();
        FieldError error;
        String fieldName;
        String errorCode;

        if (e.getClass() == EmailAlreadyTakenException.class) {
            fieldName = "email";
            errorCode = "TakenEmail";
        } else {
            fieldName = "login";
            errorCode = "TakenLogin";
        }
        error = new FieldError(UserRegistrationDTO.class.getSimpleName(),
                fieldName, null, false, new String[]{errorCode}, null, message);
        messageDTO.setType(e.getClass().getSimpleName());
        messageDTO.getErrors().add(error);
        return badRequest(messageDTO);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity handleValidationException(ValidationException e) {
        MessageDTO messageDTO = new MessageDTO();

        messageDTO.setErrors(e.getErrors().getAllErrors());
        messageDTO.setType(ValidationException.class.getSimpleName());
        return badRequest(messageDTO);
    }

    @ExceptionHandler(OperationNotPermitted.class)
    public ResponseEntity handleForbidden(OperationNotPermitted e) {
        return messageForbidden(e.getMessage());
    }

    @ExceptionHandler(InterruptedException.class)
    public ResponseEntity handleInternalError(Exception e) {
        return messageInternalServerError(e.getMessage());
    }

    @ExceptionHandler(SearchFieldsAreEmpty.class)
    public ResponseEntity handleSearchRequestError(Exception e) {
        return messageNotAcceptable(e.getMessage());
    }*/
}
