/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockmarket;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import static javafx.scene.layout.GridPane.setColumnSpan;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *This app is a stock market simulation. There is a "basic currency"
 * used in all of the transactions. There is one raw material market and
 * one currency market. The following objects are added by the user:
 * <ul>
 * <li>Investor
 * <li>Fund (investment fund)
 * <li>Company
 * <li>Currency
 * <li>Raw Material
 * <li>Exchange
 * <li>Index
 * </ul>
 * 
 * Each company, investor and investment fund has its own thread where it
 * updates itself and issues shares (company) or performs transactions (investor,
 * fund).
 * 
 * When the app is lunched, the user can either start a new simulation or open
 * a file with saved data.
 *  
 */
public class Main extends Application  {
    
    public static NameGenerator nameGenerator = new NameGenerator();
    public static long timeUnit;
    private Stage stage;
    private final Thread updates = new UpdateThread();
    private ObjectOutputStream output;
    private ObjectInputStream input;
    

    public static ObservableList<Investor>investorList = FXCollections.observableArrayList();
    public static ObservableList<Fund>fundList = FXCollections.observableArrayList();
    public static ObservableList<Company>companyList = FXCollections.observableArrayList();
    public static ObservableList<Currency> currencyList = FXCollections.observableArrayList();
    public static ObservableList<Share> shareList = FXCollections.observableArrayList();
    public static ObservableList<RawMaterial> materialList = FXCollections.observableArrayList();

    public static ObservableList<Exchange> exchangeList = FXCollections.observableArrayList();
    public static ObservableList<Index> indexList = FXCollections.observableArrayList();
    public static CurrencyMarket currencyMarket = new CurrencyMarket();
    public static RawMaterialMarket materialMarket = new RawMaterialMarket();
    
    public static ArrayList<Thread> investorThreadList = new ArrayList<>();
    public static ArrayList<Thread> fundThreadList = new ArrayList<>();
    public static ArrayList<Thread> companyThreadList = new ArrayList<>();
          
   
    
    public static void main(String[] args) {
        
            
        launch();
    }
    
    
    @Override    
    public void start(Stage primaryStage) throws InterruptedException {
        
        stage = primaryStage;

        GridPane menuGrid = new GridPane();
        menuGrid.setAlignment(Pos.CENTER);
        menuGrid.setHgap(8);
        menuGrid.setVgap(8);
        menuGrid.setPadding(new Insets(10, 10, 10, 10));

       BorderPane layout = new BorderPane();
       HBox header = new HBox();
       header.setAlignment(Pos.BASELINE_LEFT);
       header.setPadding(new Insets(20, 20, 20, 20));
       header.setSpacing(10);
       Text title = new Text("Stock Market Simulation");
       header.getChildren().add(title);
       title.setStyle("font-size:20px; alignment:center;");
       layout.setTop(header);

        Button addButton = new Button ("Add an object");
        addButton.setOnAction(e-> {
            ObjectAdderWindow.display();
        });
        addButton.setPrefWidth(160);

        Button chartButton =  new Button("Asset chart");
        chartButton.setOnAction(e-> ChartWindow.display());
        chartButton.setPrefWidth(160);

               
        final FileChooser fileChooser = new FileChooser();
        
        Stage chooserStage = new Stage();

        Button saveButton = new Button ("Save");
        saveButton.setOnAction(e-> {
            fileChooser.setTitle("Choose save file");
            fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home")));
            File file = fileChooser.showOpenDialog(chooserStage);
            if (file!=null) {
                try {
                    saveStuff(file);
                    ExceptionWindow.display("Data saved successfully to file " + file.getName());
                } catch (IOException ex) {
                    ExceptionWindow.display("There was a problem with saving: " + ex.getMessage());
                }
            }
            
        });
        saveButton.setPrefWidth(160);

        Button exitButton = new Button ("Exit");
        exitButton.setOnAction(e-> closeApp());
        exitButton.setPrefWidth(160);

        VBox menu = new VBox();
        menu.setPrefWidth(180);
        menu.setPadding(new Insets(10, 10, 10, 10));
        menu.setSpacing(20);
        menu.setAlignment(Pos.CENTER);
        menu.getChildren().addAll(addButton, chartButton, saveButton, exitButton);
        layout.setLeft(menu);
    
            
        stage.setTitle("Stock Market Simulation");

        Scene welcomeScene;
        GridPane welcomeGrid = new GridPane();
        welcomeGrid.setAlignment(Pos.CENTER);
        welcomeGrid.setHgap(10);
        welcomeGrid.setVgap(10);
        welcomeGrid.setPadding(new Insets (20, 20, 20, 20));

        Button restoreButton = new Button("Open data...");
         restoreButton.setOnAction(e-> {
         try {
            fileChooser.setTitle("Choose save file");
            fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home")));
            File file = fileChooser.showOpenDialog(chooserStage);
            if (file!=null) restoreStuff(file);

             for (int i=0; i<investorList.size(); i++) {
                 Thread t = new InvestorThread(investorList.get(i));
                 investorThreadList.add(t);
                 t.start();
             }
             for (int i=0; i<fundList.size(); i++){
                 Thread t = new FundThread(fundList.get(i));
                 fundThreadList.add(t);
                 t.start();
             }
             for (int i=0; i<companyList.size(); i++) {
                 Thread t = new CompanyThread(companyList.get(i));
                 companyThreadList.add(t);
                 t.start();
             }

             for (int i=0; i<exchangeList.size(); i++) {
                 Exchange exchange = exchangeList.get(i);
                 for (int j=0; j<exchange.getIndexes().size(); j++) {
                     indexList.add(exchange.getIndexes().get(j));
                 }
             }

             layout.setCenter(MainScene.setScene());
             layout.setLeft(menu);

            Scene homeScene = new Scene(layout, 1024, 720);

            stage.setScene(homeScene);
            homeScene.getStylesheets().add(Main.class.getResource("Main.css").toExternalForm());


            updates.start();


         } catch (IOException ex) {
             ExceptionWindow.display("There was a problem with your file: " + ex.getMessage());
         } catch (ClassNotFoundException ex) {
             System.out.println(ex.getMessage());
         }
         });

         welcomeGrid.add(restoreButton, 0, 2);
         restoreButton.setPrefWidth(240);

         Button newMarketButton = new Button ("Start a new simulation");
         newMarketButton.setOnAction (e -> {

             layout.setCenter(MainScene.setScene());
             layout.setLeft(menu);

             Scene homeScene = new Scene(layout, 1024, 720);

                 stage.setScene(homeScene);
                 updates.start();
         });

         welcomeGrid.add(newMarketButton, 0, 1);
         newMarketButton.setPrefWidth(240);
         
         Button welcomeExitButton = new Button ("Exit");
        welcomeExitButton.setOnAction(e-> closeApp());
        welcomeExitButton.setPrefWidth(240);
        welcomeExitButton.setAlignment(Pos.CENTER);

         Label welcomeLabel = new Label("Welcome to Stock Market Simulation!");
         welcomeGrid.add(welcomeLabel, 0, 0);
         welcomeGrid.add(welcomeExitButton, 0, 3);
         welcomeGrid.setAlignment(Pos.CENTER);

         welcomeScene = new Scene(welcomeGrid, 1024, 720);
         
         stage.setOnCloseRequest(e -> closeApp());

         stage.setScene(welcomeScene);
         stage.show();

         

    }

