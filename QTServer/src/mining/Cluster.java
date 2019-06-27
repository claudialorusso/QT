package mining;

import data.Data;
import data.Tuple;

import java.io.Serializable;
import java.util.*;


/**
 * Modella un cluster
 * @author Dileo Angela, Lorusso Claudia
 */
public class Cluster implements Iterable<Integer>, Comparable<Cluster>, Serializable {
	private static final long serialVersionUID=1L;
	/**
	 * Centroide, cioe' il punto
	 * rappresentativo del cluster
	 */
	private Tuple centroid;
	/**
	 * L'insieme clusterizzato
	 */
	Set <Integer> clusteredData;
	
	/**
	 * Costruttore di default della
	 * classe Cluster
	 */
	public Cluster(){
		
	}
	/**
	 * Secondo costruttore della
	 * classe Cluster,
	 * assegna un valore al centroide,
	 * crea un nuovo insieme di cluster
	 * @param centroid centroide del cluster
	 */
	public Cluster(Tuple centroid){
		this.centroid=centroid;
		clusteredData =  new HashSet <Integer>();
	}
	/**
	 * Acquisisce il valore del centroide
	 * @return il valore del centroide
	 */
	public Tuple getCentroid(){
		return centroid;
	}
	
	/**
	 * restituisce true se 
	 * la tupla ha cambiato il suo cluster
	 * @param id elemento da aggiungere all'arraySet
	 * @return vero se la tupla ha modificato
	 * il suo cluster
	 */
	boolean addData(int id){
		return clusteredData.add(id);
	}
	
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
		//boolean test = false;
		clusteredData.remove(id);
		//test =true;
		//return test;
	}	
	/**
	 * Restituisce la cardinalita'
	 * dell'insieme clusterizzato
	 * @return grandezza dellinsieme
	 * gia' clusterizzato
	 */
	int  getSize(){
		return clusteredData.size();
	}
	/**
	 * Override del toString di Object.
	 * Mostra esplicitamente la posizione
	 * del centroide corrente.
	 * @return stringa contenente la posizione
	 * del centroide corrente
	 * (es. Centroid=(7.5) )
	 */
	@Override
	public String toString(){
		String str="Centroid=(";
		for(int i=0;i<centroid.getLength();i++)
			str+=centroid.get(i);
		str+=")";
		return str;
	}
	/**
	 * Override del toString di Object.
	 * <p>
	 * 
	 * @param data
	 * @return
	 */
	
	public String toString(Data data){
		String str="Centroid=(";
		/*
		 * mostra la posizione del centroide corrente
		 */
		for(int i=0;i<centroid.getLength();i++)
			str+=centroid.get(i)+ " ";
		str+=")\nExamples:\n";
		
		//definisco l'iteratore su clusteredData
		Iterator<Integer> it=clusteredData.iterator();
		
		while(it.hasNext()){
			int i=it.next();
			str+="[";
			
			/*
			 * Per ogni elemento clusterizzato,
			 * per ogni elemento in data,
			 * prendo la posizione dell'elemento
			 * clusterizzato, presente nell'array,
			 * salvandola nella stringa.
			 */
			for(int j=0;j<data.getNumberOfAttributes();j++)
				str+=data.getAttributeValue(i, j)+" ";
			
			/* vado in data ed ottengo il valore
			 * dell'attributo desiderato.
			 * Ne calcolo in seguito la distanza
			 * tra questo ed il centroide,
			 * salvandola nella stringa.
			 */
			str+="] dist="+getCentroid().getDistance(data.getItemSet(i))+"\n";
		}
		
		//mostro la distanza media con il centroide
		str+="AvgDistance="+getCentroid().avgDistance(data,this.clusteredData)+"\n";
		return str;
	}

	@Override
	public Iterator<Integer> iterator() {
		return clusteredData.iterator();
	}
	
	@Override
	public int compareTo(Cluster c2) {
		if(this.getSize() > c2.getSize())
			return -1;
		else
			return 1;
	}
}
