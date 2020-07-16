/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringMastery.include;


import org.springframework.stereotype.Component;

/**
 *
 * @author Qing
 */

public class Regex {
    
    public static final String FORMAT_DATE = "^(0?[1-9]|1[012])[\\/\\-](0?[1-9]|[12][0-9]|3[01])[\\/\\-]\\d{4}$"; 
    public static final String FORMAT_CUSTOMERNAME ="^(?!\\s*$)[-a-zA-Z0-9_:,.' ']{1,100}$";
}
