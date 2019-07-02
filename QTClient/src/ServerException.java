/**
 * Classe che gestisce l'eccezione
 * ServerException sollevata nel caso
 * in cui si verichino degli errori di connessione
 * con il Server o di una richiesta
 * non valida da parte del Client.
 * @author Claudia Lorusso, Angela Dileo
 *
 */
public class ServerException extends Exception {
	/**
	 * ID di serializzazione.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Costruttore di default, con messaggio personalizzato.
	 */
	public ServerException () {
		super("Server connection error!");
	}
	/**
	 * Costruttore parametrizzato
	 * con messaggio personalizzato.
	 * @param result stringa contenente
	 * la richiesta, errata, da parte del Client.
	 */
	public ServerException(String result) {
		super("ERROR: Invalid client request;\nMOTIVATION:'"+result+"'");////////////////////////////////////////
	}
}
