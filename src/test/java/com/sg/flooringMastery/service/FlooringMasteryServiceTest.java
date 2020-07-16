/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringMastery.service;

import com.sg.flooringMastery.dao.OrderDaoImpl;
import com.sg.flooringMastery.dao.OrderDaoMock;
import com.sg.flooringMastery.dao.ProductDaoMock;
import com.sg.flooringMastery.dao.TaxDaoMock;
import com.sg.flooringMastery.dto.Order;
import com.sg.flooringMastery.dto.Product;
import com.sg.flooringMastery.dto.Tax;
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
public class FlooringMasteryServiceTest {

    FlooringMasteryService service;

    public FlooringMasteryServiceTest() {


        service = new FlooringMasteryService(new OrderDaoMock(),new ProductDaoMock(),new TaxDaoMock());
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of checkOrderDateExist method, of class FlooringMasteryService.
     */
    @Test
    public void testCheckOrderDateExist() throws Exception {
        try {
            //good pass
            service.checkOrderDateExist("06012013");
        } catch (OrderDateNotExistException ex) {
            fail();
        }

        try {
            //bad pass
            service.checkOrderDateExist("06012022");
            fail("fail on not existing in dates list");
        } catch (OrderDateNotExistException ex) {

        }
    }

    /**
     * Test of checkOrderNoExist method, of class FlooringMasteryService.
     */
    @Test
    public void testCheckOrderNoExist() throws Exception {
        try {
            //good pass
            service.checkOrderNoExist(1, "06012013");
        } catch (OrderNumberNotExistException ex) {
            fail();
        }

        try {
            //bad pass
            service.checkOrderNoExist(100, "06012013");
            fail("fail on not existing in dates list");
        } catch (OrderNumberNotExistException ex) {

        }
    }

    /**
     * Test of checkDuplicateProduct method, of class FlooringMasteryService.
     */
    @Test
    public void testCheckDuplicateProduct() throws Exception {
        Product good = new Product("Good", new BigDecimal("1"), new BigDecimal("1"));
        Product bad = new Product("Tile", new BigDecimal("1"), new BigDecimal("1"));
        try {
            //good pass
            service.checkDuplicateProduct(good);
        } catch (ProductTypeDuplicatedException ex) {
            fail();
        }

        try {
            //bad pass
            service.checkDuplicateProduct(bad);
            fail();
        } catch (ProductTypeDuplicatedException ex) {

        }
    }

    /**
     * Test of checkProductExist method, of class FlooringMasteryService.
     */
    @Test
    public void testCheckProductExist() throws Exception {
        try {
            //good pass
            service.checkProductExist("Tile");
        } catch (ProductNotExistException ex) {
            fail();
        }

        try {
            //bad pass
            service.checkProductExist("bad");
            fail();
        } catch (ProductNotExistException ex) {

        }
    }

    /**
     * Test of checkDuplicateTax method, of class FlooringMasteryService.
     */
    @Test
    public void testCheckDuplicateTax() throws Exception {
        Tax good = new Tax("Good", "test", new BigDecimal("1"));
        Tax bad = new Tax("NY", "test", new BigDecimal("1"));
        try {
            //good pass
            service.checkDuplicateTax(good);
        } catch (StateAbbrDuplicatedException ex) {
            fail();
        }

        try {
            //bad pass
            service.checkDuplicateTax(bad);
            fail();
        } catch (StateAbbrDuplicatedException ex) {

        }
    }

    /**
     * Test of checkTaxExist method, of class FlooringMasteryService.
     */
    @Test
    public void testCheckTaxExist() throws Exception {
        try {
            //good pass
            service.checkTaxExist("NY");
        } catch (TaxNotExistException ex) {
            fail();
        }

        try {
            //bad pass
            service.checkTaxExist("bad");
            fail();
        } catch (TaxNotExistException ex) {

        }
    }

}
