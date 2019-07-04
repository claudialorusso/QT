package data;

import java.io.Serializable;

/**
 * Modella un generico Item
 * (coppia attributo-valore,
 * ad esempio Outlook="Sunny")
 * @author Claudia Lorusso, Angela Dileo
 */
abstract class Item implements Serializable {
	/**
	 * ID di serializzazione
	 */
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
	Item(Attribute attribute, Object value){
		this.attribute = attribute;
		this.value = value;
	}
	/**
	 * Restituisce l'attributo contenuto nell'Item
	 * @return l'attributo conteuto nell'Item
	 */
	Attribute getAttribute(){
		return this.attribute;
	}
	/**
	 * Restituisce il valore associato all'attributo
	 * @return il valore associato all'attributo
	 */
	Object getValue(){
		return this.value;
	}
	/**
	 * Override del metodo toString di Object.</p>
	 * Restituisce una stringa contenente il value.
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
	 * @param a oggetto contenente il valore
	 * di cui bisgona calcolare la distanza
	 * @return distanza tra due Item
	 */
	abstract double distance(Object a);
}