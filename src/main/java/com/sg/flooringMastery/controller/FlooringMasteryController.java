/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringMastery.controller;

import com.sg.flooringMastery.dao.OrderDaoPersistenceException;
import com.sg.flooringMastery.dao.ProductDaoPersistenceException;
import com.sg.flooringMastery.dao.TaxDaoPersistenceException;
import com.sg.flooringMastery.dto.Order;
import com.sg.flooringMastery.dto.Product;
import com.sg.flooringMastery.dto.Tax;
import com.sg.flooringMastery.include.Regex;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.sg.flooringMastery.service.FlooringMasteryService;
import com.sg.flooringMastery.service.OrderDateNotExistException;
import com.sg.flooringMastery.service.OrderNumberNotExistException;
import com.sg.flooringMastery.service.ProductNotExistException;
import com.sg.flooringMastery.service.ProductTypeDuplicatedException;
import com.sg.flooringMastery.service.StateAbbrDuplicatedException;
import com.sg.flooringMastery.service.TaxNotExistException;
import com.sg.flooringMastery.view.FlooringMasteryView;

/**
 *
 * @author Qing
 */
@Component
public class FlooringMasteryController {

    FlooringMasteryService service;
    FlooringMasteryView view;
    String date = "";
    int orderNo = -1;

    List<Order> order = new ArrayList<>();
    List<Tax> tax = new ArrayList<>();
    List<Product> product = new ArrayList<>();
    Order theOrder;
    Tax theTax;
    Product theProduct;
    String currentDate;
    boolean exitCurrentOption = false;

    @Autowired
    public FlooringMasteryController(FlooringMasteryService service, FlooringMasteryView view) {
        this.service = service;
        this.view = view;
    }

    public void run() {
        view.welcomeBanner();

        while (true) {
            int choice = view.displayMenuAndGetOption();
            try {
                switch (choice) {
                    case 1: //Display Orders
                        displayOrder();
                        break;
                    case 2: //Add an Order                       
                        addOrder();
                        break;
                    case 3: //Edit an Order
                        editOrder();
                        break;
                    case 4: //Remove an Order                      
                        removeOrder();
                        break;
                    case 5: //Export All Data
                        exportAllData();
                        break;
                    case 6: //Exit.
                        view.exitBanner();
                        System.exit(0);
                }
            } catch (OrderDaoPersistenceException | ProductDaoPersistenceException
                    | TaxDaoPersistenceException | TaxNotExistException
                    | ProductNotExistException | OrderDateNotExistException | OrderNumberNotExistException ex) {
                view.displayError(ex.getMessage());
            }
        }
    }

    public void displayOrder() throws OrderDaoPersistenceException {
        List<String> orderDateList = service.getOrderDatesList();
        //if there is at least order file in the system
        if (orderDateList.size() != 0) {
            date = view.enterOrderDate("\n\t----- Show orders in a specifc date -----", orderDateList);
            try {
                order = service.getOrderByDate(date);
                view.viewOrders(order, date);
                view.actionSuccess("Flooring Mastery displayed orders in " + date);
            } catch (OrderDateNotExistException ex) {
                view.actionFailure("Can not find orders in the date. Displayed orders");
            }
        }
        //if the order file folder is empty in the system
        if (orderDateList.size() == 0) {
            view.printMsg("\n\tThere are not any orders available.");
        }
        run();
    }

