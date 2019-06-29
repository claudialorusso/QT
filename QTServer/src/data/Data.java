package data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import database.DatabaseConnectionException;
import database.DbAccess;
import database.EmptySetException;
import database.Example;
import database.NoValueException;
import database.QUERY_TYPE;
import database.Table_Data;
import database.Table_Schema;
/**
 * Modella l'insieme di tuple
 * anche dette transizioni
 * @author Dileo Angela, Lorusso Claudia
 */
public class Data implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8785322021794956318L;
	//private Object data[][];
	/**
	 * Lista di Example/tuple
	 */
	private List<Example> data = new ArrayList<Example>();
	/**
	 * cardinalita' dell'insieme di tuple,
	 * cioe' il numero di righe in data,
	 * cioe' numero di tuple in data
	 */
	private int numberOfExamples;
	/**
	 * Lista degli attributi contenuti in ciascuna tupla
	 * cioe' lo schema della tabella di dati
	 */
	private List<Attribute> attributeSet = new LinkedList<Attribute>();

	/**
	 * 
	 * Costruttore della classe Data/////////////////////////////////////
	 * @param tableName nome della tabella da cui prelevare
	 * i dati nel Database
	 * @throws DatabaseConnectionException
	 * @throws SQLException
	 * @throws EmptyDatasetException
	 * @throws TableNotFoundException
	 */
	public Data(String tableName) throws DatabaseConnectionException, SQLException, EmptyDatasetException,TableNotFoundException, NoValueException{
		try{
			DbAccess db = new DbAccess();
			db.initConnection();
			Table_Data table = new Table_Data(db);
			boolean exists = false;
			try {
				exists=TableVerify.tableExists(db.getConnection(),tableName);
				if (exists) {
					data=table.getDistinctTransazioni(tableName);
					numberOfExamples=data.size();
					if(numberOfExamples>0) {
						Table_Schema tschema = new Table_Schema(db,tableName);
						Attribute attribute;
						for(int i=0;i<tschema.getNumberOfAttributes();i++) {
							if(tschema.getColumn(i).isNumber()) {
								double maxTemp = (float) table.getAggregateColumnValue(tableName, tschema.getColumn(i), QUERY_TYPE.MAX);
								double minTemp = (float) table.getAggregateColumnValue(tableName, tschema.getColumn(i), QUERY_TYPE.MIN);
								attribute = new ContinuousAttribute(tschema.getColumn(i).getColumnName(),i,minTemp,maxTemp);
							}else {
								/**
								 * Set temporaneo in cui memorizzo i values
								 * privi di duplicati da passare al costruttore
								 * DiscreteAttribute
								 */
								Set<String> values = new TreeSet<String>();
								/**
								 * Valori effettivamente contenuti in Data
								 */
								Set<Object> efValues = table.getDistinctColumnValues(tableName, tschema.getColumn(i));
								for(Object ob: efValues) {
									values.add((String)ob);
								}
								attribute =new DiscreteAttribute(tschema.getColumn(i).getColumnName(),i,(TreeSet<String>)values);
							}
							attributeSet.add(attribute);
						}
					}else throw new EmptyDatasetException();
					//db.closeConnection();
				}else throw new TableNotFoundException(tableName);
			}catch(SQLException ex2) {
				System.out.println(ex2.getMessage());
			}catch(EmptySetException empty){
				System.out.println(empty.getMessage());
			}
		}catch(IndexOutOfBoundsException e) {
			System.out.println(e.getMessage());
		}
	}
	/**
	 * Restituisce il numero di tuple/esempi
	 * @return numero di tuple/esempi
	 */
	public int getNumberOfExamples() {
		return numberOfExamples;
	}
	/**
	 * Restituisce la dimensione di attributeSet
	 * cioe' il numero di attributi presenti
	 * nel set
	 * @return la dimensione del set di attributi
	 */
	public int getNumberOfAttributes() {
		return attributeSet.size();
	}
	/**
	 * Restituisce l'attributeSet
	 * @return lo schema degli attributi
	 */
	private List<Attribute> getAttributeSchema() {
		return attributeSet;
	}
	/**
	 * Restituisce il valore della matrice
	 * data avendo in input una riga ed una colonna
	 * @param exampleIndex indice di riga
	 * @param attributeIndex riferimento alla matrice
	 * memorizzata in data
	 * @return valore assunto in data dall'attributo
	 * in posizione attributeIndex,
	 * nella riga in posizione exampleIndex
	 */
	public Object getAttributeValue(int exampleIndex, int attributeIndex){
		return data.get(exampleIndex).get(attributeIndex);

	}
	/**
	 * Override del metodo toString di Object.
	 * <p>
	 * Crea una stringa in cui memorizza
	 * lo schema della tabella
	 * e le tuple memorizzate in data
	 * opportunamente numerate
	 */
	////////////////////////////////////////////////////IMPLEMENTARE ITERATORE
	@Override
	public String toString() throws NullPointerException{
		String transazioni = new String() ;
		int i=0;
		int j;

		for(Attribute a: getAttributeSchema()) {
			transazioni=(i<this.getNumberOfAttributes()-1)?
					(transazioni += a.getName() + ", ")
					:(transazioni += a.getName() + " " + "\n");
					i++;
		}
		i=0;
		while(i<getNumberOfExamples()){
			j=0;
			transazioni += i +":";
			while(j<getNumberOfAttributes()-1){
				transazioni += getAttributeValue(i,j).toString() +", ";
				j++;
			}
			transazioni += getAttributeValue(i,j).toString() +"\n";
			i++;
		}		
		return transazioni;
	}
	/**
	 * Crea e restituisce un oggetto di Tuple
	 * che modella come sequenza di coppie
	 * Attributo-valore la i-esima riga in data.
	 * <p>
	 * Nello specifico, memorizzo in tuple
	 * l'Item discreto corrente.
	 * @param index indice di riga da cui
	 * prelevare l'attributo in Data
	 * @return la tupla desiderata
	 */
	public Tuple getItemSet(int index) {
		Tuple tuple=new Tuple(this.getNumberOfAttributes());
		int i=0;
		/*
		 * per ogni attributo presente nel set
		 * memorizzo in tuple l'attributo discreto
		 * ed il corrispondente valore discreto
		 * in altre parole l'Item discreto corrente 
		 */
		for(Attribute a: attributeSet) {
			if (a instanceof DiscreteAttribute) {
				tuple.add(new DiscreteItem((DiscreteAttribute) a,(String)this.getAttributeValue(index,i)),i);	
			}
			else {
				tuple.add(new ContinuousItem((ContinuousAttribute)a,(Double)this.getAttributeValue(index,i)),i);
			}
			i++;
		}
		return tuple;
	}
	/**
	 * Inner class che permette di verificare
	 * se il nome della tabella da cercare nel database
	 * corrisponde ad una tabella nel database o meno.
	 * @author Claudia Lorusso
	 *
	 */
	static class TableVerify{
		static boolean tableExists(Connection conn, String tableName) throws SQLException,SQLSyntaxErrorException {
			boolean tExists = false;
			try (ResultSet rs = conn.getMetaData().getTables(null, null, tableName, null)) {
				while (rs.next()) { 
					String tName = rs.getString("TABLE_NAME");
					if (tName != null && tName.equals(tableName)) {
						tExists = true;
						break;
					}
				}
			}
			return tExists;
		}
	}

	/*	public static void main(String args[]){
		Data dataSet = new Data();
		System.out.println(dataSet);
	}*/
}