    private void closeApp() {
        stage.close();
       
        
            while(!investorThreadList.isEmpty()) {
                Thread t = investorThreadList.get(0);
                t.interrupt();
                investorThreadList.remove(0);
            }
            while(!fundThreadList.isEmpty()) {
               Thread t = fundThreadList.get(0);
                t.interrupt();
                fundThreadList.remove(0); 
            }
            while(!companyThreadList.isEmpty()) {
               Thread t = companyThreadList.get(0);
                t.interrupt();
                companyThreadList.remove(0); 
            }
            updates.interrupt();

    }
    
    private void saveStuff (File file) throws IOException {
        try {
            output = new ObjectOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(file)));
             for (int i=0; i<investorList.size(); i++) {
                investorList.get(i).saveAssets();
            }
            
            for (int i=0; i<fundList.size(); i++) {
                fundList.get(i).saveAssets();
            }
            
            
            output.writeObject(new ArrayList<>(investorList));
            output.writeObject(new ArrayList<>(fundList));
            output.writeObject(new ArrayList<>(companyList));
            output.writeObject(new ArrayList<>(currencyList));
            output.writeObject(new ArrayList<>(materialList));
            output.writeObject(new ArrayList<>(exchangeList));
            output.writeObject (materialMarket);
            output.writeObject(currencyMarket);

     }catch (IOException ex) {
            throw ex;
        }
     finally {
            try {
                output.close();
            } catch (IOException ex) {
                throw ex;
            }
     }
        
    }
    
    private void restoreStuff(File file) throws IOException, ClassNotFoundException {
        try {
            input = new ObjectInputStream(
                    new BufferedInputStream(
                            new FileInputStream(file)));
            
            ArrayList<Investor> a = (ArrayList<Investor>) input.readObject();
            ArrayList<Fund> b = (ArrayList<Fund>) input.readObject();
            ArrayList<Company> c = (ArrayList<Company>) input.readObject();
            ArrayList<Currency> d = (ArrayList<Currency>) input.readObject();
            ArrayList<RawMaterial> e = (ArrayList<RawMaterial>) input.readObject();
            ArrayList<Exchange> f = (ArrayList<Exchange>) input.readObject();
            ArrayList<Share> s = new ArrayList<>();
            
            for (int i=0; i<f.size(); i++) {
                for (int j=0; j<f.get(i).getShares().size(); j++) {
                    s.add(f.get(i).getShares().get(j));
                }
            }
            
            
            investorList = FXCollections.observableList(a);
            fundList = FXCollections.observableList(b);
            companyList = FXCollections.observableList(c);
            currencyList = FXCollections.observableList(d);
            materialList = FXCollections.observableList(e);
            exchangeList = FXCollections.observableList(f);
            shareList = FXCollections.observableList(s);
            materialMarket = (RawMaterialMarket) input.readObject();
            currencyMarket = (CurrencyMarket) input.readObject();
            
            for (int i = 0; i<currencyList.size(); i++) {
                currencyList.get(i).setRates(); //repopulate hashmap
            }
            
            for (int i=0; i<investorList.size(); i++) {
                investorList.get(i).restoreAssets();
            }
            
            for (int i=0; i<fundList.size(); i++) {
                fundList.get(i).restoreAssets();
            }

     }catch (IOException | ClassNotFoundException ex) {
            throw ex;
        }
     finally {
            try {
                input.close();
            } catch (IOException ex) {
                throw ex;
            }
     }
        
    }
}