    public void addOrder() throws TaxDaoPersistenceException, ProductDaoPersistenceException,
            TaxNotExistException, ProductNotExistException, OrderDaoPersistenceException {
        String state = getState("?????");
        String productType = getProductType("?????");
        String[] orderInfo = view.getOrderInfo("\t----- Enter order information to add -----");
        String customerName = orderInfo[0];
        String area = orderInfo[1];

        Tax aTax = service.getTaxbyState(state);
        Product aProduct = service.getProductbyProductType(productType);

        int orderNumber = getOrderNumber();
        BigDecimal[] bd = getCalculateResult(aTax, aProduct, area);
        BigDecimal taxRate = bd[0];
        BigDecimal costPerSquareFoot = bd[1];
        BigDecimal laborCostPerSquareFoot = bd[2];
        BigDecimal materialCost = bd[3];
        BigDecimal laborCost = bd[4];
        BigDecimal Tax = bd[5];
        BigDecimal Total = bd[6];

        Order newOrder = new Order(orderNumber, customerName, state, taxRate, productType,
                new BigDecimal(area), costPerSquareFoot, laborCostPerSquareFoot, materialCost,
                laborCost, Tax, Total);
        confirmAddOrder(newOrder);
        run();
    }

    public BigDecimal[] getCalculateResult(Tax aTax, Product aProduct, String area) {
        BigDecimal[] bd = new BigDecimal[7];
        bd[0] = (aTax.getTaxRate()).setScale(2);
        bd[1] = (aProduct.getCostPerSquareFoot()).setScale(2);
        bd[2] = (aProduct.getLaborCostPerSquareFoot()).setScale(2);
        bd[3] = ((new BigDecimal(area)).multiply(bd[1])).setScale(2);
        bd[4] = ((new BigDecimal(area)).multiply(bd[2])).setScale(2);
        bd[5] = ((bd[3].add(bd[4])).multiply(bd[0].divide(new BigDecimal("100")))).setScale(2, RoundingMode.HALF_DOWN);
        bd[6] = ((bd[3].add(bd[4])).add(bd[5])).setScale(2);
        return bd;
    }

    public void confirmAddOrder(Order newOrder) throws OrderDaoPersistenceException {
        view.printMsg("\tThis is the new order:\n");
        view.viewSpecificOrder(newOrder);
        if (view.checkContinue("\n\tContinue to add the new order?")) {
            Order newAddedOrder = service.addOrder(currentDate, newOrder);
            if (newAddedOrder != null) {
                view.actionSuccess("\tThe new order added");
                view.viewSpecificOrder(newAddedOrder);
            }
        }
    }

    public int getOrderNumber() throws OrderDaoPersistenceException {
        currentDate = getCurrentDate();
        try {
            service.checkOrderDateExist(currentDate);
        } catch (OrderDateNotExistException ex) {
            return 1;
        }
        return service.getLastOrderNo(currentDate) + 1;
    }

