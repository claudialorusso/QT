package data;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Estende la classe Attribute
 * e rappresenta un attributo discreto (categorico)
 * @author Dileo Angela, Lorusso Claudia
 */
public class DiscreteAttribute extends Attribute implements Iterable<String> {
	private static final long serialVersionUID=1L;
	/**
	 * Set di oggetti di tipo String,
	 * uno per ciascun valore del dominio discreto
	 */
	private Set<String> values;
	/**
	 * Invoca il costruttore della classe madre
	 * ed inizializza il membro values
	 * con il parametro in input
	 * @param name Nome simbolico dell'attributo
	 * @param index Identificativo numerico dell'attributo
	 * @param values TreeSet di oggetti di tipo String,
	 * uno per ciascun valore del dominio discreto
	 */
	public DiscreteAttribute(String name, int index, Set<String> values){
		super(name,index);
		this.values=values;
	}
	/**
	 * Restituisce la dimensione di values
	 * @return dimensione di values
	 */
	////////////////////////////////////////////////VISIBILITA'
	private int getNumberOfDistinctValues() {
		return values.size();
	}

	@Override
	public Iterator<String> iterator() {
		return new StringIterator();
	}

	//inner class
	class StringIterator implements Iterator<String>{
		private int count = -1;
		private String current = ((TreeSet<String>)values).first();
		@Override
		public boolean hasNext() {
			if(count<getNumberOfDistinctValues())
				return true;
			else return false;
		}
		@Override
		public String next() {
			count++;
			if (count==0)
				return current;
			else if (this.hasNext())
				return current=((TreeSet<String>)values).ceiling(current);
			else return null;
		}
		@Override
		public void remove() {}
	}

}