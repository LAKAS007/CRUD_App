package com.lakas.MVC.controller;

import com.lakas.MVC.model.Product;
import com.sun.javafx.beans.event.AbstractNotifyListener;
import dataBase.DataBase;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import util.Alerts;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Controller {
    @FXML
    private Button buttonInsert;
    @FXML
    private Button buttonDelete;
    @FXML
    private Button buttonUpdate;

//////////////////////////////////table////////////////////////////////
    @FXML
    private TableColumn<Product, Integer> idColumn = new TableColumn<>("ID");
    @FXML
    private TableColumn<Product, String> nameColumn = new TableColumn<>("Name");
    @FXML
    private TableColumn<Product, String> descriptionColumn = new TableColumn<>("Description");
    @FXML
    private TableColumn<Product, Integer> priceColumn = new TableColumn<>("Price");
    @FXML
    private TableView<Product> mainTable;

    @FXML
    private TextField pageNumber;

    @FXML
    private TextField findField;

    @FXML
    private TextField filtrField;

    @SuppressWarnings("all")
    private ObservableList<Product> productList = DataBase.getInstance().SELECT_ALL();

    private static final int PAGE_SIZE = 3;

    public Controller() throws SQLException {
    }

    ////////////////////////////////open forms///////////////////////////////

    @FXML
    public void openInsertForm() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/insertWindow.fxml"));
        Scene insertForm = new Scene(loader.load());
        InsertController insertController = loader.getController();
        insertController.setMainTable(this.mainTable);

        Stage stage = new Stage();
        stage.setTitle("Insert form");
        stage.setScene(insertForm);
        stage.show();
    }

    @FXML
    public void openUpdateForm() throws IOException {
        if (mainTable.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/updateWindow.fxml"));
            Scene updateForm = new Scene(loader.load());
            UpdateController updateController = loader.getController();
            updateController.setMainTable(this.productList);
            updateController.setProductForUpdate(mainTable.getSelectionModel().getSelectedItem());

            Stage stage = new Stage();
            stage.setTitle("Update form");
            stage.setScene(updateForm);
            stage.show();
        } else {
            Alerts.showAlertNotSelected();
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    @FXML
    public void deleteProduct() throws SQLException {
        Product selectedProduct = mainTable.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            if (Alerts.showAlertConfirmationDelete()) {
                mainTable.getItems().remove(selectedProduct);
                DataBase.getInstance().DELETE(selectedProduct);
            }
        }
    }

    @FXML
    private void pagination(KeyEvent event) throws SQLException {
        String text;
        try {
            text = pageNumber.getText();
            if (text.isEmpty()) {
                return;
            }
        }
        catch (NumberFormatException e){
            e.printStackTrace();
            return;
        }
        int page = Integer.parseInt(text);

        ObservableList<Product> allWithPagination = DataBase.getInstance().findAllWithPagination(page, PAGE_SIZE);
        mainTable.setItems(allWithPagination);
    }

    @FXML
    private void findByName(KeyEvent event) throws SQLException {
        String text = findField.getText();
        if (text.isEmpty()) {
            mainTable.setItems(productList);
            return;
        }

        Product byName = DataBase.getInstance().findByName(text);
        if (byName == null) {
            return;
        }

        mainTable.setItems(FXCollections.observableList(List.of(byName)));
    }


    ///////////////////////////////init//////////////////////////////


    private void initTable() throws SQLException {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        mainTable.setItems(this.productList);
    }



    @FXML
    public void initialize() throws SQLException {
        initTable();
        pageNumber.setText("1");
    }

    @FXML
    public void filtration() {
        if (filtrField.getText().isEmpty()) {
            mainTable.setItems(productList);
            return;
        }
        mainTable.setItems(FXCollections.observableList(productList.stream()
                .filter(x -> x.getName().startsWith(filtrField.getText()))
                .toList()));
    }
}
