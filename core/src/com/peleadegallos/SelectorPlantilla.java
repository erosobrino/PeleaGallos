package com.peleadegallos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * The type Selector plantilla.
 */
public class SelectorPlantilla extends PlantillaEscenas {

    /**
     * The Continuar.
     */
    TextButton continuar, /**
     * The Anterior.
     */
    anterior;
    /**
     * The Id jugador.
     */
    int idJugador;
    /**
     * The Sprite batch.
     */
    SpriteBatch spriteBatch;

    /**
     * Instantiates a new Selector plantilla.
     *
     * @param juego     the juego
     * @param idJugador the id jugador
     */
    public SelectorPlantilla(final JuegoPrincipal juego, final int idJugador) {
        super(juego);
        this.idJugador = idJugador;

        continuar = new TextButton(juego.idiomas.get("continuar"), skin);
        continuar.setSize(anchoPantalla / 7 * 2, altoPantalla / 5);
        continuar.setPosition(anchoPantalla - anchoPantalla / 7 * 2 * 1.05f, altoPantalla * 0.05f);
        continuar.scaleBy(2);
        continuar.getLabel().setPosition(2, 2);
        continuar.getLabel().setFontScale(escalado05);
        continuar.getLabel().setColor(Color.BLACK);
        continuar.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                juego.botonPulsado(sonidoClick);
                switch (idJugador) {
                    case 1:
                        juego.setScreen(juego.selectorPersonajeArma2);
                        break;
                    case 2:
                        juego.setScreen(juego.selectorMapa);
                        break;
                    case 3:
                        juego.setScreen(juego.pantallaJuego);
                        break;
                }
            }
        });

        anterior = new TextButton(juego.idiomas.get("atras"), skin);
        anterior.setSize(anchoPantalla / 7 * 2, altoPantalla / 5);
        anterior.setPosition(anchoPantalla - anchoPantalla / 7 * 2 * 1.05f, altoPantalla * 0.95f - altoPantalla / 5);
        anterior.scaleBy(2);
        anterior.getLabel().setPosition(2, 2);
        anterior.getLabel().setFontScale(escalado05);
        anterior.getLabel().setColor(Color.BLACK);
        anterior.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                juego.botonPulsado(sonidoClick);
                switch (idJugador) {
                    case 1:
                        juego.setScreen(juego.menuInicio);
                        break;
                    case 2:
                        juego.setScreen(juego.selectorPersonajeArma);
                        break;
                    case 3:
                        juego.setScreen(juego.selectorPersonajeArma2);
                        break;
                }
            }
        });

        spriteBatch = new SpriteBatch();
    }

    @Override
    public void show() {
        super.show();
        escenario.addActor(fondo);
        escenario.addActor(continuar);
        escenario.addActor(anterior);
        escenario.addActor(home);

        Gdx.input.setInputProcessor(escenario);

        fuente.getData().setScale(escalado025);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            InputEvent evento = new InputEvent();
            evento.setType(InputEvent.Type.touchDown);
            anterior.fire(evento);
            evento.setType(InputEvent.Type.touchUp);
            anterior.fire(evento);
        }

        escenario.act();
        escenario.draw();
    }

    @Override
    public void hide() {
        super.hide();

        continuar.remove();
        anterior.remove();
        home.remove();
        fondo.remove();
        Gdx.input.setInputProcessor(null);
    }
}