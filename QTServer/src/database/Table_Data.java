package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import database.Table_Schema.Column;

/**
 * Modella l’insieme di transazioni collezionate in una tabella.<br>
 * La singola transazione è modellata dalla classe {@link Example}.
 */
public class Table_Data {
	
	/**
	 * Permette l'accesso al database.
	 */
	private DbAccess db;
	
	/**
	 * Costruttore della classe {@link Table_Data}.<br>
	 * Fornisce l'accesso al database.
	 * @param db permette l'accesso al DB
	 */
	public Table_Data(DbAccess db) {
		this.db=db;
	}
	
	/**
	 * Ricava lo schema della tabella <tt>table</tt>.<br>
	 * Esegue un'interrogazione per estrarre le tuple distinte 
	 * da tale tabella.<br>
	 * Per ogni tupla del dataset, si crea un oggetto,
	 * istanza della classe {@link Example},
	 * il cui riferimento va incluso nella lista da restituire.<br>
	 * In particolare, per la tupla corrente nel dataset,
	 * si estraggono i valori dei singoli campi
	 * - che possono essere ti tipo double o stringhe -,
	 * e li si aggiungono all’oggetto istanza della classe {@link Example}
	 * che si sta costruendo.
	 * @param table nome della tabella nel database
	 * @return lista di transazioni/tuple distinte memorizzate
	 * nella tabella
	 * @throws SQLException in presenza di errori
	 * nella esecuzione della query
	 * @throws EmptySetException se il dataset è vuoto
	 */
	public List<Example> getDistinctTransazioni(String table) throws SQLException, EmptySetException{
		LinkedList<Example> transSet = new LinkedList<Example>();
		Statement statement;
		Table_Schema tSchema=new Table_Schema(db,table);
		String query="select distinct ";
		for (int i=0; i<tSchema.getNumberOfAttributes(); i++){
			Column c = tSchema.getColumn(i);
			if (i > 0)
				query += ",";
			query += c.getColumnName();
		}
		if (tSchema.getNumberOfAttributes() == 0)
			throw new SQLException();
		query += (" FROM "+table);
		statement = db.getConnection().createStatement();
		ResultSet rs = statement.executeQuery(query);
		boolean empty = true;
		while (rs.next()) {
			empty=false;
			Example currentTuple=new Example();
			for (int i=0; i<tSchema.getNumberOfAttributes(); i++)
				if (tSchema.getColumn(i).isNumber())
					currentTuple.add(rs.getDouble(i + 1));
				else
					currentTuple.add(rs.getString(i + 1));
			transSet.add(currentTuple);
		}
		rs.close();
		statement.close();
		if(empty) throw new EmptySetException();
		return transSet;
	}

	/**
	 * Formula ed esegue una interrogazione SQL
	 * per:<br><ul>
	 * <li>	estrarre i valori distinti ordinati
	 * 		di column;
	 * <li> popolare un insieme da restituire.</ul>
	 * @param table nome della tabella
	 * @param column nome della colonna della tabella
	 * @return insieme di valori distinti ordinati
	 * in modalita' ascendente che l'attributo
	 * identificato dal nome column
	 * assume nella tabella
	 * identificata dal nome table
	 * @throws SQLException in presenza di errori
	 * nella esecuzione della query
	 */
	public Set<Object> getDistinctColumnValues(String table, Column column) throws SQLException {
		Set<Object> valueSet = new TreeSet<Object>();
		Statement statement;
		String query = "select distinct ";
		query += column.getColumnName();
		query += (" FROM "+ table);
		query += (" ORDER BY " + column.getColumnName());
		statement = db.getConnection().createStatement();
		ResultSet rs = statement.executeQuery(query);
		while (rs.next())
			if (column.isNumber())
				valueSet.add(rs.getDouble(1));
			else
				valueSet.add(rs.getString(1));
		rs.close();
		statement.close();
		return valueSet;
	}
	
	/**
	 * Formula ed esegue una interrogazione SQL
	 * per estrarre il valore aggregato
	 * (valore minimo o valore massimo)
	 * cercato nella colonna <tt>column</tt>
	 * della tabella <tt>table</tt>.
	 * @param table nome della tabella
	 * @param column nome della colonna
	 * @param aggregate operatore SQL di aggregazione(min,max).
	 * aggregate e' di tipo {@link QUERY_TYPE}.
	 * @return aggregato cercato
	 * @throws SQLException in presenza di errori
	 * nella esecuzione della query 
	 * @throws NoValueException se il resultset e' vuoto
	 * o il valore calcolato e' pari a null
	 */
	public Object getAggregateColumnValue(String table, Column column, QUERY_TYPE aggregate) throws SQLException,NoValueException{
		Statement statement;
		Object value = null;
		String aggregateOp = "";
		String query = "select ";
		if(aggregate == QUERY_TYPE.MAX)
			aggregateOp += "max";
		else
			aggregateOp += "min";
		query += aggregateOp + "(" + column.getColumnName() + ") FROM " + table;
		statement = db.getConnection().createStatement();
		ResultSet rs = statement.executeQuery(query);
		if (rs.next())
			if (column.isNumber())
				value=rs.getFloat(1);
			else
				value=rs.getString(1);
		rs.close();
		statement.close();
		if (value == null)
			throw new NoValueException("No " + aggregateOp + " on "+ column.getColumnName());
		return value;
	}
}