/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wholesaleinventorysystem;

import java.awt.Image;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author josephine
 */
public class EmployeePage extends Application {
    Scene scene;
    @Override
    public void start(Stage primaryStage) {
    TabsClass tabs=new TabsClass();
    BorderPane border=new BorderPane();
    scene = new Scene(border, 900, 500);
        
    VBox leftMenu=new VBox(8);
    leftMenu.setStyle("-fx-background-color:cyan;");
    leftMenu.prefWidthProperty().bind(scene.widthProperty().divide(4));
    leftMenu.setPadding(new Insets(20, 5, 5, 10));        
    
    Label label1=new Label("Sales");
    label1.setOnMouseClicked(e -> {
    border.setCenter(tabs.empSalesTab());
    });
           
    Label label2=new Label("Customers");
    label2.setOnMouseClicked(e -> {
    border.setCenter(tabs.customersTab());
    });
    leftMenu.getChildren().addAll(label1,label2);
    border.setLeft(leftMenu);
           
    Label label3=new Label("Logout");
    HBox bottomMenu=new HBox();
    bottomMenu.setStyle("-fx-background-color:gray;");
    bottomMenu.setPadding(new Insets(10, 10, 10, 10));
    bottomMenu.getChildren().add(label3);
    border.setBottom(bottomMenu);
           
    Label iconLbl=new Label("Icon stays here..!");
    
         
        
    StackPane stack=new StackPane();
    stack.setStyle("-fx-background-color:gray;");
    stack.getChildren().add(iconLbl);
    border.setTop(stack);
           
        
        
    scene.getStylesheets().add(getClass().getResource("Stylish.css").toExternalForm());
        
    primaryStage.setTitle("Employee page");
    primaryStage.setScene(scene);
    primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
