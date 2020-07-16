/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringMastery.dao;

import com.sg.flooringMastery.dto.Order;
import com.sg.flooringMastery.include.Config;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Qing
 */
@Component
public class OrderDaoImpl implements OrderDao {

    List<Order> order = new ArrayList<>();
//    List<Order> allOrder = new ArrayList<>();
    List<String> orderDatesList = new ArrayList<>();

    @Override
    public List<String> getOrderDatesList() {
        return orderDatesList;
    }

    @Override
    public int getLastOrderNo(String date) throws OrderDaoPersistenceException {
        loadFile(date);
        return order.get(order.size() - 1).getOrderNumber();
    }

    @Override
    public List<Order> getOrderByDate(String date) throws OrderDaoPersistenceException {
        loadFile(date);
        return order;
    }

    @Override
    public Order addOrder(String date, Order newOrder) throws OrderDaoPersistenceException {
        //if file exist
        loadFile(date);
        order.add(newOrder);
        writeFile(date);
        return newOrder;
    }

    public Order addOrderInNewFile(String date, Order newOrder) throws OrderDaoPersistenceException {
        //if file not exist
        writeOrderInNewFile(date, newOrder);
        writeOrdersDatesFile(date);
        return newOrder;
    }

    @Override
    public Order removeOrder(String date, int orderNo) throws OrderDaoPersistenceException {
        loadFile(date);
        int index = -1;
        for (int i = 0; i < order.size(); i++) {
            if (order.get(i).getOrderNumber() == orderNo) {
                index = i;
            }
        }
        Order temp = new Order(order.get(index));
        if (index != -1) {
            order.remove(index);
            writeFile(date);
        }
        return temp;
    }

    @Override
    public Order editOrder(String date, int orderNo, Order newOrder) throws OrderDaoPersistenceException {
        loadFile(date);
        int index = -1;
        for (int i = 0; i < order.size(); i++) {
            if (order.get(i).getOrderNumber() == orderNo) {
                index = i;
                break;
            }
        }

        if (index != -1) {
            order.set(index, newOrder);
            writeFile(date);
            return newOrder;
        }
        return null;
    }

    @Override
    public Order getOrderByOrderNo(int orderNo, List<Order> ordersInTheDate) throws OrderDaoPersistenceException {
        for (Order o : ordersInTheDate) {
            if (o.getOrderNumber() == orderNo) {
                return o;
            }
        }
        return null;
    }

