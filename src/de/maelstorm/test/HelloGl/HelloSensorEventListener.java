package de.maelstorm.test.HelloGl;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public class HelloSensorEventListener implements SensorEventListener {

	AverageVector aM = new AverageVector(25);
	AverageVector aO = new AverageVector(25);

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			aO.add(new MVector(event.values));
		} else {
			aM.add(new MVector(event.values));
		}
	}
	
	public MVector getOrientation(){
		MVector ret = aO.getAvg();
		ret.normalize();
		return ret;
	}
	
	public MVector getMagnetic(){
		MVector ret = aM.getAvg();
		ret.normalize();
		return ret;
	}

}
