
public class ServerException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ServerException () {
		super("Server connection error!");
	}

	public ServerException(String result) {
		super("ERROR: Invalid client request;\nMOTIVATION:'"+result+"'");////////////////////////////////////////
	}
}
