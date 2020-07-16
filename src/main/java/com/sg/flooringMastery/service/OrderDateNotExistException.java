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
public class OrderDateNotExistException extends Exception{

    public OrderDateNotExistException(String message) {
        super(message);
    }

    public OrderDateNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
    
    
}
