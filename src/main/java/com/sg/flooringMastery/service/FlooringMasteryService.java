/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringMastery.service;

import com.sg.flooringMastery.dao.OrderDao;
import com.sg.flooringMastery.dao.OrderDaoImpl;
import com.sg.flooringMastery.dao.OrderDaoPersistenceException;
import com.sg.flooringMastery.dao.ProductDao;
import com.sg.flooringMastery.dao.ProductDaoPersistenceException;
import com.sg.flooringMastery.dao.TaxDao;
import com.sg.flooringMastery.dao.TaxDaoPersistenceException;
import com.sg.flooringMastery.dto.Order;
import com.sg.flooringMastery.dto.Product;
import com.sg.flooringMastery.dto.Tax;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Qing
 */
@Component
public class FlooringMasteryService {

    OrderDao orderDao;
    ProductDao productDao;
    TaxDao taxDao;
    List<Order> order = new ArrayList<>();
    List<Tax> tax = new ArrayList<>();
    List<Product> product = new ArrayList<>();

    @Autowired
    public FlooringMasteryService(OrderDao orderDao, ProductDao productDao, TaxDao taxDao) {
        this.orderDao = orderDao;
        this.productDao = productDao;
        this.taxDao = taxDao;
    }

    //*****************For orderDao**********************
    public List<String> getOrderDatesList() throws OrderDaoPersistenceException {
        orderDao.loadOrderDatesFile();
        return orderDao.getOrderDatesList();
    }

    public int getLastOrderNo(String date) throws OrderDaoPersistenceException {
        return orderDao.getLastOrderNo(date);
    }

    public Order addOrder(String date, Order newOrder) throws OrderDaoPersistenceException {
        try {
            checkOrderDateExist(date);
            orderDao.addOrder(date, newOrder);
        } catch (OrderDateNotExistException ex) {
            orderDao.addOrderInNewFile(date, newOrder);

        }
        return newOrder;
    }

    public List<Order> getOrderByDate(String date) throws OrderDaoPersistenceException, OrderDateNotExistException {
        checkOrderDateExist(date);
        return orderDao.getOrderByDate(date);
    }

    public Order getOrderByDateAndOrderNo(int orderNo, String date) throws OrderDaoPersistenceException,
            OrderDateNotExistException, OrderNumberNotExistException {
        checkOrderDateExist(date);
        checkOrderNoExist(orderNo, date);
        return orderDao.getOrderByOrderNo(orderNo, order);

    }

    public void checkOrderDateExist(String date) throws OrderDaoPersistenceException, OrderDateNotExistException {
        orderDao.loadOrderDatesFile();
        if (!(orderDao.getOrderDatesList().contains(date))) {
            throw new OrderDateNotExistException("This date is not exist in the order folder");
        }
    }

    public void checkOrderNoExist(int orderNo, String date) throws OrderDaoPersistenceException, OrderNumberNotExistException {
        order = orderDao.getOrderByDate(date);
        boolean containOrderNo = false;
        for (Order o : order) {
            if (o.getOrderNumber() == orderNo) {
                containOrderNo = true;
            }
        }
        if (containOrderNo == false) {
            throw new OrderNumberNotExistException("This order number is not exist in the date");
        }
    }

    public Order editOrder(String date, int orderNo, Order newOrder) throws OrderDaoPersistenceException, OrderNumberNotExistException {
        return orderDao.editOrder(date, orderNo, newOrder);
    }

    public Order removeSpecificOrder(int orderNo, String date) throws OrderDaoPersistenceException,
            OrderDateNotExistException, OrderNumberNotExistException {
        checkOrderDateExist(date);
        checkOrderNoExist(orderNo, date);
        return orderDao.removeOrder(date, orderNo);
    }

    public void exportAllData() throws OrderDaoPersistenceException {
        orderDao.exportAllData();
    }

    //*****************For productDao**********************
    public List<Product> getAllProduct() throws ProductDaoPersistenceException {
        return productDao.getAllProduct();
    }

    public Product getProductbyProductType(String productType) throws ProductDaoPersistenceException, ProductNotExistException {
        checkProductExist(productType);
        return productDao.getProductbyProductType(productType);
    }

    public Product addProduct(Product newProduct) throws ProductDaoPersistenceException, ProductTypeDuplicatedException {
        checkDuplicateProduct(newProduct);
        productDao.addProduct(newProduct);
        return newProduct;
    }

    public void checkDuplicateProduct(Product newProduct) throws ProductDaoPersistenceException, ProductTypeDuplicatedException {
        productDao.loadFile();
        product = productDao.getAllProduct();
        for (Product p : product) {
            if (newProduct.getProductType().equalsIgnoreCase(p.getProductType())) {
                throw new ProductTypeDuplicatedException("The product type already exists in the file.");
            }
        }
    }

    public void checkProductExist(String newProduct) throws ProductDaoPersistenceException, ProductNotExistException {
        if (productDao.getProductbyProductType(newProduct) == null) {
            throw new ProductNotExistException("The product information in the state does not exist.");
        }
    }

    //*****************For taxDao**********************
    public List<Tax> getAllTax() throws TaxDaoPersistenceException {
        return taxDao.getAllTax();

    }

    public Tax getTaxbyState(String state) throws TaxDaoPersistenceException, TaxNotExistException {
        checkTaxExist(state);
        return taxDao.getTaxbyState(state);
    }

    public Tax addTax(Tax newTax) throws TaxDaoPersistenceException, StateAbbrDuplicatedException {
        checkDuplicateTax(newTax);
        taxDao.addTax(newTax);
        return newTax;
    }

    public void checkDuplicateTax(Tax newTax) throws TaxDaoPersistenceException, StateAbbrDuplicatedException {
        taxDao.loadFile();
        tax = taxDao.getAllTax();
        for (Tax t : tax) {
            if (newTax.getStateAbbreviation().equalsIgnoreCase(t.getStateAbbreviation())) {
                throw new StateAbbrDuplicatedException("The state of the new tax already exists in the file.");
            }
        }
    }

    public void checkTaxExist(String state) throws TaxDaoPersistenceException, TaxNotExistException {
        if (taxDao.getTaxbyState(state) == null) {
            throw new TaxNotExistException("The tax information in the state does not exist.");
        }
    }
}
