/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockmarket;

import java.time.LocalTime;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *shows the chart with all of the assets.
 */
public class ChartWindow {
    
    public ChartWindow() {
        
    }
    
    public static void display() {
        
        Stage stage = new Stage();
        stage.setTitle("Asset Chart");
        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER);
        layout.setPrefHeight(720);
        layout.setPrefWidth(720);
        
        Button closeButton = new Button("Close");
        
        closeButton.setOnAction(e-> stage.close());
        

        NumberAxis x = new NumberAxis();
        NumberAxis y = new NumberAxis();
        x.setLabel ("Time");
        y.setLabel("Value change over time(%)");
        LineChart<Number, Number> chart = new LineChart<>(x, y);
        x.setAutoRanging(false);
        y.setAutoRanging(false);
        
        long minTime=LocalTime.now().toNanoOfDay();
        long maxTime=0;
        double minValue = 0.0, maxValue=0.0;

        for (int i=0; i<Main.materialList.size(); i++) {
            Series<Number, Number> series = new XYChart.Series<>();
            RawMaterial m = Main.materialList.get(i);
            
            int size = m.getChartValues().size();

            for (int j=0; j<size; j++) {
                Double v = (m.getChartValues().get(j)-m.getFirstValue())*100/m.getFirstValue();
                long time = m.getChartTimes().get(j);
                if(time<minTime) minTime=time;
                if(time>maxTime) maxTime=time;
                if (v<minValue) minValue = v;
                if (v>maxValue) maxValue = v;
                series.getData().add(new XYChart.Data(time, v));
            }
            series.setName(m.getName());
            chart.getData().add(series);
        }
        
                for (int i=0; i<Main.currencyList.size(); i++) {
            Series<Number, Number> series = new XYChart.Series<>();
            Currency c = Main.currencyList.get(i);
            
            int size = c.getChartValues().size();

            for (int j=0; j<size; j++) {
                Double v = (c.getChartValues().get(j)-c.getFirstRate())*100/c.getFirstRate();
                long time = c.getChartTimes().get(j);
                if(time<minTime) minTime=time;
                if(time>maxTime) maxTime=time;
                if (v<minValue) minValue = v;
                if (v>maxValue) maxValue = v;
                series.getData().add(new XYChart.Data(time, v));
            }
            
            
            series.setName(c.getName());
            chart.getData().add(series);
        }
                
                for (int i=0; i<Main.companyList.size(); i++) {
            Series<Number, Number> series = new XYChart.Series<>();
            Company c = Main.companyList.get(i);
            
            Share s = c.getShares().get(0);
            
            int size = s.getChartValues().size();

            for (int j=0; j<size; j++) {
                Double v = (s.getChartValues().get(j)-c.getOpeningPrice())*100/c.getOpeningPrice();
                long time = s.getChartTimes().get(j);
                if(time<minTime) minTime=time;
                if(time>maxTime) maxTime=time;
                if (v<minValue) minValue = v;
                if (v>maxValue) maxValue = v;
                series.getData().add(new XYChart.Data(time, v));
            }
            
            
            series.setName(c.getName() + " shares");
            chart.getData().add(series);
        }
                
                x.setLowerBound(minTime);
                x.setUpperBound(maxTime);
                y.setLowerBound(minValue);
                y.setUpperBound(maxValue);
            
        layout.getChildren().addAll(chart, closeButton);

        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.show();
        
    }
    
}
