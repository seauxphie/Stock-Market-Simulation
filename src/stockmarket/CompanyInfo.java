/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockmarket;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import static javafx.scene.layout.GridPane.setColumnSpan;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 *Window which shows information about a company.
 */
public class CompanyInfo 
{
    
    public static Company company;
    public static void display(Company c) {
        company = c;
        Stage stage = new Stage();
        stage.setTitle(company.getName());
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets (5, 10, 10, 5));

        Label info = new Label("Company Details");
        grid.add(info, 0, 0);
        setColumnSpan(info, 2);

        Label name = new Label("Name: ");
        Label nameContent = new Label(company.getName());
        grid.add(name, 0, 1);
        grid.add(nameContent, 1, 1);

        Label openingPrice = new Label("Opening Price: ");
        Label openingContent = new Label(Double.toString(company.getOpeningPrice()));
        grid.add(openingPrice, 0, 2);
        grid.add(openingContent, 1, 2);

        Label currentPrice = new Label("Current Price: ");
        Label currentContent = new Label(Double.toString(company.getCurrentPrice()));
        grid.add(currentPrice, 0, 3);
        grid.add(currentContent, 1, 3);

        Label shares = new Label("Number of shares: ");
        Label sharesContent = new Label(Integer.toString(company.getNumberOfShares()));
        grid.add(shares, 0, 4);
        grid.add(sharesContent, 1, 4);

        Label profit = new Label("Profit: ");
        Label profitContent = new Label(Double.toString(company.getProfit()));
        grid.add(profit, 0, 5);
        grid.add(profitContent, 1, 5);
        
        Label revenue = new Label("Revenue: ");
        Label revenueContent = new Label(Double.toString(company.getRevenue()));
        grid.add(revenue, 0, 6);
        grid.add(revenueContent, 1, 6);
        
        Label turnover = new Label("Turnover: ");
        Label turnoverContent = new Label(Double.toString(company.getProfit()));
        grid.add(turnover, 0, 7);
        grid.add(turnoverContent, 1, 7);
        
        Label volume = new Label("Volume: ");
        Label volumeContent = new Label(Integer.toString(company.getVolume()));
        grid.add(volume, 0, 8);
        grid.add(volumeContent, 1, 8);
        
        
        NumberAxis x = new NumberAxis();
        NumberAxis y = new NumberAxis();
        
        x.setLabel("Time");
        y.setLabel("Value");
        
        LineChart<Number, Number> chart = new LineChart<>(x, y);
        
        chart.setTitle("Value of "+company.getName());
        
        XYChart.Series data = new XYChart.Series();
        
        Share share = null;
        int i=0;
        while (share!=null && i<Main.shareList.size())
        {
            if (Main.shareList.get(i).getCompany() == company) share=Main.shareList.get(i);
            i++;
        }
        
                
        Button removeButton =  new Button("Remove");
        removeButton.setOnAction(e -> {
            Main.companyList.remove(company);
            int j=0;
            Thread cThread = Main.companyThreadList.get(j++);
            while (((CompanyThread) cThread).getCompany()!=company) cThread = Main.companyThreadList.get(j++);
            cThread.interrupt();
            for (j=0; j< Main.shareList.size(); j++) {
                Share ss = Main.shareList.get(j);
                Company cc = ss.getCompany();
                if (cc == company) {
                    Main.shareList.remove(j);
                    j--;
                }
                
            }

            company=null;
            stage.close();
        });
        removeButton.setMinWidth(80);

        Button closeButton = new Button("Close");
        closeButton.setOnAction(e-> stage.close());
        closeButton.setMinWidth(80);
        
        HBox buttonContainer = new HBox();
        buttonContainer.getChildren().addAll(removeButton, closeButton);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setMinWidth(300);
        buttonContainer.setSpacing(30);
        
        
        setColumnSpan(buttonContainer, 2);
                
        grid.add(buttonContainer, 0, 9);

        Scene scene = new Scene(grid, 300, 300);
        stage.setScene(scene);
        stage.show();
    }
    
    
    }
