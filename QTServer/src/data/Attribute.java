/**
 * Package che gestisce l'acquisizione degli elementi presenti
 * in Data e modella i vari Items (formati da un attributo ed un valore)
 * presenti nelle tuple di Data.
 */
package data;
import java.io.Serializable;

/**
 * Modella l'entita' attributo.
 * @author Claudia Lorusso, Angela Dileo
 */
abstract class Attribute implements Serializable {
	
	/**
	 * ID di serializzazione.
	 */
	private static final long serialVersionUID=1L;
	
	/**
	 * Nome simbolico dell'attributo.
	 */
	private String name;
	
	/**
	 * Identificativo numerico dell'attributo.
	 */
	private int index;
	
	/**
	 * Inizializza i valori di {@link #name} ed {@link #index}.
	 * @param name Nome simbolico dell'attributo
	 * @param index Identificativo numerico dell'attributo
	 */
	Attribute(String name, int index) {
		this.name = name;
		this.index = index;
	}
	
	/**
	 * Restituisce il nome dell'attributo.
	 * @return nome dell'attributo
	 */
	String getName(){
		return name;
	}
	
	/**
	 * Restituisce l'identificativo numerico dell'attributo.
	 * @return identificativo numerico dell'Attribute
	 */
	int getIndex(){
		return index;
	}
	
	/**
	 * Override di toString di Object,
	 * restituisce il nome dell'attributo
	 */
	@Override
	public String toString(){
		return this.name;
	}
}