/**
 * Package che gestisce l'acquisizione dei dati presenti
 * nelle tabelle di un Database.
 */
package database;
/**
 * Modella il fallimento di una connessione
 * al database.
 * @author Claudia Lorusso, Angela Dileo
 */
public class DatabaseConnectionException extends Exception {
	/**
	 * ID di serializzazione
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Costruttore della classe DatabaseConnectionException.
	 * Stampa a video un messaggio di errore quando la
	 * connessione al database fallisce.
	 */
	DatabaseConnectionException(){
		super("ERROR: Database connection failed!");
	}
}