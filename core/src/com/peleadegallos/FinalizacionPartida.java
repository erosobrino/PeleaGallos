package com.peleadegallos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class FinalizacionPartida extends PlantillaEscenas {

    Skin skin;
    TextButton btReiniciar, btMenuPrincipal;
    PlantillaEscenas pantallaAnterior;

    Stage escenario;

    SpriteBatch batchTexto;

    int tiempo, balas, ganador;


    public FinalizacionPartida(final JuegoPrincipal juego) {
        super(juego);

        escenario = new Stage();
        escenario.setDebugAll(juego.debug);

        skin = new Skin(Gdx.files.internal("skin/flat-earth-ui.json"));

        btReiniciar = new TextButton(juego.idiomas.get("reiniciar"), skin);
        btReiniciar.setSize(anchoPantalla / 7*2, altoPantalla / 7);
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
        btMenuPrincipal.setSize(anchoPantalla / 7*2, altoPantalla / 7);
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

        fuente.getData().setScale(0.3f);

        escenario.addActor(fondo);
        escenario.addActor(btReiniciar);
        escenario.addActor(btMenuPrincipal);
        Gdx.input.setInputProcessor(escenario);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        batchTexto.begin();
        if (ganador == 1)
            fuente.draw(batchTexto, juego.idiomas.get("jug1Gana"), anchoPantalla / 3, altoPantalla - altoPantalla / 6);
        if (ganador == 2)
            fuente.draw(batchTexto, juego.idiomas.get("jug2Gana"), anchoPantalla / 3, altoPantalla - altoPantalla / 6);
        fuente.draw(batchTexto, juego.idiomas.get("balas"), anchoPantalla / 7, altoPantalla - altoPantalla / 6 * 2);
        fuente.draw(batchTexto, juego.idiomas.get("tiempoPartida"), anchoPantalla / 7, altoPantalla - altoPantalla / 6 * 3);
        batchTexto.end();

        escenario.act();
        escenario.draw();
    }

    @Override
    public void hide() {
        musica.pause();
        fondo.remove();
        btReiniciar.remove();
        btMenuPrincipal.remove();

        Gdx.input.setInputProcessor(null);
    }
}
