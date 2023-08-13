/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package book_store;

/**
 *
 * @author biyen
 */
public class SilverStatus extends Status{
    
    public SilverStatus(){
        
    }
    @Override
    public String getStatus(){
        return "Silver";
    }

    @Override
    public void changeStatus(Customer cust) {
        System.out.println("Status Changed from Silver --> Gold");
        cust.setStatus(new GoldStatus());
    }
    
    @Override
    public int Redeem_BuyBook(Customer cust, int iniCost, boolean ap) {
        int TC =iniCost;
        int newPoints, pointsUsed;
        int iniPoints = cust.getPoints();
        int pointsNeeded = iniCost*100;
        
        if(ap){ //REDEEM AND BUY
            if(iniPoints == 0){
            TC = iniCost;
            newPoints = TC*10;
            cust.setPoints(newPoints);
        }
        else if(iniPoints >= pointsNeeded){
            newPoints = iniPoints - pointsNeeded;
            TC = 0;
            cust.setPoints(newPoints);
            
        } else if(iniPoints < pointsNeeded){
            pointsUsed = pointsNeeded - iniPoints;
            TC = pointsUsed/100;
            
            cust.setPoints(TC*10);
        }
        if((cust.getPoints() > 1000) && (cust.print_Status().equals("Silver"))){
                cust.switchStatus();
            } else if((cust.getPoints() < 1000) && (cust.print_Status().equals("Gold"))){
                cust.switchStatus();
            }
            
        return TC;
        
        } else{ // just buy
            
            if(iniPoints == 0){
            TC = iniCost;
            newPoints = TC*10;
            cust.setPoints(newPoints);
        } else {
                newPoints = cust.getPoints() + TC*10;
            cust.setPoints(newPoints);
            }
            
            
            return TC;
        }

    }
}
