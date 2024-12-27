package util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class Alerts {
    private Alerts(){};
    public static void showAlertError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка ввода данных");
        alert.setContentText("Вы не ввели данные в поля");
        alert.show();
    }

    public static void showAlertNotSelected() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Не выбрано поле");
        alert.setContentText("Вы не выбрали поле для изменения");
        alert.show();
    }

    public static boolean showAlertConfirmationDelete() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Подтверждение удаления");
        alert.setContentText("Вы уверены, что хотите удалить выбранный элемент?");
        Optional<ButtonType> answer = alert.showAndWait();

        if (answer.get() == ButtonType.OK) {
            return true;
        }
        else {
            return false;
        }

    }

    public static void showAlertIncorrectDataEnter() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Не корректный ввод данных");
        alert.setContentText("Вы ввели не корректные данные");
        alert.show();
    }
}
