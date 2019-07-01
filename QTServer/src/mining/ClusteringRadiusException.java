package mining;
/**
 * Modella un'eccezione controllata da considerare
 * qualora l'algoritmo di clustering generi un solo cluster.
 * @author Lorusso Claudia, Dileo Angela
 *
 */
public class ClusteringRadiusException extends Exception{
	private static final long serialVersionUID = 4917497189702825018L;
	/**
	 * Costruttore della classe ClusteringRadiusException.
	 */
	public ClusteringRadiusException() {}
	/**
	 * Secondo costruttore della classe ClusteringRadiusException.
	 * Stampa a video un messaggio quando il raggio viene inserito in un cluster (????)
	 * @param tuple Tupla di riferimento
	 */
	public ClusteringRadiusException(int tuple) {
		super("This radius value puts "+tuple+" tuple into one cluster!");
	}
}
