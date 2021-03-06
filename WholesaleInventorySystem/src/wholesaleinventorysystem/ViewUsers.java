/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wholesaleinventorysystem;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


/**
 *
 * @author evod
 */
public class ViewUsers{
    private final SimpleIntegerProperty userId;
    private final SimpleStringProperty fName;
    private final SimpleStringProperty lName;
    private final SimpleStringProperty phoneNumber;
    private final SimpleStringProperty email;
    private final SimpleStringProperty username;
    private final SimpleStringProperty password;
    private final SimpleStringProperty role;
    private final SimpleStringProperty status;
   
    
    
  
     ViewUsers(int id,String fname,String lname,String phone,String em,String user,String pass,String rol,String sts){
         this.userId=new SimpleIntegerProperty(id);
        this.fName=new SimpleStringProperty (fname);
        this.lName=new SimpleStringProperty (lname);
        this.phoneNumber=new SimpleStringProperty (phone);
        this.email=new SimpleStringProperty (em);
        this.username= new SimpleStringProperty (user);
        this.password=new SimpleStringProperty (pass);
        this.role=new SimpleStringProperty (rol);
          this.status=new SimpleStringProperty (sts);
        
    }
  
    public SimpleIntegerProperty getUserIdProperty(){
        return userId;
    }
     
     public SimpleStringProperty getFNameProperty(){
        return fName;
    }
   
    public SimpleStringProperty getLNameProperty(){
        return lName;
    }
   
     public SimpleStringProperty getPhoneNumberProperty(){
        return phoneNumber;
    }
    
     public SimpleStringProperty getEmailProperty(){
        return email;
    }
   
     public SimpleStringProperty getUsernameProperty(){
        return username;
    }
   
    
     public SimpleStringProperty getPasswordProperty(){
        return password;
    }
   
    
     public SimpleStringProperty getRoleProperty(){
        return role;
    }
      public SimpleStringProperty getStatusProperty(){
        return status;
    }
   //getters
     public int getUserId(){
        return this.userId.get();
    }
      public String getFName(){
        return this.fName.get();
    }
      public String getLName(){
        return this.lName.get();
    }
      public String getPhoneNumber(){
        return this.phoneNumber.get();
    }
    
    public String getEmail(){
        return this.email.get();
    }
    public String getUsername(){
        return this.username.get();
    }
    
    public String getPassword(){
        return this.password.get();
    }
     public String getRole(){
        return this.role.get();
    }
     public String getStatus(){
        return this.status.get();
    }
    //setters
     public void setUserId(int id){
        this.userId.set(id);
    }
      public void setFName(String fname){
        this.fName.set(fname);
    }
     public void setLName(String lname){
      this.lName.set(lname);
    }
      public void setPhoneNumber(String phone){
        this.phoneNumber.set(phone);
    }
    public void setEmail(String em){
        this.email.set(em);
    }
    public void setUsername(String user){
      this.username.set(user);
    }
      public void setPassword(String pass){
        this.password.set(pass);
    }
    public void setRole(String rol){
        this.role.set(rol);
    }
    
}
