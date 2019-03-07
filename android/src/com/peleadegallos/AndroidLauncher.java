package com.peleadegallos;

import android.os.Bundle;
import android.view.WindowManager;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

/**
 * Lanza la aplicacion con libgdx
 * Tambien indica cual es el adaptador para las notificaciones
 */
public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		AdaptadorAndroid notificacionesAndroid=new AdaptadorAndroid(this);

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		JuegoPrincipal juegoPrincipal=new JuegoPrincipal();
		juegoPrincipal.setAdaptadorNotificaciones(notificacionesAndroid);
			initialize(juegoPrincipal, config);

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);  //Mantiene el juego sin que bloquee
	}

	/**
	 * Se lanza cuando se pulsa el boton atras
	 */
	@Override
	public void onBackPressed() {
//		super.onBackPressed();  // Evita salir
	}
}
