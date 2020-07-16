/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringMastery.dto;

import java.math.BigDecimal;
import java.util.Objects;

import org.springframework.stereotype.Component;

/**
 *
 * @author Qing
 */
@Component
public class Tax {

    private String stateAbbreviation;
    private String StateName;
    private BigDecimal TaxRate;

    public Tax() {
    }

    public Tax(String stateAbbreviation, String StateName, BigDecimal TaxRate) {
        this.stateAbbreviation = stateAbbreviation;
        this.StateName = StateName;
        this.TaxRate = TaxRate;
    }

    public String getStateAbbreviation() {
        return stateAbbreviation;
    }

    public void setStateAbbreviation(String stateAbbreviation) {
        this.stateAbbreviation = stateAbbreviation;
    }

    public String getStateName() {
        return StateName;
    }

    public void setStateName(String StateName) {
        this.StateName = StateName;
    }

    public BigDecimal getTaxRate() {
        return TaxRate;
    }

    public void setTaxRate(BigDecimal TaxRate) {
        this.TaxRate = TaxRate;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.stateAbbreviation);
        hash = 89 * hash + Objects.hashCode(this.StateName);
        hash = 89 * hash + Objects.hashCode(this.TaxRate);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Tax other = (Tax) obj;
        if (!Objects.equals(this.stateAbbreviation, other.stateAbbreviation)) {
            return false;
        }
        if (!Objects.equals(this.StateName, other.StateName)) {
            return false;
        }
        if (!Objects.equals(this.TaxRate, other.TaxRate)) {
            return false;
        }
        return true;
    }
    
    

}
