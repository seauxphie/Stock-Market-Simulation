/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockmarket;


import java.time.LocalTime;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import static javafx.scene.layout.GridPane.setColumnSpan;
import javafx.stage.Stage;

/**
 *Window which shows information about an asset.
 */
public class AssetInfoWindow {
    
    public static void display(RawMaterial material) {
        Stage stage = new Stage();
        stage.setTitle(material.getName());
        
        GridPane grid = new GridPane();
        GridPane infoGrid = new GridPane();
        
        Label info = new Label("Raw Material Details");
        infoGrid.add(info, 0, 0);
        
        Label name = new Label("Name: ");
        Label nameContent = new Label(material.getName());
        infoGrid.add(name, 0, 1);
        infoGrid.add(nameContent, 1, 1);
        
        Label unit = new Label("Unit: ");
        Label unitContent = new Label(material.getUnit());
        infoGrid.add(unit, 0, 2);
        infoGrid.add(unitContent, 1, 2);
        
        Label currency = new Label("Currency: ");
        Label currencyContent = new Label(material.getCurrency().getName());
        infoGrid.add(currency, 0, 3);
        infoGrid.add(currencyContent, 1, 3);
        
        Label value = new Label("Value: ");
        Label valueContent = new Label(Double.toString(material.getValue()));
        infoGrid.add(value, 0, 4);
        infoGrid.add(valueContent, 1, 4);
        
        Label minv = new Label("Min. value: ");
        Label minvContent = new Label(Double.toString(material.getMinValue()));
        infoGrid.add(minv, 0, 5);
        infoGrid.add(minvContent, 1, 5);
        
        Label maxv = new Label("Max. value: ");
        Label maxvContent = new Label(Double.toString(material.getMaxValue()));
        infoGrid.add(maxv, 0, 6);
        infoGrid.add(maxvContent, 1, 6);
        
        
        //wykres
        
        NumberAxis x = new NumberAxis();
        NumberAxis y = new NumberAxis();
        
        x.setLabel("Time");
        y.setLabel("Value");
        
        LineChart<Number, Number> chart = new LineChart<>(x, y);
        chart.setTitle("Value of "+material.getName());
        XYChart.Series data = new XYChart.Series();

        long minTime=LocalTime.now().toNanoOfDay(), maxTime=0; 
        int size = material.getChartValues().size();

        for (int i=0; i<size; i++) {
            Double v = material.getChartValues().get(i);
            long time = material.getChartTimes().get(i);
            if(time<minTime) minTime=time;
            if(time>maxTime) maxTime=time;
            data.getData().add(new XYChart.Data(time, v));      
        }
        
        x.setAutoRanging(false);
        x.setLowerBound(minTime);
        x.setUpperBound(maxTime);
        
        chart.getData().add(data);
        chart.setLegendVisible(false);
        setColumnSpan(chart, 2);
        
        grid.add(infoGrid, 0, 0);
       
        grid.add(chart, 1, 0);
        grid.setMaxWidth(720);
        grid.setMaxHeight(400);
        Scene scene = new Scene(grid);
        stage.setScene(scene);
        stage.show();
    }
    
