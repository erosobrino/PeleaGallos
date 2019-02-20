package com.peleadegallos;

import com.badlogic.gdx.Gdx;
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

public class FinalizacionPartida extends PlantillaEscenas {

    Skin skin;
    TextButton btReiniciar, btMenuPrincipal;
    PlantillaEscenas pantallaAnterior;

    Stage escenario;

    SpriteBatch batchTexto;

    Texture[] framesGanador;
    Image imgGanador = null;

    int tiempo, balas, ganador;
    long tiempoF;
    int contFrame = 0;
    int tiempoFrame = 200;


    public FinalizacionPartida(final JuegoPrincipal juego) {
        super(juego);

        escenario = new Stage();
        escenario.setDebugAll(juego.debug);

        skin = new Skin(Gdx.files.internal("skin/flat-earth-ui.json"));

        btReiniciar = new TextButton(juego.idiomas.get("reiniciar"), skin);
        btReiniciar.setSize(anchoPantalla / 7 * 2, altoPantalla / 7);
        btReiniciar.setPosition(anchoPantalla / 7, altoPantalla / 6);
        btReiniciar.getLabel().setFontScale(0.3f);
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
        btMenuPrincipal.getLabel().setFontScale(0.3f);
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
        Gdx.input.setInputProcessor(escenario);
    }

    private void actualizaImagen(int indice) {
        if (imgGanador != null)
            imgGanador.remove();
        imgGanador = new Image(framesGanador[indice]);
        imgGanador.setPosition(anchoPantalla / 4 * 2.75f, altoPantalla - altoPantalla / 6*3.5f);
        imgGanador.setSize(juego.PIXEL_METRO_X*2, juego.PIXEL_METRO_Y*2);
        escenario.addActor(imgGanador);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if ((System.currentTimeMillis() - tiempoF) > tiempoFrame) {
            if (contFrame >= framesGanador.length-1)
                contFrame = 0;
            contFrame++;
            tiempoF = System.currentTimeMillis();
            actualizaImagen(contFrame);
        }

        batchTexto.begin();
        fuente.getData().setScale(0.4f);
        if (ganador == 1)
            fuente.draw(batchTexto, juego.idiomas.get("jug1Gana"), anchoPantalla / 7, altoPantalla - altoPantalla / 7);
        if (ganador == 2)
            fuente.draw(batchTexto, juego.idiomas.get("jug2Gana"), anchoPantalla / 7, altoPantalla - altoPantalla / 7);
        fuente.getData().setScale(0.3f);
        fuente.draw(batchTexto, juego.idiomas.get("balas"), anchoPantalla / 7, altoPantalla - altoPantalla / 6 * 2);
        fuente.draw(batchTexto, juego.idiomas.get("tiempoPartida"), anchoPantalla / 7, altoPantalla - altoPantalla / 6 * 3);
        batchTexto.end();

        escenario.act();
        escenario.draw();
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
