package com.peleadegallos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;

/**
 * La pantalla en la que se muestran los logros
 */
public class PantallaLogros extends PlantillaEscenas {
    /**
     * Para poder escribir en la pantalla
     */
    SpriteBatch texto;
    /**
     * La posicion del dedo para mover los records y asi ver todos
     */
    int posDedo;

    /**
     * Inicializa una nueva pantalla e logros
     *
     * @param juego la pantalla de juegos principal
     */
    public PantallaLogros(JuegoPrincipal juego) {
        super(juego);

        texto = new SpriteBatch();
    }

    /**
     * Se ejecuta al mostrar la pantalla, añade los actores y cambia el inputprocessor por
     * uno que gestiona el deplazamiento del dedo, GestosScrollRecords
     */
    @Override
    public void show() {
        super.show();

        escenario.addActor(fondo);
        escenario.addActor(home);
        fuente.getData().setScale(escalado025);

        posDedo = 0;

        Gdx.input.setInputProcessor(new GestosScrollRecords(new GestureDetector.GestureAdapter(), escenario, altoPantalla, this));
    }

    /**
     * Renderiza la pantalla actual y escribe la infromacion de los records
     *
     * @param delta el tiempo desde la ultima ejecucion, marca el gandor y perdedor por colores
     */
    @Override
    public void render(float delta) {
        super.render(delta);

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK))
            juego.setScreen(juego.menuInicio);

        escenario.act();
        escenario.draw();

        texto.begin();

        float y = altoPantalla / 8 * 6.5f;
        fuente.draw(texto, juego.idiomas.get("partidas") + ": " + juego.datosGuardados.getPartidas(), anchoPantalla / 10, y * 1.15f);
        fuente.draw(texto, juego.idiomas.get("balas") + ": " + juego.datosGuardados.getBalas(), anchoPantalla / 10 * 3.5f, y * 1.15f);
        String tiempoStringTotal = String.format("%02d:%02d", juego.datosGuardados.getTiempo() / 60, juego.datosGuardados.getTiempo() % 60);
        fuente.draw(texto, juego.idiomas.get("tiempoTotal") + ": " + tiempoStringTotal, anchoPantalla / 10 * 6.75f, y * 1.15f);


        fuente.draw(texto, juego.idiomas.get("jugador") + "1", anchoPantalla / 10 * 1, y);
        fuente.draw(texto, juego.idiomas.get("arma") + "", anchoPantalla / 10 * 2.75f, y);
        fuente.draw(texto, juego.idiomas.get("jugador") + "2", anchoPantalla / 10 * 3.75f, y);
        fuente.draw(texto, juego.idiomas.get("arma") + "", anchoPantalla / 10 * 5.5f, y);
        fuente.draw(texto, juego.idiomas.get("mapa") + "", anchoPantalla / 10 * 6.75f, y);
        fuente.draw(texto, juego.idiomas.get("balasRestantes") + "", anchoPantalla / 10 * 7.75f, y);
        fuente.draw(texto, juego.idiomas.get("tiempo") + "", anchoPantalla / 10 * 8.75f, y);
        y -= altoPantalla / 8;

        for (int i = 0 + posDedo; i < juego.datosGuardados.getRecords().size(); i++) {
//            try {
                fuente.draw(texto, (i + 1) + "", anchoPantalla / 10 * 0.25f, y);
                if (juego.datosGuardados.getRecords().get(i).idGanador == 1)
                    fuente.setColor(Color.YELLOW);
                else
                    fuente.setColor(Color.RED);
                fuente.draw(texto, juego.idiomas.get(juego.datosGuardados.getRecords().get(i).jugador1), anchoPantalla / 10 * 1, y);
                fuente.draw(texto, juego.idiomas.get(juego.datosGuardados.getRecords().get(i).arma1), anchoPantalla / 10 * 2.75f, y);
                if (juego.datosGuardados.getRecords().get(i).idGanador == 2)
                    fuente.setColor(Color.YELLOW);
                else
                    fuente.setColor(Color.RED);
                fuente.draw(texto, juego.idiomas.get(juego.datosGuardados.getRecords().get(i).jugador2), anchoPantalla / 10 * 3.75f, y);
                fuente.draw(texto, juego.idiomas.get(juego.datosGuardados.getRecords().get(i).arma2), anchoPantalla / 10 * 5.5f, y);
                fuente.setColor(Color.BLACK);
                fuente.draw(texto, juego.idiomas.get(juego.datosGuardados.getRecords().get(i).mapa), anchoPantalla / 10 * 6.75f, y);
                fuente.draw(texto, juego.datosGuardados.getRecords().get(i).balas + "", anchoPantalla / 10 * 7.75f, y);
                String tiempoString = String.format("%02d:%02d", juego.datosGuardados.getRecords().get(i).tiempo / 60, juego.datosGuardados.getRecords().get(i).tiempo % 60);
                fuente.draw(texto, tiempoString, anchoPantalla / 10 * 8.75f, y);
//            } catch (Exception e) {
//                fuente.setColor(Color.BLACK);
//            }//Salta si alguno de los datos no esta en los idiomas
            y -= altoPantalla / 8;
        }
        texto.end();
    }

    /**
     * Se ejecuta cuando la pantalla descarece, quita los actores y el inputprocessor
     */
    @Override
    public void hide() {
        super.hide();
        fondo.remove();
        home.remove();

        Gdx.input.setInputProcessor(null);
    }
}
