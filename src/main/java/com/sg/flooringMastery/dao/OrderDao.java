/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringMastery.dao;

import com.sg.flooringMastery.dto.Order;
import java.util.List;

/**
 *
 * @author Qing
 */
public interface OrderDao {
    
    public int getLastOrderNo(String date) throws OrderDaoPersistenceException;

    //add a DVD to the collection
    public Order addOrder(String date,Order newOrder) throws OrderDaoPersistenceException;
    
    public Order addOrderInNewFile(String date, Order newOrder) throws OrderDaoPersistenceException;

    //remove a DVD from the collection
    public Order removeOrder(String date,int orderNo) throws OrderDaoPersistenceException;

    //edit the information for an existing DVD in the collection
    public Order editOrder(String date,int orderNo, Order newOrder) throws OrderDaoPersistenceException;

    //list the DVDs in the collection
//    public List<Order> getAllFileOrders() throws OrderDaoPersistenceException;

    //display the information for a particular DVD
    public List<Order> getOrderByDate(String date) throws OrderDaoPersistenceException;

    public Order getOrderByOrderNo(int orderNo,List<Order> ordersInTheDate) throws OrderDaoPersistenceException;
    
    //load a specific file 
    public void loadFile(String date) throws OrderDaoPersistenceException;

    //write the Order to a file
    public void writeFile(String date) throws OrderDaoPersistenceException;
    
    public List<String> getOrderDatesList();
    
    public void loadOrderDatesFile() throws OrderDaoPersistenceException;
    
    public void writeAllOrdersInBackUpFileList() throws OrderDaoPersistenceException;
    
    public void writeOrdersDatesFile(String date) throws OrderDaoPersistenceException;

    public void exportAllData() throws OrderDaoPersistenceException;
}
