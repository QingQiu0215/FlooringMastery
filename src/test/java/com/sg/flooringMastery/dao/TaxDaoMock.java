/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringMastery.dao;

import com.sg.flooringMastery.dto.Product;
import com.sg.flooringMastery.dto.Tax;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Qing
 */
public class TaxDaoMock implements TaxDao{
    
    List<Tax> tax = new ArrayList<>();

    public TaxDaoMock() {
        tax.add(new Tax("test1", "tes1t", new BigDecimal("100")));
        tax.add(new Tax("NY", "tes2t", new BigDecimal("200")));
    }
    
    

    @Override
    public List<Tax> getAllTax() throws TaxDaoPersistenceException {
        return tax;
    }

    @Override
    public Tax getTaxbyState(String state) throws TaxDaoPersistenceException {
        for(Tax t:tax){
            if(t.getStateAbbreviation().equalsIgnoreCase(state))
                return t;
        }
        return null;
    }

    @Override
    public void addTax(Tax newTax) throws TaxDaoPersistenceException {
        //do nothing
    }

    @Override
    public void loadFile() throws TaxDaoPersistenceException {
        //do nothing
    }

    @Override
    public void writeFile() throws TaxDaoPersistenceException {
        //do nothing
    }
    
}
