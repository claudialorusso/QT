package mining;
/**
 * Modella un'eccezione controllata da considerare
 * qualora l'algoritmo di clustering generi un solo Cluster.
 * @author Claudia Lorusso, Angela Dileo
 */
public class ClusteringRadiusException extends Exception{
	/**
	 * ID di Serializzazione.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Costruttore della classe ClusteringRadiusException.
	 */
	ClusteringRadiusException() {}
	/**
	 * Secondo costruttore della classe ClusteringRadiusException.</p>
	 * Stampa a video un messaggio quando il valore del raggio
	 * sia talmente alto da fare in modo che tutte le tuple siano contenute in
	 * un unico Cluster.
	 * @param tuple numero di tuple
	 */
	ClusteringRadiusException(int tuple) {
		super("This radius value puts "+tuple+" tuple into one cluster!");
	}
}