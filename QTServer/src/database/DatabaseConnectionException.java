package database;
/**
 * Modella il fallimento di una connessione
 * al database.
 * @author Lorusso Claudia, Dileo Angela
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
	//public
	DatabaseConnectionException(){
		super("ERROR: Database connection failed!");
	}
}
