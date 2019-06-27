package mining;
public class ClusteringRadiusException extends Exception{
	/**
	 *
	 */
	private static final long serialVersionUID = 4917497189702825018L;
	public ClusteringRadiusException() {}
	public ClusteringRadiusException(int tuple) {
		super("This radius value puts "+tuple+" tuple into one cluster!");
	}
}
