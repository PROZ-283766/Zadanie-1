package application;

import java.util.Optional;

import javafx.application.Application;
import javafx.util.Pair;
import javafx.stage.Stage;

/**
 * Glowna klasa
 * 
 * @author Lukasz Smogorzewski
 * @version 1.1
 *
 */
public class Main extends Application {

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
	 * Metoda tworzaca LogonDialog i wysietlajaca wynik logowania.
	 */
	@Override
	public void start(Stage primaryStage) {

		Optional<Pair<Environment, String>> result = (new LogonDialog("Logowanie", "Logowanie do systemu STYLEman"))
				.showAndWait();

		if (result.isPresent()) {
			System.out.println("Zalogowano " + result.get().getValue() + " do " + result.get().getKey());
		} else {
			System.out.println("Bledne dane logowania");
		}

	}
}
