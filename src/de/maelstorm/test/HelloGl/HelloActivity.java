package de.maelstorm.test.HelloGl;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class HelloActivity extends Activity {
	HelloGLSurfaceView view;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		Window window = this.getWindow();
		window.requestFeature(Window.FEATURE_NO_TITLE);
		window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		this.view = new HelloGLSurfaceView(this);
		setContentView(this.view);
	}

	@Override
	protected void onPause() {
		super.onPause();
		this.view.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		this.view.onResume();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		System.exit(0);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
