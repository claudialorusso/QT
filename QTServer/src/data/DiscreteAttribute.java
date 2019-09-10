package data;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Estende la classe Attribute
 * e rappresenta un attributo discreto (categorico)
 * @author Claudia Lorusso, Angela Dileo
 */
class DiscreteAttribute extends Attribute implements Iterable<String> {
	
	/**
	 * ID di serializzazione.
	 */
	private static final long serialVersionUID=1L;
	
	/**
	 * Set di oggetti di tipo String,
	 * uno per ciascun valore del dominio discreto.
	 */
	private Set<String> values;
	
	/**
	 * Invoca il costruttore della classe madre
	 * ed inizializza il membro {@link #values}
	 * con il parametro <tt>values</tt> in input.
	 * @param name Nome simbolico dell'attributo
	 * @param index Identificativo numerico dell'attributo
	 * @param values TreeSet di oggetti di tipo String,
	 * uno per ciascun valore del dominio discreto
	 */
	DiscreteAttribute(String name, int index, Set<String> values){
		super(name,index);
		this.values=values;
	}
	
	/**
	 * Restituisce la dimensione di {@link #values}.
	 * @return dimensione di values
	 */
	private int getNumberOfDistinctValues() {
		return values.size();
	}
	
	/**
	 * Restituisce un iteratore per la classe {@link DiscreteAttribute}.
	 */
	@Override
	public Iterator<String> iterator() {
		return new StringIterator();
	}
	
	/**
	 * Inner Class di {@link DiscreteAttribute}.
	 * <br>
	 * Implementazione dell'iteratore di DiscreteAttribute.
	 * <br>
	 * (Abbiamo deciso di metterci alla prova
	 * e di implementare i metodi dell'interfaccia corrispondente
	 * all'iteratore di stringhe
	 * in modo che facessero esattamente lo stesso lavoro di quelli
	 * gia' implementati di default).
	 * @author Claudia Lorusso, Dileo Angela
	 */
	private class StringIterator implements Iterator<String>{
		
		/**
		 * Conta gli elementi nel {@link TreeSet}.
		 */
		private int count = -1;
		
		/**
		 * Valore corrente.
		 */
		private String current = ((TreeSet<String>)values).first();
		
		/**
		 * Verifica se c'è un elemento successivo a quello corrente.
		 */
		@Override
		public boolean hasNext() {
			if(count<getNumberOfDistinctValues())
				return true;
			else return false;
		}
		
		/**
		 * Estrapola l'elemento successivo.
		 */
		@Override
		public String next() {
			count++;
			if (count==0)
				return current;
			else if (this.hasNext())
				return current=((TreeSet<String>)values).ceiling(current);
			else return null;
		}
	}
}