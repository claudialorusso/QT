package database;
/**
 * Modella la restituzione di un ResultSet vuoto
 * @author Lorusso Claudia, Dileo Angela
 */
public class EmptySetException extends Exception {
	/**
	 * ID di serializzazione
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Costruttore della classe EmptySetException.
	 * Stampa a video un messaggio di errore
	 * nel caso in cui il resultset risulti vuoto.
	 */
	public EmptySetException(){
		super("ERROR: Resultset empty!");
	}
}