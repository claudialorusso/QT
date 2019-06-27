
public class ServerException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ServerException () {
		super("ERRORE di connessione al server.");
	}

	public ServerException(String result) {
		super("ERROR: Richiesta client '"+result+"' non valida!");////////////////////////////////////////
	}
}
