/**
 * Package che gestisce la creazione dei ClusterSets e dei
 * rispettivi Cluster in esso contenuti.
 * <p>
 * Comprende l'algoritmo per la scelta del miglior Cluster, ovvero quello
 * piu' popoloso.
 */
package mining;

import data.Data;
import data.Tuple;
import java.io.Serializable;
import java.util.*;

/**
 * Modella un cluster ovvero un set di {@link Tuple}.
 * @author Claudia Lorusso, Angela Dileo
 */
class Cluster implements Iterable<Integer>, Comparable<Cluster>, Serializable {
	
	/**
	 * ID di serializzazione.
	 */
	private static final long serialVersionUID=1L;
	
	/**
	 * {@link Tuple} corrispondente al centroide,
	 * cioe' il punto
	 * rappresentativo del cluster.
	 */
	private Tuple centroid;
	
	/**
	 * Set di interi contenente l'id
	 * delle tuple clusterizzate
	 * presenti nel {@link Cluster}.
	 */
	private Set <Integer> clusteredData;
	
	/**
	 * Costruttore della
	 * classe {@link Cluster},
	 * assegna un valore al centroide ed
	 * inizializza {@link #clusteredData}.
	 * @param centroid tupla corrispondente al centroide del cluster
	 */
	Cluster(Tuple centroid) {
		this.centroid = centroid;
		clusteredData =  new HashSet <Integer>();
	}
	
	/**
	 * Acquisisce il {@link #centroid}e
	 * @return tupla centroide
	 */
	private Tuple getCentroid(){
		return centroid;
	}
	
	/**
	 * Aggiunge l'<tt>id</tt> di una nuova
	 * tupla clusterizzata in {@link Data}.<br>
	 * Restituisce true se
	 * la tupla ha cambiato il suo {@link Cluster}
	 * di appartenenza.
	 * @param id posizone della tupla da aggiungere a
	 * {@link #clusteredData}
	 * @return true se la tupla ha modificato
	 * il suo cluster di appartenenza, false altrimenti
	 */
	boolean addData(int id){
		return clusteredData.add(id);
	}
	
	/**
	 * Restituisce la cardinalita'
	 * del {@link Cluster}.
	 * @return numero totale di tuple contenute nel Cluster
	 */
	int getSize(){
		return clusteredData.size();
	}
	
	/**
	 * Override del toString di Object.<br>
	 * Salva in una stringa la tupla {@link #centroid}e
	 * del {@link Cluster}.
	 * @return stringa contenente la tupla {@link #centroid}e
	 * del {@link Cluster}
	 */
	@Override
	public String toString(){
		String str = "Centroid=(";
		for (int i=0; i < centroid.getLength(); i++)
			str += centroid.get(i) + " ";
		str += ")";
		return str;
	}
	
	/**
	 * Memorizza in una stringa
	 * tutte le informazioni sul {@link Cluster}:<br><ul>
	 * 	<li> il suo centroide
	 * 	<li> le tuple contenute nel cluster
	 *  <li> la distanza tra la tupla ed il centroide
	 *  <li> la distanza media</ul>
	 * @param data oggetto della classe {@link Data}
	 * @return str stringa contenente le informazioni sul {@link Cluster}
	 */
	String toString(Data data) {
		String str = toString().concat("\nExamples:\n");
		Iterator<Integer> it = clusteredData.iterator();
		while (it.hasNext()){
			int i = it.next();
			str += "[";
			for (int j=0; j < data.getNumberOfAttributes(); j++)
				str += data.getAttributeValue(i, j) + " ";
			str += "] dist=" + getCentroid().getDistance(data.getItemSet(i)) + "\n";
		}
		str += "AvgDistance=" + getCentroid().avgDistance(data,this.clusteredData) + "\n";
		return str;
	}
	
	/**
	 * Restituisce un iteratore su {@link #clusteredData}.
	 */
	@Override
	public Iterator<Integer> iterator() {
		return clusteredData.iterator();
	}
	
	/**
	 * Override del metodo compareTo dell'interfaccia {@link Comparable}.<br>
	 * Il comparatore confronta due {@link Cluster}s in base alla
	 * popolosità restituendo -1 o 1.
	 * @param c2 {@link Cluster} da confrontare con quello corrente
	 * @return -1 se la dimensione del primo cluster e' superiore a quella del secondo,
	 * 1 altrimenti
	 */
	@Override 
	public int compareTo(Cluster c2) {
		if (this.getSize() > c2.getSize())
			return -1;
		else
			return 1;
	}
}