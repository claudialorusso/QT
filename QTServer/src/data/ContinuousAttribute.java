package data;
/**
 * Estende la classe {@link Attribute}
 * e modella un attributo continuo (numerico).
 * @author Claudia Lorusso, Angela Dileo
 */
class ContinuousAttribute extends Attribute {
	
	/**
	 * Id di serializzazione.
	 */
	private static final long serialVersionUID=1L;
	
	/**
	 * Estremo massimo di valori
	 * che l'attributo puo' assumere.
	 */
	private double max;
	
	/**
	 * Estremo minimo di valori
	 * che l'attributo puo' assumere.
	 */
	private double min;
	
	/**
	 * Inovoca il costruttore della classe madre {@link Attribute}
	 * ed inizializza i membri aggiunti per estensione.
	 * @param name Nome simbolico dell'attributo
	 * @param index Identificativo numerico dell'attributo
	 * @param min Estremo minimo di valori
	 * che l'attributo puo' assumere
	 * @param max Estremo massimo di valori
	 * che l'attributo puo' assumere
	 */
	ContinuousAttribute(String name, int  index, double min, double max){
		super(name,index);
		this.max=max;
		this.min=min;
	}
	
	/**
	 * Calcola e restituisce il valore normalizzato
	 * del parametro passato in input.<br>
	 * La normalizzazione ha come codominio
	 * l'intervallo [0,1].
	 * @param v Valore dell'attributo da normalizzare
	 * @return Valore normalizzato
	 */
	double getScaledValue(double v) {
		return (v-min)/(max-min);
	}
}