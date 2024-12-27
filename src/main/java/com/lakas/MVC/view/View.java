package com.lakas.MVC.view;

import com.lakas.MVC.controller.Controller;
import dataBase.DataBase;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.sql.SQLException;

public class View extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main.fxml"));
        AnchorPane root = fxmlLoader.load();

        Scene scene = new Scene(root, 1000, 600);

        stage.setScene(scene);
        stage.setTitle("CRUD app");
        stage.show();
    }

    public static void main(String[] args) throws SQLException {
        DataBase.getInstance();
        launch(args);
    }
}
