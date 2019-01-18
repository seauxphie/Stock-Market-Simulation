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
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *The window which pops up when the user is trying to "Save as..." or read
 * information from file.
 */
public class FileWindow {
    
    String fileName;
    
    public FileWindow() {
        
    }
    
    public String display() {
        Stage stage = new Stage();
        stage.setTitle("Provide the file name");
        stage.setMinWidth(300);
        stage.setMinHeight(150);
        stage.initModality(Modality.APPLICATION_MODAL);//ta linia sprawia, że trzeba zamknac to okienko przed powrotem do aplikacji
        
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        
        Label nameLabel = new Label("File:");
        grid.add(nameLabel, 0, 0);
        
        TextField nameText = new TextField();
        grid.add(nameText, 0, 1);
        
        Button closeBtn = new Button("OK");
        closeBtn.setOnAction(e-> {
            fileName = nameText.getText();
        });
        grid.add(closeBtn, 0, 1);
        
        grid.setAlignment(Pos.CENTER);
        
        Scene scene = new Scene(grid);
        stage.setScene(scene);
        
        stage.showAndWait();
        
        return fileName;
    }
    
}
