/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockmarket;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *The pop-up window which displays an exception or alert.
 * 
 */
public class ExceptionWindow {
    
    public static void display(String message) {
        Stage stage = new Stage();
        stage.setTitle("Exception");
        stage.setMinWidth(300);
        stage.setMinHeight(150);
        stage.initModality(Modality.APPLICATION_MODAL);//ta linia sprawia, że trzeba zamknac to okienko przed powrotem do aplikacji
        
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        
        Label label = new Label();
        label.setText(message);
        grid.add(label, 0, 0);
        
        Button closeBtn = new Button("OK");
        closeBtn.setOnAction(e-> stage.close());
        grid.add(closeBtn, 0, 1);
        
        grid.setAlignment(Pos.CENTER);
        
        Scene scene = new Scene(grid);
        stage.setScene(scene);
        
        stage.showAndWait();

    }
    
}
