package data;
/**
 * Estende la classe Item
 * che rappresenta la coppia
 * <Attributo discreto-valore discreto>
 * per esempio Outlook="Sunny"
 * @author Claudia Lorusso, Angela Dileo
 */
public class DiscreteItem extends Item{
	/**
	 * ID di serializzazione
	 */
	private static final long serialVersionUID=1L;
	/**
	 * Costruttore della classe DiscreteItem,
	 * struttato invocando il costruttore
	 * della classe madre.
	 * @param attribute Attributo presente nell'Item corrente
	 * @param value Valore associato all'attribute
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
	//public
	double distance(Object a) {
		return (getValue().equals(((Item) a).getValue()))?0:1;
	}
}
