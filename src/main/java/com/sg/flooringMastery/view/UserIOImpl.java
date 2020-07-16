/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringMastery.view;

import java.math.BigDecimal;
import java.util.Scanner;
import org.springframework.stereotype.Component;

/**
 *
 * @author Qing
 */
@Component
public class UserIOImpl implements UserIO{
    
    public void print(String message){
        System.out.println(message);
    }
    
    public void printFormat(String orderNumber, String customerName, String state, String taxRate, 
            String productType, String area, String costPerSquareFoot, 
            String laborCostPerSquareFoot, String materialCost, String laborCost, 
            String tax, String total){
        System.out.printf("\t%-30s%-30s%-30s%-30s%-30s%-30s%-30s%-30s%-30s%-30s%-30s%-30s",
                orderNumber,  customerName,  state,  taxRate, productType,  area,  costPerSquareFoot, 
             laborCostPerSquareFoot,  materialCost,  laborCost, tax,  total);
        System.out.println();
    }

    public String readString(String prompt){
        Scanner keyboard = new Scanner(System.in);
        print(prompt);
        return keyboard.nextLine();
    }
    
    public BigDecimal readBigDecimal(String prompt){
        Scanner keyboard = new Scanner(System.in);
        print(prompt);
        BigDecimal bd=new BigDecimal("0.00");
        boolean loop=false;
        do{
            loop=false;
            try{
                System.out.println("\n\tPlease enter a Big Decimal:");
                bd=new BigDecimal(keyboard.nextLine());
            }catch(NumberFormatException ex){
                System.out.print("\tWarning! - This is not a Big Decimal.");
                loop = true;
            }
        }while(loop);
        
        return bd;
    }
    
    public String readBigDecimalForEdit(String prompt,String oldProperty){
        Scanner keyboard = new Scanner(System.in);
        print(prompt);
        BigDecimal bd=new BigDecimal("0.00");
        boolean loop=false;
        do{
            loop=false;
            try{
                System.out.println("\n\tPlease enter a Big Decimal:");
                String temp=keyboard.nextLine();
                if(temp.equals(""))
                    return oldProperty;
                bd=new BigDecimal(temp);
            }catch(NumberFormatException ex){
                System.out.print("\tWarning! - This is not a Big Decimal.");
                loop = true;
            }
        }while(loop);
        
        return bd.toString();
    }

    public int readInt(String prompt){
        Scanner keyboard = new Scanner(System.in);
        print(prompt); 
        int result=0;
        boolean loop = false;
        do{
            loop = false;
            try{
                System.out.println("\n\tPlease enter an integer:");
                result = Integer.parseInt(keyboard.nextLine());                
            }catch(NumberFormatException e){
                System.out.print("\tWarning! - This is not an integer.");
                loop = true;
            }
        }while(loop);
        return result;
    }

    public int readInt(String prompt, int min, int max){
        print(prompt); 
        Scanner keyboard = new Scanner(System.in);
        int result=0;
        boolean loop = false;
        boolean inputFormat = false;
        do{
            loop = false;
            try{
                do{
                    inputFormat = false;
                    System.out.println("\tPlease enter an integer between " + min + " and " + max);
                    result = Integer.parseInt(keyboard.nextLine());
                    if (result > max || result < min) {
                        System.out.println("\tWarning! - Out of Range.");
                        inputFormat = true;
                    }
                }while(inputFormat);               
            }catch(NumberFormatException e){
                System.out.println("\tWarning! - This is not an integer.");
                loop = true;
            }
        }while(loop);
        
        return result;
    }

    public double readDouble(String prompt){
        Scanner keyboard = new Scanner(System.in);
        print(prompt);      
        double result=0;
        boolean loop = false;
        do{
            loop = false;
            try{
                System.out.println("\tPlease enter a double:");
                result = Double.parseDouble(keyboard.nextLine());                
            }catch(NumberFormatException e){
                System.out.print("\tWarning! - This is not a double.");
                loop = true;
            }
        }while(loop);
        return result;

    }

    public double readDouble(String prompt, double min, double max){
        print(prompt); 
        Scanner keyboard = new Scanner(System.in);
        double result=0;
        boolean loop = false;
        boolean inputFormat = false;
        do{
            loop = false;
            try{
                do{
                    inputFormat = false;
                    System.out.println("\tPlease enter a double between " + min + " and " + max);
                    result = Double.parseDouble(keyboard.nextLine());
                    if (result > max || result < min) {
                        System.out.println("\tWarning! - Out of Range.");
                        inputFormat = true;
                    }
                }while(inputFormat);               
            }catch(NumberFormatException e){
                System.out.println("\tPlease enter a double.");
                loop = true;
            }
        }while(loop);
        
        return result;
        
    }

    public float readFloat(String prompt){
        Scanner keyboard = new Scanner(System.in);
        print(prompt); 
        float result=0;
        boolean loop = false;
        do{
            loop = false;
            try{
                System.out.println("\tPlease enter a float:");
                result = Float.parseFloat(keyboard.nextLine());               
            }catch(NumberFormatException e){
                System.out.print("\tWarning! - This is not a float.");
                loop = true;
            }
        }while(loop);
        return result;
    }

    public float readFloat(String prompt, float min, float max){
        print(prompt); 
        Scanner keyboard = new Scanner(System.in);
        float result=0;
        boolean loop = false;
        boolean inputFormat = false;
        do{
            loop = false;
            try{
                do{
                    inputFormat = false;
                    System.out.println("\tPlease enter a float between " + min + " and " + max);
                    result = Float.parseFloat(keyboard.nextLine());
                    if (result > max || result < min) {
                        System.out.println("\tWarning! - Out of Range.");
                        inputFormat = true;
                    }
                }while(inputFormat);               
            }catch(NumberFormatException e){
                System.out.println("\tPlease enter a float.");
                loop = true;
            }
        }while(loop);
        
        return result;
      
    }

    public long readLong(String prompt){
        Scanner keyboard = new Scanner(System.in);
        print(prompt);
        long result=0;
        boolean loop = false;
        do{
            loop = false;
            try{
                System.out.println("\tPlease enter a long integer:");
                result = Long.parseLong(keyboard.nextLine());           
            }catch(NumberFormatException e){
                System.out.print("\tWarning! - This is not a long integer.");
                loop = true;
            }
        }while(loop);
        return result;

    }

    public long readLong(String prompt, long min, long max){
        print(prompt); 
        Scanner keyboard = new Scanner(System.in);
        long result=0;
        boolean loop = false;
        boolean inputFormat = false;
        do{
            loop = false;
            try{
                do{
                    inputFormat = false;
                    System.out.println("Please enter a long integer between " + min + " and " + max);
                    result = Long.parseLong(keyboard.nextLine());
                    if (result > max || result < min) {
                        System.out.println("\tWarning! - Out of Range.");
                        inputFormat = true;
                    }
                }while(inputFormat);               
            }catch(NumberFormatException e){
                System.out.println("Please enter a long integer.");
                loop = true;
            }
        }while(loop);
        
        return result;
        
    }
    
    
}
