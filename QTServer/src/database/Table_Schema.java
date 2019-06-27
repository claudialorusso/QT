package database;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
/**
 *  Modella lo schema di una tabella nel database relazionale
 */
public class Table_Schema {
	/**
	 * Permette l'accesso al Database
	 */
	DbAccess db;
	/**
	 * Inner Class.
	 * Modella una colonna della tabella.
	 */
	public class Column{
		/**
		 * Nome della colonna.
		 */
		private String name;
		/**
		 * Tipo della colonna.
		 */
		private String type;
		/**
		 * Costruttore della classe Column.
		 * Assegna un nome ed un tipo effettivo alla colonna.
		 * @param name nome effettivo della colonna
		 * @param type tipo effettivo della colonna
		 */
		Column(String name,String type){
			this.name=name;
			this.type=type;
		}
		/**
		 * Restituisce il nome della colonna.
		 * @return nome della colonna.
		 */
		public String getColumnName(){
			return name;
		}
		/**
		 * Restituisce un valore booleano
		 * a seconda che il tipo della colonna
		 * sia numerico o meno.
		 * @return true se il tipo e' numerico,
		 * false altrimenti
		 */
		public boolean isNumber(){
			return type.equals("number");
		}
		/**
		 * Riscrittura del toString.
		 * Permette di memorizzare in un'unica stringa
		 * il nome ed il tipo della colonna.
		 */
		public String toString(){
			return name+":"+type;
		}
	}
	/**
	 * Schema della tabella
	 * realizzato tramite una lista di colonne.
	 */
	List<Column> tableSchema=new ArrayList<Column>();
	/**
	 * Costruttore di Table_Schema.
	 * Modella lo schema della tabella inserendo
	 * il nome ed il tipo di ogni sua colonna.
	 * @param db permette l'accesso al database
	 * @param tableName nome della tabella
	 * @throws SQLException quando SQL Server restituisce un avviso o un errore.
	 */
	public Table_Schema(DbAccess db, String tableName) throws SQLException{
		this.db=db;
		HashMap<String,String> mapSQL_JAVATypes=new HashMap<String, String>();
		//http://java.sun.com/j2se/1.3/docs/guide/jdbc/getstart/mapping.html
		mapSQL_JAVATypes.put("CHAR","string");
		mapSQL_JAVATypes.put("VARCHAR","string");
		mapSQL_JAVATypes.put("LONGVARCHAR","string");
		mapSQL_JAVATypes.put("BIT","string");
		mapSQL_JAVATypes.put("SHORT","number");
		mapSQL_JAVATypes.put("INT","number");
		mapSQL_JAVATypes.put("LONG","number");
		mapSQL_JAVATypes.put("FLOAT","number");
		mapSQL_JAVATypes.put("DOUBLE","number");

		Connection con=db.getConnection();
		DatabaseMetaData meta = con.getMetaData();
		ResultSet res = meta.getColumns(null, null, tableName, null);

		while (res.next()) {

			if(mapSQL_JAVATypes.containsKey(res.getString("TYPE_NAME")))
				tableSchema.add(new Column(
						res.getString("COLUMN_NAME"),
						mapSQL_JAVATypes.get(res.getString("TYPE_NAME")))
						);
		}
		res.close();
	}
	/**
	 * Restituisce il numero di attributi
	 * e dunque il numero di colonne della tabella///////////////////////////
	 * @return
	 */
	public int getNumberOfAttributes(){
		return tableSchema.size();
	}
	/**
	 * Restituisce lo schema della colonna
	 * e dunque il suo nome ed il suo tipo.
	 * @param index indice della colonna da restituire
	 * @return schema della colonna
	 */
	public Column getColumn(int index){
		return tableSchema.get(index);
	}
}