package data;
public class ContinuousItem extends Item{
	private static final long serialVersionUID=1L;
	
	ContinuousItem(Attribute attribute, Double value){
		super(attribute,value);
	}
//VERIFICA SE E' CORRETTO O MENO
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