/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wholesaleinventorysystem;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;


/**
 *
 * @author josephine
 */
public class Suppliers {
    TextField supplierNameField,supplierPhoneField,emailField,supplierAddressField;
    PreparedStatement pst=null;
    ResultSet rs=null;
    Connection conn=null;
    TableView<ViewSuppliers> suppliersTable = new TableView<>();
    final ObservableList<ViewSuppliers> suppliersData = FXCollections.observableArrayList();
    Users users=new Users();
   
    public TabPane suppliersTab() {
       conn = DbConnect.getConnection();
       viewSuppliers();       
        TabPane supplierPane = new TabPane();
        Tab addSupplier = new Tab("Add supplier");
        Tab viewSupplier = new Tab("View suppliers");

        Label supplierLbl = new Label("Enter Suppliers details to register");
        supplierLbl.setStyle("-fx-text-fill:white;");
        supplierNameField = new TextField();
        supplierNameField.setMaxWidth(220);
        supplierNameField.setPromptText("Supplier name");
        supplierPhoneField= new TextField();
        supplierPhoneField.setMaxWidth(220);
        supplierPhoneField.setPromptText("Phone number");
        emailField = new TextField();
        emailField.setMaxWidth(220);
        emailField.setPromptText("Email address");
        supplierAddressField = new TextField();
        supplierAddressField.setMaxWidth(220);
        supplierAddressField.setPromptText("Address");
        Button addSuppliers = new Button("save");
        addSuppliers.setMaxWidth(100);
        addSuppliers.setStyle("-fx-font-size:16");
        addSuppliers.setOnAction(e -> {
            String phone = supplierPhoneField.getText();
            if (valPhone(phone) & validateEmail()) {
                try {
                    String query = "INSERT INTO Supplier(SupplierName,PhoneNumber,Email,Address) VALUES(?,?,?,?)";
                    pst = conn.prepareStatement(query);
                    pst.setString(1, supplierNameField.getText());
                    pst.setString(2, phone);
                    pst.setString(3, emailField.getText());
                    pst.setString(4, supplierAddressField.getText());
                    pst.execute();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("Supplier has been registered");
                    alert.showAndWait();
                   // clearFields();
                } catch (Exception ex) {
                    System.err.println("supplier Error: \n" + ex.toString());
                } finally {
                    try {
                        pst.close();
                        //conn.close();
                    } catch (Exception ex) {
                    }

                }
            } /*else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information dialog");
                alert.setHeaderText(null);
                alert.setContentText("invalid phone number");
                alert.showAndWait();
            }*/

        });

        VBox vbox1 = new VBox(10);
        vbox1.setPadding(new Insets(10, 10, 10, 10));
        vbox1.getChildren().addAll(supplierLbl, supplierNameField, supplierPhoneField, emailField, supplierAddressField, addSuppliers);
        vbox1.setAlignment(Pos.CENTER);
        addSupplier.setContent(vbox1);

        //create column supplier name to diplay names of suppliers registered in the database
        TableColumn<ViewSuppliers,String> snameColumn = new TableColumn<>("Supplier Name");
        snameColumn.setMinWidth(150);
        snameColumn.setCellValueFactory(new PropertyValueFactory<>("supplierName"));

        //set column for suppliers phone numbers
        TableColumn<ViewSuppliers,String> phoneColumn = new TableColumn<>("Phone number");
        phoneColumn.setMinWidth(150);
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        //set column for displaying suppliers email
        TableColumn<ViewSuppliers,String> emailColumn = new TableColumn<>("Email");
        emailColumn.setMinWidth(150);
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        //set column for displaying suppliers adress
        TableColumn<ViewSuppliers,String> addressColumn = new TableColumn<>("Address");
        addressColumn.setMinWidth(150);
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        //add all columns to the table
        suppliersTable.getColumns().addAll(snameColumn, phoneColumn, emailColumn, addressColumn);
       
        //set layout
        VBox supplierTableLayout = new VBox(8);
        supplierTableLayout.setPadding(new Insets(10, 10, 10, 10));
        supplierTableLayout.getChildren().addAll(suppliersTable);
        viewSupplier.setContent(supplierTableLayout);

        //adding tabs to tabpane layout
        supplierPane.getTabs().addAll(addSupplier, viewSupplier);
        return supplierPane;
    }
   
    //******************************phone number validation**************************************************
   
   public static boolean valPhone(String pn) {
        if(pn.charAt(0) == '0' && pn.length() == 10 && pn.matches("[0-9]+")){
            return true;
        }
        else{
             Alert alert1=new Alert(Alert.AlertType.WARNING);
                alert1.setTitle("Information dialog");
                alert1.setHeaderText(null);
                alert1.setContentText("Invalid phonenumber");
                alert1.showAndWait();
        }
        return false;
    }

    //****************************************************************************************************
     public void viewSuppliers() {
            try {
                suppliersData.clear();
                String query = "select SupplierName,PhoneNumber,Email,Address from supplier";
                pst = conn.prepareStatement(query);
                rs = pst.executeQuery();
               
                while (rs.next()) {
                    suppliersData.add(new ViewSuppliers(
                            rs.getString("SupplierName"),
                            rs.getString("PhoneNumber"),
                            rs.getString("Email"),
                            rs.getString("Address")
                    ));
                    suppliersTable.setItems(suppliersData);
                }
                pst.close();
                rs.close();
            } catch (Exception ex1) {
                System.err.println(ex1);
            }
            suppliersTable.refresh();
        }

      public  boolean validateEmail(){
            Pattern p=Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
            Matcher m= p.matcher(emailField.getText());
            if(m.find() && m.group().equals(emailField.getText())){
                        return true;
            }
            else{
               Alert alert1=new Alert(Alert.AlertType.WARNING);
                alert1.setTitle("Information dialog");
                alert1.setHeaderText(null);
                alert1.setContentText("Invalid email address");
                alert1.showAndWait();
            }
            return false;
        }
      
      
    
    public void clearFields(){
           
           supplierNameField.clear();
           supplierPhoneField.clear();
           emailField.clear();
           supplierAddressField.clear();
          
          
        }
}