    public String getCurrentDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMddyyyy");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public String getState(String oldProperty) throws TaxDaoPersistenceException {
        tax = service.getAllTax();
        String state = "";
        boolean loop = false;
        do {
            loop = false;
            //Calling method is adding.
            //Because there is no old property for adding function.????? is just for padding and identifer
            if (oldProperty.equals("?????")) {
                state = view.getState("for adding", tax);
            } 
            //if the calling method is editing
            else {
                state = view.getState("for editing", tax);
            }

            if (state.equals("")) {
                return oldProperty;
            }
            try {
                service.getTaxbyState(state);
            } catch (TaxNotExistException ex) {
                //view.displayError(ex.getMessage());
                addNewTax();
                tax = service.getAllTax();
                loop = true;
            }

        } while (loop);
        return state;
    }

    public void addNewTax() throws TaxDaoPersistenceException {

        while (view.checkContinue("\tDo you want to add a new tax item?")) {
            theTax = view.getTaxInfo("\t----- Enter tax information to add -----");
            try {
                service.addTax(theTax);
                view.actionSuccess("\tThe new tax information was added");
            } catch (StateAbbrDuplicatedException ex) {
                view.displayError(ex.getMessage());
            }
        }
    }

    public String getProductType(String oldProperty) throws ProductDaoPersistenceException {
        product = service.getAllProduct();
        String productType = "";
        boolean loop = false;
        do {
            loop = false;
            if (oldProperty.equals("?????")) {
                productType = view.getProductType("for adding", product);
            } else {
                productType = view.getProductType("for editing", product);
            }

            if (productType.equals("")) {
                return oldProperty;
            }
            try {
                service.getProductbyProductType(productType);
            } catch (ProductNotExistException ex) {
                //view.displayError(ex.getMessage());
                addNewProduct();
                product = service.getAllProduct();
                loop = true;
            }

        } while (loop);
        return productType;
    }

    public void addNewProduct() throws ProductDaoPersistenceException {

        while (view.checkContinue("\tDo you want to add a new product item?")) {
            theProduct = view.getProductInfo("\t----- Enter product information to add -----");
            try {
                service.addProduct(theProduct);
                view.actionSuccess("\tThe new product type was added");
            } catch (ProductTypeDuplicatedException ex) {
                view.displayError(ex.getMessage());
            }
        }
    }

    public void editOrder() throws OrderDaoPersistenceException, TaxDaoPersistenceException,
            ProductDaoPersistenceException, OrderDateNotExistException, OrderNumberNotExistException,
            TaxNotExistException, ProductNotExistException {

        view.printMsg("\t----- Edit an order -----");
        List<String> orderDateList = service.getOrderDatesList();
        //if there is at least order file in the system
        if (orderDateList.size() != 0) {
            exitCurrentOption = false;
            showSpecificOrder();
            //if the selected order is not existed, go back to main menu.
            if (!exitCurrentOption) {

                //allow to edit multiple properties
                do {
                    int option = view.selectProperties();
                    String oldProperty = getOldProperty(option);
                    String newProperty = "";
                    String temp = "";
                    //option is "state"
                    if (option == 2) {
                        view.printMsg("\tEnter a new " + view.convertToStr(option) + " (" + oldProperty + "):\n");
                        newProperty = getState(oldProperty);
                    } 
                    //option is "product type"
                    else if (option == 3) {
                        view.printMsg("\tEnter a new " + view.convertToStr(option) + " (" + oldProperty + "):\n");
                        newProperty = getProductType(oldProperty);
                    } 
                    //other options-customer name or rate
                    else {
                        newProperty = view.getNewProperty(option, oldProperty);
                    }

                    theOrder = updateProperty(option, newProperty);

                    String state = theOrder.getState();
                    String productType = theOrder.getProductType();
                    String area = theOrder.getArea().toString();
                    Tax aTax = service.getTaxbyState(state);
                    Product aProduct = service.getProductbyProductType(productType);

                    BigDecimal[] bd = getCalculateResult(aTax, aProduct, area);
                    updateProperty(5, bd[0].toString());
                    updateProperty(6, bd[1].toString());
                    updateProperty(7, bd[2].toString());
                    updateProperty(8, bd[3].toString());
                    updateProperty(9, bd[4].toString());
                    updateProperty(10, bd[5].toString());
                    updateProperty(11, bd[6].toString());

                    confirmOrder(theOrder);
                    service.editOrder(date, orderNo, theOrder);
                    view.actionSuccess("\tThe order was updated");

                } while (view.checkContinue("\tContinue to update other property?"));
            }
        }
        //if the order file folder is empty in the system
        if (orderDateList.size() == 0) {
            view.printMsg("\n\tThere are not any orders available.");
        }
        run();
    }

    public String getOldProperty(int option) {
        String oldProperty = "";
        switch (option) {
            case 1:
                oldProperty = theOrder.getCustomerName();
                break;
            case 2:
                oldProperty = theOrder.getState();
                break;
            case 3:
                oldProperty = theOrder.getProductType();
                break;
            case 4:
                oldProperty = theOrder.getArea().toString();
                break;

        }
        return oldProperty;
    }

    private Order updateProperty(int option, String newProperty) throws OrderDaoPersistenceException,
            OrderDateNotExistException, OrderNumberNotExistException {
        //Order o = service.getOrderByDateAndOrderNo(orderNo, date);
        switch (option) {
            case 1:
                theOrder.setCustomerName(newProperty);
                break;
            case 2:
                theOrder.setState(newProperty);
                break;
            case 3:
                theOrder.setProductType(newProperty);
                break;
            case 4:
                theOrder.setArea(new BigDecimal(newProperty));
                break;
            case 5:
                theOrder.setTaxRate(new BigDecimal(newProperty));
                break;
            case 6:
                theOrder.setCostPerSquareFoot(new BigDecimal(newProperty));
                break;
            case 7:
                theOrder.setLaborCostPerSquareFoot(new BigDecimal(newProperty));
                break;
            case 8:
                theOrder.setMaterialCost(new BigDecimal(newProperty));
                break;
            case 9:
                theOrder.setLaborCost(new BigDecimal(newProperty));
                break;
            case 10:
                theOrder.setTax(new BigDecimal(newProperty));
                break;
            case 11:
                theOrder.setTotal(new BigDecimal(newProperty));
                break;

        }
        return theOrder;
    }

    public void showSpecificOrder() throws OrderDaoPersistenceException {
        boolean loop = false;
        do {
            loop = false;
            try {
                List<String> orderDateList = service.getOrderDatesList();
                date = view.enterOrderDate("\n\t----- Show an order in a specifc date -----", orderDateList);
                order = service.getOrderByDate(date);
                view.viewOrders(order, date);
                orderNo = view.getOrderNo("\n\tEnter the order number:");
                theOrder = service.getOrderByDateAndOrderNo(orderNo, date);
                view.viewSpecificOrder(theOrder);
                view.actionSuccess("\tDisplayed the order");
            } catch (OrderDateNotExistException | OrderNumberNotExistException ex) {
                view.actionFailure("\tNot in order file. Displayed the order");
                if (view.checkContinue("\tContinue to select another order to edit?")) {
                    loop = true;
                } else {
                    exitCurrentOption = true;
//                    view.exitBanner();
//                    System.exit(0);
                }
            }

        } while (loop);

    }

    public void removeOrder() throws OrderDaoPersistenceException {
        List<String> orderDateList = service.getOrderDatesList();
        //if there is at least order file in the system
        if (orderDateList.size() != 0) {

            boolean loop = false;
            do {
                loop = false;
                try {

                    date = view.enterOrderDate("\t----- Remove an order in a specifc date -----", orderDateList);
                    order = service.getOrderByDate(date);
                    view.viewOrders(order, date);
                    int orderNo = view.getOrderNo("\tEnter the order number:");
                    theOrder = service.getOrderByDateAndOrderNo(orderNo, date);
                    confirmOrder(theOrder);
                    theOrder = service.removeSpecificOrder(orderNo, date);
                    view.actionSuccess("\tFlooring Mastery removed the order in " + date);
                } catch (OrderDateNotExistException | OrderNumberNotExistException ex) {
                    view.actionFailure("\tCan not find orders in the date. Displayed orders");
                    boolean confirm = view.checkContinue("\n\tContinue to remove another order?");
                    if(confirm==true)
                        loop=true;
                    //if the selected order is not existed, go back to main menu.
                    else
                        break;  
                }
            } while (loop);
        }
        //if the order file folder is empty in the system
        if (orderDateList.size() == 0) {
            view.printMsg("\n\tThere are not any orders available.");
        }
        run();
    }

    //show order confirmation
    public void confirmOrder(Order theOrder) {
        view.printMsg("\n\tThis is the order information:");
        view.viewSpecificOrder(theOrder);
        boolean confirm = view.checkContinue("\n\tDo you want to confirm to continue?");
        if (confirm == false) {
            view.exitBanner();
            //System.exit(0);
            run();
        }
    }

    public void exportAllData() throws OrderDaoPersistenceException {
        if (view.checkContinue("\n\tConfirm to continue to export all data?")) {
            service.exportAllData();
            view.actionSuccess("\tFlooring Mastery exported all data");
        }

        run();
    }

}
