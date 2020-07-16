/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringMastery.service;

/**
 *
 * @author Qing
 */
public class TaxNotExistException extends Exception{

    public TaxNotExistException(String message) {
        super(message);
    }

    public TaxNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    
}
