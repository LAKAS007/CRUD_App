import com.lakas.MVC.model.Product;
import dataBase.DataBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.codehaus.plexus.util.PropertyUtils;
import org.junit.Test;
import org.junit.*;

import javax.swing.plaf.ActionMapUIResource;
import javax.xml.crypto.Data;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class TestDataBase {

    @Test
    public void testSelectLastProduct() throws SQLException {

        String query = "SELECT * FROM Product WHERE ID = (SELECT max(ID) FROM Product)";
        PreparedStatement pstm = DataBase.getInstance().getConn().prepareStatement(query);

        ResultSet resultSet = pstm.executeQuery();
        int expected = resultSet.getInt("ID");

        Product actualProduct = DataBase.getInstance().SELECT_LAST_PRODUCT();
        int actual = actualProduct.getId();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testSelectAll() throws SQLException {
        String query = "SELECT * FROM Product;";
        PreparedStatement pstm = DataBase.getInstance().getConn().prepareStatement(query);
        ResultSet res = pstm.executeQuery();

        ObservableList<Product> expected = FXCollections.observableArrayList();
        while (res.next()) {
            int id = res.getInt("ID");
            String name = res.getString("Name");
            String desc = res.getString("Description");
            int price = res.getInt("Price");

            expected.add(new  Product(id, name, desc, price));
        }

        ObservableList<Product> actual = DataBase.getInstance().SELECT_ALL();

        int cnt = 0;
        for (int i = 0; i < actual.size(); i++) {
            if (actual.get(i).getId() == expected.get(i).getId()) {
                cnt++;
            }
        }

        Assert.assertEquals(expected.size(), cnt);
    }

    @Test
    public void testInsert() throws SQLException {
        Product testProduct = new Product("testtt", "testtt", 333);
        DataBase.getInstance().INSERT(testProduct);

        Product actual = DataBase.getInstance().SELECT_LAST_PRODUCT();

        Assert.assertEquals(testProduct.getName(), actual.getName());
    }

    @Test
    public void testUpdate() throws SQLException {
        Product test = DataBase.getInstance().SELECT_LAST_PRODUCT();
        test.setName("updatedTest");

        DataBase.getInstance().UPDATE(test);

        Product actual = DataBase.getInstance().SELECT_LAST_PRODUCT();
        Assert.assertEquals("updatedTest", actual.getName());
    }

    @Test
    public void testDelete() throws SQLException {
        Product last = DataBase.getInstance().SELECT_LAST_PRODUCT();

        DataBase.getInstance().DELETE(last);

        Product actual = DataBase.getInstance().SELECT_LAST_PRODUCT();
        Assert.assertNotEquals(last.getName(), actual.getName());
    }
}
