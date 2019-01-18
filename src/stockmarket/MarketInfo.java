/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockmarket;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import static stockmarket.Main.companyList;
import static stockmarket.Main.currencyList;
import static stockmarket.Main.materialList;


/**
 *Window which displays the parameters of a market
 */
public class MarketInfo 
{
 
    /**
     *Displays information about the <code>RawMaterialMarket</code>.
     * @param rmMarket  the market
     */
    public static void display(RawMaterialMarket rmMarket ) {
        
        Stage stage = new Stage();
        stage.setTitle(rmMarket.getName());
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets (5, 10, 10, 5));

        Label info = new Label("Raw Material Market Details");
        GridPane.setColumnSpan(info, 2);
        grid.add(info, 0, 0);

        Label markup = new Label("Markup: ");
        Label markupContent = new Label(Double.toString(rmMarket.getMarkup()));
        grid.add(markup, 0, 1);
        grid.add(markupContent, 1, 1);
        ListView<String> listView = new ListView<>();
        listView.setPrefHeight(200);
        listView.setEditable(true);
        
        ObservableList list = FXCollections.observableArrayList();
        for (int i=0; i<materialList.size(); i++) list.add(materialList.get(i).getName());
        
        listView.setItems(list);
        grid.add (listView, 0, 2);
        GridPane.setColumnSpan(listView, 2);
               

        Scene scene = new Scene(grid);
        stage.setScene(scene);
        stage.show();
    }
    
    /**
     *Displays information about the <code>CurrencyMarket</code>.
     * @param cMarket  the market
     */
    public static void display(CurrencyMarket cMarket) {
        
        Stage stage = new Stage();
        stage.setTitle(cMarket.getName());
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets (5, 10, 10, 5));

        Label info = new Label("Currency Market Details");
        GridPane.setColumnSpan(info, 2);
        grid.add(info, 0, 0);

        Label markup = new Label("Markup: ");
        Label markupContent = new Label(Double.toString(cMarket.getMarkup()));
        grid.add(markup, 0, 1);
        grid.add(markupContent, 1, 1);
        ListView<String> listView = new ListView<>();
        listView.setPrefHeight(200);
        listView.setEditable(true);
        
        ObservableList list = FXCollections.observableArrayList();
        for (int i=0; i<currencyList.size(); i++) list.add(currencyList.get(i).getName());
        
        listView.setItems(list);
        grid.add (listView, 0, 2);
        GridPane.setColumnSpan(listView, 2);
        
        
               

        Scene scene = new Scene(grid);
        stage.setScene(scene);
        stage.show();
        
    }
    
    /**
     *Displays information about an <code>Exchange</code>.
     * @param exchange  the exchange
     */
    public static void display(Exchange exchange) {
        
        Stage stage = new Stage();
        stage.setTitle(exchange.getName());
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets (5, 10, 10, 5));

        Label info = new Label("Exchange Details");
        GridPane.setColumnSpan(info, 2);
        grid.add(info, 0, 0);
        
        Label name = new Label("Name: ");
        Label nameContent = new Label(exchange.getName());
        grid.add(name, 0, 1);
        grid.add(nameContent, 1, 1);

        Label markup = new Label("Markup: ");
        Label markupContent = new Label(Double.toString(exchange.getMarkup()));
        grid.add(markup, 0, 2);
        grid.add(markupContent, 1, 2);
        
        Label country = new Label("Country: ");
        Label countryContent = new Label(exchange.getCountry());
        grid.add(country, 0, 3);
        grid.add(countryContent, 1, 3);
        
        Label city = new Label("City: ");
        Label cityContent = new Label(exchange.getCity());
        grid.add(city, 0, 4);
        grid.add(cityContent, 1, 4);
        
              
        Label companyListTitle = new Label("Companies");
        grid.add(companyListTitle, 0, 5);
        GridPane.setColumnSpan(companyListTitle, 2);
        
        ListView<Index> listView = new ListView<>();
        listView.setPrefHeight(200);
        listView.setEditable(true);
        
        ObservableList list = FXCollections.observableArrayList();
        for (int i=0; i<companyList.size(); i++) list.add(companyList.get(i).getName());
        
        listView.setItems(list);
        
        grid.add (listView, 0, 6);
        GridPane.setColumnSpan(listView, 2);
        
        Label indexListTitle = new Label("Indexes");
        grid.add(indexListTitle, 0, 7);
        GridPane.setColumnSpan(indexListTitle, 2);
        
        ListView<String> indexListView = new ListView<>();
        indexListView.setPrefHeight(200);
        indexListView.setEditable(true);
        
                
        ObservableList iList = FXCollections.observableArrayList();
        for (int i=0; i<exchange.getIndexes().size(); i++) iList.add(exchange.getIndexes().get(i).getName());
        
        indexListView.setItems(iList);
        
        grid.add(indexListView, 0, 8);
        GridPane.setColumnSpan(indexListView, 2);
               

        Scene scene = new Scene(grid, 300, 600);
        stage.setScene(scene);
        stage.show();
        
        
        
    }
    
    
    }
