package com.lakas.MVC.controller;

import com.lakas.MVC.model.Product;
import util.Alerts;
import util.LogicalExpressionUtil;
import dataBase.DataBase;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class InsertController {


    ////////////////////////////////////enter fields////////////////////
    @FXML
    private TextField nameField;
    @FXML
    private TextField descriptionField;
    @FXML
    private TextField priceField;

    ////////////////////////////////////buttons///////////////////////////////
    @FXML
    private Button buttonOk;
    @FXML
    private Button buttonCancel;


    /////////////////////////////////////mainTable//////////////////////////////
    private TableView<Product> mainTable;

    public void setMainTable(TableView<Product> mainTable) {
        this.mainTable = mainTable;
    }
    //////////////////////////////////////////////////////////////////////////



    @FXML
    public void INSERT() throws SQLException {

        if (LogicalExpressionUtil.isInsertFieldsEmpty(nameField, descriptionField, priceField)) {
            Alerts.showAlertError();
            return;
        }
        else if (LogicalExpressionUtil.isDuplicate(nameField.getText(), mainTable)) {
            Alerts.showAlertIncorrectDataEnter();
            return;
        }
        else {
            String name = nameField.getText();
            String desc = descriptionField.getText();
            int price = Integer.parseInt(priceField.getText());

            Product prod = new Product(name, desc, price);
            DataBase.getInstance().INSERT(prod);
            mainTable.getItems().add(DataBase.getInstance().SELECT_LAST_PRODUCT());

            Stage stage = (Stage) buttonCancel.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    public void closeForm() {
        Stage stage = (Stage) buttonCancel.getScene().getWindow();
        stage.close();
    }

}
