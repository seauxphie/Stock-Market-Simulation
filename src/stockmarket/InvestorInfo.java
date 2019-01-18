/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockmarket;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import static javafx.scene.layout.GridPane.setColumnSpan;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 *The window which displays the parameters of an instance of <code>Investor</code>
 */
public class InvestorInfo {
    
    public static Investor investor;
    
        public static void display(Investor i) {
        investor = i;
        Stage stage = new Stage();
        stage.setTitle(investor.getFullName());
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets (5, 10, 10, 5));

        Label info = new Label("Investor Details");
        grid.add(info, 0, 0);

        Label name = new Label("Name: ");
        Label nameContent = new Label(investor.getFullName());
        grid.add(name, 0, 1);
        grid.add(nameContent, 1, 1);

        Label pesel = new Label("PESEL: ");
        Label peselContent = new Label(investor.getPESEL());
        grid.add(pesel, 0, 2);
        grid.add(peselContent, 1, 2);

        Label currentPrice = new Label("Budget: ");
        Label currentContent = new Label(Double.toString(investor.getBudget()));
        grid.add(currentPrice, 0, 3);
        grid.add(currentContent, 1, 3);
        
        Label tableTitle = new Label ("Assets");
        setColumnSpan(tableTitle, 2);
        grid.add (tableTitle, 0, 4);
        
        HashMap<String, String> assets = new HashMap<>();
        try {
        investor.getCapital().forEach((Asset asset, Double amount) -> {
            assets.put(asset.getName(), amount.toString());
        });
        } catch(ConcurrentModificationException exception) {
            
        }
        
        ObservableList<Map.Entry<String, String>> assetsObsList = FXCollections.observableArrayList(assets.entrySet());
        
        TableView<Map.Entry<String, String>> table = new TableView<>();
        TableColumn<Map.Entry<String, String>, String> nameColumn = new TableColumn<>("Name");
        TableColumn<Map.Entry<String, String>, String> amountColumn = new TableColumn<>("Amount");
        
        nameColumn.setCellValueFactory
        ((CellDataFeatures<Map.Entry<String, String>, String> a) -> new ReadOnlyObjectWrapper(a.getValue().getKey()));

        amountColumn.setCellValueFactory
                ((CellDataFeatures<Map.Entry<String, String>, String> a) -> new ReadOnlyObjectWrapper((a.getValue()).getValue()));
        
        table.setItems(assetsObsList);
        table.getColumns().add(nameColumn);
        table.getColumns().add(amountColumn);
        setColumnSpan(table, 2);
        
        grid.add (table, 0, 5);
        Button removeButton =  new Button("Remove");
        removeButton.setOnAction(e -> {
            Main.investorList.remove(investor);
            int j=0;
            Thread iThread = Main.investorThreadList.get(j++);
            while (((InvestorThread) iThread).getInvestor()!=investor) iThread = Main.investorThreadList.get(j++);
            iThread.interrupt();
            investor = null;
            stage.close();
        });
        
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e-> stage.close());
        closeButton.setMinWidth(80);
        
        HBox buttonContainer = new HBox();
        buttonContainer.getChildren().addAll(removeButton, closeButton);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setMinWidth(300);
        buttonContainer.setSpacing(30);
        
        setColumnSpan(buttonContainer, 2);
                
        grid.add(buttonContainer, 0, 6);
        

        Scene scene = new Scene(grid, 320, 400);
        stage.setScene(scene);
        stage.show();
    }
        
    
    
}
