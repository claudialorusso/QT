package data;
/**
 * Modella la restituzione di una tabella inesistente.
 * @author Claudia Lorusso, Angela Dileo
 */
public class TableNotFoundException extends Exception{
	private static final long serialVersionUID = 1L;
	/**
	 * Costruttore della classe TableNotFoundException.
	 * Stampa a video un messaggio di errore nel caso in cui
	 * la tabella richiesta non esiste.
	 * @param tableName Tabella richiesta
	 */
	TableNotFoundException(String tableName){
		super("ERROR:'"+ tableName+".dmp' table DOES NOT exist!");
	}
}