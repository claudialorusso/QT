package data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
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
 * anche dette transizioni o esempi(Examples)
 * @author Dileo Angela, Lorusso Claudia
 */
public class Data implements Serializable {
	/**
	 * ID di serializzazione
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Lista di Example/tuple
	 */
	private List<Example> data = new ArrayList<Example>();
	/**
	 * Cardinalita' dell'insieme di tuple,
	 * cioe' numero di tuple in data
	 */
	private int numberOfExamples;
	/**
	 * Lista degli attributi contenuti in ciascuna tupla
	 * cioe' lo schema della tabella di dati
	 */
	private List<Attribute> attributeSet = new LinkedList<Attribute>();
	/**
	 * Costruttore della classe Data. </p>
	 * Preleva da una tabella presente nel database
	 * i dati necessaria ll'inizializzazione di Data.
	 * Preleva i valori da inserire in DescreteAttribute ed in
	 * ContinuousAttribute.
	 * @param tableName nome della tabella da cui prelevare
	 * i dati nel Database
	 * @throws DatabaseConnectionException nel caso di errori durante la connessione al DB
	 * @throws SQLException in presenza di errori nella esecuzione della query
	 * @throws EmptyDatasetException nel caso in cui il DataSet dovesse risultare vuoto
	 * @throws TableNotFoundException nel caso in cui non esistesse la tabella all'interno del
	 * database
	 * @throws NoValueException se il resultset e' vuoto o il valore calcolato e' pari a null
	 */
	public Data(String tableName) throws DatabaseConnectionException, SQLException,TableNotFoundException, NoValueException,EmptySetException{
		try{
			DbAccess db = new DbAccess();
			db.initConnection();
			Table_Data table = new Table_Data(db);
			boolean exists = false;
			exists=TableVerify.tableExists(db.getConnection(),tableName);
			if (exists) {
				data=table.getDistinctTransazioni(tableName);
				numberOfExamples=data.size();
				Table_Schema tschema = new Table_Schema(db,tableName);
				Attribute attribute;
				for(int i=0;i<tschema.getNumberOfAttributes();i++) {
					if(tschema.getColumn(i).isNumber()) {
						double maxTemp = (float) table.getAggregateColumnValue(tableName, tschema.getColumn(i), QUERY_TYPE.MAX);
						double minTemp = (float) table.getAggregateColumnValue(tableName, tschema.getColumn(i), QUERY_TYPE.MIN);
						attribute = new ContinuousAttribute(tschema.getColumn(i).getColumnName(),i,minTemp,maxTemp);
					}else {
						/*Set temporaneo in cui memorizzo i values
						 * privi di duplicati da passare al costruttore
						 * DiscreteAttribute
						 */
						Set<String> values = new TreeSet<String>();
						//Valori effettivamente contenuti in Data
						Set<Object> efValues = table.getDistinctColumnValues(tableName, tschema.getColumn(i));
						for(Object ob: efValues) {
							values.add((String)ob);
						}
						attribute =new DiscreteAttribute(tschema.getColumn(i).getColumnName(),i,(TreeSet<String>)values);
					}
					attributeSet.add(attribute);
				}
				db.closeConnection();
			}else throw new TableNotFoundException(tableName);
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
	 * Restituisce il valore della lista di Examples
	 * 'data' avendo in input una riga ed una colonna
	 * @param exampleIndex indiceche fa riferimento alla tupla di data
	 * da cui estrapolare l'attributo
	 * @param attributeIndex indice che fa riferimento all'attributo
	 * @return valore assunto in data dall'attributo
	 * in posizione attributeIndex,
	 * nella tupla in posizione exampleIndex
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
	@Override
	public String toString() throws NullPointerException{
		String transazioni = new String() ;
		int i=0;
		int j;
		Iterator<Attribute> it = this.getAttributeSchema().iterator();
		while(it.hasNext()){
			transazioni=(i<this.getNumberOfAttributes()-1)?
					(transazioni += it.next().getName() + ", ")
					:(transazioni += it.next().getName() + " " + "\n");
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
	 * @param index indice di riferimento alla tupla
	 * da cui prelevare l'attributo in Data
	 * @return la tupla desiderata
	 */
	public Tuple getItemSet(int index) {
		Tuple tuple=new Tuple(this.getNumberOfAttributes());
		int i=0;
		/*
		 * per ogni attributo presente nel set
		 * memorizzo in tuple l'attributo discreto
		 * ed il corrispondente valore discreto
		 * in altre parole l'Item discreto corrente;
		 * stessa cosa vale nel caso in cui
		 * l'attribute sia continuo.
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
	 * corrisponda o meno ad una tabella effettivamente presente
	 * nel database.
	 * @author Claudia Lorusso, Dileo Angela
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
}