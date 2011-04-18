package de.maelstorm.test.HelloGl;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class HelloGLSurfaceView extends GLSurfaceView {
	HelloRenderer mRenderer;
	HelloSensorEventListener _listener = new HelloSensorEventListener();
	SensorManager _sM;
	float hLen = 0.0f;

	public HelloGLSurfaceView(Context context) {
		super(context);
		mRenderer = new HelloRenderer(context, _listener);
		setRenderer(mRenderer);

		_sM = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_MOVE:
			if (event.getPointerCount() > 1) {
				int hLen = event.getHistorySize();
				if (hLen > 0) {
					float rx = event.getX(0) - event.getX(1);
					float ry = event.getY(0) - event.getY(1);
					float pLen = (float) Math.sqrt(rx * rx + ry * ry);

					float diff = pLen - this.hLen;
					mRenderer.addFov(-diff);

					this.hLen = pLen;
				}

			}
			break;
		case MotionEvent.ACTION_POINTER_1_DOWN:
		case MotionEvent.ACTION_POINTER_2_DOWN:
			if (event.getPointerCount() > 1) {
				{
					float rx = event.getX(0) - event.getX(1);
					float ry = event.getY(0) - event.getY(1);
					this.hLen = (float) Math.sqrt(rx * rx + ry * ry);
				}
			}
			break;
		}

		return true;
	}

	@Override
	public void onResume() {
		Sensor s = _sM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		_sM.registerListener(_listener, s, SensorManager.SENSOR_DELAY_GAME);

		s = _sM.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		_sM.registerListener(_listener, s, SensorManager.SENSOR_DELAY_GAME);
		super.onResume();
	}

	@Override
	public void onPause() {
		_sM.unregisterListener(_listener);
		super.onPause();
	}
}
