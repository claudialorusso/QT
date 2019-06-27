package database;

import java.util.ArrayList;
import java.util.List;
/**
 * Modella una transazione letta dalla base di dati.
 */
public class Example implements Comparable<Example>{
	/**
	 * Lista di oggetti(Item) contenuti nella tupla example/////////////////
	 */
	private List<Object> example=new ArrayList<Object>();
	/**
	 * Aggiunge un item nella tupla
	 * @param o item da aggiungere nella tupla
	 */
	public void add(Object o){
		example.add(o);
	}
	/**
	 * Acquisisce l'item in posizione i-esima
	 * nella tupla
	 * @param i posizione da cui reperire l'item
	 * nella tupla
	 * @return i-esimo item nella tupla
	 */
	public Object get(int i){
		return example.get(i);
	}
	/**
	 * Confronta due tuple.
	 * Restituisce un intero negativo, zero
	 * oppure un intero positivo a seconda che l'item sia
	 * minore, uguale o maggiore dell'item con cui lo si sta
	 * confrontando.
	 */
	public int compareTo(Example ex) {
		int i=0;
		for(Object o:ex.example){
			if(!o.equals(this.example.get(i)))
				return ((Comparable)o).compareTo(example.get(i));
			i++;
		}
		return 0;
	}
	/**
	 * Riscrittura del toString.
	 * Scrive in un'unica stringa la tupla
	 * considerata.
	 */
	public String toString(){
		String str="";
		for(Object o:example)
			str+=o.toString()+ " ";
		return str;
	}
}