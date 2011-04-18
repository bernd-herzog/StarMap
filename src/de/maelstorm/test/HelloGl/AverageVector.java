package de.maelstorm.test.HelloGl;

public class AverageVector {

	private MVector[] values;

	public AverageVector(int count) {
		values = new MVector[count];

	}

	public MVector getAvg() {
		MVector v = new MVector();
		for (int i = 0; i < values.length; i++) {
			
			v.add(values[i]);
			
		}
		v.div((float)values.length);
		return v;
	}
	
	void add(MVector v){
		for (int i = 0; i < values.length-1; i++) {
			values[i]=values[i+1];
		}
		values[values.length-1]=v;
	}
}
