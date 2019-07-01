package database;
/**
 * Modella l’assenza di un valore all’interno di un resultset
 * @author Claudia Lorusso
 */
public class NoValueException extends Exception {
	private static final long serialVersionUID = 1L;
	/**
	 * Costruttore della classe NoValueException.
	 * Stampa a video un messaggio di errore
	 * nel caso in cui venga sollevata l'eccezione.
	 * @param msg messaggio di errore
	 * dovuto all'assenza di un valore all'interno di un resultset
	 */
	public NoValueException(String msg) {
		super(msg);
	}
	public NoValueException() {
		super("ERROR: Absent value!");
	}
}