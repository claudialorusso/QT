package data;

public class EmptyDatasetException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public EmptyDatasetException(){
		super("ERROR:The Data set is empty!");
	}
}