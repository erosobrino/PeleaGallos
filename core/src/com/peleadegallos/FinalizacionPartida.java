package com.peleadegallos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;

import java.util.ArrayList;

/**
 * The type Finalizacion partida.
 */
public class FinalizacionPartida extends PlantillaEscenas {

    /**
     * The Skin.
     */
    Skin skin;
    /**
     * The Bt reiniciar.
     */
    TextButton btReiniciar, /**
     * The Bt menu principal.
     */
    btMenuPrincipal;
    /**
     * The Pantalla anterior.
     */
    PlantillaEscenas pantallaAnterior;

    /**
     * The Batch texto.
     */
    SpriteBatch batchTexto;

    /**
     * The Frames ganador.
     */
    Texture[] framesGanador;
    /**
     * The Img ganador.
     */
    Image imgGanador = null;
    /**
     * The Jugadores.
     */
    ArrayList<Jugador> jugadores;
    /**
     * The Mapa.
     */
    String mapa;

    private int tiempo;
    /**
     * The Balas.
     */
    int balas, /**
     * The Ganador.
     */
    ganador;
    /**
     * The Str tiempo.
     */
    String strTiempo;

    /**
     * The Tiempo f.
     */
    long tiempoF;
    /**
     * The Cont frame.
     */
    int contFrame = 0;
    /**
     * The Tiempo frame.
     */
    int tiempoFrame = 200;

    /**
     * Gets tiempo.
     *
     * @return the tiempo
     */
    public int getTiempo() {
        return tiempo;
    }

    /**
     * Sets tiempo.
     *
     * @param tiempo the tiempo
     */
    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
        strTiempo = String.format("%s: %02d:%02d", juego.idiomas.get("tiempoPartida"), tiempo / 60, tiempo % 60);
    }

    /**
     * Instantiates a new Finalizacion partida.
     *
     * @param juego the juego
     */
    public FinalizacionPartida(final JuegoPrincipal juego) {
        super(juego);

        skin = new Skin(Gdx.files.internal("skin/flat-earth-ui.json"));

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
                juego.setScreen(pantallaAnterior);
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

    @Override
    public void show() {
        super.show();

        tiempoF = System.currentTimeMillis();

        actualizaImagen(0);
        escenario.addActor(imgGanador);

        escenario.addActor(fondo);
        escenario.addActor(btReiniciar);
        escenario.addActor(btMenuPrincipal);


        juego.datosGuardados.addRecord(new Record(jugadores.get(0).nombre, jugadores.get(1).nombre, mapa, jugadores.get(0).arma, jugadores.get(1).arma, tiempo, balas, ganador));

        juego.guardaDatos();

        Gdx.input.setInputProcessor(escenario);
    }

    private void actualizaImagen(int indice) {
        if (imgGanador != null)
            imgGanador.remove();
        imgGanador = new Image(framesGanador[indice]);
        imgGanador.setPosition(anchoPantalla / 4 * 2.75f, altoPantalla - altoPantalla / 6 * 3.5f);
        imgGanador.setSize(juego.PIXEL_METRO_X * 2, juego.PIXEL_METRO_Y * 2);
        escenario.addActor(imgGanador);
    }

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

        escenario.act();
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

    @Override
    public void hide() {
        musica.pause();
        imgGanador.remove();
        fondo.remove();
        btReiniciar.remove();
        btMenuPrincipal.remove();

        Gdx.input.setInputProcessor(null);
    }
}
