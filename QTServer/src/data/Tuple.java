package data;
import java.io.Serializable;
import java.util.*;

/**
 * Rappresenta una tupla come sequenza di
 * coppie attributo-valore
 * @author Dileo Angela, Lorusso Claudia
 */
public class Tuple implements Serializable{
	private static final long serialVersionUID=1L;
	/**
	 * Vettore di Item
	 */
	private Item[] tuple;
	/**
	 * Costruisce l'oggetto riferito da tuple
	 * @param size numero di item che costituira' la tupla
	 */
	public Tuple(int size){
		this.tuple=new Item[size];
	}
	/**
	 * Restituisce la lunghezza delle tuple
	 * @return la lunghezza delle tuple
	 */
	public int getLength() {
		return tuple.length;
	}
	/**
	 * Restituisce l'item in posizione i
	 * @param i posizione
	 * @return l'item in posizione i
	 */
	public Item get(int i) {
		return tuple[i];
	}
	/**
	 * memorizza l'item c
	 * nella posizione i-esima di tuple
	 * @param c item da memorizzare in tuple
	 * @param i posizione di tuple in cui memorizzare c
	 */
	void add(Item c, int i) {
		tuple[i]=c;
		//test=true;
		//return test;
	}
	/**
	 * Determina la distanza tra
	 * la tupla riferita da obj
	 * e la tupla corrente (riferita da this).
	 * <p>
	 * La distanza e' ottenuta come la somma
	 * delle distanze tra gli Item in posizioni uguali
	 * nelle due tuple.
	 * @param obj tupla da cui determinare la distanza
	 * @return distanza tra la tupla riferita da obj e quella corrente
	 */
	//////////////////////////////////////////////////////////////////////////
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
	 * tra la tupla corrente e quelle ottenibili
	 * dalle righe della matrice in data
	 * aventi l'indice in ClusteredData
	 * @param data matrice nXm di tipo Object
	 * dove ogni riga modella una tupla
	 * @param clusteredData l'insieme clusterizzato
	 * @return distanza media 
	 */
	public double avgDistance(Data data, Set<Integer> clusteredData) {
		double p=0.0,sumD=0.0;
		/*for(int i=0;i<clusteredData.length;i++) {
			double d=getDistance(data.getItemSet(clusteredData[i]));
			sumD+=d;
		}
		p=sumD/clusteredData.length;*/
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
