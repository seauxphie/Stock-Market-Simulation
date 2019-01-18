/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockmarket;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import static javafx.scene.layout.GridPane.setColumnSpan;
import javafx.scene.layout.HBox;
import javafx.stage.*;

/**
 *The window which is displayed when the user wants to add an object.
 * 
 */
public class ObjectAdderWindow {
    
    public static void display() {
        Stage controlStage = new Stage();
        
        controlStage.setMinWidth(480);
        controlStage.setMinHeight(128);
        controlStage.setTitle("Add A New Object");
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);
        
        Button cancelBtn = new Button("Close");

        
        HBox hbCancel = new HBox(5);
        hbCancel.setAlignment(Pos.CENTER);
        hbCancel.getChildren().add(cancelBtn);
        grid.add(hbCancel, 1, 4);
        
        cancelBtn.setOnAction (e-> {
           controlStage.close();
        });    
        
        Label message = new Label("Select the type of the object:");

        grid.add(message, 0, 1);
        
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.setPrefHeight(20);
        ObservableList<String> types = FXCollections.observableArrayList("Exchange", "Index", "Company", "Currency",
                "Raw Material", "Investor", "Investment Fund");
        
        choiceBox.setItems(types);
        grid.add(choiceBox, 0, 2);
                
        Label nameLabel = new Label("Name (optional):");
        grid.add(nameLabel, 1, 1);
        
        TextField nameText = new TextField();
        grid.add(nameText, 1, 2);
        
        Button nextBtn = new Button("Next");
        
        HBox hbNext = new HBox(5);
        hbNext.setAlignment(Pos.CENTER);
        hbNext.getChildren().add(nextBtn);
        grid.add(hbNext, 0, 4);
        
           
        Scene scene = new Scene(grid);
        controlStage.setScene(scene);
        controlStage.show();
        
        nextBtn.setOnAction ((ActionEvent e) -> {
            String type = choiceBox.getSelectionModel().getSelectedItem();
            String name="";
            if (!nameText.getText().isEmpty() && nameText.getText() !=  null) name = nameText.getText();
            switch(type) {
                case "Index":
                    if (Main.exchangeList.isEmpty()) ExceptionWindow.display("Add an exchange first");
                    else {
                        name = "";
                        if (!nameText.getText().isEmpty() && nameText.getText() !=  null) name = nameText.getText();
                        createIndex(name);
                    }
                    break;
                default:
                    createObject(type, name);
                    
                    break;
            }          
        });
        
       

    }
    
    private static void createIndex(String name) {
        try {
        Index index = new Index(name);
        Exchange e = Main.exchangeList.get(new Random().nextInt(Main.exchangeList.size()));
        e.addIndex(index);
       
        }
        catch(NoExchangesException exception) {
            System.out.println(exception.getMessage());
        } catch (Exception ex) {
            ExceptionWindow.display(ex.getMessage());
        }
        
    }
    
    private static void createObject(String type, String name)  {
         try {
            switch (type) {
                case "Exchange":
                    Exchange e = new Exchange(name);
                    Main.exchangeList.add(e);
                    //ExceptionWindow.display("Exchange "+e.getName()+" successfully created!");
                    break;

                case "Company":
                    try {
                    Company c = new Company(name);
                    Main.companyList.add(c);
                    Thread cth = new CompanyThread(c);
                    cth.start();
                    Main.companyThreadList.add(cth);
                    //ExceptionWindow.display("Company "+c.getName()+" successfully created!");
                    }
                    catch(NoExchangesException exception) {
                        ExceptionWindow.display(exception.getMessage());
                    }
                    break;

                case "Currency":
                    Currency cu = new Currency(name);
                    Main.currencyList.add(cu);
                    Main.currencyMarket.add(cu);
                    //ExceptionWindow.display("Currency "+cu.getName()+" successfully created!");
                    break;

                case "Raw Material":
                    RawMaterial m = new RawMaterial(name);
                    Main.materialList.add(m);
                    Main.materialMarket.add(m);
                    //ExceptionWindow.display("Material "+m.getName()+" successfully created!");
                    break;

                case "Investor":
                    Investor i = new Investor(name);
                    Main.investorList.add(i);
                    Thread ith = new InvestorThread(i);
                    ith.start();
                    Main.investorThreadList.add(ith);
                    //ExceptionWindow.display("Investor "+i.getFullName()+" successfully created!");
                    break;

                case "Investment Fund":
                    Fund in = new Fund(name);
                     Main.fundList.add(in);
                     Thread inth = new FundThread(in);
                     inth.start();
                     Main.fundThreadList.add(inth);
                     //ExceptionWindow.display("Investment Fund "+in.getFundName()+" successfully created!");
                     break;

                default:
                    break;
            }
    
        } catch (NoNamesException e) {
                ExceptionWindow.display(e.getMessage());
            } catch (DoesntExistException ex) {
            ExceptionWindow.display(ex.getMessage());
        }
            
    }
    
    
}
