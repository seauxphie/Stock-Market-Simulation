/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockmarket;

import java.util.ArrayList;
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
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import static javafx.scene.layout.GridPane.setColumnSpan;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 *The window with all of the parameters of a <code>Fund</code> object.
 */
public class FundInfo {
    
    private static Fund fund;
    
    public static void display(Fund f) {
        fund = f;
        Stage stage = new Stage();
        stage.setTitle(fund.getFundName());
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets (5, 10, 10, 5));

        Label info = new Label("Fund Details");
        grid.add(info, 0, 0);

        Label name = new Label("Name: ");
        Label nameContent = new Label(fund.getFundName());
        grid.add(name, 0, 1);
        grid.add(nameContent, 1, 1);

        Label manager = new Label("Manager: ");
        Label managerContent = new Label(fund.getFullName());
        grid.add(manager, 0, 2);
        grid.add(managerContent, 1, 2);

        Label currentPrice = new Label("Budget: ");
        Label currentContent = new Label(Double.toString(fund.getBudget()));
        grid.add(currentPrice, 0, 3);
        grid.add(currentContent, 1, 3);
        
        Label tableTitle = new Label ("Assets:");
        setColumnSpan(tableTitle, 2);
        grid.add (tableTitle, 0, 4);
        
        HashMap<String, String> assets = new HashMap<>();
        fund.getCapital().forEach((Asset asset, Double amount) -> {
            assets.put(asset.getName(), amount.toString());
        });
        
        ObservableList<Map.Entry<String, String>> assetsObsList = FXCollections.observableArrayList(assets.entrySet());
        
        TableView<Map.Entry<String, String>> assetTableView = new TableView<>();
        TableColumn<Map.Entry<String, String>, String> nameColumn = new TableColumn<>("Name");
        TableColumn<Map.Entry<String, String>, String> amountColumn = new TableColumn<>("Amount");
        
        nameColumn.setCellValueFactory
        ((TableColumn.CellDataFeatures<Map.Entry<String, String>, String> a) -> new ReadOnlyObjectWrapper(a.getValue().getKey()));

        amountColumn.setCellValueFactory
                ((TableColumn.CellDataFeatures<Map.Entry<String, String>, String> a) -> new ReadOnlyObjectWrapper((a.getValue()).getValue()));

        assetTableView.setItems(assetsObsList);
        assetTableView.getColumns().add(nameColumn);
        assetTableView.getColumns().add(amountColumn);
        grid.add (assetTableView, 0, 5);
        
        Label memberTitle = new Label ("Members:");
        setColumnSpan(memberTitle, 2);
        grid.add (memberTitle, 0, 6);
        
        ListView<Investor> memberListView = new ListView<>();
        memberListView.setEditable(true);
        
        ArrayList<Investor> members = new ArrayList<>();
        
        members.addAll(fund.getMembers());
        
        ObservableList<Investor> memberObsList = FXCollections.observableArrayList(members);
        
        memberListView.setItems(memberObsList);
        
        memberListView.setCellFactory((ListView<Investor> p) -> {
                ListCell<Investor> cell = new ListCell<Investor>(){

                    @Override
                    protected void updateItem(Investor a, boolean bln) {
                        super.updateItem(a, bln);
                        if (a != null) {
                            setText(a.getFullName());
                        }
                    }
                };                   
                return cell;
            });
        grid.add(memberListView, 0, 7);
        
        
         Button removeButton =  new Button("Remove");
        removeButton.setOnAction(e -> {
            Main.fundList.remove(fund);
            int j=0;
            Thread fThread = Main.fundThreadList.get(j++);
            while (((FundThread) fThread).getFund()!=fund) fThread = Main.fundThreadList.get(j++);
            fThread.interrupt();
            fund=null;
            stage.close();
        });
        
        setColumnSpan(removeButton, 2);
        
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e-> stage.close());
        closeButton.setMinWidth(80);
        
        HBox buttonContainer = new HBox();
        buttonContainer.getChildren().addAll(removeButton, closeButton);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setMinWidth(300);
        buttonContainer.setSpacing(30);
              
        setColumnSpan(buttonContainer, 2);
        setColumnSpan(memberListView, 2);
        setColumnSpan(assetTableView, 2);

        Scene scene = new Scene(grid, 320, 600);
        stage.setScene(scene);
        stage.show();
    }
    
}
