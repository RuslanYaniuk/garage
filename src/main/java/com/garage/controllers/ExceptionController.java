package com.garage.controllers;

import com.garage.dto.ResponseDTO;
import com.garage.exceptions.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author Ruslan Yaniuk
 * @date July 2016
 */
@ControllerAdvice
public class ExceptionController extends AbstractController {

    @ExceptionHandler({
            ValidationException.class,
            GarageNotFoundException.class,
            GarageConfigurationException.class,
            GarageModificationException.class,
            NoParkingLotsAreAvailableException.class,
            VehicleAlreadyInGarageException.class,
            VehicleNotFoundException.class
    })
    public ResponseEntity handle(Exception e) {
        return badRequest(new ResponseDTO<>(e.getClass().getSimpleName(), null));
    }
}
