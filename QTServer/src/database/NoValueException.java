package database;
/**
 * Modella l’assenza di un valore all’interno di un ResultSet
 * @author Claudia Lorusso, Angela Dileo
 */
public class NoValueException extends Exception {
	private static final long serialVersionUID = 1L;
	/**
	 * Costruttore della classe NoValueException.</p>
	 * Stampa a video un messaggio di errore
	 * nel caso in cui venga sollevata l'eccezione.
	 * @param msg messaggio di errore
	 * dovuto all'assenza di un valore all'interno di un Resultet
	 */
	//public
	NoValueException(String msg) {
		super(msg);
	}
	/**
	 * Costruttore di default della classe con messaggio
	 * di default.
	 */
	//public
	NoValueException() {
		super("ERROR: Absent value!");
	}
}