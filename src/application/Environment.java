package application;

/**
 * Klasa opisujaca srodowisko do ktorego moze sie logowac.
 * 
 * @author Lukasz Smogorzewski
 * @version 1.1
 *
 */
public class Environment {

	private String environmentName;

	/**
	 * Konstruktor tej klasy
	 * 
	 * @param n
	 *            nazwa srodowiska
	 */
	public Environment(String n) {
		environmentName = n;
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

}
