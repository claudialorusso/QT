package data;

/**
 * Modella la restituzione di una tabella inesistente.
 * @author Claudia Lorusso, Angela Dileo
 */
public class TableNotFoundException extends Exception{
	/**
	 * ID di serializzazione
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Costruttore della classe {@link TableNotFoundException}.<br>
	 * Stampa a video un messaggio di errore nel caso in cui
	 * la tabella richiesta non esiste.
	 * @param tableName Tabella richiesta
	 */
	TableNotFoundException(String tableName){
		super("'" + tableName + "' table DOES NOT exist!");
	}
}