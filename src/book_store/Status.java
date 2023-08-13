/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package book_store;

/**
 *
 * @author biyen
 */
public abstract class Status {
    
    public abstract String getStatus();
    
    public abstract void changeStatus(Customer cust);
    
    public abstract int Redeem_BuyBook(Customer cust, int iniCost, boolean ap);
    
}
