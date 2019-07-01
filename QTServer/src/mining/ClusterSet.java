package mining;

import data.Data;

import java.io.Serializable;
import java.util.*;

/**
 * Rappresenta un insieme di cluster
 * determinati da QT
 * @author Lorusso Claudia, Dileo Angela
 */
public class ClusterSet implements Iterable<Cluster>, Serializable{
	private static final long serialVersionUID=1L;
	/**
	 * Set di Cluster
	 */
	private Set<Cluster> C = null;
	
	public ClusterSet(boolean test) {
		C=new TreeSet<Cluster>(new Comparator<Cluster>() {
			//anonymous class
			@Override
			public int compare(Cluster c1, Cluster c2) {
				/*if(c1.getSize()>c2.getSize())
					return -1;
				else if(c1.getSize()<=c2.getSize())
					return 1;
				else */return c1.compareTo(c2);
			}
		});
	}
	
	Cluster last() {
		return ((TreeSet<Cluster>) C).last();
	}
	//////////////////////////forse non serve
	Cluster first() {
		return ((TreeSet<Cluster>) C).first();
	}
	ClusterSet() {
		C= new TreeSet<Cluster>();
	}
	/**
	 * Aggiunge un cluster
	 * al set di cluster
	 * @param c cluster da aggiungere al set
	 */
	void add(Cluster c) {
		C.add(c);
	}
	
	/**
	 * @return numero di elementi che costituiscono il set di cluster
	 */
	int getSize() {
		return C.size();
	}
	/**
	 * Override del metodo toString
	 * di Object.
	 * <p>
	 * Restituisce una stringa
	 * fatta da ciascun centroide
	 * dell'insieme dei cluster
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
	 * Override del metodo toString di Object.
	 * <p>
	 * Restituisce una stringa
	 * che descriva lo stato
	 * di ciascun cluster in C.
	 * @param data
	 * @return str 
	 */
	//@Override
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
	 * Override del metodo iterator.
	 * Itera sul set di Cluster.
	 */
	@Override
	public Iterator<Cluster> iterator() {
		return C.iterator();
	}
}