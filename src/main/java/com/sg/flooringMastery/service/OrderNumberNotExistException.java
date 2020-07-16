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
public class OrderNumberNotExistException extends Exception{

    public OrderNumberNotExistException(String message) {
        super(message);
    }

    public OrderNumberNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
    
    
}
