package com.lakas.MVC.controller;

import com.lakas.MVC.model.Product;
import dataBase.DataBase;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.Alerts;
import util.LogicalExpressionUtil;

import java.security.Principal;
import java.sql.SQLException;

public class UpdateController {
    private Product productForUpdate;
    public void setProductForUpdate(Product product) {
        this.productForUpdate = product;
    }
    private ObservableList<Product> productList;
    public void setMainTable(ObservableList<Product> productList) {
        this.productList = productList;
    }
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
    ////////////////////////////////////////////////////////////////////////

    @FXML
    public void updateProduct() throws SQLException {
        if (LogicalExpressionUtil.isInsertFieldsEmpty(nameField, descriptionField, priceField)) {
            Alerts.showAlertError();
        } else if (LogicalExpressionUtil.isDuplicateUpdate(productForUpdate.getId(), nameField.getText(), productList)) {
            Alerts.showAlertIncorrectDataEnter();
        } else {
            int index = productList.indexOf(productForUpdate);

            productForUpdate.setName(nameField.getText());
            productForUpdate.setDescription(descriptionField.getText());
            productForUpdate.setPrice(Integer.parseInt(priceField.getText()));

            productList.set(index, productForUpdate);

            DataBase.getInstance().UPDATE(productForUpdate);

            Stage stage = (Stage) buttonOk.getScene().getWindow();
            stage.close();
        }

    }





    ////////////////////////////////////////////////////////////////////////
    @FXML
    public void closeForm() {
        Stage stage = (Stage) buttonCancel.getScene().getWindow();
        stage.close();
    }
}
