package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Realizza l'accesso alla base di dati.
 * @author Claudia Lorusso, Angela Dileo
 */
public class DbAccess {
	/**
	 * Nome del Driver
	 */
	private String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
	/**
	 * Indirizzo dbms
	 */
	private final String DBMS = "jdbc:mysql";
	/**
	 * id server su cui risiede il database
	 */
	private final String SERVER="localhost";
	/**
	 * Nome del database
	 */
	private final String DATABASE = "MapDB";
	/**
	 * Porta su cui DBMS mysql accetta le connessioni
	 */
	private final String PORT="3306";
	/**
	 * Nome utente per accesso al db
	 */
	private final String USER_ID = "MapUser";
	/**
	 * Password per accesso al db
	 */
	private final String PASSWORD = "map";
	/**
	 *Gestisce la connessione
	 */
	private Connection conn;
	/**
	 * Permette di sincronizzare il TimeZone
	 */
	private final String TIMEZONE = "?serverTimezone=UTC";
	/**
	 * Impartisce al class loader l’ordine di caricare il driver mysql,
	 * inizializza la connessione riferita da conn.
	 * @throws DatabaseConnectionException in caso di fallimento nella connessione al database.
	 */
	public void initConnection() throws DatabaseConnectionException{
		try {
			Class.forName(DRIVER_CLASS_NAME).newInstance();
			String str = DBMS + "://" + SERVER + ":" + PORT + "/" + DATABASE + TIMEZONE;
			conn = DriverManager.getConnection(str,USER_ID,PASSWORD);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new DatabaseConnectionException();
		}
	}
	/**
	 * Restituisce la connessione al Database
	 * @return connessione al Database
	 */
	public Connection getConnection(){
		return this.conn;
	}
	/**
	 * Chiude la connessione al Database
	 */
	public void closeConnection(){
		try{
			conn.close();
		} catch (SQLException e){
			System.out.println(e.getMessage());
		}
	}
}