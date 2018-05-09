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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;



/**
 *
 * @author josephine
 */
public class Users  {
    
TextField fNameField,lNameField,phoneField,emailField,userField,roleField;
PasswordField passField;
PreparedStatement statement=null;
ResultSet rs=null;
Connection conn=null;
TableView<ViewUsers>  usersTable = new TableView<>();
final ObservableList<ViewUsers> usersData = FXCollections.observableArrayList();

   public TabPane usersTab() {
       viewUsers();
        TabPane userPane = new TabPane();

        Tab addUser = new Tab("Create user account");
        Tab viewUser = new Tab("View users");

        Label fNameLabel = new Label("First Name");
        fNameField = new TextField();
        
        Label lNameLabel = new Label("Last name");
        lNameField = new TextField();
        Label phoneLabel = new Label("Phone number");

        phoneField = new TextField();
        Label emailLabel = new Label("Email");

        emailField = new TextField();
        Label userLabel = new Label("Username");

        userField = new TextField();
        Label passLabel = new Label("Password");

        passField = new PasswordField();
        Label roleLabel = new Label("User role");

        ComboBox roleCombo = new ComboBox();
        roleCombo.getItems().addAll(
                "admin",
                "operator"
        );

        roleCombo.setPromptText("Select user role");

        roleCombo.setEditable(true);
        Button saveUserButton = new Button("Save");
        saveUserButton.setMaxWidth(100);
        saveUserButton.setStyle("-fx-font-size:16");
        saveUserButton.setMaxWidth(220);
        
        saveUserButton.setOnAction(e -> {
          String phone = phoneField.getText();
            if (valPhone(phone) & validateEmail()) {
            try {
                String query = "INSERT INTO Users(FirstName,LastName,PhoneNumber,Email,Username,Password,Role) VALUES(?,?,?,?,?,?,?)";
                conn = DbConnect.getConnection();
                statement = conn.prepareStatement(query);
                statement.setString(1, fNameField.getText());
                statement.setString(2, lNameField.getText());
                statement.setString(3, phone);
                statement.setString(4, emailField.getText());
                statement.setString(5, userField.getText());
                statement.setString(6, passField.getText());
                statement.setString(7, roleCombo.getSelectionModel().getSelectedItem().toString());
                statement.execute();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information dialog");
                alert.setHeaderText(null);
                alert.setContentText("User has been created");
                alert.showAndWait();
                clearFields();
            } catch (Exception ex) {
                System.err.println("Error: \n" + ex.toString());
            } finally {
                try {
                    statement.close();
                    //conn.close();
                } catch (Exception ex) {
                }

            }
            }
            
        });

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 5, 5, 5));
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5);
        gridPane.setVgap(8);

        //add to layout gridpane
        gridPane.add(fNameLabel, 0, 0, 1, 1);
        gridPane.add(fNameField, 1, 0, 1, 1);
        gridPane.add(lNameLabel, 0, 1, 1, 1);
        gridPane.add(lNameField, 1, 1, 1, 1);
        gridPane.add(phoneLabel, 0, 2, 1, 1);
        gridPane.add(phoneField, 1, 2, 1, 1);
        gridPane.add(emailLabel, 0, 3, 1, 1);
        gridPane.add(emailField, 1, 3, 1, 1);
        gridPane.add(userLabel, 0, 4, 1, 1);
        gridPane.add(userField, 1, 4, 1, 1);
        gridPane.add(passLabel, 0, 5, 1, 1);
        gridPane.add(passField, 1, 5, 1, 1);
        gridPane.add(roleLabel, 0, 6, 1, 1);
        gridPane.add(roleCombo, 1, 6, 1, 1);
        gridPane.add(saveUserButton, 1, 7, 1, 1);

        addUser.setContent(gridPane);
        
        //===============================table for displaying all users===================================
       
        
        TableColumn<ViewUsers,String> fnameColumn = new TableColumn<>("FIRST NAME");
        fnameColumn.setMinWidth(150);
        fnameColumn.setCellValueFactory(new PropertyValueFactory<>("fName"));

        TableColumn<ViewUsers,String> lnameColumn = new TableColumn<>("LAST NAME");
        lnameColumn.setMinWidth(150);
        lnameColumn.setCellValueFactory(new PropertyValueFactory<>("lName"));

        TableColumn<ViewUsers,String> phoneColumn = new TableColumn<>("PHONENUMBER");
        phoneColumn.setMinWidth(150);
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        TableColumn<ViewUsers,String> emailColumn = new TableColumn<>("EMAIL");
        emailColumn.setMinWidth(200);
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<ViewUsers,String> userColumn = new TableColumn<>("USERNAME");
        userColumn.setMinWidth(100);
        userColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<ViewUsers,String> passColumn = new TableColumn<>("PASSWORD");
        passColumn.setMinWidth(100);
        passColumn.setCellValueFactory(new PropertyValueFactory<>("password"));

        TableColumn<ViewUsers,String> roleColumn = new TableColumn<>("ROLE");
        roleColumn.setMinWidth(100);
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        usersTable.getColumns().addAll(fnameColumn, lnameColumn, phoneColumn, emailColumn, userColumn, passColumn, roleColumn);

        //=================================end of users table===================================================

       
        VBox userBox = new VBox(8);
        userBox.getChildren().addAll(usersTable);
        userBox.setPadding(new Insets(10, 10, 10, 10));
        viewUser.setContent(userBox);
        userPane.getTabs().addAll(addUser, viewUser);
        return userPane;
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
    public void viewUsers(){
        usersData.clear();
            try {
                String query = "select FirstName,LastName,PhoneNumber,Email,Username,Password,Role from users";
                conn = DbConnect.getConnection();
                statement = conn.prepareStatement(query);
                rs = statement.executeQuery();
                while (rs.next()) {
                    usersData.add(new ViewUsers(
                            rs.getString("FirstName"),
                            rs.getString("LastName"),
                            rs.getString("PhoneNumber"),
                            rs.getString("Email"),
                            rs.getString("Username"),
                            rs.getString("Password"),
                            rs.getString("Role")
                    ));
                    usersTable.setItems(usersData);
                }
                //statement.close();
                //rs.close();
                //conn.close();
            } catch (Exception ex1) {
                System.err.println(ex1);
            }
             usersTable.refresh();
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
           
           fNameField.clear();
           lNameField.clear();
           phoneField.clear();
           emailField.clear();
           userField.clear();
           passField.clear();
           roleField.clear();
          
          
        }
}