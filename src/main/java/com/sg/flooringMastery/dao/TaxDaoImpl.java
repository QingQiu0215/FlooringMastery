/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringMastery.dao;

import com.sg.flooringMastery.dto.Product;
import com.sg.flooringMastery.dto.Tax;
import com.sg.flooringMastery.include.Config;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Qing
 */
@Component
public class TaxDaoImpl implements TaxDao {

    @Autowired
    List<Tax> tax = new ArrayList<>();

    @Override
    public List<Tax> getAllTax() throws TaxDaoPersistenceException {
        loadFile();
        return tax;

    }

    @Override
    public Tax getTaxbyState(String state) throws TaxDaoPersistenceException {
        loadFile();
        for (Tax t : tax) {
            if (t.getStateAbbreviation().equalsIgnoreCase(state)) {
                return t;
            }
        }
        return null;
    }

    @Override
    public void loadFile() throws TaxDaoPersistenceException {
        Scanner sc = null;
        try {
            sc = new Scanner(new File(Config.TAX_FILENAME));
        } catch (FileNotFoundException ex) {
            throw new TaxDaoPersistenceException("Error loading file");
        }

        tax = new ArrayList<>();
        String line = sc.nextLine();
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            String parts[] = line.split(Config.DELIMITER);
            if (parts.length == 3) {
                //StateAbbreviation, StateName , TaxRate

                Tax t = new Tax();
                t.setStateAbbreviation(parts[0]);
                t.setStateName(parts[1]);
                t.setTaxRate(new BigDecimal(parts[2]));

                tax.add(t);
            }
        }
        sc.close();
    }

    @Override
    public void writeFile() throws TaxDaoPersistenceException {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileWriter(Config.TAX_FILENAME));
        } catch (IOException ex) {
            throw new TaxDaoPersistenceException("Error writing to file");
        }
        pw.append("StateAbbreviation" + Config.DELIMITER
                + "StateName" + Config.DELIMITER
                + "TaxRate"
                + "\n"
        );
        for (Tax t : tax) {
            pw.append(t.getStateAbbreviation() + Config.DELIMITER
                    + t.getStateName() + Config.DELIMITER
                    + t.getTaxRate() + "\n");
        }

        pw.flush();
        pw.close();
    }

    @Override
    public void addTax(Tax newTax) throws TaxDaoPersistenceException {
        loadFile();
        tax.add(newTax);
        writeFile();
    }

}
