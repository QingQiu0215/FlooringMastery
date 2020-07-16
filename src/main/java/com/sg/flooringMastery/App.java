/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringMastery;

import com.sg.flooringMastery.controller.FlooringMasteryController;
import com.sg.flooringMastery.dao.OrderDao;
import com.sg.flooringMastery.dao.OrderDaoImpl;
import com.sg.flooringMastery.dao.ProductDao;
import com.sg.flooringMastery.dao.ProductDaoImpl;
import com.sg.flooringMastery.dao.TaxDao;
import com.sg.flooringMastery.dao.TaxDaoImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.sg.flooringMastery.service.FlooringMasteryService;
import com.sg.flooringMastery.view.FlooringMasteryView;
import com.sg.flooringMastery.view.UserIO;
import com.sg.flooringMastery.view.UserIOImpl;

/**
 *
 * @author Qing
 */
public class App {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext appContext=new AnnotationConfigApplicationContext();
        appContext.scan("com.sg.flooringMastery");
        appContext.refresh();
        
        FlooringMasteryController controller=appContext.getBean("flooringMasteryController",FlooringMasteryController.class);
        controller.run();
    }
}
