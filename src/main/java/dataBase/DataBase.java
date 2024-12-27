package dataBase;

import com.lakas.MVC.model.Product;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import javax.xml.crypto.Data;
import java.security.Principal;
import java.security.PrivateKey;
import java.sql.*;
import java.util.Formatter;

public class DataBase {
    private static DataBase instance;
    private static final String url = "jdbc:sqlite:C:\\Users\\ASUS\\IdeaProjects\\CRUD_Application\\DataBase.db";
    private final Connection conn = DriverManager.getConnection(url);
    private final Statement statement = conn.createStatement();


    private DataBase() throws SQLException {};
    public static DataBase getInstance() throws SQLException {
        if (instance == null) {
            instance = new DataBase();
        }
        return instance;
    }
    public Connection getConn() {
        return conn;
    }
    public Statement getStatement() {
        return statement;
    }


    ///////////////////operations////////////

    public void INSERT(Product product) throws SQLException {
        String query = "INSERT INTO Product(name, Description, Price) VALUES(?, ?, ?)";
        PreparedStatement pstm = conn.prepareStatement(query);

        pstm.setString(1, product.getName());
        pstm.setString(2, product.getDescription());
        pstm.setInt(3, product.getPrice());

        pstm.executeUpdate();
    }

    public ObservableList<Product> SELECT_ALL() throws SQLException {
        ObservableList<Product> productList = FXCollections.observableArrayList();

        String select_all = "SELECT * FROM Product;";
        ResultSet res = statement.executeQuery(select_all);

        while (res.next()) {
            int id = res.getInt("ID");
            String name = res.getString("Name");
            String desc = res.getString("Description");
            int price = res.getInt("Price");

            productList.add(new Product(id, name, desc, price));
        }
        return productList;
    }

    public Product SELECT_LAST_PRODUCT() throws SQLException {
        String query = "SELECT * FROM Product WHERE ID = (SELECT max(ID) FROM Product)";
        ResultSet productSet = statement.executeQuery(query);
        int id = productSet.getInt("ID");
        String name = productSet.getString("Name");
        String desc = productSet.getString("Description");
        int price = productSet.getInt("Price");
        return new Product(id, name, desc, price);
    }

    public void DELETE(Product product) throws SQLException {
        int id = product.getId();
        String query = String.format("DELETE FROM Product WHERE ID = %d", id);
        statement.execute(query);
    }

    public void UPDATE(Product product) throws SQLException {
        String query = "UPDATE Product SET name = ?, Description = ?, Price = ? WHERE id = ?";

        PreparedStatement pstm = conn.prepareStatement(query);

        pstm.setString(1, product.getName());
        pstm.setString(2, product.getDescription());
        pstm.setInt(3, product.getPrice());
        pstm.setInt(4, product.getId());

        pstm.executeUpdate();
    }

    public ObservableList<Product> findAllWithPagination(int page, int pageSize) throws SQLException {
        String sql = "SELECT * FROM Product ORDER BY id LIMIT ? OFFSET ?";
        ObservableList<Product> productList = FXCollections.observableArrayList();

        PreparedStatement pstmt = conn.prepareStatement(sql);

        int offset = (page - 1) * pageSize;
        pstmt.setInt(1, pageSize); // LIMIT
        pstmt.setInt(2, offset);  // OFFSET

        ResultSet res = pstmt.executeQuery();


        while (res.next()) {
            int id = res.getInt("ID");
            String name = res.getString("Name");
            String desc = res.getString("Description");
            int price = res.getInt("Price");

            productList.add(new Product(id, name, desc, price));
        }
        return productList;
    }

    public Product findByName(String name) throws SQLException {
        String sql = "SELECT * FROM Product WHERE name = ?;";


        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, name);
        ResultSet res = pstmt.executeQuery();

        if (res.next()) {
            int id = res.getInt("ID");
            String productName = res.getString("Name");
            String desc = res.getString("Description");
            int price = res.getInt("Price");
            return new Product(id, productName, desc, price);
        }
        return null;
    }

}

