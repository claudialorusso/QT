package data;

import java.io.Serializable;

/**
 * Modella un generico Item
 * (coppia attributo-valore,
 * ad esempio Outlook="Sunny")
 * @author Dileo Angela, Lorusso Claudia
 */
public abstract class Item implements Serializable {
	private static final long serialVersionUID=1L;
	/**
	 * Attributo coinvolto nell'Item
	 */
	private Attribute attribute; 
	/**
	 * Valore assegnato all'attributo
	 */
	private Object value;
	/**
	 * Inizializza i valori dei membri attributi
	 * @param attribute Attributo coinvolto nell'Item
	 * @param value Valore assegnato all'attributo
	 */
	public Item(Attribute attribute, Object value){
		this.attribute = attribute;
		this.value = value;
	}
	/**
	 * Restituisce attribute
	 * @return l'attributo coinvolto nell'Item
	 */
	public Attribute getAttribute(){
		return this.attribute;
	}
	/**
	 * Restituisce value
	 * @return il valore assegnato all'attributo
	 */
	public Object getValue(){
		return this.value;
	}
	/**
	 * Override del metodo toString di Object
	 * Restituisce value
	 */
	@Override
	public String toString(){
			return this.value.toString();
	}
	/**
	 * Metodo astratto che sara'
	 * utilizzato per modellare,
	 * con diverse implementazioni,
	 * la distanza per l'item astratto
	 * e per quello continuo
	 * @param a tupla in input ////////////////////////////////////////
	 * @return
	 */
	abstract double distance(Object a);
}