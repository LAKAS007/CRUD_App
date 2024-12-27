package util;

import com.lakas.MVC.model.Product;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LogicalExpressionUtil {

    private LogicalExpressionUtil() {};
    public static boolean isInsertFieldsEmpty(TextField name, TextField desc, TextField price) {
        if (name.getText().isEmpty() || desc.getText().isEmpty() || price.getText().isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean isDuplicate(String name, TableView<Product> mainTable) {

        List<String> names = mainTable.getItems().stream()
                .map(Product::getName)
                .toList();
        for (String nameCheck : names) {
            if (nameCheck.equals(name)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isDuplicateUpdate(int id, String newName, ObservableList<Product> mainTable) {
        for (int i = 0; i < mainTable.size(); i++) {
            Product iterProduct = mainTable.get(i);
            if (iterProduct.getId() != id && iterProduct.getName().equals(newName)) {
                return true;
            }
        }
        return false;
    }
}
