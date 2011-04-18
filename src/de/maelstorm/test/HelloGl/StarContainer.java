package de.maelstorm.test.HelloGl;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.content.res.AssetManager;

public class StarContainer {
	static final int parts = 2;

	Vector<MVector>[] stars;
	FloatBuffer[] mFVertexBuffer = new FloatBuffer[parts * parts * parts];
	ShortBuffer[] mIndexBuffer = new ShortBuffer[parts * parts * parts];
	FloatBuffer[] mColorBuffer = new FloatBuffer[parts * parts * parts];
	HashMap<Integer, Long> l;
	
	public StarContainer(Context context) {
		AssetManager am = context.getAssets();
		InputStream is;
		
		stars =(Vector<MVector>[]) new Vector[parts * parts * parts];
		try {
			is = am.open("binary.dat");
		} catch (IOException e) {
			return;
		}

		for (int i = 0; i < parts * parts * parts; i++) {
			stars[i] = new Vector<MVector>();
		}

		DataInputStream dis = new DataInputStream(is);

		double rmag = Math.pow(100, 0.2);
		// double rmag = 1.4f;
		double deg = Math.PI / 180.0f;
		while (true) {
			try {
				double m = (double) dis.readShort() / 100;
				// 0-24h (0-360) => 0 - 2Pi
				double RadRa = dis.readDouble() * deg;
				// -90 - 90 => -1 - 1Pi
				double RadDek = dis.readDouble() * -deg;
				// Log.d("RAD", "" + DekRad);
				double RASin = Math.sin(RadRa);
				double RACos = Math.cos(RadRa);

				double DESin = Math.sin(RadDek);
				double DECos = Math.cos(RadDek);

				double x = DECos * RACos;
				double y = DESin;
				double z = DECos * RASin;

				MVector v = new MVector((float) x, (float) y, (float) z);

				// stars to mag 4.0 are max
				float color = (float) (4.0f / Math.pow(rmag, m));

				// float color = (float) (1.0f / m);
				color = color < 0.01f ? 0.01f : (color > 1.0f ? 1.0f : color);

				v.setColor(color, color, color, 1.0f);

				// which
				double mul = 2.0f / parts;
				int wx = (int) ((x + 1.0f) / mul);
				int wy = (int) ((y + 1.0f) / mul);
				int wz = (int) ((z + 1.0f) / mul);

				stars[wx + wy * parts + wz * parts * parts].add(v);

			} catch (IOException e) {
				break;
			}
		}

		this.CreateBuffers();

	}

	private void CreateBuffers() {

		for (int i = 0; i < parts * parts * parts; i++) {
			ByteBuffer vbb = ByteBuffer.allocateDirect(stars[i].size() * 3 * 4);
			vbb.order(ByteOrder.nativeOrder());
			mFVertexBuffer[i] = vbb.asFloatBuffer();

			ByteBuffer cbb = ByteBuffer.allocateDirect(stars[i].size() * 4 * 4);
			cbb.order(ByteOrder.nativeOrder());
			mColorBuffer[i] = cbb.asFloatBuffer();

			// index
			ByteBuffer ibb = ByteBuffer.allocateDirect(stars[i].size() * 2);
			ibb.order(ByteOrder.nativeOrder());
			mIndexBuffer[i] = ibb.asShortBuffer();
		}

		short[] num = new short[parts * parts * parts];

		for (int i = 0; i < parts * parts * parts; i++) {
			for (MVector star : stars[i]) {

				mFVertexBuffer[i].put(star.getValues());
				mColorBuffer[i].put(star.getColor());
				mIndexBuffer[i].put(num[i]++);
			}

			mFVertexBuffer[i].position(0);
			mColorBuffer[i].position(0);
			mIndexBuffer[i].position(0);
		}
	}

	public void render(GL10 gl) {
		gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);

		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		for (int i = 0; i < parts * parts * parts; i++) {
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mFVertexBuffer[i]);
			gl.glColorPointer(4, GL10.GL_FLOAT, 0, mColorBuffer[i]);
			// gl.glColorMask(true, false, false, false);
			gl.glDrawElements(GL10.GL_POINTS, stars[i].size(),
					GL10.GL_UNSIGNED_SHORT, mIndexBuffer[i]);
		}
	}

}
