package data;
import java.io.Serializable;

/**
 * Modella l'entita' attributo
 * @author Lorusso Claudia, Dileo Angela
 */
public abstract class Attribute implements Serializable {
	private static final long serialVersionUID=1L;
	/**
	 * Nome simbolico dell'attributo
	 */
	private String name;
	/**
	 * Identificativo numerico dell'attributo
	 */
	private int index;
	/**
	 * Inizializza i valori di name ed index
	 * @param name Nome simbolico dell'attributo
	 * @param index Identificativo numerico dell'attributo
	 */
	Attribute(String name, int index) {
		this.name = name;
		this.index = index;
	}
	/**
	 * Restituisce il nome dell'attributo
	 * @return nome dell'attributo
	 */
	public String getName(){
		return name;
	}
	/**
	 * Restituisce l'identificativo numerico dell'attributo
	 * @return identificativo numerico dell'Attribute
	 */
	public int getIndex(){
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
