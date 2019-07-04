package mining;

import data.Data;
import java.io.Serializable;
import java.util.*;

/**
 * Rappresenta un Set di Cluster
 * determinati per mezzo dell'algoritmo
 * Quality Threshold.
 * @author Claudia Lorusso, Angela Dileo
 */
public class ClusterSet implements Iterable<Cluster>, Serializable{
	/**
	 * ID di serializzazione
	 */
	private static final long serialVersionUID=1L;
	/**
	 * Set di Cluster
	 */
	private Set<Cluster> C = null;
	/**
	 * Costruttore della classe ClusterSet.
	 */
	ClusterSet() {
		C= new TreeSet<Cluster>();
	}
	/**
	 * Costruttore della classe ClusterSet
	 * ordinato tramite l'interfaccia Comparator. 
	 * @param test valore booleano che permette
	 * di selezionare questo costruttore
	 * piuttosto che l'altro.
	 */
	ClusterSet(boolean test) {
		C=new TreeSet<Cluster>(
				new Comparator<Cluster>() {
					/**
					 * Classe anonima che permette di implementare
					 * l'interfaccia Comparator, in modo da poter riordinare
					 * il ClusterSet tramite il metodo
					 * compare.</p>
					 * L'ultimo Cluster sara' quello piu' popoloso.
					 */
					@Override
					public int compare(Cluster c1, Cluster c2) {
						return c1.compareTo(c2);
					}
				});
	}
	/**
	 * Permette di selezionare l'ultimo Cluster
	 * del ClusterSet, corrispondente a quello
	 * piu' popoloso.
	 * @return ultimo Cluster nel Set, quello piu' popoloso.
	 */
	Cluster last() {
		return ((TreeSet<Cluster>) C).last();
	}
	/**
	 * Aggiunge un Cluster
	 * al ClusterSet
	 * @param c cluster da aggiungere al set
	 */
	
	void add(Cluster c) {
		C.add(c);
	}
	/**
	 * Override del metodo toString
	 * di Object.
	 * <p>
	 * Restituisce una stringa
	 * contenente i vari centroidi dei Cluster
	 * contenuti nel ClusterSet.
	 */
	@Override
	public String toString() {
		String str="";
		Iterator<Cluster> cl = C.iterator();
		while(cl.hasNext()) {
			str+=cl.next().toString()+"\n";
		}
		return str;
	}
	/**
	 * Restituisce una stringa
	 * che descriva lo stato
	 * di ciascun Cluster nel ClusterSet.
	 * @param data oggetto di tipo Data
	 * @return str stringa che descrive lo stato
	 * dei Cluster nel ClusterSet. 
	 */
	public String toString(Data data) {
		int i = 0;
		String str="";
		Iterator<Cluster> c = C.iterator();
		while(c.hasNext()) {
			str+=i+":"+ c.next().toString(data)+"\n";
			i++;
		}
		return str;
	}
	/**
	 * Override del metodo iterator.</p>
	 * Itera sul ClusterSet
	 */
	@Override
	public Iterator<Cluster> iterator() {
		return C.iterator();
	}
}