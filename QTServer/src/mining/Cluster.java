package mining;

import data.Data;
import data.Tuple;
import java.io.Serializable;
import java.util.*;

/**
 * Modella un cluster
 * @author Lorusso Claudia, Dileo Angela
 */
//public
class Cluster implements Iterable<Integer>, Comparable<Cluster>, Serializable {
	/**
	 * ID di serializzazione
	 */
	private static final long serialVersionUID=1L;
	/**
	 * Tupla corrispondente al Centroide,
	 * cioe' il punto
	 * rappresentativo del cluster
	 */
	private Tuple centroid;
	/**
	 * Set di interi contenente l'id
	 * delle tuple clusterizzate
	 * presenti nel Cluster
	 */
	private Set <Integer> clusteredData;
	/**
	 * Costruttore della
	 * classe Cluster,
	 * assegna un valore al centroide,
	 * inizializza clusteredData
	 * @param centroid centroide del cluster
	 */
	Cluster(Tuple centroid){
		this.centroid=centroid;
		clusteredData =  new HashSet <Integer>();
	}
	/**
	 * Acquisisce la tupla Centroide
	 * @return tupla centroide
	 */
	private Tuple getCentroid(){
		return centroid;
	}
	/**
	 * Aggiunge l'id di una nuova
	 * tupla clusterizzata in Data.
	 * Restituisce true se
	 * la tupla ha cambiato il suo cluster
	 * di appartenenza.
	 * @param id posizone tupla da aggiungere a
	 * ClusteredData
	 * @return true se la tupla ha modificato
	 * il suo cluster di appartenenza, false altrimenti
	 */
	boolean addData(int id){
		return clusteredData.add(id);
	}
	/**
	 * Restituisce la cardinalita'
	 * del Cluster
	 * @return numero totale di tuple contenute nel Cluster
	 */
	int  getSize(){
		return clusteredData.size();
	}
	/**
	 * Override del toString di Object.
	 * Salva in una stringa la tupla Centroide
	 * del Cluster.
	 * @return stringa contenente la tupla Centroide
	 * del cluster
	 */
	@Override
	public String toString(){
		String str="Centroid=(";
		for(int i=0;i<centroid.getLength();i++)
			str+=centroid.get(i)+" ";
		str+=")";
		return str;
	}
	/**
	 * Memorizza in una stringa
	 * tutte le informazioni sul Cluster:</p>
	 * -	il suo centroide</p>
	 * -	le tuple contenute nel cluster</p>
	 * -	la distanza tra la tupla ed il centroide</p>
	 * -	la distanza media
	 * @param data oggetto della classe Data
	 * @return str stringa contenente le informazioni sul Cluster
	 */
	public String toString(Data data){
		String str = toString().concat("\nExamples:\n");
		Iterator<Integer> it=clusteredData.iterator();
		while(it.hasNext()){
			int i=it.next();
			str+="[";
			for(int j=0;j<data.getNumberOfAttributes();j++)
				str+=data.getAttributeValue(i, j)+" ";
			str+="] dist="+getCentroid().getDistance(data.getItemSet(i))+"\n";
		}
		str+="AvgDistance="+getCentroid().avgDistance(data,this.clusteredData)+"\n";
		return str;
	}
	/**
	 * Restituisce un iteratore su clusteredData.
	 */
	@Override
	public Iterator<Integer> iterator() {
		return clusteredData.iterator();
	}
	/**
	 * Override del metodo compareTo dell'interfaccia Comparable.
	 * Il comparatore confronta due Cluster in base alla
	 * popolosità restituendo -1 o 1
	 */
	@Override 
	public int compareTo(Cluster c2) {
		if(this.getSize() > c2.getSize())
			return -1;
		else
			return 1;
	}
	/////////////////////////////////////////////////CANCELLARE?
	/**
	 * Verifica se una tupla e' clusterizzata
	 * nell'array corrente:
	 * <p>
	 * se il valore id e' pari a true
	 * vuol dire che la tupla e' stata clusterizzata
	 * @param id
	 * @return
	 */
	private boolean contain(int id){
		return clusteredData.contains(id);
	}
	/**
	 * Rimuove la tupla che ha cambiato
	 * il cluster
	 * @param id identificativo della tupla da rimuovere
	 */
	private void removeTuple(int id){
		clusteredData.remove(id);
	}
	/////////////////////////////////////////////////////////////////
}