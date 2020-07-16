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
public class Product {

    private String productType;
    private BigDecimal CostPerSquareFoot;
    private BigDecimal LaborCostPerSquareFoot;

    public Product() {
    }

    public Product(String productType, BigDecimal CostPerSquareFoot, BigDecimal LaborCostPerSquareFoot) {
        this.productType = productType;
        this.CostPerSquareFoot = CostPerSquareFoot;
        this.LaborCostPerSquareFoot = LaborCostPerSquareFoot;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public BigDecimal getCostPerSquareFoot() {
        return CostPerSquareFoot;
    }

    public void setCostPerSquareFoot(BigDecimal CostPerSquareFoot) {
        this.CostPerSquareFoot = CostPerSquareFoot;
    }

    public BigDecimal getLaborCostPerSquareFoot() {
        return LaborCostPerSquareFoot;
    }

    public void setLaborCostPerSquareFoot(BigDecimal LaborCostPerSquareFoot) {
        this.LaborCostPerSquareFoot = LaborCostPerSquareFoot;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.productType);
        hash = 61 * hash + Objects.hashCode(this.CostPerSquareFoot);
        hash = 61 * hash + Objects.hashCode(this.LaborCostPerSquareFoot);
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
        final Product other = (Product) obj;
        if (!Objects.equals(this.productType, other.productType)) {
            return false;
        }
        if (!Objects.equals(this.CostPerSquareFoot, other.CostPerSquareFoot)) {
            return false;
        }
        if (!Objects.equals(this.LaborCostPerSquareFoot, other.LaborCostPerSquareFoot)) {
            return false;
        }
        return true;
    }

}
