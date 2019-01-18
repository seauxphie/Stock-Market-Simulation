/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockmarket;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import static javafx.scene.layout.GridPane.setRowSpan;
import static stockmarket.Main.companyList;
import static stockmarket.Main.currencyList;
import static stockmarket.Main.currencyMarket;
import static stockmarket.Main.exchangeList;
import static stockmarket.Main.fundList;
import static stockmarket.Main.indexList;
import static stockmarket.Main.investorList;
import static stockmarket.Main.materialList;
import static stockmarket.Main.materialMarket;
import static stockmarket.Main.shareList;

/**
 *Creates the main scene which displays the assets, investors, markets etc.
 * 
 */
public class MainScene {
    
    public MainScene() {
        
    }
    
    public static GridPane setScene() {
        GridPane grid = new GridPane();
            grid.setAlignment(Pos.TOP_LEFT);
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets (20, 20, 20, 20));
            
            GridPane assetGrid = new GridPane();
            assetGrid.setAlignment(Pos.CENTER);
            assetGrid.setHgap(8);
            assetGrid.setVgap(8);
            assetGrid.setPadding(new Insets(10, 10, 10, 10));
            
            GridPane marketGrid = new GridPane();
            marketGrid.setAlignment(Pos.CENTER);
            marketGrid.setHgap(8);
            marketGrid.setVgap(8);
            marketGrid.setPadding(new Insets(10, 10, 10, 10));
            
                        
            GridPane investorGrid = new GridPane();
            investorGrid.setAlignment(Pos.CENTER);
            investorGrid.setHgap(8);
            investorGrid.setVgap(8);
            investorGrid.setPadding(new Insets(10, 10, 10, 10));
            
            /*GridPane menuGrid = new GridPane();
            menuGrid.setAlignment(Pos.CENTER);
            menuGrid.setHgap(8);
            menuGrid.setVgap(8);
            menuGrid.setPadding(new Insets(10, 10, 10, 10));
           
           
           BorderPane layout = new BorderPane();
           HBox header = new HBox();
           header.setAlignment(Pos.BASELINE_LEFT);
           header.setPadding(new Insets(20, 40, 20, 40));
           header.setSpacing(10);
           Text title = new Text("Stock Market Simulation");
           header.getChildren().add(title);
           layout.setTop(header);
*/
            ListView<RawMaterial> materialListView = new ListView<>();
            materialListView.setPrefHeight(200);
            materialListView.setEditable(true);
            materialListView.setItems(materialList);
            
            

           ListView<Share> shareListView = new ListView<>();
           shareListView.setPrefHeight(200);
           shareListView.setEditable(true);
           shareListView.setItems(shareList);

           ListView<Currency> currencyListView = new ListView<>();
           currencyListView.setPrefHeight(200);
           currencyListView.setEditable(true);
           currencyListView.setItems(currencyList);
           
           ListView<Investor> investorListView = new ListView<>();
           investorListView.setPrefHeight(200);
           investorListView.setEditable(true);
           investorListView.setItems(investorList);
           
           ListView<Fund> fundListView = new ListView<>();
           fundListView.setPrefHeight(200);
           fundListView.setEditable(true);
           fundListView.setItems(fundList);
           
           ListView<Company> companyListView = new ListView<>();
           companyListView.setPrefHeight(200);
           companyListView.setEditable(true);
           companyListView.setItems(companyList);
           
           ListView<Exchange> exchangeListView = new ListView<>();
           exchangeListView.setPrefHeight(200);
           exchangeListView.setEditable(true);
           exchangeListView.setItems(exchangeList);
           
           ListView<Index> indexListView = new ListView<>();
           indexListView.setPrefHeight(200);
           indexListView.setEditable(true);
           indexListView.setItems(indexList);


            materialListView.setCellFactory((ListView<RawMaterial> p) -> {
                ListCell<RawMaterial> cell = new ListCell<RawMaterial>(){

                    @Override
                    protected void updateItem(RawMaterial a, boolean bln) {
                        super.updateItem(a, bln);
                        if (a != null) {
                            setText(a.getName());
                        }
                    }
                };                   
                return cell;
            });

            currencyListView.setCellFactory((ListView<Currency> p) -> {
                ListCell<Currency> cell = new ListCell<Currency>(){

                    @Override
                    protected void updateItem(Currency a, boolean bln) {
                        super.updateItem(a, bln);
                        if (a != null) {
                            setText(a.getName());
                        }
                    }
                };                   
                return cell;
            });
            

            shareListView.setCellFactory((ListView<Share> p) -> {
                ListCell<Share> cell = new ListCell<Share>(){

                    @Override
                    protected void updateItem(Share a, boolean bln) {
                        super.updateItem(a, bln);
                        if (a != null) {
                            setText(a.getName());
                        }
                    }
                };                   
                return cell;
            });
            
