/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringMastery.view;

import com.sg.flooringMastery.dto.Order;
import com.sg.flooringMastery.dto.Product;
import com.sg.flooringMastery.dto.Tax;
import com.sg.flooringMastery.include.Config;
import com.sg.flooringMastery.include.Regex;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Qing
 */
@Component
public class FlooringMasteryView {

    UserIO io;

    @Autowired
    public FlooringMasteryView(UserIO io) {
        this.io = io;
    }

    public void welcomeBanner() {
        io.print("\n\tWelcome to Flooring Mastery.");
    }

    /**
     * Display the main menu for selecting
     *
     * @return int
     */
    public int displayMenuAndGetOption() {

        io.print("\n\t1 - Display Orders"
                + "\n\t2 - Add an Order"
                + "\n\t3 - Edit an Order"
                + "\n\t4 - Remove an Order"
                + "\n\t5 - Export All Data"
                + "\n\t6 - Quit\n");
        return io.readInt("\tSelect an option:", 1, 6);

    }

    /**
     * help method to get serial No, also check duplicate item in Machine
     * inventory.
     *
     * @return string
     */
    public String getSerialNo() {
        return io.readString("\tEnter serial No:");
    }

    public void wrongFormatMsg() {
        io.print("\tWarning! - Wrong custom name format.");
    }

    public void actionSuccess(String action) {
        io.print(String.format("\n\t-->%s successfully", action));
    }

    public void actionFailure(String action) {
        io.print(String.format("\n\t-->%s failed", action));
    }

    public void viewOrders(List<Order> order, String date) {

        io.print("\tListing orders in " + date);
        io.printFormat("\n\tOrderNumber", "  CustomerName", "  State", "  TaxRate", "  ProductType", "  Area", "  CostPerSquareFoot",
                "  LaborCostPerSquareFoot", "  MaterialCost", "  LaborCost", "  Tax", "  Total");
        for (Order o : order) {

            io.printFormat(Integer.toString(o.getOrderNumber()), o.getCustomerName(), o.getState(), o.getTaxRate().toString(),
                    o.getProductType(), o.getArea().toString(), o.getCostPerSquareFoot().toString(),
                    o.getLaborCostPerSquareFoot().toString(), o.getMaterialCost().toString(), o.getLaborCost().toString(),
                    o.getTax().toString(), o.getTotal().toString());
        }

    }

    public void viewSpecificOrder(Order o) {

        io.printFormat("\n\tOrderNumber", "  CustomerName", "  State", "  TaxRate", "  ProductType", "  Area", "  CostPerSquareFoot",
                "  LaborCostPerSquareFoot", "  MaterialCost", "  LaborCost", "  Tax", "  Total");

        io.printFormat(Integer.toString(o.getOrderNumber()), o.getCustomerName(), o.getState(), o.getTaxRate().toString(),
                o.getProductType(), o.getArea().toString(), o.getCostPerSquareFoot().toString(),
                o.getLaborCostPerSquareFoot().toString(), o.getMaterialCost().toString(), o.getLaborCost().toString(),
                o.getTax().toString(), o.getTotal().toString());

    }

    //**********************************for tax***********************************
    public Tax getTaxInfo(String prompt) {
        io.print("\t-----Start to enter all tax information-----");
        String state = io.readString("\tEnter state abbreviation:");
        String stateName = io.readString("\tEnter state name:");
        BigDecimal taxRate = io.readBigDecimal("\tEnter the tax rate:");

        return new Tax(state, stateName, taxRate);
    }

    //**********************************for product***********************************
    public Product getProductInfo(String prompt) {
        io.print("\t-----Start to enter all product information-----");
        String ProductType = io.readString("\tEnter Product Type:");
        BigDecimal CostPerSquareFoot = io.readBigDecimal("\tEnter Cost Per Square Foot:").setScale(2);
        BigDecimal LaborCostPerSquareFoot = io.readBigDecimal("\tEnter the Labor Cost Per Square Foot :").setScale(2);

        return new Product(ProductType, CostPerSquareFoot, LaborCostPerSquareFoot);
    }

    public String getProductType(String functionType, List<Product> product) {
        io.print("\n\tThese are the existed product types in the system:");
        int option = 0;
        //if the calling function is "add an order", only one additional option "add another product type" here
        if (functionType.equals("for adding")) {
            showExistedProductType(product, functionType);
            option = io.readInt("\n\tSelect an option (1 - " + (product.size() + 1) + ")", 1, product.size() + 1);
            if (option == product.size() + 1) {
                return "Add another product type";
            }
        } //if the calling function is "edit an order", there is an option to allow the user to choose the old property as it is
        else {
            showExistedProductType(product, functionType);
            option = io.readInt("\n\tSelect an option (1 - " + (product.size() + 2) + ")", 1, product.size() + 2);
            if (option == product.size() + 1) {
                return "Add another product type";
            } else if (option == product.size() + 2) {
                return "";
            }
        }
        int index = 1;
        String type = "";
        for (Product p : product) {
            if (index == option) {
                type = p.getProductType();
            }
            index++;
        }
        return type;
    }

    public void showExistedProductType(List<Product> product, String functionType) {
        int i = 1;
        for (Product p : product) {
            io.print("\t" + i + ":" + p.getProductType());
            i++;
        }
        io.print("\t" + i + ":Add another product type");
        //if the function is editing, allow the user keep the old Product Type as it is.
        if (functionType.equals("for editing")) {
            i++;
            io.print("\t" + i + ":Keep the old value as it is");
        }

    }

