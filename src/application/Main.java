package application;

import java.util.Optional;
import java.util.Vector;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.*;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;

/**
 * Moja klasa tworzaca okienko logowania
 * 
 * @author Łukasz Smogorzewski
 * @version 1.0
 *
 */
public class Main extends Application {

	Dialog<Pair<String, String>> dialog = new Dialog<>();
	private Vector<Pair<String, String>> productionVector = new Vector<Pair<String, String>>(8);
	private Vector<Pair<String, String>> testVector = new Vector<Pair<String, String>>(8);
	private Vector<Pair<String, String>> deweloperVector = new Vector<Pair<String, String>>(8);
	private ObservableList<String> items = FXCollections.observableArrayList();

	/**
	 * Glowona metoda klasy Main
	 * 
	 * @param args
	 *            argumenty wiersza polecenia
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Metoda inicjalizujaca vectory z przykladowymi danymi logowania
	 */
	@Override
	public void init() {
		productionVector.addElement(new Pair<>("producer", "admin1"));
		productionVector.addElement(new Pair<>("producer2", "admin1"));
		productionVector.addElement(new Pair<>("MainProducer", "root"));

		testVector.addElement(new Pair<>("tester", "password"));
		testVector.addElement(new Pair<>("jan.kowalski", "haselko"));

		deweloperVector.addElement(new Pair<>("adam.nowak", "snickers"));
		deweloperVector.addElement(new Pair<>("ewa.cudna", "123"));
		deweloperVector.addElement(new Pair<>("janusz.smutny", "rybki123"));
		deweloperVector.addElement(new Pair<>("antek.smieszny", "leszcz"));
	}

	/**
	 * Metoda tworzaca okienko dialogowe
	 */
	@Override
	public void start(Stage primaryStage) {

		dialog.setTitle("Logowanie");
		dialog.setHeaderText("Logowanie do systemu STYLEman");

		ButtonType logonButtonType = new ButtonType("Logon", ButtonData.OK_DONE);
		ButtonType anulujButtonType = new ButtonType("Anuluj", ButtonData.CANCEL_CLOSE);
		dialog.getDialogPane().getButtonTypes().addAll(logonButtonType, anulujButtonType);
		Node logonButton = dialog.getDialogPane().lookupButton(logonButtonType);
		logonButton.setDisable(true);

		GridPane gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(20, 150, 10, 10));

		Label environmentLbl = new Label("Środowisko:");
		Label userLbl = new Label("Użytkownik:");
		Label passwordLbl = new Label("Hasło:");

		ChoiceBox<String> enviChoiceBox = new ChoiceBox<>(
				FXCollections.observableArrayList("Produkcyjne", "Testowe", "Deweloperskie"));

		ComboBox<String> userComboBox = new ComboBox<>(FXCollections.observableArrayList(items));
		userComboBox.getItems().addAll(items);
		userComboBox.setEditable(true);
		userComboBox.setValue("Username");

		PasswordField passField = new PasswordField();
		passField.setPromptText("Wpisz tu hasło");

		ChangeListener<? super String> logonListener = (ov, t, t1) -> logonButton
				.setDisable(enviChoiceBox.getValue() == null || userComboBox.getValue().equals("")
						|| passField.getText().equals(""));

		enviChoiceBox.valueProperty().addListener(logonListener);
		enviChoiceBox.valueProperty().addListener((ov, t, t1) -> {
			itemsUpdate(t1);
			userComboBox.getItems().clear();
			userComboBox.getItems().addAll(items);
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
				return new Pair<>(userComboBox.getValue(), passField.getText());

			}
			return null;
		});

		dialog.showAndWait().ifPresent(usernamePassword -> {
			if (checkPassword(enviChoiceBox.getValue(), userComboBox.getValue(), passField.getText()))
				System.out
						.println("Username=" + usernamePassword.getKey() + ", Password=" + usernamePassword.getValue());
			else
				System.out.println("Bledne dane logowania");
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
		if (environment == "Produkcyjne" && productionVector.contains(new Pair<String, String>(username, password))) {
			return true;
		} else if (environment == "Testowe" && testVector.contains(new Pair<String, String>(username, password))) {
			return true;
		} else if (environment == "Deweloperskie"
				&& deweloperVector.contains(new Pair<String, String>(username, password))) {
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
		items.clear();
		if (environment == "Produkcyjne") {
			for (int i = 0; i != productionVector.size(); i++) {
				items.add(productionVector.elementAt(i).getKey());
			}
		} else if (environment == "Testowe") {
			for (int i = 0; i != testVector.size(); i++) {
				items.add(testVector.elementAt(i).getKey());
			}
		} else if (environment == "Deweloperskie") {
			for (int i = 0; i != deweloperVector.size(); i++) {
				items.add(deweloperVector.elementAt(i).getKey());
			}
		}
	}

	public Optional<Pair<String, String>> showAndWait() {
		return dialog.showAndWait();
	}

}
