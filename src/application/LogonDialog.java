package application;

import java.util.Optional;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.util.Pair;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.*;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;

/**
 * Klasa tworzaca okienko logowania
 * 
 * @author Lukasz Smogorzewski
 * @version 1.2
 *
 */
public class LogonDialog {

	Dialog<Pair<Environment, String>> dialog = new Dialog<>();
	Environment productionEnvironment;
	Environment testEnvironment;
	Environment deweloperEnvironment;
	ComboBox<String> userComboBox;

	/**
	 * Konstruktor LogonDialog
	 * 
	 * @param windowName
	 *            to zostanie ustawione jako nazwa okienka
	 * @param headerText
	 *            to zostanie ustawione jako nazwa naglowka
	 */
	public LogonDialog(String windowName, String headerText) {
		dialog.setTitle(windowName);
		dialog.setHeaderText(headerText);

		initializeEnvironments();
		start();
	}

	/**
	 * Metoda inicjalizujaca srodowiska z przykladowymi danymi logowania
	 */
	private void initializeEnvironments() {
		productionEnvironment = new Environment("Production");
		productionEnvironment.addUser("producer", "admin1");
		productionEnvironment.addUser("producer2", "admin1");
		productionEnvironment.addUser("MainProducer", "root");

		testEnvironment = new Environment("Test");
		testEnvironment.addUser("tester", "password");
		testEnvironment.addUser("jan.kowalski", "haselko");

		deweloperEnvironment = new Environment("Deweloper");
		deweloperEnvironment.addUser("adam.nowak", "snickers");
		deweloperEnvironment.addUser("ewa.cudna", "123");
		deweloperEnvironment.addUser("janusz.smutny", "rybki123");
		deweloperEnvironment.addUser("antek.smieszny", "leszcz");
	}

	/**
	 * Metoda tworzaca okienko dialogowe
	 */
	public void start() {
		ButtonType logonButtonType = new ButtonType("Logon", ButtonData.OK_DONE);
		ButtonType anulujButtonType = new ButtonType("Anuluj", ButtonData.CANCEL_CLOSE);
		dialog.getDialogPane().getButtonTypes().addAll(logonButtonType, anulujButtonType);
		Node logonButton = dialog.getDialogPane().lookupButton(logonButtonType);
		logonButton.setDisable(true);

		GridPane gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(20, 150, 10, 10));

		Label environmentLbl = new Label("Srodowisko:");
		Label userLbl = new Label("Uzytkownik:");
		Label passwordLbl = new Label("Haslo:");

		ChoiceBox<String> enviChoiceBox = new ChoiceBox<>(
				FXCollections.observableArrayList("Produkcyjne", "Testowe", "Deweloperskie"));

		userComboBox = new ComboBox<>();
		userComboBox.setEditable(true);
		userComboBox.setValue("Username");

		PasswordField passField = new PasswordField();
		passField.setPromptText("Wpisz tu haslo");

		ChangeListener<? super String> logonListener = (ov, t, t1) -> logonButton
				.setDisable(enviChoiceBox.getValue() == null || userComboBox.getValue().equals("")
						|| passField.getText().equals(""));

		enviChoiceBox.valueProperty().addListener(logonListener);
		enviChoiceBox.valueProperty().addListener((ov, t, t1) -> {
			itemsUpdate(t1);
		});

		userComboBox.valueProperty().addListener(logonListener);

		passField.textProperty().addListener(logonListener);

		gridPane.add(environmentLbl, 0, 0);
		gridPane.add(enviChoiceBox, 1, 0);
		gridPane.add(userLbl, 0, 1);
		gridPane.add(userComboBox, 1, 1);
		gridPane.add(passwordLbl, 0, 2);
		gridPane.add(passField, 1, 2);

		dialog.getDialogPane().setContent(gridPane);

		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == logonButtonType) {
				if (checkPassword(enviChoiceBox.getValue(), userComboBox.getValue(), passField.getText()))
					return new Pair<>(new Environment(enviChoiceBox.getValue()), userComboBox.getValue());

			}
			return null;
		});
	}

	/**
	 * Metoda sprawdzajaca wprowadzone dane logowania
	 * 
	 * @param environment
	 *            podane srodowisko
	 * @param username
	 *            podana nazwa uzytkownika
	 * @param password
	 *            podane haslo
	 * @return zwraca informacje czy dane sa poprawne
	 */
	private boolean checkPassword(String environment, String username, String password) {
		if (environment == "Produkcyjne" && productionEnvironment.checkUser(username, password)) {
			return true;
		} else if (environment == "Testowe" && testEnvironment.checkUser(username, password)) {
			return true;
		} else if (environment == "Deweloperskie" && deweloperEnvironment.checkUser(username, password)) {
			return true;
		}
		return false;
	}

	/**
	 * Metoda potrzebna do zmiany zawartosci comboboxa
	 * 
	 * @param environment
	 *            nazwa srodowiska na ktore ma zostac wykonana zmiana
	 */
	private void itemsUpdate(String environment) {
		userComboBox.setValue("");
		if (environment == "Produkcyjne") {
			userComboBox.setItems(productionEnvironment.getUsers());
		} else if (environment == "Testowe") {
			userComboBox.setItems(testEnvironment.getUsers());
		} else if (environment == "Deweloperskie") {
			userComboBox.setItems(deweloperEnvironment.getUsers());
		}
	}

	public Optional<Pair<Environment, String>> showAndWait() {
		return dialog.showAndWait();
	}
}
