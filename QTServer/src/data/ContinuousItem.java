package data;
/**
 * Estende la classe Item e modella una coppia (attributo continuo - valore numerico)
 * @author Claudia Lorusso, Angela Dileo
 */
class ContinuousItem extends Item{
	/**
	 * ID di serializzazione
	 */
	private static final long serialVersionUID=1L;
	/**
	 * Invoca il costruttore della classe madre Item
	 * @param attribute Attributo coinvolto nell'item
	 * @param value Valore assegnato all'attributo
	 */
	ContinuousItem(Attribute attribute, Double value){
		super(attribute,value);
	}
	/**
	 * Determina la distanza in valore assoluto
	 * tra il valore scalato memorizzato nell'item corrente
	 * e quello scalato associato al paramentro 'a'
	 * @param a Parametro che sarà confrontato con l'item corrente
	 */
	@Override
	double distance(Object a) {
		ContinuousAttribute c = (ContinuousAttribute)this.getAttribute();
		Double v1 = (double) this.getValue();	
		Double v2 = (Double)(((Item) a).getValue());
		Double scaledValue = c.getScaledValue(v1)-c.getScaledValue(v2);
		Double distance = Math.abs(scaledValue);
		return distance;
	}
}