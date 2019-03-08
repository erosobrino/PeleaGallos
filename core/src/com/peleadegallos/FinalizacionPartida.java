package com.peleadegallos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.util.ArrayList;

/**
 * La pantalla de finalizacion mostrada despues de acabar cada partida
 */
public class FinalizacionPartida extends PlantillaEscenas {

    /**
     * El boton de reiniciar partida
     */
    TextButton btReiniciar, /**
     * El boton para ir al menu principal
     */
    btMenuPrincipal;
    /**
     * El batch para poder escribir en la pantalla
     */
    SpriteBatch batchTexto;

    /**
     * Los frames que se mostraran del ganador
     */
    Texture[] framesGanador;
    /**
     * El frame mostrado en cada momento, se quita de framesGanador
     */
    Image imgGanador = null;
    /**
     * Los jugadores de la partida
     */
    ArrayList<Jugador> jugadores;
    /**
     * El Mapa.
     */
    String mapa;

    /**
     * El tiempo de la partida
     */
    private int tiempo;
    /**
     * Las balas de la partida
     */
    int balas,
    /**
     * El id del ganador
     */
    ganador;
    /**
     * La cadena de tiempo formateada a minutos y segundos
     */
    String strTiempo;

    /**
     * El tiempo en el que cambia el frame, en ms
     */
    long tiempoF;
    /**
     * En contador de frames, cambia solo
     */
    int contFrame = 0;
    /**
     * El tiempo que tarda en cambiar de frame
     */
    int tiempoFrame = 200;

    /**
     * Devuelve el tiempo en segundos
     *
     * @return el tiempo
     */
    public int getTiempo() {
        return tiempo;
    }

    /**
     * Funcion que cuando se modifica el tiempo modifica la cadena strTiempo formateandola a minutos y segundos
     *
     * @param tiempo el nuevo tiempo
     */
    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
        strTiempo = String.format("%s: %02d:%02d", juego.idiomas.get("tiempoPartida"), tiempo / 60, tiempo % 60);
    }

    /**
     * Inicializa una nueva pantalla de finalizacion de partida
     *
     * @param juego la clase principal del juego
     */
    public FinalizacionPartida(final JuegoPrincipal juego) {
        super(juego);

        btReiniciar = new TextButton(juego.idiomas.get("reiniciar"), skin);
        btReiniciar.setSize(anchoPantalla / 7 * 2, altoPantalla / 7);
        btReiniciar.setPosition(anchoPantalla / 7, altoPantalla / 6);
        btReiniciar.getLabel().setFontScale(escalado03);
        btReiniciar.getLabel().setColor(Color.BLACK);
        btReiniciar.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (juego.vibracionEncendida)
                    Gdx.input.vibrate(juego.tiempoVibrar);
                juego.setScreen(juego.pantallaJuego);
            }
        });

        btMenuPrincipal = new TextButton(juego.idiomas.get("volverMenu"), skin);
        btMenuPrincipal.setSize(anchoPantalla / 7 * 2, altoPantalla / 7);
        btMenuPrincipal.setPosition(anchoPantalla / 7 * 4, altoPantalla / 6);
        btMenuPrincipal.getLabel().setFontScale(escalado03);
        btMenuPrincipal.getLabel().setColor(Color.BLACK);
        btMenuPrincipal.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (juego.vibracionEncendida)
                    Gdx.input.vibrate(juego.tiempoVibrar);
                juego.setScreen(juego.menuInicio);
            }
        });

        batchTexto = new SpriteBatch();
    }

    /**
     * Se ejecuta en cuando la pantalla de su muestra
     * Guarda los datos de la ultima partida
     */
    @Override
    public void show() {
        super.show();

        tiempoF = System.currentTimeMillis();       //Cuando aparece coge el tiempo actual para ir cambiando el frame

        actualizaImagen(0);                 //Modifica el frame que aparece por pantalla
        contFrame=0;
        escenario.addActor(imgGanador);

        escenario.addActor(fondo);
        escenario.addActor(btReiniciar);
        escenario.addActor(btMenuPrincipal);


        juego.datosGuardados.addRecord(new Record(jugadores.get(0).nombre, jugadores.get(1).nombre, mapa, jugadores.get(0).arma, jugadores.get(1).arma, tiempo, balas, ganador));

        juego.guardaDatos();

        Gdx.input.setInputProcessor(escenario);
    }

    /**
     * Modifica el frame actual dandole una posicion y un tamaño, si existe primero la elimin del escenario y despues la añade
     * @param indice el indice que tiene que poner cogiendolo del array de frames
     */
    private void actualizaImagen(int indice) {
        if (imgGanador != null)
            imgGanador.remove();
        imgGanador = new Image(framesGanador[indice]);
        imgGanador.setPosition(anchoPantalla / 4 * 2.75f, altoPantalla - altoPantalla / 6 * 3.5f);
        imgGanador.setSize(juego.PIXEL_METRO_X * 2, juego.PIXEL_METRO_Y * 2);
        escenario.addActor(imgGanador);
    }

    /**
     *Modiica el frame si ha pasado el tiempo necesario e indica cierta informacion de la partida
     * @param delta el tiempo desde la ultima ejecucion
     */
    @Override
    public void render(float delta) {
        super.render(delta);

        if ((System.currentTimeMillis() - tiempoF) > tiempoFrame) {
            if (contFrame >= framesGanador.length - 1)
                contFrame = 0;
            contFrame++;
            tiempoF = System.currentTimeMillis();
            actualizaImagen(contFrame);
        }

        escenario.act();    //Esto primero para que no aparezcan las nubes por encima del texto
        escenario.draw();

        batchTexto.begin();
        fuente.getData().setScale(escalado04);
        if (ganador == 1)
            fuente.draw(batchTexto, juego.idiomas.get("jug1Gana"), anchoPantalla / 7, altoPantalla - altoPantalla / 7);
        if (ganador == 2)
            fuente.draw(batchTexto, juego.idiomas.get("jug2Gana"), anchoPantalla / 7, altoPantalla - altoPantalla / 7);
        fuente.getData().setScale(escalado03);
        fuente.draw(batchTexto, juego.idiomas.get("balas") + ": " + balas, anchoPantalla / 7, altoPantalla - altoPantalla / 6 * 2);
        fuente.draw(batchTexto, strTiempo, anchoPantalla / 7, altoPantalla - altoPantalla / 6 * 3);
        batchTexto.end();
    }

    /**
     * Se ejecuta cuando la pantalla desaparece, quita los actores del escenario y tambien el inputprocessor
     */
    @Override
    public void hide() {
        super.hide();
        imgGanador.remove();
        fondo.remove();
        btReiniciar.remove();
        btMenuPrincipal.remove();

        Gdx.input.setInputProcessor(null);
    }
}
