/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringMastery.dao;

import com.sg.flooringMastery.dto.Product;
import com.sg.flooringMastery.dto.Tax;
import java.util.List;

/**
 *
 * @author Qing
 */
public interface TaxDao {
    
     //list the Machine in the collection
    public List<Tax> getAllTax() throws TaxDaoPersistenceException;

    //display the information for a particular Product
    public Tax getTaxbyState(String state) throws TaxDaoPersistenceException;
    
    public void addTax(Tax newTax)  throws TaxDaoPersistenceException;

    public void loadFile() throws TaxDaoPersistenceException;
    
    public void writeFile() throws TaxDaoPersistenceException;
    
}
