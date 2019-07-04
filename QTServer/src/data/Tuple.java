package data;
import java.io.Serializable;
import java.util.*;
/**
 * Rappresenta una tupla come sequenza di
 * coppie attributo-valore
 * @author Claudia Lorusso, Angela Dileo
 */
public class Tuple implements Serializable{
	/**
	 * ID di serializzazione
	 */
	private static final long serialVersionUID=1L;
	/**
	 * Vettore di Item che compongono una tupla
	 */
	private Item[] tuple;
	/**
	 * Costruisce l'oggetto riferito da tuple
	 * @param size numero di item che costituira' la tupla
	 */
	Tuple(int size){
		this.tuple=new Item[size];
	}
	/**
	 * Restituisce la lunghezza della tupla
	 * @return la lunghezza della tupla
	 */
	public int getLength() {
		return tuple.length;
	}
	/**
	 * Restituisce l'item in posizione i-esima
	 * @param i posizione dell'item da estrapolare
	 * dalla tupla
	 * @return l'item in posizione i-esima
	 */
	public Item get(int i) {
		return tuple[i];
	}
	/**
	 * Memorizza l'item c
	 * nella posizione i-esima di tuple
	 * @param c item da memorizzare in tuple
	 * @param i posizione di tuple in cui memorizzare c
	 */
	void add(Item c, int i) {
		tuple[i]=c;
	}
	/**
	 * Determina la distanza tra
	 * la tupla riferita da obj
	 * e la tupla corrente.
	 * <p>
	 * La distanza corrisponde alla somma
	 * delle distanze tra gli Item in posizioni uguali
	 * nelle due tuple.
	 * @param obj tupla da cui determinare la distanza
	 * @return distanza tra la tupla riferita da obj e quella corrente
	 */
	public double getDistance (Tuple obj) {
		double distanza=0.0;
		int i=0;
		for(i=0;i<getLength();i++) {
			distanza+= get(i).distance(obj.get(i));
		}
		return distanza;
	}
	/**
	 * Restituisce la media delle distanze
	 * tra le tuple contenute nel Cluster
	 * nelle posizioni specificate dal Set clusteredData
	 * @param data oggetto di tipo Data da cui
	 * estrapolare i vari Items
	 * @param clusteredData l'insieme clusterizzato
	 * @return distanza media tra le tuple nel cluster
	 */
	public double avgDistance(Data data, Set<Integer> clusteredData) {
		double p=0.0,sumD=0.0;
		Iterator<Integer> it = clusteredData.iterator();
		while(it.hasNext()){
			int i = it.next();
			double d=getDistance(data.getItemSet(i));
			sumD+=d;
		}
		p=sumD/clusteredData.size();
		return p;
	}
}