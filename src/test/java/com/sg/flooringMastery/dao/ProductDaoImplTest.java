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
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *
 * @author Qing
 */
public class ProductDaoImplTest {
    
    ProductDaoImpl dao;
    public ProductDaoImplTest() {
        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
        appContext.scan("com.sg.flooringMastery");
        appContext.refresh();

        dao = appContext.getBean("productDaoImpl", ProductDaoImpl.class);
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() throws Exception {
        File file=new File("testFiles-taxDao,productDao/Data/Products.txt");
        PrintWriter writer = new PrintWriter(file);
        writer.append("ProductType " + Config.DELIMITER
                + "CostPerSquareFoot " + Config.DELIMITER
                + "LaborCostPerSquareFoot "
                + "\n");
        writer.append("Carpet" + Config.DELIMITER
                    + "2.25" + Config.DELIMITER
                    + "2.10" + "\n");
        
        writer.flush();
        writer.close();
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getAllProduct method, of class ProductDaoImpl.
     */
    @Test
    public void testAddProductAndGetAllProductAndLoadFileAndWriteFileAndGetProductbyProductType() throws Exception {
    

        Config.PRODUCT_FILENAME="testFiles-taxDao,productDao/Data/Products.txt";
        Product[] previous = new Product[2];
        previous[0] = new Product("test1", new BigDecimal("100"),new BigDecimal("100"));
        previous[1] = new Product("test2", new BigDecimal("200"),new BigDecimal("200"));

        dao.addProduct(previous[0]);
        dao.addProduct(previous[1]);
        List<Product> test = dao.getAllProduct();
        assertEquals(3,test.size());

        Product[] after = new Product[2];
        after[0] = test.get(1);
        after[1] = test.get(2);
        assertArrayEquals(previous, after);
        
        Product previous2=new Product("test3", new BigDecimal("300"),new BigDecimal("300"));
        dao.addProduct(previous2);
        Product after2=dao.getProductbyProductType("test3");
        assertEquals(previous2,after2);
    }

    /**
     * Test of getProductbyProductType method, of class ProductDaoImpl.
     */
    @Test
    public void testGetProductbyProductType() throws Exception {
        Config.PRODUCT_FILENAME="testFiles-taxDao,productDao/Data/Products.txt";
        Product previous=new Product("test3", new BigDecimal("300"),new BigDecimal("300"));
        dao.addProduct(previous);
        Product after=dao.getProductbyProductType("test3");
        assertEquals(previous,after);
    }

    
}


