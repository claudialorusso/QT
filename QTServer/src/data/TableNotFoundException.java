package data;

public class TableNotFoundException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public TableNotFoundException(String tableName){
		super("ERROR:'"+ tableName+"' table DOES NOT exist!");
	}
}
