/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringMastery.dao;

import com.sg.flooringMastery.dto.Product;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Qing
 */
public class ProductDaoMock implements ProductDao{

    List<Product> product = new ArrayList<>();
    
    public ProductDaoMock() {
        product.add(new Product("test1", new BigDecimal("100"),new BigDecimal("100")));
        product.add(new Product("Tile", new BigDecimal("200"),new BigDecimal("200")));
    }
    
    

    @Override
    public List<Product> getAllProduct() throws ProductDaoPersistenceException {
       return product;
    }

    @Override
    public Product getProductbyProductType(String productType) throws ProductDaoPersistenceException {
        for(Product p:product){
            if(p.getProductType().equalsIgnoreCase(productType))
                return p;
        }
        return null;
    }

    @Override
    public void loadFile() throws ProductDaoPersistenceException {
        //do nothing
    }

    @Override
    public void writeFile() throws ProductDaoPersistenceException {
        //do nothing
    }

    @Override
    public void addProduct(Product newProduct) throws ProductDaoPersistenceException {
        //do nothing
    }
    
}
