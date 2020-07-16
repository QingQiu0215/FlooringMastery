/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringMastery.dao;

import com.sg.flooringMastery.dto.Order;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Qing
 */
public class OrderDaoMock implements OrderDao {

    List<Order> order = new ArrayList<>();

    List<String> orderDatesList = new ArrayList<>();

    public OrderDaoMock() {
        orderDatesList.add("06012013");
        order.add(new Order(1, "test1", "06012013", new BigDecimal("100"),
                "test", new BigDecimal("100"), new BigDecimal("100"),
                new BigDecimal("100"), new BigDecimal("100"), new BigDecimal("100"),
                new BigDecimal("100"), new BigDecimal("100")));
        order.add(new Order(2, "test2", "06012013", new BigDecimal("100"),
                "test", new BigDecimal("100"), new BigDecimal("100"),
                new BigDecimal("100"), new BigDecimal("100"), new BigDecimal("100"),
                new BigDecimal("100"), new BigDecimal("100")));
    }

    @Override
    public int getLastOrderNo(String date) throws OrderDaoPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Order addOrder(String date, Order newOrder) throws OrderDaoPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Order addOrderInNewFile(String date, Order newOrder) throws OrderDaoPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Order removeOrder(String date, int orderNo) throws OrderDaoPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Order editOrder(String date, int orderNo, Order newOrder) throws OrderDaoPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Order> getOrderByDate(String date) throws OrderDaoPersistenceException {
        return order;
                
    }

    @Override
    public Order getOrderByOrderNo(int orderNo, List<Order> ordersInTheDate) throws OrderDaoPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void loadFile(String date) throws OrderDaoPersistenceException {
        //do nothing
    }

    @Override
    public void writeFile(String date) throws OrderDaoPersistenceException {
        //do nothing
    }

    @Override
    public List<String> getOrderDatesList() {
        return orderDatesList;
    }

    @Override
    public void loadOrderDatesFile() throws OrderDaoPersistenceException {
        //do nothing
    }

    @Override
    public void writeAllOrdersInBackUpFileList() throws OrderDaoPersistenceException {
        //do nothing
    }

    @Override
    public void writeOrdersDatesFile(String date) throws OrderDaoPersistenceException {
        //do nothing
    }

    @Override
    public void exportAllData() throws OrderDaoPersistenceException {
        //do nothing
    }

}