    public static void display(Currency currency) {
        Stage stage = new Stage();
        stage.setTitle(currency.getName());

        GridPane grid = new GridPane();
        GridPane infoGrid = new GridPane();
        
        Label info = new Label("Currency Details");
        infoGrid.add(info, 0, 0);
        
        Label name = new Label("Name: ");
        Label nameContent = new Label(currency.getName());
        infoGrid.add(name, 0, 1);
        infoGrid.add(nameContent, 1, 1);
        
        Label value = new Label("Basic currency rate: ");
        Label valueContent = new Label(Double.toString(currency.getBasicRate()));
        infoGrid.add(value, 0, 2);
        infoGrid.add(valueContent, 1, 2);
        
        HashMap<String, String> rates = new HashMap<>();
        try {
        currency.getRates().forEach((Currency c, Double rate) -> {
            rates.put(c.getName(), rate.toString());
        });
        } catch(ConcurrentModificationException exception) {
            
        }
        
        ObservableList<Map.Entry<String, String>> ratesObsList = FXCollections.observableArrayList(rates.entrySet());
        
        TableView<Map.Entry<String, String>> table = new TableView<>();
        TableColumn<Map.Entry<String, String>, String> nameColumn = new TableColumn<>("Currency name");
        TableColumn<Map.Entry<String, String>, String> amountColumn = new TableColumn<>("Rate");
        
        nameColumn.setCellValueFactory
        ((TableColumn.CellDataFeatures<Map.Entry<String, String>, String> a) -> new ReadOnlyObjectWrapper(a.getValue().getKey()));

        amountColumn.setCellValueFactory
                ((TableColumn.CellDataFeatures<Map.Entry<String, String>, String> a) -> new ReadOnlyObjectWrapper((a.getValue()).getValue()));
        
        table.setItems(ratesObsList);
        table.getColumns().add(nameColumn);
        table.getColumns().add(amountColumn);
        setColumnSpan(table, 2);
        
        infoGrid.add(table, 0, 3);
        
        Label listTitle = new Label ("Countries:");
        setColumnSpan(listTitle, 2);
        infoGrid.add (listTitle, 0, 4);
        
        ListView<String> countryListView = new ListView<>();
        countryListView.setEditable(true);

        ObservableList<String> couObsList = FXCollections.observableArrayList(currency.getCountries());
        
        countryListView.setItems(couObsList);
        
       
        infoGrid.add(countryListView, 0, 5);
        
        //wykres
        
        
        NumberAxis x = new NumberAxis();
        NumberAxis y = new NumberAxis();
        
        x.setLabel("Time");
        y.setLabel("Value");
        
        LineChart<Number, Number> chart = new LineChart<>(x, y);
        
        chart.setTitle("Value of "+currency.getName());

        XYChart.Series data = new XYChart.Series();

        long minTime=LocalTime.now().toNanoOfDay(), maxTime=0; 

        int size = currency.getChartValues().size();

        for (int i=0; i<size; i++) {
            Double v = currency.getChartValues().get(i);
            long time = currency.getChartTimes().get(i);
            if(time<minTime) minTime=time;
            if(time>maxTime) maxTime=time;
            data.getData().add(new XYChart.Data(time, v));
        }
        
        x.setAutoRanging(false);
        x.setLowerBound(minTime);
        x.setUpperBound(maxTime);

        chart.getData().add(data);
        
        setColumnSpan(chart, 2);
        
        chart.setLegendVisible(false);

        grid.add(infoGrid, 0, 0);
       
        grid.add(chart, 1, 0);
        grid.setMaxWidth(720);
        grid.setMaxHeight(400);
        Scene scene = new Scene(grid);
        stage.setScene(scene);
        stage.show();
    }
    
    public static void display(Share share) {
        Stage stage = new Stage();
        stage.setTitle(share.getName());

        GridPane grid = new GridPane();
        
        GridPane infoGrid =  new GridPane();
        
        Label info = new Label("Share Details");
        infoGrid.add(info, 0, 0);
        
        Label name = new Label("Name: ");
        Label nameContent = new Label(share.getName());
        infoGrid.add(name, 0, 1);
        infoGrid.add(nameContent, 1, 1);
        
        Label unit = new Label("Company: ");
        Label unitContent = new Label(share.getCompany().getName());
        infoGrid.add(unit, 0, 2);
        infoGrid.add(unitContent, 1, 2);
        
        Label value = new Label("Value: ");
        Label valueContent = new Label(Double.toString(share.getValue()));
        infoGrid.add(value, 0, 3);
        infoGrid.add(valueContent, 1, 3);
        
        Label number = new Label("Number: ");
        Label numberContent = new Label(Integer.toString(share.getNumber()));
        infoGrid.add(number, 0, 4);
        infoGrid.add(numberContent, 1, 4);
       
        
        //wykres
                
        NumberAxis x = new NumberAxis();
        NumberAxis y = new NumberAxis();
        
        x.setLabel("Time");
        y.setLabel("Value");
        
        LineChart<Number, Number> chart = new LineChart<>(x, y);
        
        chart.setTitle("Value of "+share.getName());

        XYChart.Series<Number, Number> data = new XYChart.Series<>();

        long minTime=LocalTime.now().toNanoOfDay(), maxTime=0; 
        
        int size = share.getChartValues().size();

        for (int i=0; i<size; i++) {
            Double v = share.getChartValues().get(i);
            long time = share.getChartTimes().get(i);
            if(time<minTime) minTime=time;
            if(time>maxTime) maxTime=time;
            data.getData().add(new XYChart.Data(time, v));
        }
        
        x.setAutoRanging(false);        
        x.setLowerBound(minTime);
        x.setUpperBound(maxTime);
        
        chart.getData().add(data);
        chart.setLegendVisible(false);

        
        grid.add(infoGrid, 0, 0);
       
        grid.add(chart, 1, 0);
        grid.setMaxWidth(720);
        grid.setMaxHeight(400);
        Scene scene = new Scene(grid);
        stage.setScene(scene);
        stage.show();
    }

    
    
    
}
