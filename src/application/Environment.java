package application;

import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Klasa opisujaca srodowisko do ktorego moze sie logowac.
 * 
 * @author Lukasz Smogorzewski
 * @version 1.2
 *
 */
public class Environment {

	private String environmentName;
	private HashMap<String, String> users;

	/**
	 * Konstruktor tej klasy
	 * 
	 * @param n
	 *            nazwa srodowiska
	 */
	public Environment(String n) {
		environmentName = n;
		users = new HashMap<String, String>();
	}

	/**
	 * Metoda nadpisujaca java.lang.Object.toString
	 * 
	 * @return nazwa srodowiska
	 */
	@Override
	public String toString() {
		return environmentName;
	}

	/**
	 * Metoda zwracajaca nazwe srodowiska
	 * 
	 * @return nazwa srodowiska
	 */
	public String getEnviromentName() {
		return environmentName;
	}

	/**
	 * Metoda dodajaca uzytkownika
	 * 
	 * @param username
	 *            nazwa uzytkownika
	 * @param password
	 *            haslo uzytkownika
	 */
	public void addUser(String username, String password) {
		if (!users.containsKey(username))
			users.put(username, password);
	}

	/**
	 * Metoda zwracajaca observableArrayList nazw uzytkownikow
	 * 
	 * @return observableArrayList
	 */
	public ObservableList<String> getUsers() {
		return FXCollections.observableArrayList(users.keySet());
	}

	/**
	 * Metoda sprawdzajaca poprawnosc danych
	 *
	 * @param username
	 *            nazwa uzytkownika
	 * @param password
	 *            haslo uzytkownika
	 * @return true jesli dane sa poprawne, false w p.p.
	 */
	public boolean checkUser(String username, String password) {
		if (users.containsKey(username) && users.get(username).matches(password))
			return true;
		return false;
	}

}
