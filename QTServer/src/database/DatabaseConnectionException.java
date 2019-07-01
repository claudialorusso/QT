package database;
/**
 * 
 * @author Claudia Lorusso
 * Modella il fallimento di una connessione
 * al database.
 */
public class DatabaseConnectionException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public DatabaseConnectionException(){
		super("ERROR: Database connection failed!");
	}
}
