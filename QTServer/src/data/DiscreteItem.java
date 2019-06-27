package data;
/**
 * @author Dileo Angela, Lorusso Claudia
 *Estende la classe Item
 *che rappresenta la coppia
 *<Attributo discreto-valore discreto>
 *per esempio Outlook="Sunny"
 */
public class DiscreteItem extends Item{
	private static final long serialVersionUID=1L;
	/**
	 * costruttore della classe DiscreteItem,
	 * struttato invocando il costruttore
	 * della classe madre
	 * @param attribute Item
	 * @param value
	 */
	DiscreteItem(DiscreteAttribute attribute, String value) {
		super(attribute, value);
	}
	/**
	 * Implementazione del metodo
	 * presente nella classe astratta Item.
	 * <p>
	 * Permette di modellare la distanza per un
	 * Item discreto.
	 */
	@Override
	public double distance(Object a) {
		return (getValue().equals(((Item) a).getValue()))?0:1;
	}
}