    @Override
    public void loadFile(String date) throws OrderDaoPersistenceException {
        Scanner sc = null;
        try {
            sc = new Scanner(new File(Config.ORDER_FILEPATH + date + ".txt"));
        } catch (FileNotFoundException ex) {
            throw new OrderDaoPersistenceException("Error loading file");
        }

        order = new ArrayList<>();
        String line = sc.nextLine();
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            String parts[] = line.split(Config.DELIMITER);
            if (parts.length == 12) {
                //serialNo, title, releaseDate, ratingMPAA,directorName,studio,userNote;

                Order o = new Order();
                o.setOrderNumber(Integer.parseInt(parts[0]));
                o.setCustomerName(parts[1]);
                o.setState(parts[2]);
                o.setTaxRate(new BigDecimal(parts[3]));
                o.setProductType(parts[4]);
                o.setArea(new BigDecimal(parts[5]));
                o.setCostPerSquareFoot(new BigDecimal(parts[6]));
                o.setLaborCostPerSquareFoot(new BigDecimal(parts[7]));
                o.setMaterialCost(new BigDecimal(parts[8]));
                o.setLaborCost(new BigDecimal(parts[9]));
                o.setTax(new BigDecimal(parts[10]));
                o.setTotal(new BigDecimal(parts[11]));

                order.add(o);
            }
        }
        sc.close();
    }

    @Override
    public void writeFile(String date) throws OrderDaoPersistenceException {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileWriter(Config.ORDER_FILEPATH + date + ".txt"));
        } catch (IOException ex) {
            throw new OrderDaoPersistenceException("Error writing to file");
        }
        Collections.sort(order, new SortByNo());
        pw.append("OrderNumber " + Config.DELIMITER
                + "CustomerName" + Config.DELIMITER
                + "State" + Config.DELIMITER
                + "TaxRate" + Config.DELIMITER
                + "ProductType" + Config.DELIMITER
                + "Area" + Config.DELIMITER
                + "CostPerSquareFoot" + Config.DELIMITER
                + "LaborCostPerSquareFoot" + Config.DELIMITER
                + "MaterialCost" + Config.DELIMITER
                + "LaborCost" + Config.DELIMITER
                + "Tax" + Config.DELIMITER
                + "Total"
                + "\n");
        for (Order o : order) {
            pw.append(o.getOrderNumber() + Config.DELIMITER
                    + o.getCustomerName() + Config.DELIMITER
                    + o.getState() + Config.DELIMITER
                    + o.getTaxRate() + Config.DELIMITER
                    + o.getProductType() + Config.DELIMITER
                    + o.getArea() + Config.DELIMITER
                    + o.getCostPerSquareFoot() + Config.DELIMITER
                    + o.getLaborCostPerSquareFoot() + Config.DELIMITER
                    + o.getMaterialCost() + Config.DELIMITER
                    + o.getLaborCost() + Config.DELIMITER
                    + o.getTax() + Config.DELIMITER
                    + o.getTotal()
                    + "\n");
        }

        pw.flush();
        pw.close();
    }

    class SortByNo implements Comparator<Order> {

        // Used for sorting in ascending order of 
        // serial No
        public int compare(Order a, Order b) {
            return a.getOrderNumber() - (b.getOrderNumber());
        }
    }

    @Override
    public void loadOrderDatesFile() throws OrderDaoPersistenceException {
        Scanner sc = null;
        try {
            sc = new Scanner(new File(Config.ORDERDATESFILE_FILENAME));
        } catch (FileNotFoundException ex) {
            throw new OrderDaoPersistenceException("Error loading file");
        }

        orderDatesList = new ArrayList<>();

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            orderDatesList.add(line);
        }
        sc.close();
    }

    @Override
    public void exportAllData() throws OrderDaoPersistenceException {
        writeAllOrdersInBackUpFileList();
    }

    public void writeAllOrdersInBackUpFileList() throws OrderDaoPersistenceException {
        loadOrderDatesFile();
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileWriter(Config.BACKUP_FILENAME));
        } catch (IOException ex) {
            throw new OrderDaoPersistenceException("Error writing to file");
        }
        //Collections.sort(allOrder,new SortByNo());
        pw.append("OrderNumber " + Config.DELIMITER
                + "CustomerName" + Config.DELIMITER
                + "State" + Config.DELIMITER
                + "TaxRate" + Config.DELIMITER
                + "ProductType" + Config.DELIMITER
                + "Area" + Config.DELIMITER
                + "CostPerSquareFoot" + Config.DELIMITER
                + "LaborCostPerSquareFoot" + Config.DELIMITER
                + "MaterialCost" + Config.DELIMITER
                + "LaborCost" + Config.DELIMITER
                + "Tax" + Config.DELIMITER
                + "Total" + Config.DELIMITER
                + "Date"
                + "\n");
        for (String s : orderDatesList) {
            loadFile(s);
            for (Order o : order) {
                pw.write(o.getOrderNumber() + Config.DELIMITER
                        + o.getCustomerName() + Config.DELIMITER
                        + o.getState() + Config.DELIMITER
                        + o.getTaxRate() + Config.DELIMITER
                        + o.getProductType() + Config.DELIMITER
                        + o.getArea() + Config.DELIMITER
                        + o.getCostPerSquareFoot() + Config.DELIMITER
                        + o.getLaborCostPerSquareFoot() + Config.DELIMITER
                        + o.getMaterialCost() + Config.DELIMITER
                        + o.getLaborCost() + Config.DELIMITER
                        + o.getTax() + Config.DELIMITER
                        + o.getTotal() + Config.DELIMITER
                        + convertDateFormat(s)
                        + "\n");
            }
        }

        pw.flush();
        pw.close();
    }

    private String convertDateFormat(String date) {
        //01012020->01-01-2020
        String mm = date.substring(0, 2),
                dd = date.substring(2, 4),
                yyyy = date.substring(4, 8);
        return mm + "-" + dd + "-" + yyyy;
    }

    public void writeOrderInNewFile(String date, Order newOrder) throws OrderDaoPersistenceException {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileWriter(Config.ORDER_FILEPATH + date + ".txt"));
        } catch (IOException ex) {
            throw new OrderDaoPersistenceException("Error writing to file");
        }
        pw.append("OrderNumber " + Config.DELIMITER
                + "CustomerName" + Config.DELIMITER
                + "State" + Config.DELIMITER
                + "TaxRate" + Config.DELIMITER
                + "ProductType" + Config.DELIMITER
                + "Area" + Config.DELIMITER
                + "CostPerSquareFoot" + Config.DELIMITER
                + "LaborCostPerSquareFoot" + Config.DELIMITER
                + "MaterialCost" + Config.DELIMITER
                + "LaborCost" + Config.DELIMITER
                + "Tax" + Config.DELIMITER
                + "Total"
                + "\n");

        pw.append(newOrder.getOrderNumber() + Config.DELIMITER
                + newOrder.getCustomerName() + Config.DELIMITER
                + newOrder.getState() + Config.DELIMITER
                + newOrder.getTaxRate() + Config.DELIMITER
                + newOrder.getProductType() + Config.DELIMITER
                + newOrder.getArea() + Config.DELIMITER
                + newOrder.getCostPerSquareFoot() + Config.DELIMITER
                + newOrder.getLaborCostPerSquareFoot() + Config.DELIMITER
                + newOrder.getMaterialCost() + Config.DELIMITER
                + newOrder.getLaborCost() + Config.DELIMITER
                + newOrder.getTax() + Config.DELIMITER
                + newOrder.getTotal()
                + "\n");

        pw.flush();
        pw.close();
    }

    public void writeOrdersDatesFile(String date) throws OrderDaoPersistenceException {
        loadOrderDatesFile();
        orderDatesList.add(date);
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileWriter(Config.ORDERDATESFILE_FILENAME));
        } catch (IOException ex) {
            throw new OrderDaoPersistenceException("Error writing to file");
        }

        for (String s : orderDatesList) {
            pw.append(s + "\n");
        }

        pw.flush();
        pw.close();
    }
}
