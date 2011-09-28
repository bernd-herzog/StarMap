package de.maelstorm.test.HelloGl;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.hardware.SensorManager;
import android.opengl.GLU;
import android.opengl.GLSurfaceView.Renderer;

public class HelloRenderer implements Renderer {
	private HelloSensorEventListener listener;

	StarContainer sC;
	float fov = 60.0f;

	static final int matrix_size = 16;
	float[] R = new float[matrix_size];
	// float[] I = new float[matrix_size];

	int width = 1;
	int height = 1;

	public HelloRenderer(Context context, HelloSensorEventListener listener) {
		this.listener = listener;
		this.sC = new StarContainer(context);
	}

	public void onDrawFrame(GL10 gl) {
		SensorManager.getRotationMatrix(this.R, null, this.listener
				.getOrientation().getValues(), this.listener.getMagnetic()
				.getValues());

		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU.gluPerspective(gl, fov, (float) this.width / this.height, 0.1f,
				80.0f);

		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glLoadMatrixf(this.R, 0);

		this.sC.render(gl);
	}


	public void onSurfaceChanged(GL10 gl, int width, int height) {
		this.width = width;
		this.height = height;
		gl.glViewport(0, 0, width, height);

		gl.glEnable(GL10.GL_POINT_SMOOTH);
		gl.glHint(GL10.GL_POINT_SMOOTH_HINT, GL10.GL_NICEST);
	}

	public void addFov(float diff) {
		this.fov += diff * this.fov / 250.0f;

		if (this.fov < 1.01f)
			this.fov = 1.01f;
		if (this.fov > 170.0f)
			this.fov = 170.0f;
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
	}
}
