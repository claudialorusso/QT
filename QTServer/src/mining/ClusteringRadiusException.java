package mining;

/**
 * Modella un'eccezione controllata da considerare
 * qualora l'algoritmo di clustering generi un solo {@link Cluster}.
 * @author Claudia Lorusso, Angela Dileo
 */
public class ClusteringRadiusException extends Exception {
	
	/**
	 * ID di Serializzazione.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Secondo costruttore della classe {@link ClusteringRadiusException}.
	 * <br>
	 * Stampa a video un messaggio quando il valore del raggio
	 * e' talmente alto da fare in modo che tutte le tuple siano contenute in
	 * un unico {@link Cluster}.
	 * @param tuple numero di tuple
	 */
	ClusteringRadiusException(int tuple) {
		super("This radius value puts " + tuple + " tuple into one cluster!");
	}
}