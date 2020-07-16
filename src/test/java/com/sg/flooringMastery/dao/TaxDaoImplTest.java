/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringMastery.dao;

import com.sg.flooringMastery.controller.FlooringMasteryController;
import com.sg.flooringMastery.dto.Tax;
import com.sg.flooringMastery.include.Config;
import java.io.File;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
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
public class TaxDaoImplTest {

    TaxDaoImpl dao;

    public TaxDaoImplTest() {
        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
        appContext.scan("com.sg.flooringMastery");
        appContext.refresh();

        dao = appContext.getBean("taxDaoImpl", TaxDaoImpl.class);

    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() throws Exception{
        File file = new File("testFiles-taxDao,productDao/Data/Taxes.txt");
        PrintWriter writer = new PrintWriter(file);
        writer.append("StateAbbreviation" + Config.DELIMITER
                + "StateName" + Config.DELIMITER
                + "TaxRate"
                + "\n"
        );
        writer.append("TX" + Config.DELIMITER
                + "Texas" + Config.DELIMITER
                + "4.45"
                + "\n"
        );

        writer.flush();
        writer.close();
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getAllTax method, of class TaxDaoImpl.
     */
    @Test
    public void testAddTaxAndGetAllTaxAndLoadFileAndWriteFile() throws Exception {

        Config.TAX_FILENAME = "testFiles-taxDao,productDao/Data/Taxes.txt";
        Tax[] previous = new Tax[2];
        previous[0] = new Tax("TT1", "tes1t", new BigDecimal("100"));
        previous[1] = new Tax("TT2", "test2", new BigDecimal("200"));

        dao.addTax(previous[0]);
        dao.addTax(previous[1]);
        List<Tax> test = dao.getAllTax();
        assertEquals(3, test.size());

        Tax[] after = new Tax[2];
        after[0] = test.get(1);
        after[1] = test.get(2);
        assertArrayEquals(previous, after);

        Tax previous2 = new Tax("TT3", "test3", new BigDecimal("300"));
        dao.addTax(previous2);
        Tax after2 = dao.getTaxbyState("TT3");
        assertEquals(previous2, after2);
    }

    /**
     * Test of getTaxbyState method, of class TaxDaoImpl.
     */
    @Test
    public void testGetTaxbyState() throws Exception {
        Config.TAX_FILENAME = "testFiles-taxDao,productDao/Data/Taxes.txt";
        System.out.println("GetTaxbyState");
        Tax previous = new Tax("TT3", "test3", new BigDecimal("300"));
        dao.addTax(previous);
        Tax after = dao.getTaxbyState("TT3");
        assertEquals(previous, after);
    }

}
