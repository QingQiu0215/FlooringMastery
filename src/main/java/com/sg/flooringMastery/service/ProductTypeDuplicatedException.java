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
public class ProductTypeDuplicatedException extends Exception{

    public ProductTypeDuplicatedException(String message) {
        super(message);
    }

    public ProductTypeDuplicatedException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
