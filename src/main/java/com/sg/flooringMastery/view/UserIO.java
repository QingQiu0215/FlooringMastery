/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringMastery.view;

import java.math.BigDecimal;

/**
 *
 * @author Qing
 */
public interface UserIO {

    void print(String message);
    
    void printFormat(String orderNumber, String customerName, String state, String taxRate, 
            String productType, String area, String costPerSquareFoot, 
            String laborCostPerSquareFoot, String materialCost, String laborCost, 
            String tax, String total);

    String readString(String prompt);

    int readInt(String prompt);

    int readInt(String prompt, int min, int max);

    double readDouble(String prompt);

    double readDouble(String prompt, double min, double max);

    float readFloat(String prompt);

    float readFloat(String prompt, float min, float max);

    long readLong(String prompt);

    long readLong(String prompt, long min, long max);
    
    public BigDecimal readBigDecimal(String prompt);
    
    public String readBigDecimalForEdit(String prompt,String oldProperty);

}
