package com.lakas.MVC.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import lombok.Data;

public class Product {
    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleStringProperty description;
    private SimpleIntegerProperty price;

    public Product(int id, String name, String description, int price) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
        this.price = new SimpleIntegerProperty(price);
    }
    public Product(String name, String description, int price) {

        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
        this.price = new SimpleIntegerProperty(price);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != getClass()) return false;
        Product product = (Product) o;
        return id == product.id && name.equals(product.name) && description.equals(product.description) && price == product.price;
    }

    public int getId() {
        return id.get();
    }

    public String getName() {
        return name.get();
    }

    public String getDescription() {
        return description.get();
    }

    public int getPrice() {
        return price.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public void setPrice(int price) {
        this.price.set(price);
    }

    public SimpleIntegerProperty getIdProperty() {
        return id;
    }

    public SimpleStringProperty getNameProperty() {
        return name;
    }
    public SimpleStringProperty getDescriptionProperty() {
        return description;
    }
    public SimpleIntegerProperty getPriceProperty() {
        return price;
    }
}
