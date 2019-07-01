package database;
/**
 * 
 * @author Lorusso Claudia, Dileo Angela
 * Modella il fallimento di una connessione
 * al database.
 */
public class DatabaseConnectionException extends Exception {
	private static final long serialVersionUID = 1L;
	/**
	 * Costruttore della classe DatabaseConnectionException.
	 * Stampa a video un messaggio di errore quando la
	 * connessione al database fallisce.
	 */
	public DatabaseConnectionException(){
		super("ERROR: Database connection failed!");
	}
}
