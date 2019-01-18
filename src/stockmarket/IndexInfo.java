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
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import static javafx.scene.layout.GridPane.setColumnSpan;
import javafx.stage.Stage;
/**
 *The window which displays the parameters of an instance of <code>Index</code>
 */
public class IndexInfo {
    
    public static void display(Index index) {
        Stage stage = new Stage();
        stage.setTitle(index.getName());
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets (5, 10, 10, 5));

        Label info = new Label("Index Details");
        grid.add(info, 0, 0);

        Label name = new Label("Name: ");
        Label nameContent = new Label(index.getName());
        grid.add(name, 0, 1);
        grid.add(nameContent, 1, 1);

        Label exchange = new Label("Exchange: ");
        Label exchangeContent = new Label(index.getExchange().getName());
        grid.add(exchange, 0, 2);
        grid.add(exchangeContent, 1, 2);
        
        ObservableList compList = FXCollections.observableArrayList(index.getCompanies());
        
        ListView<Company> companyListView = new ListView<>();
        companyListView.setEditable(true);
           companyListView.setPrefHeight(200);
           companyListView.setEditable(true);
           companyListView.setItems(compList);
        setColumnSpan(companyListView, 2);
        grid.add(companyListView, 0, 5);
        
        companyListView.setCellFactory((ListView<Company> p) -> {
                ListCell<Company> cell = new ListCell<Company>(){

                    @Override
                    protected void updateItem(Company a, boolean bln) {
                        super.updateItem(a, bln);
                        if (a != null) {
                            setText(a.getName());
                        }
                    }
                };                   
                return cell;
            });
        

        Label currentValue = new Label("Value: ");
        Label currentContent = new Label(Double.toString(index.getValue()));
        grid.add(currentValue, 0, 3);
        grid.add(currentContent, 1, 3);
        
        Scene scene = new Scene(grid);
        stage.setScene(scene);
        stage.show();
    }
    
}