            investorListView.setCellFactory((ListView<Investor> p) -> {
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
            
            fundListView.setCellFactory((ListView<Fund> p) -> {
                ListCell<Fund> cell = new ListCell<Fund>(){

                    @Override
                    protected void updateItem(Fund a, boolean bln) {
                        super.updateItem(a, bln);
                        if (a != null) {
                            setText(a.getFundName());
                        }
                    }
                };                   
                return cell;
            });
            
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
            
            exchangeListView.setCellFactory((ListView<Exchange> p) -> {
                ListCell<Exchange> cell = new ListCell<Exchange>(){

                    @Override
                    protected void updateItem(Exchange a, boolean bln) {
                        super.updateItem(a, bln);
                        if (a != null) {
                            setText(a.getName());
                        }
                    }
                };                   
                return cell;
            });
            
            indexListView.setCellFactory((ListView<Index> p) -> {
                ListCell<Index> cell = new ListCell<Index>(){

                    @Override
                    protected void updateItem(Index a, boolean bln) {
                        super.updateItem(a, bln);
                        if (a != null) {
                            setText(a.getName());
                        }
                    }
                };                   
                return cell;
            });

            currencyListView.setOnMouseClicked(e-> {
                Currency c = currencyListView.getSelectionModel().getSelectedItem();
                if (c!=null) AssetInfoWindow.display(c);
                else ExceptionWindow.display("Select a currency to view details.");
            });
           
           materialListView.setOnMouseClicked(e-> {
                RawMaterial mat = materialListView.getSelectionModel().getSelectedItem();
                if (mat!=null) AssetInfoWindow.display(mat);
                else ExceptionWindow.display("Select a raw material to view details.");
            });
           
            shareListView.setOnMouseClicked(e-> {
                Share share = shareListView.getSelectionModel().getSelectedItem();
                if (share!=null) AssetInfoWindow.display(share);
                else ExceptionWindow.display("Select a share to view details.");
            });
           
           investorListView.setOnMouseClicked (e-> {
               Investor i = investorListView.getSelectionModel().getSelectedItem();
               if (i!=null) InvestorInfo.display(i);
               else ExceptionWindow.display("Select an investor to view details.");
               
           });
           
            companyListView.setOnMouseClicked(e-> {
                Company c = companyListView.getSelectionModel().getSelectedItem();
                if (c!= null)  CompanyInfo.display(c);
                else ExceptionWindow.display("Select a company to view details.");
            });
                        
           fundListView.setOnMouseClicked(e-> {
                Fund f = fundListView.getSelectionModel().getSelectedItem();
                if(f!=null) FundInfo.display(f);
                else ExceptionWindow.display("Select an investment fund to view details.");

            });
           
           exchangeListView.setOnMouseClicked (e-> {
               Exchange exchange = exchangeListView.getSelectionModel().getSelectedItem();
               if(exchange!=null) MarketInfo.display(exchange);
                else ExceptionWindow.display("Select an exchange to view details.");
           });
           
           indexListView.setOnMouseClicked (e-> {
               Index index = indexListView.getSelectionModel().getSelectedItem();
               if(index!=null) IndexInfo.display(index);
                else ExceptionWindow.display("Select an index to view details.");
           });
           
           
           
           Button materialMarketButton = new Button ("Raw Material Market");
           materialMarketButton.setOnAction(e-> {
               MarketInfo.display(materialMarket);
           });
           
           Button currencyMarketButton = new Button ("Currency Market");
           currencyMarketButton.setOnAction(e-> {
               MarketInfo.display(currencyMarket);
           });
           
           materialMarketButton.setPrefWidth(240);
           currencyMarketButton.setPrefWidth(240);
           
            assetGrid.add(new Label("Currencies"), 0, 1);
            assetGrid.add(new Label("Shares"), 2, 1);
            assetGrid.add(new Label("Raw materials"), 1, 1);
            assetGrid.add(currencyListView, 0, 2);
            assetGrid.add(shareListView, 2, 2);
            assetGrid.add(materialListView, 1, 2);
            
            investorGrid.add(new Label("Investors"), 0, 1);
            investorGrid.add(new Label("Investment Funds"), 1, 1);
            investorGrid.add(new Label("Companies"), 2, 1);
            investorGrid.add(investorListView, 0, 2);
            investorGrid.add(fundListView, 1, 2);
            investorGrid.add(companyListView, 2, 2);
            
            marketGrid.add(new Label("Exchanges"), 1, 1);
            marketGrid.add(new Label("Indexes"), 2, 1);
            marketGrid.add(materialMarketButton, 0, 3);
            marketGrid.add(currencyMarketButton, 0, 2);
            marketGrid.add(exchangeListView, 1, 2);
            marketGrid.add(indexListView, 2, 2);
            setRowSpan(exchangeListView, 2);
            setRowSpan(indexListView, 2);

            grid.add(assetGrid, 0, 1);
            grid.add(investorGrid, 0, 2);
            grid.add(marketGrid, 0, 3);
            
            return grid;
            
    }
    
}