    //**********************************for order***********************************
    public String[] getOrderInfo(String prompt) {
        io.print(prompt);
        String[] orderInfo = new String[2];
        orderInfo[0] = getCustomerName("?????", "for adding");
        orderInfo[1] = getArea();

        return orderInfo;
    }

    public String getCustomerName(String oldProperty, String functionType) {
        boolean inputFormat = false;
        String customerName = "";
        do {
            inputFormat = false;
            customerName = io.readString("\tEnter customer name:\n"
                    + "\t(for adding a new name,only allow [a-z][0-9],\\. \\, Not allow blank)\n"
                    + "\t(for editing a name,only allow [a-z][0-9],\\. \\, Not allow blank,but allow empty string to keep the old name as it is)\n");
            if (customerName.equals("") && functionType.equals("for editing")) {
                return oldProperty;
            }
            if (!customerName.matches(Regex.FORMAT_CUSTOMERNAME)) {
                io.print("\tWarning! - Wrong format of customer format.");
                inputFormat = true;
            }
        } while (inputFormat);
        return customerName;
    }

    public String getState(String functionType, List<Tax> tax) {
        io.print("\n\tThese are the existed states in the system:");
        int option = 0;
        //if the calling function is "add an order", only one additional option "add another state" here
        if (functionType.equals("for adding")) {
            showExistedState(tax, functionType);
            option = io.readInt("\n\tSelect an option (1 - " + (tax.size() + 1) + ")", 1, tax.size() + 1);
            if (option == tax.size() + 1) {
                return "Add another state";
            }
        } //if the calling function is "edit an order", there is an option to allow the user to choose the old property as it is
        else {
            showExistedState(tax, functionType);
            option = io.readInt("\n\tSelect an option (1 - " + (tax.size() + 2) + ")", 1, tax.size() + 2);
            if (option == tax.size() + 1) {
                return "Add another state";
            } else if (option == tax.size() + 2) {
                return "";
            }
        }
        int index = 1;
        String state = "";
        for (Tax t : tax) {
            if (index == option) {
                state = t.getStateAbbreviation();
            }
            index++;
        }
        return state;
    }

    public void showExistedState(List<Tax> tax, String functionType) {
        int i = 1;
        for (Tax t : tax) {
            io.print("\t" + i + ":" + t.getStateAbbreviation() + " (" + t.getStateName() + ")");
            i++;
        }
        io.print("\t" + i + ":Add another state");
        //if the function is editing, allow the user keep the old state as it is.
        if (functionType.equals("for editing")) {
            i++;
            io.print("\t" + i + ":Keep the old value as it is");
        }

    }

    public String getArea() {
        BigDecimal area;
        boolean loop = false;
        do {
            loop = false;
            area = io.readBigDecimal("\tEnter the area for the order:").setScale(2);
            if (area.compareTo(new BigDecimal("0")) < 0
                    || ((area.subtract(new BigDecimal("100"))).compareTo(new BigDecimal("0")) < 0)) {
                io.print("\tWarning! - The area must be a positive decimal. Minimum order size is 100.");
                loop = true;
            }
        } while (loop);
        return area.toString();
    }

    public int selectProperties() {

        io.print("\n\t1 - Customer Name"
                + "\n\t2 - State"
                + "\n\t3 - Product Type"
                + "\n\t4 - Area"
                + "\n");
        return io.readInt("\tSelect an option to edit:", 1, 4);
    }

    public String getNewProperty(int option, String oldProperty) {
        String property = convertToStr(option);
        String newProperty = "";
        String temp = "";
        if (option == 1) {
            io.print("\tEnter a new " + property + " (" + oldProperty + "):\n");
            newProperty = getCustomerName(oldProperty, "for editing");
        } else if (option == 4) {
            newProperty = io.readBigDecimalForEdit("\tEnter a new " + property + " (" + oldProperty + "):", oldProperty);
        }
        return newProperty;
    }

    public String convertToStr(int option) {
        String property = "";
        switch (option) {
            case 1:
                property = "customer name";
                break;
            case 2:
                property = "state";
                break;
            case 3:
                property = "product type";
                break;
            case 4:
                property = "area";
                break;
        }
        return property;
    }

    public int getOrderNo(String prompt) {
        return io.readInt(prompt);
    }

    public String enterOrderDate(String banner, List<String> orderDateList) {
        io.print("\tThese are available dates (MM-DD-YYYY):\n");
        int i = 1;
        for (String s : orderDateList) {

            io.print("\t" + i + ": " + s.substring(0, 2) + "-" + s.substring(2, 4) + "-" + s.substring(4));
            i++;
        }
        io.print(banner);
        return getOrderDate(orderDateList);
    }

    private String getOrderDate(List<String> orderDateList) {
        int length = orderDateList.size();
        int index = io.readInt("\tSelect a date in the above list (1 - " + length + ")", 1, length);
        int i = 1;
        for (String s : orderDateList) {
            if (index == i) {
                return s;
            }
            i++;
        }
        return "";
    }

    public void displayError(String error) {
        io.print("\n\tERROR: " + error);
    }

    public boolean checkContinue(String prompt) {
        io.print(prompt);
        int option = io.readInt("\t1 - Continue\n\t2 - Quit", 1, 2);
        return option == 1;
    }

    public void exitBanner() {
        io.print("\n\tThank you for using the Flooring Mastery.");
    }

    public void printMsg(String message) {
        io.print(message);
    }

    public void showDeposit(String prompt) {
        io.print(prompt);
    }

    public int enterQty(int qty) {
        return io.readInt("\n\tSelect the quantity (1 - " + qty + "):", 1, qty);
    }

}
