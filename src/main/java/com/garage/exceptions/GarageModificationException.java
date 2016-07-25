package com.garage.exceptions;

/**
 * @author Ruslan Yaniuk
 * @date July 2016
 */
public class GarageModificationException extends Exception {

    private static final long serialVersionUID = 6343488820945058970L;

    public GarageModificationException() {
        super("Could not modify garage. Maybe, garage is not empty");
    }
}
