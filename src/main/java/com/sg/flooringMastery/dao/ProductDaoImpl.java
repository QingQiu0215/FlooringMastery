/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringMastery.dao;

import com.sg.flooringMastery.dto.Product;
import com.sg.flooringMastery.dto.Tax;
import com.sg.flooringMastery.include.Config;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Qing
 */
@Component
public class ProductDaoImpl implements ProductDao {

    @Autowired
    List<Product> product = new ArrayList<>();

    @Override
    public List<Product> getAllProduct() throws ProductDaoPersistenceException {
        loadFile();
        return product;
    }

    @Override
    public Product getProductbyProductType(String productType) throws ProductDaoPersistenceException {
        loadFile();
        for (Product p : product) {
            if (p.getProductType().equalsIgnoreCase(productType)) {
                return p;
            }
        }
        return null;
    }

    @Override
    public void loadFile() throws ProductDaoPersistenceException {
        Scanner sc = null;
        try {
            sc = new Scanner(new File(Config.PRODUCT_FILENAME));
        } catch (FileNotFoundException ex) {
            throw new ProductDaoPersistenceException("Error loading file");
        }

        product = new ArrayList<>();
        String line = sc.nextLine();
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            String parts[] = line.split(Config.DELIMITER);
            if (parts.length == 3) {
                //serialNo, title, releaseDate, ratingMPAA,directorName,studio,userNote;

                Product p = new Product();
                p.setProductType(parts[0]);
                p.setCostPerSquareFoot(new BigDecimal(parts[1]));
                p.setLaborCostPerSquareFoot(new BigDecimal(parts[2]));

                product.add(p);
            }
        }
        sc.close();
    }

    @Override
    public void writeFile() throws ProductDaoPersistenceException {

        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileWriter(Config.PRODUCT_FILENAME));
        } catch (IOException ex) {
            throw new ProductDaoPersistenceException("Error writing to file");
        }
        pw.append("ProductType " + Config.DELIMITER
                + "CostPerSquareFoot " + Config.DELIMITER
                + "LaborCostPerSquareFoot "
                + "\n");
        for (Product p : product) {
            pw.append(p.getProductType() + Config.DELIMITER
                    + p.getCostPerSquareFoot() + Config.DELIMITER
                    + p.getLaborCostPerSquareFoot() + "\n");
        }

        pw.flush();
        pw.close();
    }

    @Override
    public void addProduct(Product newProduct) throws ProductDaoPersistenceException {
        loadFile();
        product.add(newProduct);
        writeFile();
    }

}
