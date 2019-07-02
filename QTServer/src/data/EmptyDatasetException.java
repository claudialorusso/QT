package data;
/**
 * Modella un'eccezione controllata da considerare qualora
 * il dataset sia vuoto.
 * @author Lorusso Claudia, Dileo Angela
 */
public class EmptyDatasetException extends Exception{
	/**
	 * ID di serializzazione
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Costruttore della classe EmptyDatasetException.
	 * Stampa a video un messaggio di errore nel caso 
	 * in cui il dataset e' vuoto.
	 */
	public EmptyDatasetException(){
		super("ERROR:The Data set is empty!");
	}
}