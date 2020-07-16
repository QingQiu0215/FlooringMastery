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
public class ProductNotExistException extends Exception{

    public ProductNotExistException(String message) {
        super(message);
    }

    public ProductNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
    
    
    
}
