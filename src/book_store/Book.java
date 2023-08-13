/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package book_store;

import javafx.scene.control.CheckBox;

/**
 *
 * @author biyen
 */

public class Book {
   public CheckBox select; // checkbox for the tableview
   private  String bookName;
   
   int price;
   
   
    public Book(String book_name, int price){ 
        bookName = book_name;
        this.price = price;
        this.select = new CheckBox();
    }

    public String getBookName(){ 
        return bookName;
    }
    
    public void setBookName(String bname){ 
       bookName = bname;
    }

    public int getPrice(){ 
        return price;
    }
    public void setPrice (int p){ 
        this.price = p;
    }

    public CheckBox getSelect(){ 
        return select;
    }
    public void setSelect(CheckBox select){ 
        this.select = select;
    }

}
