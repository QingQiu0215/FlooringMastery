/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringMastery.dao;

import com.sg.flooringMastery.dto.Order;
import com.sg.flooringMastery.include.Config;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
public class OrderDaoImplTest {

    OrderDaoImpl dao;

    public OrderDaoImplTest() {
        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
        appContext.scan("com.sg.flooringMastery");
        appContext.refresh();

        dao = appContext.getBean("orderDaoImpl", OrderDaoImpl.class);
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() throws IOException {
//        Config.ORDER_FILEPATH="testFiles-orderDao/Orders/Orders_";
//        Config.ORDERDATESFILE_FILENAME="testFiles-orderDao/Orders/OrdersName.txt";
//        File testFolder=new File("testFiles-orderDao/Orders");
//        File [] testFiles= testFolder.listFiles();
//        for(File f:testFiles){
//            f.delete();
//        }
//        File seedFolder=new File("seedfiles/Orders");
//        File [] seedFiles= seedFolder.listFiles();
//        for(File f:seedFiles){
//            Files.copy(f.toPath(), Paths.get("testFiles-orderDao/Orders", f.getName()), StandardCopyOption.REPLACE_EXISTING);
//        }
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of addOrder method, of class OrderDaoImpl.
     */
    @Test
    public void testAddOrderAndGetOrderByDateAndLoadFileAndWriteFileAndGetLastOrderNoAndGetOrderByOrderNo() throws Exception {
        Config.ORDER_FILEPATH="testFiles-orderDao/Orders/Orders_";
        Config.ORDERDATESFILE_FILENAME="testFiles-orderDao/Orders/OrdersName.txt";
        Order[] previous = new Order[2];
        previous[0] = new Order(11, "test1", "12122020", new BigDecimal("100"),
                "test", new BigDecimal("100"), new BigDecimal("100"),
                new BigDecimal("100"), new BigDecimal("100"), new BigDecimal("100"),
                new BigDecimal("100"), new BigDecimal("100"));
        previous[1] = new Order(12, "test2", "12122020", new BigDecimal("100"),
                "test", new BigDecimal("100"), new BigDecimal("100"),
                new BigDecimal("100"), new BigDecimal("100"), new BigDecimal("100"),
                new BigDecimal("100"), new BigDecimal("100"));

        dao.addOrder("12122020", previous[0]);
        dao.addOrder("12122020", previous[1]);
        List<Order> fromGet = dao.getOrderByDate("12122020");
        //assert addOrder function(and load, write function)
        assertEquals(3,fromGet.size());

        Order[] after = new Order[2];
        after[0] = fromGet.get(1);
        after[1] = fromGet.get(2);
        //assert getOrderByDate function
        assertArrayEquals(previous, after);

        //assert last order number is a specific date
        assertEquals(previous[1].getOrderNumber(), dao.getLastOrderNo("12122020"));

        //assert getOrderByDateNo function
        assertEquals(previous[1], dao.getOrderByOrderNo(12, fromGet));
    }

    /**
     * Test of addOrderInNewFile method, of class OrderDaoImpl.
     */
    @Test
    public void testAddOrderInNewFileAndWriteOrderInNewFileAndRemoveOrderAndEditOrder() throws Exception {
        Config.ORDER_FILEPATH="testFiles-orderDao/Orders/Orders_";
        Config.ORDERDATESFILE_FILENAME="testFiles-orderDao/Orders/OrdersName.txt";
        Order[] previous = new Order[2];
        previous[0] = new Order(1, "test1", "11112020", new BigDecimal("100"),
                "test", new BigDecimal("100"), new BigDecimal("100"),
                new BigDecimal("100"), new BigDecimal("100"), new BigDecimal("100"),
                new BigDecimal("100"), new BigDecimal("100"));
        previous[1] = new Order(2, "test2", "11112020", new BigDecimal("100"),
                "test", new BigDecimal("100"), new BigDecimal("100"),
                new BigDecimal("100"), new BigDecimal("100"), new BigDecimal("100"),
                new BigDecimal("100"), new BigDecimal("100"));
        //add new order and write in a new order file
        dao.addOrderInNewFile("11112020", previous[0]);
        dao.addOrder("11112020", previous[1]);
        List<Order> fromGet = dao.getOrderByDate("11112020");
        assertTrue(fromGet.size() == 2);

        Order[] after = new Order[2];
        after[0] = fromGet.get(0);
        after[1] = fromGet.get(1);
        assertArrayEquals(previous, after);

        //remove an order in the date
        dao.removeOrder("11112020", 2);
        List<Order> fromGetAfterRemove = dao.getOrderByDate("11112020");
        assertTrue(fromGetAfterRemove.size() == 1);
        assertEquals(previous[0], fromGetAfterRemove.get(0));

        //edit and order
        Order editOrder = new Order(100, "test100", "11112020", new BigDecimal("100"),
                "test", new BigDecimal("100"), new BigDecimal("100"),
                new BigDecimal("100"), new BigDecimal("100"), new BigDecimal("100"),
                new BigDecimal("100"), new BigDecimal("100"));
        Order orderAfterEdit = dao.editOrder("11112020", 100, editOrder);
        assertNotEquals(previous[0], orderAfterEdit);
        assertNotEquals(editOrder, orderAfterEdit);
    }

    /**
     * Test of exportAllData method, of class OrderDaoImpl.
     */
    @Test
    public void testExportAllData() {
        try {
            dao.exportAllData();
        } catch (OrderDaoPersistenceException ex) {
            fail();
        }
    }

    /**
     * Test of getOrderFileList method, of class OrderDaoImpl.
     */
    //************using new file-"testFiles-orderDao-OrderDatesFile", block the other tests
    @Test
    public void testGetOrderDatesListAndloadOrderDatesFileAndwriteOrdersDatesFile() throws Exception {
        Config.ORDER_FILEPATH="testFiles-orderDao-OrderDatesFile/Orders/Orders_";
        Config.ORDERDATESFILE_FILENAME="testFiles-orderDao-OrderDatesFile/Orders/OrdersName.txt";
        String date1 = "01012021";
        String date2 = "01022021";
        dao.writeOrdersDatesFile(date1);
        dao.writeOrdersDatesFile(date2);

        List<String> orderDatesList = dao.getOrderDatesList();
        assertEquals(2,orderDatesList.size());

        assertEquals(date1, orderDatesList.get(0));
        assertEquals(date2, orderDatesList.get(1));
    }

}
