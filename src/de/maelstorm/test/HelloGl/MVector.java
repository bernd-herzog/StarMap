package de.maelstorm.test.HelloGl;

public class MVector {
	private float[] p = new float[3];
	private float[] c = new float[4];

	public MVector(float x, float y, float z) {
		this.p[0] = x;
		this.p[1] = y;
		this.p[2] = z;
	}

	public MVector() {

	}

	public MVector(MVector v) {
		p = v.p.clone();
	}

	public MVector(float p[]) {
		this.p = p.clone();
	}

	public float getX() {
		return p[0];
	}

	public float getY() {
		return p[1];
	}

	public float getZ() {
		return p[2];
	}

	public void add(MVector v) {
		if (v == null)
			return;
		this.p[0] += v.p[0];
		this.p[1] += v.p[1];
		this.p[2] += v.p[2];
	}

	public void sub(MVector v) {
		if (v == null)
			return;
		this.p[0] -= v.p[0];
		this.p[1] -= v.p[1];
		this.p[2] -= v.p[2];
	}

	public MVector cross(MVector v) {
		MVector ret = new MVector(0, 0, 0);
		ret.p[0] = this.p[1] * v.p[2] - this.p[2] * v.p[1];
		ret.p[1] = this.p[2] * v.p[0] - this.p[0] * v.p[2];
		ret.p[2] = this.p[0] * v.p[1] - this.p[1] * v.p[0];
		ret.normalize();
		return ret;
	}

	public float[] getValues() {
		return p;
	}

	public void mult(float m) {
		this.p[0] *= m;
		this.p[1] *= m;
		this.p[2] *= m;
	}

	public void div(float m) {
		this.p[0] /= m;
		this.p[1] /= m;
		this.p[2] /= m;
	}

	public void normalize() {
		float length = p[0] * p[0] + p[1] * p[1] + p[2] * p[2];
		this.div((float)Math.sqrt(length));
	}

	public float[] getColor() {
		return c;
	}
	
	public void setColor(float r, float g, float b, float a){
		c[0]=r;
		c[1]=g;
		c[2]=b;
		c[3]=a;
	}
}
