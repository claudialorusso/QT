package database;
/**
 * Modella la restituzione di un resultset vuoto
 * @author Claudia Lorusso
 */
public class EmptySetException extends Exception {
	private static final long serialVersionUID = 1L;
	/**
	 * Costruttore della classe EmptySetException.
	 * Stampa a video un messaggio di errore
	 * nel caso in cui il resultset risulti vuoto.
	 */
	public EmptySetException(){
		super("ERRORE: Resultset vuoto!");
	}
}
