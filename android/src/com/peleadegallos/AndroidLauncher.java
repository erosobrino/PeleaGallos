package com.peleadegallos;

import android.os.Bundle;
import android.view.WindowManager;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		AdaptadorAndroid notificacionesAndroid=new AdaptadorAndroid(this);

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		JuegoPrincipal juegoPrincipal=new JuegoPrincipal();
		juegoPrincipal.setAdaptadorNotificaciones(notificacionesAndroid);

		initialize(juegoPrincipal, config);

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	@Override
	public void onBackPressed() {
//		super.onBackPressed();  // Evita salir
	}
}
