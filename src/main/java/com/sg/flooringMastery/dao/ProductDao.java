/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringMastery.dao;

import com.sg.flooringMastery.dto.Product;
import java.util.List;

/**
 *
 * @author Qing
 */
public interface ProductDao {
    
     //list the Machine in the collection
    public List<Product> getAllProduct() throws ProductDaoPersistenceException;

    //display the information for a particular Product
    public Product getProductbyProductType(String productType) throws ProductDaoPersistenceException;

    public void loadFile() throws ProductDaoPersistenceException;

    //write the Product to a file
    public void writeFile() throws ProductDaoPersistenceException;

    public void addProduct(Product newProduct) throws ProductDaoPersistenceException;
    
}
